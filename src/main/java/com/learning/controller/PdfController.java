package com.learning.controller;

import com.learning.model.Invoice;
import com.learning.service.PdfGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/invoices")
public class PdfController {

    @Autowired
    private PdfGeneratorService pdfGenerator;

    @PostMapping("/export-to-pdf")
    public ResponseEntity<byte[]> generatePdfFile(@RequestBody Invoice invoice)
    {
        byte[] outputStream =  pdfGenerator.createPdf(invoice);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("invoice.pdf").build());
        return new ResponseEntity<>(outputStream, headers, HttpStatus.OK);

    }
}
