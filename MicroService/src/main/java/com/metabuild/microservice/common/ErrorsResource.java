package com.metabuild.microservice.common;

import org.springframework.hateoas.EntityModel;
import org.springframework.validation.Errors;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class ErrorsResource extends EntityModel<Errors> {

	@JsonUnwrapped
	private Errors errors;

	public ErrorsResource(Errors errors) {
		this.errors = errors;
	}
}
