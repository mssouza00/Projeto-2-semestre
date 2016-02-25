/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.dmc.model;

import java.sql.*;

/**
 *
 * @author Grupo DMC
 */
public class ConexaoDB {

    private final String driver = "org.sqlite.JDBC"; // Driver do BD
    private final String caminho = "jdbc:sqlite:Banco.db";
    public Connection conn; // Realizar conexão com o BD

    public Connection conectar() {
        try {
            Class.forName(driver); // Seta a propriedade do driver de conexao
            conn = DriverManager.getConnection(caminho); // realiza a conexão com o banco
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("Erro ao conectar!");
        }
        return conn;
    }

    public void desconectar() {
        try {
            conn.close(); // Fecha a conexao
        } catch (SQLException ex) {
            System.err.println("Erro ao desconectar!");
        }
    }
}
