package hr.tvz.watchnext.watchnextapp.job;

import hr.tvz.watchnext.watchnextapp.config.ApplicationContextProvider;
import hr.tvz.watchnext.watchnextapp.model.SeriesDTO;
import hr.tvz.watchnext.watchnextapp.service.SeriesService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class WatchReminderJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(WatchReminderJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("Quartz Job: WatchReminderJob se pokreće...");

        SeriesService seriesService = ApplicationContextProvider.getBean(SeriesService.class);

        try {
            List<SeriesDTO> allSeries = seriesService.getAllSeries();

            int seriesSize = allSeries.size();

            log.info("[PODSJETNIK] Trenutno imas {} serija na svojoj listi!", seriesSize);

            if (seriesSize > 0) {
                log.info("Ne zaboravi baciti oko na neku od njih danas!");
            }

        } catch (Exception e) {
            log.error("Greška prilikom izvršavanja WatchReminderJob-a", e);
            throw new JobExecutionException(e);
        }
    }
}