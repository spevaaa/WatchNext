package hr.tvz.watchnext.watchnextapp.controller;

import hr.tvz.watchnext.watchnextapp.command.SeriesCommand;
import hr.tvz.watchnext.watchnextapp.enumeration.SeriesStatus;
import hr.tvz.watchnext.watchnextapp.model.Series;
import hr.tvz.watchnext.watchnextapp.model.SeriesDTO;
import hr.tvz.watchnext.watchnextapp.service.SeriesService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/series")
@CrossOrigin(origins = "http://localhost:5173/")
public class SeriesController {

    private final SeriesService seriesService;

    public SeriesController(SeriesService seriesService) {
        this.seriesService = seriesService;
    }

    @GetMapping
    public List<SeriesDTO> getAll() {
        return seriesService.getAllSeries();
    }

    @GetMapping("/id/{id}")
    public SeriesDTO getById(@PathVariable Long id) {
        return seriesService.getSeriesById(id);
    }

    @GetMapping("/{title}")
    public ResponseEntity<SeriesDTO> getByTitle(@PathVariable String title) {
        return seriesService.findByTitle(title)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(params = "imdbId")
    public ResponseEntity<SeriesDTO> getByImdbId(@RequestParam String imdbId) {
        return seriesService.findByImdbId(imdbId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        seriesService.deleteSeries(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/title/{title}")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<Void> delete(@PathVariable String title) {
        boolean deleted = seriesService.delete(title);

        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping
    public ResponseEntity<SeriesDTO> save(@Valid @RequestBody SeriesCommand command) {
        return seriesService.save(command)
                .map(dto -> ResponseEntity.status(HttpStatus.CREATED).body(dto))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PutMapping("/title/{title}")
    public ResponseEntity<SeriesDTO> update(@PathVariable String title, @RequestBody Series updatedSeries) {
        return seriesService.update(title, updatedSeries)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id, @RequestParam SeriesStatus status) {
        seriesService.updateStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/list")
    public ResponseEntity<Void> listInsert(@RequestBody List<Series> seriesList) {
        seriesService.listInsert(seriesList);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/status/{status}")
    public ResponseEntity<Void> deleteByStatus(@PathVariable SeriesStatus status) {
        seriesService.deleteByStatus(status);
        return ResponseEntity.noContent().build();
    }
}