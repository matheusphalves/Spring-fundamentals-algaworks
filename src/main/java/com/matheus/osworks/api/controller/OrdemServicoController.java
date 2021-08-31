package com.matheus.osworks.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.matheus.osworks.api.model.OrdemServicoDTO;
import com.matheus.osworks.api.model.OrdemServicoInput;
import com.matheus.osworks.domain.model.OrdemServico;
import com.matheus.osworks.domain.repository.OrdemServicoRepository;
import com.matheus.osworks.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {
	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServico;
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServicoDTO criar(@Valid @RequestBody OrdemServicoInput ordemServicoInput) {
		return toDTO(gestaoOrdemServico.criar(toDomain(ordemServicoInput)));
	}
	
	@GetMapping
	public List<OrdemServicoDTO> listar(){
		return toCollectionDTO(ordemServicoRepository.findAll());
	}
	
	@GetMapping("/{ordemServicoId}")
	public ResponseEntity<OrdemServicoDTO> buscar(@PathVariable Long ordemServicoId){
		Optional<OrdemServico> ordem = ordemServicoRepository.findById(ordemServicoId);
		if(ordem.isPresent()) {
			OrdemServicoDTO model = toDTO(ordem.get());
			return ResponseEntity.ok(model);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{ordemServicoId}/cancelar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancelar(@PathVariable Long ordemServicoId) {
		gestaoOrdemServico.cancelarOrdem(ordemServicoId);
	}
	
	@PutMapping("/{ordemServicoId}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable Long ordemServicoId) {
		gestaoOrdemServico.finalizarOrdem(ordemServicoId);
	}
	
	private OrdemServicoDTO toDTO(OrdemServico ordem) {
		return modelMapper.map(ordem, OrdemServicoDTO.class);
	}
	
	private List<OrdemServicoDTO> toCollectionDTO(List<OrdemServico> ordens){
		return ordens.stream()
				.map(ordemServico -> toDTO(ordemServico))
				.collect(Collectors.toList());
	}
	
	private OrdemServico toDomain(OrdemServicoInput ordem) {
		return modelMapper.map(ordem, OrdemServico.class);
	}

}
