import java.util.List;

import com.gnostice.core.XDocException;
import com.gnostice.core.digitizationengine.DigitizationMode;
import com.gnostice.core.digitizationengine.ImageEnhancementMode;
import com.gnostice.core.digitizationengine.ImageEnhancementSettings;
import com.gnostice.core.dom.IDigitizedPage;
import com.gnostice.core.dom.IDigitizedSegment;
import com.gnostice.core.dom.IDigitizedText;
import com.gnostice.core.dom.IDocItem;
import com.gnostice.core.dom.Paragraph;
import com.gnostice.core.dom.Run;
import com.gnostice.core.dom.Text;
import com.gnostice.core.dom.TextRun;
import com.gnostice.documents.ConverterDigitizerSettings;
import com.gnostice.documents.ConverterEndDigitizePageEvent;
import com.gnostice.documents.ConverterException;
import com.gnostice.documents.ConverterListenerAdapter;
import com.gnostice.documents.DocumentConverter;
import com.gnostice.documents.FormatNotSupportedException;
import com.gnostice.xtremedocumentstudio.Framework;

/*
 * Convert from scanned documents to searchable PDFs. This class
 * extends ConverterListenerAdapter to override only the
 * endDigitizePage event to get the digitized page elements, iterate
 * through the elements, modify/correct the digitized text and set the
 * corrected one to the text elements.
 */
public class Demo11_ScannedDocToSearchablePDF_Using_DigitizePageEvents
    extends ConverterListenerAdapter
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
        
        // Register to ConverterListener events
        converter.addConverterListener(new Demo11_ScannedDocToSearchablePDF_Using_DigitizePageEvents());

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
    
    // override endDigitizePage in order to edit the
    // recognized/digitized page items.
    @Override
    public void endDigitizePage(ConverterEndDigitizePageEvent e)
    {
        super.endDigitizePage(e);
        
        IDigitizedPage digitizedPage = e.getDigitizedPage();

        // digitizedPage contains list of digitized segments
        List<IDigitizedSegment> digitizedSegments = digitizedPage
            .getDigitizedSegments();
        
        IDigitizedSegment digitizedSegment = null;
        List<IDocItem> digitizedSegmentElements = null;
        Paragraph para = null;
        for(int i = 0; i < digitizedSegments.size(); i++)
        {
            digitizedSegment = digitizedSegments.get(i);
            
            // Each digitized segment contains list of IDocItem
            // objects. Usually after OCRing IDocItem objects will be
            // instances of Paragraphs.
            digitizedSegmentElements = digitizedSegment.getElements();
            
            for (int j = 0; j < digitizedSegmentElements.size(); j++)
            {
                para = (Paragraph) digitizedSegmentElements.get(j);
                
                // Each paragraph contains list of Run object
                List<Run> runs = para.getRuns();
                
                for (int k = 0; k < runs.size(); k++)
                {
                    Run run = runs.get(k);
                    
                    if (run instanceof TextRun)
                    {
                        TextRun textRun = (TextRun) run;
                        
                        // Each TextRun contains list of Text objects
                        // that share the same text attributes such as
                        // FontFace, FontSize and so on. 
                        List<Text> texts = textRun.getTexts();
                        
                        for (int l = 0; l < texts.size(); l++)
                        {
                            Text txt = texts.get(l);
                            
                            String digitizedText = txt.getText();
                            
                            // Print digitizedText and its confidence value
                            if (!digitizedText.trim().isEmpty())
                            {
                                System.out.print(digitizedText);
                                
                                if (txt instanceof IDigitizedText)
                                {
                                    IDigitizedText dText = (IDigitizedText) txt;
                                    float confidenceVal = dText.getConfidenceValue();
                                    System.out.print(", Confidence value: " + confidenceVal);
                                    System.out.print(", BoundingRect: " + txt.getBoundingRect());
                                }
                                
                                System.out.println();
                            }
                            
                            // The digitizedText can be manually corrected
                            // here and set the corrected one to the Text
                            // object which will be committed while
                            // saving the document.
//                            String correctedDigitizedText = "";
//                            txt.setText(correctedDigitizedText);
                        }
                    }
                }
            }
        }
    }
}
