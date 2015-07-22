

import java.io.IOException;

import com.gnostice.core.XDocException;
import com.gnostice.documents.FormatNotSupportedException;
import com.gnostice.documents.InsufficientPermissionsException;
import com.gnostice.documents.PageRangeSettings;
import com.gnostice.documents.enums.PageOrdering;
import com.gnostice.documents.enums.PageRange;
import com.gnostice.documents.pdf.PDF;
import com.gnostice.xtremedocumentstudio.Framework;

public class Demo1_MergeDocuments
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
        // Input document 1
        String inputFileName1 = "InputPDFDocument1.pdf";
        // All pages
        PageRangeSettings pageRangeSettings1 = new PageRangeSettings("-");
        
        // Input document 2
        String inputFileName2 = "InputPDFDocument2.pdf";
        // Only odd number pages in the reverse oder
        PageRangeSettings pageRangeSettings2 = new PageRangeSettings(
            "-", PageRange.ODD, PageOrdering.REVERSE);

        // Output File Name
        String outputFileName = "MergedOutput.pdf";
        
        // Create the PDF object
        PDF pdfDoc = new PDF();
        
        // Appends the specified pages to the pdfDoc object
        pdfDoc.appendPagesFrom(inputFileName1, pageRangeSettings1);
        pdfDoc.appendPagesFrom(inputFileName2, pageRangeSettings2);
        
        // save to the merged document to the output file
        pdfDoc.save(outputFileName );
        pdfDoc.closeDocument();
    }

}
