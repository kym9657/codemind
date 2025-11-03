/*
Filename : KRD_301_TOC_TOU__basic_thread_0101_bad.java
CWEID    : CWE367_TOC_TOU
sinkname : thread
sinkline : 29,37,70
makedate : 2012 08 20
license  : Copyright KISA.
*/

package testcases.C0020_TOCTOU;

import testcasesupport.*;

import java.io.*;

// from K
public class C0020_TOCTOU__basic_thread_0101_bad {
    public void bad()
    {
		FileAccessThread fileAccessThread = new FileAccessThread();
		FileDeleteThread fileDeleteThread = new FileDeleteThread();
		/* FLAW */
		fileAccessThread.start();
		fileDeleteThread.start();
    }
}

class FileAccessThread extends Thread
{
	/* FLAW */
    public void run()
	{
    	BufferedReader br = null;
	    try
		{
		    File f = new File("c:\\toctou.txt");
			if(f.exists())
			{
			    br = new BufferedReader(new FileReader(f));
			}
		}catch(IOException e)
		{
		    System.err.println("IOException occured");
		}
	    finally
	    {
	    	try
	    	{
	    		if(br != null)
	    		{
	    			br.close();
	    		}
	    	}catch(IOException e)
	    	{
	    		System.err.println("Error closing BufferedReader");
	    	}
	    }
	}
}

class FileDeleteThread extends Thread
{
	/* FLAW */
    public void run()
	{
	    File f = new File("c:\\toctou.txt");
		if(f.exists())
		{
		    f.delete();
		}
	}
}

