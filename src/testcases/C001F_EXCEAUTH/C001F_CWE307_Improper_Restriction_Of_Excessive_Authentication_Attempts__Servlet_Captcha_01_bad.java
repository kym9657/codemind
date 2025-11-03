package testcases.C001F_EXCEAUTH;/*
Filename : CWE307_Improper_Restriction_Of_Excessive_Authentication_Attempts__Servlet_Captcha_01_bad.java
*/



import testcasesupport.*;

import java.util.logging.Logger;



import javax.servlet.http.*;
import java.io.*;

public class C001F_CWE307_Improper_Restriction_Of_Excessive_Authentication_Attempts__Servlet_Captcha_01_bad
{

    public void bad(HttpServletRequest request, HttpServletResponse response) throws Throwable
    {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        /* FLAW */
        if (username == null || password == null || !RUtil.isAuthenticatedUser(username, password))
        {
        	response.getWriter().println("Invalid username, password<br/>");
        }
        else
    	{
    		response.getWriter().println(username + " has successfully logged in!");
    	}
    }


}

