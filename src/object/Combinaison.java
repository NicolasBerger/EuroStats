package object;

import java.util.*;

public class Combinaison {

	private List<Integer> numeros = new ArrayList<>(5);
	private List<Integer> etoiles = new ArrayList<>(2);
	
	public Combinaison(List<Integer> n, List<Integer> e){
		this.numeros = n;
		this.etoiles = e;
	}

	public Combinaison() {
	}

	public List<Integer> getEtoiles() {
		return etoiles;
	}

	public List<Integer> getNumeros() {
		return numeros;
	}

	public void setNumeros(List<Integer> numeros) {
		this.numeros.addAll(numeros);
		Collections.sort(this.numeros);
	}

	public void setEtoiles(List<Integer> etoiles) {
	  this.etoiles.addAll(etoiles);
	  Collections.sort(this.etoiles);
	}
	
	@Override
  public String toString(){
		return new StringBuilder()
            .append(reduceList(this.numeros))
            .append(" ")
            .append(reduceList(this.etoiles))
            .toString();
	}
	
  public String toStringBasic(){
    return new StringBuilder("").append(reduceList(this.numeros)).toString();
  }
  
  private String reduceList(List list){
    return list.stream()
                .map(Object::toString)
                .reduce((a,b) -> a + "-" + b)
                .orElse("")
                .toString();
  }
  
  public boolean isUneEtoileCommun(List<Integer> other) {
    return countCommuns(this.etoiles,other) == 1;
  }

  public boolean isDeuxEtoilesCommuns(List<Integer> other) {
    return countCommuns(this.etoiles,other) == 2;
  }

  public boolean isUnNumeroCommun(List<Integer> other) {
    return countCommuns(this.numeros,other) == 1;
  }
  
  public boolean isDeuxNumerosCommuns(List<Integer> other) {
    return countCommuns(this.numeros,other) == 2;
  }
  
  public boolean isTroisNumerosCommuns(List<Integer> other) {
    return countCommuns(this.numeros,other) == 3;
  }
  
  public boolean isQuatreNumerosCommuns(List<Integer> other) {
    return countCommuns(this.numeros,other) == 4;
  }
  
  public boolean isCinqNumerosCommuns(List<Integer> other) {
    return countCommuns(this.numeros,other) == 5;
  }

  private int countCommuns(List<Integer> list, List<Integer> other){
    int count = 0;
    for (Integer integer : other) {
      if(list.contains(integer))
        count ++;
    }
    return count;
  }
}