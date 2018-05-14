package cz.moravec.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * For scheduling periodical downloading
 */
@Conditional(Conditions.ReadOnlyModeDisabled.class)
@Configuration
public class TaskSchedulerConfig {

    @Bean
    ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }
}
