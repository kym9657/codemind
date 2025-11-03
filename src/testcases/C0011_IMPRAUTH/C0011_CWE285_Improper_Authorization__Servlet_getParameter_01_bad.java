package testcases.C0011_IMPRAUTH;/*
Filename : CWE285_Improper_Authorization__Servlet_getParameter_01_bad.java
*/



import testcasesupport.*;

import testcasesupport.RUtil;
import java.util.logging.Logger;



import javax.servlet.http.*;
import java.io.*;

public class C0011_CWE285_Improper_Authorization__Servlet_getParameter_01_bad
{

    public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
    {
        String data;


        data = "passwd";


        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String msgId = request.getParameter("msgId");
        if (username == null || password == null || !RUtil.isAuthenticatedUser(username, password))
        {
            throw new Exception("Invalid username, password");
        }
        if (msgId == null)
        {
            throw new Exception("Invalid msgId");            
        }
        RMessage msg = RUtil.LookupRMessageObject(msgId);
        /* FLAW */
        if (msg != null) {
            response.getWriter().println("From: " + msg.getUserName());
            response.getWriter().println("Subject: " + msg.getSubField());
            response.getWriter().println("Body: " + msg.getBodyField());            
        }

    }

}

