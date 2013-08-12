/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.dia.tta.utility;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import it.uniroma3.dia.tta.model.Twitt;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Fabio Cumbo, Alessandro Giacomini
 * @application TwitterTextAnalyzer
 * @version 1.0
 * @organization Università degli studi Roma Tre - Dipartimento di Informatica e Automazione
 * 
 */
public class POSTagUtils {
    
    private static MaxentTagger tagger = new MaxentTagger("taggers/english-left3words-distsim.tagger");
    private static HashMap<String, Double> positiveTwittPOSTag;
    private static HashMap<String, Double> negativeTwittPOSTag;
    
    public static MaxentTagger getTagger() {
        return tagger;
    }
    
    public static void computePOSTags(ArrayList<Twitt> twittList) {
        positiveTwittPOSTag = new HashMap<>();
        negativeTwittPOSTag = new HashMap<>();
        for (Twitt t: twittList) {
            if (t.getTwittState()==4) {
                for (String pos: t.getPOSTag2Frequencies().keySet()) {
                    if (positiveTwittPOSTag.containsKey(pos))
                        positiveTwittPOSTag.put(pos, positiveTwittPOSTag.get(pos)+1);
                    else
                        positiveTwittPOSTag.put(pos, 1.0);
                }
            }
            else if (t.getTwittState()==0) {
                for (String pos: t.getPOSTag2Frequencies().keySet()) {
                    if (negativeTwittPOSTag.containsKey(pos))
                        negativeTwittPOSTag.put(pos, ((Math.abs(negativeTwittPOSTag.get(pos))+1)*(-1)));
                    else
                        negativeTwittPOSTag.put(pos, -1.0);
                }
            }
        }
    }
    
    public static HashMap<String, Double> getPositiveTwittPOSTag() {
        return positiveTwittPOSTag;
    }
    
    public static HashMap<String, Double> getNegativeTwittPOSTag() {
        return negativeTwittPOSTag;
    }
    
}
