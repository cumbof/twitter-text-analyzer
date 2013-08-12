/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.dia.tta.io;

import it.uniroma3.dia.tta.TwitterTextAnalyzer;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.ProgressMonitorInputStream;

/**
 *
 * @author Fabio Cumbo, Alessandro Giacomini
 * @application TwitterTextAnalyzer
 * @version 1.0
 * @organization Università degli studi Roma Tre - Dipartimento di Informatica e Automazione
 * 
 */
public class TermsLoader extends Thread {
    
    private File file;
    private static ArrayList<String> positiveTerms = null;
    private static ArrayList<String> negativeTerms = null;
    private static ArrayList<String> stopwords = null;
    private int _CURRENT_STATE_ = 0;
    
    /*
     *  STATE SUMMARY:
     *  NEGATIVE WORD = -1
     *  STORP WORD = 0
     *  POSITIVE WORD = 1
     * 
     */
    
    public TermsLoader(File file, int state) {
        super(file.getName());
        this.file = file;
        this._CURRENT_STATE_ = state;
    }
    
    @Override
    public void run() {
        try {
            JFrame frame = new JFrame();
            frame.setVisible(true);
            frame.setAlwaysOnTop(true);
            try (InputStream fstream = new FileInputStream(file.getAbsolutePath()); 
                    InputStream fstreamMonitor = new ProgressMonitorInputStream(frame, "Loading Terms...", fstream);
                    DataInputStream in = new DataInputStream(fstreamMonitor); 
                    BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
                frame.setVisible(false);
                
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TwittSetLoader.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                String strLine;
                try {
                    
                    while ((strLine = br.readLine()) != null) {
                        if (_CURRENT_STATE_ == 0) {
                            if (stopwords == null)
                                stopwords = new ArrayList<>();
                            stopwords.add(strLine.toUpperCase());
                        }
                        else if (_CURRENT_STATE_ == 1) {
                            if (positiveTerms == null)
                                positiveTerms = new ArrayList<>();
                            positiveTerms.add(strLine.toUpperCase());
                        }
                        else if (_CURRENT_STATE_ == -1) {
                            if (negativeTerms == null)
                                negativeTerms = new ArrayList<>();
                            negativeTerms.add(strLine.toUpperCase());
                        }
                    }
                    
                    if ((_CURRENT_STATE_ == 0) && (stopwords!=null)) {
                        if (TwittSetLoader.getTrainTwittList()!=null) {
                            
                            TwittSetLoader.tokenizeTwitts(true);
                            TwittSetLoader.posTagTwitts(true);
                            
                            TwitterTextAnalyzer.getApp().getPlotWordFrequenciesDistribution_button().setEnabled(true);
                            TwitterTextAnalyzer.getApp().getPlotTagsComparisonDistribution_button().setEnabled(true);
                            TwitterTextAnalyzer.getApp().getLoadTestDataset_button().setEnabled(true);                            
                        }
                    }
                    
                    br.close();
                    in.close();
                    fstreamMonitor.close();
                    fstream.close();
                }
                catch (NullPointerException e) {                                
                    br.close();
                    in.close();
                    fstreamMonitor.close();
                    fstream.close();
                }
            }
        } catch (IOException e) { }
    }
    
    public static ArrayList<String> getStopWords() {
        return stopwords;
    }
    
    public static ArrayList<String> getPositiveTerms() {
        return positiveTerms;
    }
    
    public static ArrayList<String> getNegativeTerms() {
        return negativeTerms;
    }
    
}
