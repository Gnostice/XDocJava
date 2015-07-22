import java.util.Arrays;

import com.gnostice.core.XDocException;
import com.gnostice.documents.ConverterException;
import com.gnostice.documents.DocumentConverter;
import com.gnostice.documents.FormatNotSupportedException;
import com.gnostice.xtremedocumentstudio.Framework;

/*
 * Convert from one format to another by supplying the input
 * file name and output file name.
 */
public class Demo01_OneToOneConversion
{
    static
    {
        // Activate the product
        Framework.activate("G23VRW:34KEOVT:5PVZ16V:D9OV8",
            "ENE8RI6Z:45019ERAE:7ERFGQGR6:VTOR10");
        
        // Directory path to refer the Font files which are not
        // installed in OS Fonts Directory.
        Framework.setUserFontsDirectory("C:\\FontsCollection\\");
    }
    
    public static void main(String[] args)
        throws FormatNotSupportedException, ConverterException,
        XDocException
    {
        /*
         * Supported Formats: PDF, DOCX, BMP, PNG, JPEG, GIF, and
         * if Java Advanced Imaging API is referred then TIFF and JPEG2000
         */
        
        // Lists the formats supported by XtremeDocumentStudio Java
        listSupportedFormatsByFileExtension();
        
        // Create an object of DocumentConverter
        DocumentConverter converter = new DocumentConverter();
        
        String inputFileName = "InputPDFDoc.pdf";
        String outputFileName = "Output.tif";
        
        // Convert from one format to another by supplying the input
        // and output file name.
        converter.convertToFile(inputFileName, outputFileName);
    }
    
    public static void listSupportedFormatsByFileExtension()
    {
        String[] supportedFileExtensions = Framework
            .getSupportedFileExtensions();
        
        // getSupportedMIMETypes method returns the list of supported
        // formats by MIMEType. 
//        String[] supportedMIMETypes = Framework.getSupportedMIMETypes();

        String strSupportedFileExtensions = Arrays
            .toString(supportedFileExtensions);
        System.out.println("Supported File Extensions:"
            + strSupportedFileExtensions);
        
        if (strSupportedFileExtensions.indexOf("tif") == -1)
        {
            System.out.println("Refer Java Advanced Imaging API in the "
                + "classpath to use tif and jpeg2000");
        }
    }
}
