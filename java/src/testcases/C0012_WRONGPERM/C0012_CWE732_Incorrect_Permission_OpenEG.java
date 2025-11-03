package testcases.C0012_WRONGPERM;

/*
Filename : CWE732_Incorrect_Permission_Assignment_for_Critical_Resource__basic_File_01_bad.java
*/



import testcasesupport.*;

import javax.servlet.http.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;


import javax.servlet.http.*;
import java.io.*;

public class C0012_CWE732_Incorrect_Permission_OpenEG extends AbstractTestCaseServletReturnString
{

	@RequestMapping(value="/test/access_control_test.do")
	@ResponseBody
	@Override
	public String bad(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		 
		HttpSession session = request.getSession();
		StringBuffer buffer=new StringBuffer(); 
		MemberService service = new MemberService();
		 MemberModel m=null;
		 String id=request.getParameter("id");
		 String name = request.getParameter("name");
		 String action=request.getParameter("action");
		
		 // 사용자정보 조회 요청인 경우 DB에서 해당 사용자 정보를 조회한 결과를 응답
		 /* FLAW */
		 if( "view".equals(action)) {
		   if( id != null && ! "".equals(id)) {
		       m=service.findMember(id);
		       if(m==null) {
			      buffer.append("등록되지 않은 사용자입니다. ");
		       } else {
		          // 조회한 사용자 정보는 세션에 저장
			     session.setAttribute("member", m);
			     buffer.append("사용자ID: "+m.getUserId()+"<br/>");
			     buffer.append("고객명: "+m.getUserName()+"<br/>");
			     buffer.append("전화번호: "+m.getPinNo()+"<br/>");
			     buffer.append("가입일자: "+m.getJoinDate()+"<br/>"); 
		       }
		   }else{
		       buffer.append("userId가 입력되지 않았습니다.");
		   }
		
		   // 실행결과 사용자에 대한 고객 정보 수정
		 } else if( "modify".equals(action)) {
		   m=(MemberModel)session.getAttribute("member");
		   if(m==null) {
		       buffer.append("사용자정보 조회부터 실행하세요");
		   } else {
		       m.setPinNo(name);
		       service.modifyMember(m);
		       session.setAttribute("member", m);
		         buffer.append(m.getUserId()+"님의 고객번호 수정을 완료하였습니다.<br/>");   
			     buffer.append("사용자ID: "+m.getUserId()+"<br/>");
			     buffer.append("고객명: "+m.getUserName()+"<br/>");
			     buffer.append("전화번호: "+m.getPinNo()+"<br/>");
			     buffer.append("가입일자: "+m.getJoinDate()+"<br/>"); 
		    }

		   // 실행결과 사용자에 대한 고객정보 삭제
		 }else if ( "delete".equals(action)) {
		      m=(MemberModel)session.getAttribute("member");
		      if(m==null) {
			      buffer.append("사용자정보 조회부터 실행하세요");
		      } else {				
			service.deleteMember(m);
			session.removeAttribute("member");
			buffer.append(m.getUserId()+"님의  정보를 삭제하였습니다.");
		      }
		      
		  // 새로운 고객 정보 등록
		 }else if ( "edit".equals(action)) {
		    if( id != null && ! "".equals(id)) {
		        m=new MemberModel(0,id,id,name,"","");
		        int result=service.addMember(m);       
		        if ( result == 3) {
		        	 m=service.findMember(id);
		        	 if ( m != null) {    
		        		 session.setAttribute("member", m);
				         buffer.append(m.getUserId()+" 사용자 등록을 완료하였습니다.<br/>");   
					     buffer.append("사용자ID: "+m.getUserId()+"<br/>");
					     buffer.append("고객명: "+m.getUserName()+"<br/>");
					     buffer.append("전화번호: "+m.getPinNo()+"<br/>");
					     buffer.append("가입일자: "+m.getJoinDate()+"<br/>"); 
		        	 } else {
		        		 buffer.append("사용자 등록 실패: "+result);
		        	 }
		        } else {
			        buffer.append("사용자 등록 실패: "+result);
		        }
		    }else {
		         buffer.append("userId가 입력되지 않았습니다.");
		    }
		 }	
		 return buffer.toString();
	}

	@Override
	public String good(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub
		return null;
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

