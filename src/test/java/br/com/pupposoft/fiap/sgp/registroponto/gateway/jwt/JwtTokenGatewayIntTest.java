package br.com.pupposoft.fiap.sgp.registroponto.gateway.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.junit.jupiter.api.Test;

import br.com.pupposoft.fiap.sgp.registroponto.gateway.TokenGateway;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

class JwtTokenGatewayIntTest {

	@Test
	void shouldSucessOnGetUserId() {
		final String secret = "JKNlgE8ogvrTuu8v50860Psw4PpZNwBMFAKhPaow4a1ATeUooC";
		
		final Long userId = 15L;
		
		final String token = generateToken(userId, secret);
		System.out.println(token);
		
		TokenGateway tokenGateway = new JwtTokenGateway(secret);
		
		assertEquals(userId, tokenGateway.getUserId(token)) ;
		
	}
	
	private String generateToken(Long userId, String secret) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		Date now = new Date();

		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
		SecretKeySpec signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		Map<String, Object> infos = new HashMap<>();
		infos.put("user-id", (Long) userId);
		
		JwtBuilder builder = Jwts.builder()
				.setId(UUID.randomUUID().toString())
				.setIssuedAt(now)
				.setSubject("any subject")
				.setIssuer("sgp-usuario")
				.setExpiration(getExpirationDate())
				.setClaims(infos)
				.signWith(signingKey, signatureAlgorithm);

		return builder.compact();
	}
	
	private Date getExpirationDate() {
		return Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
	}
	
}
