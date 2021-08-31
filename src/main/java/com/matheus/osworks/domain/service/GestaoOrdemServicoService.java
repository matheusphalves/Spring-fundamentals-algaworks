package com.matheus.osworks.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matheus.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.matheus.osworks.domain.exception.NegocioException;
import com.matheus.osworks.domain.model.Cliente;
import com.matheus.osworks.domain.model.Comentario;
import com.matheus.osworks.domain.model.OrdemServico;
import com.matheus.osworks.domain.model.StatusOrdemServico;
import com.matheus.osworks.domain.repository.ClienteRepository;
import com.matheus.osworks.domain.repository.ComentarioRepository;
import com.matheus.osworks.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	public OrdemServico criar(OrdemServico ordemServico) {
		Cliente cliente = 
				clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow(() -> new NegocioException("Cliente não encontrado"));
		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		return ordemServicoRepository.save(ordemServico);
	}
	
	public void finalizarOrdem(Long ordemServicoId) {
		OrdemServico ordem = buscarOrdem(ordemServicoId);
		ordem.finalizar();
		ordemServicoRepository.save(ordem);
	}
	
	public void cancelarOrdem(Long ordemServicoId) {
		OrdemServico ordem = buscarOrdem(ordemServicoId);
		ordem.cancelar();
		ordemServicoRepository.save(ordem);
	}
	
	public Comentario adicionarComentario(Long ordemServicoId, String descricao) {
		OrdemServico ordem = buscarOrdem(ordemServicoId);
		Comentario com = new Comentario();
		com.setDataEnvio(OffsetDateTime.now());
		com.setDescricao(descricao);
		com.setOrdemServico(ordem);
		return comentarioRepository.save(com);
		
	}
	
	private OrdemServico buscarOrdem(Long ordemServicoId){
		return ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada!"));
	}
	
}
