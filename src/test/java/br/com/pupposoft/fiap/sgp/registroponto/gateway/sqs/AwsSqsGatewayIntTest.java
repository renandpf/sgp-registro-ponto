package br.com.pupposoft.fiap.sgp.registroponto.gateway.sqs;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.pupposoft.fiap.sgp.registroponto.domain.Ponto;
import br.com.pupposoft.fiap.sgp.registroponto.gateway.RegistroPontoGateway;

@Disabled("Teste integrado")
class AwsSqsGatewayIntTest {

	@Test
	void shouldRegistrarWithhSucess() {//NOSONAR
		
		RegistroPontoGateway awsSqsGateway = new AwsSqsGateway("sgp-registro-ponto-qeue", getSQSConnectionFactory(), new ObjectMapper());

		Ponto ponto = new Ponto(15L);
		
		awsSqsGateway.registrar(ponto);

	}

	private SQSConnectionFactory getSQSConnectionFactory(){

		String accessKey = "XXX";
		String secretKey = "YYY";
		String sessionToken = "ZZZ";

		final AWSCredentials credentials = new BasicSessionCredentials(accessKey, secretKey, sessionToken);
		final AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);

		AmazonSQSClientBuilder region = AmazonSQSClientBuilder.standard().withCredentials(credentialsProvider).withRegion(Regions.US_WEST_2);

		return new SQSConnectionFactory(new ProviderConfiguration(), region);
	}

}
