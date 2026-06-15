package hr.tvz.watchnext.watchnextapp.job;

import hr.tvz.watchnext.watchnextapp.config.ApplicationContextProvider;
import hr.tvz.watchnext.watchnextapp.model.Series;
import hr.tvz.watchnext.watchnextapp.repository.SeriesRepository;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class RecentSeriesJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(RecentSeriesJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        SeriesRepository seriesRepository = ApplicationContextProvider.getBean(SeriesRepository.class);

        try {
            List<Series> lastFive = seriesRepository.findByOrderByIdDesc(PageRequest.of(0, 5));

            log.info("[ZADNJIH 5 ZAPISA IZ TABLICE SERIES]:");

            if (lastFive.isEmpty()) {
                log.info("Tablica je trenutno prazna.");
            } else {
                for (Series s : lastFive) {
                    log.info("- ID: {}, Naslov: {}", s.getId(), s.getTitle());
                }
            }

        } catch (Exception e) {
            log.error("Greška prilikom čitanja zadnjih zapisa u RecentSeriesJob-u", e);
            throw new JobExecutionException(e);
        }
    }
}