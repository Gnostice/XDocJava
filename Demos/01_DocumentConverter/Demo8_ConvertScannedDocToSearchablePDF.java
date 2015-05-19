import com.gnostice.core.XDocException;
import com.gnostice.core.digitizationengine.DigitizationMode;
import com.gnostice.core.digitizationengine.DocumentLanguage;
import com.gnostice.core.digitizationengine.RecognizeElementTypes;
import com.gnostice.documents.ConverterDigitizerSettings;
import com.gnostice.documents.ConverterException;
import com.gnostice.documents.DocumentConverter;
import com.gnostice.documents.FormatNotSupportedException;
import com.gnostice.xtremedocumentstudio.Framework;

/*
 * Convert from scanned images to searchable PDFs
 */
public class Demo8_ConvertScannedDocToSearchablePDF
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
        
        // Create an object of DocumentConverter
        DocumentConverter converter = new DocumentConverter();

        // Get the DigitizerSettings object from the Preferences of DocumentConverter
        ConverterDigitizerSettings digitizerSettings = converter.getPreferences().getDigitizerSettings();
        
        // Set the DigitizationMode to ALL_IMAGES. Default is NEVER.
        digitizerSettings.setDigitizationMode(DigitizationMode.ALL_IMAGES);
        
        // Specify page elements in the input document that need to be
        // digitized (currently only text elements are supported). Default is only Text
//        digitizerSettings.setRecognizeElementTypes(RecognizeElementTypes.TEXT);
        
        // Specify input document language (currently only English language is supported)
//        digitizerSettings.getOCRSettings().setDocumentLanguage(DocumentLanguage.ENGLISH);
        
        // scanned document input (image, PDF or DOCX)
        String inputFileName = "ScannedInputImage.jpg";
//        String inputFileName = "ScannedInputDocument.pdf";
//        String inputFileName = "ScannedInputDocument.docx";
        
        String outputFileName = "SearchablePDFOutput.pdf";
        
        // Convert from one format to another by supplying the input
        // and output file name.
        converter.convertToFile(inputFileName, outputFileName);
    }
}
