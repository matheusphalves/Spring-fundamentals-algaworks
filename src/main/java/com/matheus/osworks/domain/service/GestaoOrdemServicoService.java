package com.matheus.osworks.domain.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matheus.osworks.domain.exception.NegocioException;
import com.matheus.osworks.domain.model.Cliente;
import com.matheus.osworks.domain.model.OrdemServico;
import com.matheus.osworks.domain.model.StatusOrdemServico;
import com.matheus.osworks.domain.repository.ClienteRepository;
import com.matheus.osworks.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	
	
	
	public OrdemServico criar(OrdemServico ordemServico) {
		Cliente cliente = 
				clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow(() -> new NegocioException("Cliente não encontrado"));
		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(LocalDateTime.now());
		return ordemServicoRepository.save(ordemServico);
	}
	
}
