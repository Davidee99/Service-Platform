package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.model.dto.AuthRequest;
import com.model.dto.LoginResponse;
import com.model.entity.Employee;
import com.model.entity.LoginInfo;
import com.model.entity.User;
import com.repository.LoginInfoRepository;
import com.service.security.JwtService;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	LoginInfoRepository loginInfoRep;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtService jwtservice;

	/**
	 * Metodo per fare il login tramite email e password che restituisce un response
	 * con all'interno:
	 * 
	 * firstName lastName role token
	 * 
	 */
	@Override
	public ResponseEntity<?> login(AuthRequest authRequest, String loginRole) {

		LoginResponse response = new LoginResponse();

		Authentication authentication = null;

		if (authRequest.getPassword() == null || authRequest.getEmail() == null) {
			return new ResponseEntity<String>("Password o Email null", HttpStatus.BAD_REQUEST); // 400
		}

		try {

			// Auetenticazione dell utente
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

			if (authentication.isAuthenticated()) {

				String email = authRequest.getEmail();

				LoginInfo loginInfo = loginInfoRep.findByEmail(email);

				// Caso in cui un employee cerchi di fare il login come user
				if (loginRole == "USER" && !loginInfo.getRole().equals(loginRole)) {
					return new ResponseEntity<String>("Employee cerca di fare il login come user",
							HttpStatus.BAD_REQUEST); // 400
				}

				// Caso in cui un user cerchi di fare il login come employee
				if (loginRole != "USER" && loginInfo.getRole().equals("USER")) {
					return new ResponseEntity<String>("User cerca di fare il login come employee",
							HttpStatus.BAD_REQUEST); // 400
				}

				if (loginInfo == null) {
					return new ResponseEntity<String>("loginInfo non trovata", HttpStatus.BAD_REQUEST); // 400
				}

				// Setting della respnse in base al tipo di ruolo

				// Se l'employee di loginInfo è null, vuol dire che si tratta di uno user
				if (loginInfo.getEmployee() == null) {
					User user = loginInfo.getUser();
					if (user == null) {
						return new ResponseEntity<String>("user not found", HttpStatus.BAD_REQUEST); // 400
					}
					response.setId(user.getId());
					response.setFirstName(user.getFirstname());
					response.setLastName(user.getLastname());
					response.setRole(loginInfo.getRole());
					// Settiamo il Token tramite il metodo generateToken che utilizza l'email di
					// loginInfo
					response.setToken(jwtservice.generateToken(loginInfo.getEmail()));
				} else {
					Employee employee = loginInfo.getEmployee();
					if (employee == null) {
						return new ResponseEntity<String>("employee not found", HttpStatus.BAD_REQUEST); // 400
					}
					response.setId(employee.getId());
					response.setFirstName(employee.getFirstname());
					response.setLastName(employee.getLastname());
					response.setRole(loginInfo.getRole());
					// Settiamo il Token tramite il metodo generateToken che utilizza l'email di
					// loginInfo
					response.setToken(jwtservice.generateToken(loginInfo.getEmail()));
				}

				return new ResponseEntity<LoginResponse>(response, HttpStatus.OK); // 200
			}

		} catch (DisabledException de) {
			System.err.println("LoginService :" + de.getMessage());
			return new ResponseEntity<LoginResponse>(response, HttpStatus.UNAUTHORIZED); // 401
		} catch (LockedException le) {
			System.err.println("LoginService :" + le.getMessage());
			return new ResponseEntity<LoginResponse>(response, HttpStatus.UNAUTHORIZED); // 401
		} catch (BadCredentialsException bce) {
			System.err.println("LoginService :" + bce.getMessage());
			return new ResponseEntity<LoginResponse>(response, HttpStatus.UNAUTHORIZED); // 401
		} catch (AuthenticationException ex) {
			System.err.println("LoginService :" + ex.getMessage());
			return new ResponseEntity<String>("Email o Password errate", HttpStatus.UNAUTHORIZED); // 401
		}
		return new ResponseEntity<String>("Prblema interno Server!!", HttpStatus.INTERNAL_SERVER_ERROR); // 500
	}

}
