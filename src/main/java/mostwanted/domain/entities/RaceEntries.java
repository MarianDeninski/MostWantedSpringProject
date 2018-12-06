package mostwanted.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "race_entries")
public class RaceEntries extends BaseEntity {

    private boolean hasFinished;

    private Double FinishedTime;

    private Car car;

    private Racer racer;

    private Race race;


    @ManyToOne(targetEntity = Race.class)
    @JoinColumn(name = "race_id",referencedColumnName = "id")
    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    @Column(name = "has_finished")
    public boolean isHasFinished() {
        return hasFinished;
    }

    public void setHasFinished(boolean hasFinished) {
        this.hasFinished = hasFinished;
    }

    @Column(name = "finish_time")
    public Double getFinishedTime() {
        return FinishedTime;
    }

    public void setFinishedTime(Double finishedTime) {
        FinishedTime = finishedTime;
    }

    @ManyToOne(targetEntity = Car.class)
    @JoinColumn(name = "car_id",referencedColumnName = "id")
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
    @ManyToOne(targetEntity = Racer.class)
    @JoinColumn(name = "racer_id",referencedColumnName = "id")
    public Racer getRacer() {
        return racer;
    }

    public void setRacer(Racer racer) {
        this.racer = racer;
    }
}
