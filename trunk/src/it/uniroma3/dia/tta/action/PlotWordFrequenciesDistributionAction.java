/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.dia.tta.action;

import it.uniroma3.dia.tta.io.TwittSetLoader;
import it.uniroma3.dia.tta.model.Twitt;
import it.uniroma3.dia.tta.utility.PlotUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 *
 * @author Fabio Cumbo, Alessandro Giacomini
 * @application TwitterTextAnalyzer
 * @version 1.0
 * @organization Università degli studi Roma Tre - Dipartimento di Informatica e Automazione
 * 
 */
public class PlotWordFrequenciesDistributionAction extends Action {

    @Override
    public ArrayList<Object> execute(String command) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Object> execute() {
        if (TwittSetLoader.getTrainTwittList() != null) {
            PlotUtils utils = new PlotUtils();
            utils.plotDistribution(null, null, null, 0, getWordFrequenciesInformation(TwittSetLoader.getTrainTwittList()));
        }
        return null;
    }
    
    private ArrayList<Double> getWordFrequenciesInformation(ArrayList<Twitt> twittList) {
        HashMap<String, Double> words2frequencies = new HashMap<>();
        for (Twitt t: twittList) {
            ArrayList<String> words = t.getWords();
            for (String w: words) {
                if (words2frequencies.containsKey(w))
                    words2frequencies.put(w, words2frequencies.get(w)+1);
                else
                    words2frequencies.put(w, 1.0);
            }
        }
        ArrayList<Double> frequencies = new ArrayList<>(words2frequencies.values());
        Collections.sort(frequencies);
        Collections.reverse(frequencies);
        return frequencies;
    }
    
}
