/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.dmc.view;

import br.com.dmc.controller.Relatorio;
import java.util.Scanner;

/**
 *
 * @author joelsantos
 */
public class RelatorioVIEW {

    protected void gerarRelatorios() {
        util funcao = new util();
        Scanner input = new Scanner(System.in);
        int itemMenu = 0;
        while (itemMenu != 4) {
            System.out.println("");
            System.out.println("1- Gerar relatório de Itens em estoque"
                    + "\n2- Gerar relatórios de Itens desativados"
                    + "\n3- Gerar Relatório de Vendas"
                    + "\n4- Voltar");

            itemMenu = funcao.validarNumeroInt();
            Relatorio relatorio = new Relatorio();
            switch (itemMenu) {
                case 1:
                    relatorio.listarItens(1);
                    break;
                case 2:
                    relatorio.listarItens(2);
                    break;
                case 3:
                    relatorio.listarCarrinho();
                    break;
                case 4:
                    System.out.println("");
                    break;
                default:

            }
        }
    }

}
