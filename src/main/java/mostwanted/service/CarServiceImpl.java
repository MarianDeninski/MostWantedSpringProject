package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.domain.dtos.json.CarDto;
import mostwanted.domain.entities.Car;
import mostwanted.repository.CarRepository;
import mostwanted.repository.RacerRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class CarServiceImpl implements CarService {

    private final static String IMPORT_CAR = "src/main/resources/files/cars.json";
    private final RacerRepository racerRepository;
    private final CarRepository carRepository;


    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final FileUtil fileUtil;
    private final Gson gson;

    public CarServiceImpl(RacerRepository racerRepository,
                          CarRepository carRepository,
                          ModelMapper modelMapper,
                          ValidationUtil validationUtil,
                          FileUtil fileUtil, Gson gson) {
        this.racerRepository = racerRepository;
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.fileUtil = fileUtil;
        this.gson = gson;
    }

    @Override
    public Boolean carsAreImported() {
        return this.carRepository.count()>0;
    }

    @Override
    public String readCarsJsonFile() throws IOException {
        return this.fileUtil.readFile(IMPORT_CAR);
    }

    @Override
    public String importCars(String carsFileContent) {

        StringBuilder str = new StringBuilder();

        CarDto[] districtDto = this.gson.fromJson(carsFileContent, CarDto[].class);

        Arrays.stream(districtDto).forEach(claz->{

            if(!this.validationUtil.isValid(claz)){

                str.append("Error: Incorrect Data!").append(System.lineSeparator());

                return;
            }
            Car car = this.modelMapper.map(claz,Car.class);
            car.setRacer(this.racerRepository.findAllByName(claz.getRacerName()));

            str.append(String.format("Successfully imported Car - %s %s @ %s.\n",claz.getBrand(),claz.getModel(),claz.getYearOfProduction()));

            this.carRepository.saveAndFlush(car);

        });
        return str.toString();




    }
}
