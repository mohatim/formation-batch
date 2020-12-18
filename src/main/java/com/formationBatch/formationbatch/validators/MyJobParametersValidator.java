package com.formationBatch.formationbatch.validators;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.util.StringUtils;

public class MyJobParametersValidator implements JobParametersValidator {

	@Override
	public void validate(JobParameters parameters) throws JobParametersInvalidException {
		if(!StringUtils.endsWithIgnoreCase(parameters.getString("formationsFile"), "xml")) {
			throw new JobParametersInvalidException("le fichier des formations doit être au format XML");
		}
		
		if(!StringUtils.endsWithIgnoreCase(parameters.getString("formateursFile"), "csv")) {
			throw new JobParametersInvalidException("le fichier des formateurs doit être au format CSV");
		}
		
		if(!StringUtils.endsWithIgnoreCase(parameters.getString("sceancesFile"), "txt") && !StringUtils.endsWithIgnoreCase(parameters.getString("sceanceFile"), "csv")) {
			throw new JobParametersInvalidException("le fichier des séances doit être au format CSV ou TXT");
		}
		
	}

}
