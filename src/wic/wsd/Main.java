package wic.wsd;

import java.io.FileNotFoundException;
import java.io.PrintStream;

import wic.wsd.corpus.Corpus;
import wic.wsd.corpus.Text;
import wic.wsd.dictionary.Dictionary;

public class Main {
	
	public static void main(String[] args) throws FileNotFoundException{
		
		/* Argument de ligne de commande:
		 * Main.class [dictionnaire] [corpus]
		 * Par exemple: java org.wic.wsd.Main dictionnaire.xml eng-coarse-all-words.xml sortie.ans*/
		
		/*Exemple d'utilisation*/
		Dictionary d = new Dictionary();
		d.loadDictionary(args[0]);
		
		Corpus c = new Corpus();
		c.loadCorpus(d, args[1]);
		
        PrintStream answerWriter = new PrintStream(args[2]);
        
		for(Text t : c.getTexts()){// On réalise les mêmes opérations sur chaque texte du corpus
			System.out.println("Texte "+t.getLabel());
			/*Configuration initiale: Selection de sens aléatoires*/
			ProblemConfiguration configuration = new ProblemConfiguration(t.getLength(), true, t);
			
			/*Faire quelque chose avec configuration*/
			
			/*Par exemple, pour calculer son score*/
			configuration.computeScore(t, d);
			double score = configuration.getScore();
			System.out.println("\tScore initial=         "+score);
			
			/*Faire un changement aléatoire, 
			 * on peut étendre/modifier la classe pour avoir un comportement
			 * plus spécifique*/
			configuration.makeChange(t);
			
			/*Score post changement*/
			configuration.computeScore(t, d);
			score = configuration.getScore();
			System.out.println("\tScore après changement="+score);
            configuration.writeResult(t, answerWriter);
        
        }
	}
    
    
}
