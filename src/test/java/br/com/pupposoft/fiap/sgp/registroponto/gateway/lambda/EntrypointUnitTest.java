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
		final String input = "{resource=/pontos, path=/pontos, httpMethod=POST, headers={Accept=*/*, Accept-Encoding=gzip, deflate, br, Authorization=bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyLXJvbGVzIjpbIkFETUlOIl19.V3_b99xqs2I7DD3N4-Hz7OowwhTgZVQJN0ORcPZoqGw, Content-Type=application/json, Host=9vdxosj26e.execute-api.us-west-2.amazonaws.com, Postman-Token=0e9271c2-64d0-4000-8670-67450552c7e9, User-Agent=PostmanRuntime/7.36.1, X-Amzn-Trace-Id=Root=1-66004a41-1ee6075707ab74c465479153, X-Forwarded-For=177.137.224.208, X-Forwarded-Port=443, X-Forwarded-Proto=https}, multiValueHeaders={Accept=[*/*], Accept-Encoding=[gzip, deflate, br], Authorization=[eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyLXJvbGVzIjpbIkFETUlOIl19.V3_b99xqs2I7DD3N4-Hz7OowwhTgZVQJN0ORcPZoqGw], Content-Type=[application/json], Host=[9vdxosj26e.execute-api.us-west-2.amazonaws.com], Postman-Token=[0e9271c2-64d0-4000-8670-67450552c7e9], User-Agent=[PostmanRuntime/7.36.1], X-Amzn-Trace-Id=[Root=1-66004a41-1ee6075707ab74c465479153], X-Forwarded-For=[177.137.224.208], X-Forwarded-Port=[443], X-Forwarded-Proto=[https]}, queryStringParameters=null, multiValueQueryStringParameters=null, pathParameters=null, stageVariables=null, requestContext={resourceId=bywkm9, resourcePath=/pontos, httpMethod=POST, extendedRequestId=VJCKRGLvvHcEBTg=, requestTime=24/Mar/2024:15:44:01 +0000, path=/prd/pontos, accountId=992382745295, protocol=HTTP/1.1, stage=prd, domainPrefix=9vdxosj26e, requestTimeEpoch=1711295041435, requestId=a107fef8-9004-4664-b6b5-6ea344f803ad, identity={cognitoIdentityPoolId=null, accountId=null, cognitoIdentityId=null, caller=null, sourceIp=177.137.224.208, principalOrgId=null, accessKey=null, cognitoAuthenticationType=null, cognitoAuthenticationProvider=null, userArn=null, userAgent=PostmanRuntime/7.36.1, user=null}, domainName=9vdxosj26e.execute-api.us-west-2.amazonaws.com, deploymentId=8xtiiv, apiId=9vdxosj26e}, body={\"dataHora\": \"2024-03-19T20:29:35.896093573\"}, isBase64Encoded=false}";
		
		ResponseJson handleRequest = entrypoint.handleRequest(input, null);
		
		assertNotNull(handleRequest);
		assertEquals(200, handleRequest.getStatusCode());
		
		verify(tokenGateway).getUserId("eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyLXJvbGVzIjpbIkFETUlOIl19.V3_b99xqs2I7DD3N4-Hz7OowwhTgZVQJN0ORcPZoqGw");
		
		//Continuar asserts e verifys
	}
	
}
