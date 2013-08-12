/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.dia.tta.utility;

import it.uniroma3.dia.tta.action.PartOfSpeechTaggerAction;
import it.uniroma3.dia.tta.model.Twitt;
import java.awt.Toolkit;
import java.util.ArrayList;
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
public class PartOfSpeechTagger extends SwingWorker<Void, Void> {
    
    private ArrayList<Twitt> twitts;
    
    public PartOfSpeechTagger(ArrayList<Twitt> twittList) {
        this.twitts = twittList;
    }
        
    @Override
    public Void doInBackground() { 
        setProgress(0);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(PartOfSpeechTagger.class.getName()).log(Level.SEVERE, null, ex);
        }
        setProgress(0);
             
        int count = 1;
        for (Twitt t: twitts) {
            t.setPOSTag(t.posTag(t.getWords()));
            setProgress((count*100)/twitts.size());
            count++;
        }
        
        setProgress(100);
        return null;
    }
    
    /*
     * Executed in event dispatching thread
    */
    @Override
    public void done() {
        Toolkit.getDefaultToolkit().beep();
        PartOfSpeechTaggerAction.getProgressWindow().dispose();
    }
    
}