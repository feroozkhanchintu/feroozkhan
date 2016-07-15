package com.codenation.ecommerce.Pojos;

import java.sql.Timestamp;

/**
 * Created by Ferooz on 11/07/16.
 */
public class LogInformation {
    private String URL;
    private String parameters;
    private int responseCode;
    private String ipAddress;
    private Timestamp startTime;
    private long execTime;

    public LogInformation() {
    }

    public LogInformation(String URL, String parameters, int responseCode, String ipAddress, Timestamp startTime, long execTime) {
        this.URL = URL;
        this.parameters = parameters;
        this.responseCode = responseCode;
        this.ipAddress = ipAddress;
        this.startTime = startTime;
        this.execTime = execTime;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public long getExecTime() {
        return execTime;
    }

    public void setExecTime(long execTime) {
        this.execTime = execTime;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

}
