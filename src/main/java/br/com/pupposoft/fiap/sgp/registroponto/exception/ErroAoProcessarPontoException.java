package br.com.pupposoft.fiap.sgp.registroponto.exception;

import br.com.pupposoft.fiap.starter.exception.SystemBaseException;
import lombok.Getter;

@Getter
public class ErroAoProcessarPontoException extends SystemBaseException {
	private static final long serialVersionUID = -8494944037288744426L;
	
	private final String code = "sgp.erroAoProcessaPonto";//NOSONAR
	private final String message = "Erro ao processar o ponto";//NOSONAR
	private final Integer httpStatus = 500;//NOSONAR

	
}
