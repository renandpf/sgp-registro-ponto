package br.com.pupposoft.fiap.sgp.registroponto.gateway.jwt;

import javax.xml.bind.DatatypeConverter;

import br.com.pupposoft.fiap.sgp.registroponto.exception.ErroAoObterDadosTokenException;
import br.com.pupposoft.fiap.sgp.registroponto.gateway.TokenGateway;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtTokenGateway implements TokenGateway {
	private static final String USER_ID = "user-id";

	private String secretKey;
	
	@Override
	public Long getUserId(String token) {
		try {
			
			Integer userIdInteger = (Integer) getBodyfromToken(token).get(USER_ID);
			
			return userIdInteger.longValue();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ErroAoObterDadosTokenException();
		}
	}

	private Claims getBodyfromToken(String token) {	 
		byte[] secretBytes = DatatypeConverter.parseBase64Binary(secretKey);
		return Jwts.parserBuilder()
	        .setSigningKey(secretBytes)
	        .build()
	        .parseClaimsJws(token)
	        .getBody();
	}
	
}
