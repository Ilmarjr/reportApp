package com.loja.reportapp.reportapp.repositories;

import com.loja.reportapp.reportapp.dtos.ConsumoMedioClienteDTO;
import com.loja.reportapp.reportapp.dtos.ProdutoBaixoEstoqueDTO;
import com.loja.reportapp.reportapp.dtos.ProdutoMaisVendidoDTO;
import com.loja.reportapp.reportapp.dtos.ProdutoPorClienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReportRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    // Relatório de produtos mais vendidos
    public List<ProdutoMaisVendidoDTO> getProdutosMaisVendidos() {
        String sql = """
                SELECT p.id AS produtoId, p.name AS produtoName, SUM(cp.quantidade) AS quantidadeVendida
                FROM produto p
                JOIN compra_produto cp ON p.id = cp.produto_id
                GROUP BY p.id, p.name
                ORDER BY quantidadeVendida DESC
                LIMIT 10
                """;

        RowMapper<ProdutoMaisVendidoDTO> rowMapper = new BeanPropertyRowMapper<>(ProdutoMaisVendidoDTO.class);
        return namedParameterJdbcTemplate.query(sql, rowMapper);
    }

    // Relatório de produtos por cliente
    public List<ProdutoPorClienteDTO> getProdutosPorCliente(int limit) {
        String sql = """
                SELECT c.id AS clienteId, c.name AS clienteName, p.id AS produtoId, p.name AS produtoName, SUM(cp.quantidade) AS quantidadeComprada
                FROM cliente c
                JOIN compra co ON c.id = co.cliente_id
                JOIN compra_produto cp ON co.id = cp.compra_id
                JOIN produto p ON cp.produto_id = p.id
                GROUP BY c.id, c.name, p.id, p.name
                ORDER BY clienteId, quantidadeComprada DESC
                LIMIT
                """ + " " + limit;

        RowMapper<ProdutoPorClienteDTO> rowMapper = new BeanPropertyRowMapper<>(ProdutoPorClienteDTO.class);
        return namedParameterJdbcTemplate.query(sql, rowMapper);
    }

    // Relatório de consumo médio do cliente
    public List<ConsumoMedioClienteDTO> getConsumoMedioCliente() {
        String sql = """
                SELECT c.id AS clienteId, c.name AS clienteName, AVG(cp.quantidade * p.price) AS consumoMedio
                FROM cliente c
                JOIN compra co ON c.id = co.cliente_id
                JOIN compra_produto cp ON co.id = cp.compra_id
                JOIN produto p ON cp.produto_id = p.id
                GROUP BY c.id, c.name
                ORDER BY consumoMedio DESC
                """;

        RowMapper<ConsumoMedioClienteDTO> rowMapper = new BeanPropertyRowMapper<>(ConsumoMedioClienteDTO.class);
        return namedParameterJdbcTemplate.query(sql, rowMapper);
    }

    // Relatório de produto com baixo estoque
    public List<ProdutoBaixoEstoqueDTO> getProdutosBaixoEstoque(int estoqueLimite) {
        String sql = """
                SELECT p.id AS produtoId, p.name AS produtoName, p.estoque
                FROM produto p
                WHERE p.estoque <= :estoqueLimite
                """;

        Map<String, Object> params = new HashMap<>();
        params.put("estoqueLimite", estoqueLimite);

        RowMapper<ProdutoBaixoEstoqueDTO> rowMapper = new BeanPropertyRowMapper<>(ProdutoBaixoEstoqueDTO.class);
        return namedParameterJdbcTemplate.query(sql, params, rowMapper);
    }
}
