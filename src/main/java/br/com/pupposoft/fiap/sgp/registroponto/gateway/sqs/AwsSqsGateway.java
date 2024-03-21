package br.com.pupposoft.fiap.sgp.registroponto.gateway.sqs;

import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

import br.com.pupposoft.fiap.sgp.registroponto.domain.Ponto;
import br.com.pupposoft.fiap.sgp.registroponto.exception.ErroAoEnviarMensagemException;
import br.com.pupposoft.fiap.sgp.registroponto.gateway.RegistroPontoGateway;

public class AwsSqsGateway implements RegistroPontoGateway {

	private SQSConnectionFactory sqsConnectionFactory;

	public AwsSqsGateway() {
		getSQSConnectionFactory();
	}
	
	@Override
	public void registrar(Ponto ponto) {
		SQSConnection sqsConnection = null;
        Session session = null;
        MessageProducer messageProducer = null;

        try {
        	try {
        		sqsConnection = sqsConnectionFactory.createConnection();
        		session = sqsConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        		messageProducer = session.createProducer(session.createQueue("QUEUE NAME"));//FIXME
        		String asString = "message";//FIXME
        		TextMessage message = session.createTextMessage(asString);
        		
        		messageProducer.send(message);
        		
        		
        	} finally {
        		if(sqsConnection != null) {
        			sqsConnection.close();
        		}
        	}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ErroAoEnviarMensagemException();
		}
        

	}
	
	private SQSConnectionFactory getSQSConnectionFactory(){

		String accessKey = System.getProperty("ACCESS_KEY");
		String secretKey = System.getProperty("SECRET_KEY");

		if(sqsConnectionFactory == null){
			final AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
			final AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);

			AmazonSQSClientBuilder region = AmazonSQSClientBuilder.standard().withCredentials(credentialsProvider).withRegion(Regions.US_WEST_2);

			sqsConnectionFactory = new SQSConnectionFactory(new ProviderConfiguration(), region);
		}

		return sqsConnectionFactory;
	}
}
