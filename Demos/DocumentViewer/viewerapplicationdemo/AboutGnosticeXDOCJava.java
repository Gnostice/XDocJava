package viewerapplicationdemo;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class AboutGnosticeXDOCJava extends JDialog
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();

    /**
     * Create the dialog.
     * @param viewerDemo 
     */
    public AboutGnosticeXDOCJava(JFrame parent)
    {
        super(parent);
        
        setTitle("About Gnostice XtremeDocumentStudio (for Java)");
        setSize(520, 420);
        setModal(true);
        
        Dimension screenDimension = getToolkit().getScreenSize();
        setLocation((screenDimension.width - this.getWidth())/2, (screenDimension.height - this.getHeight())/2);
        setResizable(false);
        
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(
            KeyEvent.VK_ESCAPE, 0, false);

        getRootPane().getInputMap(
            JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
            escapeKeyStroke, "escape");
        getRootPane().getActionMap().put("escape",
            new AbstractAction()
            {
                private static final long serialVersionUID = 1L;

                public void actionPerformed(ActionEvent e)
                {
                    dispose();
                }
            });
        
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        // commented as it is not showing the icon transparently
//        InputStream iconImgStream = getClass().getResourceAsStream("icons/ICON_XtrmDocumentStudio_Java.png");
//        BufferedImage bufImg;
//        try
//        {
//            bufImg = ImageIO.read(iconImgStream);
//            Image scaledImg = bufImg.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
//            setIconImage(scaledImg);
//        }
//        catch (IOException e1)
//        {
//        }
        
//        InputStream iconImgStream = getClass().getResourceAsStream("icons/ICON-XtremeDocumentStudio_Java_small.png");
//        BufferedImage bufImg;
//        try
//        {
//            bufImg = ImageIO.read(iconImgStream);
//            Image scaledImg = bufImg.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
//            setIconImage(scaledImg);
//        }
//        catch (IOException e1)
//        {
//        }
        
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
        gbl_contentPanel.rowHeights = new int[]{0, 0, 0};
        gbl_contentPanel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
        gbl_contentPanel.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
        contentPanel.setLayout(gbl_contentPanel);
        {
            JLabel lblProductBoxshot = new JLabel();
            lblProductBoxshot.setHorizontalAlignment(SwingConstants.CENTER);
            lblProductBoxshot.setBackground(Color.WHITE);
            lblProductBoxshot.setOpaque(true);
            lblProductBoxshot.setIcon(new ImageIcon(this.getClass().getResource("icons/BOX_XDoc_Java_Resampled_135x211.png")));

//            InputStream boxImgStream = getClass().getResourceAsStream("icons/BOX_XDoc_Java.png");
//            BufferedImage boxBufImg;
//            try
//            {
//                boxBufImg = ImageIO.read(boxImgStream);
//                Image scaledImg = boxBufImg.getScaledInstance(135, 211, Image.SCALE_SMOOTH);
//                ImageIcon boxImageIcon = new ImageIcon(scaledImg);
//                lblProductBoxshot.setIcon(boxImageIcon);
//            }
//            catch (IOException e1)
//            {
//            }
            
            
            GridBagConstraints gbc_lblProductBoxshot = new GridBagConstraints();
            gbc_lblProductBoxshot.insets = new Insets(2, 2, 2, 2);
            gbc_lblProductBoxshot.gridx = 0;
            gbc_lblProductBoxshot.gridy = 0;
            gbc_lblProductBoxshot.weightx = 1.0;
            gbc_lblProductBoxshot.weighty = 1.0;
            contentPanel.add(lblProductBoxshot, gbc_lblProductBoxshot);
        }
        {
            String editorText = "<p><Font size = '5'>" 
                + "Gnostice Document Viewer Demo</Font>"
                + "<br>"
                + "<a href='http://www.gnostice.com/XtremeDocumentStudio_Java.asp'>Powered by Gnostice XtremeDocumentStudio (for Java)</a>"
                + "<br>"
                + "<br>"
                + "Copyright &copy; 2002-2015"
                + "<br>"
                + "Gnostice Information Technologies Private Limited."
                + "<br>"
                + "All rights reserved."
                + "<br>"
                + "<a href='http://www.gnostice.com'>http://www.gnostice.com</a>"
                + "</p>";
            
            JEditorPane editorPane = new JEditorPane("text/html", editorText);
            editorPane.setEditable(false);
            editorPane.setOpaque(true);
            editorPane.addHyperlinkListener(new HyperlinkListener()
            {
                public void hyperlinkUpdate(HyperlinkEvent hle)
                {
                    if (HyperlinkEvent.EventType.ACTIVATED.equals(hle
                        .getEventType()))
                    {
                        BareBonesBrowserLaunch.openURL(hle.getURL().toString());
                    }
                }
            });
            GridBagConstraints gbc_editorPane = new GridBagConstraints();
            gbc_editorPane.insets = new Insets(2, 2, 2, 2);
            gbc_editorPane.fill = GridBagConstraints.BOTH;
            gbc_editorPane.gridx = 1;
            gbc_editorPane.gridy = 0;
            contentPanel.add(editorPane, gbc_editorPane);
        }
        {
            JScrollPane scrollPane = new JScrollPane();
            GridBagConstraints gbc_scrollPane = new GridBagConstraints();
            gbc_scrollPane.weightx = 1.0;
            gbc_scrollPane.weighty = 1.0;
            gbc_scrollPane.gridwidth = 2;
            gbc_scrollPane.insets = new Insets(2, 2, 2, 2);
            gbc_scrollPane.fill = GridBagConstraints.BOTH;
            gbc_scrollPane.gridx = 0;
            gbc_scrollPane.gridy = 1;
            contentPanel.add(scrollPane, gbc_scrollPane);
            {
                String description = "Gnostice XtremeDocumentStudio (for Java) is the next-generation "
                    + "multi-format document-processing component suite for J2SE/J2EE developers. "
                    + "Currently, it supports viewing, printing, and converting "
                    + "PDF, DOCX, BMP, JPEG, PNG, JPEG2000 and single-page/multi-page TIFF.";
                JTextArea textArea = new JTextArea(description);
                textArea.setEditable(false);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                textArea.setOpaque(true);
                scrollPane.setViewportView(textArea);
            }
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setBackground(Color.WHITE);
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            JButton okButton = new JButton("OK");
            okButton.setHorizontalAlignment(SwingConstants.RIGHT);
            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    dispose();
                }
            });
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
            okButton.setActionCommand("OK");
            buttonPane.add(okButton);
            getRootPane().setDefaultButton(okButton);
        }
    }

}
