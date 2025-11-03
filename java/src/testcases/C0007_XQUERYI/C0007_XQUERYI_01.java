package testcases.C0007_XQUERYI;

import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;
import javax.xml.namespace.QName;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;

public class C0007_XQUERYI_01 {
	public void bad(Properties props) throws NamingException, XQException {
		// �쇅遺�濡쒕��꽣 �엯�젰�쓣 諛쏆쓬
		String name = props.getProperty("name");
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sunjndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://localhost:389/o=rootDir");
		javax.naming.directory.DirContext ctx = new InitialDirContext(env);
		javax.xml.xquery.XQDataSource xqds = (javax.xml.xquery.XQDataSource) ctx.lookup("xqj/personnel");
		javax.xml.xquery.XQConnection conn = xqds.getConnection();
		String es = "doc('users.xml')/userlist/user[uname='" + name + "']";
		// �엯�젰媛믪씠 XQuery�쓽 �씤�옄濡� �궗�슜
		// FLAW: 
		XQPreparedExpression expr = conn.prepareExpression(es);
		XQResultSequence result = expr.executeQuery();
		while (result.next()) {
		  String str = result.getAtomicValue();
		  if (str.indexOf('>') < 0) {
		    System.out.println(str);
		  }
		}
	}
	
	public void good(Properties props) throws NamingException, XQException {
		// �쇅遺�濡쒕��꽣 �엯�젰�쓣 諛쏆쓬
		String name = props.getProperty("name");
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sunjndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://localhost:389/o=rootDir");
		javax.naming.directory.DirContext ctx = new InitialDirContext(env);
		javax.xml.xquery.XQDataSource xqds = (javax.xml.xquery.XQDataSource) ctx.lookup("xqj/personnel");
		javax.xml.xquery.XQConnection conn = xqds.getConnection();
		String es = "doc('users.xml')/userlist/user[uname='$xpathname']";
		// �엯�젰媛믪씠 XQuery�쓽 �씤�옄濡� �궗�슜
		XQPreparedExpression expr = conn.prepareExpression(es);
		// bindString �븿�닔 �궗�슜�븿�쑝濡쒖뜥 �쇅遺� �엯�젰�쑝濡� �씤�븳 荑쇰━援ъ“ 蹂�議� 諛⑹�
		expr.bindString(new QName("xpathname"), name, null);
		XQResultSequence result = expr.executeQuery();
		while (result.next()) {
		  String str = result.getAtomicValue();
		  if (str.indexOf('>') < 0) {
		    System.out.println(str);
		  }
		}
	}
}
