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

public class CSVParse {
    
    private Database db = new Database();
    
    public CSVParse() throws MalformedURLException, SQLException {
        parseStates();
        parseVaccines();
    }
    
    public void parseStates() throws MalformedURLException, SQLException {
        
        URL url = new URL("https://raw.githubusercontent.com/nytimes/covid-19-data/master/us-states.csv");

        CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase();

        try(CSVParser csvParser = CSVParser.parse(url, StandardCharsets.UTF_8, csvFormat)) {
            db.createStateTable();
            
            for(CSVRecord csvRecord : csvParser) {
                String date = csvRecord.get("date");
                String state = csvRecord.get("state");
                String cases = csvRecord.get("cases");
                String deaths = csvRecord.get("deaths");
                
                db.insertStateValues(date, state, Integer.parseInt(cases), Integer.parseInt(deaths));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void parseVaccines() throws MalformedURLException, SQLException {
        
        URL url = new URL("https://raw.githubusercontent.com/owid/covid-19-data/master/public/data/vaccinations/us_state_vaccinations.csv");

        CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase();

        try(CSVParser csvParser = CSVParser.parse(url, StandardCharsets.UTF_8, csvFormat)) {
            db.createVaccineTable();
            
            for(CSVRecord csvRecord : csvParser) {
                String date = csvRecord.get("date");
                String state = csvRecord.get("location");
                String totalVac = csvRecord.get("total_vaccinations");
                String totalDis = csvRecord.get("total_distributed");
                String peopleVac = csvRecord.get("people_vaccinated");
                String peopleFullyVac = csvRecord.get("people_fully_vaccinated");
                String dailyVac = csvRecord.get("daily_vaccinations");
                
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
                
                db.insertVaccineValues(date, state, (int) FloatingDecimal.parseDouble(totalVac), 
                                                    (int) FloatingDecimal.parseDouble(totalDis), 
                                                    (int) FloatingDecimal.parseDouble(peopleVac), 
                                                    (int) FloatingDecimal.parseDouble(peopleFullyVac), 
                                                    (int) FloatingDecimal.parseDouble(dailyVac));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}