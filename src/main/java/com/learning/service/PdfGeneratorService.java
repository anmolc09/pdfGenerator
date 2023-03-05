package com.learning.service;

import com.learning.model.Invoice;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
@Slf4j
public class PdfGeneratorService {

    public byte[] createPdf(Invoice invoice) {

        log.info("creating pdf...");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, outputStream);

        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);

        Paragraph paragraph = new Paragraph("Invoice", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph);

        PdfPTable table1 = new PdfPTable(2);
        table1.setWidthPercentage(100f);
        table1.setWidths(new int[]{10, 10});
        table1.setSpacingBefore(20);

        String seller = "Seller:\n" + invoice.getSeller() + "\n" + invoice.getSellerAddress() + "\n GSTIN: "
                + invoice.getSellerGstin();
        table1.addCell(new Paragraph(seller));

        String buyer = "Buyer:\n" + invoice.getBuyer() + "\n" + invoice.getBuyerAddress() + "\n GSTIN: "
                + invoice.getBuyerGstin();
        table1.addCell(new Paragraph(buyer));

        document.add(table1);

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new int[]{3, 3, 3, 3});
        table.setSpacingBefore(5);

        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(CMYKColor.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setColor(CMYKColor.WHITE);

        cell.setPhrase(new Phrase("Item", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Quantity", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Rate", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Amount", font));
        table.addCell(cell);

        for (Invoice.Items items : invoice.getItems()) {
            table.addCell(String.valueOf(items.getName()));
            table.addCell(items.getQuantity());
            table.addCell(items.getRate().toString());
            table.addCell(items.getAmount().toString());
        }

        document.add(table);
        document.close();

        return outputStream.toByteArray();
    }
}
