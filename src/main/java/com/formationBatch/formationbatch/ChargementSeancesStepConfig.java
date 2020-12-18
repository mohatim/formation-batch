package com.formationBatch.formationbatch;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import javax.sql.DataSource;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.io.Resource;

import com.formationBatch.formationbatch.entities.Seance;
import com.formationBatch.formationbatch.listeners.ChargementSeancesStepListener;
import com.formationBatch.formationbatch.mappers.SeanceItemPreparedStatementSetter;

@Configuration
public class ChargementSeancesStepConfig {
	
	@Bean
	@StepScope
	public FlatFileItemReader<Seance> seanceCsvItemReader(@Value("#{jobParameters['sceancesFile']}") Resource inputFile){
		return new FlatFileItemReaderBuilder<Seance>()
				.name("seanceCsvItemReader")
				.resource(inputFile)
				.delimited()
				.delimiter(";")
				.names(new String[] {"idFormateur", "codeFormation", "dateDebut", "dateFin"})
				.fieldSetMapper(seanceFieldSetMapper(null))
				.build();
	}
	
	@Bean
	@StepScope
	public FlatFileItemReader<Seance> seanceTxtItemReader(@Value("#{jobParameters['sceancesFile']}") Resource inputFile){
		return new FlatFileItemReaderBuilder<Seance>()
				.name("seanceTxtItemReader")
				.resource(inputFile)
				.fixedLength()
				.columns(new Range[] { new Range(1, 16), new Range(17, 20), new Range(25,
						32), new Range(37, 44) })
				.names(new String[] { "codeFormation", "idFormateur", "dateDebut",
				"dateFin" })
				.fieldSetMapper(seanceFieldSetMapper(null))
				.build();
	}
	
	@Bean
	public ConversionService myConversionService() {
		DefaultConversionService dcs = new DefaultConversionService();
		DefaultConversionService.addDefaultConverters(dcs);
		dcs.addConverter( new Converter<String, LocalDate>() {

			@Override
			public LocalDate convert(String source) {
				DateTimeFormatter df = DateTimeFormatter.ofPattern("ddMMyyyy");
				return LocalDate.parse(source, df);
			}
		}
				);
		return dcs;
	}
	
	@Bean
	public FieldSetMapper<Seance> seanceFieldSetMapper(final ConversionService myConversionService){
		BeanWrapperFieldSetMapper<Seance> bean = new BeanWrapperFieldSetMapper<>();
		bean.setTargetType(Seance.class);
		bean.setConversionService(myConversionService);
		return bean;
	}
	
	@Bean
	public ItemWriter<Seance> sceanceItemWriter(DataSource dataSource){
		return new JdbcBatchItemWriterBuilder<Seance>()
				.dataSource(dataSource)
				.sql(SeanceItemPreparedStatementSetter.SEANCES_INSERT_QUERY)
				.itemPreparedStatementSetter(new SeanceItemPreparedStatementSetter())
				.build();
	}
	
	@Bean
	public Step chargementSeancesCsvStep(StepBuilderFactory stepBuilderFactory) {
		return stepBuilderFactory
				.get("chargementSeancesCsvStep")
				.<Seance, Seance>chunk(10)
				.reader(seanceCsvItemReader(null))
				.writer(sceanceItemWriter(null))
				.listener(chargementSeancesListener())
				.build();
				
	}
	
	@Bean
	public Step chargementSeancesTxtStep(StepBuilderFactory stepBuilderFactory) {
		return stepBuilderFactory
				.get("chargementSeancesTxtStep")
				.<Seance, Seance>chunk(10)
				.reader(seanceTxtItemReader(null))
				.writer(sceanceItemWriter(null))
				.listener(chargementSeancesListener())
				.build();
				
	}
	
	@Bean
	public StepExecutionListener chargementSeancesListener() {
		return new ChargementSeancesStepListener();
	}
	
	

}
