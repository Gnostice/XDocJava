package simpleviewerdemo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JToolBar;

import com.gnostice.core.XDocException;
import com.gnostice.documents.FormatNotSupportedException;
import com.gnostice.documents.IncorrectPasswordException;
import com.gnostice.documents.controls.swing.printer.DocumentPrinter;
import com.gnostice.documents.controls.swing.viewer.DocumentViewer;
import com.gnostice.documents.controls.swing.viewer.DocumentViewerListener;
import com.gnostice.documents.controls.swing.viewer.ViewerNeedPasswordEvent;
import com.gnostice.documents.controls.swing.viewer.ViewerPageChangeEvent;
import com.gnostice.documents.controls.swing.viewer.ViewerPageLayoutChangeEvent;
import com.gnostice.documents.controls.swing.viewer.ViewerRenderErrorEvent;
import com.gnostice.documents.controls.swing.viewer.ViewerRotationChangeEvent;
import com.gnostice.documents.controls.swing.viewer.ViewerStateChangeEvent;
import com.gnostice.documents.controls.swing.viewer.ViewerZoomChangeEvent;
import com.gnostice.documents.pdf.PDFException;

public final class SimpleDocumentViewerDemo extends JFrame implements
    ActionListener, DocumentViewerListener
{
    private static final long serialVersionUID = 1L;

    String docPath;

    DocumentViewer viewer;
    
    DocumentPrinter printer;

    // Controls for toolbar
    JFileChooser fc;

    JButton btnLoad;

    JButton btnPrint;

    JButton btnClose;

    JButton btnFirstPage = null;

    JButton btnPreviousPage = null;

    JButton btnNextPage = null;

    JButton btnLastPage = null;

    JLabel lblCurrentPageNum = null;
    
    static final String PRODUCT_NAME = "Gnostice Multi-Format Document Viewer";

    public static void main(String[] args)
    {
        SimpleDocumentViewerDemo vd = new SimpleDocumentViewerDemo();

        vd.setSize(1024, 740);
        vd.setTitle(PRODUCT_NAME);
        vd.setExtendedState(JFrame.MAXIMIZED_BOTH);
        vd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vd.setVisible(true);
    }

    public SimpleDocumentViewerDemo()
    {
        // File open dialog
        fc = new JFileChooser();
        fc.setCurrentDirectory(new File(System
            .getProperty("user.home")
            + File.separatorChar + "Desktop"));
        
        // Create a new viewer container
        viewer = new DocumentViewer();
        viewer.addViewerListener(this);
        
        
        // Create a new DocumentPrinter object
        try
        {
            printer = new DocumentPrinter();
        }
        catch (XDocException e)
        {
            e.printStackTrace();
        }
        
        // Prepare window
        getContentPane().setLayout(new BorderLayout());
        // Add viewer to Frame
        getContentPane().add(viewer, BorderLayout.CENTER);
        // Add toolbar at the top of the frame
        getContentPane().add(getTopToolBar(), BorderLayout.NORTH);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent arg0)
            {
                closeFile();
                try
                {
                    viewer.dispose();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    private JToolBar getTopToolBar()
    {
        // Create a toolbar
        JToolBar topToolbar = new JToolBar("Tools",
            JToolBar.HORIZONTAL);
        topToolbar.setFloatable(false);
        topToolbar.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Create a button on the toolbar
        btnLoad = new JButton("Open");
        btnLoad.setToolTipText("Open");
        // Ensure that actionPerformed method of this
        // class receives events from the button
        btnLoad.addActionListener(this);

        btnPrint = new JButton("Print");
        btnPrint.setToolTipText("Print");
        btnPrint.addActionListener(this);

        btnFirstPage = new JButton("<<");
        btnFirstPage.setToolTipText("First Page");
        btnFirstPage.addActionListener(this);

        btnPreviousPage = new JButton("<");
        btnPreviousPage.setToolTipText("Previous Page");
        btnPreviousPage.addActionListener(this);

        btnNextPage = new JButton(">");
        btnNextPage.setToolTipText("Next Page");
        btnNextPage.addActionListener(this);

        btnLastPage = new JButton(">>");
        btnLastPage.setToolTipText("Last Page");
        btnLastPage.addActionListener(this);

        btnClose = new JButton("Close");
        btnClose.setToolTipText("Close");
        btnClose.addActionListener(this);

        lblCurrentPageNum = new JLabel();

        // Add items to toolbar
        topToolbar.add(btnLoad);
        topToolbar.add(btnPrint);
        topToolbar.add(btnClose);
        topToolbar.add(btnFirstPage);
        topToolbar.add(btnPreviousPage);
        topToolbar.add(btnNextPage);
        topToolbar.add(btnLastPage);
        topToolbar.add(lblCurrentPageNum);

        return topToolbar;
    }

    // This method processes events sent by
    // buttons on the toolbar.
    // This method is from the ActionListener
    // interface implemented by this class
    public void actionPerformed(ActionEvent ae)
    {
        Object sourceButton = ae.getSource();

        try
        {
            if (ae.getSource() == btnLoad)
            {
                loadFile();
            }
            else if (ae.getSource() == btnPrint)
            {
                printFile();
            }
            else if (sourceButton == btnFirstPage)
            {
                viewFirstPage();
            }
            else if (sourceButton == btnPreviousPage)
            {
                viewPreviousPage();
            }
            else if (sourceButton == btnNextPage)
            {
                viewNextPage();
            }
            else if (sourceButton == btnLastPage)
            {
                viewLastPage();
            }
            else if (sourceButton == btnClose)
            {
                closeFile();
            }
        }
        catch (XDocException ex)
        {
            JOptionPane.showMessageDialog(this.getParent(), ex
                .getMessage(), PRODUCT_NAME,
                JOptionPane.ERROR_MESSAGE);
        }
        catch (IOException ioEx)
        {
            JOptionPane.showMessageDialog(this.getParent(), ioEx
                .getMessage(), PRODUCT_NAME,
                JOptionPane.ERROR_MESSAGE);
        }
    }

    // Display the first page of the document
    private void viewFirstPage() throws IOException, XDocException
    {
        if (viewer.isDocumentLoaded())
        {
            viewer.firstPage();
            updatePageNumberLabel(viewer.getCurrentPageNumber());
        }
    }

    // Display the previous page of the document
    private void viewPreviousPage() throws IOException, XDocException
    {
        if (viewer.isDocumentLoaded())
        {
            viewer.previousPage();
            updatePageNumberLabel(viewer.getCurrentPageNumber());
        }
    }

    // Display the next page of the document
    private void viewNextPage() throws IOException, XDocException
    {
        if (viewer.isDocumentLoaded())
        {
            viewer.nextPage();
            updatePageNumberLabel(viewer.getCurrentPageNumber());
        }
    }

    // Display the last page of the document
    private void viewLastPage() throws IOException, XDocException
    {
        if (viewer.isDocumentLoaded())
        {
            viewer.lastPage();
            updatePageNumberLabel(viewer.getCurrentPageNumber());
        }
    }

    // Update label with number of current page
    private void updatePageNumberLabel(int pageNum)
    {
        if (pageNum == 0)
        {
            lblCurrentPageNum.setText("");
        }
        else
        {
            lblCurrentPageNum.setText("Showing Page "
                + String.valueOf(pageNum) + " of "
                + String.valueOf(viewer.getPageCount()));
        }
    }
    
    // Display a PDF document in the viewer
    protected void loadFile()
    {
        int fcState = fc.showOpenDialog(this);

        // Exit if a file has not been selected succesfully
        if (fcState != JFileChooser.APPROVE_OPTION)
        {
            return;
        }

        File selectedFile = fc.getSelectedFile();

        // Exit if it is not a valid file
        if ( !(selectedFile.exists() && selectedFile.isFile()))
        {
            JOptionPane.showMessageDialog(this, "The File \""
                + selectedFile.getAbsoluteFile()
                + "\" does not exist", PRODUCT_NAME,
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Remove the display of any other document
        closeFile();

        docPath = selectedFile.getAbsolutePath();
        setTitle(selectedFile.getName() + " - Gnostice Document Viewer");

        try
        {
            // Load the document in the viewer
            viewer.loadDocument(docPath, "");
            
            // calling refresh() method on the viewer is optional as
            // it will be refreshed automatically the document is
            // associated with the viewer using setDocument() method
//            viewer.refresh();
            
            // Update label with number of current page
            updatePageNumberLabel(viewer.getCurrentPageNumber());
        }
        catch (XDocException ex)
        {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                PRODUCT_NAME, JOptionPane.ERROR_MESSAGE);
        }
        catch (IOException ioEx)
        {
            JOptionPane.showMessageDialog(this, ioEx.getMessage(),
                PRODUCT_NAME, JOptionPane.ERROR_MESSAGE);
        }
    }

    // Print the displayed document
    protected void printFile() throws IOException, PDFException
    {
        if (viewer.isDocumentLoaded())
        {
            if (printer != null)
            {
                try
                {
                    printer.loadDocument(docPath, "");
                    printer.showPrintDialog();
                }
                catch (FormatNotSupportedException xDocEx)
                {
                    JOptionPane.showMessageDialog(this, xDocEx.getMessage(),
                        "Gnostice XDoc Viewer", JOptionPane.ERROR_MESSAGE);
                }
                catch (IncorrectPasswordException xDocEx)
                {
                    JOptionPane.showMessageDialog(this, xDocEx.getMessage(),
                        "Gnostice XDoc Viewer", JOptionPane.ERROR_MESSAGE);
                }
                catch (XDocException xDocEx)
                {
                    JOptionPane.showMessageDialog(this, xDocEx.getMessage(),
                        "Gnostice XDoc Viewer", JOptionPane.ERROR_MESSAGE);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(this,
                    "Could not create PdfPrinter object",
                    "PdfPrinter object creation failed",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this,
                "Document not loaded, Load a PDF Document to Print",
                "Gnostice PDF Viewer", JOptionPane.ERROR_MESSAGE);
            loadFile();
        }
    }

    // Remove the display of any document in viewer and
    // dispose close any I/O streams associated with
    // the document reader
    protected void closeFile()
    {
        lblCurrentPageNum.setText("");
        setTitle(PRODUCT_NAME);

        if (viewer.isDocumentLoaded())
        {
            try
            {
                // this will automatically dispose the objects
                // associated with the viewer object
                viewer.closeDocument();
            }
            catch (Exception ioEx)
            {
                JOptionPane.showMessageDialog(this,
                    ioEx.getMessage(), PRODUCT_NAME,
                    JOptionPane.ERROR_MESSAGE);
            }
        }
        
        if (printer != null && printer.isDocumentLoaded())
        {
            try
            {
                // this will automatically dispose the objects
                // associated with the printer object
                printer.closeDocument();
            }
            catch (Exception ioEx)
            {
                JOptionPane.showMessageDialog(this,
                    ioEx.getMessage(), PRODUCT_NAME,
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void pageChanged(ViewerPageChangeEvent pageChangeEvent)
    {
        lblCurrentPageNum.setText("Showing Page "
            + String.valueOf(pageChangeEvent.getCurrentPageNumber()) + " of "
            + String.valueOf(viewer.getPageCount()));
    }

    @Override
    public void zoomChanged(ViewerZoomChangeEvent zoomChangeEvent)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void pageLayoutChanged(
        ViewerPageLayoutChangeEvent pageLayoutChangeEvent)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void rotationChanged(
        ViewerRotationChangeEvent rotationChangeEvent)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void viewerStateChanged(ViewerStateChangeEvent viewerChangeEvent)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void error(ViewerRenderErrorEvent renderErrorEvent)
    {
        // TODO Auto-generated method stub
        
    }

 // Use a prompt to obtain document password from the user
    @Override
    public void needPassword(
        ViewerNeedPasswordEvent needPasswordEvent)
    {
        JPanel panel = new JPanel(new FlowLayout());

        JPasswordField field = new JPasswordField(10);
        panel.add(new JLabel("Password: "));
        panel.add(field);

        field.requestFocus();

        JOptionPane.showMessageDialog(this, panel,
            PRODUCT_NAME, JOptionPane.OK_OPTION
                | JOptionPane.QUESTION_MESSAGE);

        String pwd = "";

        char[] pin = field.getPassword();
        try
        {
            pwd = new String(pin);
        }
        finally
        {
            Arrays.fill(pin, ' ');
            field.setText("");
        }

        needPasswordEvent.setPassword(pwd);
        
    }

}