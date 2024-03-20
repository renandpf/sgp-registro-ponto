package br.com.pupposoft.fiap.sgp.registroponto.domain;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class Ponto {
	private Long userId;
	private LocalDateTime dataHora;
	
	public Ponto(Long userId) {
		this.userId = userId;
		dataHora = LocalDateTime.now();
	}
	
}
