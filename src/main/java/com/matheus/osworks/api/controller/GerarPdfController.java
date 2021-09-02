package com.matheus.osworks.api.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.matheus.osworks.domain.model.Cliente;
import com.matheus.osworks.domain.repository.ClienteRepository;
import com.matheus.osworks.domain.service.GerarPdfService;

@RestController
@RequestMapping("/pdf")
public class GerarPdfController {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private GerarPdfService pdfService;
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Cliente> gerarPdf(@PathVariable Long id) throws IOException {
		System.out.println(id);
		Optional<Cliente> cliente = clienteRepository.findById(id);
		
		if(cliente.isPresent()) {
			pdfService.gerarPDF();
			return ResponseEntity.ok(cliente.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
}
