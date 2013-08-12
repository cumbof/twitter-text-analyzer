/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.dia.tta.filter;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Fabio Cumbo, Alessandro Giacomini
 * @application TwitterTextAnalyzer
 * @version 1.0
 * @organization Università degli studi Roma Tre - Dipartimento di Informatica e Automazione
 * 
 */
public class TXTFilter extends FileFilter {
    
    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            // Directories must always be accepted, otherwise
            // you cannot navigate into the file-system!!!
            return true;
        }

        String name = f.getName().toLowerCase();

        return name.endsWith(".txt");
    }

    @Override
    public String getDescription() {
        return "TEXT (*.txt)";
    }
    
}