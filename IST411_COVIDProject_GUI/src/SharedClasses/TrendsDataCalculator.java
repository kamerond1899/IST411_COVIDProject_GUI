/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SharedClasses;

import java.util.ArrayList;

/**
 *
 * @author RIVER
 */
public class TrendsDataCalculator {
    public static ArrayList<Dataset> calculateTrendsData(ArrayList<Dataset> selectedMonth, ArrayList<Dataset> previousMonth){
        
        int month1CasesTrend = selectedMonth.get(selectedMonth.size()-1).getCases() - selectedMonth.get(0).getCases();
        int month1DeathsTrend = selectedMonth.get(selectedMonth.size()-1).getDeaths() - selectedMonth.get(0).getDeaths();
        int month1TVTrend = selectedMonth.get(selectedMonth.size()-1).getTotalVac() - selectedMonth.get(0).getTotalVac();
        int month1TDTrend = selectedMonth.get(selectedMonth.size()-1).getTotalDis() - selectedMonth.get(0).getTotalDis();
        int month1PFTrend = selectedMonth.get(selectedMonth.size()-1).getPeopleVac() - selectedMonth.get(0).getPeopleVac();
        int month1PFVTrend = selectedMonth.get(selectedMonth.size()-1).getPeopleFullyVac() - selectedMonth.get(0).getPeopleFullyVac();
        int month1DVSum = 0;
        for (int i = 0; i < selectedMonth.size(); i++){
            month1DVSum = month1DVSum + selectedMonth.get(i).getDailyVac();
        }
        int month1DVTrend = Math.round(month1DVSum / selectedMonth.size());
        
        int month2CasesTrend = previousMonth.get(previousMonth.size()-1).getCases() - previousMonth.get(0).getCases();
        int month2DeathsTrend = previousMonth.get(previousMonth.size()-1).getDeaths() - previousMonth.get(0).getDeaths();
        int month2TVTrend = previousMonth.get(previousMonth.size()-1).getTotalVac() - previousMonth.get(0).getTotalVac();
        int month2TDTrend = previousMonth.get(previousMonth.size()-1).getTotalDis() - previousMonth.get(0).getTotalDis();
        int month2PFTrend = previousMonth.get(previousMonth.size()-1).getPeopleVac() - previousMonth.get(0).getPeopleVac();
        int month2PFVTrend = previousMonth.get(previousMonth.size()-1).getPeopleFullyVac() - previousMonth.get(0).getPeopleFullyVac();
        int month2DVSum = 0;
        for (int i = 0; i < previousMonth.size(); i++){
            month2DVSum = month2DVSum + previousMonth.get(i).getDailyVac();
        }
        int month2DVTrend = Math.round(month2DVSum / previousMonth.size());
        
        int casesTrend = month2CasesTrend - month1CasesTrend;
        int deathsTrend = month2DeathsTrend - month1DeathsTrend;
        int totalVacTrend = month2TVTrend - month1TVTrend;
        int totalDisTrend = month2TDTrend - month1TDTrend;
        int peopleVacTrend = month2PFTrend - month1PFTrend;
        int peopleFVacTrend = month2PFVTrend - month1PFVTrend;
        int dailyVacTrend = month2DVTrend - month1DVTrend;
        
        Dataset trends = new Dataset(selectedMonth.get(0).getState(), 
                                    casesTrend, 
                                    deathsTrend, 
                                    totalVacTrend, 
                                    totalDisTrend,
                                    peopleVacTrend,
                                    peopleFVacTrend,
                                    dailyVacTrend);
        
        ArrayList<Dataset> trendsData = new ArrayList<Dataset>();
        trendsData.add(trends);
        
        return trendsData;
    }
}
