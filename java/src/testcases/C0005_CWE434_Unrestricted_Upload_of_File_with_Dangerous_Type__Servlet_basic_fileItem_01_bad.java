import testcasesupport.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.util.logging.Logger;
import com.oreilly.servlet.MultipartRequest;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.io.*;


import javax.servlet.ServletContext;
import javax.servlet.http.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;

import java.util.logging.Logger;

public class C0005_CWE434_Unrestricted_Upload_of_File_with_Dangerous_Type__Servlet_basic_fileItem_01_bad extends AbstractTestCaseServlet
{

    
    public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
    {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        ServletContext context = this.getServletContext();
        String path = context.getRealPath("/upload");
        File dir = new File(path);
        if(!dir.exists()) dir.mkdir();
        if (isMultipart) {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            
            try {
                List items = upload.parseRequest(request);
                Iterator iterator = items.iterator();
                while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();
                    if(item.getFieldName().equals("file"))
                    {
                    	String FileNametemp = item.getName();
                    	StringTokenizer st = new StringTokenizer(FileNametemp, "\\");
                    	while(st.hasMoreTokens())
                    	{
                    		FileNametemp = st.nextToken();
                    	}
                    	File uploadFile = new File(dir,FileNametemp);
                    	/* FLAW */
                        item.write(uploadFile);
                    }
                }
            } catch (FileUploadException e) {
                throw new FileUploadException("File Upload Exception has been occurred");
            } 
        }
    }
}

