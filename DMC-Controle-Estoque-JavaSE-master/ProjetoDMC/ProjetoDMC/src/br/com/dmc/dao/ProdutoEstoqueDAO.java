/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.dmc.dao;

import br.com.dmc.model.ConexaoDB;
import br.com.dmc.model.ProdutoEstoque;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Consulta de Produto e Estoque  *
 * @author Grupo DMC
 *
 * @version 1
 *
 * @since 16/11/2015
 *
 */
public class ProdutoEstoqueDAO {

    /**
     * Realiza a consulta no banco trazendo informações de Produto e Estoque
     * para gerar Relatórios
     *
     * @author Grupo DMC
     *
     * @param produtoEstoque - Referencia de uma instância de ProdutoEstoque
     * para receber dados do Banco
     * @param opcao int - Auxilia o tipo de pesquisa a executar
     *
     * @return listProdutoEstoque ArrayList - Retorna uma lista com produtos em
     * estoque
     */
    public ArrayList<ProdutoEstoque> ListaEstoque(ProdutoEstoque produtoEstoque, int opcao) {
        ConexaoDB conn = new ConexaoDB();
        Connection conexao = conn.conectar();
        Statement stm;
        ResultSet rs;
        ArrayList<ProdutoEstoque> listProdutoEstoque = new ArrayList<>();
        try {
            stm = conexao.createStatement();
            String sql;

            // com essas opções escolhe qual tipo de consulta  sera feita em protudo_estoque 
            if (opcao == 1) {
                sql = " SELECT b.idproduto , a.Nome,a.descricao,a.marca,a.valor, b.Quantidade,b.qtdMinima,b.qtdMaxima, b.status, c.Entrada "
                        + " FROM produto a, estoque b, produto_estoque c "
                        + " WHERE a.Id = b.IdProduto and c.idestoque=b.idproduto;";
            } else {
                sql = " SELECT b.idproduto , a.Nome,a.descricao,a.marca,a.valor, b.Quantidade,b.qtdMinima,b.qtdMaxima, b.status "
                        + " FROM produto a, estoque b "
                        + " WHERE a.Id = b.IdProduto and b.status =0;";
            }

            rs = stm.executeQuery(sql);
            boolean aux = true;

            // faz um set na classe estoque  e adiciona os valores no arraylist
            while (rs.next()) {
                produtoEstoque = new ProdutoEstoque();
                produtoEstoque.setId(rs.getInt("IdProduto"));
                produtoEstoque.setNome(rs.getString("nome"));
                produtoEstoque.setDescricao(rs.getString("descricao"));
                produtoEstoque.setMarca(rs.getString("marca"));
                produtoEstoque.setValor(rs.getFloat("valor"));
                produtoEstoque.setQuantidade(rs.getInt("Quantidade"));
                produtoEstoque.setQtdMinima(rs.getInt("qtdMinima"));
                produtoEstoque.setQtdMaxima(rs.getInt("qtdMaxima"));
                produtoEstoque.setStatus(rs.getInt("status"));
                if (opcao == 1) {
                    produtoEstoque.setEntrada(rs.getString("Entrada"));
                }
                listProdutoEstoque.add(produtoEstoque);
                aux = false;
            }
            if (aux) {
                produtoEstoque.setId(-1);
            }
        } catch (Exception e) {
            System.err.println("Erro ao consultar!");
        } finally {
            conn.desconectar();
        }
        return listProdutoEstoque;
    }

}
