package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.domain.dtos.json.DistrictDto;
import mostwanted.domain.entities.District;
import mostwanted.repository.DistrictRepository;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class DistrictServiceImpl implements DistrictService {

    private final static String IMPORT_DISTRICT = "src/main/resources/files/districts.json";
    private final DistrictRepository districtRepository;
    private final TownRepository townRepository;

    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final FileUtil fileUtil;
    private final Gson gson;

    public DistrictServiceImpl(DistrictRepository districtRepository, TownRepository townRepository, ModelMapper modelMapper,
                               ValidationUtil validationUtil, FileUtil fileUtil, Gson gson) {
        this.districtRepository = districtRepository;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.fileUtil = fileUtil;
        this.gson = gson;
    }

    @Override
    public Boolean districtsAreImported() {
        return this.districtRepository.count()>0;
    }

    @Override
    public String readDistrictsJsonFile() throws IOException {

        return this.fileUtil.readFile(IMPORT_DISTRICT);
    }

    @Override
    public String importDistricts(String districtsFileContent) {

        StringBuilder str = new StringBuilder();

        DistrictDto[] districtDto = this.gson.fromJson(districtsFileContent, DistrictDto[].class);

        Arrays.stream(districtDto).forEach(claz->{

            District checker = this.districtRepository.findAllByName(claz.getName());

            if(checker!=null){

                str.append("Error: Duplicate Data!").append(System.lineSeparator());

                return;

            }
            if(!this.validationUtil.isValid(claz)){

                str.append("Error: Incorrect Data!").append(System.lineSeparator());

                return;
            }
            District district = this.modelMapper.map(claz,District.class);
            district.setTown(this.townRepository.findAllByName(claz.getTownName()));

            str.append(String.format("Successfully imported %s - %s.\n",claz.getName(),claz.getTownName()));

            this.districtRepository.saveAndFlush(district);

        });
        return str.toString();
    }
}
