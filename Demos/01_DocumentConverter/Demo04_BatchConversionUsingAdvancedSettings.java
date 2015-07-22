import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.gnostice.core.PageSize;
import com.gnostice.core.XDocException;
import com.gnostice.documents.AddAttachmentEvent;
import com.gnostice.documents.ConverterBeginDigitizePageEvent;
import com.gnostice.documents.ConverterBeginInputDocumentEvent;
import com.gnostice.documents.ConverterBeginInputPageEvent;
import com.gnostice.documents.ConverterBeginJobEvent;
import com.gnostice.documents.ConverterEndDigitizePageEvent;
import com.gnostice.documents.ConverterEndInputDocumentEvent;
import com.gnostice.documents.ConverterEndInputPageEvent;
import com.gnostice.documents.ConverterEndJobEvent;
import com.gnostice.documents.ConverterErrorEvent;
import com.gnostice.documents.ConverterListener;
import com.gnostice.documents.ConverterNeedFileNameEvent;
import com.gnostice.documents.ConverterNeedOutputStreamEvent;
import com.gnostice.documents.ConverterNeedPasswordEvent;
import com.gnostice.documents.ConverterOutputPageReadyEvent;
import com.gnostice.documents.ConverterOutputReadyEvent;
import com.gnostice.documents.ConverterPreferences;
import com.gnostice.documents.DocumentConverter;
import com.gnostice.documents.EncoderSettings;
import com.gnostice.documents.PageRangeSettings;
import com.gnostice.documents.PasswordSettings;
import com.gnostice.documents.RenderingSettings;
import com.gnostice.documents.enums.ConversionMode;
import com.gnostice.documents.enums.Operation;
import com.gnostice.documents.enums.PageOrdering;
import com.gnostice.documents.enums.PageRange;
import com.gnostice.documents.image.ImageEncoderSettings;
import com.gnostice.documents.image.TiffImageEncoderSettings;
import com.gnostice.documents.image.TiffImageEncoderSettings.TiffCompressionType;
import com.gnostice.documents.pdf.FontEmbedType;
import com.gnostice.documents.pdf.PDFEncoderSettings;
import com.gnostice.xtremedocumentstudio.Framework;

/*
 * Convert multiple input documents of various supported formats to
 * another format by supplying the input file names and output MIME
 * type.
 * 
 * This program demonstrates how to use the advanced options of
 * DocumentConverter and how to listen to the converter
 * events/progress.
 */
public class Demo04_BatchConversionUsingAdvancedSettings implements
    ConverterListener
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
        converter.addConverterListener(new Demo04_BatchConversionUsingAdvancedSettings());
        
        // Get ConverterPreferences
        ConverterPreferences preferences = converter.getPreferences();
        
        // change EncoderSettings for each format in the ConverterPreferences.
        // Note: convertTo* methods that accept EncoderSettings will
        // override the one in the preferences.
        EncoderSettings encSettings = preferences.getEncoderSettings("image/tiff");
        if (encSettings instanceof TiffImageEncoderSettings)
        {
            TiffImageEncoderSettings tiffImageEncoderSettings = (TiffImageEncoderSettings) encSettings;
            tiffImageEncoderSettings.setMultiPage(false);
        }
        
        // set Password settings
        PasswordSettings passwordSettings = preferences.getPasswordSettings();
        // Eg. When an input PDF document does not have permission for
        // CopyContents and if forceFullPermission is true then the
        // operation is forced to perform. 
        passwordSettings.setForceFullPermission(true);
        
        // set Rendering settings
        RenderingSettings renderingSettings = preferences.getRenderingSettings();
        renderingSettings.getResolution().setDpiX(110);
        renderingSettings.getResolution().setDpiY(110);
        
        // Add files to the input list
        ArrayList<String> inputFiles = new ArrayList<String>();
        inputFiles.add("InputPDFDoc.pdf");
        inputFiles.add("InputDOCXDoc.docx");
        inputFiles.add("InputPNGImage.png");
        
        // set the outputMIMEType
        String outputMIMEType = "application/pdf";
//        String outputMIMEType = "image/tiff";
//        String outputMIMEType = "image/jpeg";
        
        // Specify the output directory path.
        // "" = currentDir.
        String outputDirPath = "";
        
        // Specify the prefix/baseFileName for the output file name
        String baseFileName = "Output";
        
        // Specify the EncoderSettings, if null then default settings
        // will be used for the specified output MIMEType.
        // Note: This encoderSettings (if used in convertTo* method)
        // will override the encoderSettings in the
        // ConverterPreferences.
        EncoderSettings encoderSettings = EncoderSettings.get(outputMIMEType);
        
        if (encoderSettings instanceof TiffImageEncoderSettings)
        {
            // multiPage is true by default.
            TiffImageEncoderSettings tiffImageEncoderParams = (TiffImageEncoderSettings)encoderSettings;
            tiffImageEncoderParams.setMultiPage(false);
            tiffImageEncoderParams.setCompressionType(TiffCompressionType.CCITT_4);
            tiffImageEncoderParams.setCompressionQuality(0.4f);
            
            // set CompressionType using Java's ImageWriteParam
//            ImageWriteParam imageWriteParam = ImageEncoderParams.getImageWriteParam(outputFileFormat);
//            imageWriteParam.setCompressionMode(TIFFImageWriteParam.MODE_EXPLICIT);
////            imageWriteParam.setCompressionType("CCITT T.6"); // CompressionType CCITT4
//            imageWriteParam.setCompressionType("JPEG");
//            imageWriteParam.setCompressionQuality(0.4f);
//            tiffImageEncoderParams.setImageWriteParam(imageWriteParam);
        }
        else if (encoderSettings instanceof ImageEncoderSettings)
        {
            ImageEncoderSettings imgEncoderParams = (ImageEncoderSettings) encoderSettings;
            // Settings Compression Quality takes effect when the
            // given format support compression based on quality
            // values.
            imgEncoderParams.setCompressionQuality(1f);
        }
        else if (encoderSettings instanceof PDFEncoderSettings)
        {
            PDFEncoderSettings pdfEncoderParams = (PDFEncoderSettings) encoderSettings;
            // while converting from DOCX to PDF the fonts used in the
            // input document will be embedded to the output PDF
            // document based on this setting.
            // Default EmbedType = SUBSET
            pdfEncoderParams.setFontEmbedType(FontEmbedType.SUBSET);
            // When this is true then the font will be embedded even
            // when the font file does not have the permission for
            // embedding.
            pdfEncoderParams.setFontEmbedOverrideRestriction(true);
        }
        
     // set ConversionMode to specify whether input documents
        // should be converted to separate output documents or merged
        // together into single file output. 
        ConversionMode conversionMode = ConversionMode.CONVERT_TO_SINGLE_FILE;
        
        // passwords for the encrypted files in the input list
        ArrayList<String> pwdsForInputs = new ArrayList<String>();
        pwdsForInputs.add("");
        pwdsForInputs.add("mypassword");
        pwdsForInputs.add("");
        
        // Page range settings for each input files
        ArrayList<PageRangeSettings> pageRangeSettingsForInputs = new ArrayList<PageRangeSettings>();
        // "-" = all pages
        pageRangeSettingsForInputs.add(new PageRangeSettings("-"));
        pageRangeSettingsForInputs.add(new PageRangeSettings("-"));
        // Only odd number pages in reverse order.
        pageRangeSettingsForInputs.add(new PageRangeSettings("-", PageRange.ODD, PageOrdering.REVERSE));
        
        // ConvertToFile
        List<String> outputFiles = converter.convertToFile(
            inputFiles, 
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

    @Override
    public void beginJob(ConverterBeginJobEvent e)
    {
        System.out.println("Conversion started.....");
        System.out.println("Total number of input files: "
            + e.getJobInfo().getInputDocumentCount());
        System.out.println("Converting the input files to the Output MIMEType: "
                + e.getJobInfo().getOutputMIMEType());
        System.out.println();
    }

    @Override
    public void beginDocument(ConverterBeginInputDocumentEvent e)
    {
        System.out.println("Started input "
            + (e.getInputDocumentInfo().getInputIndex() + 1) + " of "
            + e.getJobInfo().getInputDocumentCount());
    }

    @Override
    public void beginPage(ConverterBeginInputPageEvent e)
    {
        
    }
    
    @Override
    public void beginDigitizePage(
        ConverterBeginDigitizePageEvent e)
    {
        // This event is called before digitizing the input page of a
        // scanned document while converting/digitizing it to
        // searchable PDF.
    }

    @Override
    public void endDigitizePage(ConverterEndDigitizePageEvent e)
    {
        // This event is called after digitizing the input page of a
        // scanned document while converting/digitizing it to
        // searchable PDF.
    }

    @Override
    public void endPage(ConverterEndInputPageEvent e)
    {
    }
    
    @Override
    public void outputPageReady(ConverterOutputPageReadyEvent e)
    {
        // get output page size
//        PageSize outputPageSize = e.getOutputPage().getPageSize();
    }

    @Override
    public void endDocument(ConverterEndInputDocumentEvent e)
    {
        System.out.println("Finished input "
            + (e.getInputDocumentInfo().getInputIndex() + 1) + " of "
            + e.getJobInfo().getInputDocumentCount());
    }

    @Override
    public void endJob(ConverterEndJobEvent e)
    {
        System.out.println("Conversion completed!");
    }

    @Override
    public void error(ConverterErrorEvent e)
    {
        System.out
            .println("Error occurred while converting document "
                + (e.getInputDocumentIndex() + 1));
        
        // Note: You can use the rethrowError option if you want to
        // interrupt the conversion operation by re-throwing this
        // exception.
//        e.setRethrowError(true);
        
        // You can choose to cancel the conversion operation at this time.
//        e.setCancel(true);
        
        // print the cause of the exception.
        e.getCause().printStackTrace();
    }

    @Override
    public void needPassword(ConverterNeedPasswordEvent e)
    {
        // This event is called only when the input document is
        // encrypted and if it requires password to perform the
        // current operation.
        
        if (e.getOperation() == Operation.LOAD_DOCUMENT)
        {
            e.setPassword("OpenPassword");
        }
        else
        {
            // If the requested operation is other than document
            // loading and when an input document does not have the
            // permission for requested operation then the user of the
            // operation needs to provide the owner/permissions
            // password.
            e.setPassword("OwnerPassword");
            
            // Eg. When an input PDF document does not have the
            // permission to perform the requested operation then if
            // forceFullPermission is true then the
            // operation is forced to perform even though the
            // permissions password is incorrect/not-known.
//            e.setForceFullPermission(true);
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
    }

    @Override
    public void needFileName(ConverterNeedFileNameEvent e)
    {
        // This event is called when convertToFile method is used.
        
        // Use this event if you want to specify the output file name
        // for the current input document and page.
    }

    @Override
    public void needOutputStream(ConverterNeedOutputStreamEvent e)
    {
        // This event is called when convertToStream method is used.
        
        // Use this event if you want to set the OutputStream
        // for the current input document and page.
    }

    @Override
    public void outputReady(ConverterOutputReadyEvent e)
    {
        // This event is to notify when the current/final output file/stream is ready.
        
        // You can use e.isStreamOutput method to check if the the output is a stream or file.
//        boolean isStreamOutput = e.isStreamOutput();
        
    }

    @Override
    public void addAttachment(AddAttachmentEvent e)
    {
        // This event is called before adding attachment to output PDF
        // document while copying the attachments from source PDF to
        // output PDF and while attaching the input files to PDF (when
        // the conversoinMode is
        // CONVERT_FIRST_FILE_AND_ATTACH_REST_AS_ORIGINAL or
        // CREATE_NEW_FILE_AND_ATTACH_ALL_AS_ORIGINAL).
        
        // if you don't want to copy attachments from source PDF to
        // output PDF you can skip the attachment.
//        if (e.isExistingAttachment())
//        {
//            e.setSkipAttachment(true);
//        }
    }
}
