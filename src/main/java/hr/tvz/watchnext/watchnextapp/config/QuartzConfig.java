package hr.tvz.watchnext.watchnextapp.config;

import hr.tvz.watchnext.watchnextapp.job.RecentSeriesJob;
import hr.tvz.watchnext.watchnextapp.job.WatchReminderJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    /*@Bean
    public JobDetail watchReminderJobDetail() {
        return JobBuilder.newJob(WatchReminderJob.class)
                .withIdentity("watchReminderJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger watchReminderJobTrigger() {
        String cronExpression = "0 0 8 ? * MON";

        return TriggerBuilder.newTrigger()
                .forJob(watchReminderJobDetail())
                .withIdentity("watchReminderTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();
    }

    @Bean
    public Trigger watchReminderJobTriggerWithJobDetail(JobDetail jobDetail) {
        String testCronExpression = "0/30 * * * * ?";

        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity("watchremindertrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule(testCronExpression))
                .build();
    }
*/




    @Bean
    public JobDetail recentSeriesJobDetail() {
        return JobBuilder.newJob(RecentSeriesJob.class)
                .withIdentity("recentSeriesJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger recentSeriesJobTrigger(JobDetail recentSeriesJobDetail) {
        String customCron = "0/10 * 15-21 ? * MON-FRI 2026";

        return TriggerBuilder.newTrigger()
                .forJob(recentSeriesJobDetail)
                .withIdentity("recentSeriesTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule(customCron))
                .build();
    }
}