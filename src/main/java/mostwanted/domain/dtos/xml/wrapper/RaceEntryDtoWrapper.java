package mostwanted.domain.dtos.xml.wrapper;

import mostwanted.domain.dtos.xml.RaceEntryDtoXml;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "race-entries")
@XmlAccessorType(XmlAccessType.FIELD)
public class RaceEntryDtoWrapper {

    @XmlElement(name = "race-entry")
    private List<RaceEntryDtoXml> raceEntries;


    public List<RaceEntryDtoXml> getRaceEntries() {
        return raceEntries;
    }

    public void setRaceEntries(List<RaceEntryDtoXml> raceEntries) {
        this.raceEntries = raceEntries;
    }
}
