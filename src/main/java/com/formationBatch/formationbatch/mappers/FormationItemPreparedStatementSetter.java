package com.formationBatch.formationbatch.mappers;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import com.formationBatch.formationbatch.entities.Formation;

public class FormationItemPreparedStatementSetter implements ItemPreparedStatementSetter<Formation>{
	
	public static final String FORMATIONS_INSERT_QUERY = "INSERT INTO formations(code,libelle,descriptif) VALUES (?,?,?);";

	@Override
	public void setValues(Formation item, PreparedStatement ps) throws SQLException {
		ps.setString(1, item.getCode());
		ps.setString(2, item.getLibelle());
		ps.setString(3, item.getDescriptif());
		
	}
}
