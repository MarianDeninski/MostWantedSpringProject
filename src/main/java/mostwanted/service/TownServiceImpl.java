package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.domain.dtos.json.TownDto;
import mostwanted.domain.entities.Town;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class TownServiceImpl implements TownService {

    private final static String IMPORT_TOWNS = "src/main/resources/files/towns.json";
    private final TownRepository townRepository;


    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final FileUtil fileUtil;
    private final Gson gson;

    @Autowired
    public TownServiceImpl(ModelMapper modelMapper, TownRepository townRepository,
                           ValidationUtil validationUtil, FileUtil fileUtil, Gson gson) {
        this.modelMapper = modelMapper;
        this.townRepository = townRepository;
        this.validationUtil = validationUtil;
        this.fileUtil = fileUtil;
        this.gson = gson;
    }


    @Override
    public Boolean townsAreImported() {
        return this.townRepository.count() != 0;
    }

    @Override
    public String readTownsJsonFile() throws IOException {
        return this.fileUtil.readFile(IMPORT_TOWNS);
    }

    @Override
    public String importTowns(String townsFileContent) {

        StringBuilder sb = new StringBuilder();
        TownDto[] townDto = this.gson.fromJson(townsFileContent,TownDto[].class);

        Arrays.stream(townDto).forEach(e->{

            Town town = this.townRepository.findAllByName(e.getName());
            if(town!=null){
                sb.append("Error: Duplicate Data!").append(System.lineSeparator());

                return;
            }
            if(!this.validationUtil.isValid(e)){
                sb.append("Error: Incorrect Data!").append(System.lineSeparator());

                return;
            }
            Town town1 = this.modelMapper.map(e,Town.class);
            sb.append(String.format("Successfully imported %s – %s.\n",town1.getClass().getSimpleName(),town1.getName()));
            this.townRepository.saveAndFlush(town1);

        });

        return sb.toString();
    }

    @Override
    public String exportRacingTowns() {


        List<Town> towns = this.townRepository.getTownsOrderBy();

        StringBuilder sb = new StringBuilder();
        for (Town town : towns) {

            sb.append(String.format("Name: %s\nRacers: %s",town.getName(),town.getRacers().size()));
            sb.append(System.lineSeparator());

        }


        return sb.toString().trim();
    }
}
