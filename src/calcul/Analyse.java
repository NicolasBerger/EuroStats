package calcul;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import object.Combinaison;
import object.GraphCombinaison;
import object.Tirage;

public class Analyse {

	public volatile static Map<String, GraphCombinaison> mapCombinaison = new HashMap<>();
	public static Map<Integer, Integer> mapFrequenceNumero = new HashMap<>();
	public static Map<Integer, Integer> mapFrequenceEtoile = new HashMap<>();
	public static int nbCombinaisons;
  
	public static void init() {
		IntStream.rangeClosed(1, 50).forEach(n -> mapFrequenceNumero.put(n, 0));
		IntStream.rangeClosed(1, 12).forEach(n -> mapFrequenceEtoile.put(n, 0));
	}
  
  	public static void analyse(List<Tirage> list){
  		long start = System.currentTimeMillis();
  		
  		nbCombinaisons = list.size();
  		List<Combinaison> combinaisons = list.stream()
											.map(Tirage::getCombinaison)
											.collect(Collectors.toList());
  		frequenceParNombre(combinaisons);
  		frequenceParEtoile(combinaisons);
  		frequenceParCombinaison(combinaisons);
  		gainParCombinaison(combinaisons);

  		long end = System.currentTimeMillis();
  		System.out.println("Durée du traitement : " + (end-start) + " ms");
  		System.out.println("Nombre de combinaisons générées : " + nbCombinaisons);
  	}
  
  	private static void gainParCombinaison(List<Combinaison> list) {
	 	DecimalFormat df = new DecimalFormat("#.#");
	 	df.setRoundingMode(RoundingMode.CEILING);
	 	list.forEach(c -> {
	 		int[] gains = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	 		for (Combinaison combinaison : list) {
	 			if(combinaison.isCinqNumerosCommuns(c.getNumeros()))
	 				if(combinaison.isDeuxEtoilesCommuns(c.getEtoiles()))        gains[0] += 1;
	 				else if(combinaison.isUneEtoileCommun(c.getEtoiles()))      gains[1] += 1;
	 				else                                                        gains[2] += 1;
	 			else if(combinaison.isQuatreNumerosCommuns(c.getNumeros()))
	 				if(combinaison.isDeuxEtoilesCommuns(c.getEtoiles()))        gains[3] += 1;
	 				else if(combinaison.isUneEtoileCommun(c.getEtoiles()))      gains[4] += 1;
	 				else                                                        gains[6] += 1;
	 			else if(combinaison.isTroisNumerosCommuns(c.getNumeros()))
	 				if(combinaison.isDeuxEtoilesCommuns(c.getEtoiles()))        gains[5] += 1;
	 				else if(combinaison.isUneEtoileCommun(c.getEtoiles()))      gains[8] += 1;
	 				else                                                        gains[9] += 1;
	 			else if(combinaison.isDeuxNumerosCommuns(c.getNumeros()))
	 				if(combinaison.isDeuxEtoilesCommuns(c.getEtoiles()))        gains[7] += 1;
	 				else if(combinaison.isUneEtoileCommun(c.getEtoiles()))      gains[12] += 1;
	 				else                                                        gains[13] += 1;
	 			else if(combinaison.isUnNumeroCommun(c.getNumeros()))
	 				if(combinaison.isDeuxEtoilesCommuns(c.getEtoiles()))        gains[10] += 1;
	 				else if(combinaison.isDeuxEtoilesCommuns(c.getEtoiles()))   gains[11] += 1;
	 				else if(combinaison.isUneEtoileCommun(c.getEtoiles()))      gains[14] += 1;
	 		}
	 		int total = 0;
	 		for (int i : gains) {
	 			total += i;
	 		}
	 		GraphCombinaison gc = mapCombinaison.get(c.toString());
	 		gc.setGains(gains);
	 		gc.setNbGains(total);
	 		gc.setPourcentageGains(Double.parseDouble(
						 				df.format(
					 						gc.getNbGains() * 100 / (double)nbCombinaisons
				 						).replace(',', '.')));
	 	});
	}

  	private static void frequenceParNombre(List<Combinaison> list){
  		list.forEach(c -> c.getNumeros().stream()
  										.forEach(n -> {
  											if(!mapFrequenceNumero.containsKey(n))
  												mapFrequenceNumero.put(n, 0);
  											mapFrequenceNumero.put(n, mapFrequenceNumero.get(n) + 1);
  										}));
  	}
  	
  	private static void frequenceParEtoile(List<Combinaison> list){
  		list.forEach(c -> c.getEtoiles().stream()
						  				.forEach(n -> {
						  					if(!mapFrequenceEtoile.containsKey(n))
						  						mapFrequenceEtoile.put(n, 0);
						  					mapFrequenceEtoile.put(n, mapFrequenceEtoile.get(n) + 1);
						  				}));
  	}
  
  	private static void frequenceParCombinaison(List<Combinaison> list){
  		list.forEach(c -> {
  			GraphCombinaison gc = new GraphCombinaison(c.toString());
  			for (Combinaison combinaison : list) {
  				if(combinaison.isCinqNumerosCommuns(c.getNumeros()))
  					gc.incrementeCinq(1);
  				else if(combinaison.isQuatreNumerosCommuns(c.getNumeros()))
  					gc.incrementeQuatre(1);
  				else if(combinaison.isTroisNumerosCommuns(c.getNumeros()))
  					gc.incrementeTrois(1);
  				else if(combinaison.isDeuxNumerosCommuns(c.getNumeros()))
  					gc.incrementeDeux(1);
  				else if(combinaison.isUnNumeroCommun(c.getNumeros()))
  					gc.incrementeUn(1);
  				if(combinaison.isUneEtoileCommun(c.getEtoiles()))
  					gc.incrementeEUn(1);
  				else if(combinaison.isDeuxEtoilesCommuns(c.getEtoiles()))
  					gc.incrementeEDeux(1);
  			}
  			gc.calculerCoefficient();
  			mapCombinaison.put(gc.getCombinaison(), gc);
  		});
  	}

}