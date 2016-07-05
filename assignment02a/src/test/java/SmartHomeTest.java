import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import org.junit.Test;

import com.codenation.assingment02.*;

public class SmartHomeTest {
  

  @Test(expected=FileNotFoundException.class)
  public void evaluatesExpression() throws FileNotFoundException{
    
        SmartHome smartHome = new SmartHome();
        
        String fileName = "abc.txt";
        
        FileReader reader = new FileReader(fileName);
        
    }
    
    @Test(expected=IndexOutOfBoundsException.class)
public void testIndexOutOfBoundsException() {
    ArrayList emptyList = new ArrayList();
    Object o = emptyList.get(0);
}
}
