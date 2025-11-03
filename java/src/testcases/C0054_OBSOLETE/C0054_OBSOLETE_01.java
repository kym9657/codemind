package testcases.C0054_OBSOLETE;

class C0054_OBSOLETE_01 {
	public void bad(int a, int b, String s, byte[] x) {
		// FLAW: use deprecated function
		s.getBytes(1, 2, x, 1);
	}
	public void good(String s) {
		s.getBytes();
	}
}