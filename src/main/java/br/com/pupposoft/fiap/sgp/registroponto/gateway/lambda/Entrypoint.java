package br.com.pupposoft.fiap.sgp.registroponto.gateway.lambda;

import java.util.HashMap;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import br.com.pupposoft.fiap.sgp.registroponto.domain.Ponto;
import br.com.pupposoft.fiap.sgp.registroponto.gateway.TokenGateway;
import br.com.pupposoft.fiap.sgp.registroponto.gateway.lambda.json.ResponseJson;
import br.com.pupposoft.fiap.sgp.registroponto.usecase.ProcessaPontoUsecase;


public class Entrypoint implements RequestHandler<Object, ResponseJson> {

	private ProcessaPontoUsecase processaPontoUsecase;
	
	private TokenGateway tokenGateway;
	
	@Override
	public ResponseJson handleRequest(Object input, Context context) {
		System.out.println("input=" + input);
		System.out.println("context=" + context);

		try {
			final String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyUm9sZXMiOlsiQURNSU4iXX0.yUjlX1zLU_fxCWGLZc1B1_2oB1BEwEAUZsKdoO5grQY";
			Long userId = tokenGateway.getUserId(token);
			
			processaPontoUsecase.processar(new Ponto(userId));//NOSONAR
			return new ResponseJson(false, 200, new HashMap<>(), "OK");
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseJson(false, 500, new HashMap<>(), e.getMessage());
		}
	}
}
