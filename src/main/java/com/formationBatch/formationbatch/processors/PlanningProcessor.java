package com.formationBatch.formationbatch.processors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.formationBatch.formationbatch.entities.Planning;
import com.formationBatch.formationbatch.entities.PlanningItem;
import com.formationBatch.formationbatch.mappers.PlanningItemRowMapper;

public class PlanningProcessor implements ItemProcessor<Planning, Planning>{

	
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	private static final String QUERY = "select f.libelle, s.date_debut,s.date_fin"
			+ " from formations f join seances s on f.code=s.code_formation"
			+ " where s.id_formateur=:formateur"
			+ " order by s.date_debut ";

	


	public PlanningProcessor(NamedParameterJdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Planning process(Planning planning) throws Exception {
		
		Map<String, Object> params = new HashMap<>();
		params.put("formateur", planning.getFormateur().getId());
		 List<PlanningItem> plannins = jdbcTemplate.query(QUERY, params, new PlanningItemRowMapper());
		planning.setSeances(plannins);
		return planning;
	}
	
}
