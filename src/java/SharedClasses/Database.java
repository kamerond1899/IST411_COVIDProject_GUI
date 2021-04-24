/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SharedClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RIVER
 */
public class Database {
    
    public Database() {
        
    }
    
    public Connection getConnection() {
        Connection con = null;
        
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (Exception ex) {
            System.out.println("Driver failed to connect");
        }
        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/COVID19Database", "final", "final");
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return con;
    }
    
    public void createStateTable() throws SQLException {
        
        Connection con = getConnection();
        Statement stmt = con.createStatement();
        
        stmt.execute("CREATE TABLE CasesAndDeaths" +
                         "(Date varchar(50)," +
                         "State varchar(50)," +
                         "Cases int," +
                         "Deaths int," +
                         "primary key (Date))");
                
        stmt.close();
        con.close();
    }
    
    public void createVaccineTable() throws SQLException {
        
        Connection con = getConnection();
        Statement stmt = con.createStatement();
        
        stmt.execute("CREATE TABLE VaccineInformation" +
                         "(Date varchar(50)," +
                         "State varchar(50)," +
                         "Total_Vaccines int," +
                         "Total_Distributed int," +
                         "People_Vaccinated int," +
                         "People_Fully_Vaccinated int," +
                         "Daily_Vaccinated int," +
                         "primary key (Date))");
                
        stmt.close();
        con.close();
    }
    
    public void insertStateValues(String date, String state, int cases, int deaths) throws SQLException {
        
        Connection con = getConnection();
        Statement stmt = con.createStatement();
        
        stmt.execute("INSERT INTO CasesAndDeaths(Date, State, Cases, Deaths) "
                   + "VALUES ('" + date + "'," + "'" + state + "'," + cases + deaths + ")");
        
        stmt.close();
        con.close();
    }
    
    public void insertVaccineValues(String date, String state, int totalVac, 
            int totalDis, int peopleVac, int peopleFullyVac, int dailyVac) throws SQLException {
        
        Connection con = getConnection();
        Statement stmt = con.createStatement();
        
        stmt.execute("INSERT INTO CasesAndDeaths(Date, State, Cases, Deaths) "
                   + "VALUES ('" + date + "'," + "'" + state + "'," + totalVac + 
                              totalDis + peopleVac + peopleFullyVac + dailyVac + ")");
        
        stmt.close();
        con.close();
    }
    
    public void deleteVaccineTable() throws SQLException {
        
        Connection con = getConnection();
        Statement stmt = con.createStatement();
        
        stmt.execute("DROP TABLE VaccineInformation");
        
        stmt.close();
        con.close();
    }
    
    public ArrayList<Dataset> selectData(String tableType, String month, String state) throws SQLException {
        
        Connection con = getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;
        
        ArrayList<Dataset> datasetArray = new ArrayList<>();
        if (tableType.equals("Monthly Cases and Deaths")){ //if State Table is used
            Dataset staterow = new Dataset("01-12-2021","Ohio",5555,3434);
            datasetArray.add(staterow);
        } else if (tableType == "Monthly Vaccine Information"){ //if Vaccine Table is used
            Dataset vacrow = new Dataset("01-12-2021","Ohio",100000,5000,2,342785569,27745);
            datasetArray.add(vacrow);
        } else if (tableType == "Monthly Trends"){ //if Trends Table is used
            Dataset trendsrow = new Dataset("Pennsylvania",3343,3434,53, 53, 34,43,46);
            datasetArray.add(trendsrow);
        }
        return datasetArray;
    }
}
