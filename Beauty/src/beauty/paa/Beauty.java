package beauty.paa;

public class Beauty {
	private int TuoteID;
	private String Nimi;
	private double Hinta;

	public Beauty() {
		
	}

	public Beauty(int tuoteid, String nimi, double hinta) {
		this.TuoteID = tuoteid;
		this.Nimi = nimi;
		this.Hinta = hinta;
	}

	public int getTuoteID() {
		return TuoteID;
	}

	public void setTuoteID(int tuoteID) {
		TuoteID = tuoteID;
	}

	public String getNimi() {
		return Nimi;
	}

	public void setNimi(String nimi) {
		Nimi = nimi;
	}

	public double getHinta() {
		return Hinta;
	}

	public void setHinta(double hinta) {
		Hinta = hinta;
	}

	@Override
	public String toString() {
		return "Beauty [TuoteID=" + TuoteID + ", Nimi=" + Nimi + ", Hinta=" + Hinta + "]";
	}
	
}
