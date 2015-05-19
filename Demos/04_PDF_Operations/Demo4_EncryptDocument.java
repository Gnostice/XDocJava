

import java.io.IOException;

import com.gnostice.core.XDocException;
import com.gnostice.documents.FormatNotSupportedException;
import com.gnostice.documents.IncorrectPasswordException;
import com.gnostice.documents.pdf.PDF;
import com.gnostice.documents.pdf.PDFEncryptionLevel;
import com.gnostice.documents.pdf.PdfEncryption;
import com.gnostice.xtremedocumentstudio.Framework;

public class Demo4_EncryptDocument
{
    static
    {
        // Activate the product
        Framework.activate("G23VRW:34KEOVT:5PVZ16V:D9OV8",
            "ENE8RI6Z:45019ERAE:7ERFGQGR6:VTOR10");
    }
    
    public static void main(String[] args)
        throws FormatNotSupportedException,
        IncorrectPasswordException, XDocException, IOException
    {
        // Specify input file name
        String inputFileName = "InputDocument.pdf";
        
        // Specify output file name
        String outputFileName = "EncryptedDocument.pdf";
        
        // Create the PDF object
        PDF pdfDoc = new PDF();
     
        // Load the input document
        pdfDoc.loadDocument(inputFileName , "");
        
        // Get the Encryption object from the document
        PdfEncryption encryptor = pdfDoc.getEncryptor();
        // Set document open password
        encryptor.setUserPwd("open");
        
        // Set permissions/owner password
        encryptor.setOwnerPwd("owner");
        
        // Set the encryption level/algorithm 
        encryptor.setLevel(PDFEncryptionLevel.AES_128BIT);
        
        // Set the permissions for the user to perform the opertion.
        // Here user is allowed to perform only High Resolution
        // Printing, other operations cannot be performed by the users
        // without the permissions password.
        encryptor.setPermissions(PdfEncryption.AllowHighResPrint);
        
        // Apply the encryption settings to the document
        pdfDoc.setEncryptor(encryptor);;
        
        // Save the changes
        pdfDoc.save(outputFileName );
        
        // close the document
        pdfDoc.closeDocument();
    }

}
