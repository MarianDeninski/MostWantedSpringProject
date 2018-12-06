package mostwanted.service;

import mostwanted.domain.dtos.xml.RaceDtoXml;
import mostwanted.domain.dtos.xml.RaceEntriesIdDtoXml;
import mostwanted.domain.dtos.xml.wrapper.RaceWrapper;
import mostwanted.domain.entities.Race;
import mostwanted.domain.entities.RaceEntries;
import mostwanted.repository.DistrictRepository;
import mostwanted.repository.RaceEntryRepository;
import mostwanted.repository.RaceRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import mostwanted.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RaceServiceImpl implements RaceService {

    private final static String IMPORT_RACE = "src/main/resources/files/races.xml";

    private final DistrictRepository districtRepository;
    private final RaceEntryRepository raceEntryRepository;
    private final RaceRepository raceRepository;

    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;

    public RaceServiceImpl(DistrictRepository districtRepository,
                           RaceEntryRepository raceEntryRepository,
                           RaceRepository raceRepository, ModelMapper modelMapper,
                           ValidationUtil validationUtil,
                           FileUtil fileUtil,
                           XmlParser xmlParser) {
        this.districtRepository = districtRepository;
        this.raceEntryRepository = raceEntryRepository;
        this.raceRepository = raceRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public Boolean racesAreImported() {
        return this.raceRepository.count()>0;
    }

    @Override
    public String readRacesXmlFile() throws IOException {
        return this.fileUtil.readFile(IMPORT_RACE);
    }

    @Override
    public String importRaces() throws JAXBException {


        RaceWrapper raceWrapper = this.xmlParser.parseXml(RaceWrapper.class,IMPORT_RACE);

        StringBuilder sb = new StringBuilder();
        int num = 1;
        for (RaceDtoXml raceDtoXml : raceWrapper.getRaces()) {

            Race race = new Race();

            if(!this.validationUtil.isValid(raceDtoXml)){

                sb.append("Error: Incorrect Data!").append(System.lineSeparator());

            }
            race.setLaps(raceDtoXml.getLaps());
            race.setDistrict(this.districtRepository.findAllByName(raceDtoXml.getDistrict()));



            List<RaceEntries> entries = new ArrayList<>();

            for (RaceEntriesIdDtoXml raceEntriesIdWrapper : raceDtoXml.getEntries().getEntries()) {

                    RaceEntries raceEntries = this.raceEntryRepository.findAllById(raceEntriesIdWrapper.getId());

                    raceEntries.setRace(race);
                    entries.add(raceEntries);

            }
            race.setRaceEntriesList(entries);
            sb.append(String.format("Successfully imported Race - %s.",num)).append(System.lineSeparator());
            num++;

            this.raceRepository.saveAndFlush(race);

        }

        return sb.toString().trim();
    }
}
