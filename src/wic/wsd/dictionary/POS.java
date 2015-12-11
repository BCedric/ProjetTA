package wic.wsd.dictionary;

public class POS {
    //Type de Mots

    public static final int NONE = -1;
    public static final int NOUN = 0;
    public static final int VERB = 1;
    public static final int ADJECTIVE_SATELLITE = 2;
    public static final int ADJECTIVE = 3;
    public static final int ADVERB = 4;

    public static int convert(String cat) {

        cat = cat.toUpperCase();
        if (cat.equals("NOUN")) {
            return NOUN;
        } else if (cat.equals("VERB")) {
            return VERB;
        } else if (cat.equals("ADJECTIVE_SATELLITE")) {
            return ADJECTIVE_SATELLITE;
        } else if (cat.equals("ADJECTIVE")) {
            return ADJECTIVE;
        } else if (cat.equals("ADVERB")) {
            return ADVERB;
        } else {
            return NONE;
        }
    }
    
    public static int convertShort(String cat) {

        cat = cat.toLowerCase();
        if (cat.equals("n")) {
            return NOUN;
        } else if (cat.equals("v")) {
            return VERB;
        } else if (cat.equals("a")) {
            return ADJECTIVE;
        } else if (cat.equals("r")) {
            return ADVERB;
        } else {
            return NONE;
        }
    }

    public static String convertBack(int cat) {
        String scat = "";

        switch(cat){
            case NOUN:scat = "n";break;
            case VERB:scat = "v";break;
            case ADJECTIVE_SATELLITE:scat = "a";break;
            case ADJECTIVE:scat = "a";break;
            case ADVERB:scat = "r";break;
            case NONE:break;
        }
        return scat;
    }
}
