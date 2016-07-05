package com.codenation.assingment02;

public class Appliance {
    
    private Status status;
    private int startTime;
    private int defaultRunTime;
    private int totalRunTimeUnits;
    private String applianceName;
    private static int currentTimeUnit = 0;
    
    Appliance(String applianceName, int defaultRunTime)
    {
        this.applianceName = applianceName;
        this.defaultRunTime = defaultRunTime;
        initStatus();
    }
    
    public void init(int startTime, int totalRunTimeUnits)
    {
        this.startTime = startTime;
        this.totalRunTimeUnits = totalRunTimeUnits;
    }
    
    public String getName()
    {
        return applianceName;
    }
    
    public void initStatus()
    {
        this.status = Status.OFF;
    }
    
    public void turnON()
    {
        this.status = Status.ON;
    }
    
    public void turnOFF()
    {
        this.status = Status.OFF;
        totalRunTimeUnits = 0;
    }
    
    public void updateStatus(int currentTime)
    {

        if(status == Status.ON){
        
        if((currentTime - startTime - defaultRunTime) == 0 
                || (currentTime - startTime - totalRunTimeUnits) == 0)
            turnOFF();
            
        }

        printStatus();

    }
    
    public void printStatus()
    {
        System.out.println("Appliance Name : " + applianceName);
        System.out.println("Status : " + status);
        System.out.println();
        
    }
    
}

