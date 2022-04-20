package at.fhhgb.persistenceservice.repository;

import at.fhhgb.persistenceservice.entity.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity,Integer>{

    @Query
    public List<RecipeEntity> findAllByType(String type);
}
