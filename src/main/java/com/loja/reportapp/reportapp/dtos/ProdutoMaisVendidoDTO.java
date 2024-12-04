package com.loja.reportapp.reportapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProdutoMaisVendidoDTO {
    private Long produtoId;
    private String produtoName;
    private Long quantidadeVendida;
}
