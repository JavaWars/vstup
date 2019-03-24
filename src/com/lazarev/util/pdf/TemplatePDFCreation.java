package com;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.FileOutputStream;
import java.io.IOException;

public class TemplatePDFCreation  {

    public static void createPdfByExample() throws IOException, DocumentException {
        PdfReader reader = new PdfReader("./example_data_field.pdf");
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("./out.pdf"));
        AcroFields form = stamper.getAcroFields();

        form.setField("data_label","label");
        form.setField("data_text_box","text_box");
        form.setField("data_formatted_field","data_formatted_field");
//        stamper.setFormFlattening(true);
        stamper.close();
    }

    public static void main(String[] args) throws IOException, DocumentException {
        TemplatePDFCreation.createPdfByExample();
    }
}
