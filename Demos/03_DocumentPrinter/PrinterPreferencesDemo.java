import java.awt.print.PrinterException;
import java.io.IOException;

import com.gnostice.core.XDocException;
import com.gnostice.documents.FormatNotSupportedException;
import com.gnostice.documents.IncorrectPasswordException;
import com.gnostice.documents.controls.swing.printer.DocumentPrinter;
import com.gnostice.documents.enums.ColorRendering;
import com.gnostice.documents.enums.TextAntiAlias;


public class PrinterPreferencesDemo
{

    public static void main(String[] args) throws FormatNotSupportedException, IncorrectPasswordException, XDocException
    {
        try
        {
            ///////////////////////////////////////////
            // Printing the document with Preferences//
            //////////////////////////////////////////
            
            DocumentPrinter documentPrinter = new DocumentPrinter();
            
            //Load the document with its password
            documentPrinter.loadDocument(args[0], "");
            
            //Setting preferences on the printer
            documentPrinter.getPreferences().getRenderingSettings().getText().setTextAntiAlias(TextAntiAlias.ON);
            documentPrinter.getPreferences().getRenderingSettings().getColor().setColorRendering(ColorRendering.QUALITY);
            
            //Setting DPI
            documentPrinter.getPreferences().getRenderingSettings().getResolution().setDpiX(72);
            documentPrinter.getPreferences().getRenderingSettings().getResolution().setDpiY(72);
            
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
            System.out.println("Usage : java PrinterPreferencesDemo "
                + "<input file path>");
        }
    }

}
