package com.configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.model.entity.LoginInfo;

/*
 * Questa classe realizza il casting di loginInfo in un oggetto di tipo UserDetails.
 * Spring Security si interfaccia con l'utente usando l'oggetto che ne rappresenta le sue credenziali,
 * ovvero UserDetails. Tale oggetto e' arricchito di metodi per la gestione dell'utenza (con tanto
 * di lista di autorizzazioni(da approfondire)) 
 */
public class LoginInfoDetails implements UserDetails {

	private static final long serialVersionUID = -8773921465190832995L;
	private String email;
	private String password;
	private List<GrantedAuthority> authorities = new ArrayList<>();

	public LoginInfoDetails(LoginInfo loginInfo) {
		email = loginInfo.getEmail();
		password = loginInfo.getPassword();
		authorities.add(new SimpleGrantedAuthority(loginInfo.getRole()));
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// Questa booleana "banna" l'utente :)
	@Override
	public boolean isEnabled() {
		return true;
	}

	/**
	 * Questo restituisce l'email anziche' lo username(perche' spring Security)
	 */
	@Override
	public String getUsername() {
		return email;
	}

	public String getEmail() {
		return email;
	}

}
