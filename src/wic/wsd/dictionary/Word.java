package wic.wsd.dictionary;

import java.util.ArrayList;


public class Word {

	private String label;
	private String lemme;
	private String idTask;
	private int categorieLex;
	private ArrayList<Sense> senses;
	
	public Word(String lemme, int cat) {
		this.label = lemme;
		this.lemme = lemme;
		this.categorieLex = cat;
		this.idTask = "";
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String l) {
		this.label = l;
	}

	public void setLemme(String lemme) {
		this.lemme = lemme;
	}

	public String getLemme() {
		return lemme;
	}

	public void setIdTask(String idTask) {
		this.idTask = idTask;
	}

	public String getIdTask() {
		return idTask;
	}

	public void setCategorieLex(int categorieLex) {
		this.categorieLex = categorieLex;
	}

	public int getCategorieLex() {
		return categorieLex;
	}

	public int sensesSize() {
		return senses.size();
	}

	public Sense getSense(int i) {
		return senses.get(i);
	}

	public void createPos(Dictionary d) {

		if (this.categorieLex > POS.NONE) {
			senses = d.getSenses(this.lemme,
					this.categorieLex);
		}

	}
}
