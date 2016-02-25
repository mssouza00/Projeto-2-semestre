/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.dmc.controller;

import br.com.dmc.dao.EstoqueDAO;
import br.com.dmc.model.Estoque;
import java.util.ArrayList;

/**
 *
 * @author web
 */
public class GerenciadorEstoque {

    public int cadastrarItemDeEstoque(int idProduto, int quantidade, int qtdMinima, int qtdMaxima) {
        // Setando dados em estoque
        Estoque estoque = new Estoque();
        estoque.setIdProduto(idProduto);
        estoque.setQuantidade(quantidade);
        estoque.setQtdMaxima(qtdMaxima);
        estoque.setQtdMinima(qtdMinima);
        // Cadastrando no Banco
        EstoqueDAO estoqueDAO = new EstoqueDAO();
        int retorno = estoqueDAO.cadastrarEstoque(estoque);
        return retorno;
    }
    
    public ArrayList<Estoque> consultarItemDeEstoque(int opcao, String nomeProduto){
        EstoqueDAO estoqueDAO = new EstoqueDAO();
        Estoque estoque = new Estoque();
        ArrayList<Estoque> tempProduto;
        tempProduto = estoqueDAO.consultaEstoque(opcao, nomeProduto,estoque);
        if(estoque.getIdProduto()== -1){
            tempProduto.add(estoque);
        }
        return tempProduto;
        
    }
    
    public int alterarItemDeEstoque(int idProduto, int quantidade, int qtdMinima, int qtdMaxima){
        EstoqueDAO estoqueDAO = new EstoqueDAO();
        Estoque estoque = new Estoque();
        int retorno = estoqueDAO.selecionaEstoque(idProduto, estoque);
        if(retorno == 0){
            return retorno;
        }
        
        if(quantidade != 0){
            estoque.setQuantidade(quantidade);
        }
        if(qtdMinima != 0){
            estoque.setQtdMinima(qtdMinima);
        }
        if(qtdMaxima != 0){
            estoque.setQtdMaxima(qtdMaxima);
        }
        retorno = estoqueDAO.alterarProduto(idProduto, estoque);
        
        return retorno;
    }
    
    public int desativarItemDeEstoque(int codigo,int opcao){
        EstoqueDAO estoqueDAO = new EstoqueDAO();
        int resultado = estoqueDAO.desativarItemDeEstoqueDAO(codigo,opcao);
        return resultado;
    }
}
