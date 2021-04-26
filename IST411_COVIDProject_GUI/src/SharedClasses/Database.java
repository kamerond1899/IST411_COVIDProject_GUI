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
                         "Deaths int)");
                
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
                         "Daily_Vaccinated int)");
                
        stmt.close();
        con.close();
    }
    
    public void insertStateValues(String date, String state, int cases, int deaths) throws SQLException {
        
        Connection con = getConnection();
        Statement stmt = con.createStatement();
        
        stmt.execute("INSERT INTO CasesAndDeaths(Date, State, Cases, Deaths) "
                   + "VALUES ('" + date + "'," + "'" + state + "'," + cases + "," + deaths + ")");
        
        stmt.close();
        con.close();
    }
    
    public void insertVaccineValues(String date, String state, int totalVac, 
            int totalDis, int peopleVac, int peopleFullyVac, int dailyVac) throws SQLException {
        
        Connection con = getConnection();
        Statement stmt = con.createStatement();
        
        stmt.execute("INSERT INTO VaccineInformation(Date, State, Total_Vaccines, Total_Distributed,"
                                                  + "People_Vaccinated, People_Fully_Vaccinated, Daily_Vaccinated) "
                   + "VALUES ('" + date + "'," + "'" + state + "'," + totalVac + "," +
                              totalDis + "," + peopleVac + "," + peopleFullyVac + "," + dailyVac + ")");
        
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
            switch (month) {
                case "January, 2020": month = "2020-01"; break;
                case "February, 2020": month = "2020-02"; break;
                case "March, 2020": month = "2020-03"; break;
                case "April, 2020": month = "2020-04"; break;
                case "May, 2020": month = "2020-05"; break;
                case "June, 2020": month = "2020-06"; break;
                case "July, 2020": month = "2020-07"; break;
                case "August, 2020": month = "2020-08"; break;
                case "September, 2020": month = "2020-09"; break;
                case "October, 2020": month = "2020-10"; break;
                case "November, 2020": month = "2020-11"; break;
                case "December, 2020": month = "2020-12"; break;
                case "January, 2021": month = "2021-01"; break;
                case "February, 2021": month = "2021-02"; break;
                case "March, 2021": month = "2021-03"; break;
                case "April, 2021": month = "2021-04"; break;
                default: break;
            }
            rs = stmt.executeQuery("SELECT Date, State, Cases, Deaths "
                                 + "FROM CasesAndDeaths"
                                 + "WHERE Date = '"+month+"%' AND State = '"+state+"'");
            
            Dataset staterow;
            while(rs.next()) {
                staterow = new Dataset(
                rs.getString("Date"),
                rs.getString("State"),
                rs.getInt("Cases"),
                rs.getInt("Deaths"));
                datasetArray.add(staterow);
            }
            
        } else if (tableType == "Monthly Vaccine Information"){ //if Vaccine Table is used
            switch (month) {
                case "January, 2021": month = "2021-01"; break;
                case "February, 2021": month = "2021-02"; break;
                case "March, 2021": month = "2021-03"; break;
                case "April, 2021": month = "2021-04"; break;
                //case "May, 2021": month = "2021-05"; break;
            }
            rs = stmt.executeQuery("SELECT Date, State, Total_Vaccines, "
                                        + "Total_Distributed, People_Vaccinated, "
                                        + "People_Fully_Vaccinated, Daily_Vaccinated "
                                 + "FROM VaccineInformation"
                                 + "WHERE Date LIKE '"+month+"%' AND State = '"+state+"'");
            Dataset vacrow;
            while(rs.next()) {
                vacrow = new Dataset(
                rs.getString("Date"),
                rs.getString("State"),
                rs.getInt("Total_Vaccines"),
                rs.getInt("Total_Distributed"),
                rs.getInt("People_Vaccinated"),
                rs.getInt("People_Fully_Vaccinated"),
                rs.getInt("Daily_Vaccinated"));
                datasetArray.add(vacrow);
            }
            
        } else if (tableType == "Monthly Trends"){ //if Trends Table is used
            String month1 = "";
            String month2 = "";
            switch (month) {
                case "January-February, 2021": month1 = "2021-01"; month2 = "2021-02"; break;
                case "February-March, 2021": month1 = "2021-02"; month2 = "2021-03"; break;
                case "March-April, 2021": month1 = "2021-03"; month2 = "2021-04";break;
                case "April-May, 2021": month1 = "2021-04"; month2 = "2021-05"; break;
            }
            
            rs = stmt.executeQuery("SELECT CasesAndDeaths.Date, CasesAndDeaths.State, CasesAndDeaths.Cases, "
                                        + "CasesAndDeaths.Deaths, VaccineInformation.Total_Vaccines, "
                                        + "VaccineInformation.Total_Distributed, VaccineInformation.People_Vaccinated, "
                                        + "VaccineInformation.People_Fully_Vaccinated, VaccineInformation.Daily_Vaccinated"
                                 + "FROM CasesAndDeaths"
                                 + "JOIN VaccineInformation ON CasesAndDeaths.Date = VaccineInformation.Date"
                                 + "WHERE Date LIKE '"+month1+"%' AND State = '"+state+"'");
            
            ArrayList<Dataset> month1Array = new ArrayList<Dataset>();
            Dataset month1row;
            while(rs.next()){
                month1row = new Dataset(
                    rs.getString("CasesAndDeaths.State"),
                    rs.getInt("CasesAndDeaths.Cases"),
                    rs.getInt("CasesAndDeaths.Deaths"),
                    rs.getInt("VaccineInformation.Total_Vaccines"),
                    rs.getInt("VaccineInformation.Total_Distributed"),
                    rs.getInt("VaccineInformation.People_Vaccinated"),
                    rs.getInt("VaccineInformation.People_Fully_Vaccinated"),
                    rs.getInt("VaccineInformation.Daily_Vaccinated")
                );
                month1Array.add(month1row);
            }
            
            rs = stmt.executeQuery("SELECT CasesAndDeaths.Date, CasesAndDeaths.State, CasesAndDeaths.Cases, "
                                        + "CasesAndDeaths.Deaths, VaccineInformation.Total_Vaccines, "
                                        + "VaccineInformation.Total_Distributed, VaccineInformation.People_Vaccinated, "
                                        + "VaccineInformation.People_Fully_Vaccinated, VaccineInformation.Daily_Vaccinated"
                                 + "FROM CasesAndDeaths"
                                 + "JOIN VaccineInformation ON CasesAndDeaths.Date = VaccineInformation.Date"
                                 + "WHERE Date LIKE '"+month2+"%' AND State = '"+state+"'");
            
            ArrayList<Dataset> month2Array = new ArrayList<Dataset>();
            Dataset month2row;
            while(rs.next()){
                month2row = new Dataset(
                    rs.getString("CasesAndDeaths.State"),
                    rs.getInt("CasesAndDeaths.Cases"),
                    rs.getInt("CasesAndDeaths.Deaths"),
                    rs.getInt("VaccineInformation.Total_Vaccines"),
                    rs.getInt("VaccineInformation.Total_Distributed"),
                    rs.getInt("VaccineInformation.People_Vaccinated"),
                    rs.getInt("VaccineInformation.People_Fully_Vaccinated"),
                    rs.getInt("VaccineInformation.Daily_Vaccinated")
                );
                month2Array.add(month2row);
            }
            
            datasetArray = TrendsDataCalculator.calculateTrendsData(month2Array, month1Array);
        }
        return datasetArray;
    }
}
