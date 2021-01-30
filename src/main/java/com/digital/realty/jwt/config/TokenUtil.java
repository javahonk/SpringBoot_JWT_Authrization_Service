package com.digital.realty.jwt.config;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.digital.realty.jwt.config.decoder.JwtDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenUtil implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//public static final long JWT_TOKEN_VALIDITY = 5*60*60;
	public static final long JWT_TOKEN_VALIDITY = 8760*60*60;

	@Value("${jwt.secret}")
	private String secret;

	@Autowired
	private JwtDecoder jwtDecoder;

	public String getUsernameFromToken(String token) throws Exception {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getIssuedAtDateFromToken(String token) throws Exception {
		return getClaimFromToken(token, Claims::getIssuedAt);
	}

	public Date getExpirationDateFromToken(String token) throws Exception {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) throws Exception {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) throws  Exception{

		Claims claims = null;
		if (token.contains("custom")){
			token = token.substring(7);
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} else {
			//custom code starts
			claims = jwtDecoder.getClaimsFromToken(token);
			//custom code ends
		}
		//Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		return claims;

	}

	private Boolean isTokenExpired(String token) throws Exception {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	private Boolean ignoreTokenExpiration(String token) {
		// here you specify tokens, for that the expiration is ignored
		return false;
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*1000)).signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public Boolean canTokenBeRefreshed(String token) throws Exception {
		return (!isTokenExpired(token) || ignoreTokenExpiration(token));
	}

	public Boolean validateToken(String token, UserDetails userDetails) throws Exception {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
