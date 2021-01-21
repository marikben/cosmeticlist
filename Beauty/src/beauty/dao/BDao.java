package beauty.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import beauty.paa.Beauty;

public class BDao {
	private Connection con=null;
	private ResultSet rs = null;
	private PreparedStatement stmtPrep=null; 
	private String sql;
	private String db ="Beauty.db";
	
	private Connection yhdista(){
    	Connection con = null;    	
    	String path = System.getProperty("catalina.base");    	
    	path = path.substring(0, path.indexOf(".metadata")).replace("\\", "/"); 
    	String url = "jdbc:sqlite:"+path+db;    	
    	try {	       
    		Class.forName("org.sqlite.JDBC");
	        con = DriverManager.getConnection(url);	
	        System.out.println("Yhteys avattu.");
	     }catch (Exception e){	
	    	 System.out.println("Yhteyden avaus epäonnistui.");
	        e.printStackTrace();	         
	     }
	     return con;
	}
	
	public ArrayList<Beauty> listaaKaikki(){
		ArrayList<Beauty> kosmetiikka = new ArrayList<Beauty>();
		sql = "SELECT * FROM tuotteet;";       
		try {
			con=yhdista();
			if(con!=null){ //jos yhteys onnistui
				stmtPrep = con.prepareStatement(sql);        		
        		rs = stmtPrep.executeQuery();   
				if(rs!=null){ //jos kysely onnistui									
					while(rs.next()){
						Beauty beauty = new Beauty();
						beauty.setTuoteID(rs.getInt(1));
						beauty.setNimi(rs.getString(2));
						beauty.setHinta(rs.getDouble(3));
						
						kosmetiikka.add(beauty);
						}					
				}				
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return kosmetiikka;
	}
	public ArrayList<Beauty> listaaKaikki(String hakusana){
		ArrayList<Beauty> tuotteet = new ArrayList<Beauty>();
		//sql = "SELECT * FROM tuotteet WHERE tuotenimi LIKE '"+hakusana+"'";   
		sql = "SELECT * FROM tuotteet WHERE tuotenimi LIKE ?";  
		try {
			con=yhdista();
			if(con!=null){ //jos yhteys onnistui
				stmtPrep = con.prepareStatement(sql);  
				stmtPrep.setString(1, "%" + hakusana + "%");   
        		rs = stmtPrep.executeQuery();   
				if(rs!=null){ //jos kysely onnistui							
					while(rs.next()){
						Beauty beauty = new Beauty();
						beauty.setTuoteID(rs.getInt(1));
						beauty.setNimi(rs.getString(2));
						beauty.setHinta(rs.getDouble(3));
						
						tuotteet.add(beauty);
					}						
				}
				con.close();
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return tuotteet;
	}
	
	
	public int maara() {
		ArrayList<Beauty> kosmetiikka = new ArrayList<Beauty>();
	sql = "SELECT * FROM tuotteet;";       
	try {
		con=yhdista();
		if(con!=null){ //jos yhteys onnistui
			stmtPrep = con.prepareStatement(sql);        		
    		rs = stmtPrep.executeQuery();   
			if(rs!=null){ //jos kysely onnistui									
				while(rs.next()){
					Beauty beauty = new Beauty();
					beauty.setTuoteID(rs.getInt(1));
					beauty.setNimi(rs.getString(2));
					beauty.setHinta(rs.getDouble(3));
					
					kosmetiikka.add(beauty);
					}					
			}				
		}
		con.close();
	} catch (Exception e) {
		e.printStackTrace();
	}		
	return kosmetiikka.size();
}
	
	public boolean muutaAsiakas(Beauty beauty){
		boolean paluuArvo=true;
		sql="UPDATE tuotteet SET tuotenimi=?, hinta=? WHERE tuoteid=?";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql); 
			stmtPrep.setString(1, beauty.getNimi());
			stmtPrep.setDouble(2, beauty.getHinta());
			stmtPrep.setInt(3, beauty.getTuoteID());
			stmtPrep.executeUpdate();
	        con.close();
		} catch (SQLException e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}
	
	public Beauty etsiAsiakas(int asiakas_id){
		Beauty beauty = null;
		sql = "SELECT * FROM tuotteet WHERE tuoteid=?";       
		try {
			con=yhdista();
			if(con!=null){ 
				stmtPrep = con.prepareStatement(sql); 
				stmtPrep.setInt(1, asiakas_id);
        		rs = stmtPrep.executeQuery();  
        		if(rs.isBeforeFirst()){ //jos kysely tuotti dataa, eli rekno on käytössä
        			//rs.next();
        			beauty = new Beauty(rs.getInt("tuoteid"), rs.getString("nimi"), rs.getDouble("hinta"));       			
				}	
        		con.close(); 
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return beauty;		
	}
	public boolean poistaAsiakas(int asiakas_id){ //Oikeassa elämässä tiedot ensisijaisesti merkitään poistetuksi.
		boolean paluuArvo=true;
		sql="DELETE FROM tuotteet WHERE tuoteid=?";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql); 
			stmtPrep.setInt(1, asiakas_id);			
			stmtPrep.executeUpdate();
	        con.close();
		} catch (SQLException e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}
	public boolean lisaaAsiakas(Beauty beauty){
		boolean paluuArvo=true;
		sql="INSERT INTO tuotteet(tuotenimi, hinta) VALUES(?,?)";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql); 
			stmtPrep.setString(1, beauty.getNimi());
			stmtPrep.setDouble(2, beauty.getHinta());
		
			stmtPrep.executeUpdate();
			//System.out.println("Uusin id on " + stmtPrep.getGeneratedKeys().getInt(1));
	        con.close();
		} catch (SQLException e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}
}