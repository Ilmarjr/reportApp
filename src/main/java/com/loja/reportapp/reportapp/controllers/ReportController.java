package com.loja.reportapp.reportapp.controllers;

import com.loja.reportapp.reportapp.dtos.ConsumoMedioClienteDTO;
import com.loja.reportapp.reportapp.dtos.ProdutoBaixoEstoqueDTO;
import com.loja.reportapp.reportapp.dtos.ProdutoMaisVendidoDTO;
import com.loja.reportapp.reportapp.dtos.ProdutoPorClienteDTO;
import com.loja.reportapp.reportapp.repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportRepository reportRepository;

    @GetMapping("/produtos-mais-vendidos")
    public ResponseEntity<List<ProdutoMaisVendidoDTO>> getProdutosMaisVendidos() {
        List<ProdutoMaisVendidoDTO> produtos = reportRepository.getProdutosMaisVendidos();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/produtos-por-cliente")
    public ResponseEntity<List<ProdutoPorClienteDTO>> getProdutosPorCliente(@RequestParam(value = "Limit", defaultValue = "100") int limit) {
        List<ProdutoPorClienteDTO> produtos = reportRepository.getProdutosPorCliente(limit);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/consumo-medio-cliente")
    public ResponseEntity<List<ConsumoMedioClienteDTO>> getConsumoMedioPorCliente() {
        List<ConsumoMedioClienteDTO> consumos = reportRepository.getConsumoMedioCliente();
        return ResponseEntity.ok(consumos);
    }

    @GetMapping("/produtos-baixo-estoque")
    public ResponseEntity<List<ProdutoBaixoEstoqueDTO>> getProdutosBaixoEstoque(
            @RequestParam(defaultValue = "10") int limite) {
        List<ProdutoBaixoEstoqueDTO> produtos = reportRepository.getProdutosBaixoEstoque(limite);
        return ResponseEntity.ok(produtos);
    }
}
