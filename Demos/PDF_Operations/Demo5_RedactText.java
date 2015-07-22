
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;

import com.gnostice.core.XDocException;
import com.gnostice.documents.DocumentListenerAdapter;
import com.gnostice.documents.FormatNotSupportedException;
import com.gnostice.documents.IncorrectPasswordException;
import com.gnostice.documents.InsufficientPermissionsException;
import com.gnostice.documents.NeedPasswordEvent;
import com.gnostice.documents.PageRangeSettings;
import com.gnostice.documents.SearchItem;
import com.gnostice.documents.enums.Operation;
import com.gnostice.documents.enums.PageOrdering;
import com.gnostice.documents.enums.PageRange;
import com.gnostice.documents.enums.SearchMode;
import com.gnostice.documents.enums.SearchOption;
import com.gnostice.documents.pdf.CleanUpOptions;
import com.gnostice.documents.pdf.FillSettings;
import com.gnostice.documents.pdf.FontSizingMode;
import com.gnostice.documents.pdf.IncludeAdditionalItems;
import com.gnostice.documents.pdf.PDF;
import com.gnostice.documents.pdf.RedactSettings;
import com.gnostice.documents.pdf.encodings.PDFEncodings;
import com.gnostice.documents.pdf.fonts.PDFFont;
import com.gnostice.documents.pdf.graphics.PDFBrush;
import com.gnostice.documents.pdf.graphics.PDFPen;
import com.gnostice.xtremedocumentstudio.Framework;

public class Demo5_RedactText 
{
    static
    {
        // Activate the product
        Framework.activate("G23VRW:34KEOVT:5PVZ16V:D9OV8",
            "ENE8RI6Z:45019ERAE:7ERFGQGR6:VTOR10");
    }
    
    public static void main(String[] args) throws IOException,
        FormatNotSupportedException, IncorrectPasswordException,
        XDocException
    {
        // Specify input file name
        String inputFileName = "InputDocument.pdf";
        
        // Specify output file name
        String outputFileName = "RedactedOutput.pdf";
        
        // Create the PDF object
        PDF pdfDoc = new PDF();
        
        // Add the DocumentListener to listen to the needPassword event
        pdfDoc.addDocumentListener(new DocumentListenerAdapter()
        {
            @Override
            public void needPassword(NeedPasswordEvent e)
            {
                // This event is called when a password is
                // required for loading/opening the document or a
                // password required to perform the requested
                // operation on the document.
                
                if (e.getOperation() == Operation.LOAD_DOCUMENT)
                {
                    e.setPassword("OpenPassword");
                }
                else // Operation could be MODIFY_CONTENTS for RedactText operation
                {
                    e.setPassword("OwnerPassword");
                    
                    // If user is allowed to override owner
                    // permission and force performing the
                    // operation then the forceFullPermission flag
                    // can be set to true. Note: this is false by
                    // default.
//                    e.setForceFullPermission(true);
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
                else
                {
                    e.setCallAgainIfUnsuccessful(false);
                }
            }
        });
        pdfDoc.loadDocument(inputFileName, "");
        
        // Specify the page number which should be considered while
        // searching the text for redaction.
        PageRangeSettings pageRange = new PageRangeSettings("-",
            PageRange.ALL, PageOrdering.NORMAL);
       
        // Specify the list of SearchItem objects
        ArrayList<SearchItem> searchItems = new ArrayList<SearchItem>();
        searchItems.add(new SearchItem("SearchText1"));
        // By default SearchMode is LITERAL and SearchOption is none.
        searchItems.add(new SearchItem("SearchText2", SearchMode.REGEX,
                EnumSet.of(SearchOption.CASE_SENSITIVE)));
        
        // Specify outline color to stroke the redacted text boundary.
        // No outlining when pen is null.
        PDFPen pen = new PDFPen(Color.RED);
        
        // Specify fill color to fill the redacted text boundary
        // No Filling when brush is null
        PDFBrush brush = new PDFBrush(Color.YELLOW);
        
        // Specify the string that should be written at the place of
        // redacted text.
        String replaceText = "";
        
        // Specify the font with which the replaceString should be
        // written.
        PDFFont font = PDFFont.create(
            "C:\\FontsCollection\\ARIALUNI.TTF", 10,
            PDFEncodings.CP1252);
        
        // Specify the color with which the replaceText should be
        // written.
        // If this is null then the color of the source/redacted text is
        // used.
        Color replaceTextColor = null;
        
        // Specify if the replaceText should be automatically
        // sized to fit the redacted text boundary.
        FontSizingMode fontSizingMode = FontSizingMode.AUTO_FIT;
        
        // Create FillSettings object to specify how the redacted area
        // should be filled.
        FillSettings fillSettings = new FillSettings(pen, brush);
        fillSettings.getFillText().setReplaceText(replaceText);
        fillSettings.getFillText().setFont(font);
        fillSettings.getFillText().setFontSizingMode(fontSizingMode);
        fillSettings.getFillText().setReplaceTextColor(replaceTextColor);
        
        // Create RedactSettings and specify what to be redacted in
        // addition to text specified by the searchText. 
        RedactSettings redactSettings = new RedactSettings();
        
        // Specifies whether to remove the associated annotations
        // which intersect the bounding rectangles of the searchText. 
        redactSettings.setRemoveAssociatedAnnotations(true);

        // Specifies whether the redactText should search, redact, and
        // replaceText in these specified additional items other than
        // visible page text contents of the document. 
        EnumSet<IncludeAdditionalItems> includeAdditionalItems = EnumSet
            .noneOf(IncludeAdditionalItems.class);
        // Specifies whether to search, redact, and replace in
        // document properties (such as Author, Title, and others) of
        // the document.
        includeAdditionalItems.add(IncludeAdditionalItems.DOCUMENT_PROPERTIES);
        redactSettings.setIncludeAdditionalItems(includeAdditionalItems );
        
        // Specifies whether the redacted affected additional items
        // should be considered for clean-up. 
        EnumSet<CleanUpOptions> cleanUpOptions = EnumSet.noneOf(CleanUpOptions.class);
        // Specifies whether to remove the Link actions set for
        // Bookmarks and Link Annotations which were undergone
        // redaction.
        cleanUpOptions.add(CleanUpOptions.REMOVE_AFFECTED_LINK_ACTIONS);
        redactSettings.setCleanUpOptions(cleanUpOptions );
        
        try
        {
            // call redactText by supplying the above options
            pdfDoc.redactText(searchItems, pageRange, fillSettings, redactSettings);
        }
        catch (InsufficientPermissionsException e)
        {
            // if the correct password is not provided in the
            // needPassword event and even if forceFullPermissions is
            // false then this exception will be thrown.
            
            System.out.println("Input Document does not have "
                + "permission to redact text in the document!");
            e.printStackTrace();
        }
        
        // Save the changes to the output file name
        pdfDoc.save(outputFileName);
        
        // Close the document
        pdfDoc.closeDocument();
    }
}
