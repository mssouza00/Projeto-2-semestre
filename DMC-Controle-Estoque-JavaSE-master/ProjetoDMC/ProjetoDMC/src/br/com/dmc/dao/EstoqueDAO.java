package br.com.dmc.dao;

import br.com.dmc.model.Estoque;
import br.com.dmc.model.ConexaoDB;
import java.sql.*;
import java.util.ArrayList;

/**
 * Persistência de dados de Estoque
 *
 * @author Grupo DMC
 *
 * @version 1
 *
 * @since 16/11/2015
 *
 */
public class EstoqueDAO {

    /**
     * Cadastro de produtos em estoque
     *
     * @param estoque Estoque - Recebe por referência uma instância de Estoque
     *
     * @return aux int - Auxilia na mensagem de retorno ao usuário
     */
    public int cadastrarEstoque(Estoque estoque) {
        ConexaoDB conn = new ConexaoDB();
        Connection conexao = conn.conectar();
        int aux = 0;
        try {
            String sql = "INSERT INTO Estoque (IdProduto,Quantidade,qtdMinima,qtdMaxima)"
                    + "VALUES (?,?,?,?);";
            try (PreparedStatement ps = conexao.prepareStatement(sql)) {
                ps.setInt(1, estoque.getIdProduto());
                ps.setInt(2, estoque.getQuantidade());
                ps.setInt(3, estoque.getQtdMinima());
                ps.setInt(4, estoque.getQtdMaxima());
                ps.execute();
                aux = 1;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar!");
        } finally {
            conn.desconectar();
        }
        return aux;
    }

    /**
     * Pesquisas de itens em estoque
     *
     * @param opcao int - Auxilia o tipo de pesquisa a executar
     * @param nomeProduto String - Pesquisas por nome
     * @param estoque Estoque - Referencia de uma instância de Estoque para
     * receber dados do Banco
     *
     * @return tempProduto ArrayList - Retorna uma lista itens de estoque
     */
    public ArrayList<Estoque> consultaEstoque(int opcao, String nomeProduto, Estoque estoque) {
        ConexaoDB conn = new ConexaoDB();
        Connection conexao = conn.conectar();
        Statement stm;
        ResultSet rs;
        ArrayList<Estoque> listProduto = new ArrayList<>();
        try {
            stm = conexao.createStatement();
            String sql;
            
            // com essas opções escolhe qual tipo de consulta  sera feita em protudo_estoqye 
            switch (opcao) {
                case 1:
                    sql = "SELECT b.IdProduto, a.Nome, b.Quantidade,b.qtdMinima,b.qtdMaxima,c.Entrada \n"
                            + "FROM produto a, estoque b, produto_estoque c \n"
                            + "WHERE a.Id = b.IdProduto and a.Nome LIKE '" + nomeProduto + "%' and b.Status=1 and c.IdEstoque = b.IdProduto;";
                    break;
                case 2:
                    sql = "SELECT b.IdProduto, a.Nome, b.Quantidade,b.qtdMinima,b.qtdMaxima\n"
                            + "FROM produto a, estoque b\n"
                            + "WHERE a.Id = b.IdProduto and b.Status=0;";
                    break;
                default:
                    sql = "SELECT b.IdProduto, a.Nome, b.Quantidade,b.qtdMinima,b.qtdMaxima,c.Entrada\n"
                            + "FROM produto a, estoque b, produto_estoque c\n"
                            + "WHERE a.Id = b.IdProduto and b.Status=1 and b.idproduto = c.idestoque;";
                    break;
            }
            rs = stm.executeQuery(sql);
            boolean aux = true;
            
            // faz um set na classe estoque  e adiciona os valores no arraylist
            while (rs.next()) {
                estoque = new Estoque();
                estoque.setIdProduto(rs.getInt("IdProduto"));
                estoque.setNomeProduto(rs.getString("Nome"));
                estoque.setQuantidade(rs.getInt("Quantidade"));
                estoque.setQtdMinima(rs.getInt("qtdMinima"));
                estoque.setQtdMaxima(rs.getInt("qtdMaxima"));
                if(opcao!=2){
                    estoque.setEntrada(rs.getString("Entrada"));
                }
                listProduto.add(estoque);
                aux = false;
            }
            if (aux) {
                estoque.setIdProduto(-1);
            }
        } catch (Exception e) {
            System.err.println("Erro ao consultar!");
        } finally {
            conn.desconectar();
        }
        return listProduto;
    }

    /**
     * Seleciona um item de estoque
     *
     * @param codigo int - Código do Estoque escolhido pelo usuário
     * @param estoque Estoque - Referencia de uma instância de Estoque para receber dados do Banco
     *
     * @return aux int - Auxilia na mensagem de retorno ao usuário
     */
    public int selecionaEstoque(int codigo, Estoque estoque) {
        ConexaoDB conn = new ConexaoDB();
        Connection conexao = conn.conectar();
        Statement stm;
        ResultSet rs;
        int aux = 0;
        try {
            stm = conexao.createStatement();
            String sql = "SELECT * FROM Estoque WHERE IdProduto = " + codigo + " and Status = 1;";
            rs = stm.executeQuery(sql);
            if (rs.next()) {
                int quantidade = rs.getInt("Quantidade");
                int qtdMinima = rs.getInt("qtdMinima");
                int qtdMaxima = rs.getInt("qtdMaxima");
                estoque.setQuantidade(quantidade);
                estoque.setQtdMinima(qtdMinima);
                estoque.setQtdMaxima(qtdMaxima);
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
     * Realiza a alteração do Item de estoque no Banco de Dados
     *
     * @param estoque Estoque - Recebe por referência uma instância de Estoque
     * @param codigo int - Utiliza o código para setar qual produto quer alterar
     *
     * @return aux int - Auxilia na mensagem de retorno ao usuário
     */
    public int alterarProduto(int codigo, Estoque estoque) {
        ConexaoDB conn = new ConexaoDB();
        Connection conexao = conn.conectar();
        // UPDATE
        int aux = 0;
        try {
            conexao.setAutoCommit(false);
            String sql = "UPDATE Estoque SET Quantidade=?,qtdMinima=?,qtdMaxima=?"
                    + "WHERE IdProduto = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, estoque.getQuantidade());
            ps.setInt(2, estoque.getQtdMinima());
            ps.setInt(3, estoque.getQtdMaxima());
            ps.setInt(4, codigo);
            ps.execute();
            conexao.commit();
            aux = 1;

        } catch (Exception ex) {
            System.err.println("Erro ao alterar!");
        } finally {
            conn.desconectar();
        }
        return aux;
    }

    /**
     * Desativa ou Ativa itens em Estoque
     *
     * @param opcao int - Auxilia na escolha da instrução a executar
     * @param codigo int - Utiliza o código para setar qual produto quer alterar
     *
     * @return aux int - Auxilia na mensagem de retorno ao usuário
     */
    public int desativarItemDeEstoqueDAO(int codigo, int opcao) {
        ConexaoDB conn = new ConexaoDB();
        Connection conexao = conn.conectar();
        // UPDATE
        int aux = 0;
        String sql;
        try {
            conexao.setAutoCommit(false);
            if (opcao == 1) {
                sql = "UPDATE Estoque SET Status=0 WHERE IdProduto = ? ;";
            } else {
                sql = "UPDATE Estoque SET Status=1 WHERE IdProduto = ? ;";
            }
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, codigo);
            ps.execute();
            conexao.commit();
            aux = 1;
        } catch (Exception ex) {
            if (opcao == 1) {
                System.err.println("Erro ao desativar o item! " + ex);
            } else {
                System.err.println("Erro ao ativar o item! " + ex);
            }
        } finally {
            conn.desconectar();
        }

        return aux;
    }
    /**
     * Desativa ou Ativa itens em Estoque
     *
     * @param estoque Estoque - Recebe por referência uma instância de Estoque
     *
     * @return listEstoque ArrayList - Retorna uma lista popula com Itens de estoque
     */
    public ArrayList<Estoque> emitirAlerta(Estoque estoque) {
        
        ArrayList<Estoque> listEstoque = new ArrayList<>();
        ConexaoDB conn = new ConexaoDB();
        Connection conexao = conn.conectar();
        Statement stm;
        ResultSet rs;
        try {
            stm = conexao.createStatement();
            String sql = "select b.IdProduto, a.Nome, b.quantidade, b.qtdMinima from produto a, estoque b where b.qtdMinima > b.Quantidade AND a.Id = b.IdProduto AND b.Status = 1;";

            rs = stm.executeQuery(sql);
            boolean aux = true;
            while (rs.next()) {
                estoque = new Estoque();
                estoque.setIdProduto(rs.getInt("IdProduto"));
                estoque.setNomeProduto(rs.getString("Nome"));
                estoque.setQuantidade(rs.getInt("Quantidade"));
                estoque.setQtdMinima(rs.getInt("qtdMinima"));
                listEstoque.add(estoque);
                aux=false;
            }
            if(aux){
                estoque.setIdProduto(-1);
            }
            rs.close();
            stm.close();

        } catch (Exception e) {
            System.out.println("Erro ao consultar. [SQL] " + e.getMessage());
        } finally {
            conn.desconectar();
        }
        return listEstoque;

    }
}
