package testcases.C0082_LIBI;

import javax.servlet.http.HttpServletRequest;

public class C0082_LIBI__getParameter_01 {
    public void bad(HttpServletRequest request) {
        // FLAW:
        System.loadLibrary(request.getParameter("lib"));
    }
    public void good(HttpServletRequest request) {
        // do not load
    }
}
