package beauty.control;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beauty.dao.BDao;

/**
 * Servlet implementation class insert
 */
@WebServlet("/insert")
public class insert extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public insert() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("nimi");
		String hinta = request.getParameter("hinta");
		BDao dao = new BDao();
		int pituus = dao.maara();
		int id = pituus+1;
		String kohta = Integer.toString(id);
		String db ="Beauty.db";
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		try {
			String path = System.getProperty("catalina.base");    	
	    	path = path.substring(0, path.indexOf(".metadata")).replace("\\", "/"); 
	    	String url = "jdbc:sqlite:"+path+db; 
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection(url);
			
			String sql = "insert into tuotteet values(?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, kohta);
			ps.setString(2, name);
			ps.setString(3, hinta);
			
			ps.executeUpdate();
			
			ps.close();
			con.close();
			out.println("Onnistui");
		}catch(Exception e) {
			e.printStackTrace();
		}
		

}
}
