package com.loja.reportapp.reportapp.controllers;

import com.loja.reportapp.reportapp.PDF.ExportPDF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/api/v1/exportreport")
public class ExportReportController {

    @Autowired
    private ExportPDF exportPDF;

    @GetMapping(produces = MediaType.APPLICATION_PDF_VALUE)
    public @ResponseBody byte[] exportReport(){
        try{
            ByteArrayOutputStream pdfByteStream = exportPDF.generatePDF();
            return pdfByteStream.toByteArray();
        }catch (Exception ex){
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
