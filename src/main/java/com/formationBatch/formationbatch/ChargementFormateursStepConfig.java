package com.formationBatch.formationbatch;

import javax.sql.DataSource;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.formationBatch.formationbatch.entities.Formateur;
import com.formationBatch.formationbatch.listeners.ChargementFormateursStepListener;
import com.formationBatch.formationbatch.mappers.FormateurItemPreparedStatementSetter;

@Configuration
public class ChargementFormateursStepConfig {

	@Bean
	@StepScope
	public FlatFileItemReader<Formateur> formateurItemReader(@Value("#{jobParameters['formateursFile']}") Resource inputFile){
		return new FlatFileItemReaderBuilder<Formateur>()
				.name("FormateurItemReader")
				.resource(inputFile)
				.delimited()
				.delimiter(";")
				.names(new String[] {"id", "nom", "prenom", "adresseEmail"})
				.targetType(Formateur.class)
				.build();
	}
	
	@Bean
	public JdbcBatchItemWriter<Formateur> formateurItemWriter(DataSource dataSource){
		return new JdbcBatchItemWriterBuilder<Formateur>()
				.dataSource(dataSource)
				.sql(FormateurItemPreparedStatementSetter.FORMATEURS_INSERT_QUERY)
				.itemPreparedStatementSetter(new FormateurItemPreparedStatementSetter())
				.build();
	}
	
	@Bean
	public Step chargementFormateursStep(StepBuilderFactory stepBuilderFactory) {
		return stepBuilderFactory
				.get("chargementFormateurStep")
				.<Formateur,Formateur> chunk(10)
				.reader(formateurItemReader(null))
				.writer(formateurItemWriter(null))
				.listener(chargementFormateursListener())
				.build();
	}
	
	@Bean
	public StepExecutionListener chargementFormateursListener() {
		return new ChargementFormateursStepListener();
	}
}
