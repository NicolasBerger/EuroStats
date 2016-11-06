package object;

public class GraphCombinaison {

	private String combinaison;
	private int un = 0;
	private int deux = 0;
	private int trois = 0;
	private int quatre = 0;
	private int cinq = 0;
	private int eUn = 0;
	private int eDeux = 0;
	private int coefficient = 0;
	private int nbGains = 0;
	private int[] gains;
	private double pourcentageGains = 0;

	public GraphCombinaison(String s) {
		this.combinaison = s;
	}

	public void incrementeUn(int i) {
		this.un += i;
	}

	public void incrementeDeux(int i) {
		this.deux += i;
	}

	public void incrementeTrois(int i) {
		this.trois += i;
	}

	public void incrementeQuatre(int i) {
		this.quatre += i;
	}

	public void incrementeCinq(int i) {
		this.cinq += i;
	}

	public void incrementeEUn(int i) {
		this.eUn += i;
	}

	public void incrementeEDeux(int i) {
		this.eDeux += i;
	}

	public String getCombinaison() {
		return combinaison;
	}

	public void setCombinaison(String combinaison) {
		this.combinaison = combinaison;
	}

	public int getUn() {
		return un;
	}

	public int getDeux() {
		return deux;
	}

	public int getTrois() {
		return trois;
	}

	public int getQuatre() {
		return quatre;
	}

	public int getCinq() {
		return cinq;
	}

	public int getEUn() {
		return eUn;
	}

	public int getEDeux() {
		return eDeux;
	}

	public int getCoefficient() {
		return coefficient;
	}

	public void calculerCoefficient() {
		this.coefficient = 15* (getCinq()   + getEDeux())
	                      +14* (getCinq()   + getEUn())
	                      +13*  getCinq()
	                      +12* (getQuatre() + getEDeux())
	                      +11* (getQuatre() + getEUn())
	                      +10* (getTrois()  + getEDeux())
	                      + 9*  getQuatre()
	                      + 8* (getDeux()   + getEDeux())
	                      + 7* (getTrois()  + getEUn())
	                      + 6*  getTrois()
	                      + 5* (getUn()     + getEDeux())
	                      + 4*  getEDeux()
	                      + 3* (getDeux()   + getEUn())
	                      + 2*  getDeux()
	                      +     getEUn();
	}

	public String getGains() {
		StringBuilder sb = new StringBuilder();
		for (int i : gains) {
			sb.append(i);
			sb.append("-");
		}
		return sb.substring(0, sb.lastIndexOf("-"));
	}

	public void setGains(int[] gains) {
		this.gains = gains;
	}

	public int getNbGains() {
		return nbGains;
	}

	public void setNbGains(int nbGains) {
		this.nbGains = nbGains;
	}

	public double getPourcentageGains() {
		return pourcentageGains;
	}

	public void setPourcentageGains(double pourcentageGains) {
		this.pourcentageGains = pourcentageGains;
	}
  
}
