package Controller;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class CSVParse {
    public static void main(String... args) throws MalformedURLException {
        URL url = new URL("https://raw.githubusercontent.com/owid/covid-19-data/master/public/data/vaccinations/us_state_vaccinations.csv");

        CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase();
//date,location,total_vaccinations,total_distributed,people_vaccinated,people_fully_vaccinated_per_hundred,
//total_vaccinations_per_hundred,people_fully_vaccinated,people_vaccinated_per_hundred,distributed_per_hundred,
//daily_vaccinations_raw,daily_vaccinations,daily_vaccinations_per_million,share_doses_used
        try(CSVParser csvParser = CSVParser.parse(url, StandardCharsets.UTF_8, csvFormat)) {
            System.out.println("Date, Total Vaccinations, Total Distributed, People Vaccinated"
                    + ", People fully Vaccinated");
            ArrayList<String> dates = new ArrayList<>();
            int i = 0;
            for(CSVRecord csvRecord : csvParser) {
                String date = csvRecord.get("date");
                String totalvac = csvRecord.get("total_vaccinations");
                String totaldist = csvRecord.get("total_distributed");
                String peoplevac = csvRecord.get("people_vaccinated");
                //String peoplefvac = csvRecord.get("people_fully_vaccinated");
                dates.add(date);

                
                System.out.println(dates.get(i) + "," + totalvac + "," + totaldist + "," + peoplevac);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}