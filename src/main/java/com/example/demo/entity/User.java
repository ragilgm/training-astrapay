package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;
import javax.validation.constraints.Size;

@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 5534441879591858724L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NotBlank(message = "name is required")
	@Size(min = 5, max = 30)
	private String username;

	@Size(min = 5, max = 50)
	@NotBlank(message = "email is required")
	@Pattern(regexp = "^(?=.*[@])(?=\\S+$).{5,20}$", flags = Flag.UNICODE_CASE)
	private String email;

//	   ^                 # start-of-string
//	   (?=.*[0-9])       # a digit must occur at least once
//	   (?=.*[a-z])       # a lower case letter must occur at least once
//	   (?=.*[A-Z])       # an upper case letter must occur at least once
//	   (?=.*[@#$%^&+=])  # a special character must occur at least once
//	   (?=\S+$)          # no whitespace allowed in the entire string
//	   .{8,}             # anything, at least eight places though
//	   $                 # end-of-string

	@NotBlank(message = "password is required")
//	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*])(?=\\S+$).{8,20}$", flags = Flag.UNICODE_CASE)
	private String password;
	private boolean deletable = true;
	private String created_at;
	private String updated_at;

	public User(@NotBlank(message = "name is required") @Size(min = 5, max = 30) String username,
			@Size(min = 5, max = 50) @NotBlank(message = "email is required") String email,
			@NotBlank(message = "password is required") @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*])(?=\\S+$).{8,20}$", flags = Flag.UNICODE_CASE) String password,
			boolean deletable, String created_at, String updated_at) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.deletable = deletable;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

	public User() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isDeletable() {
		return deletable;
	}

	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
