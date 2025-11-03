package testcases.C001E_DOWNCODE;/*
Filename : CWE494_Download_of_Code_Without_Integrity_Check__Environment_URLClassLoader_01_bad.java
*/



import testcasesupport.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;


import javax.servlet.http.*;

import java.util.logging.Logger;

public class C001E_CWE494_Download_of_Code_Without_Integrity_Check__Environment_URLClassLoader_01_bad
{

    
    public void bad() throws Throwable
    {
        String data;

        Logger log_bad = Logger.getLogger("local-logger");

        /* get environment variable ADD */
        data = System.getenv("TEST216");

        URL classUrl = null;
        URLClassLoader ucl = null;
        try{	
        	classUrl = new URL(data);
        	URL[] classUrls = { classUrl };
        	/* FLAW */
        	ucl = new URLClassLoader(classUrls);
        	Class c = ucl.loadClass("org.apache.commons.lang.ArrayUtils"); 
        	for(Field fld: c.getDeclaredFields()) {
        		System.out.println("Field name" + fld.getName());
        	}
        }
        catch(NoClassDefFoundError e)
        {
        	IO.logger.log(Level.WARNING, "Error in URLClassLoader", e);
        }
        catch(ClassNotFoundException e)
        {
        	IO.logger.log(Level.WARNING, "Error in URLClassLoader", e);
        }
        finally
        {
        	try
        	{
        		if(ucl != null)
        		{
        			ucl.close();
        		}
        	}catch(NoClassDefFoundError e)
        	{
        		IO.logger.log(Level.WARNING, "Error closing URLClassLoader", e);
        	}
        }

    }

}

