/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.dmc.view;

import br.com.dmc.controller.GerenciadorCarrinho;
import br.com.dmc.model.Carrinho;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author joel.osantos
 */
public class CarrinhoVIEW {

    protected void realizarPedido() {
        GerenciadorCarrinho carrinho = new GerenciadorCarrinho();
        Scanner input = new Scanner(System.in);

        ArrayList<Carrinho> listResultado = new ArrayList<>();
        ArrayList<Carrinho> listCarrinho = new ArrayList<>();
        int itemMenu = 0;
        int codigo, resultado, aux2=0;
        boolean aux = true;
        String nomeProduto;

        while (itemMenu != 3) {
            System.out.println("");
            System.out.println("1- Efetuar compras"
                    + "\n2- Consulta de pedidos"
                    + "\n3- Voltar");
            util funcao = new util();
            itemMenu = funcao.validarNumeroInt();
            switch (itemMenu) {
                case 1:
                    itemMenu = 0;
                    while (itemMenu != 4) {
                        System.out.println("");
                        System.out.println("1- Adicionar itens no carrinho"
                                + "\n2- Listar itens no carrinho"
                                + "\n3- Concluir a compra"
                                + "\n4- Cancelar a compra");
                        itemMenu = funcao.validarNumeroInt();
                        switch (itemMenu) {
                            case 1:
                                System.out.println("1- Buscar item pelo nome"
                                        + "\n2- Listar todos"
                                        + "\n3- Listar itens em promoção");
                                itemMenu = funcao.validarNumeroInt();
                                switch (itemMenu) {
                                    case 1:
                                        System.out.println("Digite o nome do produto:");
                                        nomeProduto = input.nextLine();
                                        listResultado = carrinho.consultarItemDeEstoquePorNome(nomeProduto);
                                        if (listResultado.get(0).getIdProduto() != -1) {
                                            for (int i = 0; i < listResultado.size(); i++) {
                                                System.out.print("Código: " + listResultado.get(i).getIdProduto()
                                                        + " | " + listResultado.get(i).getNomeProduto()
                                                        + " | " + listResultado.get(i).getDescricao()
                                                        + " | Estoque: " + listResultado.get(i).getQuantidade()
                                                        + " | R$ " + listResultado.get(i).getValor() + "\n");
                                            }
                                        } else {
                                            System.err.println("Item não encontrado!");
                                            aux2 = 1;
                                        }
                                        break;
                                    case 2:
                                        listResultado = carrinho.listarTodosItemDeEstoque();
                                        if (listResultado.get(0).getIdProduto() != -1) {
                                            for (int i = 0; i < listResultado.size(); i++) {
                                                System.out.print("Código: " + listResultado.get(i).getIdProduto()
                                                        + " | " + listResultado.get(i).getNomeProduto()
                                                        + " | " + listResultado.get(i).getDescricao()
                                                        + " | Estoque: " + listResultado.get(i).getQuantidade()
                                                        + " | R$ " + listResultado.get(i).getValor() + "\n");
                                            }
                                        } else {
                                            System.err.println("Item não encontrado!");
                                            aux2 = 1;
                                        }
                                        break;
                                    case 3:
                                        listResultado = carrinho.listarTodosItemDeEstoqueEmPromocao();
                                        if (listResultado.get(0).getIdProduto() != -1) {
                                            for (int i = 0; i < listResultado.size(); i++) {
                                                System.out.print("Código: " + listResultado.get(i).getIdProduto()
                                                        + " | " + listResultado.get(i).getNomeProduto()
                                                        + " | " + listResultado.get(i).getDescricao()
                                                        + " | Estoque: " + listResultado.get(i).getQuantidade()
                                                        + " | R$ " + listResultado.get(i).getValor() + "\n");
                                                aux2=3;
                                            }
                                        } else {
                                            System.err.println("Item não encontrado!");
                                            aux2 = 1;
                                        }
                                        break;
                                    default:
                                        System.err.println("Opção inválida, tente novamente!");
                                }
                                if (aux2 == 1) {
                                    aux2=0;
                                    break;
                                }
                                System.out.println("Digite o código do produto:");
                                codigo = funcao.validarNumeroInt();
                                for (int i = 0; i < listResultado.size(); i++) {
                                    if (listResultado.get(i).getIdProduto() == codigo) {
                                        System.out.println("Quantidade a comprar:");
                                        listResultado.get(i).setQuantidade(funcao.validarNumeroInt());
                                        resultado = carrinho.verificaQuantidade(listResultado.get(i).getQuantidade(), codigo);
                                        if (resultado == 0) {
                                            System.err.println("Quantidade escolhida maior que estoque atual!");
                                            break;
                                        }
                                        float valor=0;
                                        if(aux2==3){
                                            System.out.println("Digite o valor de desconto");
                                            valor = listResultado.get(i).getValor();
                                            valor -= funcao.validarNumeroFloat();
                                        }else{
                                            valor = listResultado.get(i).getValor();
                                        }
                                        listResultado.get(i).setTotal(valor * listResultado.get(i).getQuantidade());
                                        System.out.println("Valor total: R$ " + listResultado.get(i).getTotal());
                                        listCarrinho.add(listResultado.get(i));
                                        aux = false;
                                    }
                                }
                                itemMenu = 0;
                                break;
                            case 2:
                                if (aux == false) {
                                    for (int i = 0; i < listCarrinho.size(); i++) {
                                        System.out.print("Código: " + listCarrinho.get(i).getIdProduto()
                                                + " | " + listCarrinho.get(i).getNomeProduto()
                                                + " | " + listCarrinho.get(i).getDescricao()
                                                + " | Quantidade: " + listCarrinho.get(i).getQuantidade()
                                                + " | Total: R$ " + listCarrinho.get(i).getTotal() + "\n");
                                    }
                                } else {
                                    System.err.println("Nenhum item no carrinho!");
                                }
                                break;
                            case 3:
                                if (aux == false) {
                                    System.out.println("Digite seu nome: (Responsável pela venda)");
                                    String nomeResponsavel = input.nextLine();
                                    resultado = carrinho.cadastrarPedido(nomeResponsavel, listCarrinho);
                                    if (resultado != 0) {
                                        System.out.println("Código do pedido gerado: " + resultado);
                                        System.out.println("Nota fiscal de garantia gerada!");
                                        itemMenu=4;
                                    }
                                } else {
                                    System.err.println("Nenhum item no carrinho!");
                                }
                                break;
                            case 4:
                                System.out.println("");
                                break;
                            default:
                                System.err.println("Opção inválida, tente novamente!");
                        }

                    }
                    itemMenu = 0;
                    break;
                case 2:
                    System.out.println("Digite o código de venda:");
                    codigo = funcao.validarNumeroInt();
                    listResultado = carrinho.consultarPedido(codigo);
                    if (listResultado.get(0).getIdProduto() != -1) {
                        float total = 0;
                        System.out.println("\nDetalhes do pedido"
                                + "\nReponsável: " + listResultado.get(0).getResponsavel()
                                + "\nCódigo: " + listResultado.get(0).getIdVenda()
                                + "\nDia: " + listResultado.get(0).getSaida() + "\n"
                                + "\nProdutos comprados:\n");
                        for (int i = 0; i < listResultado.size(); i++) {
                            System.out.print(listResultado.get(i).getNomeProduto()
                                    + " | " + listResultado.get(i).getDescricao()
                                    + " | Quantidade: " + listResultado.get(i).getQuantidade()
                                    + " | Valor: " + listResultado.get(i).getValor()
                                    + " | Total: " + listResultado.get(i).getTotal() + "\n");
                            total += listResultado.get(i).getTotal();
                        }
                        System.out.println("\nValor total gasto:| R$ " + total + " |");
                    } else {
                        System.err.println("Produto não encontrado!");
                    }
                    break;
                case 3:
                    System.out.println("");
                    break;
                default:
                    System.err.println("Opção inválida, tente novamente!");

            }
        }
    }
}
