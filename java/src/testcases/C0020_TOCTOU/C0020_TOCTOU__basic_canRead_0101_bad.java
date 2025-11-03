/*
Filename : KRD_301_TOC_TOU__basic_canRead_0101_bad.java
CWEID    : CWE367_TOC_TOU
sinkname : canRead
sinkline : 48,
makedate : 2012 08 20
license  : Copyright KISA.
*/

package testcases.C0020_TOCTOU;

import testcasesupport.*;

import java.io.*;

// from K
public class C0020_TOCTOU__basic_canRead_0101_bad
{

    public void bad() throws Throwable
    {

        java.util.logging.Logger log_bsnk = java.util.logging.Logger.getLogger("local-logger");

        File f = new File("c:\\toctou.txt"); /* may need to be adjusted depending on script */

        if (!f.exists())
        {
            IO.writeLine("The file does not exist!");
            return;
        }

        BufferedReader bufread2 = null;
        InputStreamReader inread2 = null;
        FileInputStream finstr2 = null;

        String line = "";
        try
        {
            finstr2 = new FileInputStream("c:\\toctou.txt");
            inread2 = new InputStreamReader(finstr2);
            bufread2 = new BufferedReader(inread2);

            /*
            * Instructions: see the TOCTOU effect
            * During delay, swap the "test_evil.txt" and "test_bad.txt" files (or contents).
            */
            /* POSSIBLE FLAW: Delay between file access check and file read */
            IO.writeLine("Intentional delay. Hit return to continue.");
            bufread2.readLine();

            while((line = bufread2.readLine()) != null)
            {
                IO.writeLine(line);
            }
        }
        catch(IOException e)
        {
            log_bsnk.warning("Error reading from console");
        }
        finally
        {
            try
            {
                if( bufread2 != null )
                {
                    bufread2.close();
                }
            }
            catch( IOException e )
            {
                log_bsnk.warning("Error closing bufread2");
            }
            finally
            {
                try
                {
                    if( inread2 != null )
                    {
                        inread2.close();
                    }
                }
                catch( IOException e )
                {
                    log_bsnk.warning("Error closing inread2");
                }
                finally
                {
                    try
                    {
                        if( finstr2 != null )
                        {
                            finstr2.close();
                        }
                    }
                    catch( IOException e )
                    {
                        log_bsnk.warning("Error closing finstr2");
                    }
                }
            }
        }

    }


}

