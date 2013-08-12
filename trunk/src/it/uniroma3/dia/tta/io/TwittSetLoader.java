/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.dia.tta.io;

import it.uniroma3.dia.tta.TwitterTextAnalyzer;
import it.uniroma3.dia.tta.action.Action;
import it.uniroma3.dia.tta.action.PartOfSpeechTaggerAction;
import it.uniroma3.dia.tta.model.Twitt;
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
public class TwittSetLoader extends Thread {
    
    private File file;
    private static ArrayList<Twitt> trainTwittList = null;
    private static ArrayList<Twitt> testTwittList = null;
    private boolean _TRAIN_ = true;
    private final String regex = ";";
    
    public TwittSetLoader(File file, boolean train) {
        super(file.getName());
        this.file = file;
        this._TRAIN_ = train;
    }
    
    @Override
    public void run() {
        try {
            JFrame frame = new JFrame();
            frame.setVisible(true);
            frame.setAlwaysOnTop(true);
            try (InputStream fstream = new FileInputStream(file.getAbsolutePath()); 
                    InputStream fstreamMonitor = new ProgressMonitorInputStream(frame, "Loading Twitt Set...", fstream);
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
                        String[] splitLine = strLine.split(regex);
                        // CREATE NEW TWITT
                        if (splitLine.length >= 10) {
                            Twitt twitt = new Twitt(Integer.valueOf(splitLine[0]), recomposeText(splitLine, 10));
                            if (_TRAIN_) {
                                if (trainTwittList == null)
                                    trainTwittList = new ArrayList<>();
                                trainTwittList.add(twitt);
                            }
                            else if (!_TRAIN_) {
                                if (testTwittList == null)
                                    testTwittList = new ArrayList<>();
                                testTwittList.add(twitt);
                            }
                        }
                    }
                    
                    br.close();
                    in.close();
                    fstreamMonitor.close();
                    fstream.close();
                    
                    if (_TRAIN_ && (trainTwittList!=null)) {
                        if (TermsLoader.getStopWords()!=null) {
                            TwittSetLoader.tokenizeTwitts(true);
                            TwittSetLoader.posTagTwitts(true);
                            
                            TwitterTextAnalyzer.getApp().getPlotWordFrequenciesDistribution_button().setEnabled(true);
                            TwitterTextAnalyzer.getApp().getPlotTagsComparisonDistribution_button().setEnabled(true);
                            TwitterTextAnalyzer.getApp().getLoadTestDataset_button().setEnabled(true);
                            
                        }
                    }
                    else if ((!_TRAIN_) && (testTwittList!=null)) {
                        TwittSetLoader.tokenizeTwitts(false);
                        TwittSetLoader.posTagTwitts(false);
                        
                        TwitterTextAnalyzer.getApp().getRunNaiveBayesClassifier_button().setEnabled(true);
                    }
                    
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
    
    public static ArrayList<Twitt> getTrainTwittList() {
        return trainTwittList;
    }
    
    public static ArrayList<Twitt> getTestTwittList() {
        return testTwittList;
    }

    private String recomposeText(String[] splitLine, int indexToStart) {
        String text = "";
        for (int i=indexToStart; i<splitLine.length-1; i++)
            text+=splitLine[i]+regex;
        return (text+splitLine[splitLine.length-1]);
    }
    
    public static void tokenizeTwitts(boolean train) {
        if (train) {
            if (trainTwittList!=null) {
                for (Twitt t: trainTwittList) {
                    t.setTokenizedText(t.tokenizeText(t.getTwittText()));
                }
            }
        }
        else {
            if (testTwittList!=null) {
                for (Twitt t: testTwittList) {
                    t.setTokenizedText(t.tokenizeText(t.getTwittText()));
                }
            }
        }
    }
    
    public static void posTagTwitts(boolean train) {
        Action tagger = new PartOfSpeechTaggerAction();
        if (train) {
            if (trainTwittList!=null) {
                tagger.execute("TRAIN");
            }
        }
        else {
            if (testTwittList!=null) {
                tagger.execute("TEST");
            }
        }
    }
    
}