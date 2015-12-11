package wic.wsd.corpus;

import java.util.ArrayList;

import wic.wsd.dictionary.Word;
public class Text {

    private String label;
    private ArrayList<Word> words;

    public Text(String l) {
        this.label = l;
        this.words = new ArrayList<Word>();
    }

    public String getLabel() {
        return this.label;
    }

    public void addWord(Word w) {
        this.words.add(w);
    }

    public int indexOfWord(Word w){
        return words.indexOf(w);
    }

    public Word getWord(int index){
        return words.get(index);
    }

    public int getLength(){
        return words.size();
    }
}
