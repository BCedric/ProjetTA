import java.awt.List;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.TreeSet;

import com.opencsv.CSVReader;

import wic.wsd.ProblemConfiguration;
import wic.wsd.Stats;
import wic.wsd.corpus.Corpus;
import wic.wsd.corpus.Text;
import wic.wsd.dictionary.*;

public class Main {

	public static void main(String[] args) {
		Dictionary dico = new Dictionary();
//		try {
//			ex20();
//		} catch (CloneNotSupportedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		afficherNbMots(dico, "./Dict-Lesk-etendu.xml");
//		afficherNbMots(dico, "./Dict-Lesk.xml");
//		sensMot("pine", dico);
//		sensMot("cone", dico);
//		afficherSimilarite("pine", "cone", dico, "Dict-Lesk.xml");
//		afficherSimilarite("pine", "cone", dico, "Dict-Lesk-etendu.xml");
//		afficherSimilarite("pine", "cone", dico, "dict_all_stopwords_stemming_semcor_dso_wordnetglosstag_150.xml");
		
		
		long startTime = System.currentTimeMillis();
		
		Corpus c =new Corpus();
		Dictionary d = new Dictionary();
		d.loadDictionary("Dict-Lesk.xml");
		c.loadCorpus(d, "TP-WSD-WIK/evaluation/test/eng-coarse-all-words.xml");
		ex26(1000, 100 ,4, c.getTexts().get(0), d);
		long endTime = System.currentTimeMillis();
		System.out.println("Temps d'exécution :"+ (endTime-startTime));
		
		
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
		
		ArrayList<Sense> sens =algoExhaustif("doctor be hospital last day night",getDictionnaires().get(2));
		for(Sense s:sens){
			System.out.println(s.getIDS());
		}
		
		
	}
	
	public static ArrayList<Sense> algoExhaustif(String S, Dictionary Dict) throws CloneNotSupportedException{
		System.out.println("Début algo exhaustif");
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
				System.out.println(dico.getSenses(mot).size());
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
	
	public static void ex23(Corpus c, int n, Dictionary d) throws FileNotFoundException{
		/* Argument de ligne de commande:
		 * Main.class [dictionnaire] [corpus]
		 * Par exemple: java org.wic.wsd.Main dictionnaire.xml eng-coarse-all-words.xml sortie.ans*/
		
        
		for(Text t : c.getTexts()){// On rÃ©alise les mÃªmes opÃ©rations sur chaque texte du corpus
			System.out.println("Texte "+t.getLabel());
			/*Configuration initiale: Selection de sens alÃ©atoires*/
			ProblemConfiguration configuration = new ProblemConfiguration(t.getLength(), true, t);
			
			/*Faire quelque chose avec configuration*/
			
			/*Par exemple, pour calculer son score*/
			configuration.computeScore(t, d);
			double score = configuration.getScore();
			System.out.println("\tScore initial=         "+score);
			ProblemConfiguration configClone;
			
				for(int i=0;i<n;++i){
					configClone = configuration.getClone();
					configClone.makeChange(t);
					configClone.computeScore(t, d);
					if(configClone.getScore() > configuration.getScore()){
						configuration = configClone;
					}
				}
			
			
			
			/*Score post changement*/
			System.out.println("\tScore aprÃ¨s changement="+configuration.getScore());
	        //configuration.writeResult(t, answerWriter);
		}
	}
	
	public static void ex26(int n, int m, int s, Text t, Dictionary d){
		int i, j, s1 = s;
		double[][] tab = new double[t.getLength()][];
		ArrayList<ProblemConfiguration> configurations = new ArrayList<ProblemConfiguration>();
		ProblemConfiguration config;
		
		//initialisation des différentes configurations
		for(i=0; i<n;++i){
			config = new ProblemConfiguration(t.getLength(), true, t);
			config.computeScore(t, d);
			configurations.add(config);
		}
		Collections.sort(configurations);
		for(ProblemConfiguration configuration : configurations){
			System.out.println(configuration.getScore());
		}
		
		
		//initialisation du tableau de probabilités
		for(i=0; i<t.getLength();++i){
			tab[i] = new double[d.getSenses(t.getWord(i).getLabel()).size()];
			for(j=0; j<tab[i].length; ++j) tab[i][j]=0;
			}
		
		
		config = configurations.get(configurations.size()-1);
		System.out.println("Score initiale : "+config.getScore());
		while(s1!=0){
			tab = calculProba(configurations, m, tab);
			configurations = nouvellePop(configurations, tab, t,d);
			Collections.sort(configurations);
			
			
			if(configurations.get(configurations.size()-1).compareTo(config)==0) s1--;
			else s1 =s;
			config = configurations.get(configurations.size()-1).getClone();
			System.out.println("Nouveau score : "+config.getScore());
		}
		System.out.println("Score final : "+config.getScore());
	}
	
	public static ArrayList<ProblemConfiguration> nouvellePop(ArrayList<ProblemConfiguration> configurations,  double[][] prob, Text t, Dictionary d){
		int j;
		double rand;
		for(ProblemConfiguration configuration : configurations){
			for(int i=0;i<prob.length; ++i){
				rand = Math.random();
				j=0;
				while(rand>prob[i][j]){
					++j;
				}
				
				configuration.setSelectedSenseAt(i, j);
				//System.out.println(configuration.getSelectedSenseAt(i));
			}
			configuration.computeScore(t, d);
		}
		
		return configurations;
	}
	
	public static double[][] calculProba(ArrayList<ProblemConfiguration> configurations, int m, double[][] tab){
		int j, i=0;
		for(ProblemConfiguration configuration : configurations){
			if(i>=configurations.size()-m){
				for(j=0;j<configuration.getLength();++j){
					tab[j][configuration.getSelectedSenseAt(j)]++;
				}
			}
			++i;
		}
		for(i=0; i<tab.length; ++i){
			for(j=0;j<tab[i].length;++j){
				if(j==0) tab[i][j] = tab[i][j]/m;
				else tab[i][j] = (tab[i][j-1] + tab[i][j]/m);
				
			}
		}
		
		return tab;
	}
	

}
