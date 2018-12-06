package mostwanted.domain.dtos.xml.wrapper;

import mostwanted.domain.dtos.xml.RaceDtoXml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "races")
@XmlAccessorType(XmlAccessType.FIELD)
public class RaceWrapper {


    @XmlElement(name = "race")
    private List<RaceDtoXml> races;


    public List<RaceDtoXml> getRaces() {
        return races;
    }

    public void setRaces(List<RaceDtoXml> races) {
        this.races = races;
    }
}
