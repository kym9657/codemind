package testcases.C000E_BUFOVER;

import javax.servlet.http.HttpServletRequest;

public class C000E_BUFOVER__Array_03 {
    public String bad(HttpServletRequest request, String[] data) throws Throwable {
        String[] y = new String[3];
        y[0] = "a";
        y[1] = "b";
        y[2] = "c";

        /* FLAW: */
        return y[3];
    }
    public String good(HttpServletRequest request, String[] data) throws Throwable {
        String[] y = new String[3];
        y[0] = "a";
        y[1] = "b";
        y[2] = "c";

        /* FIX: */
        return y[2];
    }
}
