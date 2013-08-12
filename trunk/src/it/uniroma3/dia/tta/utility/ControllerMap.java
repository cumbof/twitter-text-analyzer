/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.dia.tta.utility;

import it.uniroma3.dia.tta.TwitterTextAnalyzer;
import java.util.HashMap;

/**
 *
 * @author Fabio Cumbo, Alessandro Giacomini
 * @application TwitterTextAnalyzer
 * @version 1.0
 * @organization Università degli studi Roma Tre - Dipartimento di Informatica e Automazione
 * 
 */
public class ControllerMap {
    
    private HashMap<Object, String> map;
    
    public ControllerMap() {
        initMap();
    }
    
    private void initMap() {
        map = new HashMap<>();
        map.put(TwitterTextAnalyzer.getApp().getLoadStopWordsDataset_button(), "loadStopWordsDataset_button");
        map.put(TwitterTextAnalyzer.getApp().getLoadTestDataset_button(), "loadTestDataset_button");
        map.put(TwitterTextAnalyzer.getApp().getLoadTrainDataset_button(), "loadTrainDataset_button");
        map.put(TwitterTextAnalyzer.getApp().getPlotTagsComparisonDistribution_button(), "plotTagsComparisonDistribution_button");
        map.put(TwitterTextAnalyzer.getApp().getPlotWordFrequenciesDistribution_button(), "plotWordFrequenciesDistribution_button");
        map.put(TwitterTextAnalyzer.getApp().getRunNaiveBayesClassifier_button(), "runNaiveBayesClassifier_button");
    }
    
    public HashMap<Object, String> getMap() {
        return this.map;
    }
    
}
