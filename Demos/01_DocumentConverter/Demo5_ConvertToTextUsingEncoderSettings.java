import java.util.Arrays;

import com.gnostice.core.XDocException;
import com.gnostice.documents.ConverterException;
import com.gnostice.documents.DocumentConverter;
import com.gnostice.documents.EncoderSettings;
import com.gnostice.documents.FormatNotSupportedException;
import com.gnostice.documents.PageRangeSettings;
import com.gnostice.documents.enums.PageOrdering;
import com.gnostice.documents.enums.PageRange;
import com.gnostice.documents.txt.TXTEncoderSettings;
import com.gnostice.xtremedocumentstudio.Framework;

/*
 * Convert PDF or DOCX to text (TXT) by supplying the input
 * file name and output file name.
 */
public class Demo5_ConvertToTextUsingEncoderSettings
{
    static
    {
        // Activate the product
        Framework.activate("G23VRW:34KEOVT:5PVZ16V:D9OV8",
            "ENE8RI6Z:45019ERAE:7ERFGQGR6:VTOR10");
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
        

        // Specify the input file name
        String inputFileName = "InputPDFDoc.pdf";
//        String inputFileName = "InputDOCXDoc.docx";
        
        // Specify the output file name
        String outputFileName = "Output.txt";
        
        // Specify the Encoder settings
        EncoderSettings encoderSettings = EncoderSettings
            .get("text/plain");
        if (encoderSettings != null)
        {
            TXTEncoderSettings txtEncoderSettings = 
                (TXTEncoderSettings) encoderSettings;
            
            // By default File Encoding is FileEncoding.UTF_8
//            txtEncoderSettings.setFileEncoding(FileEncoding.ISO_8859_1);
            
            // By default BOM (Byte Order Mark) is written at the
            // beginning of the file according to the specified
            // FileEncoding.
            txtEncoderSettings.setByteOrderMarkOn(false);
            
            // By default Line Breaks are on
            txtEncoderSettings.getFormatter().setLineBreaksOn(false);
            
            // By default Paragraph Breaks are on
            txtEncoderSettings.getFormatter().setParagraphBreaksOn(false);
            
            // By default Page Breaks are on
            txtEncoderSettings.getFormatter().setPageBreaksOn(false);
            
            // By default writing File Separator char is on. This
            // option is useful when multiple input files are
            // converted to text file using the option
            // mergeAfterConvert.
            txtEncoderSettings.getFormatter().setFileSeparatorsOn(false);
            
            // By default Page Break Lines are off (this is to identify the end of the page using a Page Break Line)
            txtEncoderSettings.getFormatter().setPageBreakLinesOn(true);
            
            // By default Page Break Line is the text "-- End of Page --"
            txtEncoderSettings.getFormatter().setPageBreakLine("-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-");
        }
        
        // Specify the input document password if it is encrypted
        String inputDocPassword = "";

        // Specify the page range
        // Below statement specifies that all pages in the document should be converted
        PageRangeSettings pageRangeSettings = new PageRangeSettings(
            "-", PageRange.ALL, PageOrdering.NORMAL);
        
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
