package testcases.C0082_LIBI;

public class C0082_LIBI__simple_01 {
    public void bad() {
        // FLAW:
        System.loadLibrary("library.dll");
    }
    public void good() {
        // do not load
    }
}
