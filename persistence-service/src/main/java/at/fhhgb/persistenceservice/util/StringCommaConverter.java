package at.fhhgb.persistenceservice.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.List;

@Converter
public class StringCommaConverter implements AttributeConverter<List<String>,String>{

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        return String.join(",", attribute);
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if(dbData!=null &&!dbData.isEmpty()){
            return List.of(dbData.split(","));
        }
        return new ArrayList<>();
    }
}
