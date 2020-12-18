package com.formationBatch.formationbatch.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.formationBatch.formationbatch.entities.PlanningItem;

public class PlanningItemRowMapper implements RowMapper<PlanningItem>{

	@Override
	public PlanningItem mapRow(ResultSet rs, int rowNum) throws SQLException {
		PlanningItem item = new PlanningItem();
		item.setLibelleFormation(rs.getString(1));
		item.setDateDebutSeance(rs.getDate(2).toLocalDate());
		item.setDateFinSeance(rs.getDate(3).toLocalDate());
		return item;
	}

}
