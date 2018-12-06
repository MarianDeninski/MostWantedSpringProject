package mostwanted.repository;

import mostwanted.domain.entities.Racer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RacerRepository extends JpaRepository<Racer,Integer> {

    Racer findAllByName(String name);


    @Query("SELECT r FROM Racer r WHERE r.name is not null and r.age is not null " +
            " ORDER BY size(r.cars) DESC,r.name ")
    List<Racer> getCarsOrderBy();
}
