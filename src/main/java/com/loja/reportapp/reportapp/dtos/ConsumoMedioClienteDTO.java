package com.loja.reportapp.reportapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ConsumoMedioClienteDTO {
    private Long clienteId;
    private String clienteName;
    private Double consumoMedio;
}
