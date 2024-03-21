package br.com.pupposoft.fiap.sgp.registroponto.gateway.lambda;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.pupposoft.fiap.sgp.registroponto.domain.Ponto;
import br.com.pupposoft.fiap.sgp.registroponto.gateway.TokenGateway;
import br.com.pupposoft.fiap.sgp.registroponto.gateway.jwt.JwtTokenGateway;
import br.com.pupposoft.fiap.sgp.registroponto.gateway.lambda.json.ResponseJson;
import br.com.pupposoft.fiap.sgp.registroponto.gateway.sqs.AwsSqsGateway;
import br.com.pupposoft.fiap.sgp.registroponto.usecase.ProcessaPontoUsecase;


public class Entrypoint implements RequestHandler<Object, ResponseJson> {

	private ProcessaPontoUsecase processaPontoUsecase;
	
	private TokenGateway tokenGateway;
	
	public Entrypoint() {
		if(System.getenv("IS_UNIT_TEST") == null) {
			String queueName = System.getenv("SGP_REGISTRO_PONTO_QUEUE_NAME");
			SQSConnectionFactory sqsConnectionFactory = getSQSConnectionFactory();
			AwsSqsGateway awsSqsGateway = new AwsSqsGateway(queueName, sqsConnectionFactory, new ObjectMapper());
			processaPontoUsecase = new ProcessaPontoUsecase(awsSqsGateway);
			tokenGateway = new JwtTokenGateway(System.getenv("TOKEN_SECRET"));
		}
	}
	
	@Override
	public ResponseJson handleRequest(Object input, Context context) {
		System.out.println("input=" + input);
		System.out.println("context=" + context);

		try {
			final String token = getToken(input.toString());
			Long userId = tokenGateway.getUserId(token);
			
			processaPontoUsecase.processar(new Ponto(userId));//NOSONAR
			return new ResponseJson(false, 200, new HashMap<>(), "OK");
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseJson(false, 500, new HashMap<>(), e.getMessage());
		}
	}
	
	private SQSConnectionFactory getSQSConnectionFactory(){

		String accessKey = System.getenv("ACCESS_KEY");
		String secretKey = System.getenv("SECRET_KEY");
		String sessionToken = System.getenv("SESSION_TOKEN");

		final AWSCredentials credentials = new BasicSessionCredentials(accessKey, secretKey, sessionToken);
		final AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);

		AmazonSQSClientBuilder region = AmazonSQSClientBuilder.standard().withCredentials(credentialsProvider).withRegion(Regions.US_WEST_2);

		return new SQSConnectionFactory(new ProviderConfiguration(), region);
	}
	
	private String getToken(String input) {
		List<String> inputs = Arrays.asList(input.split(", "));
		
		String headers = inputs.stream().filter(i -> i.contains("headers=")).findAny().orElseThrow();
		String[] authorization = headers.split("Authorization=");
		
		if(authorization.length == 0) {
			throw new RuntimeException("Token não informado");//NOSONAR
		}
		
		return authorization[1].substring(0, authorization[1].length()-1);
	}
}
