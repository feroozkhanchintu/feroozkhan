package com.codenation.assingment02;

public class Event {
    private String name;
    private Status status;
    private int startTime;
    private int runTimeUnits;
    
    Event(String Name, Status status, int startTime, int runTimeUnits)
    {
        this.name = Name;
        this.status = status;
        this.startTime = startTime;
        this.runTimeUnits = runTimeUnits;
    }
    
    public String getName()
    {
        return name;
    }
    
    public Status getStatus()
    {
        return status;
    }
    
    public int getStartTime()
    {
        return startTime;
    }
    
    public int getTotalTimeUnits()
    {
        return runTimeUnits;
    }
}
