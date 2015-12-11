import java.util.ArrayList;

import wic.wsd.dictionary.Dictionary;
import wic.wsd.dictionary.Sense;

public class Combinaison {
	private float somme;
	private ArrayList<Sense> sens;
	private Dictionary dico;
	
	public Combinaison(ArrayList<Sense> sens, Dictionary dico){
		this.somme = 0;
		this.dico = dico;
		this.sens=sens;
		calculSomme();
		
		
	}

	public void calculSomme(){
		this.somme = 0;
		for(int i=0; i<sens.size(); ++i ){
			for(int j=i+1; j<sens.size();++j){
				somme+=dico.getSimilarity(sens.get(i), sens.get(j));
			}
		}
	}
	public float getSomme() {
		return somme;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		ArrayList<Sense> newSenses = (ArrayList<Sense>) sens.clone();
		return new Combinaison(newSenses, dico);
	}

	public ArrayList<Sense> getSens() {
		return sens;
	}

	public void add(Sense s) {
		this.sens.add(s);
		calculSomme();
	}

}
