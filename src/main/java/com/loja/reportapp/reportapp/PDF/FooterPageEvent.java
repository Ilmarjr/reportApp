package com.loja.reportapp.reportapp.PDF;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FooterPageEvent extends PdfPageEventHelper {
    private final Font font =  new Font(Font.FontFamily.UNDEFINED, 12, Font.BOLD);
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
    private final Date currentDate = new Date();
    /*
     * Footer
     */
    @Override
    public void onEndPage(PdfWriter pdfWriter, Document document) {
        font.setColor(BaseColor.BLACK);
        Rectangle rect = pdfWriter.getBoxSize("rectangle");
        PdfContentByte cb = pdfWriter.getDirectContent();

        // BOTTOM LEFT
        ColumnText.showTextAligned(pdfWriter.getDirectContent(),
                Element.ALIGN_CENTER, new Phrase("Relatório de Vendas - " + simpleDateFormat.format(currentDate), font),
                rect.getLeft()+75, rect.getBottom(), 0);

        // BOTTOM RIGHT
        ColumnText.showTextAligned(pdfWriter.getDirectContent(),
                Element.ALIGN_CENTER, new Phrase(String.valueOf(document.getPageNumber()),font),
                rect.getRight(), rect.getBottom(), 0);

    }
}
