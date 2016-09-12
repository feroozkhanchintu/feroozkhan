import java.lang.instrument.Instrumentation;

/**
 * Created by Ferooz on 20/07/16.
 */
public class Agent {
    public static void premain(String agentArgs, Instrumentation inst){
        System.out.println("Starting the agent");
        inst.addTransformer(new LogClassTransformer());
    }
}
