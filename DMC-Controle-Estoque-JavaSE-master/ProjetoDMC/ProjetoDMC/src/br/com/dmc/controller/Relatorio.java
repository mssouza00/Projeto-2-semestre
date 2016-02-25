/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.dmc.controller;

import br.com.dmc.dao.CarrinhoDAO;
import br.com.dmc.dao.EstoqueDAO;
import br.com.dmc.dao.ProdutoEstoqueDAO;
import br.com.dmc.model.Carrinho;
import br.com.dmc.model.ProdutoEstoque;
import br.com.dmc.model.Estoque;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controla e passa as informações para as classes responsáveis
 *
 * @author Grupo DMC
 *
 * @version 1
 *
 * @since 16/11/2015
 *
 */
public class Relatorio {
    /**
     * Verifica se ja existe o código de venda em pedidos antigos
     * 
     * @return listEstoque ArrayList - Passa uma lista de Estoque
     */
    public ArrayList<Estoque> emitirAlerta() {
        ArrayList<Estoque> listEstoque;
        EstoqueDAO estoqueDAO = new EstoqueDAO();
        Estoque estoque = new Estoque();
        listEstoque = estoqueDAO.emitirAlerta(estoque);
        if (estoque.getIdProduto() == -1) {
            listEstoque.add(estoque);
        }
        return listEstoque;
    }
    /**
     * Responsável por gerar a nota de compra para o usuário
     *
     * @param listCarrinho ArrayList - Recebe um lista com o pedido realizado
     *
     */
    public void gerarNotaDeCompra(ArrayList<Carrinho> listCarrinho) {
        // Criamos um documento pdf vazio
        Document documentoPDF = new Document();
        try {
            // cria uma instancia e da o nome do pdf
            PdfWriter.getInstance(documentoPDF, new FileOutputStream("" + listCarrinho.get(0).getIdVenda() + ".pdf"));
            // abrir o documento
            documentoPDF.open();
            // setar o tamanho da pagina
            documentoPDF.setPageSize(PageSize.A4);
            // CORPO DO PDF
            Font f = new Font(Font.FontFamily.COURIER, 24, Font.BOLD);
            float total = 0;
            documentoPDF.add(new Paragraph("NOTA FISCAL\n", f));
            documentoPDF.add(new Paragraph("Reponsável: " + listCarrinho.get(0).getResponsavel()));
            documentoPDF.add(new Paragraph("Código: " + listCarrinho.get(0).getIdVenda()));
            documentoPDF.add(new Paragraph("Dia: " + listCarrinho.get(0).getSaida() + "\n"));
            documentoPDF.add(new Paragraph("\nProdutos comprados:\n"));

            for (int i = 0; i < listCarrinho.size(); i++) {
                documentoPDF.add(new Paragraph(listCarrinho.get(i).getNomeProduto()
                        + " | " + listCarrinho.get(i).getDescricao()
                        + " | Quantidade: " + listCarrinho.get(i).getQuantidade()
                        + " | Valor: " + listCarrinho.get(i).getValor()
                        + " | Total: " + listCarrinho.get(i).getTotal() + "\n"));
                total += listCarrinho.get(i).getTotal();
            }
            documentoPDF.add(new Paragraph("\nValor total gasto: R$ " + total));

            documentoPDF.addTitle("Nota fiscal - " + listCarrinho.get(0).getIdVenda());
            documentoPDF.addSubject("Nota fiscal gerada a partir do Controle DMC");
            documentoPDF.addKeywords("www.dmc.com.br");
            documentoPDF.addCreator("iText");
            documentoPDF.addAuthor("GRUPO DMC");
        } catch (DocumentException | IOException ex) {
            System.out.println("Erro ao gerar relatório " + ex);
        } finally {
            documentoPDF.close();
            try {
                java.awt.Desktop.getDesktop().open(new File("" + listCarrinho.get(0).getIdVenda() + ".pdf"));
            } catch (IOException ex) {
                Logger.getLogger(Relatorio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    /**
     * Responsavel por realizar a busca de itens em estoque
     *
     * @param opcao int - Auxilia no tipo de instrução a executar
     *
     */
    public void listarItens(int opcao) {
        ProdutoEstoque produtoEstoque = new ProdutoEstoque();
        ProdutoEstoqueDAO produtoEstoqueDAO = new ProdutoEstoqueDAO();
        ArrayList<ProdutoEstoque> listProdutoEstoque;
        listProdutoEstoque = produtoEstoqueDAO.ListaEstoque(produtoEstoque, opcao);
        gerarRelatorioDeItensEmEstoque(listProdutoEstoque, opcao);
    }
    /**
     * Verifica se ja existe o código de venda em pedidos antigos
     *
     * @param produtoEstoque ArrayList - ArrayList de Produtos em Estoque
     * @param opcao int - Auxilia no tipo de instrução a executar
     * 
     */
    public void gerarRelatorioDeItensEmEstoque(ArrayList<ProdutoEstoque> produtoEstoque, int opcao) {
        // Criamos um documento pdf vazio
        Document documentoPDF = new Document();
        try {
            // cria uma instancia e da o nome do pdf
            if (opcao == 1) {
                PdfWriter.getInstance(documentoPDF, new FileOutputStream("relatorioProdutoEstoque.pdf"));
            } else {
                PdfWriter.getInstance(documentoPDF, new FileOutputStream("relatorioProdutoEstoqueDesativos.pdf"));
            }
            // abrir o documento
            documentoPDF.open();
            // setar o tamanho da pagina
            documentoPDF.setPageSize(PageSize.A4);
            // CORPO DO PDF
            Font f = new Font(Font.FontFamily.COURIER, 24, Font.BOLD);
            Font p = new Font(Font.FontFamily.COURIER, Font.BOLD);
            float total = 0;
            if (opcao == 1) {
                documentoPDF.add(new Paragraph("Relatorio de itens em estoque\n\n", f));
            } else {
                documentoPDF.add(new Paragraph("Relatorio de itens desativados em estoque\n\n", f));
            }
            for (int i = 0; i < produtoEstoque.size(); i++) {
                if (opcao == 1) {
                    documentoPDF.add(new Paragraph(produtoEstoque.get(i).getId()
                            + " | " + produtoEstoque.get(i).getNome()
                            + " | " + produtoEstoque.get(i).getDescricao()
                            + " | Marca: " + produtoEstoque.get(i).getMarca() + "\n"
                            + "Valor: " + produtoEstoque.get(i).getValor()
                            + " | Quantidade: " + produtoEstoque.get(i).getQuantidade()
                            + " | Mínima: " + produtoEstoque.get(i).getQtdMinima()
                            + " | Máxima: " + produtoEstoque.get(i).getQtdMaxima()
                            + "\nData de cadastro: " + produtoEstoque.get(i).getEntrada() + "\n\n"
                    ));
                } else {
                    documentoPDF.add(new Paragraph(produtoEstoque.get(i).getId()
                            + " | " + produtoEstoque.get(i).getNome()
                            + " | " + produtoEstoque.get(i).getDescricao()
                            + " | Marca: " + produtoEstoque.get(i).getMarca() + "\n"
                            + "Valor: " + produtoEstoque.get(i).getValor()
                            + " | Quantidade: " + produtoEstoque.get(i).getQuantidade()
                            + " | Mínima: " + produtoEstoque.get(i).getQtdMinima()
                            + " | Máxima: " + produtoEstoque.get(i).getQtdMaxima() + "\n\n"));
                }
            }
            documentoPDF.addTitle("relatorio de produtos em estoque");
            documentoPDF.addKeywords("www.dmc.com.br");
            documentoPDF.addCreator("iText");
            documentoPDF.addAuthor("GRUPO DMC");
        } catch (DocumentException | IOException ex) {
            System.out.println("Erro ao gerar relatório " + ex);
        } finally {
            documentoPDF.close();
            try {
                if (opcao == 1) {
                    java.awt.Desktop.getDesktop().open(new File("relatorioProdutoEstoque.pdf"));
                } else {
                    java.awt.Desktop.getDesktop().open(new File("relatorioProdutoEstoqueDesativos.pdf"));
                }
            } catch (IOException ex) {
                Logger.getLogger(Relatorio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    /**
     * Responsavel por realizar a busca de pedidos realizados
     */
    public void listarCarrinho (){
        Carrinho carrinho = new Carrinho();
        CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
        ArrayList<Carrinho> listCarrinho;
        listCarrinho = carrinhoDAO.listarTodosOsPedidos(carrinho);
        gerarRelatorioDePedidos(listCarrinho);
    }
    /**
     * Responsável por gerar o relatório de Pedidos
     *
     * @param listCarrinho ArrayList - ArrayList de pedidos
     * 
     */
    public void gerarRelatorioDePedidos(ArrayList<Carrinho> listCarrinho) {
        // Criamos um documento pdf vazio
        Document documentoPDF = new Document();
        try {
            // cria uma instancia e da o nome do pdf
            PdfWriter.getInstance(documentoPDF, new FileOutputStream("vendidos.pdf"));
            // abrir o documento
            documentoPDF.open();
            // setar o tamanho da pagina
            documentoPDF.setPageSize(PageSize.A4);
            // CORPO DO PDF
            Font f = new Font(Font.FontFamily.COURIER, 24, Font.BOLD);
            float total = 0;
            documentoPDF.add(new Paragraph("Relatorio de vendas\n", f));

            for (int i = 0; i < listCarrinho.size(); i++) {
                documentoPDF.add(new Paragraph("Reponsável: " + listCarrinho.get(0).getResponsavel()));
                documentoPDF.add(new Paragraph("Código: " + listCarrinho.get(0).getIdVenda()));
                documentoPDF.add(new Paragraph("Dia: " + listCarrinho.get(0).getSaida() + "\n"));
                documentoPDF.add(new Paragraph("\nProdutos comprados:\n"));

                documentoPDF.add(new Paragraph(listCarrinho.get(i).getNomeProduto()
                        + " | " + listCarrinho.get(i).getDescricao()
                        + " | Quantidade: " + listCarrinho.get(i).getQuantidade()
                        + " | Valor: " + listCarrinho.get(i).getValor()
                        + " | Total: " + listCarrinho.get(i).getTotal() + "\n"));
                total += listCarrinho.get(i).getTotal();

                documentoPDF.add(new Paragraph("\nValor total gasto: R$ " + total));
                documentoPDF.add(new Paragraph("\n______________________________"));
            }
            documentoPDF.addTitle("relatorio de produtos em estoque");
            documentoPDF.addKeywords("www.dmc.com.br");
            documentoPDF.addCreator("iText");
            documentoPDF.addAuthor("GRUPO DMC");
        } catch (DocumentException | IOException ex) {
            System.out.println("Erro ao gerar relatório " + ex);
        } finally {
            documentoPDF.close();
            try {
                java.awt.Desktop.getDesktop().open(new File("vendidos.pdf"));
            } catch (IOException ex) {
                Logger.getLogger(Relatorio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
