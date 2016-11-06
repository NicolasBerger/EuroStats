package object;
import java.time.LocalDate;

public class Tirage {

	private Combinaison combinaison;
	private LocalDate date;
	private int montant;
	
	public Tirage(){};
	
	public Tirage(Combinaison c, LocalDate d, int m){
		this.combinaison = c;
		this.date = d;
		this.setMontant(m);
	}

	public Combinaison getCombinaison() {
		return combinaison;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setCombinaison(Combinaison combinaison) {
		this.combinaison = combinaison;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	@Override
  public String toString(){
		return date + " " + combinaison;
	}

  public int getMontant() {
    return montant;
  }

  public void setMontant(int montant) {
    this.montant = montant;
  }
	
}