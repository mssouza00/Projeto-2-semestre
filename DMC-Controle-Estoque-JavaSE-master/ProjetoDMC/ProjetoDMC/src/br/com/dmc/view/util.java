/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.dmc.view;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Grupo DMC
 */
public class util {

    protected int validarNumeroInt() {
        int numero = 0;
        Scanner input = new Scanner(System.in);
        boolean auxMenu = true;
        do {
            try {
                numero = input.nextInt();
                if (numero > 0) {
                    auxMenu = false;

                } else {
                    System.err.println("Digite um valor positivo!");
                }

            } catch (InputMismatchException e) {
                System.err.println("Tipo de dado incorreto, tente novamente:");
                input.nextLine();
            }
        } while (auxMenu);
        return numero;
    }

    protected float validarNumeroFloat() {
        float numero = 0;
        Scanner input = new Scanner(System.in);
        boolean auxMenu = true;
        do {
            try {
                numero = input.nextFloat();
                if (numero > 0) {
                    auxMenu = false;
                } else {
                    System.err.println("Digite um valor positivo!");
                }
            } catch (InputMismatchException e) {
                System.err.println("Tipo de dado incorreto, tente novamente:");
                input.nextLine();
            }
        } while (auxMenu);
        return numero;
    }
}
