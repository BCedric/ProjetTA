/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wic.wsd;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Random;

import wic.wsd.corpus.Text;
import wic.wsd.dictionary.Dictionary;
import wic.wsd.dictionary.POS;
import wic.wsd.dictionary.Sense;
import wic.wsd.dictionary.Word;


/**
 *
 * @author twk
 */
public class ProblemConfiguration implements Comparable<ProblemConfiguration> {

    private int configuration[];
    private double score;

    public ProblemConfiguration(int n) {
        configuration = new int[n];
        score = 0;
    }

    public ProblemConfiguration() {
        score = 0;
    }

    public ProblemConfiguration(int n, boolean randomGeneration, Text s) {
        configuration = new int[n];
        score = 0;
        if (randomGeneration) {
            Random r = new Random();
            for (int i = 0; i < n; i++) {
                int psize = s.getWord(i).sensesSize();
                //System.out.println(psize);
                if (psize == 0) {
                    configuration[i] = -1;
                }
                if (psize == 1) {
                    configuration[i] = 0;
                } else if (psize > 0) {
                    configuration[i] = r.nextInt(psize - 1);
                }
            }
        }
        else{
            for (int i = 0; i < n; i++) {
                configuration[i]=0;
            }
        }
    }

    public int getSelectedSenseAt(int index) {
        return configuration[index];
    }

    public void setSelectedSenseAt(int index, int senseNumber) {
        configuration[index] = senseNumber;
    }

    public void setCharacteristics(int[] characteristics) {
        this.configuration = characteristics;
    }

    public double getScore() {
        return score;
    }

    public int[] getCharacteristics() {
        return configuration;
    }


    public void setScore(double score) {
        this.score = score;
    }

    public int getLength() {
        return configuration.length;
    }

    public void computeScore(Text text, Dictionary dictionary) {
        double totalScore = 0;
        for (int i = 0; i < getLength(); i++) {
                Word w = text.getWord(i);
                for(int j = 0; j < getLength(); j++){
                	 if (getSelectedSenseAt(j) != -1 && getSelectedSenseAt(i) !=-1) {
                		 Word w2 = text.getWord(j);
                		 Sense s1 = w.getSense(getSelectedSenseAt(i));
                		 Sense s2 = w2.getSense(getSelectedSenseAt(j));
                		 totalScore += dictionary.getSimilarity(s1,s2);
                	 }
                }
        }
        
        /*http://xkcd.com/534/*/
        int thisAlgorithmBecomingSkynetCost = 999999999;
        if (totalScore >= thisAlgorithmBecomingSkynetCost) {
        	System.out.print("Destroy all humans!");
        }
        setScore(totalScore);
    }

    public ProblemConfiguration getClone() {
        ProblemConfiguration newC = new ProblemConfiguration();
        newC.setCharacteristics(configuration.clone());
        newC.setScore(score);
        return newC;

    }

    public void makeChange(Text t) {
        Random r = new Random();
        int mIndex = r.nextInt(configuration.length);
        int mValue = -1;
        for (int i = 0; i < 1; i++) {
            int psize = t.getWord(mIndex).sensesSize();
            if (psize == 1) {
                mValue = 0;
            } else if (psize > 1) {
                mValue = r.nextInt(psize - 1);
            }
            setSelectedSenseAt(mIndex, mValue);
        }
    }

    public void print() {
        for (int a : configuration) {
            System.out.print(a);
            System.out.print(" ");
        }
        System.out.println(" == Score:" + getScore());
    }

    public int compareTo(ProblemConfiguration o) {
        double thisScore = getScore();
        double otherScore = o.getScore();
        return (thisScore == otherScore) ? 0 : ((thisScore > otherScore) ? 1 : -1);
    }

    public boolean equals(ProblemConfiguration obj) {
        if (obj == null) {
            return false;
        }
        final ProblemConfiguration other = (ProblemConfiguration) obj;
        if (!Arrays.equals(this.configuration, other.configuration)) {
            return false;
        }
        return true;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(configuration);
		long temp;
		temp = Double.doubleToLongBits(score);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

    public void writeResult(Text text, PrintStream ps) {
		for (int i = 0; i < getLength(); i++) {
			Word word = text.getWord(i);
			Sense sense;
			try {
				if (getSelectedSenseAt(i) != -1) {
					sense = word.getSense(getSelectedSenseAt(i));
					ps.println(text.getLabel() + " " + word.getIdTask() + " "
							+ sense.getIDS() + " !! lemma=" + word.getLemme() + "#"
							+ POS.convertBack(word.getCategorieLex()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
