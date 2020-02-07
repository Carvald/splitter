package com.diatom.batch.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;

import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class BatchConfig {

	private static final Logger log = LoggerFactory.getLogger(BatchConfig.class);
	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean
	public FlatFileItemReader<SplitFile> reader() throws UnexpectedInputException, ParseException, Exception {

		FlatFileItemReader<SplitFile> reader = new FlatFileItemReader<SplitFile>();
		reader.setName("counntryItemReader");
		reader.setResource(new ClassPathResource("input/inputData"));
		reader.setLinesToSkip(0);

		// Configure how each line will be parsed and mapped to different values
		reader.setLineMapper(new DefaultLineMapper() {
			{
				// 3 columns in each row
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(new String[] { "name" });
					}
				});
				// Set values in Employee class
				setFieldSetMapper(new BeanWrapperFieldSetMapper<SplitFile>() {
					{
						setTargetType(SplitFile.class);
					}
				});
			}
		});

		

		return reader;
	}

	@Bean
	public CountryItemProcessor processor() {
		return new CountryItemProcessor();
	}

	@Bean
	public ConsoleItemWriter<SplitFile> writer() {
		return new ConsoleItemWriter<SplitFile>();
	}

	@Bean
	public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
		return jobBuilderFactory.get("importUserJob").incrementer(new RunIdIncrementer()).listener(listener).flow(step1)
				.end().build();
	}

	@Bean
	public Step step1(ConsoleItemWriter<SplitFile> writer) throws UnexpectedInputException, ParseException, Exception {
		return stepBuilderFactory.get("step1").<SplitFile, SplitFile>chunk(10).reader(reader())
				.writer(writer).build();
	}

}