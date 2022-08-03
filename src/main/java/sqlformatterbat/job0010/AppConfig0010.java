package sqlformatterbat.job0010;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import sqlformatterbat.common.listener.JobExecutionLoggingListener;
import sqlformatterbat.common.listener.StepExecutionLoggingListener;
import sqlformatterbat.common.validator.JobParametersSampleValidator;

@Configuration
@EnableBatchProcessing
@ComponentScans({@ComponentScan("sqlformatterbat.common"), @ComponentScan("sqlformatterbat.job0010")})
public class AppConfig0010 {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private SqlFormatterBatTasklet sqlFormatterBatTasklet;
    @Autowired
    private JobExecutionLoggingListener jobExecutionLoggingListener;
    @Autowired
    private StepExecutionLoggingListener stepExecutionLoggingListener;
    @Autowired
    private JobParametersSampleValidator jobParametersSampleValidator;

    @Bean
    public Job sqlFormatterBatJob(Step sqlFormatterBatStep) {
        return jobBuilderFactory.get("sqlFormatterBatStepJob").validator(jobParametersSampleValidator)
                .start(sqlFormatterBatStep).listener(jobExecutionLoggingListener).build();
    }

    @Bean
    public Step sqlFormatterBatStep() {
        return stepBuilderFactory.get("sqlFormatterBatStep").tasklet(sqlFormatterBatTasklet)
                .listener(stepExecutionLoggingListener).build();
    }
}
