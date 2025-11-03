package testcasesupport;

import java.io.IOException;
import javax.servlet.http.*;

public class RMessage {
    private String msgId = "ASDFQWER";
	public String userName = "userName";
	public String subField = "subfield";
	public String bodyField = "bodyField";

    public RMessage(String str_msgid) {
        msgId = str_msgid;
    }
	
	public String getUserName() throws Throwable {
        return userName;
    }
	public String getSubField() throws Throwable {
        return subField;
    }
	public String getBodyField() throws Throwable {
        return bodyField;
    }
} 
