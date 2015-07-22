

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gnostice.core.PageSize;
import com.gnostice.core.XDocException;
import com.gnostice.core.enums.MeasurementUnit;
import com.gnostice.core.enums.PaperSize;
import com.gnostice.core.enums.ResolutionMode;
import com.gnostice.documents.DocumentConverter;
import com.gnostice.documents.EncoderSettings;
import com.gnostice.documents.FormatNotSupportedException;
import com.gnostice.documents.InsufficientPermissionsException;
import com.gnostice.documents.PageAlignment;
import com.gnostice.documents.PageRangeSettings;
import com.gnostice.documents.RenderingSettings.ResolutionSettings;
import com.gnostice.documents.enums.ConversionMode;
import com.gnostice.documents.enums.Horizontal;
import com.gnostice.documents.enums.Vertical;
import com.gnostice.documents.image.ImageEncoderSettings;
import com.gnostice.documents.image.PageScaling;
import com.gnostice.documents.image.PageSizingMode;
import com.gnostice.documents.image.RelativePageSizePercent;
import com.gnostice.xtremedocumentstudio.Framework;

public class Demo06_ConvertToImageUsingEncoderSettings
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
        
        // Get ResolutionSettings from Converter Preferences
        ResolutionSettings resolutionSettings = converter
            .getPreferences().getRenderingSettings().getResolution();
        
        // List of inputs
        ArrayList<String> inputFilePaths = new ArrayList<String>();
        inputFilePaths.add("InputDocument.pdf");
//        inputFilePaths.add("InputDocument.docx");
//        inputFilePaths.add("InputDocument.jpg");
        
        // specify the output MIME type of the image
        String outputMIMEType = "image/tiff";
//        String outputMIMEType = "image/jpeg";
//        String outputMIMEType = "image/bmp";
//        String outputMIMEType = "image/gif";
        
        // specify the output directory
        // "" = current directory
        String outputDirectory = "";
        
        // set baseFileName of the output image file
        String baseFileName = "Demo";
//      String baseFileName = "MergedOutput";
        
        // specify that separate files should be generated for each
        // input (i.e., outputs should not be merged.)
        ConversionMode conversionMode = ConversionMode.CONVERT_TO_SEPARATE_FILES;
        
        String inputDocPassword = "";
      
        // Specify the page range
        PageRangeSettings pageRangeSettings = new PageRangeSettings();
        pageRangeSettings.setPageRange("1");
        

        // Specify the output format encoder settings
        EncoderSettings encoderSettings = EncoderSettings.get(outputMIMEType);
        
        if (encoderSettings instanceof ImageEncoderSettings)
        {
            ImageEncoderSettings imgEncoderSettings = (ImageEncoderSettings) encoderSettings;
            
            /*
             * Example.1: 300 Dpi output image size
             */
            setImgSizeTo300DPI(imgEncoderSettings, resolutionSettings);
            
            /*
             * Example.2: 200% of the original image size
             */
//            setImgSizeTo200Percent(imgEncoderSettings, resolutionSettings);
            
            /*
             * Example.3: Predefined Standard A3 Size
             */
//            setImgSizeToStandardA3(imgEncoderSettings, resolutionSettings);
            
            /*
             * Example.4: Specify custom sizing, scaling and alignment options
             */
//            setCustomImgSize(imgEncoderSettings, resolutionSettings);
        }
        
        // ConvertToFile
        List<String> outputFiles = converter.convertToFile(
            inputFilePaths, 
            outputMIMEType, 
            outputDirectory ,
            baseFileName, 
            encoderSettings, 
            conversionMode, 
            inputDocPassword , 
            pageRangeSettings);
        
        System.out.println(outputFiles);
    }
    
    private static void setImgSizeTo300DPI(
        ImageEncoderSettings imgEncoderSettings, ResolutionSettings resolutionSettings)
    {
        // Change ResolutionMode to UseSpecifiedDpi and 
        // set the dpi values.
        int dpiX = 300;
        int dpiY = 300;
        resolutionSettings.setResolutionMode(ResolutionMode.USE_SPECIFIED_DPI);
        resolutionSettings.setDpiX(dpiX);
        resolutionSettings.setDpiY(dpiY);
        
        // set the output sizing mode to Source
        imgEncoderSettings.setPageSizingMode(PageSizingMode.USE_SOURCE);
    }
    
    private static void setImgSizeTo200Percent(
        ImageEncoderSettings imgEncoderSettings, ResolutionSettings resolutionSettings)
    {
        // Set ResolutionMode to UseSource (ResolutionMode will be UseSource by default).
        resolutionSettings.setResolutionMode(ResolutionMode.USE_SOURCE);
        
        // set the output sizing mode to Relative Size
        imgEncoderSettings.setPageSizingMode(PageSizingMode.USE_RELATIVE_SIZE);
        
        // Specify the Relative size
        RelativePageSizePercent relativePageSizePercent = new RelativePageSizePercent();
        relativePageSizePercent.setPercent(200, 200);
        
        // Set the relativePageSizePercent object imgEncoderSettings
        imgEncoderSettings.setRelativePageSizePercent(relativePageSizePercent);
        
        // specify how the source content should be scaled onto the output image canvas
//        imgEncoderSettings.setPageScaling(PageScaling.NONE);
//        imgEncoderSettings.setPageScaling(PageScaling.FIT_TO_PAGE);
        imgEncoderSettings.setPageScaling(PageScaling.FIT_TO_PAGE_WITH_ASPECT);
        
        // specify where the source content should be aligned/placed
        // onto the output image canvas when there is a white
        // space/area around the drawn contents.
        PageAlignment alignment = new PageAlignment();
        alignment.setAlignment(Horizontal.CENTER, Vertical.CENTER);
        // specify the offset value from the chosen alignment.
        alignment.setOffset(10, 10);
        // set the alignment object imgEncoderSettings
        imgEncoderSettings.setPageAlignment(alignment);
        
    }
    
    private static void setImgSizeToStandardA3(
        ImageEncoderSettings imgEncoderSettings, ResolutionSettings resolutionSettings)
    {
        // Set ResolutionMode to UseSource (ResolutionMode will be UseSource by default).
        resolutionSettings.setResolutionMode(ResolutionMode.USE_SOURCE);
        
        // set the output sizing mode to Page Size
        imgEncoderSettings.setPageSizingMode(PageSizingMode.USE_PAGE_SIZE);
        
        // Specify the page size
        PageSize pageSize = new PageSize();
        pageSize.setPageSize(PaperSize.A3);
        // set the pageSize object to imgEncoderSettings
        imgEncoderSettings.setPageSize(pageSize);

        // specify how the source content should be scaled onto the output image canvas
        imgEncoderSettings.setPageScaling(PageScaling.NONE);
//        imgEncoderSettings.setPageScaling(PageScaling.FIT_TO_PAGE);
//        imgEncoderSettings.setPageScaling(PageScaling.FIT_TO_PAGE_WITH_ASPECT);
        
        // specify where the source content should be aligned/placed
        // onto the output image canvas when there is a white
        // space/area around the drawn contents.
        PageAlignment alignment = new PageAlignment();
        alignment.setAlignment(Horizontal.CENTER, Vertical.CENTER);
        // specify the offset value from the chosen alignment.
//        alignment.setOffset(10, 10);
        // set the alignment object imgEncoderSettings
        imgEncoderSettings.setPageAlignment(alignment);
    }

    private static void setCustomImgSize(
        ImageEncoderSettings imgEncoderSettings, ResolutionSettings resolutionSettings)
    {
        // Set ResolutionMode to UseSource (ResolutionMode will be UseSource by default).
        resolutionSettings.setResolutionMode(ResolutionMode.USE_SOURCE);
        
        // set the output image page sizing mode
//        imgEncoderSettings.setPageSizingMode(PageSizingMode.USE_SOURCE);
//        imgEncoderSettings.setPageSizingMode(PageSizingMode.USE_RELATIVE_SIZE);
        imgEncoderSettings.setPageSizingMode(PageSizingMode.USE_PAGE_SIZE);

        /*
         * Set the output image page size according to the sizing mode
         * set above. 
         */
        /*
         *  1. Retain the size of source when the sizing mode is USE_SOURCE.
         */
        /*
         *  2. Set Relative Size when the sizing mode is USE_RELATIVE_SIZE.
         */
//        RelativePageSizePercent relativePageSizePercent = new RelativePageSizePercent(200, 200);
//        imgEncoderSettings.setRelativePageSizePercent(relativePageSizePercent);
        /*
         *  3. Set the predefined or custom page size when the page sizing mode is USE_PAGE_SIZE.
         */
        PageSize pageSize = new PageSize(PaperSize.A3);
        // set custom page dimension
        pageSize.setCustomPageDimension(100, 100, MeasurementUnit.POINTS);
        imgEncoderSettings.setPageSize(pageSize);
        
        /*
         *  Specify how the source should be scaled onto the output image canvas.
         */
//        imgEncoderSettings.setPageScaling(PageScaling.NONE);
        imgEncoderSettings.setPageScaling(PageScaling.FIT_TO_PAGE);
//        imgEncoderSettings.setPageScaling(PageScaling.FIT_TO_PAGE_WITH_ASPECT);
        
        /*
         *  Specify where the source should be placed onto the output image canvas.
         */
        PageAlignment alignment = new PageAlignment();
        alignment.setHAlignment(Horizontal.CENTER);
        alignment.setVAlignment(Vertical.CENTER);
        alignment.setHorizontalOffset(10f);
        alignment.setVerticalOffset(10f);
        imgEncoderSettings.setPageAlignment(alignment);
    }
}
