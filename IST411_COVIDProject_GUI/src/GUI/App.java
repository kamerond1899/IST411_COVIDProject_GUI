/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import SharedClasses.CSVParse;
import java.net.MalformedURLException;
import java.sql.SQLException;

/**  
 * IST 411-001 - Final Project
 * App.java  
 * Purpose: Runs (holds the main method) the GUI (DataFrame) 
 * and API Call/Database storage (CSVParse).
 *  
 * @author (Lead) River Martinez & Kameron Dangleben 
 * @version 1.0 5/4/2021
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MalformedURLException, SQLException {
        CSVParse parseDataIntoDatabase = new CSVParse();
        DataFrame dataFrame = new DataFrame();
    }
    
}
