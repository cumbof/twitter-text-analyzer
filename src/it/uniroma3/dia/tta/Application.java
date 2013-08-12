/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.dia.tta;

import java.awt.Frame;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JTextField;

/**
 *
 * @author Fabio Cumbo, Alessandro Giacomini
 * @application TwitterTextAnalyzer
 * @version 1.0
 * @organization Università degli studi Roma Tre - Dipartimento di Informatica e Automazione
 * 
 */
public interface Application {
    
    public Frame getFrame();
    
    public void setVisibleOverride(boolean visible);
    
    public void setDefaultCloseOperationOverride(int _DO_);
    
    public JButton getLoadStopWordsDataset_button();
    
    public JButton getLoadTestDataset_button();
    
    public JButton getLoadTrainDataset_button();
    
    public JButton getPlotTagsComparisonDistribution_button();
    
    public JButton getPlotWordFrequenciesDistribution_button();
    
    public JButton getRunNaiveBayesClassifier_button();
    
    public JTextField getStopWordsPath_text();
    
    public JTextField getTestPath_text();
    
    public JTextField getTrainPath_text();
    
    public JInternalFrame getTagsComparisonDistribution_frame();
    
    public JInternalFrame getWordFrequenciesDistribution_frame();
        
}
