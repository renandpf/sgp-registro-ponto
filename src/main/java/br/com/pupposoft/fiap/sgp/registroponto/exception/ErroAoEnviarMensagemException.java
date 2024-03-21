package br.com.pupposoft.fiap.sgp.registroponto.exception;

import br.com.pupposoft.fiap.starter.exception.SystemBaseException;
import lombok.Getter;

@Getter
public class ErroAoEnviarMensagemException extends SystemBaseException {
	private static final long serialVersionUID = -4308164376145793198L;
	
	private final String code = "sgp.erroAoEnviarMensagem";//NOSONAR
	private final String message = "Erro ao enviar mensagem";//NOSONAR
	private final Integer httpStatus = 500;//NOSONAR

	
}
