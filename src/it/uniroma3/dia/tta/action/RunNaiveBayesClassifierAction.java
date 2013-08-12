/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.dia.tta.action;

import it.uniroma3.dia.tta.classifier.NaiveBayesClassifier;
import it.uniroma3.dia.tta.frame.GenericProgressWindow;
import it.uniroma3.dia.tta.io.TwittSetLoader;
import it.uniroma3.dia.tta.model.Twitt;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
public class RunNaiveBayesClassifierAction extends Action implements PropertyChangeListener {

    private NaiveBayesClassifier task;
    private static GenericProgressWindow progressWindow;
    
    @Override
    public ArrayList<Object> execute(String command) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Object> execute() {
        progressWindow = new GenericProgressWindow(1);
        progressWindow.setVisible(true);
        HashMap<Integer, HashMap<String, Double>> trained_classes = getTrainedClasses(TwittSetLoader.getTrainTwittList());
        //task = new NaiveBayesClassifier(trained_classes, getTotalFeatureOccurrences(trained_classes));
        task = new NaiveBayesClassifier(trained_classes);
        task.setTwittsToClassify(TwittSetLoader.getTestTwittList());
        task.addPropertyChangeListener(this);
        task.execute();
        return null;
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress".equals(evt.getPropertyName())) {
            int progress = (Integer) evt.getNewValue();
            GenericProgressWindow.getProgressBar().setValue(progress);
        } 
    }
    
    public static GenericProgressWindow getProgressWindow() {
        return progressWindow;
    }

    private HashMap<Integer, HashMap<String, Double>> getTrainedClasses(ArrayList<Twitt> trainTwittList) {
        HashMap<Integer, HashMap<String, Double>> result = new HashMap<>();
        for (Twitt t: trainTwittList) {
            if (result.containsKey(t.getTwittState()))
                result.put(t.getTwittState(), mergePOSTag2Frequencies(result.get(t.getTwittState()), t.getPOSTag2Frequencies()));
            else
                result.put(t.getTwittState(), t.getPOSTag2Frequencies());
        }
        return result;
    }

    /*private HashMap<String, Double> getTotalFeatureOccurrences(HashMap<Integer, HashMap<String, Double>> trained_classes) {
        HashMap<String, Double> result = new HashMap<>();
        for (int key: trained_classes.keySet()) {
            result = mergePOSTag2Frequencies(result, trained_classes.get(key));
        }
        return result;
    }*/

    private HashMap<String, Double> mergePOSTag2Frequencies(HashMap<String, Double> set1, HashMap<String, Double> set2) {
        for (String s: set1.keySet()) {
            if (set2.containsKey(s))
                set2.put(s, set2.get(s)+set1.get(s));
            else
                set2.put(s, set1.get(s));
        }
        return set2;
    }
    
}
