/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.dia.tta.classifier;

import it.uniroma3.dia.tta.action.RunNaiveBayesClassifierAction;
import it.uniroma3.dia.tta.frame.PostClassificationResultsWindow;
import it.uniroma3.dia.tta.model.Twitt;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;

/**
 *
 * @author Fabio Cumbo, Alessandro Giacomini
 * @application TwitterTextAnalyzer
 * @version 1.0
 * @organization Università degli studi Roma Tre - Dipartimento di Informatica e Automazione
 * 
 */
public class NaiveBayesClassifier extends SwingWorker<Void, Void> {
    
    private HashMap<Integer, HashMap<String, Double>> trained_classes;
    //private HashMap<String, Double> total_feature_occurences;
    private ArrayList<Twitt> test_set;
    
    /*public NaiveBayesClassifier(HashMap<Integer, HashMap<String, Double>> trained_classes, HashMap<String, Double> total_feature_occurences) {
        this.trained_classes = trained_classes;
        this.total_feature_occurences = total_feature_occurences;
    }*/
    
    public NaiveBayesClassifier(HashMap<Integer, HashMap<String, Double>> trained_classes) {
        this.trained_classes = trained_classes;
    }
    
    public void setTwittsToClassify(ArrayList<Twitt> twitts) {
        this.test_set = twitts;
    }
    
    @Override
    public Void doInBackground() { 
        setProgress(0);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(NaiveBayesClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
        setProgress(0);
              
        // NAIVE-BAYES CLASSIFIER THREAD
        if (test_set != null) {
            
            // P(p), P(n)
            HashMap<Integer, Double> classProbabilities = new HashMap<>();
            for (int c: trained_classes.keySet())
                classProbabilities.put(c, (classProbability(c)));
            
            // P(X|p), P(X|n)
            int count = 1;
            for (Twitt t: test_set) {
                
                HashMap<Integer, Double> result = new HashMap<>();
                for (String pos: t.getPOSTag2Frequencies().keySet()) {
                    for (int class_name: trained_classes.keySet()) {
                        /*if (result.containsKey(class_name))
                            result.put(class_name, result.get(class_name) + ((t.getPOSTag2Frequencies().get(pos) * Math.log(probability(pos, class_name)))));
                        else
                            result.put(class_name, ((t.getPOSTag2Frequencies().get(pos) * Math.log(probability(pos, class_name)))));
                        */
                        if (result.containsKey(class_name))
                            result.put(class_name, result.get(class_name) * (((probability(pos, class_name)))));
                        else
                            result.put(class_name, (((probability(pos, class_name)))));
                    }
                }
                
                for (int c: result.keySet())
                    result.put(c, result.get(c)*classProbabilities.get(c));
                
                t.classifiedAs(getKeyWithMaxValue(result));  
                
                setProgress((count*100)/test_set.size());
                count++;
            }
        }
        else
            System.err.println("You have to load twitts to classify...");
        
        setProgress(100);
        return null;
    }
    
    /*
     * Executed in event dispatching thread
    */
    @Override
    public void done() {
        Toolkit.getDefaultToolkit().beep();
        RunNaiveBayesClassifierAction.getProgressWindow().dispose();
        HashMap<String, Double> stats = printStatistics(test_set);
        PostClassificationResultsWindow results = new PostClassificationResultsWindow();
        
        results.setClassifierName("Naive-Bayes Classifier");
        results.setTotalPositives(String.valueOf(stats.get("_TOTAL_POS_")));
        results.setRightPositives(String.valueOf(stats.get("_RIGHT_POS_")));
        results.setWrongPositives(String.valueOf(stats.get("_WRONG_POS_")));
        results.setPositiveSuccessRate(String.valueOf(stats.get("_SUCC_RATE_POS_")));
        results.setTotalNegatives(String.valueOf(stats.get("_TOTAL_NEG_")));
        results.setRightNegatives(String.valueOf(stats.get("_RIGHT_NEG_")));
        results.setWrongNegatives(String.valueOf(stats.get("_WRONG_NEG_")));
        results.setNegativeSuccessRate(String.valueOf(stats.get("_SUCC_RATE_NEG_")));
        
        results.setVisible(true);
    }
    
    private double probability(String feature_name, int class_name) {
        double numerator = trained_classes.get(class_name).get(feature_name);
        //double denominator = total_feature_occurences.get(feature_name);
        double denominator = 0;
        for (String pos: trained_classes.get(class_name).keySet())
            denominator += trained_classes.get(class_name).get(pos);
        return ((numerator / denominator));
    }
    
    private double classProbability(int class_name) {
        double numerator = trained_classes.get(class_name).size();
        double denominator = 0.0;
        for (int c: trained_classes.keySet())
            denominator += trained_classes.get(c).size();
        return ((numerator / denominator));
    }

    private int getKeyWithMaxValue(HashMap<Integer, Double> result) {
        int k = (new ArrayList<>(result.keySet())).get(0);
        double v = result.get(0);
        for (Integer i: result.keySet()) {
            if (result.get(i) > v) {
                k = i;
                v = result.get(i);
            }
        }
        return k;
    }

    private HashMap<String, Double> printStatistics(ArrayList<Twitt> test_set) {
        HashMap<String, Double> result = new HashMap<>();
        double totalPositiveTwitt = 0;
        double rightPositiveTwitt = 0;
        double wrongPositiveTwitt = 0;
        double totalNegativeTwitt = 0;
        double rightNegativeTwitt = 0;
        double wrongNegativeTwitt = 0;
        
        for (Twitt t: test_set) {
            if (t.getTwittState()==4) {
                if (t.isCorrectlyClassified())
                    rightPositiveTwitt++;
                else
                    wrongPositiveTwitt++;
                totalPositiveTwitt++;
            }
            else if (t.getTwittState()==0) {
                if (t.isCorrectlyClassified())
                    rightNegativeTwitt++;
                else
                    wrongNegativeTwitt++;
                totalNegativeTwitt++;
            }
        }
        
        double positive_success_rate = (((double)((int)((rightPositiveTwitt/totalPositiveTwitt)*1000)))/1000);
        double negative_success_rate = (((double)((int)((rightNegativeTwitt/totalNegativeTwitt)*1000)))/1000);
        
        result.put("_TOTAL_POS_", totalPositiveTwitt);
        result.put("_RIGHT_POS_", rightPositiveTwitt);
        result.put("_WRONG_POS_", wrongPositiveTwitt);
        result.put("_SUCC_RATE_POS_", positive_success_rate);
        result.put("_TOTAL_NEG_", totalNegativeTwitt);
        result.put("_RIGHT_NEG_", rightNegativeTwitt);
        result.put("_WRONG_NEG_", wrongNegativeTwitt);
        result.put("_SUCC_RATE_NEG_", negative_success_rate);
        
        /*
        System.out.println("----------------------------------------------------------------\n"+
                            "Class   | Total      | Right      | Wrong      | Success rate\n"+
                            "----------------------------------------------------------------\n"+
                            "positive   | "+totalPositiveTwitt+"        | "+rightPositiveTwitt+"        | "+wrongPositiveTwitt+"          | "+positive_success_rate+"\n"+
                            "negative   | "+totalNegativeTwitt+"        | "+rightNegativeTwitt+"        | "+wrongNegativeTwitt+"          | "+negative_success_rate+"\n"+
                            "----------------------------------------------------------------");
        */
        
        return result;
    }
    
}
