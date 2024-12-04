package com.loja.reportapp.reportapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProdutoPorClienteDTO {
    private Long clienteId;
    private String clienteName;
    private Long produtoId;
    private String produtoName;
    private Long quantidadeComprada;
}
