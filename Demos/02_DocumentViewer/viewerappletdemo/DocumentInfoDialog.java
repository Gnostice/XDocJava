package viewerappletdemo;


import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.gnostice.documents.DocumentInfo;
import com.gnostice.documents.IDocument;

class DocumentInfoDialog extends JDialog
{
    private static final long serialVersionUID = 1L;
    
    JLabel lblFile;
    
    JTextField txtTitle;
    
    JTextField txtAuthor;
    
    JTextField txtSubject;
    
    JTextArea txtKeywords;
    
    JLabel lblCreated; 
    
    JLabel lblModified;
    
    JLabel lblApplication;
    
    JLabel lblPdfProducer;
    
    JLabel lblPdfVersion;
    
    JLabel lblLocation;
    
    JLabel lblFileSize;
    
    JLabel lblNumOfPages;
    
    JLabel lblSecurity;
    
    JButton btnOk;
    
    public DocumentInfoDialog()
    {
        super();
        lblFile = new JLabel();
        
        txtTitle = new JTextField();
        txtTitle.setEditable(false);
        txtTitle.setDisabledTextColor(Color.black);
        
        txtAuthor = new JTextField();
        txtAuthor.setEditable(false);
        txtAuthor.setDisabledTextColor(Color.black);
        
        txtSubject = new JTextField();
        txtSubject.setEditable(false);
        txtSubject.setDisabledTextColor(Color.black);
        
        txtKeywords = new JTextArea();
        txtKeywords.setRows(4);
        txtKeywords.setEditable(false);
        JScrollPane txtKeywordsScrollPane = new JScrollPane(
            txtKeywords, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        txtKeywords.setOpaque(false);
        txtKeywords.setDisabledTextColor(Color.black);
        
        lblCreated = new JLabel(); 
        
        lblModified = new JLabel();
        
        lblApplication = new JLabel();
        
        lblPdfProducer = new JLabel();
        
        lblPdfVersion = new JLabel();
        
        lblLocation = new JLabel();
        
        lblFileSize = new JLabel();
        
        lblNumOfPages = new JLabel();
        
        lblSecurity = new JLabel();
        
        btnOk = new JButton("Ok");
        btnOk.setMnemonic('O');
        btnOk.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                dispose();
            }
        });
        
        // Create Panel for Description panel frame
        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setBorder(BorderFactory.createTitledBorder("Description"));
        descriptionPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc1 = new GridBagConstraints();
        
        // First add all left hand side labels in the description panel
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.anchor = GridBagConstraints.EAST;
        gbc1.insets = new Insets(5, 5, 5, 5);
        gbc1.fill = GridBagConstraints.NONE;
        descriptionPanel.add(new JLabel("File:", SwingConstants.RIGHT), gbc1);
        
        gbc1.gridy = 1;
        descriptionPanel.add(new JLabel("Title:", SwingConstants.RIGHT), gbc1);
        
        gbc1.gridy = 2;
        descriptionPanel.add(new JLabel("Author:", SwingConstants.RIGHT), gbc1);
        
        gbc1.gridy = 3;
        descriptionPanel.add(new JLabel("Subject:", SwingConstants.RIGHT), gbc1);
        
        gbc1.gridy = 4;
        descriptionPanel.add(new JLabel("Keywords:", SwingConstants.RIGHT), gbc1);
        
        gbc1.gridy = 8;
        descriptionPanel.add(new JLabel("Created:", SwingConstants.RIGHT), gbc1);
        
        gbc1.gridy = 9;
        descriptionPanel.add(new JLabel("Modified:", SwingConstants.RIGHT), gbc1);
        
        gbc1.gridy = 10;
        descriptionPanel.add(new JLabel("Application:", SwingConstants.RIGHT), gbc1);
        
        // Now add all text fields and labels to the right hand side
        // that should come as the value for the labels on the left
        // hand size
        gbc1.gridx = 1;
        gbc1.gridy = 0;
        gbc1.weightx = 1.0;
        gbc1.anchor = GridBagConstraints.WEST;
        gbc1.insets = new Insets(5, 5, 5, 5);
        gbc1.fill = GridBagConstraints.BOTH;
        descriptionPanel.add(lblFile, gbc1);
        
        gbc1.gridy = 1;
        descriptionPanel.add(txtTitle, gbc1);
        
        gbc1.gridy = 2;
        descriptionPanel.add(txtAuthor, gbc1);
        
        gbc1.gridy = 3;
        descriptionPanel.add(txtSubject, gbc1);
        
        gbc1.gridy = 4;
        gbc1.weighty = 4.0;
        gbc1.gridheight = 4;
        descriptionPanel.add(txtKeywordsScrollPane, gbc1);
        
        gbc1.gridy = 8;
        gbc1.gridheight = 1;
        gbc1.weighty = 0.0;
        descriptionPanel.add(lblCreated, gbc1);
        
        gbc1.gridy = 9;
        descriptionPanel.add(lblModified, gbc1);
        
        gbc1.gridy = 10;
        descriptionPanel.add(lblApplication, gbc1);
        
        
        JPanel advancedPanel = new JPanel();
        advancedPanel.setBorder(BorderFactory.createTitledBorder("Advanced"));
        advancedPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        
        // First add all left hand side labels in the advanced panel
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        gbc2.anchor = GridBagConstraints.EAST;
        gbc2.insets = new Insets(5, 5, 5, 5);
        gbc2.fill = GridBagConstraints.NONE;
        advancedPanel.add(new JLabel("PDF Producer:", SwingConstants.RIGHT), gbc2);
        
        gbc2.gridy = 1;
        advancedPanel.add(new JLabel("PDF Version:", SwingConstants.RIGHT), gbc2);
        
        gbc2.gridy = 2;
        advancedPanel.add(new JLabel("Location:", SwingConstants.RIGHT), gbc2);
        
        gbc2.gridy = 3;
        advancedPanel.add(new JLabel("File Size:", SwingConstants.RIGHT), gbc2);
        
        gbc2.gridy = 4;
        advancedPanel.add(new JLabel("Number of Pages:", SwingConstants.RIGHT), gbc2);
        
        gbc2.gridy = 5;
        advancedPanel.add(new JLabel("Secured:", SwingConstants.RIGHT), gbc2);
        
        // Now add all text fields and labels to the right hand side
        // that should come as the value for the labels on the left
        // hand size
        gbc2.gridx = 1;
        gbc2.gridy = 0;
        gbc2.weightx = 1.0;
        gbc2.anchor = GridBagConstraints.WEST;
        gbc2.insets = new Insets(5, 5, 5, 5);
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        advancedPanel.add(lblPdfProducer, gbc2);
        
        gbc2.gridy = 1;
        advancedPanel.add(lblPdfVersion, gbc2);
        
        gbc2.gridy = 2;
        advancedPanel.add(lblLocation, gbc2);
        
        gbc2.gridy = 3;
        advancedPanel.add(lblFileSize, gbc2);
        
        gbc2.gridy = 4;
        advancedPanel.add(lblNumOfPages, gbc2);
        
        gbc2.gridy = 5;
        advancedPanel.add(lblSecurity, gbc2);
        
        Container contentPane = getContentPane();
        GridBagLayout gbl = new GridBagLayout();
        contentPane.setLayout(gbl);
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.fill = GridBagConstraints.BOTH;
        contentPane.add(descriptionPanel, gbc);
        
        gbc.gridy = 1;
        contentPane.add(advancedPanel, gbc);
        
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, 5, 5, 10);
        gbc.fill = GridBagConstraints.NONE;
        contentPane.add(btnOk, gbc);
        
        setSize(new Dimension(600, 580));
        setModal(true);
        
        pack();
        
        Dimension screenDimension = getToolkit().getScreenSize();
        setLocation((screenDimension.width - this.getWidth()) / 2,
            (screenDimension.height - this.getHeight()) / 2);
        setResizable(false);
        setTitle("Document Properties");
        
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
    
    public Dimension getPreferredSize()
    {
        return new Dimension(600, 580);
    }
    
    public void updateDocumentInfo(IDocument d)
    {
        
        // set values for the fields in the Description Panel
        DocumentInfo documentInfo = d.getDocumentInfo();
        if(documentInfo == null )
        {
            return;
        }
        String inputFileName = d.getFileName();
        lblFile.setText(inputFileName == null? "" : inputFileName);
        txtTitle.setText(documentInfo.getTitle());
        txtAuthor.setText(documentInfo.getAuthor());
        txtSubject.setText(documentInfo.getSubject());
        txtKeywords.setText(documentInfo.getKeywords());
        
//        Date creationDate = d.getCreationDate();
//        SimpleDateFormat df = new SimpleDateFormat("");
        
        Date creationDate = documentInfo.getCreatedDate();
        
        if (creationDate == null)
        {
            lblCreated.setText("");
        }
        else
        {
            String strCreationDate = creationDate.toString();
            lblCreated.setText(strCreationDate);
        }
        
        Date modifiedDate = documentInfo.getModifiedDate();
        
        if (modifiedDate == null)
        {
            lblModified.setText("");
        }
        else
        {
            String strModifiedDate = modifiedDate.toString();
            lblModified.setText(strModifiedDate);
        }
        
        lblApplication.setText(documentInfo.getCreator());
        
        // set values for the fields in the Adavanced Panel
//        lblPdfProducer.setText(documentInfo.getProducer());
        lblPdfVersion.setText(documentInfo.getVersion());
//TODO
        /*String inputFilePath = d.getInputFilePath();
        
        if (inputFilePath != null)
        {
            File inputFileDirPath = new File(inputFilePath);
            lblLocation.setText(inputFileDirPath.getAbsolutePath());
            
            File inputFile = new File(inputFileDirPath, inputFileName);
            
            lblFileSize.setText(((double)inputFile.length() / 1024.0) + " KB (" + inputFile.length() + " Bytes)");
        }
        else // the file may be loaded through byte[]
        {
            lblLocation.setText("");
            lblFileSize.setText("");
        }*/
        
        lblNumOfPages.setText(String.valueOf(d.getCurrentPageCount().getPageCount()));
        lblSecurity.setText(documentInfo.isEncrypted()? "Yes" : "No");
    }
}
