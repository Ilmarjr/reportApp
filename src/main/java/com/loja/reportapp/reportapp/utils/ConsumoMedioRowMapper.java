package com.loja.reportapp.reportapp.utils;

import com.loja.reportapp.reportapp.dtos.ConsumoMedioClienteDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsumoMedioRowMapper implements RowMapper<ConsumoMedioClienteDTO> {
    @Override
    public ConsumoMedioClienteDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ConsumoMedioClienteDTO dto = new ConsumoMedioClienteDTO();
        dto.setClienteId(rs.getLong("clienteId"));
        dto.setClienteName(rs.getString("clienteName"));
        dto.setConsumoMedio(rs.getDouble("consumoMedio"));
        return dto;
    }
}

