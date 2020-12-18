package com.formationBatch.formationbatch.entities;

import java.time.LocalDate;

public class Seance {

	private Integer idFormateur;
	
	private String codeFormation;
	
	private LocalDate dateDebut;
	
	private LocalDate dateFin;

	public Seance() {
		super();
	}
	
	public Seance(Integer idFormateur, String codeFormation, LocalDate dateDebut, LocalDate dateFin) {
		super();
		this.idFormateur = idFormateur;
		this.codeFormation = codeFormation;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
	}

	public Integer getIdFormateur() {
		return idFormateur;
	}

	public void setIdFormateur(Integer idFormateur) {
		this.idFormateur = idFormateur;
	}

	public String getCodeFormation() {
		return codeFormation;
	}

	public void setCodeFormation(String codeFormation) {
		this.codeFormation = codeFormation;
	}

	public LocalDate getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}

	public LocalDate getDateFin() {
		return dateFin;
	}

	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}

	@Override
	public String toString() {
		return "Seance [idFormateur=" + idFormateur + ", codeFormation=" + codeFormation + ", dateDebut=" + dateDebut
				+ ", dateFin=" + dateFin + "]";
	}
	
	
	
}
