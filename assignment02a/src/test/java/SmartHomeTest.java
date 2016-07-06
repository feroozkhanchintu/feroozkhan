import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

import org.junit.Test;

import com.codenation.assingment02.*;

public class SmartHomeTest {
  

  @Test
  public void testingEventFile() {
    
        String filePath = "wrongFile.txt";
        
        try{
        SmartHome.getEventDetailsFromFile(filePath);
        
        }
        catch(IOException ex)
        {
            assertTrue(true);
        }
        
        filePath = "/projects/feroozkhan/assignment02a/src/main/resources/testSimulation.txt";
        
        try{
        SmartHome.getEventDetailsFromFile(filePath);
        
        assertTrue(true);
        }
        catch(IOException ex)
        {
            assertFalse(true);
        }
        
    }
    
    @Test(expected=IOException.class)
    public void IOException() throws IOException{
    
        String filePath = "wrongFile.txt";
        SmartHome.getEventDetailsFromFile(filePath);
        
    }
    

    
    @Test
    public void testGetAppliancesDetails()
    {
        String mockInput = "TV 12\n" +
                            "AC 10\n" +
                            "OVEN 4";
        ByteArrayInputStream in = new ByteArrayInputStream(mockInput.getBytes());
        System.setIn(in);
        
        HashMap<String, Appliance> nameApplianceMap = new HashMap<String, Appliance>();
        nameApplianceMap.put("TV", new Appliance("TV",12));
        nameApplianceMap.put("AC", new Appliance("AC",10));            
        nameApplianceMap.put("OVEN", new Appliance("OVEN",4));  
        
        HashMap<String, Appliance> returnedAppliances = SmartHome.getAppliancesDetails(3, new Scanner(System.in));
        
        assertEquals(true, mapsAreEqual(nameApplianceMap, returnedAppliances));
    }
    
    @Test 
    public void testSmartHomeMain()
    {
        String mockInput = "3\n" +
                            "TV\n12\n" +
                            "AC\n10\n" +
                            "OVEN\n4";
        ByteArrayInputStream in = new ByteArrayInputStream(mockInput.getBytes());
        System.setIn(in);
        
        SmartHome.main(null);
    }
    
    public boolean mapsAreEqual(HashMap<String, Appliance> mapA, HashMap<String, Appliance> mapB) {

    try{
        for (String k : mapB.keySet())
        {
            Appliance a1 = (Appliance) mapA.get(k);
            Appliance a2 = (Appliance) mapB.get(k);
            
            if (a1.getDefaultTime() != a2.getDefaultTime()) {
                return false;
            }
        } 
        for (String y : mapA.keySet())
        {
            if (!mapB.containsKey(y)) {
                return false;
            }
        } 
    } catch (NullPointerException np) {
        return false;
    }
    return true;
}
}
