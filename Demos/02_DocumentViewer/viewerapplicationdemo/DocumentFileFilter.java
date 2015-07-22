package viewerapplicationdemo;


import java.io.File;

class DocumentFileFilter extends javax.swing.filechooser.FileFilter
{
    String extn = "";

    DocumentFileFilter(String extn)
    {
            this.extn = extn;
    }

    public boolean accept(File file)
    {
        String filename = file.getName();

        if (file.isDirectory())
        {
            return true;
        }
        else if (extn.equalsIgnoreCase("All XDoc Supported Files"))
        {
            if (filename.toLowerCase().endsWith(".pdf")
                || filename.toLowerCase().endsWith(".docx")
                || filename.toLowerCase().endsWith(".jpg")
                || filename.toLowerCase().endsWith(".tiff")
                || filename.toLowerCase().endsWith(".tif")
                || filename.toLowerCase().endsWith(".gif")
                || filename.toLowerCase().endsWith(".png")
                || filename.toLowerCase().endsWith(".bmp"))
            {
                return true;
            }
        }
        else if (extn.equalsIgnoreCase("*.docx"))
        {
            if (filename.toLowerCase().endsWith(".docx"))
            {
                return true;
            }
        }
        else if (extn.equalsIgnoreCase("*.pdf"))
        {
            if (filename.toLowerCase().endsWith(".pdf"))
            {
                return true;
            }
        }
        return false;
    }

    public String getDescription()
    {
        return extn;
    }

}
