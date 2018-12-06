package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.domain.dtos.json.DistrictDto;
import mostwanted.domain.dtos.json.RacersDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.District;
import mostwanted.domain.entities.Racer;
import mostwanted.repository.RacerRepository;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class RacerServiceImpl implements RacerService {

    private final static String IMPORT_RACERS = "src/main/resources/files/racers.json";
    private final RacerRepository racerRepository;
    private final TownRepository townRepository;

    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final FileUtil fileUtil;
    private final Gson gson;

    public RacerServiceImpl(RacerRepository racerRepository,
                            TownRepository townRepository,
                            ModelMapper modelMapper,
                            ValidationUtil validationUtil,
                            FileUtil fileUtil, Gson gson) {
        this.racerRepository = racerRepository;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.fileUtil = fileUtil;
        this.gson = gson;
    }

    @Override
    public Boolean racersAreImported() {
        return this.racerRepository.count()>0;
    }

    @Override
    public String readRacersJsonFile() throws IOException {
        return this.fileUtil.readFile(IMPORT_RACERS);
    }

    @Override
    public String importRacers(String racersFileContent) {


        StringBuilder str = new StringBuilder();

        RacersDto[] racersDtos = this.gson.fromJson(racersFileContent, RacersDto[].class);

        Arrays.stream(racersDtos).forEach(claz->{

            Racer checker = this.racerRepository.findAllByName(claz.getName());

            if(checker!=null){

                str.append("Error: Duplicate Data!").append(System.lineSeparator());

                return;

            }
            if(!this.validationUtil.isValid(claz)){

                str.append("Error: Incorrect Data!").append(System.lineSeparator());

                return;
            }
            Racer racer = this.modelMapper.map(claz,Racer.class);
            racer.setTown(this.townRepository.findAllByName(claz.getHomeTown()));

            str.append(String.format("Successfully imported Racer - %s.\n",claz.getName()));

            this.racerRepository.saveAndFlush(racer);

        });
        return str.toString();



    }

    @Override
    public String exportRacingCars() {

        List<Racer> list = this.racerRepository.getCarsOrderBy();

        StringBuilder sb = new StringBuilder();

        for (Racer racer : list) {

            if(racer.getCars().size()!=0){

            }else{
                continue;
            }

            sb.append(String.format("Name: %s",racer.getName()));
            sb.append(System.lineSeparator());
            sb.append("Cars:");
            sb.append(System.lineSeparator());




            for (Car car : racer.getCars()) {

                sb.append(car.getBrand()+" "+car.getModel()+" "+car.getYearOfProduction())
                        .append(System.lineSeparator());


            }
            sb.append(System.lineSeparator());
            sb.append(System.lineSeparator());

        }


        return sb.toString().trim();
    }
}
