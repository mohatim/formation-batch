package com.formationBatch.formationbatch.mappers;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import com.formationBatch.formationbatch.entities.Formateur;

public class FormateurItemPreparedStatementSetter implements ItemPreparedStatementSetter<Formateur>{

	public static final String FORMATEURS_INSERT_QUERY = "INSERT INTO formateurs(id,nom,prenom,adresse_email) VALUES (?,?,?,?);";
	@Override
	public void setValues(Formateur item, PreparedStatement ps) throws SQLException {
		ps.setInt(1, item.getId());
		ps.setString(2, item.getNom());
		ps.setString(3, item.getPrenom());
		ps.setString(4, item.getAdresseEmail());
	}

}
