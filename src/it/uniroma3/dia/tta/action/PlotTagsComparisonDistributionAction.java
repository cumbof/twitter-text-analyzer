/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.dia.tta.action;

import it.uniroma3.dia.tta.io.TwittSetLoader;
import it.uniroma3.dia.tta.utility.POSTagUtils;
import it.uniroma3.dia.tta.utility.PlotUtils;
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
public class PlotTagsComparisonDistributionAction extends Action {

    @Override
    public ArrayList<Object> execute(String command) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Object> execute() {
        if (TwittSetLoader.getTrainTwittList() != null) {
            POSTagUtils.computePOSTags(TwittSetLoader.getTrainTwittList());
            HashMap<String, Double> positiveTwittPOSTag = POSTagUtils.getPositiveTwittPOSTag();
            HashMap<String, Double> negativeTwittPOSTag = POSTagUtils.getNegativeTwittPOSTag();
            Object[] arr = new Object[2];
            arr[0] = positiveTwittPOSTag;
            arr[1] = negativeTwittPOSTag;
            PlotUtils utils = new PlotUtils();
            utils.plotDistribution(null, null, null, 1, arr);
        }
        return null;
    }
    
}
