package com.formationBatch.formationbatch.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.formationBatch.formationbatch.entities.PlanningItem;
import com.formationBatch.formationbatch.mappers.PlanningItemRowMapper;

public class SeanceDaoImpl implements SeanceDao{

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	private static final String QUERY = "select f.libelle, s.date_debut,s.date_fin"
			+ " from formations f join seances s on f.code=s.code_formation"
			+ " where s.id_formateur=:formateur"
			+ " order by s.date_debut ";

	@Override
	public List<PlanningItem> getByFormateurId(Integer formateurId) {
		HashMap<String, Object> params = new HashMap<>();
		params.put("formateur", formateurId);
		return jdbcTemplate.query(QUERY, params, new PlanningItemRowMapper());
	}
}
