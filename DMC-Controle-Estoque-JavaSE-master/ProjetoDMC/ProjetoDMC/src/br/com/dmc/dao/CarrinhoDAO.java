package br.com.dmc.dao;

import br.com.dmc.model.Carrinho;
import br.com.dmc.model.ConexaoDB;
import java.sql.*;
import java.util.ArrayList;

/**
 * Persistência de dados de Carrinho
 *
 * @author Grupo DMC
 *
 * @version 1
 *
 * @since 20/11/2015
 *
 */
public class CarrinhoDAO {
    /**
     * Verifica se ja existe o código de venda em pedidos antigos
     *
     * @param codigo int - Código de Venda para realizar verificação
     *
     * @return aux int - Auxilia na mensagem de retorno ao usuário
     */
    public int verificaCodigoDeVenda(int codigo) {
        ConexaoDB conn = new ConexaoDB();
        Connection conexao = conn.conectar();
        Statement stm;
        ResultSet rs;
        int aux = 0;
        try {
            stm = conexao.createStatement();
            String sql = "select IdVenda from Carrinho where IdVenda= " + codigo + ";";
            rs = stm.executeQuery(sql);
            if (rs.next()) {
                aux = rs.getInt("IdVenda");
                if (aux == codigo) {
                    return 1;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar Código de venda");
        } finally {
            conn.desconectar();
        }
        return aux;
    }
    /**
     * Verifica se a quantidade escolhida pelo usuário é maior doque a quantidade de estoque
     *
     * @param quantidade int - Quantidade Escolhida pelo usuário no pedido
     * @param codigo int - Código de Venda para realizar verificação
     *
     * @return aux int - Auxilia na mensagem de retorno ao usuário
     */
    public int verificaQuantidade(int quantidade, int codigo) {
        ConexaoDB conn = new ConexaoDB();
        Connection conexao = conn.conectar();
        Statement stm;
        ResultSet rs;
        int aux = 0;
        try {
            stm = conexao.createStatement();
            String sql = "Select (quantidade - " + quantidade + " ) >= 0 as valido from estoque where idproduto = " + codigo + ";";
            rs = stm.executeQuery(sql);
            if (rs.next()) {
                aux = rs.getInt("valido");
            }
        } catch (SQLException e) {
            System.out.println("Erro " + e);
        } finally {
            conn.desconectar();
        }

        return aux;
    }
    /**
     * Realiza o cadastro de pedido/vendas
     *
     * @param listCarrinho ArrayList - Recebe uma lista de Carrinho com os produtos escolhidos pelo usuário
     * @param nomeResponsavel String - Recebe o nome do responsável pela venda
     * @param codigoVenda int - Recebe o código da venda gerado automaticamente pelo sistema
     *
     * @return int - Auxilia na mensagem de retorno ao usuário
     */
    public int cadastraPedido(ArrayList<Carrinho> listCarrinho, String nomeResponsavel, int codigoVenda) {
        for (int i = 0; i < listCarrinho.size(); i++) {
            ConexaoDB conn = new ConexaoDB();
            Connection conexao = conn.conectar();

            String sql = "INSERT INTO Carrinho (IdVenda, Responsavel, IdProduto, Quantidade, Total, Saida) "
                    + "VALUES (?,?,?,?,?,date('now'));";
            try (PreparedStatement ps = conexao.prepareStatement(sql)) {
                ps.setInt(1, codigoVenda);
                ps.setString(2, nomeResponsavel);
                ps.setInt(3, listCarrinho.get(i).getIdProduto());
                ps.setInt(4, listCarrinho.get(i).getQuantidade());
                ps.setFloat(5, listCarrinho.get(i).getTotal());
                ps.execute();
                ps.close();
            } catch (SQLException e) {
                System.err.println("Erro ao registrar pedido! " + e);
            } finally {
                conn.desconectar();
            }
        }
        return 1;
    }
    /**
     * Realiza a busca de todos os pedidos realizados
     *
     * @param carrinho Carrinho - Referencia de uma instância de Carrinho para receber dados do Banco
     *
     * @return ArrayList - Retorna uma lista de carrinho com todos os pedidos consultados
     */
    public ArrayList<Carrinho> listarTodosOsPedidos(Carrinho carrinho) {
        ConexaoDB conn = new ConexaoDB();
        Connection conexao = conn.conectar();
        ArrayList<Carrinho> listCarrinho = new ArrayList<>();

        Statement stm;
        ResultSet rs;
        try {
            stm = conexao.createStatement();
            String sql = "SELECT a.IdVenda,a.Responsavel, b.IdProduto, c.Nome, c.Descricao, a.Quantidade, c.Valor, a.Total, a.Saida\n"
                    + "                    FROM Carrinho a, Estoque b, Produto c\n"
                    + "                    WHERE a.IdProduto = b.IdProduto and  a.idproduto=c.id ;";
            rs = stm.executeQuery(sql);
            boolean aux = true;
            while (rs.next()) {
                carrinho = new Carrinho();
                carrinho.setIdVenda(rs.getInt("IdVenda"));
                carrinho.setResponsavel(rs.getString("Responsavel"));
                carrinho.setIdProduto(rs.getInt("IdProduto"));
                carrinho.setNomeProduto(rs.getString("Nome"));
                carrinho.setDescricao(rs.getString("Descricao"));
                carrinho.setQuantidade(rs.getInt("Quantidade"));
                carrinho.setValor(rs.getFloat("Valor"));
                carrinho.setTotal(rs.getFloat("Total"));
                carrinho.setSaida(rs.getString("Saida"));
                listCarrinho.add(carrinho);
                aux = false;
            }
            stm.close();
            rs.close();
            if (aux) {
                carrinho.setIdProduto(-1);
            }
        } catch (Exception e) {
            System.err.println("Erro ao consultar! " + e);
        } finally {
            conn.desconectar();
        }
        return listCarrinho;
    }
    /**
     * Realiza consulta de pedidos através do código de venda
     *
     * @param carrinho Carrinho - Referencia de uma instância de Carrinho para receber dados do Banco
     * @param codigoVenda int - Código de venda fornecido pelo usuário
     *
     * @return ArrayList - Retorna uma lista de carrinho com o pedido consultado
     */
    public ArrayList<Carrinho> consultarPedido(int codigoVenda, Carrinho carrinho) {
        ConexaoDB conn = new ConexaoDB();
        Connection conexao = conn.conectar();
        ArrayList<Carrinho> listCarrinho = new ArrayList<>();
        Statement stm;
        ResultSet rs;
        try {
            stm = conexao.createStatement();
            String sql = "SELECT a.IdVenda,a.Responsavel, b.IdProduto, c.Nome, c.Descricao, a.Quantidade, c.Valor, a.Total, a.Saida\n"
                    + "FROM Carrinho a, Estoque b, Produto c\n"
                    + "WHERE \n"
                    + "    a.IdProduto = b.IdProduto and\n"
                    + "    b.IdProduto = c.Id and\n"
                    + "    a.IdVenda = " + codigoVenda + ";";
            rs = stm.executeQuery(sql);
            boolean aux = true;
            while (rs.next()) {
                carrinho = new Carrinho();
                carrinho.setIdVenda(rs.getInt("IdVenda"));
                carrinho.setResponsavel(rs.getString("Responsavel"));
                carrinho.setIdProduto(rs.getInt("IdProduto"));
                carrinho.setNomeProduto(rs.getString("Nome"));
                carrinho.setDescricao(rs.getString("Descricao"));
                carrinho.setQuantidade(rs.getInt("Quantidade"));
                carrinho.setValor(rs.getFloat("Valor"));
                carrinho.setTotal(rs.getFloat("Total"));
                carrinho.setSaida(rs.getString("Saida"));
                listCarrinho.add(carrinho);
                aux = false;
            }
            stm.close();
            rs.close();
            if (aux) {
                carrinho.setIdProduto(-1);
            }
        } catch (Exception e) {
            System.err.println("Erro ao consultar! " + e);
        } finally {
            conn.desconectar();
        }
        return listCarrinho;
    }
    /**
     * Realiza consulta de pedidos através do código de venda
     *
     * @param nomeProduto String - Consulta itens em estoque ativos
     * @param carrinho Carrinho - Referencia de uma instância de Carrinho para receber dados do Banco
     *
     * @return ArrayList - Retorna uma lista de carrinho com o pedido consultado
     */
    public ArrayList<Carrinho> consultarItemPorNome(String nomeProduto, Carrinho carrinho) {
        ArrayList<Carrinho> tempCarrinho = new ArrayList<>();
        ConexaoDB conn = new ConexaoDB();
        Connection conexao = conn.conectar();
        Statement stm;
        ResultSet rs;
        try {
            stm = conexao.createStatement();
            String sql = "SELECT b.IdProduto, a.Nome, a.Descricao,b.Quantidade,a.Valor\n"
                    + "                    FROM Produto a, Estoque b, Produto_Estoque c\n"
                    + "                    WHERE a.Id = b.IdProduto\n"
                    + "                    and a.Nome LIKE '" + nomeProduto + "%'\n"
                    + "                    and b.Status=1\n"
                    + "                    and b.IdProduto = c.IdEstoque\n"
                    + "                    and b.Quantidade > 0;";
            rs = stm.executeQuery(sql);
            boolean aux = true;
            while (rs.next()) {
                carrinho = new Carrinho();
                carrinho.setIdProduto(rs.getInt("IdProduto"));
                carrinho.setNomeProduto(rs.getString("Nome"));
                carrinho.setDescricao(rs.getString("Descricao"));
                carrinho.setQuantidade(rs.getInt("Quantidade"));
                carrinho.setValor(rs.getFloat("Valor"));
                tempCarrinho.add(carrinho);
                aux = false;
            }
            if (aux) {
                carrinho.setIdProduto(-1);
            }
        } catch (Exception e) {
            System.err.println("Erro ao consultar!");
        } finally {
            conn.desconectar();
        }
        return tempCarrinho;
    }
    /**
     * Realiza consulta de todos os itens ativos em estoque
     *
     * @param carrinho Carrinho - Referencia de uma instância de Carrinho para receber dados do Banco
     *
     * @return ArrayList - Retorna uma lista de carrinho com o pedido consultado
     */
    public ArrayList<Carrinho> listarTodosItem(Carrinho carrinho) {
        ArrayList<Carrinho> tempCarrinho = new ArrayList<>();
        ConexaoDB conn = new ConexaoDB();
        Connection conexao = conn.conectar();
        Statement stm;
        ResultSet rs;
        try {
            stm = conexao.createStatement();
            String sql = "SELECT b.IdProduto, a.Nome, a.Descricao,b.Quantidade,a.Valor\n"
                    + "                    FROM Produto a, Estoque b, Produto_Estoque c\n"
                    + "                    WHERE a.Id = b.IdProduto\n"
                    + "                    and b.Status=1\n"
                    + "                    and b.IdProduto = c.IdEstoque\n"
                    + "                    and b.Quantidade > 0;";
            rs = stm.executeQuery(sql);
            boolean aux = true;
            while (rs.next()) {
                carrinho = new Carrinho();
                carrinho.setIdProduto(rs.getInt("IdProduto"));
                carrinho.setNomeProduto(rs.getString("Nome"));
                carrinho.setDescricao(rs.getString("Descricao"));
                carrinho.setQuantidade(rs.getInt("Quantidade"));
                carrinho.setValor(rs.getFloat("Valor"));
                tempCarrinho.add(carrinho);
                aux = false;
            }
            if (aux) {
                carrinho.setIdProduto(-1);
            }
        } catch (Exception e) {
            System.err.println("Erro ao consultar!");
        } finally {
            conn.desconectar();
        }
        return tempCarrinho;
    }
    /**
     * Realiza consulta de todos os itens ativos em estoque e com quantidade acima da quantidade máxima de estoque
     *
     * @param carrinho Carrinho - Referencia de uma instância de Carrinho para receber dados do Banco
     *
     * @return ArrayList - Retorna uma lista de carrinho com o pedido consultado
     */
    public ArrayList<Carrinho> listarTodosItemEmPromocao(Carrinho carrinho) {
        ArrayList<Carrinho> tempCarrinho = new ArrayList<>();
        ConexaoDB conn = new ConexaoDB();
        Connection conexao = conn.conectar();
        Statement stm;
        ResultSet rs;
        try {
            stm = conexao.createStatement();
            String sql = "SELECT b.IdProduto, a.Nome, a.Descricao,b.Quantidade,a.Valor\n"
                    + "                    FROM Produto a, Estoque b, Produto_Estoque c\n"
                    + "                    WHERE a.Id = b.IdProduto\n"
                    + "                    and b.Status=1\n"
                    + "                    and b.IdProduto = c.IdEstoque\n"
                    + "                    and b.Quantidade > b.qtdMaxima;";
            rs = stm.executeQuery(sql);
            boolean aux = true;
            while (rs.next()) {
                carrinho = new Carrinho();
                carrinho.setIdProduto(rs.getInt("IdProduto"));
                carrinho.setNomeProduto(rs.getString("Nome"));
                carrinho.setDescricao(rs.getString("Descricao"));
                carrinho.setQuantidade(rs.getInt("Quantidade"));
                carrinho.setValor(rs.getFloat("Valor"));
                tempCarrinho.add(carrinho);
                aux = false;
            }
            if (aux) {
                carrinho.setIdProduto(-1);
            }
        } catch (Exception e) {
            System.err.println("Erro ao consultar!");
        } finally {
            conn.desconectar();
        }
        return tempCarrinho;
    }

}
