/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.dmc.controller;

import br.com.dmc.dao.CarrinhoDAO;
import br.com.dmc.model.Carrinho;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author web
 */
public class GerenciadorCarrinho {

    public ArrayList<Carrinho> consultarItemDeEstoquePorNome(String nomeProduto) {
        CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
        Carrinho carrinho = new Carrinho();
        ArrayList<Carrinho> tempCarrinho;
        tempCarrinho = carrinhoDAO.consultarItemPorNome(nomeProduto, carrinho);
        if (carrinho.getIdProduto() == -1) {
            tempCarrinho.add(carrinho);
        }
        return tempCarrinho;
    }

    public ArrayList<Carrinho> listarTodosItemDeEstoque() {
        CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
        Carrinho carrinho = new Carrinho();
        ArrayList<Carrinho> tempCarrinho;
        tempCarrinho = carrinhoDAO.listarTodosItem(carrinho);
        if (carrinho.getIdProduto() == -1) {
            tempCarrinho.add(carrinho);
        }
        return tempCarrinho;
    }

    public ArrayList<Carrinho> listarTodosItemDeEstoqueEmPromocao() {
        CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
        Carrinho carrinho = new Carrinho();
        ArrayList<Carrinho> tempCarrinho;
        tempCarrinho = carrinhoDAO.listarTodosItemEmPromocao(carrinho);
        if (carrinho.getIdProduto() == -1) {
            tempCarrinho.add(carrinho);
        }
        return tempCarrinho;
    }

    public int cadastrarPedido(String nomeResponsavel, ArrayList<Carrinho> listCarrinho) {
        Random random = new Random();
        CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
        // Gerador de um numero int aleatório
        int resultado, idVenda = 0;
        boolean aux = true;
        while (aux) {
            idVenda = random.nextInt(Integer.MAX_VALUE);
            resultado = carrinhoDAO.verificaCodigoDeVenda(idVenda);
            if (resultado == 0) {
                aux = false;
            }
        }
        resultado = carrinhoDAO.cadastraPedido(listCarrinho, nomeResponsavel, idVenda);
        // Usa o return para tratamento
        if (resultado == 1) {
            Carrinho carrinho = new Carrinho();
            Relatorio relatorio = new Relatorio();
            listCarrinho = carrinhoDAO.consultarPedido(idVenda, carrinho);
            relatorio.gerarNotaDeCompra(listCarrinho);
            return idVenda;
        }
        return 0;
    }

    public ArrayList<Carrinho> consultarPedido(int codigoVenda) {
        CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
        ArrayList<Carrinho> listCarrinho;
        Carrinho carrinho = new Carrinho();
        // Faz a consulta e verifica se foi adicionado
        listCarrinho = carrinhoDAO.consultarPedido(codigoVenda, carrinho);
        if (carrinho.getIdProduto() == -1) {
            listCarrinho.add(carrinho);
        }
        return listCarrinho;
    }

    public int verificaQuantidade(int quantidade, int codigo) {
        CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
        int resultado = carrinhoDAO.verificaQuantidade(quantidade, codigo);
        return resultado;
    }

}
