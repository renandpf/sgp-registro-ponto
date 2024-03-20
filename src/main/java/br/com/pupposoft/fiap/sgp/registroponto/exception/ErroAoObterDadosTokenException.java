package br.com.pupposoft.fiap.sgp.registroponto.exception;

import br.com.pupposoft.fiap.starter.exception.SystemBaseException;
import lombok.Getter;

@Getter
public class ErroAoObterDadosTokenException extends SystemBaseException {
	private static final long serialVersionUID = 7477882704402778287L;
	
	private final String code = "sgp.erroAoObterDadosToken";//NOSONAR
	private final String message = "Erro ao obter dados do tojen";//NOSONAR
	private final Integer httpStatus = 500;//NOSONAR

	
}
