package hr.tvz.watchnext.watchnextapp.command;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SeriesActorCommand {

    @NotNull(message = "ID serije je obavezan")
    private Long seriesId;

    @NotBlank(message = "Ime glumca ne smije biti prazno")
    @Size(min = 2, max = 50, message = "Ime mora imati između 2 i 50 znakova")
    private String name;

    @Email(message = "Email mora biti u ispravnom formatu")
    private String contactEmail;
}
