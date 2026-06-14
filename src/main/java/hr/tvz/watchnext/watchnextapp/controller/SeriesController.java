package hr.tvz.watchnext.watchnextapp.controller;

import hr.tvz.watchnext.watchnextapp.command.SeriesCommand;
import hr.tvz.watchnext.watchnextapp.model.SeriesDTO;
import hr.tvz.watchnext.watchnextapp.service.SeriesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/series")
@CrossOrigin(origins = "http://localhost:5173/")
public class SeriesController {

    private final SeriesService seriesService;

    public SeriesController(SeriesService seriesService) {
        this.seriesService = seriesService;
    }

    @PostMapping
    public ResponseEntity<SeriesDTO> createSeries(@RequestBody SeriesCommand command) {
        return seriesService.save(command)
                .map(seriesDTO -> ResponseEntity.status(HttpStatus.CREATED).body(seriesDTO))
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteSeriesById(@PathVariable Long id) {
        seriesService.deleteSeries(id);
        return ResponseEntity.noContent().build();
    }
}