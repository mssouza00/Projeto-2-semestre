/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.dmc.model;

/**
 *
 * @author Grupo DMC
 */
public class Estoque {
    
    private int idProduto;
    private String nomeProduto;
    private int quantidade;
    private int qtdMinima;
    private int qtdMaxima;
    private byte status;
    private String entrada;

    /**
     * @return the idProduto
     */
    public int getIdProduto() {
        return idProduto;
    }

    /**
     * @param idProduto the idProduto to set
     */
    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
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
     * @return the qtdMinima
     */
    public int getQtdMinima() {
        return qtdMinima;
    }

    /**
     * @param qtdMinima the qtdMinima to set
     */
    public void setQtdMinima(int qtdMinima) {
        this.qtdMinima = qtdMinima;
    }

    /**
     * @return the qtdMaxima
     */
    public int getQtdMaxima() {
        return qtdMaxima;
    }

    /**
     * @param qtdMaxima the qtdMaxima to set
     */
    public void setQtdMaxima(int qtdMaxima) {
        this.qtdMaxima = qtdMaxima;
    }

    /**
     * @return the status
     */
    public byte getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(byte status) {
        this.status = status;
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
     * @return the entrada
     */
    public String getEntrada() {
        return entrada;
    }

    /**
     * @param entrada the entrada to set
     */
    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }

    
}
