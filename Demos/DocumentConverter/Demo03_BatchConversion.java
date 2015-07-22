import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.gnostice.core.XDocException;
import com.gnostice.documents.DocumentConverter;
import com.gnostice.documents.EncoderSettings;
import com.gnostice.documents.PageRangeSettings;
import com.gnostice.documents.enums.ConversionMode;
import com.gnostice.documents.enums.PageOrdering;
import com.gnostice.documents.enums.PageRange;
import com.gnostice.xtremedocumentstudio.Framework;

/*
 * Convert multiple input documents of various supported formats to
 * another format by supplying the input file names and output MIME
 * type.
 */
public class Demo03_BatchConversion
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
    
    public static void main(String[] args) throws XDocException
    {
        /*
         * Supported Formats: PDF, DOCX, BMP, PNG, JPEG, GIF, and
         * if Java Advanced Imaging API is referred then TIFF and JPEG2000
         */
        
        // Lists the formats supported by XtremeDocumentStudio Java
        listSupportedFormatsByFileExtension();
        
        DocumentConverter converter = new DocumentConverter();
        
        // Add input file names to the input list
        ArrayList<String> inputFileNamesList = new ArrayList<String>();
        inputFileNamesList.add("InputPDFDoc.pdf");
        inputFileNamesList.add("InputDOCXDoc.docx");
        inputFileNamesList.add("InputPNGImage.png");
        
        // Specify the outputMIMEType
        String outputMIMEType = "application/pdf";
        
        // Specify the output directory path.
        // "" = currentDir.
        String outputDirPath = "";
        
        // Specify the prefix/baseFileName for the output file name
        String baseFileName = "Output";
        
        // Specify the EncoderSettings, if null then default settings
        // will be used for the specified output MIMEType.
        EncoderSettings encoderSettings = null;
        
        // set ConversionMode to specify whether input documents
        // should be converted to separate output documents or merged
        // together into single file output. 
        ConversionMode conversionMode = ConversionMode.CONVERT_TO_SEPARATE_FILES;
        
        // passwords for the encrypted files in the input list
        ArrayList<String> pwdsForInputs = new ArrayList<String>();
        pwdsForInputs.add("");
        pwdsForInputs.add("password");
        pwdsForInputs.add("");
        
        // Page range settings for each input files
        ArrayList<PageRangeSettings> pageRangeSettingsForInputs = new ArrayList<PageRangeSettings>();
        // "-" = all pages
        pageRangeSettingsForInputs.add(new PageRangeSettings("-"));
        pageRangeSettingsForInputs.add(new PageRangeSettings("-"));
        pageRangeSettingsForInputs.add(new PageRangeSettings("-", PageRange.ODD, PageOrdering.REVERSE));
        
        // ConvertToFile
        List<String> outputFiles = converter.convertToFile(
            inputFileNamesList, 
            outputMIMEType, 
            outputDirPath,
            baseFileName, 
            encoderSettings, 
            conversionMode, 
            pwdsForInputs, 
            pageRangeSettingsForInputs);
        
        
        System.out.println("Output Filepaths: " + outputFiles);
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
