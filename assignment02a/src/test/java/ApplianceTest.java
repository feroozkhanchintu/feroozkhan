import static org.junit.Assert.assertEquals;
import org.junit.Test;
import com.codenation.assingment02.*;

public class ApplianceTest {

@Test
public void testInitStatus()
{
    Appliance app = new Appliance("TV", 10);
    
    assertEquals(Status.OFF, app.getStatus());
    assertEquals("TV", app.getName());
    assertEquals(10, app.getDefaultTime());
    
}

@Test 
public void testTurnON()
{
    Appliance app = new Appliance("OVEN", 20);
    app.turnON();
    assertEquals(Status.ON, app.getStatus());
}

@Test
public void testTurnOFF()
{
    Appliance app = new Appliance("AC", 12);
    app.turnON();
    assertEquals(Status.ON, app.getStatus());
    app.turnOFF();
    assertEquals(Status.OFF, app.getStatus());
}

@Test
public void testUpdateStatus()
{
    Appliance app = new Appliance("AC", 2);
    app.turnON();
    
    app.init(1, 5);
    app.updateStatus(2);
    assertEquals(Status.OFF, 0);
    
}

}

