<div style="text-align: center;">
<h1  style="font-size: 35px">Spring-Opentracing</h1>
<h3>Julian Pichler</h3> 
<h3>Tobias Wecht</h3>
</div>

## Zielsetzung
Das Ziel dieses Projekts ist es anhand einer simplen Essens-Rezept Applikation zu zeigen, wie man die Open-Tracing Software Jaeger in eine verteilte Spring-Service Architektur integriert.

## Architektur
![alt text](/images/architektur.jpg)

Die beiden Services wurden mit Spring+Java realisiert und für die Datenbank wurde MySQL gewählt. Die restful Requests, sowie die Datenbankzugriffe, des gesammten Systems wird mit Opentracing und Jaeger gelogged. Das [Deployment](#installationsanleitung) aller Komponenten des Systems wird über Docker gehandhabt.

Jaeger Web UI: http://localhost:16686

MySQL: jdbc:mysql://localhost:3306

MySQL Credentials sind im docker-compose File zu finden.

Das Interaction service ist für die Kommunikation mit den Clients(Benutzer) zuständig und liefert, unter Verwendung von Thymeleaf, ein HTML an diesen.
Das Service bzw. die HTML Representation der Rezepte-Übersichts Seite und aller andere Funktionen kann über http://localhost:8080/api/recipe/html erreicht werden.

Das Persistence Service ist für die Verwaltung der Daten in der MySQL Datenbank zuständig und wird vom Interaction Service mittels restful Requests genutzt. Das Service kann unter http://localhost:8888/api/... erreicht werden. 

Persistence  Service Requests:

GET Recipes: http://persistence-service:8888/api/recipe?type=ALL

Delete Recipe: http://persistence-service:8888/api/recipe/{id}

POST Recipe: http://persistence-service:8888/api/recipe/

    Form-Data: title={Title}&description={Description}&imageUrl={example.com}


## Umsetzung

### Kommunikation vom User bis zur Datenbank

Client greift über einen GET-Request auf den interaction-service zu.

```java
@GetMapping("/html/{type}")
public String getRecipesHTML(@PathVariable(value="type", required = false) String type, Model model){

    RecipeType recipeType;
    try {
        recipeType = RecipeType.valueOf(type);
    }catch (IllegalArgumentException e){
        recipeType = RecipeType.ALL;
    }
    model.addAttribute("recipeForm", new RecipeCreationDto());
    model.addAttribute("recipes",restTemplate.getRecipes(recipeType));
    return HtmlTemplates.INDEX;

}
```

Es wird die Template-Engine Thymleaf verwendet um HTML für den User zu generieren. Hier ein Auszug aus der Index.html zu Generierung der einzelnen Rezept-Karten:

```html
  <div class="card-body">
      <h4 class="card-title" th:text="${recipe.title}"></h4>
      <div th:switch="${recipe.type}">
          <span th:case="'VEGETARIAN'" class="badge badge-success" th:text="${recipe.type}"></span>
          <span th:case="'MEAT'" class="badge badge-danger" th:text="${recipe.type}"></span>
      </div>
      <a th:href="@{~/api/recipe/html/details/{id}(id=${recipe.id})}"> Information</a>
      <p class="card-text"><small class="text-muted" th:text="${recipe.date}"></small></p>
  </div>
```

Der interaction-service greift über ein RestTemplate auf den persistence-service zu.

```java
public RecipeDto getRecipe(int id){
    String urlTemplate = UriComponentsBuilder.fromHttpUrl(baseUrl+"/api/recipe/"+id)
            .encode()
            .toUriString();

    try {
        return rest.getForObject(urlTemplate,RecipeDto.class);
    }catch (RestClientException ex){
        logger.error("getRecipes()",ex);
        return null;
    }

}
```

Der persistence-service ist über einen REST-Controller erreichbar.

```java
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{id}")
  public ResponseEntity<RecipeEntity> getRecipe(@PathVariable(value="id")  int id){

      if(recipeService.getRecipe(id).isPresent()){
          return new ResponseEntity<>(recipeService.getRecipe(id).get(), HttpStatus.OK);
      }else{
          return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }
  }

```

Über Spring-Data greift der persistence-service auf die von Hibernate gemanagte Datenbank zu.

```java
@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity,Integer>{

    @Query
    public List<RecipeEntity> findAllByType(String type);
}
```



### Jaeger-Einbindung

Jaeger Service im docker-compose
```yml
  jaeger:
    image: jaegertracing/all-in-one:latest
    container_name: jaeger
    ports:
      - "6831:6831/udp"
      - "16686:16686"
      - "4317:4317"
    networks:
      - jaeger_net     
```

Properties für Jaeger Setup im persistence-service
```properties
spring.application.name=persistence-service
opentracing.jaeger.udp-sender.host=jaeger
opentracing.jaeger.udp-sender.port=6831
opentracing.jaeger.log-spans=true
```

Properties für Jaeger Setup im interaction-service
```properties
spring.application.name=interaction-service
opentracing.jaeger.udp-sender.host=jaeger
opentracing.jaeger.udp-sender.port=6831
opentracing.jaeger.log-spans=true
```

Damit die zusammenhängend Spans über REST-Kommunikation richtig bei Jaeger getraced werden, ist es wichtig, das RestTemplate als Bean bekannt zu machen.
```java
@Bean
public RestTemplate restTemplate(RestTemplateBuilder builder) {
  return builder.build();
}
  ```


## Ergebnis
### UI
Auf der Startseite der Beispiel-Applikation findet man eine Übersicht über alle Rezepte. Man kann nach Rezepten filtern, einzelne Rezepte löschen oder über ein Formular neue Rezepte hinzufügen.

![alt text](/images/UI-1.jpg)

Mit einem Klick auf "Information" gelangt man zur Detail-Seite.

![alt text](/images/UI-2.jpg)


### Jaeger-Tracing
Es ist nun möglich die Requests des Users über die verteilten Services hinweg zu tracen. Diese Traces verlaufen vom interaction-service bis zur Datenbank.

![alt text](/images/jaeger-trace.jpg)

Da die Datenbank und das gesamte Entity Management mit Hibernate umgesetzt wurde, ist auch die Datenbank in dem Jaeger-Trace integriert und es ist u.a. möglich, Informationen über die generierten Querys abzufragen.

![alt text](/images/jaeger-trace-with-query.jpg)

Wenn im Zuge der Kommunikation der Services bzw. innerhalb eines Services eine Exception geworfen wird, ist diese sofort bereits in der Übersicht erkennbar.

![alt text](/images/error-persistence-service-1.jpg)

Über den jeweiligen Error, kann man alle Details abfragen. Debugging in verteilten Systemen wird dadurch sehr stark vereinfacht.

![alt text](/images/error-persistence-service-2.jpg)

## Conclusion
Die Integrierung von Jaeger in eine verteilte Spring-Service-Architektur, gestaltete sich problemlos. Es sind dafür nur wenige Konfigurationen und Abhängigkeiten zu setzen. Die besondere Stärke von Jaeger sehen wir in der Verfolgung von Requests in einer verteilten Architektur um eventuelle Bottlenecks zu identifizieren und dadurch Performanceverbesserungen umzusetzen. Außerdem wird wie bereits erwähnt das Debuggen sehr viel einfacher.

Zusätzlich haben wir versucht die Monitoring-Funktionalität in Kombination mit Prometheus zu aktivieren. Diese Funktionalität ist auf der Seite von Jaeger noch im Status "experimental" und durch mangelnde Dokumentation, konnten wir diese nicht lauffähig umsetzen.


## Installationsanleitung
Das start.ps1 Skript ausführen, dieses baut die Jars der Web Services und führt anschließend das docker-compose aus.

Jaeger: http://localhost:16686/search

Interaction-Service: http://localhost:8080/api/recipe/html
