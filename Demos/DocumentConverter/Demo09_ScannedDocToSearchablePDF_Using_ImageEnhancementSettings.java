import com.gnostice.core.XDocException;
import com.gnostice.core.digitizationengine.DigitizationMode;
import com.gnostice.core.digitizationengine.ImageEnhancementMode;
import com.gnostice.core.digitizationengine.ImageEnhancementSettings;
import com.gnostice.documents.ConverterDigitizerSettings;
import com.gnostice.documents.ConverterException;
import com.gnostice.documents.DocumentConverter;
import com.gnostice.documents.FormatNotSupportedException;
import com.gnostice.xtremedocumentstudio.Framework;

/*
 * Convert from scanned images to searchable PDFs
 */
public class Demo09_ScannedDocToSearchablePDF_Using_ImageEnhancementSettings
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
        
        // Specify input document languages (default language is
        // English specify explicitly for other languages)
        // 2 or more languages can be combined using '+' delimiter 
        // eg. "eng+chi_sim" for English and Chinese Simplified.
//        digitizerSettings.getOCRSettings().setDocumentLanguage("eng");
        
        // Specify Image Enhancement settings
        ImageEnhancementSettings imageEnhancementSettings = digitizerSettings.getImageEnhancementSettings();
        
        // By default ImageEnhancementMode is OFF.
//        imageEnhancementSettings.setImageEnhancementMode(ImageEnhancementMode.OFF);

        // AUTO: Automatically determines the combination of
        // image-processing technique(s) that would produce the
        // highest accuracy.
        imageEnhancementSettings.setImageEnhancementMode(ImageEnhancementMode.AUTO);
        
        // USE_SPECIFIED_TECHNIQUES - Applies a user-specified list of
        // image-processing techniques before digitization.
//        imageEnhancementSettings.setImageEnhancementMode(ImageEnhancementMode.USE_SPECIFIED_TECHNIQUES);
        
        // Specify the list of ImageEnhancement techniques that should be used.
//        List<ImageEnhancementTechnique> listOfImageEnhancingTechniques = new ArrayList<ImageEnhancementTechnique>();
//        listOfImageEnhancingTechniques.add(new Scaling(2f));
//        imageEnhancementSettings.setImageEnhancementTechniques(listOfImageEnhancingTechniques );
        
        // By default skew correction is on. You can disable it by set
        // it to false.
//        digitizerSettings.setSkewCorrectionOn(false);
        
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
