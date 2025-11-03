package testcasesupport;

import testcasesupport.*;

public class RUtil {

    public static boolean isAuthenticatedUser(String username, String passwd) {
    	String id = "id";
    	String pw = "pass!@#$";
    	if (id.equalsIgnoreCase(username) && pw.equals(passwd))
    	{
    		return true;
    	}	
    	else
    	{
    		return false;
    	}
    }
    public static RMessage LookupRMessageObject(String msgId) {
        return new RMessage(msgId);
    }
    
    public static String toHexString(byte buf[])
    {
    	StringBuffer sb = new StringBuffer();
    	for(int i=0;i<buf.length;i++)
    	{
    		sb.append(Integer.toHexString(0x0100 + (buf[i] & 0x00FF)).substring(1));
    	}
    	return sb.toString();
    }
    
    public static byte[] toByteArray(String hex)
    {
    	if (hex == null || hex.length() ==0)
    	{
    		return null;
    	}
    	
    	byte[] ba = new byte[hex.length() / 2];
    	for (int i=0;i<ba.length;i++)
    	{
    		ba[i] = (byte) Integer.parseInt(hex.substring(2*i,2*i+2), 16);
    	}
    	return ba;
    }
}
