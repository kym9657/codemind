package testcases.C0020_TOCTOU;

/*
Filename : CWE732_Incorrect_Permission_Assignment_for_Critical_Resource__basic_File_01_bad.java
*/



import testcasesupport.*;

import javax.servlet.http.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class C0020_TOCTOU_OpenEG extends AbstractTestCaseServletReturnString
{
	
	static int count;

	@RequestMapping(value="/test/concurrency_test.do")
	@ResponseBody
	@Override
	public String bad(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		 
	    StringBuffer buffer=new StringBuffer();			
	    buffer.append("실행결과1: "+ count +"<br/>");
	    count=count+getInt(request.getParameter("data"));
	    try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			buffer.append("처리 불가");
		}
	    buffer.append("실행결과2: "+count);
	
    return buffer.toString();
	}

	@Override
	public String good(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}

	public static  int getInt(String data){
		int i=-1;
		try {
		    i= Integer.parseInt(data);
		}catch(NumberFormatException e){
			return i;
		}
		return i;
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

