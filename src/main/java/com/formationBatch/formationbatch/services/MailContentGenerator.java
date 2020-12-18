package com.formationBatch.formationbatch.services;

import java.io.IOException;

import com.formationBatch.formationbatch.entities.Planning;

import freemarker.template.TemplateException;

public interface MailContentGenerator {

	String generate(Planning planning) throws TemplateException, IOException;
}
