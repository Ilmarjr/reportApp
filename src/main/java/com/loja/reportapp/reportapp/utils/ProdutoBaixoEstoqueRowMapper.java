package com.loja.reportapp.reportapp.utils;

import com.loja.reportapp.reportapp.dtos.ProdutoBaixoEstoqueDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdutoBaixoEstoqueRowMapper implements RowMapper<ProdutoBaixoEstoqueDTO> {
    @Override
    public ProdutoBaixoEstoqueDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProdutoBaixoEstoqueDTO dto = new ProdutoBaixoEstoqueDTO();
        dto.setProdutoId(rs.getLong("produtoId"));
        dto.setProdutoName(rs.getString("produtoName"));
        dto.setEstoque(rs.getInt("estoque"));
        return dto;
    }
}

