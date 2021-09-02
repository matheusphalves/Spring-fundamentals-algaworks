package com.matheus.osworks.domain.service;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.springframework.stereotype.Service;


@Service
public class GerarPdfService {
	
	public void gerarPDF() throws IOException {
		String pdfName = "teste.pdf";
		
		try {
			PDDocument doc = new PDDocument();
			doc.addPage(new PDPage());
			doc.save(pdfName);
			doc.close();
			System.out.println("Salvo em: " + System.getProperty("user.dir"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
}
