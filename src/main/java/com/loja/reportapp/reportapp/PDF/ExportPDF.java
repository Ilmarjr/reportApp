package com.loja.reportapp.reportapp.PDF;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.loja.reportapp.reportapp.controllers.ReportController;
import com.loja.reportapp.reportapp.dtos.ConsumoMedioClienteDTO;
import com.loja.reportapp.reportapp.dtos.ProdutoBaixoEstoqueDTO;
import com.loja.reportapp.reportapp.dtos.ProdutoMaisVendidoDTO;
import com.loja.reportapp.reportapp.dtos.ProdutoPorClienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Component
public class ExportPDF {

    @Autowired
    private ReportController reportController;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy, HH:mm:ss (z)", Locale.ENGLISH);

    public ByteArrayOutputStream generatePDF(int limite) {
        Document document = new Document();
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        Date currentDate = new Date();
        var pdfByteOutputStream = new ByteArrayOutputStream();

        try {
            // Provide the path to your PDF file
            document.setMargins(20, 20, 10, 20);
            PdfWriter pdfWriter = PdfWriter.getInstance(document, pdfByteOutputStream);
            Rectangle rectangle = new Rectangle(document.left(20), document.bottom(),
                    document.right(), document.top());
            pdfWriter.setBoxSize("rectangle", rectangle);
            pdfWriter.setPageEvent(new FooterPageEvent());
            // Set font
            Font customFont = new Font(Font.FontFamily.UNDEFINED, 18, Font.BOLD);// Use UNDEFINED to use the default font
            // Open the document for writing
            document.open();
            document.addTitle("Relatório de Vendas");
            // Add content to the PDF
            document.add(new Phrase("Relatório de Vendas - " + simpleDateFormat.format(currentDate).substring(0, 12), customFont));
            var phrase = new Paragraph();
            phrase.add(new Chunk("Relatório exportado em: ", new Font(Font.FontFamily.UNDEFINED, 10, Font.NORMAL, BaseColor.GRAY)));
            phrase.add(new Chunk(simpleDateFormat.format(currentDate).replace(":00", ""), new Font(Font.FontFamily.UNDEFINED, 10, Font.NORMAL)));
            document.add(phrase);
            document.add(new Paragraph("\n"));

            //Top Sold Table
            document.add(new Phrase("Produtos Mais Vendidos",
                    new Font(Font.FontFamily.UNDEFINED, 16, Font.BOLD)));
            document.add(new Paragraph(" "));
            PdfPTable topSoldTable = createTopSoldTable();
            if (topSoldTable != null)
                document.add(topSoldTable);
            document.add(new Paragraph(" "));

            //Products Low Storage Table
            document.add(new Phrase("Produtos Com baixo Estoque",
                    new Font(Font.FontFamily.UNDEFINED, 16, Font.BOLD)));
            document.add(new Paragraph(" "));
            PdfPTable productLowStorageTable = createProductLowStorageTable(limite);
            if (productLowStorageTable != null)
                document.add(productLowStorageTable);
            document.add(new Paragraph(" "));

            //Products Client Average Table
            document.add(new Phrase("Consumo Médio Por Cliente",
                    new Font(Font.FontFamily.UNDEFINED, 16, Font.BOLD)));
            document.add(new Paragraph(" "));
            PdfPTable averageTable = createAverageTable();
            if (averageTable != null)
                document.add(averageTable);
            document.add(new Paragraph(" "));

            //Products Client Average Table
            document.add(new Phrase("Produtos Por Cliente",
                    new Font(Font.FontFamily.UNDEFINED, 16, Font.BOLD)));
            document.add(new Paragraph(" "));
            PdfPTable productsByClientTable = createProductsByClientTable();
            if (productsByClientTable != null)
                document.add(productsByClientTable);
            document.add(new Paragraph(" "));

            // Close the document
            document.close();
            System.out.println("PDF created successfully.");
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return pdfByteOutputStream;
    }
    /**
     * This function creates a PDF table containing the top-selling products.
     * It retrieves the data from the reportController's getProdutosMaisVendidos method,
     * generates a table with the specified headers and widths, and populates it with the data.
     */
    private PdfPTable createTopSoldTable() throws DocumentException {

        Font topSearchFont = new Font(Font.FontFamily.UNDEFINED, 10);
        // Defining Headers
        String[] topSoldHeaders = new String[]{"", "Nome do Produto", "Quantidade Comprada"};
        int[] topSoldHeadersWidths = new int[]{30, 200, 200};
        // Getting Data
        var response = reportController.getProdutosMaisVendidos();
        List<ProdutoMaisVendidoDTO> data = Objects.requireNonNull(response.getBody());

        return populateTopSoldTable(generateTable(topSoldHeaders, topSoldHeadersWidths, topSearchFont), data);
    }


    private PdfPTable populateTopSoldTable(PdfPTable table, List<ProdutoMaisVendidoDTO> data) {
        try {
            Font font = new Font(Font.FontFamily.UNDEFINED, 7);
            int i = 0;
            for (ProdutoMaisVendidoDTO produto : data) {
                //Create Populated Cells
                PdfPCell index = new PdfPCell(new Phrase((i + 1) + ""));
                cellConfig(index, 50f);
                table.addCell(index);

                PdfPCell produtoName = new PdfPCell(new Phrase(produto.getProdutoName(), font));
                cellConfig(produtoName, 50f);
                table.addCell(produtoName);

                PdfPCell quantidade = new PdfPCell(new Phrase(produto.getQuantidadeVendida().toString(), font));
                cellConfig(quantidade, 50f);
                table.addCell(quantidade);
                i++;
            }
            return table;
        } catch (Exception ex) {
            return null;
        }
    }
    private PdfPTable createProductLowStorageTable(int limite) throws DocumentException {

        Font topSearchFont = new Font(Font.FontFamily.UNDEFINED, 10);
        // Defining Headers
        String[] topSoldHeaders = new String[]{"", "Nome do Produto", "Estoque"};
        int[] topSoldHeadersWidths = new int[]{30, 200, 200};
        // Getting Data
        var response = reportController.getProdutosBaixoEstoque(limite);
        List<ProdutoBaixoEstoqueDTO> data = Objects.requireNonNull(response.getBody());

        return populateProductsLowStorageTable(generateTable(topSoldHeaders, topSoldHeadersWidths, topSearchFont), data);
    }
    private PdfPTable populateProductsLowStorageTable(PdfPTable table, List<ProdutoBaixoEstoqueDTO> data) {
        try {
            Font font = new Font(Font.FontFamily.UNDEFINED, 7);
            int i = 0;
            for (ProdutoBaixoEstoqueDTO produto : data) {
                //Create Populated Cells
                PdfPCell index = new PdfPCell(new Phrase((i + 1) + ""));
                cellConfig(index, 50f);
                table.addCell(index);

                PdfPCell produtoName = new PdfPCell(new Phrase(produto.getProdutoName(), font));
                cellConfig(produtoName, 50f);
                table.addCell(produtoName);

                PdfPCell estoque = new PdfPCell(new Phrase(produto.getEstoque() + "", font));
                cellConfig(estoque, 50f);
                table.addCell(estoque);
                i++;
            }
            return table;
        } catch (Exception ex) {
            return null;
        }
    }
    private PdfPTable createAverageTable() throws DocumentException {

        Font topSearchFont = new Font(Font.FontFamily.UNDEFINED, 10);
        // Defining Headers
        String[] topSoldHeaders = new String[]{"", "Nome do Cliente", "Média de Consumo"};
        int[] topSoldHeadersWidths = new int[]{30, 200, 200};
        // Getting Data
        var response = reportController.getConsumoMedioPorCliente();
        List<ConsumoMedioClienteDTO> data = Objects.requireNonNull(response.getBody());

        return populateAverageTable(generateTable(topSoldHeaders, topSoldHeadersWidths, topSearchFont), data);
    }


    private PdfPTable populateAverageTable(PdfPTable table, List<ConsumoMedioClienteDTO> data) {
        try {
            Font font = new Font(Font.FontFamily.UNDEFINED, 7);
            int i = 0;
            for (ConsumoMedioClienteDTO cliente : data) {
                //Create Populated Cells
                PdfPCell index = new PdfPCell(new Phrase((i + 1) + ""));
                cellConfig(index, 50f);
                table.addCell(index);

                PdfPCell clienteName = new PdfPCell(new Phrase(cliente.getClienteName(), font));
                cellConfig(clienteName, 50f);
                table.addCell(clienteName);

                PdfPCell consumo = new PdfPCell(new Phrase(cliente.getConsumoMedio().toString(), font));
                cellConfig(consumo, 50f);
                table.addCell(consumo);
                i++;
            }
            return table;
        } catch (Exception ex) {
            return null;
        }
    }

    private PdfPTable createProductsByClientTable() throws DocumentException {

        Font topSearchFont = new Font(Font.FontFamily.UNDEFINED, 10);
        // Defining Headers
        String[] topSoldHeaders = new String[]{"", "Nome do Cliente", "Nome do Produto", "Quantidade Comprada"};
        int[] topSoldHeadersWidths = new int[]{30, 200, 200, 100};
        // Getting Data
        var response = reportController.getProdutosPorCliente(10);
        List<ProdutoPorClienteDTO> data = Objects.requireNonNull(response.getBody());

        return populateProductsByClientTable(generateTable(topSoldHeaders, topSoldHeadersWidths, topSearchFont), data);
    }


    private PdfPTable populateProductsByClientTable(PdfPTable table, List<ProdutoPorClienteDTO> data) {
        try {
            Font font = new Font(Font.FontFamily.UNDEFINED, 7);
            int i = 0;
            for (ProdutoPorClienteDTO produto : data) {
                //Create Populated Cells
                PdfPCell index = new PdfPCell(new Phrase((i + 1) + ""));
                cellConfig(index, 50f);
                table.addCell(index);

                PdfPCell clienteName = new PdfPCell(new Phrase(produto.getClienteName(), font));
                cellConfig(clienteName, 50f);
                table.addCell(clienteName);

                PdfPCell produtoName = new PdfPCell(new Phrase(produto.getProdutoName(), font));
                cellConfig(produtoName, 50f);
                table.addCell(produtoName);

                PdfPCell quantidade = new PdfPCell(new Phrase(produto.getQuantidadeComprada().toString(), font));
                cellConfig(quantidade, 50f);
                table.addCell(quantidade);
                i++;
            }
            return table;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * This function generates a PDF table with the given headers, widths, and font.
     */
    private PdfPTable generateTable(String[] headers, int[] widths, Font font) throws DocumentException {
        PdfPTable table = new PdfPTable(headers.length);
        table.setWidthPercentage(100);
        table.setWidths(widths);

        //Create table headers
        for (String header : headers) {
            PdfPCell column = new PdfPCell(new Phrase(header, font));
            column.setMinimumHeight(50f);
            column.setPaddingTop(15);
            column.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
            column.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(column);
        }
        return table;
    }

    private static void cellConfig(PdfPCell cell, float height) {
        cell.setMinimumHeight(height);
        cell.setBorder(Rectangle.BOTTOM);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    }


}
