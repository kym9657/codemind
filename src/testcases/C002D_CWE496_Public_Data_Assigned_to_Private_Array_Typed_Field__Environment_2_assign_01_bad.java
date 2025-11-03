/*
Filename : CWE496_Public_Data_Assigned_to_Private_Array_Typed_Field__Environment_2_assign_01_bad.java
*/



import testcasesupport.*;

import java.io.BufferedReader;
import java.io.IOException;

import java.util.logging.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


import javax.servlet.http.*;

import java.util.logging.Logger;

public class C002D_CWE496_Public_Data_Assigned_to_Private_Array_Typed_Field__Environment_2_assign_01_bad extends AbstractTestCase
{


    private byte[] foo = {};
    public void setFoo(byte[] msg)
    {
		/* FLAW */
    	foo = msg;
    }

    public void bad() throws Throwable

    {
        String data;

        Logger log_bad = Logger.getLogger("local-logger");

        /* get environment variable ADD */
        data = System.getenv("ADD");


        /* FLAW */
		setFoo(data.getBytes());


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

