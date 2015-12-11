package wic.wsd.corpus;

import java.util.ArrayList;

import wic.wsd.dictionary.Dictionary;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;


public class Corpus {

    private ArrayList<Text> texts;
    private static Corpus instance;

     public Corpus(){
    	 texts = new ArrayList<Text>();
     }

    public static Corpus getInstance(){
        return instance;
    }

    public ArrayList<Text> getTexts() {
        return texts;
    }

    public void setTexts(ArrayList<Text> texts) {
        this.texts = texts;
    }

    public void loadCorpus(Dictionary d, String path){
    	try {
			XMLReader saxReader = XMLReaderFactory.createXMLReader();
			saxReader
					.setContentHandler(new CorpusParser(this,d));
			saxReader.parse(path);
		} catch (Throwable t) {
			t.printStackTrace();
		}
    }


    
}
