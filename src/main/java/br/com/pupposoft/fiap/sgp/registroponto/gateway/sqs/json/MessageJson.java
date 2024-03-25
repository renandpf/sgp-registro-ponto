package br.com.pupposoft.fiap.sgp.registroponto.gateway.sqs.json;

import br.com.pupposoft.fiap.sgp.registroponto.domain.Ponto;
import lombok.Getter;

@Getter
public class MessageJson {
	private Long userId;
	private String dataHora;

	public MessageJson(Ponto ponto) {
		userId = ponto.getUserId();
		dataHora = ponto.getDataHora().toString();
	}
	
}
