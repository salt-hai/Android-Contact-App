package servlet;
import com.google.gson.Gson;
import dao.impl.UserDaoImpl;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
@WebServlet("/login")
public class  LoginServlet extends HttpServlet{

	  @Override
	    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        doPost(req,resp);
	    }

	    @Override
	    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        PrintWriter pw = resp.getWriter();
//	        获取前端发送过来的数据
	        String username = req.getParameter("username");
	        String password = req.getParameter("password");
	        System.out.println(username);
	        System.out.println(password);
	        try {
	            //DAO,Data Access Object
	            User user = new UserDaoImpl().findOne(username,password);
	            if(user==null){
	                pw.write("0");
	                pw.flush();
	            }else {
	                Gson gson = new Gson();
	                String resultStr = gson.toJson(user,User.class);
	                System.out.println(resultStr);
	                pw.write(resultStr);  //resultStr传数据
	                pw.flush();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
}
