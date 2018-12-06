package mostwanted.domain.dtos.json;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;


public class TownDto {

    @Expose
    private String name;


    @NotNull(message = "Error: Incorrect Data!")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
