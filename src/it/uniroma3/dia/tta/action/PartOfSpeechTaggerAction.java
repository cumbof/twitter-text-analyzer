/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.dia.tta.action;

import it.uniroma3.dia.tta.frame.GenericProgressWindow;
import it.uniroma3.dia.tta.io.TwittSetLoader;
import it.uniroma3.dia.tta.utility.PartOfSpeechTagger;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

/**
 *
 * @author Fabio Cumbo, Alessandro Giacomini
 * @application TwitterTextAnalyzer
 * @version 1.0
 * @organization Università degli studi Roma Tre - Dipartimento di Informatica e Automazione
 * 
 */
public class PartOfSpeechTaggerAction extends Action implements PropertyChangeListener {

    private PartOfSpeechTagger task;
    private static GenericProgressWindow progressWindow;
    
    @Override
    public ArrayList<Object> execute(String command) {
        progressWindow = new GenericProgressWindow(0);
        progressWindow.setVisible(true);
        if (command.equals("TRAIN"))
            task = new PartOfSpeechTagger(TwittSetLoader.getTrainTwittList());
        else if (command.equals("TEST"))
            task = new PartOfSpeechTagger(TwittSetLoader.getTestTwittList());
        task.addPropertyChangeListener(this);
        task.execute();
        return null;
    }

    @Override
    public ArrayList<Object> execute() {
        throw new UnsupportedOperationException("Not supported yet.");
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
    
}
