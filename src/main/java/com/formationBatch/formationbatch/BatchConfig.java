package com.formationBatch.formationbatch;

import java.util.Arrays;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.CompositeJobParametersValidator;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.formationBatch.formationbatch.deciders.SeancesStepDecider;
import com.formationBatch.formationbatch.validators.MyJobParametersValidator;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Bean
	public JobParametersValidator defaultJobParametersValidator() {
		DefaultJobParametersValidator bean = new DefaultJobParametersValidator();
		bean.setRequiredKeys(new String[] {"formationsFile","formateursFile","sceancesFile"});
		bean.setOptionalKeys(new String[] {"run.id"});
		return bean;
	}
	
//	@Bean
//	public Step step1(StepBuilderFactory stepBuilderFactory) {
//		return stepBuilderFactory
//				.get("step1")
//				.tasklet(new Tasklet() {
//					
//					@Override
//					public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//						System.out.println("Hello world");
//						return RepeatStatus.FINISHED;
//					}
//				}).build();
//	}
	
	@Bean
	public JobParametersValidator compositeJobParametersValidator() {
		CompositeJobParametersValidator bean = new CompositeJobParametersValidator();
		bean.setValidators(Arrays.asList(defaultJobParametersValidator(), myJobParametersValidator()));
		return bean;
	}
	
	@Bean
	public JobParametersValidator myJobParametersValidator() {
		return new MyJobParametersValidator();
	}
	
	@Bean
	public JobExecutionDecider seancesStepDecider() {
		return new SeancesStepDecider();
	}
	
	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory,  Step chargementFormateursStep, Step chargementFormationsStep, Step chargementSeancesCsvStep, Step chargementSeancesTxtStep, Step planningStep) {
		return jobBuilderFactory.get("planning-formations")
				.start(chargementFormateursStep)
				.next(chargementFormationsStep)
				.next(seancesStepDecider())
				.from(seancesStepDecider()).on("txt").to(chargementSeancesTxtStep)
				.from(seancesStepDecider()).on("csv").to(chargementSeancesCsvStep)
				.from(chargementSeancesTxtStep).on("*").to(planningStep)
				.from(chargementSeancesCsvStep).on("*").to(planningStep)
				.end()
				.validator(myJobParametersValidator())
				.incrementer(new RunIdIncrementer())
				.build();
				
	}
}
