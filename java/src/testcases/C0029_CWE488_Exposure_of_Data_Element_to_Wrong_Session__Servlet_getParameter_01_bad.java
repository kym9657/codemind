/*
Filename : CWE488_Exposure_of_Data_Element_to_Wrong_Session__Servlet_getParameter_01_bad.java
*/



import testcasesupport.*;

import javax.servlet.http.*;
import java.io.*;
import java.net.*;


import javax.servlet.http.*;
import java.io.*;

public class C0029_CWE488_Exposure_of_Data_Element_to_Wrong_Session__Servlet_getParameter_01_bad extends AbstractTestCaseServlet
{
    public String m_str_name;

    public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
    {
        String data;


        data = "name";



        /* FLAW */        
        m_str_name = request.getParameter(data);
        if (m_str_name != null) {
            response.getWriter().println("name: " + m_str_name);
        }


    }


    
    

    /* Below is the main(). It is only used when building this testcase on
       its own for testing or for building a binary to use in testing binary
       analysis tools. It is not used when compiling all the testcases as one
       application, which is how source code analysis tools are tested. */
    public static void main(String[] args) throws ClassNotFoundException,
           InstantiationException, IllegalAccessException
    {
        mainFromParent(args);
    }
}

