package mostwanted.domain.dtos.xml.wrapper;

import mostwanted.domain.dtos.xml.RaceEntriesIdDtoXml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "entries")
@XmlAccessorType(XmlAccessType.FIELD)
public class RaceEntriesIdWrapper {


    @XmlElement(name = "entry")
    private List<RaceEntriesIdDtoXml> entries;


    public List<RaceEntriesIdDtoXml> getEntries() {
        return entries;
    }

    public void setEntries(List<RaceEntriesIdDtoXml> entries) {
        this.entries = entries;
    }
}
