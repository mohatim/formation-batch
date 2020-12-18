package com.formationBatch.formationbatch.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Formation {

	private String code;
	
	private String libelle;
	
	private String descriptif;

	public Formation() {
		super();
	}
	
	public Formation(String code, String libelle, String descriptif) {
		super();
		this.code = code;
		this.libelle = libelle;
		this.descriptif = descriptif;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getDescriptif() {
		return descriptif;
	}

	public void setDescriptif(String descriptif) {
		this.descriptif = descriptif;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Formation [code=");
		builder.append(code);
		builder.append(", libelle=");
		builder.append(libelle);
		builder.append(", descriptif=");
		builder.append(descriptif);
		builder.append("]");
		return builder.toString();
	}
	
	
}
