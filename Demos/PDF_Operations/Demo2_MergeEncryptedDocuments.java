

import java.io.IOException;

import com.gnostice.core.XDocException;
import com.gnostice.documents.DocumentListenerAdapter;
import com.gnostice.documents.FormatNotSupportedException;
import com.gnostice.documents.InsufficientPermissionsException;
import com.gnostice.documents.NeedPasswordEvent;
import com.gnostice.documents.PageRangeSettings;
import com.gnostice.documents.enums.Operation;
import com.gnostice.documents.enums.PageOrdering;
import com.gnostice.documents.enums.PageRange;
import com.gnostice.documents.pdf.PDF;
import com.gnostice.xtremedocumentstudio.Framework;

public class Demo2_MergeEncryptedDocuments
{

    static
    {
        // Activate the product
        Framework.activate("G23VRW:34KEOVT:5PVZ16V:D9OV8",
            "ENE8RI6Z:45019ERAE:7ERFGQGR6:VTOR10");
    }
    
    public static void main(String[] args)
        throws FormatNotSupportedException, IOException,
        XDocException
    {
        // Input Document 1 which is Encrypted
        String inputFileName1 = "EncryptedInputDocument1.pdf";
        // All pages
        PageRangeSettings pageRangeSettings1 = new PageRangeSettings("-");
        
        // Input Document 2
        String inputFileName2 = "InputDocument2.pdf";
        // Only odd number pages in the reverse oder
        PageRangeSettings pageRangeSettings2 = new PageRangeSettings(
            "-", PageRange.ODD, PageOrdering.REVERSE);
        
        // Output File Name
        String outputFileName = "MergedOutput.pdf";
        
        // Create the PDF object
        PDF pdfDoc = new PDF();
        
        // Create PDF object for the input document 1
        PDF encryptedInputPDFDoc1 = new PDF();
        // Add the DocumentListener to listen to the needPassword event
        encryptedInputPDFDoc1.addDocumentListener(new DocumentListenerAdapter()
        {
            @Override
            public void needPassword(NeedPasswordEvent e)
            {
                // This event is called when a password is
                // required for loading/opening the document or a
                // password required to perform the requested
                // operation on the document.
                
                if (e.getOperation() == Operation.LOAD_DOCUMENT)
                {
                    e.setPassword("OpenPassword");
                }
                else // Operation could be EXTRACT_PAGES
                {
                    e.setPassword("OwnerPassword");
                    
                    // If user is allowed to override owner
                    // permission and force performing the
                    // operation then the forceFullPermission flag
                    // can be set to true. Note: this is false by
                    // default.
//                    e.setForceFullPermission(true);
                }
                
                // specifies the number of times this event is getting called.
                int attemptCount = e.getAttemptCount();
                
                // Based on the attemptsCount you can decide if this method
                // should be called again when the above provided password is
                // incorrect.
                if (attemptCount <= 3)
                {
                    // Note: callAgainIfUnsuccessful is false by default.
                    e.setCallAgainIfUnsuccessful(true);
                }
                else
                {
                    e.setCallAgainIfUnsuccessful(false);
                }
            }
        });
        
        // Load the encrypted document 1
        encryptedInputPDFDoc1.loadDocument(inputFileName1, "");
        
        // Appends the specified pages to the pdfDoc object
        try
        {
            pdfDoc.appendPagesFrom(encryptedInputPDFDoc1, pageRangeSettings1);
        }
        catch(InsufficientPermissionsException e)
        {
            // if the correct password is not provided in the
            // needPassword event and even if forceFullPermissions is
            // false then this exception will be thrown.
            
            System.out.println("Input Document does not have permission to extract the pages!");
            e.printStackTrace();
        }
        
        // now append the pages from input document 2
        pdfDoc.appendPagesFrom(inputFileName2, pageRangeSettings2);
        
        // save to the merged document to the output file
        pdfDoc.save(outputFileName );
        pdfDoc.closeDocument();
        
        // close encryptedInputPDFDoc1
        encryptedInputPDFDoc1.closeDocument();
    }

}
