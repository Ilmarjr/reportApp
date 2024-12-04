package com.loja.reportapp.reportapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProdutoBaixoEstoqueDTO {
    private Long produtoId;
    private String produtoName;
    private int estoque;
}
