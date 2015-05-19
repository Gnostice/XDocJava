import java.awt.print.PrinterException;
import java.io.IOException;

import com.gnostice.core.XDocException;
import com.gnostice.documents.FormatNotSupportedException;
import com.gnostice.documents.IncorrectPasswordException;
import com.gnostice.documents.PageAlignment;
import com.gnostice.documents.controls.swing.printer.DocumentPrinter;
import com.gnostice.documents.enums.Horizontal;
import com.gnostice.documents.enums.PaperSize;
import com.gnostice.documents.enums.Vertical;


public class PrinterPagePositioningDemo
{

    public static void main(String[] args) throws FormatNotSupportedException, IncorrectPasswordException, XDocException
    {

        try
        {
            /////////////////////////////////////////
            //Positioning the document in the Paper//
            ////////////////////////////////////////
            
            DocumentPrinter documentPrinter = new DocumentPrinter();
            
            //Load the document with its password
            documentPrinter.loadDocument(args[0], "");
            
            //Paper size is set
            documentPrinter.getPrintSettings().setPageSize(PaperSize.A2);
            
            //Position of the the page in the paper is set in PageAlignment
            PageAlignment pageAlignment = new PageAlignment(Horizontal.RIGHT, Vertical.BOTTOM);
            
            //PageAlignment is set to PagePositioning
            documentPrinter.getPrintSettings().setPagePositioning(pageAlignment);
            
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
            System.out.println("Usage : java PrinterPagePositioningDemo "
                + "<input file path>");
        }
    
    }

}
