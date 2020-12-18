package com.formationBatch.formationbatch.writers;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

import com.formationBatch.formationbatch.entities.Planning;
import com.formationBatch.formationbatch.services.MailContentGenerator;
import com.formationBatch.formationbatch.services.PlanningMailSenderService;

public class PlanningItemWriter implements ItemWriter<Planning>{

	private PlanningMailSenderService planningService;
	
	private MailContentGenerator mailContentGenerator;
	
	
	
	public PlanningItemWriter(PlanningMailSenderService planningService, MailContentGenerator mailContentGenerator) {
		super();
		this.planningService = planningService;
		this.mailContentGenerator = mailContentGenerator;
	}


	@Override
	public void write(List<? extends Planning> plannings) throws Exception {
		for (Planning planning : plannings) {
			String content = mailContentGenerator.generate(planning);
			planningService.send(planning.getFormateur().getAdresseEmail(), content);
		}
	}

}
