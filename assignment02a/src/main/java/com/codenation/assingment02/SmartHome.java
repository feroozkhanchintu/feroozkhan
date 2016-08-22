package com.codenation.assingment02;

import java.util.*;

public class SmartHome {
    public static void main(String args[])
    {
        int hrsInADay = 2;
        
        HashMap<Integer, ArrayList<Event>> hrEventsMap = new HashMap<Integer, ArrayList<Event>>();
        HashMap<String, Appliance> nameApplianceMap = new HashMap<String, Appliance>();
        
        System.out.println("Enter number of appliances");
        
        Scanner in = new Scanner(System.in);
        
        int numDevices = in.nextInt();
        
        
        for(int i = 0; i < numDevices; i++)
        {
            System.out.println("Appliance : " + (i + 1));
            System.out.println("Enter the name of the appliance");
            String name = in.next();
            
            System.out.println("Enter the default runtime of the appliance");
            int runtime = in.nextInt();
            
            nameApplianceMap.put(name, new Appliance(name,runtime));
            
        }
        
        ArrayList<Event> events = new ArrayList<Event>();
        
        System.out.println("Please enter the events");
        
        for(int i = 0; i < hrsInADay; i++)
        {
            int repeat = 0;
            do{
            System.out.println("Do you want to add an event for HOUR " + (i + 1));
            String temp = in.next();
            
            if(temp.equals("YES"))
            {
                System.out.println("Enter name and status");
                String name = in.next();
                String status = in.next();
                
                if(status.equals("OFF"))
                    events.add(new Event(name, Status.OFF, i + 1, 0));
                else
                    {
                        System.out.println("Enter the total run units");
                        int runUnits = in.nextInt();
                        
                        events.add(new Event(name, Status.ON, i + 1, runUnits));          
                    }
                  repeat = 1;
            }
            else
              repeat = 0;
            }while(repeat == 1);
            
            hrEventsMap.put(i + 1, events);
            events = new ArrayList<Event>();
        }
        
        for(int i = 0; i < hrsInADay; i++)
        {
            if(hrEventsMap.containsKey(i+1)){
                ArrayList<Event> currentHREvent = hrEventsMap.get(i+1);
                
                
                for(int k = 0; k < currentHREvent.size(); k++)
                {
                    if(nameApplianceMap.containsKey(currentHREvent.get(k).getName()))
                    {
                        
                        Appliance app = nameApplianceMap.get(currentHREvent.get(k).getName());
                        
                        Status stat = currentHREvent.get(k).getStatus();
                        
                        if(stat == Status.OFF)
                            app.turnOFF();
                        else
                        {
                            app.turnON();
                            app.init(currentHREvent.get(k).getStartTime(), currentHREvent.get(k).getTotalTimeUnits());
                        }
                    }
                }
            }
            
            Iterator it = nameApplianceMap.entrySet().iterator();
             while (it.hasNext()) {
              Map.Entry pair = (Map.Entry)it.next();
              Appliance ap = (Appliance) pair.getValue();
              ap.updateStatus(i + 1);
         }

        
        }
        
    }
}
