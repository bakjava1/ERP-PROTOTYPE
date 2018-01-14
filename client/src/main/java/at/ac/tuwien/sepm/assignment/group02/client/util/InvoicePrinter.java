package at.ac.tuwien.sepm.assignment.group02.client.util;

/**
 * Created by raquelsima on 07.01.18.
 */
import at.ac.tuwien.sepm.assignment.group02.client.entity.Order;
import at.ac.tuwien.sepm.assignment.group02.client.entity.Task;

import java.awt.geom.Rectangle2D;
import java.awt.print.*;
import java.awt.*;

public class InvoicePrinter implements Printable {

    private int lineHeight = 20;
    private int heightAfterItems = 0;


    public void setOrder(Order order) {

        this.order = order;
    }

    private Order order;

    @Override
    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {

        // We have only one page, and 'page'
        // is zero-based
        if (page > 0) {
            return NO_SUCH_PAGE;
        }

        // User (0,0) is typically outside the
        // imageable area, so we must translate
        // by the X and Y values in the PageFormat
        // to avoid clipping.
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        // Now we perform our rendering
        g.drawString("SmartHolz", 100, 100);
        g.drawString("Kunde: " + order.getCustomerName(), 100, 120);
        g.drawString("Adresse: " + order.getCustomerAddress(), 100, 140);
        g.drawString("UID: " + order.getCustomerUID(), 100, 160);
        g.drawString("Datum: " + order.getInvoiceDate(), 100, 180);
        g.drawString("Rechnung #", 200, 240);
        g.drawString("_____________________________________________________________", 100, 260);
        g.drawString("Menge |       Artikel             | Einzelpreis       | Steuer             |", 100, 280);
        int counter = 0;
        for (Task t : order.getTaskList()) {
            g.drawString("" + t.getQuantity(), 100, 300 + counter * lineHeight);
            g.drawString(t.getItemName(), 140, 300 + counter * lineHeight);
            g.drawString("" + t.getPrice(), 300, 300 + counter * lineHeight);
            g.drawString("20%", 350, 300 + counter * lineHeight);
            counter++;
        }
        heightAfterItems = 300 + order.getTaskAmount() * lineHeight;
        g.drawString("_____________________________________________________________", 100, heightAfterItems);
        g.drawString("Nettosumme: " + order.getNetAmount(), 200, heightAfterItems + 20);
        g.drawString("Umsatzsteuer: " + order.getTaxAmount(), 200, heightAfterItems + 40);
        g.drawString("Bruttosumme: " + order.getGrossAmount(), 200, heightAfterItems + 60);


        // tell the caller that this page is part
        // of the printed document

        return PAGE_EXISTS;
    }
}

    // Open PDF file
  /*  PDFDocument pdfDoc = new PDFDocument("C:\\test\\mydocument.pdf", null);

    // Get first page
    PDFPage page = pdfDoc.getPage(0);
   ViewerFX viewer = new ViewerFX(rootContainer, null);
   viewer.setupViewer();
    // Define the new crop box, which is a rectangle that is used to crop content
// before displaying or printing the page. This rectangle is in PDF native
// coordinates starting at the bottom left and increasing up to the right.
// The dimensions are given in PostScript points.
// 1 inch = 72 points, 1cm = 28.3465 points, 1mm = 2.8346 points
// width in points
    double crop_width = 200;
    // height in points
    double crop_height = 300;
    // bottom left corner coordinates in points
    double x_1 = 20;
    double y_1 = 20;
    Rectangle2D.Double area = new Rectangle2D.Double(x_1, y_1, crop_width, crop_height);

// set the new crop box
page.setCropBox(area);

// save the cropped document
pdfDoc.saveDocument("C:\\test\\mydocument_cropped.pdf");*/

