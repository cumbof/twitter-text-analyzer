/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.dia.tta.action;

import it.uniroma3.dia.tta.TwitterTextAnalyzer;
import it.uniroma3.dia.tta.filter.TXTFilter;
import it.uniroma3.dia.tta.io.TwittSetLoader;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Fabio Cumbo, Alessandro Giacomini
 * @application TwitterTextAnalyzer
 * @version 1.0
 * @organization Università degli studi Roma Tre - Dipartimento di Informatica e Automazione
 * 
 */
public class LoadTestDatasetAction extends Action {

    @Override
    public ArrayList<Object> execute(String command) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Object> execute() {
        JFileChooser chooser = new JFileChooser();
        
        FileFilter txtFilter = new TXTFilter();
	chooser.addChoosableFileFilter(txtFilter);
	chooser.setFileFilter(txtFilter);
        
        int code = chooser.showOpenDialog(null);
        File file;
        if (code == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
            TwitterTextAnalyzer.getApp().getTestPath_text().setText(file.getName());
            TwittSetLoader loader = new TwittSetLoader(file, false);
            loader.start();
        }
        return null;
    }
    
}
