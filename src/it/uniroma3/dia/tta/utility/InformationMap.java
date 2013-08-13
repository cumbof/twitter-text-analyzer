/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.dia.tta.utility;

import java.util.HashMap;

/**
 *
 * @author Fabio Cumbo, Alessandro Giacomini
 * @application TwitterTextAnalyzer
 * @version 1.0
 * @organization Università degli studi Roma Tre - Dipartimento di Informatica e Automazione
 * 
 */
public class InformationMap {
    
    private static HashMap<String, String> tag2text;
    
    public InformationMap() {
        initMap();
    }
    
    private void initMap() {
        tag2text = new HashMap<>();
        tag2text.put("_TRAIN_", "TRAIN DATASET INFORMATION:\n\n"
                + "This dataset contains a list of twitts in a simple\n"
                + "CSV format. Each row corresponds to a twitt.\n"
                + "The main fields of this file are the first and the\n"
                + "eleventh column that represent the class of the twitt\n"
                + "and the twitt text respectively.\n\n"
                + "Below is represented an example of train dataset.\n\n\n"
                + "4;;4;;03:18:03;;kindle2;;vcu451;;Reading my kindle2... Love it!\n"
                + "4;;11;;03:27:15;;twitter;;PJ_King;;Loves twitter\n"
                + "0;;141;;17:32:36;;aig;;Trazor1;;ShaunWoo hate'n on AiG\n"
                + "...\n\n\n"
                + "If you have already loaded a stopwords list,\n"
                + "after loading train dataset, this automatically starts\n"
                + "the tokenization process including the part-of-speech\n"
                + "tagging operation.\n");
        tag2text.put("_STOPWORDS_", "STOPWORDS DATASET INFORMATION:\n\n"
                + "The StopWords dataset contains a list of words\n"
                + "that are not relevant to classify twitts.\n"
                + "These words are removed from each twitt during\n"
                + "the tokenization step.\n\n"
                + "Below is represented an example of stopwords dataset.\n\n\n"
                + "a\n"
                + "about\n"
                + "above\n"
                + "after\n"
                + "again\n"
                + "all\n"
                + "am\n"
                + "an\n"
                + "...\n\n\n"
                + "If you have already loaded a train dataset,\n"
                + "after loading a stopwords list, this automatically starts\n"
                + "the tokenization process including the part-of-speech\n"
                + "tagging operation.\n");
        tag2text.put("_TEST_", "TEST DATASET INFORMATION:\n\n"
                + "The test dataset has the same format of the train dataset.\n"
                + "It contains twitts that you want try to classify with\n"
                + "the available classifier.\n\n"
                + "For more information about the format of the dataset,\n"
                + "please click on the train dataset information button.\n");
    }
    
    public String getInformationAbout(String tag) {
        if (tag2text.containsKey(tag))
            return tag2text.get(tag);
        return "What are you asking for?";
    }
    
}
