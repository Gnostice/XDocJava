package viewerappletdemo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.gnostice.core.XDocException;
import com.gnostice.documents.FormatNotSupportedException;
import com.gnostice.documents.IncorrectPasswordException;
import com.gnostice.documents.PageLayout;
import com.gnostice.documents.PageLayoutMode;
import com.gnostice.documents.controls.swing.printer.DocumentPrintListener;
import com.gnostice.documents.controls.swing.printer.DocumentPrinter;
import com.gnostice.documents.controls.swing.printer.PrinterBeginJobEvent;
import com.gnostice.documents.controls.swing.printer.PrinterBeginPageEvent;
import com.gnostice.documents.controls.swing.printer.PrinterEndJobEvent;
import com.gnostice.documents.controls.swing.printer.PrinterEndPageEvent;
import com.gnostice.documents.controls.swing.printer.PrinterNeedPasswordEvent;
import com.gnostice.documents.controls.swing.printer.PrinterRenderErrorEvent;
import com.gnostice.documents.controls.swing.viewer.DocumentViewer;
import com.gnostice.documents.controls.swing.viewer.DocumentViewerListener;
import com.gnostice.documents.controls.swing.viewer.ViewerNeedPasswordEvent;
import com.gnostice.documents.controls.swing.viewer.ViewerPageChangeEvent;
import com.gnostice.documents.controls.swing.viewer.ViewerPageLayoutChangeEvent;
import com.gnostice.documents.controls.swing.viewer.ViewerRenderErrorEvent;
import com.gnostice.documents.controls.swing.viewer.ViewerRotationChangeEvent;
import com.gnostice.documents.controls.swing.viewer.ViewerStateChangeEvent;
import com.gnostice.documents.controls.swing.viewer.ViewerZoomChangeEvent;
import com.gnostice.documents.controls.swing.viewer.Zoom;
import com.gnostice.documents.controls.swing.viewer.ZoomMode;
import com.gnostice.documents.enums.RotationAngle;

public final class ViewerAppletDemo extends JApplet implements
ActionListener, ItemListener, WindowListener, DocumentViewerListener, DocumentPrintListener
{
    private static final long serialVersionUID = 1L;

    // Containers
    DocumentPrinter printer;
    private DocumentViewer viewer;

    // Controls for top panel
    private JFileChooser fc;

    private JButton btnLoad;

    private JButton btnPrint;

    // private JButton btnDocInfo;

    private String docPath;

    private JToggleButton btnActualSize = null;

    private JToggleButton btnFitPage = null;

    private JToggleButton btnFitWidth = null;

    private JButton btnZoomIn = null;

    private JComboBox cboZoomPercentage = null;

    private JButton btnZoomOut = null;

    private JButton btnRotateCounterClockwise = null;

    private JButton btnRotateClockwise = null;
    
    private JButton btnRotatePageCounterClockwise = null;

    private JButton btnRotatePageClockwise = null;
    
    private JButton btnResetPageRotation = null;

    // Controls for bottom panel
    private JButton btnFirstPage = null;

    private JButton btnPreviousPage = null;

    private JTextField txtGoToPage = null;

    private JButton btnNextPage = null;

    private JButton btnLastPage = null;

    // JMenuItems
    private JMenuItem mnuOpenItem;

    private JMenuItem mnuPrintItem;

    private JMenuItem mnuDocInfo;

    private JMenuItem mnuCloseItem;

    private JMenuItem mnuExitItem;

    private JMenuItem mnuFirstPageItem;

    private JMenuItem mnuPreviousPageItem;

    private JMenuItem mnuNextPageItem;

    private JMenuItem mnuLastPageItem;

    private JMenuItem mnuActualSizeItem;

    private JMenuItem mnuFitPageItem;

    private JMenuItem mnuFitWidthItem;

    private JMenuItem mnuZoomInItem;

    private JMenuItem mnuZoomOutItem;

    private JMenuItem mnuRotateAntiClokwiseItem;

    private JMenuItem mnuRotateClokwiseItem;

    private JCheckBoxMenuItem chkMnuShowLabels;

    private JRadioButtonMenuItem radioMnuLayoutSinglePage;

    private JRadioButtonMenuItem radioMnuLayoutSinglePageContinuous;

    private JRadioButtonMenuItem radioMnuLayoutSideBySide;

    private JRadioButtonMenuItem radioMnuLayoutSideBySideContinuous;

    private JRadioButtonMenuItem radioMnuLayoutAutoFitColumnsInWindow;

    private JRadioButtonMenuItem radioMnuLayoutUserDefined;

    private JRadioButtonMenuItem radioMnuDPI_72;

    private JRadioButtonMenuItem radioMnuDPI_96;

    private JRadioButtonMenuItem radioMnuDPI_110;

    private JRadioButtonMenuItem radioMnuScrollMode_BLIT_SCROLL_MODE;

    private JRadioButtonMenuItem radioMnuScrollMode_BACKINGSTORE_SCROLL_MODE;

    private JRadioButtonMenuItem[] mnuLAFItems;

    private JMenuItem mnuAboutItem;

    private double currentZoomPercentage;

    private String lookAndFeelClassName;

    private File recentDir;

    private AboutGnosticeXDOCJava aboutDialog;

    private DocumentInfoDialog docInfoDialog;

    private JToolBar topToolBar;

    private JToolBar statusBar;

    private JProgressBar progress;

    private JToggleButton btnSinglePage = null;

    private JToggleButton btnContinuous = null;

    private JToggleButton btnSideBySide = null;

    private JToggleButton btnSideBySideContinuous = null;

    private JToggleButton btnAutoFitColumnsInWindow = null;

    private JToggleButton btnCustomLayout = null;

    private JTextField txtCustomColumnCount = null;

    private boolean canDropDocument = true;

    // PdfSearchElement currentSearchElement = null;

    String quickFindStr = "";
    
    String pwd = "";

    private void setViewerPreferences()
    {
        //Set all the preferences that need to be applied on the viewer in this method
        viewer.getPreferences().getPasswordSettings().setAttempts(4);
        viewer.getPreferences().getPasswordSettings().setShowDialog(true);
    }

    private JToolBar getStatusBar()
    {
        progress = new JProgressBar();
        progress.setStringPainted(true);
        progress.setString("Loading...");

        statusBar = new JToolBar("StatusBar", JToolBar.HORIZONTAL);
        statusBar.setLayout(new GridLayout(1, 5, 20, 0));
        statusBar.setFloatable(false);

        int preferredWidth = 150;
        int preferredHeight = 25;
        statusBar.setSize(preferredWidth, preferredHeight);
        statusBar.setBounds(0, 0, 100, 25);
        statusBar.setPreferredSize(new Dimension(preferredWidth,
            preferredHeight));
        statusBar.setBorder(BorderFactory.createLoweredBevelBorder());

        statusBar.add(progress, -1);
        statusBar.setOpaque(false);

        statusBar.add(new JPanel(), -1);
        statusBar.add(new JPanel(), -1);
        statusBar.add(new JPanel(), -1);
        statusBar.add(new JPanel(), -1);

        progress.setVisible(false);

        return statusBar;
    }

    private JToolBar getTopToolBar()
    {
        ImageIcon loadIcon = new ImageIcon(getClass().getResource(
        "icons/Open.gif"));
        btnLoad = new JButton(loadIcon);
        btnLoad.setToolTipText("Open");
        btnLoad.addActionListener(this);

        ImageIcon printIcon = new ImageIcon(getClass().getResource(
        "icons/Print.gif"));
        btnPrint = new JButton(printIcon);
        btnPrint.setToolTipText("Print");
        btnPrint.addActionListener(this);

        ImageIcon actualSizeIcon = new ImageIcon(getClass()
            .getResource("icons/ActualSize.gif"));
        btnActualSize = new JToggleButton("Actual Size",
            actualSizeIcon);
        btnActualSize.setToolTipText("Actual Size");
        btnActualSize.addActionListener(this);

        ImageIcon fitPageIcon = new ImageIcon(getClass().getResource(
        "icons/FitPage.gif"));
        btnFitPage = new JToggleButton("Fit Page", fitPageIcon);
        btnFitPage.setToolTipText("Fit Page");
        btnFitPage.addActionListener(this);

        ImageIcon fitWidthIcon = new ImageIcon(getClass()
            .getResource("icons/FitWidth.gif"));
        btnFitWidth = new JToggleButton("Fit Width", fitWidthIcon);
        btnFitWidth.setToolTipText("Fit Width");
        btnFitWidth.addActionListener(this);

        ImageIcon zoomOutIcon = new ImageIcon(getClass().getResource(
        "icons/ZoomOut.gif"));
        btnZoomOut = new JButton("Zoom Out", zoomOutIcon);
        btnZoomOut.setToolTipText("Zoom Out");
        btnZoomOut.addActionListener(this);

        ImageIcon zoomInIcon = new ImageIcon(getClass().getResource(
        "icons/ZoomIn.gif"));
        btnZoomIn = new JButton("Zoom In", zoomInIcon);
        btnZoomIn.setToolTipText("Zoom In");
        btnZoomIn.addActionListener(this);

        cboZoomPercentage = new JComboBox();
        cboZoomPercentage.setToolTipText("Zoom");
        cboZoomPercentage.setMinimumSize(new Dimension(100, 25));
        cboZoomPercentage.setSize(new Dimension(100, 25));
        cboZoomPercentage.setPreferredSize(new Dimension(100, 25));
        cboZoomPercentage.addItem("6400%");
        cboZoomPercentage.addItem("3200%");
        cboZoomPercentage.addItem("2400%");
        cboZoomPercentage.addItem("1600%");
        cboZoomPercentage.addItem("1200%");
        cboZoomPercentage.addItem("800%");
        cboZoomPercentage.addItem("600%");
        cboZoomPercentage.addItem("400%");
        cboZoomPercentage.addItem("300%");
        cboZoomPercentage.addItem("200%");
        cboZoomPercentage.addItem("150%");
        cboZoomPercentage.addItem("125%");
        cboZoomPercentage.addItem("100%");
        cboZoomPercentage.addItem("75%");
        cboZoomPercentage.addItem("66.67%");
        cboZoomPercentage.addItem("50%");
        cboZoomPercentage.addItem("33.33%");
        cboZoomPercentage.addItem("25%");
        cboZoomPercentage.addItem("12.5%");
        cboZoomPercentage.addItem("8.33%");
        cboZoomPercentage.addItem("6.25%");
        cboZoomPercentage.addItem("1%");
        cboZoomPercentage.addItem("0.5%");
        cboZoomPercentage.addItem("0.25%");
        cboZoomPercentage.addItem("0.01%");

        cboZoomPercentage.setEditable(true);
        cboZoomPercentage.setSelectedItem("100%");
        currentZoomPercentage = 100.0;
        cboZoomPercentage.addItemListener(this);

        ImageIcon antiClockwiseIcon = new ImageIcon(getClass()
            .getResource("icons/CounterClockwise.gif"));
        btnRotateCounterClockwise = new JButton(
            "Rotate Counterclockwise", antiClockwiseIcon);
        btnRotateCounterClockwise
        .setToolTipText("Rotate Counterclockwise");
        btnRotateCounterClockwise.addActionListener(this);

        ImageIcon clockwiseIcon = new ImageIcon(getClass()
            .getResource("icons/Clockwise.gif"));
        btnRotateClockwise = new JButton("Rotate Clockwise",
            clockwiseIcon);
        btnRotateClockwise.setToolTipText("Rotate Clockwise");
        btnRotateClockwise.addActionListener(this);

        ImageIcon firstPageIcon = new ImageIcon(getClass()
            .getResource("icons/FirstPage.gif"));
        btnFirstPage = new JButton(firstPageIcon);
        btnFirstPage.setToolTipText("First Page");
        btnFirstPage.addActionListener(this);

        ImageIcon previousPageIcon = new ImageIcon(getClass()
            .getResource("icons/PreviousPage.gif"));
        btnPreviousPage = new JButton(previousPageIcon);
        btnPreviousPage.setToolTipText("Previous Page");
        btnPreviousPage.addActionListener(this);

        txtGoToPage = new JTextField(10);
        txtGoToPage.setMinimumSize(new Dimension(100, 25));
        txtGoToPage.setToolTipText("Go to Page");
        txtGoToPage.setHorizontalAlignment(JTextField.CENTER);
        txtGoToPage.setSize(100, 25);
        txtGoToPage.setPreferredSize(new Dimension(100, 25));
        txtGoToPage.addActionListener(this);
        txtGoToPage.addFocusListener(new FocusAdapter()
        {
            public void focusGained(FocusEvent fe)
            {
                txtGoToPage.setSelectionStart(0);
                txtGoToPage.setSelectionEnd(txtGoToPage.getText()
                    .length());
            }
        });

        ImageIcon nextPageIcon = new ImageIcon(getClass()
            .getResource("icons/NextPage.gif"));
        btnNextPage = new JButton(nextPageIcon);
        btnNextPage.setToolTipText("Next Page");
        btnNextPage.addActionListener(this);

        ImageIcon lastPageIcon = new ImageIcon(getClass()
            .getResource("icons/LastPage.gif"));
        btnLastPage = new JButton(lastPageIcon);
        btnLastPage.setToolTipText("Last Page");
        btnLastPage.addActionListener(this);

        btnFirstPage.setEnabled(false);
        btnPreviousPage.setEnabled(false);
        txtGoToPage.setEnabled(false);
        btnNextPage.setEnabled(false);
        btnLastPage.setEnabled(false);

        JPanel pageLayoutPanel = new JPanel();
        pageLayoutPanel.setLayout(new FlowLayout());

        ImageIcon continuousLayoutIcon = new ImageIcon(getClass()
            .getResource("icons/ContinuousLayout.gif"));
        btnContinuous = new JToggleButton("Continuous Layout",
            continuousLayoutIcon);
        btnContinuous.setToolTipText("Continuous Layout");
        btnContinuous.addActionListener(this);

        ImageIcon singlePageLayoutIcon = new ImageIcon(getClass()
            .getResource("icons/SinglePageLayout.gif"));
        btnSinglePage = new JToggleButton("Single Page Layout",
            singlePageLayoutIcon);
        btnSinglePage.setToolTipText("Single Page Layout");
        btnSinglePage.addActionListener(this);

        ImageIcon sideBySideIcon = new ImageIcon(getClass()
            .getResource("icons/SideBySideLayout.gif"));
        btnSideBySide = new JToggleButton("Side-by-Side Layout",
            sideBySideIcon);
        btnSideBySide.setToolTipText("Side-by-Side Layout");
        btnSideBySide.addActionListener(this);

        ImageIcon sideBySideContinuousIcon = new ImageIcon(getClass()
            .getResource("icons/SideBySideLayoutContinuous.gif"));
        btnSideBySideContinuous = new JToggleButton(
            "Side-by-Side Continuous Layout",
            sideBySideContinuousIcon);
        btnSideBySideContinuous
        .setToolTipText("Side-by-Side Continuous Layoutt");
        btnSideBySideContinuous.addActionListener(this);

        ImageIcon autoFitColumnsIcon = new ImageIcon(getClass()
            .getResource("icons/AutoFitInWindow.gif"));
        btnAutoFitColumnsInWindow = new JToggleButton(
            "Auto Fit Columns in Window", autoFitColumnsIcon);
        btnAutoFitColumnsInWindow
        .setToolTipText("Auto Fit Columns in Window");
        btnAutoFitColumnsInWindow.addActionListener(this);

        ImageIcon customLayoutIcon = new ImageIcon(getClass()
            .getResource("icons/CustomLayout.gif"));
        btnCustomLayout = new JToggleButton("Custom Layout",
            customLayoutIcon);
        btnCustomLayout.setToolTipText("Custom Layout");
        btnCustomLayout.addActionListener(this);

        txtCustomColumnCount = new JTextField(3);
        txtCustomColumnCount
        .setToolTipText("Number of page columns to be shown");
        txtCustomColumnCount
        .setHorizontalAlignment(JTextField.CENTER);
        txtCustomColumnCount.addActionListener(this);
        txtCustomColumnCount.addFocusListener(new FocusAdapter()
        {
            public void focusGained(FocusEvent fe)
            {
                txtCustomColumnCount.setSelectionStart(0);
                txtCustomColumnCount
                .setSelectionEnd(txtCustomColumnCount.getText()
                    .length());
            }
        });

        txtCustomColumnCount.addKeyListener(new KeyAdapter()
        {
            public void keyTyped(KeyEvent e)
            {
                if ( !Character.isDigit(e.getKeyChar()))
                {
                    e.consume();
                }

            }
        });

        ImageIcon pagesAntiClockwiseIcon = new ImageIcon(getClass()
            .getResource("icons/PagesCounterClockwise.gif"));
        
        Image img = pagesAntiClockwiseIcon.getImage();  
        Image newimg = img.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH);  
        pagesAntiClockwiseIcon = new ImageIcon(newimg);  
        
        btnRotatePageCounterClockwise = new JButton(
            "", pagesAntiClockwiseIcon);
        btnRotatePageCounterClockwise
        .setToolTipText("Rotate Pages Counterclockwise");
        btnRotatePageCounterClockwise.addActionListener(this);

        ImageIcon pagesClockwiseIcon = new ImageIcon(getClass()
            .getResource("icons/PagesClockwise.gif"));
        img = pagesClockwiseIcon.getImage();  
        newimg = img.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH);  
        pagesClockwiseIcon = new ImageIcon(newimg);  
        
        btnRotatePageClockwise = new JButton("",
            pagesClockwiseIcon);
        btnRotatePageClockwise.setToolTipText("Rotate Pages Clockwise");
        btnRotatePageClockwise.addActionListener(this);
        
        
        ImageIcon resetRotationIcon = new ImageIcon(getClass()
            .getResource("icons/ResetRotation.png"));
        img = resetRotationIcon.getImage();  
        newimg = img.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH);  
        resetRotationIcon = new ImageIcon(newimg);  
        btnResetPageRotation = new JButton("",
            resetRotationIcon);
        btnResetPageRotation.setToolTipText("Reset Rotation");
        btnResetPageRotation.addActionListener(this);
        
        
        // Create a horizontal toolbar
        JToolBar topToolbar = new JToolBar("Tools",
            JToolBar.HORIZONTAL);
        topToolbar.setFloatable(false);

        // topToolbar with FlowLayout
        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);
        topToolbar.setLayout(flowLayout);

        topToolbar.add(btnLoad);
        topToolbar.add(btnPrint);

        topToolbar.addSeparator();
        topToolbar.add(btnFirstPage);
        topToolbar.add(btnPreviousPage);
        topToolbar.add(txtGoToPage);
        topToolbar.add(btnNextPage);
        topToolbar.add(btnLastPage);

        topToolbar.addSeparator();
        topToolbar.add(btnActualSize);
        topToolbar.add(btnFitPage);
        topToolbar.add(btnFitWidth);

        topToolbar.addSeparator();
        topToolbar.add(btnZoomOut);
        topToolbar.add(cboZoomPercentage);
        topToolbar.add(btnZoomIn);

        topToolbar.addSeparator();
        topToolbar.add(btnRotateCounterClockwise);
        topToolbar.add(btnRotateClockwise);

        topToolbar.addSeparator();
        topToolbar.add(btnSinglePage);
        topToolbar.add(btnContinuous);
        topToolbar.add(btnSideBySide);
        topToolbar.add(btnSideBySideContinuous);
        topToolbar.add(btnAutoFitColumnsInWindow);
        topToolbar.add(btnCustomLayout);

        topToolbar.addSeparator();
        topToolbar.add(txtCustomColumnCount);

        topToolbar.add(btnRotatePageCounterClockwise);
        topToolbar.add(btnRotatePageClockwise);
        topToolbar.add(btnResetPageRotation);
        
        
        btnActualSize.setEnabled(false);
        btnFitPage.setEnabled(false);
        btnFitWidth.setEnabled(false);
        btnZoomOut.setEnabled(false);
        cboZoomPercentage.setEnabled(false);
        btnZoomIn.setEnabled(false);
        btnRotateCounterClockwise.setEnabled(false);
        btnRotateClockwise.setEnabled(false);
        btnRotatePageCounterClockwise.setEnabled(false);
        btnRotatePageClockwise.setEnabled(false);
        btnResetPageRotation.setEnabled(false);
        
        btnContinuous.setEnabled(false);
        btnSinglePage.setEnabled(false);
        btnSideBySide.setEnabled(false);
        btnSideBySideContinuous.setEnabled(false);
        btnAutoFitColumnsInWindow.setEnabled(false);
        btnCustomLayout.setEnabled(false);
        txtCustomColumnCount.setEnabled(false);

        // By default set the Actual Size fit mode as selected
        btnActualSize.setSelected(true);

        return topToolbar;
    }

    private void createMenus()
    {
        JMenuBar menuBar = new JMenuBar();

        // File Menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');

        mnuOpenItem = new JMenuItem("Open...", 'O');
        mnuOpenItem.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_O, InputEvent.CTRL_MASK));
        mnuOpenItem.addActionListener(this);

        mnuPrintItem = new JMenuItem("Print...", 'P');
        mnuPrintItem.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_P, InputEvent.CTRL_MASK));
        mnuPrintItem.addActionListener(this);

        mnuDocInfo = new JMenuItem("Document Properties", 'D');
        mnuDocInfo.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_D, InputEvent.CTRL_MASK));
        mnuDocInfo.addActionListener(this);
        mnuDocInfo.setEnabled(false);

        mnuCloseItem = new JMenuItem("Close", 'C');
        mnuCloseItem.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_W, InputEvent.CTRL_MASK));
        mnuCloseItem.addActionListener(this);
        mnuCloseItem.setEnabled(false);

        mnuExitItem = new JMenuItem("Exit", 'E');
        mnuExitItem.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_Q, InputEvent.CTRL_MASK));
        mnuExitItem.addActionListener(this);

        fileMenu.add(mnuOpenItem);
        fileMenu.add(mnuPrintItem);
        fileMenu.add(mnuDocInfo);
        fileMenu.add(new JSeparator());
        fileMenu.add(mnuCloseItem);
        fileMenu.add(new JSeparator());
        fileMenu.add(mnuExitItem);

        menuBar.add(fileMenu);

        // View Menu
        JMenu viewMenu = new JMenu("View");
        viewMenu.setMnemonic('V');

        mnuFirstPageItem = new JMenuItem("First Page", 'F');
        mnuFirstPageItem.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_HOME, InputEvent.CTRL_DOWN_MASK
            | InputEvent.SHIFT_DOWN_MASK));
        mnuFirstPageItem.addActionListener(this);

        mnuPreviousPageItem = new JMenuItem("Previous Page", 'P');
        mnuPreviousPageItem.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_PAGE_UP, InputEvent.CTRL_DOWN_MASK
            | InputEvent.SHIFT_DOWN_MASK));
        mnuPreviousPageItem.addActionListener(this);

        mnuNextPageItem = new JMenuItem("Next Page", 'N');
        mnuNextPageItem.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_PAGE_DOWN, InputEvent.CTRL_DOWN_MASK
            | InputEvent.SHIFT_DOWN_MASK));
        mnuNextPageItem.addActionListener(this);

        mnuLastPageItem = new JMenuItem("Last Page", 'L');
        mnuLastPageItem.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_END, InputEvent.CTRL_DOWN_MASK
            | InputEvent.SHIFT_DOWN_MASK));
        mnuLastPageItem.addActionListener(this);

        // add all items for Go To subMenu
        JMenu viewGotoSubMenu = new JMenu("Go To");
        viewGotoSubMenu.setMnemonic('G');
        viewGotoSubMenu.add(mnuFirstPageItem);
        viewGotoSubMenu.add(mnuPreviousPageItem);
        viewGotoSubMenu.add(mnuNextPageItem);
        viewGotoSubMenu.add(mnuLastPageItem);
        viewGotoSubMenu.add(new JSeparator());

        mnuZoomInItem = new JMenuItem("Zoom In", 'I');
        mnuZoomInItem.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_EQUALS, InputEvent.CTRL_MASK));
        mnuZoomInItem.addActionListener(this);

        mnuZoomOutItem = new JMenuItem("Zoom Out", 'O');
        mnuZoomOutItem.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_MINUS, InputEvent.CTRL_MASK));
        mnuZoomOutItem.addActionListener(this);

        mnuActualSizeItem = new JMenuItem("Actual Size", 'A');
        mnuActualSizeItem.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_NUMPAD1, InputEvent.CTRL_MASK));
        mnuActualSizeItem.addActionListener(this);

        mnuFitPageItem = new JMenuItem("Fit Page", 'i');
        mnuFitPageItem.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_NUMPAD2, InputEvent.CTRL_MASK));
        mnuFitPageItem.addActionListener(this);

        mnuFitWidthItem = new JMenuItem("Fit Width", 'W');
        mnuFitWidthItem.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_NUMPAD3, InputEvent.CTRL_MASK));
        mnuFitWidthItem.addActionListener(this);

        JMenu viewZoomSubMenu = new JMenu("Zoom");
        viewZoomSubMenu.setMnemonic('Z');
        viewZoomSubMenu.add(mnuZoomInItem);
        viewZoomSubMenu.add(mnuZoomOutItem);
        viewZoomSubMenu.add(new JSeparator());
        viewZoomSubMenu.add(mnuActualSizeItem);
        viewZoomSubMenu.add(mnuFitPageItem);
        viewZoomSubMenu.add(mnuFitWidthItem);

        mnuRotateAntiClokwiseItem = new JMenuItem(
            "Rotate AntiClockwise", 'A');
        mnuRotateAntiClokwiseItem.setAccelerator(KeyStroke
            .getKeyStroke(KeyEvent.VK_LEFT, InputEvent.CTRL_MASK));
        mnuRotateAntiClokwiseItem.addActionListener(this);

        mnuRotateClokwiseItem = new JMenuItem("Rotate Clockwise", 'C');
        mnuRotateClokwiseItem.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_RIGHT, InputEvent.CTRL_MASK));
        mnuRotateClokwiseItem.addActionListener(this);

        JMenu viewRotateViewSubMenu = new JMenu("Rotate View");
        viewRotateViewSubMenu.setMnemonic('R');
        viewRotateViewSubMenu.add(mnuRotateAntiClokwiseItem);
        viewRotateViewSubMenu.add(mnuRotateClokwiseItem);

        // Create menu items for Page Layouts
        // Single Page menu item
        radioMnuLayoutSinglePage = new JRadioButtonMenuItem(
        "Single Page");
        radioMnuLayoutSinglePage.setMnemonic('S');
        radioMnuLayoutSinglePage
        .setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_NUMPAD1, InputEvent.CTRL_DOWN_MASK
            | InputEvent.ALT_DOWN_MASK));
        radioMnuLayoutSinglePage.addActionListener(this);

        // Single Page Continuous menu item
        radioMnuLayoutSinglePageContinuous = new JRadioButtonMenuItem(
        "Single Page Continuous");
        radioMnuLayoutSinglePageContinuous.setMnemonic('C');
        radioMnuLayoutSinglePageContinuous
        .setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_NUMPAD2, InputEvent.CTRL_DOWN_MASK
            | InputEvent.ALT_DOWN_MASK));
        radioMnuLayoutSinglePageContinuous.addActionListener(this);

        // Side By Side
        radioMnuLayoutSideBySide = new JRadioButtonMenuItem(
        "Side By Side");
        radioMnuLayoutSideBySide.setMnemonic('B');
        radioMnuLayoutSideBySide
        .setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_NUMPAD3, InputEvent.CTRL_DOWN_MASK
            | InputEvent.ALT_DOWN_MASK));
        radioMnuLayoutSideBySide.addActionListener(this);

        // Side By Side Continuous
        radioMnuLayoutSideBySideContinuous = new JRadioButtonMenuItem(
        "Side By Side Continuous");
        radioMnuLayoutSideBySideContinuous.setMnemonic('y');
        radioMnuLayoutSideBySideContinuous
        .setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_NUMPAD4, InputEvent.CTRL_DOWN_MASK
            | InputEvent.ALT_DOWN_MASK));
        radioMnuLayoutSideBySideContinuous.addActionListener(this);

        // AutoFitColumnsInWindow menu item
        radioMnuLayoutAutoFitColumnsInWindow = new JRadioButtonMenuItem(
        "Auto Fit Columns In Window");
        radioMnuLayoutAutoFitColumnsInWindow.setMnemonic('F');
        radioMnuLayoutAutoFitColumnsInWindow
        .setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_NUMPAD5, InputEvent.CTRL_DOWN_MASK
            | InputEvent.ALT_DOWN_MASK));
        radioMnuLayoutAutoFitColumnsInWindow.addActionListener(this);

        // User Defined Columns menu item
        radioMnuLayoutUserDefined = new JRadioButtonMenuItem(
        "User Defined Columns");
        radioMnuLayoutUserDefined.setMnemonic('U');
        radioMnuLayoutUserDefined
        .setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_NUMPAD6, InputEvent.CTRL_DOWN_MASK
            | InputEvent.ALT_DOWN_MASK));
        radioMnuLayoutUserDefined.addActionListener(this);

        ButtonGroup grpPageLayout = new ButtonGroup();
        grpPageLayout.add(radioMnuLayoutSinglePage);
        grpPageLayout.add(radioMnuLayoutSinglePageContinuous);
        grpPageLayout.add(radioMnuLayoutSideBySide);
        grpPageLayout.add(radioMnuLayoutSideBySideContinuous);
        grpPageLayout.add(radioMnuLayoutAutoFitColumnsInWindow);
        grpPageLayout.add(radioMnuLayoutUserDefined);

        JMenu viewPageLayoutSubMenu = new JMenu("Page Layout");
        viewPageLayoutSubMenu.setMnemonic('L');
        viewPageLayoutSubMenu.add(radioMnuLayoutSinglePage);
        viewPageLayoutSubMenu.add(radioMnuLayoutSinglePageContinuous);
        viewPageLayoutSubMenu.add(radioMnuLayoutSideBySide);
        viewPageLayoutSubMenu.add(radioMnuLayoutSideBySideContinuous);
        viewPageLayoutSubMenu.add(radioMnuLayoutAutoFitColumnsInWindow);
        viewPageLayoutSubMenu.add(radioMnuLayoutUserDefined);
        viewPageLayoutSubMenu.add(new JSeparator());
        viewPageLayoutSubMenu.add(new JSeparator());

        // Scrolling Mode Submenu
        JMenu scrollModeMenu = new JMenu("Scrolling Mode");
        scrollModeMenu.setMnemonic('S');

        ButtonGroup grpScrollMode = new ButtonGroup();
        radioMnuScrollMode_BLIT_SCROLL_MODE = new JRadioButtonMenuItem(
        "Performance");
        radioMnuScrollMode_BLIT_SCROLL_MODE.addActionListener(this);

        radioMnuScrollMode_BACKINGSTORE_SCROLL_MODE = new JRadioButtonMenuItem(
        "Smooth");
        radioMnuScrollMode_BACKINGSTORE_SCROLL_MODE
        .addActionListener(this);

        grpScrollMode.add(radioMnuScrollMode_BLIT_SCROLL_MODE);
        grpScrollMode
        .add(radioMnuScrollMode_BACKINGSTORE_SCROLL_MODE);

        scrollModeMenu.add(radioMnuScrollMode_BLIT_SCROLL_MODE);
        scrollModeMenu
        .add(radioMnuScrollMode_BACKINGSTORE_SCROLL_MODE);

        ButtonGroup grpDPI = new ButtonGroup();
        radioMnuDPI_72 = new JRadioButtonMenuItem("72 DPI");
        radioMnuDPI_72.addActionListener(this);

        radioMnuDPI_96 = new JRadioButtonMenuItem("96 DPI");
        radioMnuDPI_96.setSelected(true);
        radioMnuDPI_96.addActionListener(this);

        radioMnuDPI_110 = new JRadioButtonMenuItem("110 DPI");
        radioMnuDPI_110.addActionListener(this);

        grpDPI.add(radioMnuDPI_72);
        grpDPI.add(radioMnuDPI_96);
        grpDPI.add(radioMnuDPI_110);

        JMenu viewDPISubMenu = new JMenu("Scale To DPI");
        viewDPISubMenu.setMnemonic('D');
        viewDPISubMenu.add(radioMnuDPI_72);
        viewDPISubMenu.add(radioMnuDPI_96);
        viewDPISubMenu.add(radioMnuDPI_110);

        chkMnuShowLabels = new JCheckBoxMenuItem("Show Labels");
        chkMnuShowLabels.addActionListener(this);

        mnuFirstPageItem.setEnabled(false);
        mnuPreviousPageItem.setEnabled(false);
        mnuNextPageItem.setEnabled(false);
        mnuLastPageItem.setEnabled(false);

        radioMnuLayoutSinglePage.setEnabled(false);
        radioMnuLayoutSinglePageContinuous.setEnabled(false);
        radioMnuLayoutSideBySide.setEnabled(false);
        radioMnuLayoutSideBySideContinuous.setEnabled(false);
        radioMnuLayoutAutoFitColumnsInWindow.setEnabled(false);
        radioMnuLayoutUserDefined.setEnabled(false);

        viewMenu.add(viewGotoSubMenu);
        viewMenu.add(new JSeparator());
        viewMenu.add(viewZoomSubMenu);
        viewMenu.add(viewPageLayoutSubMenu);
        viewMenu.add(viewRotateViewSubMenu);
        viewMenu.add(new JSeparator());
        viewMenu.add(viewDPISubMenu);
        // viewMenu.add(chkMnuShowLabels);
        viewMenu.add(new JSeparator());
        viewMenu.add(scrollModeMenu);

        menuBar.add(viewMenu);

        // Look and Feels
        JMenu mnuLAF = new JMenu("Look And Feel");
        mnuLAF.setMnemonic('L');

        LookAndFeelInfo[] lookAndFeels = UIManager
        .getInstalledLookAndFeels();
        ButtonGroup radioGroup = new ButtonGroup();
        mnuLAFItems = new JRadioButtonMenuItem[lookAndFeels.length];

        final JApplet thisObject = this;
        for (int i = 0; i < lookAndFeels.length; i++)
        {
            mnuLAFItems[i] = new JRadioButtonMenuItem(lookAndFeels[i]
                                                                   .getName());
            mnuLAFItems[i].setActionCommand(lookAndFeels[i]
                                                         .getClassName());
            mnuLAFItems[i].addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                    if (((JRadioButtonMenuItem) ae.getSource())
                        .isSelected())
                    {
                        try
                        {
                            if ( !UIManager.getLookAndFeel()
                                .getName().equals(
                                    ((JMenuItem) ae.getSource())
                                    .getText()))
                            {
                                UIManager
                                .setLookAndFeel(((JMenuItem) ae
                                    .getSource())
                                    .getActionCommand());

                                SwingUtilities
                                .updateComponentTreeUI(fc);
                                SwingUtilities
                                .updateComponentTreeUI(thisObject);
                                SwingUtilities
                                .updateComponentTreeUI(txtGoToPage);
                                SwingUtilities
                                .updateComponentTreeUI(aboutDialog);
                                SwingUtilities
                                .updateComponentTreeUI(docInfoDialog);
                                /*
                                 * if(pdfAsImageDialog != null) {
                                 * SwingUtilities
                                 * .updateComponentTreeUI
                                 * (pdfAsImageDialog); }
                                 */
                                lookAndFeelClassName = ((JMenuItem) ae
                                    .getSource()).getActionCommand();
                            }
                        }
                        catch (Throwable e)
                        {
                        }
                    }
                }
            });

            if (lookAndFeels[i].getClassName().equalsIgnoreCase(
                lookAndFeelClassName))
            {
                mnuLAFItems[i].setSelected(true);
            }

            radioGroup.add(mnuLAFItems[i]);
            mnuLAF.add(mnuLAFItems[i]);

        }

        menuBar.add(mnuLAF);

        JMenu mnuAbout = new JMenu("About");
        mnuAbout.setMnemonic('A');
        mnuAboutItem = new JMenuItem("About Viewer...");
        mnuAboutItem.setMnemonic('V');
        mnuAboutItem.addActionListener(this);

        mnuAbout.add(mnuAboutItem);

        menuBar.add(mnuAbout);

        setJMenuBar(menuBar);

        mnuActualSizeItem.setEnabled(false);
        mnuFitPageItem.setEnabled(false);
        mnuFitWidthItem.setEnabled(false);
        mnuZoomInItem.setEnabled(false);
        mnuZoomOutItem.setEnabled(false);
        mnuRotateAntiClokwiseItem.setEnabled(false);
        mnuRotateClokwiseItem.setEnabled(false);
        chkMnuShowLabels.setSelected(true);
        radioMnuScrollMode_BACKINGSTORE_SCROLL_MODE.setSelected(true);
    }

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
            else if (sourceButton == btnActualSize)
            {
                doActualSize();
            }
            else if (sourceButton == btnFitPage)
            {
                doFitPage();
            }
            else if (sourceButton == btnFitWidth)
            {
                doFitWidth();
            }
            else if (sourceButton == btnZoomOut)
            {
                doZoomOut();
            }
            else if (sourceButton == btnZoomIn)
            {
                doZoomIn();
            }
            else if (sourceButton == btnRotateCounterClockwise)
            {
                doRotateCounterClockwise();
            }
            else if (sourceButton == btnRotateClockwise)
            {
                doRotateClockwise();
            }
            else if (sourceButton == btnRotatePageCounterClockwise)
            {
                doRotatePageCounterClockwise();
            }
            else if (sourceButton == btnRotatePageClockwise)
            {
                doRotatePageClockwise();
            }
            else if(sourceButton == btnResetPageRotation)
            {
                resetRotation();
            }
            else if (sourceButton == btnFirstPage)
            {
                viewFirstPage();
            }
            else if (sourceButton == btnPreviousPage)
            {
                viewPreviousPage();
            }
            else if (sourceButton == txtGoToPage)
            {
                NumberFormat nf = NumberFormat.getInstance();
                try
                {
                    Number num = nf.parse(txtGoToPage.getText());
                    int pNum = num.intValue();

                    if (viewer.isDocumentLoaded()
                        && (pNum >= 1 && pNum <= viewer.getPageCount()))
                    {
                        goToPageNum(pNum);
                        updatePageNumAndZoomVal(viewer
                            .getCurrentPageNumber());
                    }
                    else
                    {
                        if (!viewer.isDocumentLoaded())
                        {
                            txtGoToPage.setText("");
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(this
                                .getParent(),
                                "There is no page numbered '"
                                + txtGoToPage.getText()
                                + "' in this document",
                                "Gnostice Document Viewer",
                                JOptionPane.INFORMATION_MESSAGE);
                            updatePageNumAndZoomVal(viewer
                                .getCurrentPageNumber());
                        }
                    }

                    if (viewer.getCurrentPageNumber() == 1)
                    {
                        btnFirstPage.setEnabled(false);
                        btnPreviousPage.setEnabled(false);

                        mnuFirstPageItem.setEnabled(false);
                        mnuPreviousPageItem.setEnabled(false);

                        if (viewer.getPageCount() > 1)
                        {
                            btnNextPage.setEnabled(true);
                            btnLastPage.setEnabled(true);

                            mnuNextPageItem.setEnabled(true);
                            mnuLastPageItem.setEnabled(true);
                        }
                    }
                    else if (viewer.getCurrentPageNumber() != 0
                        && viewer.getCurrentPageNumber() == viewer
                        .getPageCount())
                    {
                        btnNextPage.setEnabled(false);
                        btnLastPage.setEnabled(false);

                        mnuNextPageItem.setEnabled(false);
                        mnuLastPageItem.setEnabled(false);

                        if (viewer.getPageCount() > 1)
                        {
                            btnFirstPage.setEnabled(true);
                            btnPreviousPage.setEnabled(true);

                            mnuFirstPageItem.setEnabled(true);
                            mnuPreviousPageItem.setEnabled(true);
                        }
                    }
                    else
                    {
                        btnFirstPage.setEnabled(true);
                        btnPreviousPage.setEnabled(true);
                        btnNextPage.setEnabled(true);
                        btnLastPage.setEnabled(true);

                        mnuFirstPageItem.setEnabled(true);
                        mnuPreviousPageItem.setEnabled(true);
                        mnuNextPageItem.setEnabled(true);
                        mnuLastPageItem.setEnabled(true);
                    }
                }
                catch (ParseException pe)
                {
                    if (!viewer.isDocumentLoaded())
                    {
                        txtGoToPage.setText("");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this
                            .getParent(),
                            "There is no page numbered '"
                            + txtGoToPage.getText()
                            + "' in this document",
                            "Gnostice Document Viewer",
                            JOptionPane.INFORMATION_MESSAGE);
                        updatePageNumAndZoomVal(viewer
                            .getCurrentPageNumber());
                    }
                }

                txtGoToPage.transferFocusUpCycle();
            }
            else if (sourceButton == btnNextPage)
            {
                viewNextPage();
            }
            else if (sourceButton == btnLastPage)
            {
                viewLastPage();
            }
            else if (sourceButton == btnContinuous)
            {
                PageLayout pageLayout = new PageLayout();
                pageLayout.setPageLayoutMode(PageLayoutMode.SINGLE_PAGE_CONTINUOUS);
                setViewerPageLayout(pageLayout);
            }
            else if (sourceButton == btnSinglePage)
            {
                PageLayout pageLayout = new PageLayout();
                pageLayout.setPageLayoutMode(PageLayoutMode.SINGLE_PAGE);
                setViewerPageLayout(pageLayout);
            }
            else if (sourceButton == btnSideBySide)
            {
                PageLayout pageLayout = new PageLayout();
                pageLayout.setPageLayoutMode(PageLayoutMode.SIDE_BY_SIDE);
                setViewerPageLayout(pageLayout);
            }
            else if (sourceButton == btnSideBySideContinuous)
            {
                PageLayout pageLayout = new PageLayout();
                pageLayout.setPageLayoutMode(PageLayoutMode.SIDE_BY_SIDE_CONTINUOUS);
                setViewerPageLayout(pageLayout);
            }
            else if (sourceButton == btnAutoFitColumnsInWindow)
            {
                PageLayout pageLayout = new PageLayout();
                pageLayout.setPageLayoutMode(PageLayoutMode.AUTO_FIT_COLUMNS_IN_WINDOW);
                setViewerPageLayout(pageLayout);
            }
            else if (sourceButton == btnCustomLayout)
            {
                PageLayout pageLayout = new PageLayout();
                pageLayout.setPageLayoutMode(PageLayoutMode.CUSTOM_COLUMNS);
                setViewerPageLayout(pageLayout);
            }
            else if (sourceButton == txtCustomColumnCount)
            {
                NumberFormat nf = NumberFormat.getInstance();
                try
                {
                    Number num = nf.parse(txtCustomColumnCount
                        .getText());
                    int customColsCount = num.intValue();
                    if (viewer.isDocumentLoaded())
                    {
                        PageLayout pageLayout = new PageLayout();
                        pageLayout.setPageLayoutMode(PageLayoutMode.CUSTOM_COLUMNS);
                        pageLayout.setColumns(customColsCount);
                        setViewerPageLayout(pageLayout);
                    }
                    else
                    {
                        txtCustomColumnCount.setText("");
                    }
                }
                catch (ParseException pe)
                {
                    if (!viewer.isDocumentLoaded())
                    {
                        txtCustomColumnCount.setText("");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this
                            .getParent(),
                            "Invalid number for custom columns: '"
                            + txtCustomColumnCount.getText()
                            + "'", "Gnostice Document Viewer",
                            JOptionPane.INFORMATION_MESSAGE);
                    }
                }

                txtCustomColumnCount.transferFocusUpCycle();
            }
            else if (sourceButton == mnuOpenItem)
            {
                loadFile();

                // loadBookmarks();
            }
            else if (sourceButton == mnuPrintItem)
            {
                printFile();
            }
            else if (sourceButton == mnuCloseItem)
            {
                closeFile();
            }
            else if (sourceButton == mnuExitItem)
            {
                closeFile();
                System.exit(0);
            }
            else if (sourceButton == mnuFirstPageItem)
            {
                viewFirstPage();
            }
            else if (sourceButton == mnuPreviousPageItem)
            {
                viewPreviousPage();
            }
            else if (sourceButton == mnuNextPageItem)
            {
                viewNextPage();
            }
            else if (sourceButton == mnuLastPageItem)
            {
                viewLastPage();
            }
            else if (sourceButton == mnuActualSizeItem)
            {
                doActualSize();
            }
            else if (sourceButton == mnuFitPageItem)
            {
                doFitPage();
            }
            else if (sourceButton == mnuFitWidthItem)
            {
                doFitWidth();
            }
            else if (sourceButton == chkMnuShowLabels)
            {
                processChkMnuShowLabelsAction();
            }
            //TODO DPI
            else if (sourceButton == radioMnuDPI_72)
            {
                if (radioMnuDPI_72.isSelected())
                {
                    viewer.getPreferences().getRenderingSettings().getResolution().setDpiX(72);
                    viewer.getPreferences().getRenderingSettings().getResolution().setDpiY(72);
                }
            }
            else if (sourceButton == radioMnuDPI_96)
            {
                if (radioMnuDPI_96.isSelected())
                {
                    viewer.getPreferences().getRenderingSettings().getResolution().setDpiX(96);
                    viewer.getPreferences().getRenderingSettings().getResolution().setDpiY(96);
                }
            }
            else if (sourceButton == radioMnuDPI_110)
            {
                if (radioMnuDPI_110.isSelected())
                {
                    viewer.getPreferences().getRenderingSettings().getResolution().setDpiX(110);
                    viewer.getPreferences().getRenderingSettings().getResolution().setDpiY(110);
                }
            }
            else if (sourceButton == mnuZoomInItem)
            {
                doZoomIn();
            }
            else if (sourceButton == mnuZoomOutItem)
            {
                doZoomOut();
            }
            else if (sourceButton == mnuRotateAntiClokwiseItem)
            {
                viewer.rotateAllPagesAntiClockwise();
            }
            else if (sourceButton == mnuRotateClokwiseItem)
            {
                viewer.rotateAllPagesClockwise();
            }
            else if (sourceButton == radioMnuLayoutSinglePage)
            {
                if (radioMnuLayoutSinglePage.isSelected())
                {
                    PageLayout pageLayout = new PageLayout();
                    pageLayout.setPageLayoutMode(PageLayoutMode.SINGLE_PAGE);
                    setViewerPageLayout(pageLayout);
                }
            }
            else if (sourceButton == radioMnuLayoutSinglePageContinuous)
            {
                if (radioMnuLayoutSinglePageContinuous.isSelected())
                {
                    PageLayout pageLayout = new PageLayout();
                    pageLayout.setPageLayoutMode(PageLayoutMode.SINGLE_PAGE_CONTINUOUS);
                    setViewerPageLayout(pageLayout);
                }
            }
            else if (sourceButton == radioMnuLayoutSideBySide)
            {
                if (radioMnuLayoutSideBySide.isSelected())
                {
                    PageLayout pageLayout = new PageLayout();
                    pageLayout.setPageLayoutMode(PageLayoutMode.SIDE_BY_SIDE);
                    setViewerPageLayout(pageLayout);
                }
            }
            else if (sourceButton == radioMnuLayoutSideBySideContinuous)
            {
                if (radioMnuLayoutSideBySideContinuous.isSelected())
                {
                    PageLayout pageLayout = new PageLayout();
                    pageLayout.setPageLayoutMode(PageLayoutMode.SIDE_BY_SIDE_CONTINUOUS);
                    setViewerPageLayout(pageLayout);
                }
            }
            else if (sourceButton == radioMnuLayoutAutoFitColumnsInWindow)
            {
                if (radioMnuLayoutAutoFitColumnsInWindow.isSelected())
                {
                    PageLayout pageLayout = new PageLayout();
                    pageLayout.setPageLayoutMode(PageLayoutMode.AUTO_FIT_COLUMNS_IN_WINDOW);
                    setViewerPageLayout(pageLayout);
                }
            }
            else if (sourceButton == radioMnuLayoutUserDefined)
            {
                if (radioMnuLayoutUserDefined.isSelected())
                {
                    PageLayout pageLayout = new PageLayout();
                    pageLayout.setPageLayoutMode(PageLayoutMode.CUSTOM_COLUMNS);
                    //TODO
                    setViewerPageLayout(pageLayout);
                }
            }
            else if (sourceButton == mnuAboutItem)
            {
                aboutDialog.setVisible(true);
            }
            else if (sourceButton == mnuDocInfo)
            {
                docInfoDialog.setVisible(true);
            }
        }
        catch (IOException ioEx)
        {
            JOptionPane.showMessageDialog(this.getParent(), ioEx
                .getMessage(), "Gnostice Document Viewer",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetRotation()
    {
        Thread t = new Thread(new Runnable()
        {
            public void run()
            {
                startProgressBar();
                viewer.resetAllPageRotation();
                stopProgressBar();
            }
        });
        t.start();
            
    
    }

    private void doRotatePageClockwise()
    {
        Thread t = new Thread(new Runnable()
        {
            public void run()
            {
                startProgressBar();
                int[] visiblePageNumbers = viewer.getVisiblePageNumbers();
                for(int i = 0; i < visiblePageNumbers.length; i++)
                {
                    int n = visiblePageNumbers[i];
                    viewer.rotatePageTo(RotationAngle.Clockwise90, n );
                }
                stopProgressBar();
            }
        });
        t.start();
            
    }

    private void doRotatePageCounterClockwise()
    {
        Thread t = new Thread(new Runnable()
        {
            public void run()
            {
                startProgressBar();
                String visiblePageNumbers = Arrays.toString(viewer.getVisiblePageNumbers());
                visiblePageNumbers = visiblePageNumbers.substring(1, visiblePageNumbers.length()-1);
                viewer.rotatePageTo(RotationAngle.AntiClockwise90, visiblePageNumbers);
                stopProgressBar();
            }
        });
        t.start();
    }

    private void setViewerPageLayout(PageLayout pageLayout)
    {
        final PageLayout pageLayoutVal = pageLayout;
        Thread t = new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    startProgressBar();
                    viewer.setPageLayout(pageLayoutVal);
                }
                catch (XDocException e)
                {
                    
                }
                finally
                {
                    stopProgressBar();
                }
            }
        });
        t.start();
    }

    private void processChkMnuShowLabelsAction()
    {
        if (chkMnuShowLabels.isSelected())
        {
            btnLoad.setText("Open");
            btnPrint.setText("Print");
            btnActualSize.setText("Actual Size");
            btnFitPage.setText("Fit Page");
            btnFitWidth.setText("Fit Width");
            btnZoomOut.setText("Zoom Out");
            btnZoomIn.setText("Zoom In");
            btnRotateCounterClockwise
            .setText("Rotate Counterclockwise");
            btnRotateClockwise.setText("Rotate Clockwise");
            
            btnRotatePageCounterClockwise
            .setText("Rotate Page Counterclockwise");
            btnRotatePageClockwise.setText("Rotate Page Clockwise");
            
            btnResetPageRotation.setText("Reset Rotation");

            btnContinuous.setText("Continuous Layout");
            btnSinglePage.setText("Single Page Layout");
            btnSideBySide.setText("Side-by-Side Layout");
            btnSideBySideContinuous
            .setText("Side-by-Side Continuous Layout");
            btnAutoFitColumnsInWindow
            .setText("Auto Fit Columns in Window");
            btnCustomLayout.setText("Custom Layout");

        }
        else
        {
            btnLoad.setText("");
            btnPrint.setText("");
            btnActualSize.setText("");
            btnFitPage.setText("");
            btnFitWidth.setText("");
            btnZoomOut.setText("");
            btnZoomIn.setText("");
            btnRotateCounterClockwise.setText("");
            btnRotateClockwise.setText("");

            btnContinuous.setText("");
            btnSinglePage.setText("");
            btnSideBySide.setText("");
            btnSideBySideContinuous.setText("");
            btnAutoFitColumnsInWindow.setText("");
            btnCustomLayout.setText("");
        }
    }

    private void doRotateClockwise()
    {
        Thread t = new Thread(new Runnable()
        {
            public void run()
            {
                startProgressBar();
                viewer.rotateAllPagesClockwise();
                stopProgressBar();
            }
        });
        t.start();
    }

    private void doRotateCounterClockwise()
    {
        Thread t = new Thread(new Runnable()
        {
            public void run()
            {
                startProgressBar();
                viewer.rotateAllPagesAntiClockwise();
                stopProgressBar();
            }
        });
        t.start();
    }

    private void doActualSize()
    {
        Thread t = new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                startProgressBar();
                viewer.zoomTo(new Zoom(ZoomMode.ActualSize, 0));
                }catch (Exception e) {
                }
                finally
                {
                    stopProgressBar();
                }
            }
        });
        t.start();

        currentZoomPercentage = viewer.getZoom().getZoomPercentage();
        cboZoomPercentage.setSelectedItem((Double.valueOf(viewer.getZoom().getZoomPercentage()))
            .toString());

    }

    private void doFitPage() throws IOException
    {
        Thread t = new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                startProgressBar();
                viewer.zoomTo(new Zoom(ZoomMode.FitPage, 0));
                }catch (Exception e) {
                    
                }
                finally
                {
                    stopProgressBar();
                }
            }
        });
        t.start();

        currentZoomPercentage = viewer.getZoom().getZoomPercentage();
        cboZoomPercentage.setSelectedItem((Double.valueOf(viewer.getZoom().getZoomPercentage()))
            .toString());
    }

    private void doFitWidth() throws IOException
    {
        Thread t = new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    startProgressBar();
                    viewer.zoomTo(new Zoom(ZoomMode.FitWidth, 0.0));
                }
                catch (Exception e)
                {
                    e.printStackTrace();

                }
                finally
                {
                    stopProgressBar();
                }
            }
        });
        t.start();

        currentZoomPercentage = viewer.getZoom().getZoomPercentage();
        cboZoomPercentage.setSelectedItem((Double.valueOf(viewer.getZoom().getZoomPercentage()))
            .toString());
    }

    private void doZoomOut()
    {
        int nextPossibleZoomOutIndex = getNextPossibleZoomOutIndex(currentZoomPercentage);
        cboZoomPercentage.setSelectedIndex(nextPossibleZoomOutIndex);
    }

    private void doZoomIn()
    {
        int nextPossibleZoomInIndex = getNextPossibleZoomInIndex(currentZoomPercentage);
        cboZoomPercentage.setSelectedIndex(nextPossibleZoomInIndex);
    }

    private void viewFirstPage()
    {
        Thread t = new Thread(new Runnable()
        {
            public void run()
            {
                startProgressBar();

                try
                {
                    viewer.firstPage();
                }
                catch (XDocException e)
                {
                    e.printStackTrace();
                }
                stopProgressBar();
            }
        });
        t.start();
    }

    private void viewPreviousPage()
    {
        Thread t = new Thread(new Runnable()
        {
            public void run()
            {
                startProgressBar();

                try
                {
                    viewer.previousPage();
                }
                catch (XDocException e)
                {
                    e.printStackTrace();
                }
                stopProgressBar();
            }
        });
        t.start();
    }

    private void goToPageNum(int pNum)
    {
        final int gotoPageNum = pNum;
        Thread t = new Thread(new Runnable()
        {
            public void run()
            {
                startProgressBar();

                try
                {
                    viewer.goToPageNumber(gotoPageNum);
                }
                catch (XDocException e)
                {
                    e.printStackTrace();
                }
                
                stopProgressBar();
            }
        });
        t.start();

    }

    private void viewNextPage()
    {
        Thread t = new Thread(new Runnable()
        {
            public void run()
            {
                startProgressBar();

                try
                {
                    viewer.nextPage();
                }
                catch (XDocException e)
                {
                    e.printStackTrace();
                }
                
                stopProgressBar();
            }
        });
        t.start();
    }

    private void viewLastPage()
    {
        Thread t = new Thread(new Runnable()
        {
            public void run()
            {
                startProgressBar();
                try
                {
                    viewer.lastPage();
                }
                catch (XDocException e)
                {
                    e.printStackTrace();
                }
                stopProgressBar();
            }
        });
        t.start();
    }

    public void itemStateChanged(ItemEvent ie)
    {
        if (ie.getSource() == cboZoomPercentage)
        {
            if (ie.getStateChange() == ItemEvent.SELECTED)
            {
                String selectedItem = (String) ie.getItem();

                if (selectedItem.charAt(selectedItem.length() - 1) != '%')
                {
                    try
                    {
                        Double.parseDouble(selectedItem);
                        cboZoomPercentage.setSelectedItem(selectedItem
                            + "%");
                    }
                    catch (NumberFormatException nfe)
                    {
                        cboZoomPercentage.setSelectedItem(String
                            .valueOf(currentZoomPercentage)
                            + "%");
                    }
                }
                else
                {
                    try
                    {
                        double zoomFactor = Double
                        .parseDouble(selectedItem.substring(0,
                            selectedItem.length() - 1));

                        if (zoomFactor > 6400)
                        {
                            cboZoomPercentage.setSelectedIndex(0);
                        }
                        else if (zoomFactor < 0.01)
                        {
                            cboZoomPercentage
                            .setSelectedIndex(cboZoomPercentage
                                .getItemCount() - 1);
                        }
                        else
                            // if the value of zoomFactor ranges from
                            // 1 to 6400
                        {
                            // do not set if same zoom factor is
                            // entered once again
                            if (currentZoomPercentage != zoomFactor)
                            {
                                currentZoomPercentage = zoomFactor;

                                setRequiredZoom(zoomFactor);

                                // selected zoom is 0.01%
                                if (cboZoomPercentage.getSelectedIndex() == cboZoomPercentage
                                    .getItemCount() - 1)
                                {
                                    btnZoomOut.setEnabled(false);
                                }
                                else
                                {
                                    btnZoomOut.setEnabled(true);
                                }

                                // selected zoom is 6400%
                                if (cboZoomPercentage.getSelectedIndex() == 0)
                                {
                                    btnZoomIn.setEnabled(false);
                                }
                                else
                                {
                                    btnZoomIn.setEnabled(true);
                                }
                            }
                        }
                    }
                    catch (NumberFormatException nfe)
                    {
                        cboZoomPercentage.setSelectedItem(String
                            .valueOf(currentZoomPercentage)
                            + "%");
                    }
                }
            }
        }
    }

    private int getNextPossibleZoomOutIndex(double zoomPercentage)
    {
        int itemIndex = -1;
        double zoomFactor = 0.0;
        String selectedItem = null;

        for (int i = 0; i < cboZoomPercentage.getItemCount(); i++)
        {
            selectedItem = (String) cboZoomPercentage.getItemAt(i);

            try
            {
                zoomFactor = Double.parseDouble(selectedItem
                    .substring(0, selectedItem.length() - 1));

                if (zoomFactor < zoomPercentage)
                {
                    itemIndex = i;
                    break;
                }
            }
            catch (NumberFormatException nfe)
            {

            }
        }
        return itemIndex;
    }

    private int getNextPossibleZoomInIndex(double zoomPercentage)
    {
        int itemIndex = -1;
        double zoomFactor = 0.0;
        String selectedItem = null;

        for (int i = cboZoomPercentage.getItemCount() - 1; i >= 0; i--)
        {
            selectedItem = (String) cboZoomPercentage.getItemAt(i);

            try
            {
                zoomFactor = Double.parseDouble(selectedItem
                    .substring(0, selectedItem.length() - 1));

                if (zoomFactor > zoomPercentage)
                {
                    itemIndex = i;
                    break;
                }
            }
            catch (NumberFormatException nfe)
            {

            }
        }
        return itemIndex;
    }

    private void setRequiredZoom(double zoomFactor)
    {
        final double requiredZoomFactor = zoomFactor;
        Thread t = new Thread(new Runnable()
        {
            public void run()
            {
                startProgressBar();
                viewer.zoomTo(new Zoom(ZoomMode.CustomZoomPercent, requiredZoomFactor));
                stopProgressBar();
            }
        });
        t.start();
    }

    private void updatePageNumAndZoomVal(int pageNum)
    {
        System.out.println("updated");
        if (pageNum == 0)
        {
            txtGoToPage.setText("");
        }
        else
        {
            txtGoToPage.setText(String.valueOf(pageNum) + " of "
                + String.valueOf(viewer.getPageCount()));
        }

        currentZoomPercentage = viewer.getZoom().getZoomPercentage();
        cboZoomPercentage.setSelectedItem(String
            .valueOf(viewer.getZoom())
            + "%");

        ZoomMode pageZoomMode = viewer.getZoom().getZoomMode();

        switch (pageZoomMode)
        {
            case ActualSize:
                btnFitWidth.setSelected(false);
                btnActualSize.setSelected(true);
                btnFitPage.setSelected(false);
                break;
            case FitPage:
                btnFitWidth.setSelected(false);
                btnActualSize.setSelected(false);
                btnFitPage.setSelected(true);
                break;
            case FitWidth:
                btnFitWidth.setSelected(true);
                btnActualSize.setSelected(false);
                btnFitPage.setSelected(false);
                break;
            case CustomZoomPercent:
                btnFitWidth.setSelected(false);
                btnActualSize.setSelected(false);
                btnFitPage.setSelected(false);
                break;
        }
    }

    private void loadFile()
    {
        if (recentDir != null && recentDir.exists()
            && recentDir.isDirectory())
        {
            fc.setCurrentDirectory(recentDir);
        }

        int fcState = fc.showOpenDialog(this);

        if (fcState != JFileChooser.APPROVE_OPTION)
        {
            return;
        }

        recentDir = fc.getCurrentDirectory();

        File selectedFile = fc.getSelectedFile();

        if ( !(selectedFile.exists() && selectedFile.isFile()))
        {
            JOptionPane.showMessageDialog(this, "The File \""
                + selectedFile.getAbsoluteFile()
                + "\" does not exist", "Gnostice XDoc Viewer",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        closeFile();

        loadFile(selectedFile);
    }

    protected void loadFile(File fileToBeLoaded)
    {
        closeFile();

        docPath = fileToBeLoaded.getAbsolutePath();
        recentDir = fileToBeLoaded.getParentFile();

        // Show the Progress
        startProgressBar();

        Thread t = new Thread(new Runnable()
        {
            public void run()
            {
                loadDocument(docPath);
            }
        });
        t.start();
    }

    private void printFile() throws IOException
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
                "Gnostice Document Viewer", JOptionPane.ERROR_MESSAGE);
            loadFile();
        }
    }

    private void loadDocument(String docPath)
    {
        try
        {
            btnLoad.setEnabled(false);
            mnuOpenItem.setEnabled(false);

            btnPrint.setEnabled(false);
            mnuPrintItem.setEnabled(false);

            // do not allow drag & drop while loading a document
            canDropDocument = false;

            File fileToBeLoaded = new File(docPath);

            // Load the document
            viewer.loadDocument(docPath, "");
            
            viewer.getPreferences().getRenderingSettings().getResolution().setDpiX(110);
            viewer.getPreferences().getRenderingSettings().getResolution().setDpiY(110);
            radioMnuDPI_110.setSelected(true);
            radioMnuDPI_110.addActionListener(this);
            
            // ViewerPageHandler
            //TODO
//            viewer.setViewerPageHandler(this);

            fc.setSelectedFile(fileToBeLoaded);

            // calling refresh() method on the viewer is optionally as
            // it will be refreshed automatically the document is
            // associated with the viewer using setDocument() method
            // viewer.refresh();

            docInfoDialog.updateDocumentInfo(viewer.getActiveDocument());

            btnActualSize.setEnabled(true);
            btnFitPage.setEnabled(true);
            btnFitWidth.setEnabled(true);
            btnZoomOut.setEnabled(true);
            cboZoomPercentage.setEnabled(true);
            btnZoomIn.setEnabled(true);
            btnRotateCounterClockwise.setEnabled(true);
            btnRotateClockwise.setEnabled(true);
            btnRotatePageCounterClockwise.setEnabled(true);
            btnRotatePageClockwise.setEnabled(true);
            btnResetPageRotation.setEnabled(true);
            txtGoToPage.setEnabled(true);
            
            btnFirstPage.setEnabled(true);
            btnPreviousPage.setEnabled(true);
            txtGoToPage.setEnabled(true);
            btnNextPage.setEnabled(true);
            btnLastPage.setEnabled(true);

            btnContinuous.setEnabled(true);
            btnSinglePage.setEnabled(true);
            btnSideBySide.setEnabled(true);
            btnSideBySideContinuous.setEnabled(true);
            btnAutoFitColumnsInWindow.setEnabled(true);
            btnCustomLayout.setEnabled(true);
            txtCustomColumnCount.setEnabled(true);

            mnuActualSizeItem.setEnabled(true);
            mnuFitPageItem.setEnabled(true);
            mnuFitWidthItem.setEnabled(true);
            mnuZoomInItem.setEnabled(true);
            mnuZoomOutItem.setEnabled(true);
            mnuRotateAntiClokwiseItem.setEnabled(true);
            mnuRotateClokwiseItem.setEnabled(true);

            radioMnuLayoutSinglePage.setEnabled(true);
            radioMnuLayoutSinglePageContinuous.setEnabled(true);
            radioMnuLayoutSideBySide.setEnabled(true);
            radioMnuLayoutSideBySideContinuous.setEnabled(true);
            radioMnuLayoutAutoFitColumnsInWindow.setEnabled(true);
            radioMnuLayoutUserDefined.setEnabled(true);

            cboZoomPercentage.setSelectedItem((Double.valueOf(viewer
                .getZoom().getZoomPercentage())).toString());

            mnuCloseItem.setEnabled(true);
            mnuDocInfo.setEnabled(true);
        }
        catch (IncorrectPasswordException pwdEx)
        {
            // In the demo we don't throw the exception as the
            // password will be asked repeatedly until the correct
            // password is provided or user can cancel the loading.
//            JOptionPane.showMessageDialog(this, pwdEx.getMessage(),
//                "Gnostice XDoc Viewer", JOptionPane.ERROR_MESSAGE);
        }
        catch (XDocException xDocEx)
        {
            JOptionPane.showMessageDialog(this, xDocEx.getMessage(),
                "Gnostice XDoc Viewer", JOptionPane.ERROR_MESSAGE);
        }
        catch (IOException ioEx)
        {
            JOptionPane.showMessageDialog(this, ioEx.getMessage(),
                "Gnostice XDoc Viewer", JOptionPane.ERROR_MESSAGE);
        }
        finally
        {
            // Make the progress bar invisible
            stopProgressBar();

            btnLoad.setEnabled(true);
            mnuOpenItem.setEnabled(true);

            btnPrint.setEnabled(true);
            mnuPrintItem.setEnabled(true);

            // allow drag & drop once after document loading process
            // completed
            canDropDocument = true;
        }
    }

    private void startProgressBar()
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                if ( !progress.isVisible())
                {
                    progress.setVisible(true);
                    progress.setIndeterminate(true);
                }
            }
        });
        
    }

    private void stopProgressBar()
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                if (progress.isVisible())
                {
                    progress.setIndeterminate(false);
                    progress.setVisible(false);
                }
            }
        });
        
    }

    private void closeFile()
    {

        txtGoToPage.setText("");

        btnFirstPage.setEnabled(false);
        btnPreviousPage.setEnabled(false);
        txtGoToPage.setEnabled(false);
        btnNextPage.setEnabled(false);
        btnLastPage.setEnabled(false);

        mnuFirstPageItem.setEnabled(false);
        mnuPreviousPageItem.setEnabled(false);
        mnuNextPageItem.setEnabled(false);
        mnuLastPageItem.setEnabled(false);

        btnActualSize.setEnabled(false);
        btnFitPage.setEnabled(false);
        btnFitWidth.setEnabled(false);
        btnZoomOut.setEnabled(false);
        cboZoomPercentage.setEnabled(false);
        btnZoomIn.setEnabled(false);
        btnRotateCounterClockwise.setEnabled(false);
        btnRotateClockwise.setEnabled(false);
        btnRotatePageClockwise.setEnabled(false);
        btnRotatePageCounterClockwise.setEnabled(false);
        btnResetPageRotation.setEnabled(false);

        btnContinuous.setEnabled(false);
        btnSinglePage.setEnabled(false);
        btnSideBySide.setEnabled(false);
        btnSideBySideContinuous.setEnabled(false);
        btnAutoFitColumnsInWindow.setEnabled(false);
        btnCustomLayout.setEnabled(false);
        txtCustomColumnCount.setEnabled(false);

        mnuActualSizeItem.setEnabled(false);
        mnuFitPageItem.setEnabled(false);
        mnuFitWidthItem.setEnabled(false);
        mnuZoomInItem.setEnabled(false);
        mnuZoomOutItem.setEnabled(false);
        mnuRotateAntiClokwiseItem.setEnabled(false);
        mnuRotateClokwiseItem.setEnabled(false);

        radioMnuLayoutSinglePage.setEnabled(false);
        radioMnuLayoutSinglePageContinuous.setEnabled(false);
        radioMnuLayoutSideBySide.setEnabled(false);
        radioMnuLayoutSideBySideContinuous.setEnabled(false);
        radioMnuLayoutAutoFitColumnsInWindow.setEnabled(false);
        radioMnuLayoutUserDefined.setEnabled(false);

        if (viewer.isDocumentLoaded())
        {
            try
            {
                viewer.closeDocument();
            }
            catch (Exception ioEx)
            {
                JOptionPane.showMessageDialog(this,
                    ioEx.getMessage(), "Gnostice Document Viewer",
                    JOptionPane.ERROR_MESSAGE);
            }
        }

        if (printer.isDocumentLoaded())
        {
            try
            {
                printer.closeDocument();
            }
            catch (Exception ioEx)
            {
                JOptionPane.showMessageDialog(this,
                    ioEx.getMessage(), "Gnostice Document Viewer",
                    JOptionPane.ERROR_MESSAGE);
            }
        }

        mnuCloseItem.setEnabled(false);
        mnuDocInfo.setEnabled(false);
        btnLoad.requestFocusInWindow();
        pwd = "";
    }

    public void init()
    {
        fc = new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);
        
        fc.addChoosableFileFilter(new DocumentFileFilter("*.pdf"));
        fc.addChoosableFileFilter(new DocumentFileFilter("*.docx"));
        fc.addChoosableFileFilter(new DocumentFileFilter("All XDoc Supported Files"));
        fc.setCurrentDirectory(recentDir);

        if (recentDir == null)
        {
            recentDir = fc.getCurrentDirectory().getAbsoluteFile();
        }

        viewer = new DocumentViewer();
        viewer.addViewerListener(this);
        
        setViewerPreferences();
        
        // Create a new PdfPrinter object
        try
        {
            printer = new DocumentPrinter();
        }
        catch (XDocException e2)
        {
        }
        printer.addPrintListener(this);

        getContentPane().setLayout(new BorderLayout());

        getContentPane().add(viewer, BorderLayout.CENTER);
        topToolBar = getTopToolBar();
        getContentPane().add(topToolBar, BorderLayout.NORTH);
        getContentPane().add(getStatusBar(), BorderLayout.SOUTH);
        createMenus();

        requestFocus();

        aboutDialog = new AboutGnosticeXDOCJava(null);
        aboutDialog.setModal(true);

        docInfoDialog = new DocumentInfoDialog();
        docInfoDialog.setModal(true);

        // add TransferHandler to handle drop file
        viewer.setTransferHandler(new TransferHandler()
        {
            private static final long serialVersionUID = 1L;

            public boolean canImport(JComponent arg0,
                DataFlavor[] arg1)
            {
                if ( !canDropDocument)
                {
                    return false;
                }

                for (int i = 0; i < arg1.length; i++)
                {
                    DataFlavor flavor = arg1[i];
                    if (flavor.equals(DataFlavor.javaFileListFlavor))
                    {
                        return true;
                    }
                    else if (flavor.equals(DataFlavor.stringFlavor))
                    {
                        return true;
                    }
                }
                // Didn't find any that match, so:
                return false;
            }

            public boolean importData(JComponent comp, Transferable t)
            {
                if ( !canDropDocument)
                {
                    return false;
                }

                DataFlavor[] flavors = t.getTransferDataFlavors();
                for (int i = 0; i < flavors.length; i++)
                {
                    DataFlavor flavor = flavors[i];
                    try
                    {
                        if (flavor
                            .equals(DataFlavor.javaFileListFlavor))
                        {
                            List l = (List) t
                            .getTransferData(DataFlavor.javaFileListFlavor);
                            Iterator iter = l.iterator();
                            while (iter.hasNext())
                            {
                                File file = (File) iter.next();
                                if (file.isFile())
                                {
                                    loadFile(file);
                                    return true;
                                }
                            }
                        }
                        else if (flavor
                            .equals(DataFlavor.stringFlavor))
                        {
                            String fileOrURL = (String) t
                            .getTransferData(flavor);
                            try
                            {
                                URL url = new URL(fileOrURL);

                                String urlPath = url.getPath();

                                URI urlAsURI = null;

                                String javaSpecVer = System
                                .getProperty("java.specification.version");

                                float javaSpecVerNum = -1;

                                try
                                {
                                    javaSpecVerNum = Float
                                    .parseFloat(javaSpecVer);
                                }
                                catch (Exception ex)
                                {
                                    javaSpecVerNum = -1;
                                }

                                if (javaSpecVerNum != -1
                                    && javaSpecVerNum >= 1.5)
                                {
                                    try
                                    {
                                        Method methodToURI = url
                                        .getClass().getMethod(
                                            "toURI", null);

                                        Object objURI = methodToURI
                                        .invoke(url, null);

                                        if (objURI != null
                                            && objURI instanceof URI)
                                        {
                                            urlAsURI = (URI) objURI;
                                        }
                                    }
                                    catch (NoSuchMethodException nsmEx)
                                    {
                                    }
                                    catch (IllegalAccessException iaEx)
                                    {
                                    }
                                    catch (InvocationTargetException itEx)
                                    {
                                    }
                                }

                                if (url.getProtocol().toLowerCase()
                                    .equals("file"))
                                {
                                    File file = null;

                                    if (urlAsURI != null)
                                    {
                                        file = new File(urlAsURI);
                                    }
                                    else
                                    {
                                        file = new File(urlPath);
                                    }

                                    if (file.isFile())
                                    {
                                        loadFile(file);
                                        return true;
                                    }
                                }
                                return true;
                            }
                            catch (MalformedURLException ex)
                            {
                                return false;
                            }
                        }
                    }
                    catch (IOException ex)
                    {
                    }
                    catch (UnsupportedFlavorException e)
                    {
                    }
                    catch (SecurityException securityEx)
                    {
                    }
                }
                Toolkit.getDefaultToolkit().beep();
                return false;
            }
        });

        chkMnuShowLabels.setSelected(false);
        processChkMnuShowLabelsAction();

        // set the look and feel specified in the properties file
        try
        {
            UIManager.setLookAndFeel(lookAndFeelClassName);

            SwingUtilities.updateComponentTreeUI(fc);
            SwingUtilities.updateComponentTreeUI(this);
            SwingUtilities.updateComponentTreeUI(txtGoToPage);
            SwingUtilities.updateComponentTreeUI(aboutDialog);
            SwingUtilities.updateComponentTreeUI(docInfoDialog);
        }
        catch (Exception e1)
        {
        }
    }

    public void notifyOutlineAction()
    {
        updatePageNumAndZoomVal(viewer.getCurrentPageNumber());

        if (viewer.getCurrentPageNumber() == 1
            && viewer.getPageCount() == 1)
        {
            return;
        }

        if (viewer.getCurrentPageNumber() == viewer.getPageCount())
        {
            btnNextPage.setEnabled(false);
            btnLastPage.setEnabled(false);

            mnuNextPageItem.setEnabled(false);
            mnuLastPageItem.setEnabled(false);

            if (viewer.getPageCount() > 1)
            {
                btnFirstPage.setEnabled(true);
                btnPreviousPage.setEnabled(true);

                mnuFirstPageItem.setEnabled(true);
                mnuPreviousPageItem.setEnabled(true);
            }
        }
        else if (viewer.getCurrentPageNumber() == 1)
        {
            btnFirstPage.setEnabled(false);
            btnPreviousPage.setEnabled(false);

            mnuFirstPageItem.setEnabled(false);
            mnuPreviousPageItem.setEnabled(false);

            btnNextPage.setEnabled(true);
            btnLastPage.setEnabled(true);

            mnuNextPageItem.setEnabled(true);
            mnuLastPageItem.setEnabled(true);
        }
        else
        {
            btnFirstPage.setEnabled(true);
            btnPreviousPage.setEnabled(true);
            btnNextPage.setEnabled(true);
            btnLastPage.setEnabled(true);

            mnuFirstPageItem.setEnabled(true);
            mnuPreviousPageItem.setEnabled(true);
            mnuNextPageItem.setEnabled(true);
            mnuLastPageItem.setEnabled(true);
        }
    }
    @Override
    public void pageChanged(ViewerPageChangeEvent pageChangeEvent)
    {
    }

    public void zoomChanged(ViewerZoomChangeEvent zoomChangeEvent)
    {
        
    }

    public void rotationChanged(ViewerRotationChangeEvent rotationChangeEvent)
    {
    }

    public void windowActivated(WindowEvent e)
    {
    }

    public void windowClosed(WindowEvent e)
    {
    }

    public void windowClosing(WindowEvent e)
    {
        try
        {
            viewer.closeDocument();
            viewer.dispose();
        }
        catch (IOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
        aboutDialog.dispose();
        docInfoDialog.dispose();
    }

    public void windowDeactivated(WindowEvent e)
    {
    }

    public void windowDeiconified(WindowEvent e)
    {
    }

    public void windowIconified(WindowEvent e)
    {
    }

    public void windowOpened(WindowEvent e)
    {
    }

    public void pageLayoutChanged(ViewerPageLayoutChangeEvent pageLayoutChangeEvent)
    {
    }

    public synchronized void error(ViewerRenderErrorEvent renderErrorEvent)
    {
        
    }

    @Override
    public void viewerStateChanged(ViewerStateChangeEvent viewerChangeEvent)
    {
        int pageNum = viewerChangeEvent.getCurrentPageNumber();
        int pageColumnsCustomCount = viewerChangeEvent
            .getPageLayout().getColumns();
        double zoomPercentage = viewerChangeEvent.getZoom()
            .getZoomPercentage();
        ZoomMode pageZoomMode = viewerChangeEvent.getZoom()
            .getZoomMode();
        PageLayoutMode pageLayoutMode = viewerChangeEvent
            .getPageLayout().getPageLayoutMode();

        if (pageNum == 0)
        {
            txtGoToPage.setText("");
        }
        else
        {
            txtGoToPage.setText(String.valueOf(pageNum) + " of "
                + String.valueOf(viewer.getPageCount()));
        }

        txtCustomColumnCount.setText(String
            .valueOf(pageColumnsCustomCount));

        currentZoomPercentage = zoomPercentage;
        cboZoomPercentage.getEditor().setItem(
            String.valueOf(zoomPercentage) + "%");

        switch (pageZoomMode)
        {
            case ActualSize:
                btnFitWidth.setSelected(false);
                btnActualSize.setSelected(true);
                btnFitPage.setSelected(false);
                break;
            case FitPage:
                btnFitWidth.setSelected(false);
                btnActualSize.setSelected(false);
                btnFitPage.setSelected(true);
                break;
            case FitWidth:
                btnFitWidth.setSelected(true);
                btnActualSize.setSelected(false);
                btnFitPage.setSelected(false);
                break;
            case CustomZoomPercent:
                btnFitWidth.setSelected(false);
                btnActualSize.setSelected(false);
                btnFitPage.setSelected(false);
                break;
        }

        switch (pageLayoutMode)
        {
            case SINGLE_PAGE:
                btnContinuous.setSelected(false);
                btnSinglePage.setSelected(true);
                btnSideBySide.setSelected(false);
                btnSideBySideContinuous.setSelected(false);
                btnAutoFitColumnsInWindow.setSelected(false);
                btnCustomLayout.setSelected(false);

                radioMnuLayoutSinglePage.setSelected(true);
                radioMnuLayoutSinglePageContinuous.setSelected(false);
                radioMnuLayoutSideBySide.setSelected(false);
                radioMnuLayoutSideBySideContinuous.setSelected(false);
                radioMnuLayoutAutoFitColumnsInWindow
                    .setSelected(false);
                radioMnuLayoutUserDefined.setSelected(false);
                break;
            case SINGLE_PAGE_CONTINUOUS:
                btnContinuous.setSelected(true);
                btnSinglePage.setSelected(false);
                btnSideBySide.setSelected(false);
                btnSideBySideContinuous.setSelected(false);
                btnAutoFitColumnsInWindow.setSelected(false);
                btnCustomLayout.setSelected(false);

                radioMnuLayoutSinglePage.setSelected(false);
                radioMnuLayoutSinglePageContinuous.setSelected(true);
                radioMnuLayoutSideBySide.setSelected(false);
                radioMnuLayoutSideBySideContinuous.setSelected(false);
                radioMnuLayoutAutoFitColumnsInWindow
                    .setSelected(false);
                radioMnuLayoutUserDefined.setSelected(false);
                break;
            case SIDE_BY_SIDE:
                btnContinuous.setSelected(false);
                btnSinglePage.setSelected(false);
                btnSideBySide.setSelected(true);
                btnSideBySideContinuous.setSelected(false);
                btnAutoFitColumnsInWindow.setSelected(false);
                btnCustomLayout.setSelected(false);

                radioMnuLayoutSinglePage.setSelected(false);
                radioMnuLayoutSinglePageContinuous.setSelected(false);
                radioMnuLayoutSideBySide.setSelected(true);
                radioMnuLayoutSideBySideContinuous.setSelected(false);
                radioMnuLayoutAutoFitColumnsInWindow
                    .setSelected(false);
                radioMnuLayoutUserDefined.setSelected(false);
                break;
            case SIDE_BY_SIDE_CONTINUOUS:
                btnContinuous.setSelected(false);
                btnSinglePage.setSelected(false);
                btnSideBySide.setSelected(false);
                btnSideBySideContinuous.setSelected(true);
                btnAutoFitColumnsInWindow.setSelected(false);
                btnCustomLayout.setSelected(false);

                radioMnuLayoutSinglePage.setSelected(false);
                radioMnuLayoutSinglePageContinuous.setSelected(false);
                radioMnuLayoutSideBySide.setSelected(false);
                radioMnuLayoutSideBySideContinuous.setSelected(true);
                radioMnuLayoutAutoFitColumnsInWindow
                    .setSelected(false);
                radioMnuLayoutUserDefined.setSelected(false);
                break;
            case AUTO_FIT_COLUMNS_IN_WINDOW:
                btnContinuous.setSelected(false);
                btnSinglePage.setSelected(false);
                btnSideBySide.setSelected(false);
                btnSideBySideContinuous.setSelected(false);
                btnAutoFitColumnsInWindow.setSelected(true);
                btnCustomLayout.setSelected(false);

                radioMnuLayoutSinglePage.setSelected(false);
                radioMnuLayoutSinglePageContinuous.setSelected(false);
                radioMnuLayoutSideBySide.setSelected(false);
                radioMnuLayoutSideBySideContinuous.setSelected(false);
                radioMnuLayoutAutoFitColumnsInWindow
                    .setSelected(true);
                radioMnuLayoutUserDefined.setSelected(false);
                break;
            default:
                btnContinuous.setSelected(false);
                btnSinglePage.setSelected(false);
                btnSideBySide.setSelected(false);
                btnSideBySideContinuous.setSelected(false);
                btnAutoFitColumnsInWindow.setSelected(false);
                btnCustomLayout.setSelected(true);

                radioMnuLayoutSinglePage.setSelected(false);
                radioMnuLayoutSinglePageContinuous.setSelected(false);
                radioMnuLayoutSideBySide.setSelected(false);
                radioMnuLayoutSideBySideContinuous.setSelected(false);
                radioMnuLayoutAutoFitColumnsInWindow
                    .setSelected(false);
                radioMnuLayoutUserDefined.setSelected(true);
                break;

        }

        if (pageNum == 0
            || (pageNum == 1 && viewer.getPageCount() == 1))
        {
            return;
        }

        if (pageNum == viewer.getPageCount())
        {
            btnNextPage.setEnabled(false);
            btnLastPage.setEnabled(false);

            mnuNextPageItem.setEnabled(false);
            mnuLastPageItem.setEnabled(false);

            if (viewer.getPageCount() > 1)
            {
                btnFirstPage.setEnabled(true);
                btnPreviousPage.setEnabled(true);

                mnuFirstPageItem.setEnabled(true);
                mnuPreviousPageItem.setEnabled(true);
            }

        }
        else if (viewer.getCurrentPageNumber() == 1)
        {
            btnFirstPage.setEnabled(false);
            btnPreviousPage.setEnabled(false);

            mnuFirstPageItem.setEnabled(false);
            mnuPreviousPageItem.setEnabled(false);

            btnNextPage.setEnabled(true);
            btnLastPage.setEnabled(true);

            mnuNextPageItem.setEnabled(true);
            mnuLastPageItem.setEnabled(true);
        }
        else
        {
            btnFirstPage.setEnabled(true);
            btnPreviousPage.setEnabled(true);
            btnNextPage.setEnabled(true);
            btnLastPage.setEnabled(true);

            mnuFirstPageItem.setEnabled(true);
            mnuPreviousPageItem.setEnabled(true);
            mnuNextPageItem.setEnabled(true);
            mnuLastPageItem.setEnabled(true);
        }
      //TODO
/*
        if (showGapsBetweenPages)
        {
            chkMnuShowGapsBetweenPages.setSelected(true);
        }
        else
        {
            chkMnuShowGapsBetweenPages.setSelected(false);
        }

        if (showCoverPageDuringSideBySide)
        {
            chkMnuShowCoverPageDuringSideBySide.setSelected(true);
        }
        else
        {
            chkMnuShowCoverPageDuringSideBySide.setSelected(false);
        }

        if (showPageBordersWhenNoPageGaps)
        {
            chkMnuShowPageBorders.setSelected(true);
        }
        else
        {
            chkMnuShowPageBorders.setSelected(false);
        }*/
    
    }

    @Override
    public void needPassword(
        ViewerNeedPasswordEvent needPasswordEvent)
    {
         // old working one but not usable.
    //      JPanel panel = new JPanel(new FlowLayout());
    //
    //      JPasswordField field = new JPasswordField(10);
    //      panel.add(new JLabel("Password: "));
    //      panel.add(field);
    //
    //      field.requestFocus();
    //
    //      JOptionPane.showMessageDialog(this, panel,
    //          "Gnostice Document Viewer", JOptionPane.OK_OPTION
    //          | JOptionPane.QUESTION_MESSAGE);
    //
    //      char[] pin = field.getPassword();
    //      try
    //      {
    //          pwd = new String(pin);
    //      }
    //      finally
    //      {
    //          Arrays.fill(pin, ' ');
    //          field.setText("");
    //      }
    //      needPasswordEvent.setPassword(pwd);
          
          // prompts the dialog box repeatedly until the correct
          // password is provided or user can cancel the loading.
          final PasswordPanel passwordPanel = new PasswordPanel();
          JOptionPane optionPane = new JOptionPane(passwordPanel, JOptionPane.OK_CANCEL_OPTION,
                  JOptionPane.WARNING_MESSAGE);
    
          JDialog dialog = optionPane.createDialog(this, "Password");
    
          // Wire up FocusListener to ensure JPasswordField is able to
          // request focus when the dialog is first shown.
          dialog.addWindowFocusListener(new WindowAdapter()
          {
              @Override
              public void windowGainedFocus(WindowEvent e)
              {
                  passwordPanel.gainedFocus();
                  passwordPanel.passwordField.requestFocus();
                  passwordPanel.passwordField.requestFocusInWindow();
              }
          });
          
          dialog.setVisible(true);
    
          if (optionPane.getValue() != null
              && optionPane.getValue().equals(JOptionPane.OK_OPTION))
          {
              String password = new String(passwordPanel.getPassword());
    //          System.err.println("You entered: " + password);
              pwd = password;
              needPasswordEvent.setPassword(pwd);
              needPasswordEvent.setCallAgainIfUnsuccessful(true);
          }
          else
          {
              needPasswordEvent.setCallAgainIfUnsuccessful(false);
          }
    }

    @Override
    public void beginJob(PrinterBeginJobEvent arg0)
    {
        
    }

    @Override
    public void beginPage(PrinterBeginPageEvent arg0)
    {
        
    }

    @Override
    public void endJob(PrinterEndJobEvent arg0)
    {
        JOptionPane.showMessageDialog(this,
            "Document Printed Successfully", "Gnostice Document Viewer",
            JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void endPage(PrinterEndPageEvent arg0)
    {
        
    }

    @Override
    public void error(PrinterRenderErrorEvent arg0)
    {
        
    }

    @Override
    public void needPassword(PrinterNeedPasswordEvent printerNeedPasswordEvent)
    {
        /*
        JPanel panel = new JPanel(new FlowLayout());

        JPasswordField field = new JPasswordField(10);
        panel.add(new JLabel("Password: "));
        panel.add(field);

        field.requestFocus();

        JOptionPane.showMessageDialog(this, panel,
            "Gnostice Document Viewer", JOptionPane.OK_OPTION
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
         */    
        printerNeedPasswordEvent.setPassword(pwd);
    }
    
}
