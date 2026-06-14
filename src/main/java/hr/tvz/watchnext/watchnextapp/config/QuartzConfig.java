package hr.tvz.watchnext.watchnextapp.config;

import hr.tvz.watchnext.watchnextapp.job.WatchReminderJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
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
}