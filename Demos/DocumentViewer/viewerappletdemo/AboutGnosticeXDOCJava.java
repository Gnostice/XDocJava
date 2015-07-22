package viewerappletdemo;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class AboutGnosticeXDOCJava extends JDialog
{
    private static final long serialVersionUID = 1L;

    public AboutGnosticeXDOCJava()
    {
        super();
        
        getContentPane().setBackground(Color.WHITE);
        
        // Create ImageIcon for Gnostice PDFOne Java Box Logo
//        ImageIcon iconImg = new ImageIcon(getClass().getResource("icons/BOX_pdfone_java.gif"));
        JLabel iconLabel = new JLabel("");
        iconLabel.setOpaque(true);
        iconLabel.setBackground(getContentPane().getBackground());
        
        // Create JEditorPane to write Product name
//        JEditorPane jepProductName = new JEditorPane("text/html", "<p><Font color='blue' size='6'>PDFOne Java</Font></p>");
        JEditorPane jepProductName = new JEditorPane("text/html", "<p><Font color='blue' size = '5'>" 
            + "Gnostice XtremeDocumentStudio for Java</Font>"
            + "<br>"
            + "<b>Document Viewer Demo</b>"
            + "<br>"
            + "<br>"
            + "Copyright &copy; 2002-2015"
            + "<br>"
            + "Gnostice Information Technologies Private Limited."
            + "<br>"
            + "All rights reserved."
            + "<br>"
            + "<a href='http://www.gnostice.com'>http://www.gnostice.com</a>"
            + "</p>");
        jepProductName.setEditable(false);
        jepProductName.setOpaque(true);
        jepProductName.addHyperlinkListener(new HyperlinkListener()
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
        
        String description = "Gnostice XtremeDocumentStudio (for Java) is the next-generation "
            + "multi-format document-processing component suite for J2SE/J2EE developers. "
            + "Currently, it supports viewing, printing, and converting "
            + "PDF, DOCX, BMP, JPEG, PNG, JPEG2000 and single-page/multi-page TIFF.";
        JTextArea txtArea = new JTextArea(description, 6, 10);
        txtArea.setEditable(false);
        txtArea.setLineWrap(true);
        txtArea.setWrapStyleWord(true);
        txtArea.setOpaque(true);
        JScrollPane txtAreaScrollPane = new JScrollPane(txtArea);
        txtAreaScrollPane.setOpaque(true);
        
        JButton btnOk = new JButton("OK");
        btnOk.setMnemonic('O');
        btnOk.setOpaque(true);
        btnOk.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                dispose();
            }
        });
        
        GridBagLayout gbl = new GridBagLayout();
        getContentPane().setLayout(gbl);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        getContentPane().add(iconLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        getContentPane().add(jepProductName, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 2.0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        getContentPane().add(txtAreaScrollPane, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 370, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        getContentPane().add(btnOk, gbc);
        
        setSize(500, 410);
        setModal(true);
        
        Dimension screenDimension = getToolkit().getScreenSize();
        setLocation((screenDimension.width - this.getWidth())/2, (screenDimension.height - this.getHeight())/2);
        setResizable(false);
        setTitle("About Gnostice XtremeDocumentStudio for Java");
        
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
    }
}
