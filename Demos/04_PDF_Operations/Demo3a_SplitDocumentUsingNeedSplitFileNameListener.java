

import java.io.IOException;

import com.gnostice.core.XDocException;
import com.gnostice.documents.FormatNotSupportedException;
import com.gnostice.documents.InsufficientPermissionsException;
import com.gnostice.documents.PageRangeSettings;
import com.gnostice.documents.enums.PageOrdering;
import com.gnostice.documents.enums.PageRange;
import com.gnostice.documents.pdf.PDF;
import com.gnostice.documents.pdf.PDFNeedSplitFileNameEvent;
import com.gnostice.documents.pdf.PDFNeedSplitFileNameListener;
import com.gnostice.xtremedocumentstudio.Framework;

public class Demo3a_SplitDocumentUsingNeedSplitFileNameListener
    implements PDFNeedSplitFileNameListener
{
    static
    {
        // Activate the product
        Framework.activate("G23VRW:34KEOVT:5PVZ16V:D9OV8",
            "ENE8RI6Z:45019ERAE:7ERFGQGR6:VTOR10");
    }
    
    public static void main(String[] args)
        throws FormatNotSupportedException,
        InsufficientPermissionsException, IOException, XDocException
    {
        // Specify input file name
        String inputFileName = "InputDocument.pdf";
        
        PDF pdfDoc = new PDF();
        
        // Register PDFNeedSplitFileNameListener to supply the output
        // file names for split operation.
        Demo3a_SplitDocumentUsingNeedSplitFileNameListener needSplitFileNameListener = 
            new Demo3a_SplitDocumentUsingNeedSplitFileNameListener();
        pdfDoc.addNeedSplitFileNameListener(needSplitFileNameListener);
        
        // Load the input document
        pdfDoc.loadDocument(inputFileName , "");
        
        // Create the PageRangeSettings for the split outputs
        PageRangeSettings[] pageRangeSettings = new PageRangeSettings[2];
        
        // Split output1: only odd page numbers of the input document
        pageRangeSettings[0] = new PageRangeSettings("-", PageRange.ODD, PageOrdering.NORMAL);
        
        // Split output2: All pages of the input document, but in the reverse order
        pageRangeSettings[1] = new PageRangeSettings("-", PageRange.ALL, PageOrdering.REVERSE);
        
        // Split the input document with the pages specified in the
        // page range settings.
        pdfDoc.split(pageRangeSettings);
        
        // close the document
        pdfDoc.closeDocument();
    }

    @Override
    // split output file name of the PDF documents to be saved will be sent to this method.
    public void needSplitFileName(PDFNeedSplitFileNameEvent e)
    {
        e.setFileName("F:\\" + e.getFileName());
        
        // set Cancel to true to cancel the split operation
//        e.setCancel(true);
    }

}
