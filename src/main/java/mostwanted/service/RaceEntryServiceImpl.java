package mostwanted.service;
import mostwanted.domain.dtos.xml.RaceEntryDtoXml;
import mostwanted.domain.dtos.xml.wrapper.RaceEntryDtoWrapper;
import mostwanted.domain.entities.RaceEntries;
import mostwanted.repository.CarRepository;
import mostwanted.repository.RaceEntryRepository;
import mostwanted.repository.RacerRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import mostwanted.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import javax.xml.bind.JAXBException;
import java.io.IOException;

@Service
public class RaceEntryServiceImpl implements RaceEntryService {

    private final static String IMPORT_RACE_ENTRIES = "src/main/resources/files/race-entries.xml";
    private final CarRepository carRepository;
    private final RacerRepository racerRepository;
    private final RaceEntryRepository raceEntryRepository;

    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;

    public RaceEntryServiceImpl(CarRepository carRepository, RacerRepository racerRepository,
                                RaceEntryRepository raceEntryRepository, ModelMapper modelMapper,
                                ValidationUtil validationUtil,
                                FileUtil fileUtil, XmlParser xmlParser) {
        this.carRepository = carRepository;
        this.racerRepository = racerRepository;
        this.raceEntryRepository = raceEntryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public Boolean raceEntriesAreImported() {
        return raceEntryRepository.count()>0;
    }

    @Override
    public String readRaceEntriesXmlFile() throws IOException {

        return this.fileUtil.readFile(IMPORT_RACE_ENTRIES);
    }

    @Override
    public String importRaceEntries() throws JAXBException {

        RaceEntryDtoWrapper raceEntries = this.xmlParser.parseXml(RaceEntryDtoWrapper.class,IMPORT_RACE_ENTRIES);

        StringBuilder sb = new StringBuilder();
        int num = 1;
        for (RaceEntryDtoXml raceEntryDtoXml :raceEntries.getRaceEntries() ) {

            RaceEntries raceEntries1 = new RaceEntries();

            raceEntries1.setHasFinished(raceEntryDtoXml.isHasFinished());
            raceEntries1.setFinishedTime(raceEntryDtoXml.getFinisheTime());
            raceEntries1.setCar(this.carRepository.findAllById(raceEntryDtoXml.getCarId()));
            raceEntries1.setRacer(this.racerRepository.findAllByName(raceEntryDtoXml.getRacer()));

            sb.append(String.format("Successfully imported RaceEntry - %s.",num));
                    num++;
            this.raceEntryRepository.saveAndFlush(raceEntries1);

        }
        return sb.toString().trim();
    }
}
