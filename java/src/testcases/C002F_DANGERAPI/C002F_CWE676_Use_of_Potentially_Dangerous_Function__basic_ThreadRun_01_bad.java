package testcases.C002F_DANGERAPI;/*
Filename : CWE676_Use_of_Potentially_Dangerous_Function__basic_ThreadRun_01_bad.java
*/



import testcasesupport.*;

public class C002F_CWE676_Use_of_Potentially_Dangerous_Function__basic_ThreadRun_01_bad
{

    public void bad() throws Throwable
    {
    	/* FLAW */
    	new ThreadRun("DummyThread").run();
    }

}

class ThreadRun extends Thread
{
	public ThreadRun(String str)
	{
		super(str);
	}
	
	@Override
	public void run() {
		for(int i=0;i<10;i++)
		{
			try
			{
				IO.writeLine(i + " - Name: "+ getName());
				sleep(1000);
			}
			catch(InterruptedException e)
			{
				IO.writeLine("Exception Occurred");
			}
		}
	}
}

