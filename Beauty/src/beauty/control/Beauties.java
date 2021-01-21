package beauty.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import beauty.paa.Beauty;
import beauty.dao.BDao;


@WebServlet("/Beauties/*")
public class Beauties extends HttpServlet {
	private static final long serialVersionUID = 1L;

 
    public Beauties() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Beauty.doGet()");
		String pathInfo = request.getPathInfo();
		System.out.println(pathInfo);
		String strJSON="";
		ArrayList<Beauty> kosmetiikka;
		BDao dao = new BDao();
		if(pathInfo.equals("/")) { //Haetaan kaikki asiakkaat 
			kosmetiikka = dao.listaaKaikki();
			strJSON = new JSONObject().put("kosmetiikka", kosmetiikka).toString();
				
		}else{ //Haetaan hakusanan mukaiset asiakkaat
			System.out.println(pathInfo);
			String hakusana = pathInfo.replace("/", "");
			kosmetiikka = dao.listaaKaikki(hakusana);			
			strJSON = new JSONObject().put("kosmetiikka", kosmetiikka).toString();				
		}	
	
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.println(strJSON);
		
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Beauty.doPut()");		
		JSONObject jsonObj = new JsonStrObj().convert(request); //Muutetaan kutsun mukana tuleva json-string json-objektiksi			
		Beauty beauty = new Beauty();	
		beauty.setTuoteID(Integer.parseInt(jsonObj.getString("tuoteid"))); //asiakas_id on String, joka pit‰‰ muuttaa int
		beauty.setNimi(jsonObj.getString("nimi"));
		beauty.setHinta(Double.parseDouble(jsonObj.getString("hinta")));
	
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		BDao dao = new BDao();			
		if(dao.muutaAsiakas(beauty)) { //metodi palauttaa true/false
			out.println("{\"response\":1}");  //Tietojen p‰ivitys onnistui {"response":1}	
		}else {
			out.println("{\"response\":0}");  //Tietojen p‰ivitys ep‰onnistui {"response":0}	
		} 		
	}
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Asiakkaat.doDelete()");
		String pathInfo = request.getPathInfo();	//haetaan kutsun polkutiedot, esim. /5		
		int asiakas_id = Integer.parseInt(pathInfo.replace("/", "")); //poistetaan polusta "/", j‰ljelle j‰‰ id, joka muutetaan int		
		BDao dao = new BDao();
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();		    
		if(dao.poistaAsiakas(asiakas_id)){ //metodi palauttaa true/false
			out.println("{\"response\":1}"); //Asiakkaan poisto onnistui {"response":1}
		}else {
			out.println("{\"response\":0}"); //Asiakkaan poisto ep‰onnistui {"response":0}
		}		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Asiakkaat.doPost()");
		JSONObject jsonObj = new JsonStrObj().convert(request); //Muutetaan kutsun mukana tuleva json-string json-objektiksi			
		Beauty beauty = new Beauty();		
		beauty.setNimi(jsonObj.getString("nimi"));
		beauty.setHinta(jsonObj.getDouble("hinta"));
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		BDao dao = new BDao();			
		if(dao.lisaaAsiakas(beauty)){ //metodi palauttaa true/false
			out.println("{\"response\":1}");  //Asiakkaan lis‰‰minen onnistui {"response":1}
		}else{
			out.println("{\"response\":0}");  //Asiakkaan lis‰‰minen ep‰onnistui {"response":0}
		}		
	}

}

