package hr.tvz.watchnext.watchnextapp.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class SeriesBasicCommand {

    @NotBlank(message = "Naslov je obavezan")
    private String title;

    @NotBlank(message = "Žanr je obavezan")
    private String genre;

    @Positive(message = "Broj sezona mora biti pozitivan")
    private int totalSeasons;
}
