import java.awt.print.PrinterException;
import java.io.IOException;

import com.gnostice.core.XDocException;
import com.gnostice.documents.FormatNotSupportedException;
import com.gnostice.documents.IncorrectPasswordException;
import com.gnostice.documents.controls.swing.printer.DocumentPrintListener;
import com.gnostice.documents.controls.swing.printer.DocumentPrinter;
import com.gnostice.documents.controls.swing.printer.PrinterBeginJobEvent;
import com.gnostice.documents.controls.swing.printer.PrinterBeginPageEvent;
import com.gnostice.documents.controls.swing.printer.PrinterEndJobEvent;
import com.gnostice.documents.controls.swing.printer.PrinterEndPageEvent;
import com.gnostice.documents.controls.swing.printer.PrinterNeedPasswordEvent;
import com.gnostice.documents.controls.swing.printer.PrinterRenderErrorEvent;

public class PrinterNeedPasswordEventDemo implements
    DocumentPrintListener
{
    static String pwd;

    public static void main(String[] args)
        throws FormatNotSupportedException,
        IncorrectPasswordException, XDocException
    {
        try
        {
            DocumentPrinter documentPrinter = new DocumentPrinter();

            // Adding Listener to DocumentPrinter
            documentPrinter
                .addPrintListener(new PrinterNeedPasswordEventDemo());
            PrinterNeedPasswordEventDemo.pwd = args[1];

            // Load the document with its password
            documentPrinter.loadDocument(args[0], "");

            // Issue Print
            documentPrinter.print();

            // Close the Document
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
            System.out.println("Usage : java PrinterNeedPasswordEventDemo "
                + "<input file path>");
        }
    }

    @Override
    public void needPassword(
        PrinterNeedPasswordEvent printerNeedPasswordEvent)
    {
        //password is set to PrinterNeedPasswordEvent
        printerNeedPasswordEvent.setPassword(pwd);
    }

    @Override
    public void beginJob(PrinterBeginJobEvent printerBeginJobEvent)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void endJob(PrinterEndJobEvent printerEndJobEvent)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void beginPage(PrinterBeginPageEvent printerBeginPageEvent)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void endPage(PrinterEndPageEvent printerEndPageEvent)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void error(PrinterRenderErrorEvent renderErrorEvent)
    {
        // TODO Auto-generated method stub

    }

}
