/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.dia.tta;

import it.uniroma3.dia.tta.frame.MainWindow;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 *
 * @author Fabio Cumbo, Alessandro Giacomini
 * @application TwitterTextAnalyzer
 * @version 1.0
 * @organization Università degli studi Roma Tre - Dipartimento di Informatica e Automazione
 * 
 */
public class TwitterTextAnalyzer {

    private static Application app;
    private static final String appAuthor = "Fabio Cumbo, Alessandro Giacomini";
    private static final String appName = "TwitterTextAnalyzer";
    private static final String appVersion = "v1.0";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        init();
    }
    
    private static void init() {
        final String lafWindows = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        
	java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    String operatingSystem;
                    operatingSystem = System.getProperty("os.name");
                    if (operatingSystem.startsWith("Windows")) {
                        UIManager.setLookAndFeel(lafWindows);
                    }
                    else {
                        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                    }
                    app = new MainWindow();
                    app.setVisibleOverride(true);
                    app.setDefaultCloseOperationOverride(JFrame.EXIT_ON_CLOSE);
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException e) {
                    java.util.logging.Logger.getLogger(TwitterTextAnalyzer.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
                }
            }
        });
        
    }
    
    public static Application getApp() {
        return app;
    }

    public static String getAppAuthor() {
        return appAuthor;
    }

    public static String getAppName() {
        return appName;
    }

    public static String getAppVersion() {
        return appVersion;
    }
    
}
