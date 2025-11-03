/*
Filename : KRD_502_Improper_Resource_Shutdown__fromDB_2_close_0101_bad.java
CWEID    : CWE404
sinkname : close
sinkline : 128,
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

import java.util.logging.Logger;


import javax.servlet.http.*;

import java.util.logging.Level;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.logging.Logger;

public class KRD_502_Improper_Resource_Shutdown__fromDB_2_close_0101_bad extends AbstractTestCase
{

    public void bad() throws Throwable
    {
        String data;

        Logger log_bad = Logger.getLogger("local-logger");

        data = ""; /* init data */

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        BufferedReader buffread = null;
        InputStreamReader instrread = null;
        try {
            /* setup the connection */
            conn = IO.getDBConnection();

            /* prepare and execute a (hardcoded) query */
            statement = conn.prepareStatement("select name from users where id=1");
            rs = statement.executeQuery();
            rs.next();
            
            data = rs.getString(1);
        }
        catch( SQLException se )
        {
            IO.logger.log(Level.WARNING, "Error with SQL statement", se);
        }
        finally {
                /* Close database objects */
                try {
                    if( rs != null )
                    {
                        rs.close();
                    }
                }
                catch( SQLException se )
                {
                    IO.logger.log(Level.WARNING, "Error closing ResultSet", se);
                }

                try {
                    if( statement != null )
                    {
                        statement.close();
                    }
                }
                catch( SQLException se )
                {
                    IO.logger.log(Level.WARNING, "Error closing Statement", se);
                }

                try {
                    if( conn != null )
                    {
                        conn.close();
                    }
                }
                catch( SQLException se)
                {
                    IO.logger.log(Level.WARNING, "Error closing Connection", se);
                }
            }    


        BufferedReader buffread2 = null;
		FileReader fread = null;
		try
		{
			File f = new File(data);
			fread = new FileReader(f);
			buffread2 = new BufferedReader(fread);
	        String readString = buffread2.readLine();
	        if(readString == null)
	        {
	        	throw new IOException();
	        }
	        
	        
	        IO.writeLine(readString);
			
			try
            {
                if (buffread2 != null)
                {
                	/* FLAW: Attempts to close the streams should be in a finally block. */
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
		catch(IOException e)
		{
           	IO.logger.log(Level.WARNING, "Error with stream reading", e);
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

