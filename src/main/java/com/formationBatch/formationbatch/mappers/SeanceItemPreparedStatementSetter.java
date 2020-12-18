package com.formationBatch.formationbatch.mappers;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import com.formationBatch.formationbatch.entities.Seance;

public class SeanceItemPreparedStatementSetter implements ItemPreparedStatementSetter<Seance>{

	public static final String SEANCES_INSERT_QUERY = "INSERT INTO Seances(code_formation,id_formateur,date_debut,date_fin) VALUES (?,?,?,?);";
	
	@Override
	public void setValues(Seance item, PreparedStatement ps) throws SQLException {
		ps.setString(1, item.getCodeFormation());
		ps.setInt(2, item.getIdFormateur());
		ps.setDate(3, Date.valueOf(item.getDateDebut()));
		ps.setDate(4, Date.valueOf(item.getDateFin()));
	}

}
