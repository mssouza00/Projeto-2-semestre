/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.dmc.controller;

import br.com.dmc.dao.ProdutoDAO;
import br.com.dmc.model.Produto;
import java.util.ArrayList;

/**
 *
 * @author web
 */
public class GerenciadorProduto {

    public int cadastrarProduto(String nome, String descricao, String marca, float valor) {
        // criando produto
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setMarca(marca);
        produto.setValor(valor);
        // cadastrar produto no banco
        ProdutoDAO cadastraProduto = new ProdutoDAO();
        int cadastro = cadastraProduto.cadastrarProduto(produto);
        return cadastro;
    }

    public ArrayList<Produto> consultarProduto(String nome) {
        ProdutoDAO consultar = new ProdutoDAO();
        Produto produto = new Produto();
        ArrayList<Produto> tempProduto;
        tempProduto = consultar.consultarProduto(nome, 1, produto);
        if(produto.getId() == -1){
            tempProduto.add(produto);
        }
        return tempProduto;
    }

    public ArrayList<Produto> listarTodosProduto() {
        ProdutoDAO consultar = new ProdutoDAO();
        ArrayList<Produto> tempProduto;
        Produto produto = new Produto();
        tempProduto = consultar.consultarProduto("", 2, produto);
        if(produto.getId() == -1){
            tempProduto.add(produto);
        }
        return tempProduto;
    }

    public int alterarProduto(int codigo, String nome, String descricao, String marca, float valor) {
        Produto produto = new Produto();
        // cadastrar produto no banco
        ProdutoDAO altera = new ProdutoDAO();
        int retorno = altera.selecionaUnidade(codigo, produto);

        if (retorno == 0) {
            return retorno;
        }

        if (!" ".equals(nome)) {
            produto.setNome(nome);
        }
        if (!" ".equals(descricao)) {
            produto.setDescricao(descricao);
        }
        if (!" ".equals(marca)) {
            produto.setMarca(marca);
        }
        if (valor != 0) {
            produto.setValor(valor);
        }

        retorno = altera.alterarProduto(codigo, produto);
        return retorno;
    }
}
