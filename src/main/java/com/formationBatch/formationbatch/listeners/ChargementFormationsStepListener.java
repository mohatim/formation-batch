package com.formationBatch.formationbatch.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepListenerSupport;

import com.formationBatch.formationbatch.entities.Formation;

public class ChargementFormationsStepListener extends StepListenerSupport<Formation, Formation>{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ChargementFormationsStepListener.class);

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		LOGGER.info("Chargement des formations :{} formation(s) enregistré(s) ", stepExecution.getWriteCount());
		return stepExecution.getExitStatus();
	}
}
