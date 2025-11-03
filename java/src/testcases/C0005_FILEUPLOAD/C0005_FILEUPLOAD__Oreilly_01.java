package testcases.C0005_FILEUPLOAD;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class C0005_FILEUPLOAD__Oreilly_01 extends HttpServlet {
    @java.lang.Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            bad(req, resp);
        } catch (SQLException e) {
        }
        try {
            good(req, resp);
        } catch (SQLException e) {
        }
    }

    private void bad(HttpServletRequest req, HttpServletResponse resp)  throws IOException, SQLException {
        String savePath = "../save";
        MultipartRequest multipartRequest = new MultipartRequest(req, savePath, 1000, new DefaultFileRenamePolicy());

        String fileName = multipartRequest.getFilesystemName("filename");
        String sql = "INSERT INTO board(email,r_num,w_date,pwd,content,re_step,re_num,filename)"
                + " values ( ?, 0, sysdate(), ?, ?, ?, ?, ? ) ";

        Connection con = DriverManager.getConnection(String.format(
                "jdbc:derby:%s;password=codemind;bootPassword=%s", "xxx", "password"));
        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.executeUpdate();
        File file = multipartRequest.getFile("filename");
    }

    private void good(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        String savePath = "../save";
        MultipartRequest multipartRequest = new MultipartRequest(req, savePath, 1000, new DefaultFileRenamePolicy());

        String fileName = multipartRequest.getFilesystemName("filename");
        if(fileName != null) {
            String fileExt = fileName.substring(fileName.lastIndexOf("")+1).toLowerCase();
            if (!"gif".equals(fileExt) && !"jpg".equals(fileExt) && !"png".equals(fileExt)) {
                resp.getWriter().print("<script>alert('업로드 불가능한 파일입니다.')</script>");
                return;
            }
        }
        String sql = "INSERT INTO board(email,r_num,w_date,pwd,content,re_step,re_num,filename)"
                + " values ( ?, 0, sysdate(), ?, ?, ?, ?, ? ) ";

        Connection con = DriverManager.getConnection(String.format(
                "jdbc:derby:%s;password=codemind;bootPassword=%s", "xxx", "password"));
        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.executeUpdate();
        File file = multipartRequest.getFile("filename");
    }

}
