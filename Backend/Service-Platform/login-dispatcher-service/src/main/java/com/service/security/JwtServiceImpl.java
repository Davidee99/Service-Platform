package com.service.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/*
 * Questa classe genera ed effettua dei controlli sul token.
 * Il token e' composto da tre parti. Per generare l'ultima parte del token, chiamata "firma",
 * si utilizza una chiave privata (SECRET).
 * 
 * www.jwt.io
 * 
 * Generazione della firma: Quando un server crea un JWT per un utente autenticato,
 * genera la firma del token utilizzando una chiave segreta o una chiave privata.
 * Questa firma viene creata combinando l'intestazione e il corpo del JWT, e quindi
 * applicando una funzione di hash (solitamente HMAC o RSA) utilizzando la chiave segreta o privata.
 * 
 * Inclusione nel token: La firma generata viene inclusa come terza parte del JWT insieme
 * all'intestazione e al corpo. Questo JWT viene quindi inviato all'utente o al client come
 * token di autenticazione.
 * 
 * Verifica della firma: Quando il client riceve il JWT e lo invia di nuovo al server per accedere a una
 * risorsa protetta, il server deve verificare che il token non sia stato manomesso. Per farlo, estrae
 * l'intestazione e il corpo dal token ricevuto e usa la stessa chiave (o la chiave pubblica se si tratta
 * di una firma RSA) per generare una firma locale. Quindi confronta questa firma locale con la firma
 * presente nel token. Se le firme coincidono, il token è considerato valido e non è stato alterato.
 */
@Service
public class JwtServiceImpl implements JwtService {

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		// Equipara l'email ottenuta dal token con l'email ottenuta dal database e controlla che
		// il token sia ancora attivo
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public String generateToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userName);
	}

	// PRIVATE //

	private static final String SECRET = "404D635166546A576E5A7234753778214125442A472D4B6150645267556B5870";

	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	private String createToken(Map<String, Object> claims, String userName) {
		return Jwts.builder().setClaims(claims).setSubject(userName).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 60 minuti
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	/*
	 * Il metodo ".parseClaimsJws(token)" lancia delle eccezioni in caso di non conformità del
	 * token, quindi tutti gli altri metodi sono superflui, visto che tale metodo viene
	 * invocato da tutti gli altri metodi di controllo. DA IMPLEMENTARE: maggior leggibilità e
	 * efficienza
	 */
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

}
