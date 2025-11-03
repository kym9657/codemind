package testcases.C006A_SQLASTER;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class C006A_SQLASTER_01
{
	public void bad(Connection con) throws Throwable {
		// FLAW:
		String select = "SEleCT * ";
		String where = "and address = ?";
		PreparedStatement p = con.prepareStatement(select + "from people where (first_name = ? or last_name = ?) " + where);
		p.setString(1, "a");
		p.setString(2, "b");
		p.setString(3, "c");
	}

	public void good(Connection con) throws Throwable {
		String where = "and address = ?";
		PreparedStatement p = con.prepareStatement("select first_name from people where (first_name = ? or last_name = ?) " + where);
		p.setString(1, "a");
		p.setString(2, "b");
		p.setString(3, "c");
	}
}
