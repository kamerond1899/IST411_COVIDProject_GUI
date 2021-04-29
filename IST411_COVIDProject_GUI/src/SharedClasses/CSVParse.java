package SharedClasses;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import sun.misc.FloatingDecimal;

/**  
 * IST 411-001 - Final Project
 * CSVParse.java  
 * Purpose: Calls CSV APIs and stores the APIs in the database
 *  
 * @author Kameron Dangleben (Lead) & River Martinez  
 * @version 1.0 5/4/2021
 */
public class CSVParse {
    
    private Database db = new Database(); //Database object
    
    public CSVParse() throws MalformedURLException, SQLException { //CSVParse Constructor
        //parseStates(); //One and done (static API)
        parseVaccines(); //Runs once when the app is started
    }
    
    /**
     * Purpose: Calls States CSV API, retrieving CSV Records and storing each 
     * record into CasesAndDeaths table in the database.
     * 
     * @throws MalformedURLException
     * @throws SQLException 
     */
    public void parseStates() throws MalformedURLException, SQLException {
        
        //URL path for CSV file
        URL url = new URL("https://raw.githubusercontent.com/nytimes/covid-19-data/master/us-states.csv");

        //Ignores header of CSV file
        CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase();

        try(CSVParser csvParser = CSVParser.parse(url, StandardCharsets.UTF_8, csvFormat)) {
            
            db.createStateTable(); //Creates CasesAndDeath table
            ArrayList<Dataset> sr = new ArrayList<>();
            
            //For loop to retrieve data from each CSV record (row), setting a Dataset object and adding it to ArrayList<Dataset>
            for(CSVRecord csvRecord : csvParser) {
                String date = csvRecord.get("date");
                String state = csvRecord.get("state");
                String cases = csvRecord.get("cases");
                String deaths = csvRecord.get("deaths");
                
                sr.add(new Dataset(
                            date,
                            state,
                            Integer.parseInt(cases),
                            Integer.parseInt(deaths)
                            )
                );
            }
            db.insertStateValues(sr); //Insert ArrayList<Dataset> data into CasesAndDeath table
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Purpose: Calls Vaccine CSV API, retrieving CSV Records and storing each 
     * record into VaccineInformation table in the database.
     * 
     * @throws MalformedURLException
     * @throws SQLException 
     */
    public void parseVaccines() throws MalformedURLException, SQLException {
        
        //URL path for CSV file
        URL url = new URL("https://raw.githubusercontent.com/owid/covid-19-data/master/public/data/vaccinations/us_state_vaccinations.csv");
        
        //Ignores header of CSV file
        CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase();

        try(CSVParser csvParser = CSVParser.parse(url, StandardCharsets.UTF_8, csvFormat)) {
            db.createVaccineTable();
            ArrayList<Dataset> vr = new ArrayList<>();
            
            //For loop to retrieve data from each CSV record (row), setting a Dataset object and adding it to ArrayList<Dataset>
            for(CSVRecord csvRecord : csvParser) {
                String date = csvRecord.get("date");
                String state = csvRecord.get("location");
                String totalVac = csvRecord.get("total_vaccinations");
                String totalDis = csvRecord.get("total_distributed");
                String peopleVac = csvRecord.get("people_vaccinated");
                String peopleFullyVac = csvRecord.get("people_fully_vaccinated");
                String dailyVac = csvRecord.get("daily_vaccinations");
                
                //if statements to check whether a column of the each row is blank
                //If blank, set value of that row to zero
                if ("".equals(totalVac)) {
                    double tV = 0;
                    totalVac = String.valueOf(tV);
                }
                if ("".equals(totalDis)) {
                    double tD = 0;
                    totalDis = String.valueOf(tD);
                }
                if ("".equals(peopleVac)) {
                    double pV = 0;
                    peopleVac = String.valueOf(pV);
                }
                if ("".equals(peopleFullyVac)) {
                    double pFV = 0;
                    peopleFullyVac = String.valueOf(pFV);
                }
                if ("".equals(dailyVac)) {
                    double dV = 0;
                    dailyVac = String.valueOf(dV);
                }
                
                vr.add(new Dataset(
                            date,
                            state,
                            (int) FloatingDecimal.parseDouble(totalVac),
                            (int) FloatingDecimal.parseDouble(totalDis),  
                            (int) FloatingDecimal.parseDouble(peopleVac), 
                            (int) FloatingDecimal.parseDouble(peopleFullyVac), 
                            (int) FloatingDecimal.parseDouble(dailyVac)
                            )
                );         
            }
            db.insertVaccineValues(vr);//Insert ArrayList<Dataset> data into VaccineInformation table
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}