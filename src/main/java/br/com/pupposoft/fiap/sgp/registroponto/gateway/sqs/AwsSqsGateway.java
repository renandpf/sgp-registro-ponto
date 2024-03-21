package br.com.pupposoft.fiap.sgp.registroponto.gateway.sqs;

import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.pupposoft.fiap.sgp.registroponto.domain.Ponto;
import br.com.pupposoft.fiap.sgp.registroponto.exception.ErroAoEnviarMensagemException;
import br.com.pupposoft.fiap.sgp.registroponto.gateway.RegistroPontoGateway;
import br.com.pupposoft.fiap.sgp.registroponto.gateway.sqs.json.MessageJson;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AwsSqsGateway implements RegistroPontoGateway {

	private String queueName;
	
	private SQSConnectionFactory sqsConnectionFactory;
	
	private ObjectMapper objectMapper;
	
	@Override
	public void registrar(Ponto ponto) {
		SQSConnection sqsConnection = null;
        Session session = null;
        MessageProducer messageProducer = null;
        
        try {
        	try {
        		sqsConnection = sqsConnectionFactory.createConnection();
        		session = sqsConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        		messageProducer = session.createProducer(session.createQueue(queueName));
        		String messageStr = objectMapper.writeValueAsString(new MessageJson(ponto));
        		TextMessage message = session.createTextMessage(messageStr);
        		
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
}
