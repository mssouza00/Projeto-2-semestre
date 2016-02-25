/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.dmc.view;

import br.com.dmc.controller.GerenciadorProduto;
import br.com.dmc.model.Produto;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Grupo DMC
 */
public class ProdutoVIEW {

    /**
     *
     */
    protected void ManterProduto() {
        GerenciadorProduto produto = new GerenciadorProduto();
        Scanner input = new Scanner(System.in);
        int itemMenu = 0;
        while (itemMenu != 4) {
            System.out.println("");
            System.out.println("1- Cadastrar Produto"
                    + "\n2- Alterar Produto"
                    + "\n3- Consultar Produto"
                    + "\n4- Voltar");

            util funcao = new util();
            itemMenu = funcao.validarNumeroInt();
            int codigo;
            int resultado;
            String nomeProduto;
            String descricao;
            String marca;
            float valor;
            ArrayList<Produto> listResultado;

            switch (itemMenu) {
                case 1:
                    // Esse bloco é responsável por cadastrar produtos
                    System.out.println("\nNome do produto: ");
                    nomeProduto = input.nextLine();
                    System.out.println("Descrição:");
                    descricao = input.nextLine();
                    System.out.println("Marca:");
                    marca = input.nextLine();
                    System.out.println("Valor:");
                    valor = funcao.validarNumeroFloat();
                    resultado = produto.cadastrarProduto(nomeProduto, descricao, marca, valor);
                    if (resultado == 1) {
                        System.out.println("Produto cadastrado!");
                    }
                    break;
                case 2:
                    // Esse bloco é responsável por consultar o produto para a alteração
                    System.out.println("\nQual produto você quer alterar? ");
                    nomeProduto = input.nextLine();
                    listResultado = produto.consultarProduto(nomeProduto);
                    if (listResultado.get(0).getId() != -1) {
                        for (int i = 0; i < listResultado.size(); i++) {
                            System.out.print(listResultado.get(i).getId()
                                    + " | " + listResultado.get(i).getNome()
                                    + " | " + listResultado.get(i).getDescricao()
                                    + " | " + listResultado.get(i).getMarca()
                                    + " | R$ " + listResultado.get(i).getValor() + "\n");
                        }
                    } else {
                        System.err.println("Produto não encontrado!");
                        break;
                    }
                    // Esse bloco é responsável por pegar os dados que vai alterar
                    System.out.println("\nDigite o código do produto:");
                    codigo = funcao.validarNumeroInt();
                    nomeProduto = " ";
                    descricao = " ";
                    marca = " ";
                    valor = 0;
                    int itemMenu2 = 0;
                    while (itemMenu2 != 5) {
                        System.out.println("\nEscolha oque você quer alterar"
                                + "\n1- Nome"
                                + "\n2- Descrição"
                                + "\n3- Marca"
                                + "\n4- Valor"
                                + "\n5- Terminar\n");
                        itemMenu2 = input.nextInt();
                        switch (itemMenu2) {
                            case 1:
                                System.out.println("Digite o Nome:");
                                input.nextLine();
                                nomeProduto = input.nextLine();
                                break;
                            case 2:
                                System.out.println("Digite a Descrição:");
                                input.nextLine();
                                descricao = input.nextLine();
                                break;
                            case 3:
                                System.out.println("Digite a Marca:");
                                input.nextLine();
                                marca = input.nextLine();
                                break;
                            case 4:
                                System.out.println("Digite o Valor:");
                                valor = funcao.validarNumeroFloat();
                                break;
                            case 5:
                                System.out.println("Operação terminada");
                                break;
                            default:
                                System.err.println("Opção inválida, tente novamente!");
                        }
                    }
                    resultado = produto.alterarProduto(codigo, nomeProduto, descricao, marca, valor);
                    if (resultado == 0) {
                        break;
                    } else {
                        System.out.println("Produto alterado!");
                    }
                    break;
                case 3:
                    System.out.println("\n1- Buscar por nome\n"
                            + "2- Listar todos");
                    itemMenu2 = funcao.validarNumeroInt();
                    switch (itemMenu2) {
                        case 1:
                            System.out.println("Nome do produto: ");
                            nomeProduto = input.nextLine();
                            listResultado = produto.consultarProduto(nomeProduto);
                            if (listResultado.get(0).getId() != -1) {
                                for (int i = 0; i < listResultado.size(); i++) {
                                    System.out.print(listResultado.get(i).getId()
                                            + " | " + listResultado.get(i).getNome()
                                            + " | " + listResultado.get(i).getDescricao()
                                            + " | " + listResultado.get(i).getMarca()
                                            + " | R$ " + listResultado.get(i).getValor() + "\n");
                                }
                            } else {
                                System.err.println("Produto não encontrado!");
                            }
                            break;
                        case 2:
                            listResultado = produto.listarTodosProduto();
                            if (listResultado.get(0).getId() != -1) {
                                for (int i = 0; i < listResultado.size(); i++) {
                                    System.out.print(listResultado.get(i).getId()
                                            + " | " + listResultado.get(i).getNome()
                                            + " | " + listResultado.get(i).getDescricao()
                                            + " | " + listResultado.get(i).getMarca()
                                            + " | R$ " + listResultado.get(i).getValor() + "\n");
                                }
                            } else {
                                System.err.println("Nenhum produto encontrado!");
                            }
                            break;
                        default:
                            System.err.println("Opção inválida, tente novamente!");
                    }
                    break;
                case 4:
                    System.out.println("");
                    break;
                default:
                    System.err.println("Opção inválida, tente novamente!");
            }
        }
    }
}
