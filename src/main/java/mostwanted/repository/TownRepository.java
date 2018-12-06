package mostwanted.repository;

import mostwanted.domain.entities.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TownRepository extends JpaRepository<Town,Integer> {

    Town findAllByName(String name);


    @Query("SELECT t FROM Town t ORDER BY size(t.racers) DESC,t.name")
    List<Town> getTownsOrderBy();
}
