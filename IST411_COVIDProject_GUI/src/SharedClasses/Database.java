/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SharedClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
    
        public void insertStateValues(ArrayList<Dataset> s) throws SQLException {
        
        Connection con = getConnection();
              con.setAutoCommit(true);
        //Statement stmt = con.createStatement();
        PreparedStatement statement;
        
        String compiledQuery = "INSERT INTO CasesAndDeaths(Date, State, Cases, Deaths) "
                   + "VALUES"+"(?, ?, ?, ?)";
        
                statement = con.prepareStatement(compiledQuery);
        for(int i = 0; i < s.size(); i++)
        {
            statement.setString(1, s.get(i).getDate());
            statement.setString(2, s.get(i).getState());
            statement.setInt(3,  s.get(i).getCases());
            statement.setInt(4,  s.get(i).getDeaths());
            
            statement.executeUpdate();
        }
          
        statement.close();
        con.close();
    }
    
    public void insertVaccineValues(ArrayList<Dataset> v) throws SQLException {
        
         
        Connection con = getConnection();
              con.setAutoCommit(true);
        //Statement stmt = con.createStatement();
        PreparedStatement statement;
        
        String compiledQuery = "INSERT INTO VaccineInformation(Date, State, Total_Vaccines, Total_Distributed ,People_Vaccinated, People_Fully_Vaccinated, Daily_Vaccinated)"
                   + " VALUES" + "(?, ?, ?, ?, ?, ?, ?)";
        
                statement = con.prepareStatement(compiledQuery);
        for(int i = 0; i < v.size(); i++)
        {
            statement.setString(1, v.get(i).getDate());
            statement.setString(2, v.get(i).getState());
            statement.setInt(3, v.get(i).getTotalVac());
            statement.setInt(4, v.get(i).getTotalDis());
            statement.setInt(5, v.get(i).getPeopleVac());
            statement.setInt(6, v.get(i).getPeopleFullyVac());
            statement.setInt(7, v.get(i).getDailyVac());
            
            statement.executeUpdate();
        }
        
        
        statement.close();
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
        System.out.println(tableType);
        if (tableType.equals("Monthly Cases And Deaths")){ //if State Table is used
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
            
            String query = "SELECT Date, State, Cases, Deaths FROM CasesAndDeaths WHERE Date LIKE '"+month+"%' AND State = '"+state+"'";
            System.out.println(query);
            rs = stmt.executeQuery(query);
            
            Dataset staterow;
            while(rs.next()) {
                staterow = new Dataset(
                rs.getString("Date"),
                rs.getString("State"),
                rs.getInt("Cases"),
                rs.getInt("Deaths"));
                datasetArray.add(staterow);
            }
            
        }  //end "Monthly Cases And Deaths" if
        if (tableType == "Monthly Vaccine Information"){ //if Vaccine Table is used
            switch (month) {
                case "January, 2021": month = "2021-01"; break;
                case "February, 2021": month = "2021-02"; break;
                case "March, 2021": month = "2021-03"; break;
                case "April, 2021": month = "2021-04"; break;
                //case "May, 2021": month = "2021-05"; break;
            }
            
            String query = "SELECT Date, State, Total_Vaccines, Total_Distributed, People_Vaccinated, People_Fully_Vaccinated, Daily_Vaccinated FROM VaccineInformation WHERE Date LIKE '"+month+"%' AND State = '"+state+"'";
            System.out.println(query);
            rs = stmt.executeQuery(query);
            
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
        } //end "Monthly Vaccine Information" if
        if (tableType == "Monthly Trends"){ //if Trends Table is used
            String month1 = "";
            String month2 = "";
            switch (month) {
                case "January-February, 2021": month1 = "2021-01"; month2 = "2021-02"; break;
                case "February-March, 2021": month1 = "2021-02"; month2 = "2021-03"; break;
                case "March-April, 2021": month1 = "2021-03"; month2 = "2021-04";break;
            }
            
            String month1CaDQuery = "SELECT CasesAndDeaths.Cases, CasesAndDeaths.Deaths FROM CasesAndDeaths WHERE CasesAndDeaths.Date LIKE '"+month1+"%' AND CasesAndDeaths.State = '"+state+"'";
            System.out.println(month1CaDQuery);
            rs = stmt.executeQuery(month1CaDQuery);
            ArrayList<Integer> month1CasesRow = new ArrayList<>();
            ArrayList<Integer> month1DeathsRow = new ArrayList<>();
            int month1cases = 0; int month1deaths = 0;
            while(rs.next()){
                month1cases = rs.getInt("Cases");
                month1CasesRow.add(month1cases);
                month1deaths = rs.getInt("Deaths");
                month1DeathsRow.add(month1deaths);
                System.out.println(month1cases + " " + month1deaths);
            }
            
            String month1VacQuery = "SELECT VaccineInformation.Total_Vaccines, VaccineInformation.Total_Distributed, VaccineInformation.People_Vaccinated, VaccineInformation.People_Fully_Vaccinated, VaccineInformation.Daily_Vaccinated FROM VaccineInformation WHERE VaccineInformation.Date LIKE '"+month1+"%' AND VaccineInformation.State = '"+state+"'";
            System.out.println(month1VacQuery);
            rs = stmt.executeQuery(month1VacQuery);
            ArrayList<Integer> month1TVRow = new ArrayList<>();
            ArrayList<Integer> month1TDRow = new ArrayList<>();
            ArrayList<Integer> month1PVRow = new ArrayList<>();
            ArrayList<Integer> month1PFVRow = new ArrayList<>();
            ArrayList<Integer> month1DVRow = new ArrayList<>();
            int month1TV = 0; int month1TD = 0;
            int month1PV = 0; int month1PFV = 0;
            int month1DV = 0;
            while(rs.next()){
                month1TV = rs.getInt("Total_Vaccines");
                month1TVRow.add(month1TV);
                month1TD = rs.getInt("Total_Distributed");
                month1TDRow.add(month1TD);
                month1PV = rs.getInt("People_Vaccinated");
                month1PVRow.add(month1PV);
                month1PFV = rs.getInt("People_Fully_Vaccinated");
                month1PFVRow.add(month1PFV);
                month1DV = rs.getInt("Daily_Vaccinated");
                month1DVRow.add(month1DV);
                System.out.println(month1TV + " " + month1TD + " " + month1PV + " " + month1PFV + " " + month1DV);
            }
            
            ArrayList<Dataset> month1Array = new ArrayList<>();
            int month1ArraySize = 0;
            if(month1CasesRow.size() < month1TVRow.size()){
                month1ArraySize = month1CasesRow.size();
            } else if (month1CasesRow.size() > month1TVRow.size()){
                month1ArraySize = month1TVRow.size();
            } else {
                month1ArraySize = month1CasesRow.size();
            }
            for (int i = 0; i < month1ArraySize; i++){
                Dataset data = new Dataset();
                data.setState(state);
                data.setCases(month1CasesRow.get(i));
                data.setDeaths(month1DeathsRow.get(i));
                data.setTotalVac(month1TVRow.get(i));
                data.setTotalDis(month1TDRow.get(i));
                data.setPeopleVac(month1PVRow.get(i));
                data.setPeopleFullyVac(month1PFVRow.get(i));
                data.setDailyVac(month1DVRow.get(i));
                month1Array.add(data);
            }
            
            String month2CaDQuery = "SELECT CasesAndDeaths.Cases, CasesAndDeaths.Deaths FROM CasesAndDeaths WHERE CasesAndDeaths.Date LIKE '"+month2+"%' AND CasesAndDeaths.State = '"+state+"'";
            System.out.println(month2CaDQuery);
            rs = stmt.executeQuery(month2CaDQuery);
            ArrayList<Integer> month2CasesRow = new ArrayList<>();
            ArrayList<Integer> month2DeathsRow = new ArrayList<>();
            int month2cases = 0; int month2deaths = 0;
            while(rs.next()){
                month2cases = rs.getInt("Cases");
                month2CasesRow.add(month1cases);
                month2deaths = rs.getInt("Deaths");
                month2DeathsRow.add(month2deaths);
                System.out.println(month2cases + " " + month2deaths);
            }
            
            String month2VacQuery = "SELECT VaccineInformation.Total_Vaccines, VaccineInformation.Total_Distributed, VaccineInformation.People_Vaccinated, VaccineInformation.People_Fully_Vaccinated, VaccineInformation.Daily_Vaccinated FROM VaccineInformation WHERE VaccineInformation.Date LIKE '"+month2+"%' AND VaccineInformation.State = '"+state+"'";
            System.out.println(month2VacQuery);
            rs = stmt.executeQuery(month2VacQuery);
            ArrayList<Integer> month2TVRow = new ArrayList<>();
            ArrayList<Integer> month2TDRow = new ArrayList<>();
            ArrayList<Integer> month2PVRow = new ArrayList<>();
            ArrayList<Integer> month2PFVRow = new ArrayList<>();
            ArrayList<Integer> month2DVRow = new ArrayList<>();
            int month2TV = 0; int month2TD = 0;
            int month2PV = 0; int month2PFV = 0;
            int month2DV = 0;
            while(rs.next()){
                month2TV = rs.getInt("Total_Vaccines");
                month2TVRow.add(month2TV);
                month2TD = rs.getInt("Total_Distributed");
                month2TDRow.add(month2TD);
                month2PV = rs.getInt("People_Vaccinated");
                month2PVRow.add(month2PV);
                month2PFV = rs.getInt("People_Fully_Vaccinated");
                month2PFVRow.add(month2PFV);
                month2DV = rs.getInt("Daily_Vaccinated");
                month2DVRow.add(month2DV);
                System.out.println(month2TV + " " + month2TD + " " + month2PV + " " + month2PFV + " " + month2DV);
            }
            
            ArrayList<Dataset> month2Array = new ArrayList<>();
            int month2ArraySize = 0;
            if(month2CasesRow.size() < month2TVRow.size()){
                month2ArraySize = month2CasesRow.size();
            } else if (month2CasesRow.size() > month2TVRow.size()){
                month2ArraySize = month2TVRow.size();
            } else {
                month2ArraySize = month2CasesRow.size();
            }
            for (int i = 0; i < month2ArraySize; i++){
                Dataset data = new Dataset();
                data.setState(state);
                data.setCases(month2CasesRow.get(i));
                data.setDeaths(month2DeathsRow.get(i));
                data.setTotalVac(month2TVRow.get(i));
                data.setTotalDis(month2TDRow.get(i));
                data.setPeopleVac(month2PVRow.get(i));
                data.setPeopleFullyVac(month2PFVRow.get(i));
                data.setDailyVac(month2DVRow.get(i));
                month2Array.add(data);
            }
            
            datasetArray = TrendsDataCalculator.calculateTrendsData(month2Array, month1Array);
        } //end "Monthly Trends" if
        return datasetArray;
    }
}
