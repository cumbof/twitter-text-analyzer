/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.dia.tta.action;

import java.util.ArrayList;

/**
 *
 * @author Fabio Cumbo, Alessandro Giacomini
 * @application TwitterTextAnalyzer
 * @version 1.0
 * @organization Università degli studi Roma Tre - Dipartimento di Informatica e Automazione
 * 
 */
public abstract class Action {
    
    public abstract ArrayList<Object> execute(String command);
    
    public abstract ArrayList<Object> execute();
    
}
