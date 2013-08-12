/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.dia.tta;

import it.uniroma3.dia.tta.action.Action;
import it.uniroma3.dia.tta.utility.ControllerMap;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fabio Cumbo, Alessandro Giacomini
 * @application TwitterTextAnalyzer
 * @version 1.0
 * @organization Università degli studi Roma Tre - Dipartimento di Informatica e Automazione
 * 
 */
public class Controller implements ActionListener {
    
    private static HashMap<String, String> command2action;
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            initMap();
            ControllerMap map = new ControllerMap();
            String commandKey = map.getMap().get(e.getSource());
            String actionClass = command2action.get(commandKey);
            Action action = (Action)Class.forName(actionClass).newInstance();
            action.execute();
            System.out.println(actionClass);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e1);
        }
    }
    
    private void initMap() {
        command2action = new HashMap<>();
        command2action.put("loadStopWordsDataset_button", "it.uniroma3.dia.tta.action.LoadStopWordsDatasetAction");
        command2action.put("loadTestDataset_button", "it.uniroma3.dia.tta.action.LoadTestDatasetAction");
        command2action.put("loadTrainDataset_button", "it.uniroma3.dia.tta.action.LoadTrainDatasetAction");
        command2action.put("plotTagsComparisonDistribution_button", "it.uniroma3.dia.tta.action.PlotTagsComparisonDistributionAction");
        command2action.put("plotWordFrequenciesDistribution_button", "it.uniroma3.dia.tta.action.PlotWordFrequenciesDistributionAction");
        command2action.put("runNaiveBayesClassifier_button", "it.uniroma3.dia.tta.action.RunNaiveBayesClassifierAction");
    }
    
}