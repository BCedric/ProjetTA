import java.awt.List;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVReader;

import wic.wsd.Stats;
import wic.wsd.dictionary.*;

public class Main {

	public static void main(String[] args) {
		Dictionary dico = new Dictionary();
		try {
			ex20();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		afficherNbMots(dico, "./Dict-Lesk-etendu.xml");
//		afficherNbMots(dico, "./Dict-Lesk.xml");
//		sensMot("pine", dico);
//		sensMot("cone", dico);
//		afficherSimilarite("pine", "cone", dico, "Dict-Lesk.xml");
//		afficherSimilarite("pine", "cone", dico, "Dict-Lesk-etendu.xml");
//		afficherSimilarite("pine", "cone", dico, "dict_all_stopwords_stemming_semcor_dso_wordnetglosstag_150.xml");
		
		
		
		
		
	}
	
	public static void afficherNbMots(Dictionary d, String dico){
		d.loadDictionary(dico);
		System.out.println("Nombre de mot du dictionnaire "+dico+" : "+d.size());
	}
	
	public static void sensMot(String s, Dictionary dico){
		dico.loadDictionary("./Dict-Lesk-etendu.xml");
		afficherSens(dico, s);
		dico.loadDictionary("./Dict-Lesk.xml");
		afficherSens(dico, s);
		System.out.println();
	}
	
	public static void afficherSens(Dictionary dico, String mot){
		System.out.println("nombre de sens pour \""+mot+"\" : "+dico.getSenses(mot).size());
		for(Sense s:dico.getSenses(mot)){
			System.out.println("    "+s);
		}
	}
	
	public static void afficherSimilarite(String mot1, String mot2, Dictionary dico, String dictionnaire){
		dico.loadDictionary(dictionnaire);
		System.out.println("Similarité entre "+mot1+" et "+mot2+" sur le dictionnaire "+dictionnaire+" : "+dico.getSimilarity1(mot1, mot2));
	}
	
	public static void ex13(){
		double[] t1={1,4,8,12,2,5,4};
		double[] t2={4,2,1,6,6,10,4};
		System.out.println(Stats.getPearsonCorrelation(t1,t2));
	}
	
	public static void ex14() throws IOException{
		CSVReader reader = new CSVReader(new FileReader("paires.csv"));
		 String [] nextLine = reader.readNext();
		 ArrayList<String[]> list = new ArrayList<String[]>();
		 Dictionary dicoLesk = new Dictionary();
		 dicoLesk.loadDictionary("Dict-Lesk.xml");
		 Dictionary dicoLeskEtendu = new Dictionary();
		 dicoLeskEtendu.loadDictionary("Dict-Lesk-etendu.xml");
		 Dictionary dicoLeskCorpus = new Dictionary();
		 dicoLeskCorpus.loadDictionary("dict_all_stopwords_stemming_semcor_dso_wordnetglosstag_150.xml");
		 
		 
	     while ((nextLine = reader.readNext()) != null) {
	        // nextLine[] is an array of values from the line
	        list.add(nextLine);
	     }
		
		double[] humain = new double[353];
		double[] lesk = new double[353];
		double[] leskEtendu = new double[353];
		double[] leskCorpus = new double[353];
		int i = 0;
		for(String[] tab:list){
			lesk[i] = dicoLesk.getSimilarity1(tab[0], tab[1]);
			leskEtendu[i] = dicoLeskEtendu.getSimilarity1(tab[0], tab[1]);
			leskCorpus[i] = dicoLeskCorpus.getSimilarity1(tab[0], tab[1]);
			humain[i] = Double.parseDouble(list.get(i)[2]);
			++i;
		}
		
		System.out.println("Corrélation entre humain et lesk :"+Stats.getPearsonCorrelation(humain,lesk));
		System.out.println("Corrélation entre humain et lesk étendu :"+Stats.getPearsonCorrelation(humain,leskEtendu));
		System.out.println("Corrélation entre humain et lesk corpus :"+Stats.getPearsonCorrelation(humain,leskCorpus));
	}
	
	
	public static long nbCombinaisons(String S, Dictionary Dict){
		long res = 1;
		int nbSens;
		for(String s:S.split(" ")){
			nbSens = Dict.getSenses(s).size();
			if(nbSens>0){
				res = res * nbSens;
			}
		}
		return res;
	}
	
	public static void ex16(){
		String[] tabS={"go",
				"mouse pilot computer",
				"dog eat bone every day",
				"doctor be hospital last day night",
				"pictures paint be flat round figure be very often foot do look be stand ground all point downward be hanging air"};
		int i= 1;
		 for(Dictionary d : getDictionnaires()){
			 for(String s : tabS){
				 System.out.println(nbCombinaisons(s, d)+" combinaisons pour la chaine \""+s+"\" dans le "+i+"e dictionnaire");
			 }
			 
			 ++i;
		 }
		 
	}
	
	public static ArrayList<Dictionary> getDictionnaires(){
		ArrayList<Dictionary> res = new ArrayList<Dictionary>();
		 Dictionary dicoLesk = new Dictionary();
		 dicoLesk.loadDictionary("Dict-Lesk.xml");
		 res.add(dicoLesk);
		 Dictionary dicoLeskEtendu = new Dictionary();
		 dicoLeskEtendu.loadDictionary("Dict-Lesk-etendu.xml");
		 res.add(dicoLeskEtendu);
		 Dictionary dicoLeskCorpus = new Dictionary();
		 dicoLeskCorpus.loadDictionary("dict_all_stopwords_stemming_semcor_dso_wordnetglosstag_150.xml");
		 res.add(dicoLeskCorpus);
		 
		 return res;
	}
	
	public static void ex20() throws CloneNotSupportedException{
		
		ArrayList<Sense> sens =algoExhaustif("mouse pilot computer",getDictionnaires().get(0));
		System.out.println();
	}
	
	public static ArrayList<Sense> algoExhaustif(String S, Dictionary Dict) throws CloneNotSupportedException{
		String[] mots = S.split(" ");
		
		ArrayList<String> listMots = new ArrayList<String>();
		for(String m:mots) listMots.add(m);
		ArrayList<Combinaison> combinaisons= calculCombinaisons(listMots, Dict);
		Combinaison res = combinaisons.get(0);
		for(Combinaison c:combinaisons) {
			if(c.getSomme()>res.getSomme()) res =c;
		}
		return res.getSens();
	}
	
	public static ArrayList<Combinaison> calculCombinaisons(ArrayList<String> mots, Dictionary dico) throws CloneNotSupportedException{
		if(mots.size() >0){
			String mot = mots.get(0);
			mots.remove(0);
			ArrayList<Combinaison> res = calculCombinaisons(mots,dico);
			if(res.size()>0){
				ArrayList<Combinaison> resClone = cloneListCombinaison(res);
				ArrayList<Combinaison> newRes = new ArrayList<Combinaison>();
				System.out.println(dico.getSenses(mot).size());
				for(Sense s: dico.getSenses(mot)){
					for(Combinaison  combinaison: res){
						combinaison.add(s);
					}
					newRes.addAll(res);
					res = cloneListCombinaison(resClone);
				}
				return newRes;
			} else {
				for(Sense s:dico.getSenses(mot)){
					ArrayList<Sense> nouvCombin = new ArrayList<Sense>();
					nouvCombin.add(s);
					res.add(new Combinaison(nouvCombin, dico));
				}
				return res;
			}
			
		} else{
			return new ArrayList<Combinaison>();
		}
	}
	
	public static ArrayList<Combinaison> cloneListCombinaison(ArrayList<Combinaison> list) throws CloneNotSupportedException{
		ArrayList<Combinaison> res = new ArrayList<Combinaison>();
		for(Combinaison c:list)	res.add((Combinaison) c.clone());
		return res;
	}

}
