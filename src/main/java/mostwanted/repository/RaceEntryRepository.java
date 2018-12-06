package mostwanted.repository;


import mostwanted.domain.entities.RaceEntries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaceEntryRepository extends JpaRepository<RaceEntries,Integer> {


    RaceEntries findAllById(Integer id);
}
