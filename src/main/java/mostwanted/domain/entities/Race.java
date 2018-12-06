package mostwanted.domain.entities;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name = "races")
public class Race extends BaseEntity {

    private int laps;

    private District district;

    private List<RaceEntries> raceEntriesList;


    @Column(name = "laps",nullable = false)
    public int getLaps() {
        return laps;
    }

    public void setLaps(int laps) {
        this.laps = laps;
    }

    @ManyToOne(targetEntity = District.class)
    @JoinColumn(name = "district_id",referencedColumnName = "id",nullable = false)
    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    @OneToMany(targetEntity = RaceEntries.class,mappedBy = "race")
    public List<RaceEntries> getRaceEntriesList() {
        return raceEntriesList;
    }

    public void setRaceEntriesList(List<RaceEntries> raceEntriesList) {
        this.raceEntriesList = raceEntriesList;
    }
}
