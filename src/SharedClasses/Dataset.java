/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SharedClasses;

import java.io.Serializable;

/**
 *
 * @author RIVER
 */
public class Dataset implements Serializable{
    
    private String date;
    private String state;
    private int cases;
    private int deaths;
    private int totalVac;
    private int totalDis;
    private int peopleVac;
    private int peopleFullyVac;
    private int dailyVac;
    
    public Dataset(){ //zero argument constructor
        this.state = null;
        this.cases = 0;
        this.deaths = 0;
        this.totalVac = 0;
        this.totalDis = 0;
        this.peopleVac = 0;
        this.peopleFullyVac = 0;
        this.dailyVac = 0;
    }
    
    public Dataset(String da, String st, int c, int de){ //State constructor
        this.date = da;
        this.state = st;
        this.cases = c;
        this.deaths = de;
    }
    
    public Dataset(String da, String st, int tV, int tD, int pV, int pFV, int dV){ //Vaccine constructor
        this.date = da;
        this.state = st;
        this.totalVac = tV;
        this.totalDis = tD;
        this.peopleVac = pV;
        this.peopleFullyVac = pFV;
        this.dailyVac = dV;
    }
    
    public Dataset(String st, int c, int de, int tV, int tD, int pV, int pFV, int dV){ //Trends Constructor
        this.state = st;
        this.cases = c;
        this.deaths = de;
        this.totalVac = tV;
        this.totalDis = tD;
        this.peopleVac = pV;
        this.peopleFullyVac = pFV;
        this.dailyVac = dV;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }
    
    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the cases
     */
    public int getCases() {
        return cases;
    }

    /**
     * @param cases the cases to set
     */
    public void setCases(int cases) {
        this.cases = cases;
    }

    /**
     * @return the deaths
     */
    public int getDeaths() {
        return deaths;
    }

    /**
     * @param deaths the deaths to set
     */
    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    /**
     * @return the totalVac
     */
    public int getTotalVac() {
        return totalVac;
    }

    /**
     * @param totalVac the totalVac to set
     */
    public void setTotalVac(int totalVac) {
        this.totalVac = totalVac;
    }

    /**
     * @return the totalDis
     */
    public int getTotalDis() {
        return totalDis;
    }

    /**
     * @param totalDis the totalDis to set
     */
    public void setTotalDis(int totalDis) {
        this.totalDis = totalDis;
    }

    /**
     * @return the peopleVac
     */
    public int getPeopleVac() {
        return peopleVac;
    }

    /**
     * @param peopleVac the peopleVac to set
     */
    public void setPeopleVac(int peopleVac) {
        this.peopleVac = peopleVac;
    }

    /**
     * @return the peopleFullyVac
     */
    public int getPeopleFullyVac() {
        return peopleFullyVac;
    }

    /**
     * @param peopleFullyVac the peopleFullyVac to set
     */
    public void setPeopleFullyVac(int peopleFullyVac) {
        this.peopleFullyVac = peopleFullyVac;
    }

    /**
     * @return the dailyVac
     */
    public int getDailyVac() {
        return dailyVac;
    }

    /**
     * @param dailyVac the dailyVac to set
     */
    public void setDailyVac(int dailyVac) {
        this.dailyVac = dailyVac;
    }
}
