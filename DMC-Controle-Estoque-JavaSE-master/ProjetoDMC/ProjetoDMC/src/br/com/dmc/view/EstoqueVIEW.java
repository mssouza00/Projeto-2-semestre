/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.dmc.view;

import br.com.dmc.controller.GerenciadorEstoque;
import br.com.dmc.controller.GerenciadorProduto;
import br.com.dmc.model.Estoque;
import br.com.dmc.model.Produto;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Grupo DMC
 */
public class EstoqueVIEW {

    protected void ManterEstoque() {
        GerenciadorEstoque estoque = new GerenciadorEstoque();
        GerenciadorProduto produto = new GerenciadorProduto();
        util funcao = new util();
        Scanner input = new Scanner(System.in);
        int itemMenu = 0;
        while (itemMenu != 6) {
            System.out.println("");
            System.out.println("1- Cadastrar Item de Estoque"
                    + "\n2- Alterar Item Estoque"
                    + "\n3- Consulta em Estoque"
                    + "\n4- Desativar um item de Estoque"
                    + "\n5- Ativar um item de Estoque"
                    + "\n6- Voltar");

            itemMenu = funcao.validarNumeroInt();

            int idProduto, quantidade, qtdMinima, qtdMaxima, resultado,auxSaida;
            String nomeProduto;
            ArrayList<Produto> listResultado;
            ArrayList<Estoque> listResultado2;

            switch (itemMenu) {
                // CADASTRA ITEM EM ESTOQUE
                case 1:
                    // Pesquisa o produto a cadastrar em estoque
                    System.out.println("\nNome do produto: ");
                    nomeProduto = input.nextLine();
                    listResultado = produto.consultarProduto(nomeProduto);
                    if (listResultado.get(0).getId() != -1) {
                        for (int i = 0; i < listResultado.size(); i++) {
                            System.out.print("Código: " + listResultado.get(i).getId()
                                    + " | " + listResultado.get(i).getNome()
                                    + " | " + listResultado.get(i).getDescricao()
                                    + " | " + listResultado.get(i).getMarca() + "\n");
                        }
                    } else {
                        System.err.println("Produto não encontrado!");
                        break;
                    }
                    System.out.println("\nCódigo do produto: ");
                    idProduto = funcao.validarNumeroInt();
                    System.out.println("Quantidade:");
                    quantidade = funcao.validarNumeroInt();
                    System.out.println("Quantidade minima:");
                    qtdMinima = funcao.validarNumeroInt();
                    System.out.println("Quantidade máxima:");
                    qtdMaxima = funcao.validarNumeroInt();
                    resultado = estoque.cadastrarItemDeEstoque(idProduto, quantidade, qtdMinima, qtdMaxima);
                    if (resultado == 1) {
                        System.out.println("Produto cadastrado!");
                    }
                    break;
                    // ALTERAR ITENS DE ESTOQUE
                case 2:
                    System.out.println("Nome do produto:");
                    nomeProduto = input.nextLine();
                    listResultado2 = estoque.consultarItemDeEstoque(1, nomeProduto);
                    if (listResultado2.get(0).getIdProduto() != -1) {
                        for (int i = 0; i < listResultado2.size(); i++) {
                            System.out.print("Código: " + listResultado2.get(i).getIdProduto()
                                    + " | " + listResultado2.get(i).getNomeProduto()
                                    + " | Quantidade: " + listResultado2.get(i).getQuantidade()
                                    + " | Minima: " + listResultado2.get(i).getQtdMinima()
                                    + " | Máxima: " + listResultado2.get(i).getQtdMaxima() + "\n");
                        }
                    } else {
                        System.err.println("Produto não encontrado!");
                        break;
                    }
                    System.out.println("\nDigite o código do produto:");
                    idProduto = funcao.validarNumeroInt();
                    int itemMenu2 = 0;
                    quantidade = 0;
                    qtdMaxima = 0;
                    qtdMinima = 0;
                    while (itemMenu2 != 4) {
                        System.out.println("\nEscolha oque você quer alterar"
                                + "\n1- Quantidade"
                                + "\n2- Quantidade minima"
                                + "\n3- Quantidade máxima"
                                + "\n4- Terminar\n");
                        itemMenu2 = input.nextInt();
                        switch (itemMenu2) {
                            case 1:
                                System.out.println("Digite a nova quantidade:");
                                quantidade = input.nextInt();
                                break;
                            case 2:
                                System.out.println("Digite a nova quantidade mínima:");
                                qtdMinima = input.nextInt();
                                break;
                            case 3:
                                System.out.println("Digite a nova quantidade máxima:");
                                qtdMaxima = input.nextInt();
                                break;
                            case 4:
                                System.out.println("Operação terminada!\n");
                                break;
                            default:
                                System.err.println("Opção inválida, tente novamente!");
                        }
                    }
                    resultado = estoque.alterarItemDeEstoque(idProduto, quantidade, qtdMinima, qtdMaxima);
                    if (resultado != 0) {
                        System.out.println("Produto alterado!");
                    }
                    break;
                    // CONSULTA EM ESTOQUE
                case 3:
                    System.out.println("\n1- Buscar por nome de produto\n"
                            + "2- Listar todos\n"
                            + "3- Listar itens desativados");
                    itemMenu2 = funcao.validarNumeroInt();
                    switch (itemMenu2) {
                        case 1:
                            System.out.println("Nome do produto: ");
                            nomeProduto = input.nextLine();
                            listResultado2 = estoque.consultarItemDeEstoque(1, nomeProduto);
                            if (listResultado2.get(0).getIdProduto() != -1) {
                                for (int i = 0; i < listResultado2.size(); i++) {
                                    System.out.print("Código: " + listResultado2.get(i).getIdProduto()
                                            + " | " + listResultado2.get(i).getNomeProduto()
                                            + " | Quantidade: " + listResultado2.get(i).getQuantidade()
                                            + " | Minima: " + listResultado2.get(i).getQtdMinima()
                                            + " | Máxima: " + listResultado2.get(i).getQtdMaxima() + "\n");
                                }
                            } else {
                                System.err.println("Item não encontrado!");
                            }
                            break;
                        case 2:
                            listResultado2 = estoque.consultarItemDeEstoque(0, "");
                            if (listResultado2.get(0).getIdProduto() != -1) {
                                for (int i = 0; i < listResultado2.size(); i++) {
                                    System.out.print("Código: " + listResultado2.get(i).getIdProduto()
                                            + " | " + listResultado2.get(i).getNomeProduto()
                                            + " | Quantidade: " + listResultado2.get(i).getQuantidade()
                                            + " | Minima: " + listResultado2.get(i).getQtdMinima()
                                            + " | Máxima: " + listResultado2.get(i).getQtdMaxima() + "\n");
                                }
                            } else {
                                System.err.println("Nenhum item encontrado!");
                            }
                            break;
                        case 3:
                            listResultado2 = estoque.consultarItemDeEstoque(2, "");
                            if (listResultado2.get(0).getIdProduto() != -1) {
                                for (int i = 0; i < listResultado2.size(); i++) {
                                    System.out.print("Código: " + listResultado2.get(i).getIdProduto()
                                            + " | " + listResultado2.get(i).getNomeProduto()
                                            + " | Quantidade: " + listResultado2.get(i).getQuantidade()
                                            + " | Minima: " + listResultado2.get(i).getQtdMinima()
                                            + " | Máxima: " + listResultado2.get(i).getQtdMaxima() + "\n");
                                }
                            } else {
                                System.err.println("Nenhum item encontrado!");
                            }
                            break;
                        default:
                            System.err.println("Opção inválida, tente novamente!");
                    }
                    break;
                    // DESATIVA ITEM DE ESTOQUE
                case 4:
                    System.out.println("Nome do produto:");
                    nomeProduto = input.nextLine();
                    listResultado2 = estoque.consultarItemDeEstoque(1, nomeProduto);
                    if (listResultado2.get(0).getIdProduto() != -1) {
                        for (int i = 0; i < listResultado2.size(); i++) {
                            System.out.print("Código: " + listResultado2.get(i).getIdProduto()
                                    + " | " + listResultado2.get(i).getNomeProduto()
                                    + " | Quantidade: " + listResultado2.get(i).getQuantidade()
                                    + " | Minima: " + listResultado2.get(i).getQtdMinima()
                                    + " | Máxima: " + listResultado2.get(i).getQtdMaxima() + "\n");
                        }
                    } else {
                        System.err.println("Item não encontrado!");
                        break;
                    }
                    System.out.println("\nDigite o código do produto:");
                    idProduto = funcao.validarNumeroInt();
                    resultado = estoque.desativarItemDeEstoque(idProduto,1);
                    if (resultado == 1){
                        System.out.println("\nProduto desativado!");
                    }
                    break;
                    // ATIVA ITEM DE ESTOQUE
                case 5:
                    listResultado2 = estoque.consultarItemDeEstoque(2, "");
                    if (listResultado2.get(0).getIdProduto() != -1) {
                        for (int i = 0; i < listResultado2.size(); i++) {
                            System.out.print("Código: " + listResultado2.get(i).getIdProduto()
                                    + " | " + listResultado2.get(i).getNomeProduto()
                                    + " | Quantidade: " + listResultado2.get(i).getQuantidade()
                                    + " | Minima: " + listResultado2.get(i).getQtdMinima()
                                    + " | Máxima: " + listResultado2.get(i).getQtdMaxima() + "\n");
                        }
                    } else {
                        System.err.println("Nenhum item encontrado!");
                        break;
                    }
                    System.out.println("\nDigite o código do produto:");
                    idProduto = funcao.validarNumeroInt();
                    resultado = estoque.desativarItemDeEstoque(idProduto,2);
                    if (resultado == 1){
                        System.out.println("\nItem ativado!");
                    }
                    System.out.println("");
                    break;
                case 6:
                    System.out.println("");
                    break;
                default:
                    System.err.println("Opção inválida, tente novamente!");
            }
        }
    }
}
