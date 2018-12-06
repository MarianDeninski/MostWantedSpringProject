package mostwanted.domain.dtos.xml;

import mostwanted.domain.dtos.xml.wrapper.RaceEntriesIdWrapper;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "race")
@XmlAccessorType(XmlAccessType.FIELD)
public class RaceDtoXml {


    @XmlElement(name = "laps")
    @NotNull
    private int laps;

    @XmlElement(name = "district-name")
    @NotNull
    private String district;

    @XmlElement(name = "entries")
    private RaceEntriesIdWrapper entries;


    public int getLaps() {
        return laps;
    }

    public void setLaps(int laps) {
        this.laps = laps;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public RaceEntriesIdWrapper getEntries() {
        return entries;
    }

    public void setEntries(RaceEntriesIdWrapper entries) {
        this.entries = entries;
    }
}
