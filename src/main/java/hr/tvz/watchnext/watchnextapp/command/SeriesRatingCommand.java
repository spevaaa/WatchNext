package hr.tvz.watchnext.watchnextapp.command;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SeriesRatingCommand {

    @NotBlank(message = "Naslov je obavezan")
    private String title;

    @DecimalMin(value = "1.0", message = "Minimalna ocjena je 1.0")
    @DecimalMax(value = "10.0", message = "Maksimalna ocjena je 10.0")
    private double imdbRating;
}
