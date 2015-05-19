import java.awt.print.PrinterException;
import java.io.IOException;

import com.gnostice.core.XDocException;
import com.gnostice.documents.controls.swing.printer.DocumentPrinter;


public class PrintDialogDemo
{

    public static void main(String[] args) throws XDocException, PrinterException
    {
        try
        {
            //////////////////////////////////////////////////
            //Printing a document through Custom PrintDialog//
            /////////////////////////////////////////////////
            
            DocumentPrinter documentPrinter = new DocumentPrinter();
            
            //Load the document with its password
            documentPrinter.loadDocument(args[0], "");
            
            // Opens the custom print dialog box where Printer, page range and number of copies can be specified.
            documentPrinter.showPrintDialog();
            
            /*
            //Other PrintDialogs
            documentPrinter.showNativePrintDialog();
            
            documentPrinter.showCrossPlatformPrintDialog();
            
            documentPrinter.showPrintDialog(frame, modal);
            
            documentPrinter.showPrintDialog(dialog, modal);
            */
            
            //Close the Document
            documentPrinter.closeDocument();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ArrayIndexOutOfBoundsException ex)
        {
            System.out.println("Usage : java PrintDialogDemo "
                + "<input file path>");
        }
        
    }

}
