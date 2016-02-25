/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.dmc.view;

import br.com.dmc.controller.Relatorio;
import br.com.dmc.model.Estoque;
import java.util.ArrayList;

/**
 *
 * @author Grupo DMC
 */
public class Principal {

    public static void main(String[] args) {

        System.out.println("Bem vindo ao sistema!"
                + "\nControle de estoque DMC");
        int itemMenu = 0;
        while (itemMenu != 5) {
            util funcao = new util();
            Relatorio relatorio = new Relatorio();
            ArrayList<Estoque> listEstoque;
            listEstoque = relatorio.emitirAlerta();
            if (listEstoque.get(0).getIdProduto() != -1) {
                System.out.println("Você possui produtos com quantidade abaixo da quantidade mínima!");
                System.out.println("Deseja listar esses produtos?\n1-Sim\n2-Não");
                int opcao = funcao.validarNumeroInt();
                switch (opcao) {
                    case 1:
                        for (int i = 0; i < listEstoque.size(); i++) {
                            System.out.print("Código: " + listEstoque.get(i).getIdProduto()
                                    + " | " + listEstoque.get(i).getNomeProduto()
                                    + " | Quantidade: " + listEstoque.get(i).getQuantidade()
                                    + " | Minimo: " + listEstoque.get(i).getQtdMinima() + "\n");
                        }
                        System.out.println("");
                        break;
                    case 2:
                        System.out.println("");
                        break;
                    default:
                        System.err.println("Opção inválida, tente novamente!");
                }

            }
            System.out.println("Menu de navegação"
                    + "\n1- Gerenciar Produto"
                    + "\n2- Gerenciar Estoque"
                    + "\n3- Realizar Pedido"
                    + "\n4- Gerar Relatório"
                    + "\n5- Sair do sistema");
            itemMenu = funcao.validarNumeroInt();
            switch (itemMenu) {
                case 1:
                    ProdutoVIEW produto = new ProdutoVIEW();
                    produto.ManterProduto();
                    break;
                case 2:
                    EstoqueVIEW estoque = new EstoqueVIEW();
                    estoque.ManterEstoque();
                    break;
                case 3:
                    CarrinhoVIEW carrinho = new CarrinhoVIEW();
                    carrinho.realizarPedido();
                    break;
                case 4:
                    RelatorioVIEW relatorioView = new RelatorioVIEW();
                    relatorioView.gerarRelatorios();
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Opção inválida, tente novamente!");
            }
        }
    }

}
