package br.com.pupposoft.fiap.sgp.registroponto.usecase;

import br.com.pupposoft.fiap.sgp.registroponto.domain.Ponto;
import br.com.pupposoft.fiap.sgp.registroponto.gateway.RegistroPontoGateway;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProcessaPontoUsecase {
	
	private RegistroPontoGateway registroPontoGateway;
	
	public void processar(Ponto ponto) {
		registroPontoGateway.registrar(ponto);
	}

}
