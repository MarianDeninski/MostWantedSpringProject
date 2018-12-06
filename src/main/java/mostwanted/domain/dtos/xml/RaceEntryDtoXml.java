package mostwanted.domain.dtos.xml;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "race-entry")
@XmlAccessorType(XmlAccessType.FIELD)
public class RaceEntryDtoXml {


    @XmlAttribute(name = "has-finished")
    private boolean hasFinished;

    @XmlAttribute(name = "finish-time")
    private Double finisheTime;

    @XmlAttribute(name = "car-id")
    private int carId;

    @XmlElement(name = "racer")
    private String racer;

    public boolean isHasFinished() {
        return hasFinished;
    }

    public void setHasFinished(boolean hasFinished) {
        this.hasFinished = hasFinished;
    }

    public Double getFinisheTime() {
        return finisheTime;
    }

    public void setFinisheTime(Double finisheTime) {
        this.finisheTime = finisheTime;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getRacer() {
        return racer;
    }

    public void setRacer(String racer) {
        this.racer = racer;
    }
}
