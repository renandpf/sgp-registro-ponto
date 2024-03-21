package br.com.pupposoft.fiap.sgp.registroponto.gateway.lambda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.pupposoft.fiap.sgp.registroponto.gateway.TokenGateway;
import br.com.pupposoft.fiap.sgp.registroponto.gateway.lambda.json.ResponseJson;
import br.com.pupposoft.fiap.sgp.registroponto.usecase.ProcessaPontoUsecase;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;

@ExtendWith({MockitoExtension.class, SystemStubsExtension.class})
class EntrypointUnitTest {

	@InjectMocks
	private Entrypoint entrypoint;

	@Mock
	private ProcessaPontoUsecase processaPontoUsecase;
	
	@Mock
	private TokenGateway tokenGateway;

	@SystemStub
	private static EnvironmentVariables environmentVariables;

	@BeforeAll
	private static void config() {
		environmentVariables.set("IS_UNIT_TEST", "true");
	}
	
	@Test
	void shouldSucessOnHandleRequest() {
		final String input = "{resource=/pontos, path=/pontos, httpMethod=POST, headers={Authorization=eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyLWlkIjoxNX0.6d0ayQ0GAumKdlU-puKBk9EA496KhWK1BMarqcJ7GDc}, multiValueHeaders={Authorization=[eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyLWlkIjoxNX0.6d0ayQ0GAumKdlU-puKBk9EA496KhWK1BMarqcJ7GDc]}, queryStringParameters=null, multiValueQueryStringParameters=null, pathParameters=null, stageVariables=null, requestContext={resourceId=bywkm9, resourcePath=/pontos, httpMethod=POST, extendedRequestId=VAFpoF2GvHcFZ7g=, requestTime=21/Mar/2024:22:35:41 +0000, path=/pontos, accountId=992382745295, protocol=HTTP/1.1, stage=test-invoke-stage, domainPrefix=testPrefix, requestTimeEpoch=1711060541373, requestId=d07b1bae-d807-495f-988f-0d75f2bc6189, identity={cognitoIdentityPoolId=null, cognitoIdentityId=null, apiKey=test-invoke-api-key, principalOrgId=null, cognitoAuthenticationType=null, userArn=arn:aws:sts::992382745295:assumed-role/voclabs/user2937623=renan.puppo@gmail.com, apiKeyId=test-invoke-api-key-id, userAgent=Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36, accountId=992382745295, caller=AROA6ODU67LH5XW3CR4RT:user2937623=renan.puppo@gmail.com, sourceIp=test-invoke-source-ip, accessKey=ASIA6ODU67LHZF3DSNCZ, cognitoAuthenticationProvider=null, user=AROA6ODU67LH5XW3CR4RT:user2937623=renan.puppo@gmail.com}, domainName=testPrefix.testDomainName, apiId=9vdxosj26e}, body={\"userId\": 16,\"dataHora\": \"2024-03-21T19:05:41\"}, isBase64Encoded=false}";
		
		ResponseJson handleRequest = entrypoint.handleRequest(input, null);
		
		assertNotNull(handleRequest);
		assertEquals(200, handleRequest.getStatusCode());
		
		verify(tokenGateway).getUserId("eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyLWlkIjoxNX0.6d0ayQ0GAumKdlU-puKBk9EA496KhWK1BMarqcJ7GDc");
		
		//Continuar asserts e verifys
	}
	
}
