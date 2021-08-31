package com.matheus.osworks.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.matheus.osworks.api.model.ComentarioDTO;
import com.matheus.osworks.api.model.ComentarioInput;
import com.matheus.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.matheus.osworks.domain.model.Comentario;
import com.matheus.osworks.domain.model.OrdemServico;
import com.matheus.osworks.domain.repository.OrdemServicoRepository;
import com.matheus.osworks.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servico/{ordemServicoId}/comentarios")
public class ComentarioController {
	
	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServico;
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<ComentarioDTO> listar(@PathVariable Long ordemServicoId){
		OrdemServico ordem = ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada!"));
		
		return toCollectionDTO(ordem.getComentarios());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ComentarioDTO adicionar(@PathVariable Long ordemServicoId, 
			@Valid @RequestBody ComentarioInput comentarioInput) {
		Comentario com = gestaoOrdemServico.adicionarComentario(ordemServicoId, comentarioInput.getDescricao());
		return toDTO(com);
	}
	
	
	public ComentarioDTO toDTO(Comentario com) {
		return modelMapper.map(com, ComentarioDTO.class);
	}
	
	public List<ComentarioDTO> toCollectionDTO(List<Comentario> com){
		return com.stream()
				.map(comentario -> toDTO(comentario))
				.collect(Collectors.toList());
	}
}
