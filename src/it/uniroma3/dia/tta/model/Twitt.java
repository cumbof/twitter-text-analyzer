/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.dia.tta.model;

import it.uniroma3.dia.tta.io.TermsLoader;
import it.uniroma3.dia.tta.utility.POSTagUtils;
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
public class Twitt {
    
    private int twittState;  // 4=positive  -  0=negative
    private String twittText;
    private ArrayList<String> words;
    private HashMap<String, Double> posTag2frequencies;
    private boolean tokenized = false;
    private int classifiedAs = -1;
    
    public Twitt(int state, String text) {
        this.twittState = state;
        this.twittText = text;
        this.words = tokenizeText(text);
    }
    
    public int getTwittState() {
        return twittState;
    }
    
    public String getTwittText() {
        return this.twittText;
    }
    
    public HashMap<String, Double> getPOSTag2Frequencies() {
        return this.posTag2frequencies;
    }
    
    public ArrayList<String> getWords() {
        return this.words;
    }

    public ArrayList<String> tokenizeText(String text) {
        if (TermsLoader.getStopWords()!=null) {
            ArrayList<String> result = new ArrayList<>();
            String[] textSplit = text.split(" ");
            for (String w: textSplit) {
                if (!w.equals("")) {
                    // EXCLUDE @ TO REMOVE TAGS, # FOR FOLLOWERS AND http FOR LINKS
                    if (!TermsLoader.getStopWords().contains(w.toUpperCase())&& !w.contains("@") && !w.contains("#") && !w.contains("http")) {
                        w = removeNonAlphanumericCharacters(w.toUpperCase());
                        if (!w.equals("") && w.length()>2) {
                            if (!TermsLoader.getStopWords().contains(w.toUpperCase())) 
                                result.add(w.toUpperCase());
                        }
                    }
                }
            }
            tokenized = true;
            return result;
        }
        return null;
    }
    
    public HashMap<String, Double> posTag(ArrayList<String> wordSet) {
        if (tokenized && wordSet!=null) {
            HashMap<String, Double> tag2freq = new HashMap<>();
            // Initialize the tagger
            String newText = "";
            for (String word: wordSet) {
                newText += word + " ";
                
            }
            // The tagged string
            String tagged = POSTagUtils.getTagger().tagString(newText);
            String[] newWords = tagged.split(" ");
            if (!tagged.equals("")) {
                for (String word: newWords) {
                    String[] word2tag = word.split("_");
                    if (tag2freq.containsKey(word2tag[1]))
                        tag2freq.put(word2tag[1], tag2freq.get(word2tag[1])+1.0);
                    else
                        tag2freq.put(word2tag[1], 1.0);
                }
            }
            return tag2freq;
        }
        return null;
    }
    
    public void setTokenizedText(ArrayList<String> tknTxt) {
        this.words = tknTxt;
    }
    
    public void setPOSTag(HashMap<String, Double> tagSet) {
        this.posTag2frequencies = tagSet;
    }
    
    private String removeNonAlphanumericCharacters(String text) {
        return text.replaceAll("[^A-Za-z0-9]", "");
    }
    
    public void classifiedAs(int classId) {
        this.classifiedAs = classId;
    }
    
    public boolean isCorrectlyClassified() {
        if (this.twittState == this.classifiedAs)
            return true;
        return false;
    }
    
}
