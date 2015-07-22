

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gnostice.core.XDocException;
import com.gnostice.documents.AddAttachmentEvent;
import com.gnostice.documents.ConverterListenerAdapter;
import com.gnostice.documents.DocumentConverter;
import com.gnostice.documents.FormatNotSupportedException;
import com.gnostice.documents.InsufficientPermissionsException;
import com.gnostice.documents.PageRangeSettings;
import com.gnostice.documents.enums.ConversionMode;
import com.gnostice.documents.pdf.PDFEncoderSettings;
import com.gnostice.documents.pdf.PDFPortfolioSettings;
import com.gnostice.documents.pdf.PortfolioCreationMode;
import com.gnostice.documents.pdf.PortfolioLayoutMode;
import com.gnostice.xtremedocumentstudio.Framework;

public class Demo10_ConvertToPDFPortfolio extends ConverterListenerAdapter
{
    static
    {
        // Activate the product
        Framework.activate("G23VRW:34KEOVT:5PVZ16V:D9OV8",
            "ENE8RI6Z:45019ERAE:7ERFGQGR6:VTOR10");
    }
    
    public static void main(String[] args)
        throws FormatNotSupportedException,
        InsufficientPermissionsException, XDocException, IOException
    {
        DocumentConverter converter = new DocumentConverter();
        
        // Multiple inputs
        ArrayList<String> inputFilePaths = new ArrayList<String>();
        inputFilePaths.add("Input1.pdf");
        inputFilePaths.add("Input2.bmp");
        inputFilePaths.add("Input3.docx");
        
        String outputMIMEType = "application/pdf";
        
        String outputDir = "D:\\Temp\\";
        
        String baseFileName = "PDFPortfolioOutput";
        
        // Specify conversion mode with the option to attach the input files to output document.
        ConversionMode conversionMode = ConversionMode.CREATE_NEW_FILE_AND_ATTACH_ALL_AS_ORIGINAL;
//        ConversionMode conversionMode = ConversionMode.CONVERT_FIRST_FILE_AND_ATTACH_REST_AS_ORIGINAL;

        // Specify whether the output PDF should be created with Portfolio feature.
        PDFEncoderSettings pdfEncoderSettings = new PDFEncoderSettings();
        PDFPortfolioSettings portfolioSettings = pdfEncoderSettings.getPortfolioSettings();
        portfolioSettings.setPortfolioCreationMode(PortfolioCreationMode.ONLY_WHEN_ATTACHMENTS_EXIST);
        // specify view mode of the PortfolioLayout
        portfolioSettings.setLayoutMode(PortfolioLayoutMode.DETAILS);
        
        String inputDocPassword = "";
        
        List<PageRangeSettings> pageRangeSettingsList = new ArrayList<PageRangeSettings>();
        pageRangeSettingsList.add(new PageRangeSettings("-"));

        // ConvertToFile
        List<String> outputFiles = converter.convertToFile(
            inputFilePaths, 
            outputMIMEType , 
            outputDir,
            baseFileName, 
            pdfEncoderSettings, 
            conversionMode, 
            inputDocPassword , 
            pageRangeSettingsList);
        
        System.out.println(outputFiles);
    }
    
    @Override
    public void addAttachment(AddAttachmentEvent e)
    {
        super.addAttachment(e);
        
        // This event is called before adding attachment to output PDF
        // document while copying the attachments from source PDF to
        // output PDF and while attaching the input files to PDF (when
        // the conversoinMode is
        // CONVERT_FIRST_FILE_AND_ATTACH_REST_AS_ORIGINAL or
        // CREATE_NEW_FILE_AND_ATTACH_ALL_AS_ORIGINAL).
        
        // if you don't want to copy attachments from source PDF to
        // output PDF you can skip the existing attachments.
//        if (e.isExistingAttachment())
//        {
//            e.setSkipAttachment(true);
//        }
    }
}
