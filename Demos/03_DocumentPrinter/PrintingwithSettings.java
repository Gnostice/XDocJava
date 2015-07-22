import java.awt.print.PrinterException;
import java.io.IOException;

import com.gnostice.core.XDocException;
import com.gnostice.documents.FormatNotSupportedException;
import com.gnostice.documents.IncorrectPasswordException;
import com.gnostice.documents.controls.swing.printer.DocumentPrinter;


public class PrintingwithSettings
{

    public static void main(String[] args) throws FormatNotSupportedException, IncorrectPasswordException, XDocException
    {

        try
        {
            /////////////////////////////////////////////////////
            //Printing the document with settings in the printer//
            ////////////////////////////////////////////////////
            
            DocumentPrinter documentPrinter = new DocumentPrinter();
            
            //Load the document with its password
            documentPrinter.loadDocument(args[0], "");
            
            //Settings to the printer
            documentPrinter.getPrintSettings().setAutoPageRotate(false);
            
            documentPrinter.getPrintSettings().setCopies(2);
            
            //Prints to the selected printer where the printer name is passed as argument
            documentPrinter.getPrintSettings().setSelectedPrinter(args[1]);
            
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
            System.out.println("Usage : java PrintingwithSettings "
                + "<input file path>");
        }
    
    }

}
