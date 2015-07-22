import java.util.Arrays;

import com.gnostice.core.XDocException;
import com.gnostice.documents.ConverterException;
import com.gnostice.documents.DocumentConverter;
import com.gnostice.documents.EncoderSettings;
import com.gnostice.documents.FormatNotSupportedException;
import com.gnostice.documents.PageRangeSettings;
import com.gnostice.documents.enums.PageOrdering;
import com.gnostice.documents.enums.PageRange;
import com.gnostice.documents.image.TiffImageEncoderSettings;
import com.gnostice.xtremedocumentstudio.Framework;

/*
 * Convert from one format to another by supplying the input
 * file name and output file name.
 */
public class Demo02_OneToOneConversionUsingEncoderSettings
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
        
        // Specify the Encoder settings
        EncoderSettings encoderSettings = EncoderSettings
            .get("image/tiff");
        if (encoderSettings != null)
        {
            TiffImageEncoderSettings tiffImageEncoderSettings = 
                (TiffImageEncoderSettings) encoderSettings;
            // by default multiPage property is true for tiff images
            tiffImageEncoderSettings.setMultiPage(false);
        }
        
        // Specify the input document password if it is encrypted
        String inputDocPassword = "";

        // only odd number pages in the document
        PageRangeSettings pageRangeSettings = new PageRangeSettings(
            "-", PageRange.ODD, PageOrdering.NORMAL);
        
        // Convert from one format to another by supplying the input
        // file name, output file name and other settings. 
        converter.convertToFile(
            inputFileName, 
            outputFileName,
            encoderSettings, 
            inputDocPassword, 
            pageRangeSettings);
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
