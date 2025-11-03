/*
Filename : KRD_502_Improper_Resource_Shutdown__Environment_2_close_0101_good.java
CWEID    : CWE404
sinkname : close
sinkline : 74,
makedate : 2012 12 24
license  : Copyright KISA.
*/

package testcases.KRD_502_Improper_Resource_Shutdown;

import testcasesupport.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import testcasesupport.AbstractTestCase;
import testcasesupport.IO;

import java.util.logging.Level;
import java.util.logging.Logger;


import javax.servlet.http.*;

import java.util.logging.Logger;

public class KRD_502_Improper_Resource_Shutdown__Environment_2_close_0101_good extends AbstractTestCase
{


    public void good() throws Throwable
    {

        goodB2G();
    }

    
    
    private void goodB2G() throws Throwable
    {
        String data;

        Logger log_bad = Logger.getLogger("local-logger");

        /* get environment variable ADD */
        data = System.getenv("ADD");


		BufferedReader buffread2 = null;
		FileReader fread = null;
		try
		{
			File f = new File("c:\\file.txt");
			fread = new FileReader(f);
			buffread2 = new BufferedReader(fread);
	        String readString = buffread2.readLine();
	        
	        IO.writeLine(readString);
		}
		catch(IOException e)
		{
            IO.logger.log(Level.WARNING, "Error with stream reading", e);
		}		
		finally
		{
            try
            {
                if (buffread2 != null)
                {
                	/* FIX: Streams closed appropriately */
                    buffread2.close();
                }
            }
            catch( IOException ioe )
            {
                IO.logger.log(Level.WARNING, "Error closing BufferedReader", ioe);
            }
			
			try 
			{
				if( fread != null ) 
				{
					fread.close();
				}
			}
			catch( IOException ioe ) 
			{
				IO.logger.log(Level.WARNING, "Error closing FileReader", ioe);
			}
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

