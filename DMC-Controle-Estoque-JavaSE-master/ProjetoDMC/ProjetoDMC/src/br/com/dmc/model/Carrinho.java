/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.dmc.model;

import java.util.Date;

/**
 *
 * @author joelsantos
 */
public class Carrinho {

    private int IdVenda;
    private String Responsavel;
    private int IdProduto;
    private String nomeProduto;
    private String descricao;
    private int quantidade;
    private float valor;
    private float total;
    private String saida;

    /**
     * @return the IdVenda
     */
    public int getIdVenda() {
        return IdVenda;
    }

    /**
     * @param IdVenda the IdVenda to set
     */
    public void setIdVenda(int IdVenda) {
        this.IdVenda = IdVenda;
    }

    /**
     * @return the Responsavel
     */
    public String getResponsavel() {
        return Responsavel;
    }

    /**
     * @param Responsavel the Responsavel to set
     */
    public void setResponsavel(String Responsavel) {
        this.Responsavel = Responsavel;
    }

    /**
     * @return the IdProduto
     */
    public int getIdProduto() {
        return IdProduto;
    }

    /**
     * @param IdProduto the IdProduto to set
     */
    public void setIdProduto(int IdProduto) {
        this.IdProduto = IdProduto;
    }

    /**
     * @return the nomeProduto
     */
    public String getNomeProduto() {
        return nomeProduto;
    }

    /**
     * @param nomeProduto the nomeProduto to set
     */
    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return the quantidade
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * @param quantidade the quantidade to set
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * @return the valor
     */
    public float getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(float valor) {
        this.valor = valor;
    }

    /**
     * @return the total
     */
    public float getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(float total) {
        this.total = total;
    }

    /**
     * @return the saida
     */
    public String getSaida() {
        return saida;
    }

    /**
     * @param saida the saida to set
     */
    public void setSaida(String saida) {
        this.saida = saida;
    }
}
