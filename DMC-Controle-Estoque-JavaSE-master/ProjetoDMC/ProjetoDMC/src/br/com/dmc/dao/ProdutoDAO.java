/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.dmc.dao;

import br.com.dmc.model.Produto;
import br.com.dmc.model.ConexaoDB;
import java.sql.*;
import java.util.ArrayList;


/** Persistência de dados de Produtos

 * @author Grupo DMC

 * @version 1

 * @since 16/11/2015

 */
public class ProdutoDAO {

    /**
     * Cadastro de produtos
     *
     * @author Grupo DMC
     *
     * @param produto Produto - Recebe por referência uma instância de Produto
     *
     * @return int - Auxilia no retorno ao usuário
     */
    public int cadastrarProduto(Produto produto) {
        ConexaoDB conn = new ConexaoDB();
        Connection conexao = conn.conectar();
        String sql = "INSERT INTO Produto (nome,descricao,marca,valor)"
                + "VALUES (?,?,?,?);";
        try (PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, produto.getNome());
            ps.setString(2, produto.getDescricao());
            ps.setString(3, produto.getMarca());
            ps.setFloat(4, produto.getValor());
            ps.execute();
            ps.close();

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar!");
        } finally {
            conn.desconectar();
        }
        return 1;
    }
    
    /**
     * Consultar Produtos cadastrados
     *
     * @author Grupo DMC
     *
     * @param nome String - Pesquisas por nome
     * @param opcao int - Auxilia o tipo de pesquisa a executar
     * @param produto Produto - Referencia de uma instância de Produto para receber dados do Banco
     *
     * @return tempProduto ArrayList - Retorna uma lista com produtos
     */
    public ArrayList<Produto> consultarProduto(String nome, int opcao, Produto produto) {
        ConexaoDB conn = new ConexaoDB();
        Connection conexao = conn.conectar();
        Statement stm;
        ResultSet rs;
        ArrayList<Produto> tempProduto = new ArrayList<>();
        try {
            stm = conexao.createStatement();
            String sql;
            if (opcao == 1) {
                sql = "SELECT * FROM Produto WHERE Nome LIKE '" + nome + "%';";
            } else {
                sql = "SELECT * FROM Produto;";
            }
            rs = stm.executeQuery(sql);
            boolean aux = true;

            while (rs.next()) {
                produto = new Produto();
                produto.setId(rs.getInt("Id"));
                produto.setNome(rs.getString("Nome"));
                produto.setDescricao(rs.getString("Descricao"));
                produto.setMarca(rs.getString("Marca"));
                produto.setValor(rs.getFloat("Valor"));
                tempProduto.add(produto);
                aux = false;
            }
            if (aux) {
                produto.setId(-1);
            }
            rs.close();
            stm.close();

        } catch (Exception e) {
            System.err.println("Erro ao consultar.");
        } finally {
            conn.desconectar();
        }
        return tempProduto;
    }
    
        /**
     * Seta um Produto a ser alterado
     *
     * @author Grupo DMC
     *
     * @param codigo int - Código do Produto escolhido pelo usuário
     * @param produto Produto - Referencia de uma instância de Produto para receber dados do Banco
     *
     * @return aux int  - Auxilia no retorno ao usuário
     */
    public int selecionaUnidade(int codigo, Produto produto) {
        ConexaoDB conn = new ConexaoDB();
        Connection conexao = conn.conectar();
        Statement stm;
        ResultSet rs;
        int aux = 0;
        try {
            stm = conexao.createStatement();
            String sql = "SELECT * FROM Produto WHERE Id = " + codigo + ";";
            rs = stm.executeQuery(sql);

            if (rs.next()) {
                Integer id = rs.getInt("Id");
                String name = rs.getString("Nome");
                String descricao = rs.getString("Descricao");
                String marca = rs.getString("Marca");
                float valor = rs.getFloat("Valor");
                produto.setNome(name);
                produto.setDescricao(descricao);
                produto.setMarca(marca);
                produto.setValor(valor);
                aux = 1;
            }
            stm.close();
            rs.close();
        } catch (Exception e) {
            System.err.println("Erro ao consultar!");
        } finally {
            conn.desconectar();
        }
        return aux;
    }
    /**
     * Atualiza o produtos
     *
     * @author Grupo DMC
     *
     * @param codigo int - Código do Produto escolhido pelo usuário
     * @param produto Produto - Referencia de uma instância de Produto para receber dados do Banco
     *
     * @return int  - Auxilia no retorno ao usuário
     */
    public int alterarProduto(int codigo, Produto produto) {
        ConexaoDB conn = new ConexaoDB();
        Connection conexao = conn.conectar();
        // UPDATE
        try {
            conexao.setAutoCommit(false);
            String sql = "UPDATE Produto SET Nome=?,Descricao=?,Marca=?,Valor=?"
                    + "WHERE Id = ?";
            try (PreparedStatement ps = conexao.prepareStatement(sql)) {
                ps.setString(1, produto.getNome());
                ps.setString(2, produto.getDescricao());
                ps.setString(3, produto.getMarca());
                ps.setFloat(4, produto.getValor());
                ps.setInt(5, codigo);
                ps.execute();
                conexao.commit();
            }

        } catch (Exception ex) {
            System.err.println("Erro ao alterar!");
        } finally {
            conn.desconectar();
        }
        return 1;
    }
}
