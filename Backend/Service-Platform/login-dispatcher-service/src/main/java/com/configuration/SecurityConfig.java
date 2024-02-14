package com.configuration;

//import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.filter.JwtAuthFilter;
import com.service.security.LoginInfoDetailsService;

/*
 * Classe di configurazione di security.
 */

@Configuration
@EnableWebSecurity // Definisce un componente di spring per la gestione della security
public class SecurityConfig {

	/*
	 * Dependency Injection del nostro filtro custom Il filtro controlla che la
	 * request abbia il token e che sia valido e si preoccupa di verificare che
	 * questa request non sia già stata elaborata da altri filtri
	 */
	@Autowired
	private JwtAuthFilter authFilter;

	@Autowired
	private CustomAuthenticationEntryPoint customAuthEntryPoint;

	/*
	 * Quando facciamo l'autenticazione dell'utente tramite il recupero delle sue
	 * credenziali dal DB, allora abbiamo bisogno di definire due beans:
	 * UserDetailService e PasswordEncoder
	 */

	/*
	 * Recupera le credenziali dal DB e le wrappa in un oggetto di tipo UserDetails
	 * arricchito di metodi per la gestione dell'utenza
	 * 
	 * Per capire il funzionamento andare a vedere la classe LoginInfoDetailsService
	 * 
	 */
	@Bean
	UserDetailsService userDetailsService() {
		return new LoginInfoDetailsService();
	}

	/*
	 * Codifica la password dell'utente sul DB
	 */
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/*
	 * In securityFilterChain configuriamo la catena di filtri attraverso la quale
	 * passera' la request del client
	 */
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		/*
		 * DEFINIZIO permitAll():
		 * 
		 * auth.requestMatchers(endpoint1, endpoint2, endpoint3, ...).permitAll();
		 * consente a tutti, anche a client non autenticati, di usare i servizi
		 * associati agli endpoint
		 * 
		 * DEFINIZIONE E UTILIZZO DEI RUOLI:
		 * 
		 * possiamo definire inoltre dei roles e delle authorities per raffinare
		 * l'accesso agli endpoint. Nella classe UserInfo abbiamo definito un attributo
		 * chiamato "roles" che definisce una lista di roles e authorities. Un ruolo e'
		 * normalmente descritto dalla stringa "ROLE_nomeRuolo", ovvero contiene il
		 * prefisso "ROLE_". Un'authority non ha tale prefisso, sara' quindi descritta
		 * dalla stringa "nomeRuolo". Nel filtro, usiamo ad esempio:
		 * .........auth.requestMatchers("/api/v1/isRunning").hasRole("NOMERUOLO")
		 * oppure
		 * ................auth.requestMatchers("/api/v1/isRunning").hasAuthority(
		 * "NOMERUOLO") e la differenza e' che l'hasRole abilita l'uso dell'endpoint
		 * solo agli utenti che hanno "ROLE_NOME_RUOLO" nell'attributo "roles", mentre
		 * l'hasAuthority solo quelli che nell'attributo hanno "NOME_RUOLO".
		 * 
		 * DEFINIZIONE authenticated():
		 * 
		 * auth.anyRequest().authenticated(); significa che tutti gli altri endpoint
		 * sono usabili solo da client autenticati.
		 * 
		 * 
		 * Poi c'e' l'authenticationManager OPPURE (OR ESCLUSIVO) authenticationProvider
		 * qui decidiamo come gestire l'autenticazione. Se abbiamo piu' di un modo per
		 * farlo, conviene usare l'authenticationManager (che possiamo usare anche se
		 * abbiamo un solo modo). Vengono qui usati i bean userDetailService e
		 * passwordEncoder
		 * 
		 * 
		 */

		return http.authorizeHttpRequests(auth -> {
			auth.requestMatchers("/api/authentication/login/*").permitAll();
			auth.requestMatchers("/api/dispacher/test").hasAuthority("ADMIN");
			auth.requestMatchers("/api/dispacher/test").hasAuthority("OPERATOR");
			auth.requestMatchers("/api/dispacher/mail-service/*").hasAuthority("ADMIN");
			auth.requestMatchers("/api/dispacher/mail-service/*").hasAuthority("OPERATOR");
			auth.anyRequest().authenticated();
		})

				.csrf(csrf -> csrf.disable()).cors(withDefaults())
				/*
				 * Viene disabilitata la configurazione per gli attacchi CSRF (Cross Site
				 * Request Forgery): e' quasi superflua nel caso in cui il nostro backend
				 * implementi Spring Security con JWT
				 * (https://www.baeldung.com/csrf-stateless-rest-api)
				 */
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				/*
				 * Dichiariamo la sessione stateless. La sessione di ogni request e' senza
				 * stato, questo significa che ogni request e' indipendente dalle altre. Il
				 * fatto che un utente risulti loggato dipende quindi dal token (dal suo expire
				 * time)
				 */
				.httpBasic(it -> {
					/*
					 * httpBasic fornisce un'autenticazione di base con username (nel nostro caso
					 * email, a seconda del principal scelto - vedi metodi di authenticationManager)
					 * e password, da usare ad esempio sul browser (NB: nell'header viene restituito
					 * un token messo dopo il bearer che e' la codifica in base 64 di principal +
					 * password). Da non usare a meno che tu non voglia farti fottere le credenziali
					 */

					// Gestione custom della response in caso di token scaduto o accesso negato
					it.authenticationEntryPoint(customAuthEntryPoint);

				})
				// Se ho un solo AutenticationProvider posso usare questa...
				// .authenticationProvider(authenticationProvider())
				// ...oppure l'authenticationManager dove nel bean specifico un solo provider
				.authenticationManager(authenticationManager())
				.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class).build();
		/*
		 * addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
		 * inserisce il nostro filtro custom JwtAuthFilter authFilter prima del filtro
		 * specificato come secondo
		 * parametro(UsernamePasswordAuthenticationFilter.class) che rapprenseta un
		 * filtro di default. (https://www.marcobehler.com/images/filterchain-1a.png).
		 * 
		 * Infine, tutti questi controlli sulla request si buildano, quindi alla fine
		 * abbiamo .build()
		 */
	}

	/*
	 * CORS
	 * 
	 * In questo metodo vengono definite le cors (Cross-Origin Resource Sharing):
	 * dove vengono specificati gli endpoint che possono fare da client e quindi
	 * inoltrare una request: i cosiddetti Origin (l'origine della request. In una
	 * request, l'informazione sull'Origin e' contenuta nell'header alla voce
	 * "Origin". Per simulare da Postman un client con una determinata Origin che fa
	 * una request, va aggiunta agli header la relativa coppia chiave-valore, ad
	 * esempio: Origin - https://www.google.com. Abbiamo due modi per implementare
	 * le cors: passare per Spring Security o mettere l'annotazione @CrossOrigin nei
	 * vari metodi dei Controller. Se passiamo per Spring Security, le annotazioni
	 * sui controller vengono sovrascritte dalla configurazione di Spring Security.
	 * Con Spring Security, abbiamo dei filtri cors(withDefaults()) che vanno a
	 * caricare le configurazioni impostate nel metodo corsConfigurationSource, dove
	 * tra le tante cose vengono specificati la lista delle Origin consentite e dei
	 * metodi http consentiti nelle request.
	 */
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		// Creazione delle configuazione per le cors
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowCredentials(false);
		configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Headers", "Access-Control-Allow-Origin",
				"Access-Control-Request-Method", "Access-Control-Request-Headers", "Origin", "Cache-Control",
				"Content-Type", "Authorization"));
		configuration.setAllowedMethods(Arrays.asList("DELETE", "GET", "POST", "PATCH", "PUT"));
		// Assegnazione della configuazione ai nostri cors
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	/*
	 * Questo Authentication Provider, per l'autenticazione, usa due bean:
	 * userDetailsService e passwordEncoder
	 * 
	 */
	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	/*
	 * Quando abbiamo piu' di un authentication provider conviene usare un
	 * authentication manger che provera' ad eseguire l'autenticazione con tutti i
	 * provider messi nella lista
	 */
	@Bean
	AuthenticationManager authenticationManager() throws Exception {
		return new ProviderManager(List.of(authenticationProvider()));
	}

	// Posso usare questo manager se ho un solo authenticationProvider
//	@Bean
//	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//		return config.getAuthenticationManager();
//	}
}