import static org.junit.Assert.assertEquals;
import org.junit.Test;

import com.codenation.assingment02.*;


public class EventTest {
    
    @Test
    public void testCreateEvent()
    {
        Event event = new Event("TV", Status.ON, 1, 10);
        
        assertEquals("TV", event.getName());
        assertEquals(Status.ON, event.getStatus());
        assertEquals(1, event.getStartTime());
        assertEquals(10, event.getTotalTimeUnits());
        
    }
}

