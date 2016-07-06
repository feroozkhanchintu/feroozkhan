package com.codenation.assingment02;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SmartHome {
    
    
    
    public static void main(String args[])
    {
        int hrsInADay = 24;
        String fileName = "/projects/feroozkhan/assignment02a/src/main/resources/simulation.txt";
        
        HashMap<Integer, ArrayList<Event>> hrEventsMap = new HashMap<Integer, ArrayList<Event>>();
        HashMap<String, Appliance> nameApplianceMap = new HashMap<String, Appliance>();
        
        System.out.println("Enter number of appliances");
        
        Scanner in = new Scanner(System.in);
        
        int numDevices = in.nextInt();
        
        nameApplianceMap = getAppliancesDetails(numDevices, in);
        
        
        try{
            
           hrEventsMap = getEventDetailsFromFile(fileName);
        }
        catch(FileNotFoundException ex)
        {
            System.out.println("File not found");
        }
        catch(IOException ex)
        {
            System.out.println("IOException");
        }
        
        
        for(int i = 0; i < hrsInADay; i++)
        {
            System.out.println("Hour : " + (i + 1));
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
        
    try{
        Thread.sleep(100);
    }
    catch(InterruptedException ex)
    {
        System.out.println("ERROR in sleep");
    }

    

        }
        
    }
    
    public static HashMap<String, Appliance> getAppliancesDetails(int numDevices, Scanner in)
    {
        HashMap<String, Appliance> nameApplianceMap = new HashMap<String, Appliance>();
                
        for(int i = 0; i < numDevices; i++)
        {
            System.out.println("Appliance : " + (i + 1));
            System.out.println("Enter the name of the appliance");
            String name = in.next();
            
            System.out.println("Enter the default runtime of the appliance");
            int runtime = in.nextInt();
            
            nameApplianceMap.put(name, new Appliance(name,runtime));            
        }   
        return nameApplianceMap;
    }
    
    public static HashMap<Integer, ArrayList<Event>> getEventDetailsFromFile(String filePath) throws IOException
    {
           BufferedReader br;
           HashMap<Integer, ArrayList<Event>> hrEventsMap = new HashMap<Integer, ArrayList<Event>>();
            ArrayList<Event> events = new ArrayList<Event>();
            
            br = new BufferedReader(new FileReader(filePath));                                                         

            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
        
            while (line != null) {
                String inputSeperated[] = line.split(",");
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
                
                if(hrEventsMap.containsKey(new Integer(inputSeperated[2])))
                    events = hrEventsMap.get(new Integer(inputSeperated[2]));
                else
                    events = new ArrayList<Event>();
                
                if(inputSeperated.length >= 2)
                {
                    if(inputSeperated[1].equals("OFF"))
                        events.add(new Event(inputSeperated[0], Status.OFF, new Integer(inputSeperated[2]), 0));
                    else
                        events.add(new Event(inputSeperated[0], Status.ON, new Integer(inputSeperated[2]), new Integer(inputSeperated[3])));          
                }
                hrEventsMap.put(new Integer(inputSeperated[2]), events);
            }
            
            return hrEventsMap;
        
    }
}
