package hr.tvz.watchnext.watchnextapp.command;

import hr.tvz.watchnext.watchnextapp.enumeration.SeriesStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeriesCommand {

    @NotBlank(message = "Naslov ne smije biti prazan")
    private String title;

    @NotBlank(message = "Žanr ne smije biti prazan")
    private String genre;

    @Positive(message = "Broj sezona mora biti pozitivan broj")
    private Integer totalSeasons;

    @NotNull(message = "Status mora biti odabran")
    private SeriesStatus status;

    @DecimalMin(value = "0.0", message = "IMDb ocjena ne može biti manja od 0.0")
    @DecimalMax(value = "10.0", message = "IMDb ocjena ne može biti veća od 10.0")
    private Double imdbRating;

    @Pattern(regexp = "^S[0-9]{3}$", message = "Šifra mora početi slovom S i sadržavati 3 znamenke (npr. S001)")
    private String internalCode;

    @PositiveOrZero(message = "Broj nagrada ne može biti negativan")
    private Integer awardCount;
}