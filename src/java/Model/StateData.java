/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Shadd
 */
public class StateData {
    
    private String date;
    private String state;
    private int cases;
    private int deaths;
    public StateData()
    {
        
    }
    
    public StateData(String date, String state, int cases, int deaths){
        this.date = date;
        this.state = state;
        this.cases= cases;
        this.deaths= deaths;
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
}
