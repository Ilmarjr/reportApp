package com.loja.reportapp.reportapp.utils;

import com.loja.reportapp.reportapp.dtos.ProdutoPorClienteDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdutoPorClienteRowMapper implements RowMapper<ProdutoPorClienteDTO> {
    @Override
    public ProdutoPorClienteDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProdutoPorClienteDTO dto = new ProdutoPorClienteDTO();
        dto.setClienteId(rs.getLong("clienteId"));
        dto.setClienteName(rs.getString("clienteName"));
        dto.setProdutoId(rs.getLong("produtoId"));
        dto.setProdutoName(rs.getString("produtoName"));
        dto.setQuantidadeComprada(rs.getLong("quantidadeComprada"));
        return dto;
    }
}

