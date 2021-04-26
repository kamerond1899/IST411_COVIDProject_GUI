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
        
        int casesSum1 = 0; int casesSum2 = 0;
        int deathsSum1 = 0; int deathsSum2 = 0;
        int totalVacSum1 = 0; int totalVacSum2 = 0;
        int totalDisSum1 = 0; int totalDisSum2 = 0;
        int peopleVacSum1 = 0; int peopleVacSum2 = 0;
        int peopleFVacSum1 = 0; int peopleFVacSum2 = 0;
        int dailyVacSum1 = 0; int dailyVacSum2 = 0;
        
        for (int i = 0; i < selectedMonth.size(); i++){
            casesSum1 = casesSum1 + selectedMonth.get(i).getCases();
            deathsSum1 = deathsSum1 + selectedMonth.get(i).getDeaths();
            totalVacSum1 = totalVacSum1 + selectedMonth.get(i).getTotalVac();
            totalDisSum1 = totalDisSum1 + selectedMonth.get(i).getTotalDis();
            peopleVacSum1 = peopleVacSum1 + selectedMonth.get(i).getPeopleVac();
            peopleFVacSum1 = peopleFVacSum1 + selectedMonth.get(i).getPeopleFullyVac();
            dailyVacSum1 = dailyVacSum1 + selectedMonth.get(i).getDailyVac();
        }
        
        for (int i = 0; i < previousMonth.size(); i++){
            casesSum2 = casesSum2 + previousMonth.get(i).getCases();
            deathsSum2 = deathsSum2 + previousMonth.get(i).getDeaths();
            totalVacSum2 = totalVacSum2 + previousMonth.get(i).getTotalVac();
            totalDisSum2 = totalDisSum2 + previousMonth.get(i).getTotalDis();
            peopleVacSum2 = peopleVacSum2 + previousMonth.get(i).getPeopleVac();
            peopleFVacSum2 = peopleFVacSum2 + previousMonth.get(i).getPeopleFullyVac();
            dailyVacSum2 = dailyVacSum2 + previousMonth.get(i).getDailyVac();
        }
        
        int casesSumDiff = 0;
        int deathsSumDiff = 0;
        int totalVacSumDiff = 0;
        int totalDisSumDiff = 0;
        int peopleVacSumDiff = 0;
        int peopleFVacSumDiff = 0;
        int dailyVacSumDiff = 0;
        
        casesSumDiff = casesSum2 - casesSum1;
        deathsSumDiff = deathsSum2 - deathsSum1;
        totalVacSumDiff = totalVacSum2 - totalVacSum1;
        totalDisSumDiff = totalDisSum2 - totalDisSum1;
        peopleVacSumDiff = peopleVacSum2 - peopleVacSum1;
        peopleFVacSumDiff = peopleFVacSum2 - peopleFVacSum1;
        dailyVacSumDiff = dailyVacSum2 - dailyVacSum1;
        
        Dataset trends = new Dataset(selectedMonth.get(0).getState(), 
                                    casesSumDiff, 
                                    deathsSumDiff, 
                                    totalVacSumDiff, 
                                    totalDisSumDiff,
                                    peopleVacSumDiff,
                                    peopleFVacSumDiff,
                                    dailyVacSumDiff);
        
        ArrayList<Dataset> trendsData = new ArrayList<Dataset>();
        trendsData.add(trends);
        
        return trendsData;
    }
}
