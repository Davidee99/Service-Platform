package com.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.configuration.LoginInfoDetails;
import com.model.entity.LoginInfo;
import com.repository.LoginInfoRepository;

/*
 * Quando scegliamo di autenticare l'utente recuperando le sue credenziali salvate sul DB,
 * usiamo questa classe per wrappare quanto recuperato in un oggetto di tipo UserDetails
 * che contiene metodi per la gestione dell'utenza.
 * 
 * Questo wrapping avviene avvalendosi di un'ulteriore classe (che implementa UserDetails)
 * che abbiamo chiamato UserInfoDetails
 * 
 */

@Component
public class LoginInfoDetailsService implements UserDetailsService {

	@Autowired
	private LoginInfoRepository repository;

	// Metodo utilizzato da security per trovare il loginInfo tramite la decodifica del token
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LoginInfo loginInfo = repository.findByEmail(username); // email
		return new LoginInfoDetails(loginInfo);

	}

}
