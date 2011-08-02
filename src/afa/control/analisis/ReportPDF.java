/*
 *  Copyright (C) 2011 Alvaro Duran Tovar
 *
 *  This file is part of AFA.
 *
 *  AFA is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package afa.control.analisis;

import afa.model.*;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Certificate;
import java.util.Vector;

import java.security.KeyStore;
import java.security.PrivateKey;

/**
 *
 * @author Alvaro Duran Tovar
 */
class ReportPDF {

    private static String analistName;
    private static String signFile;
    private static Document document;
    private static ChapterAutoNumber chapter;
    private static Device device;
    private static Section s;
    private static PdfPTable table;
    private static Vector<Chapter> chapters = new Vector<Chapter>();

    
    static void setAnalistName(String name) {
        analistName = name;
    }

    static void setSignFile(String file) {
        signFile = file;
    }

    private static void addSignature(String fname) {
        //for test only
        setSignFile("name-cert.p12");
        //end for test

        try {
            // Creacion de un spacio para keys
            KeyStore ks = KeyStore.getInstance("pkcs12");
            // Cargar el fichero p12 con el certificado
            ks.load(new FileInputStream(signFile), signFile.toCharArray());
            String alias = (String) ks.aliases().nextElement();
            // Recuperacion de la clave privada
            PrivateKey key = (PrivateKey) ks.getKey(alias, signFile.toCharArray());
            // et de la chaine de certificats
            Certificate[] chain = (Certificate[]) ks.getCertificateChain(alias);

            // Lectura del documento origen
            PdfReader pdfReader = new PdfReader((new File(fname)).getAbsolutePath());
            File outputFile = new File("fichero_firmado.pdf");
            // Firma del documento
            PdfStamper pdfStamper;
            pdfStamper = PdfStamper.createSignature(pdfReader, null, '\0', outputFile);
            PdfSignatureAppearance sap = pdfStamper.getSignatureAppearance();
            sap.setCrypto(key,(java.security.cert.Certificate[]) chain, null, PdfSignatureAppearance.SELF_SIGNED);

            sap.setVisibleSignature(new Rectangle(10, 10, 50, 30), 1, "null");

            pdfStamper.setFormFlattening(true);
            pdfStamper.close();

        } catch (Exception key) {
            key.printStackTrace();
        }
    }


    static void createDocument(String file) throws Exception {
        //open
        document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();
        document.setPageCount(0);
        document.setFooter(new HeaderFooter(new Phrase("Pagina numero: "), true));
        document.add(Chunk.NEWLINE);
        document.newPage();
        initIndex();
        


        Vector<Device> devices = Analisis.getDevices();
        for (Device d : devices) {
            device = d;
            writeDevice(Analisis.getPlugins());
        }

        //add content
        addContent();
        //close
        document.close();
        //sign document
        //addSignature(file);
    }

    private static void addContent() throws DocumentException{
        document.add(table);

        for(Chapter c : chapters){
            document.add(c);
        }
    }

    private static void initIndex() throws DocumentException {
        table = new PdfPTable(1);
        table.setWidths(new int[]{500});
        PdfPCell cell = table.getDefaultCell();
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
    }

    private static void writeDevice(Vector<Category> vec) throws Exception {
        //write device
        //add to index
        Paragraph par = new Paragraph(new Chunk("Device: "+device.getDeviceName().getAbsolutePath()
                +" - "+device.getIdentifier()).setLocalDestination(device.getIdentifier()));
        table.addCell(new Phrase(new Chunk(par.getContent()).setLocalGoto(device.getIdentifier())));
        //end add to index
        
        chapter = new ChapterAutoNumber(par);

        for (Category c : vec) {
            writeCategory(c);
        }


        //add chapter
        chapters.add(chapter);
    }

    private static void writeCategory(Category cat) throws Exception {
        //write category
        Paragraph par = new Paragraph(cat.getName());
        //add to index
        table.addCell(par);
        //end add to index

        s = chapter.addSection(par);
        
        s.setIndentation(30.0f);
        for (Plugin p : cat.getPlugins()) {
            writePlugin(p);
        }
    }

    private static void writePlugin(Plugin plugin) {
        //write plugin
        Section aux = s;
        s = s.addSection(new Paragraph(plugin.getName()));
        s.setIndentation(30.0f);
        for (Technique t : plugin.getTechniques()) {
            writeTechnique(t);
        }
        s = aux;
    }

    private static void writeTechnique(Technique t) {
        //write technique
        s.add(new Paragraph("Technique type: "+t.getParam("type")));
        //writeResult(t.getResult(device));
    }

    private static void writeResult(Result r) {
        //write result
        s.add(new Paragraph(r.getValue()+" - "+r.isSuccess()));
    }
}

