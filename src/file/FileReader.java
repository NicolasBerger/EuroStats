package file;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import calcul.Analyse;
import object.*;

public class FileReader {
	
	public static void main(String args[]){
    Analyse.analyse(getData());
	}
	
	public static List<Tirage> getData(){
	  List<Tirage> tirages = new ArrayList<>();
	  try{
  	  Files.lines(Paths.get("data.txt")).forEach(l -> {
  	    String[] temp = l.split(" ");
  	    Tirage t = new Tirage();
  	    t.setDate(LocalDate.parse(temp[0]));
  	    Combinaison c = new Combinaison();
  	    c.setNumeros(
  	        (Arrays.asList(temp[1].split("-"))
  	            .stream()
  	            .map(Integer::parseInt)
  	            .collect(Collectors.toList())));
  	    c.setEtoiles(
  	        (Arrays.asList(temp[2].split("-"))
  	            .stream()
  	            .map(Integer::parseInt)
  	            .collect(Collectors.toList())));
  	    t.setCombinaison(c);
  	    try{
  	      t.setMontant(Integer.parseInt(temp[3]));
  	    }catch(Exception e){
  	      t.setMontant(0);
  	    }
  	    tirages.add(t);
  	  });
	  }catch(Exception e){
	    e.printStackTrace();
	  }
	  return tirages;
	}
	
	private static void recupererDonneesHTML() throws IOException{
	   List<String> lignes = new ArrayList<>();
	    Files.lines(Paths.get("in.txt")).forEach(lignes::add);
	    
	    List<Tirage> tirages = new ArrayList<>();
	    for (String l : lignes) {
	      if(l.matches(".*../../....</.*")){
	        l = l.substring(l.lastIndexOf("/") - 11, l.lastIndexOf("/") - 1);
	        Tirage t = new Tirage();
	        t.setDate(LocalDate.parse(l, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
	        t.setCombinaison(new Combinaison());
	        tirages.add(t);
	      }
	    }
	    
	    int numTirage = 0;
	    List<Integer> temp = new ArrayList<>(5);
	    
	    for (String l : lignes) {
	      if(l.contains("game_point")){
	        int n = Integer.parseInt(l.substring(l.lastIndexOf("\">") + 2, l.lastIndexOf("</")));
	        temp.add(n);
	        if(temp.size() == 5){
	          tirages.get(numTirage).getCombinaison().setNumeros(temp);
	          temp.clear();
	          numTirage ++;
	        }
	      }
	    }
	    
	    numTirage = 0;
	    temp = new ArrayList<>(2);
	    
	    for (String l : lignes) {
	      if(l.contains("star_small")){
	        int n = Integer.parseInt(l.substring(l.lastIndexOf("\">") + 2, l.lastIndexOf("</")));
	        temp.add(n);
	        if(temp.size() == 2){
	          tirages.get(numTirage).getCombinaison().setEtoiles(temp);
	          temp.clear();
	          numTirage ++;
	        }
	      }
	    }
	    
     numTirage = 0;
      
      for (String l : lignes) {
        if(l.contains("td align=\"right\"")){
          try{
            int montant = Integer.parseInt(l.substring(l.lastIndexOf("\">") + 2, l.lastIndexOf("</")));
            tirages.get(numTirage).setMontant(montant);
            numTirage ++;
          }catch(NullPointerException e){
            tirages.get(numTirage).setMontant(0);
            numTirage ++;
          }
        }
      }
	    tirages.forEach(t->System.out.println(t.getDate() + " " + t.getCombinaison()));
//	    System.out.println(tirages.size());
	}

}
