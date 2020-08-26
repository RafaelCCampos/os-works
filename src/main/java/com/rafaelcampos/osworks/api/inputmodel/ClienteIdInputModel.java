package com.rafaelcampos.osworks.api.inputmodel;

import javax.validation.constraints.NotNull;

public class ClienteIdInputModel {
	
	@NotNull
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
