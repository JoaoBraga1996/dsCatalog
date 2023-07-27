package com.devsuperior.dscatalog.dto;

import com.devsuperior.dscatalog.servicies.validation.UserInsertValid;

@UserInsertValid
public class UserInsertDTO extends UserDTO {
	private static final long serialVersionUID = 1L;

	private String passaword;

	UserInsertDTO() {
		super();
	}

	public String getPassaword() {
		return passaword;
	}

	public void setPassaword(String passaword) {
		this.passaword = passaword;
	}

}
