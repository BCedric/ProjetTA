package wic.wsd.dictionary;


import java.util.ArrayList;
import java.util.HashMap;

import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class Dictionary {

	private HashMap<String, ArrayList<Sense>> dico;

	public Dictionary() {
		this.dico = new HashMap<String, ArrayList<Sense>>();
	}
	
	public float getSimilarity1(String word1, String word2){
		int sum = 0;
		int n =0;
		for(Sense s1 : this.getSenses(word1)){
			for(Sense s2 : this.getSenses(word2)){
				sum+=this.getSimilarity(s1, s2);
				++n;
			}
		}
		if(n==0){
			System.out.println("le ou les mot(s) n'est (sont) pas dans le dictionnaire");
			return 0;
		}
		return (float)sum/n;
	}
	
	
	public void loadDictionary(String path){
		try {
			XMLReader saxReader = XMLReaderFactory.createXMLReader();
			saxReader.setContentHandler(new DictionaryParser(dico));
			saxReader.parse(path);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	// Acc???s direct aux Words (ajout??? par DS)
	public ArrayList<Sense> getSenses(String lemme) {

		// System.out.println(lemme);
		
		ArrayList<Sense> nouns = getSenses(lemme,POS.NOUN);
		ArrayList<Sense> adverbs = getSenses(lemme,POS.ADVERB);
		ArrayList<Sense> verbs = getSenses(lemme,POS.VERB);
		ArrayList<Sense> adjectives = getSenses(lemme,POS.ADJECTIVE);
		ArrayList<Sense> adjectives_sat = getSenses(lemme,POS.ADJECTIVE_SATELLITE);
		
		nouns.addAll(adverbs);
		nouns.addAll(verbs);
		nouns.addAll(adjectives);
		nouns.addAll(adjectives_sat);

		return nouns;
	}

	public ArrayList<Sense> getSenses(String lemme, int categorieLex) {
		ArrayList<Sense> r = new ArrayList<Sense>();
		String l = lemme.toLowerCase();
		char cat = ' ';
		String s = null;

		switch (categorieLex) {
		case POS.NOUN: {

			cat = 'n';
			break;
		}
		case POS.VERB: {
			cat = 'v';
			break;
		}
		case POS.ADJECTIVE: {
			cat = 'a';
			break;
		}
		case POS.ADJECTIVE_SATELLITE: {
			cat = 'a';
			break;
		}
		case POS.ADVERB: {
			cat = 'r';
			break;
		}
		}
		try {
			s = l + '%' + cat;
			if (this.dico.get(s) != null) {
				r = new ArrayList<Sense>(this.dico.get(s));

				for (int i = 0; i < r.size(); i++)
					r.get(i).setSenseID(l + '#' + cat + '#' + (i + 1));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println();
			//System.out.println("PROBL??ME AVEC " + s);
			System.out.println("dico null " + (this.dico == null));
			System.out.println(s + " dans dico " + (this.dico.get(s) != null));
			System.out.println();
		}
		return r;
	}
	
	
	public int getSimilarity(Sense s1, Sense  s2){
		Definition a = s1.getDef();
		Definition b = s2.getDef();
		int count = 0;
		int i = 0;
		int j = 0;
		int aSize = a.getSize();
		int bSize = b.getSize();
		while (i < aSize && j < bSize) {
			if (a.getElement(i) == b.getElement(j) && b.getElement(j)!=-1) {
				count++;
				i++;
				j++;
			} else if (a.getElement(i) < b.getElement(j)) {
				i++;
			} else {
				j++;
			}
		}
		return count;
	}
	
	public int getSimilarity(String word1, String word2){
		ArrayList<Sense> senses1 = getSenses(word1);
		ArrayList<Sense> senses2 = getSenses(word2);
		return getSimilarityFromSenseArray(senses1, senses2);
	}
	
	private int getSimilarityFromSenseArray(ArrayList<Sense> senses1, ArrayList<Sense> senses2){
		int count = 0;
		int similaritySum = 0;
		for(Sense s1:senses1){
			for(Sense s2:senses2){
				count++;
				similaritySum += getSimilarity(s1, s2);
			}
		}
		count = (count>0)?count:1;
		return similaritySum/count;
	}
	
	public int getSimilarity(String word1,int pos1, String word2, int pos2){
		ArrayList<Sense> senses1 = getSenses(word1,pos1);
		ArrayList<Sense> senses2 = getSenses(word2,pos2);
		return getSimilarityFromSenseArray(senses1, senses2);
		
	}

	public int size() {
		return this.dico.size();
	}

}
