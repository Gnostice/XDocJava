import java.awt.print.PrinterException;
import java.io.IOException;

import com.gnostice.core.XDocException;
import com.gnostice.documents.FormatNotSupportedException;
import com.gnostice.documents.IncorrectPasswordException;
import com.gnostice.documents.controls.swing.printer.DocumentPrinter;


public class SimplePrinting
{

    public static void main(String[] args) throws FormatNotSupportedException, IncorrectPasswordException, XDocException
    {
        try
        {
            /////////////////////////////////
            //Silent Printing of a document//
            /////////////////////////////////
            
            DocumentPrinter documentPrinter = new DocumentPrinter();
            
            //Load the document with its password
            documentPrinter.loadDocument(args[0], "");
            
            //Issue Print
            documentPrinter.print();
            
            //Close the Document
            documentPrinter.closeDocument();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (PrinterException e)
        {
            e.printStackTrace();
        }
        catch (ArrayIndexOutOfBoundsException ex)
        {
            System.out.println("Usage : java SimplePrintingDemo "
                + "<input file path>");
        }
    }

}
