package com.formationBatch.formationbatch.dao;

import java.util.List;

import com.formationBatch.formationbatch.entities.PlanningItem;

public interface SeanceDao{

	List<PlanningItem> getByFormateurId(Integer formateurId);
}
