import javassist.*;
import javassist.bytecode.MethodInfo;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.Modifier;
import java.security.ProtectionDomain;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Ferooz on 20/07/16.
 */
public class LogClassTransformer implements ClassFileTransformer {


    public byte[] transform(ClassLoader loader, String className,
                            Class classBeingRedefined, ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {

        if (className.contains("java") || className.contains("sun"))
            return classfileBuffer;

        ClassPool classPool = ClassPool.getDefault();
        try {

           // if(className.contains("com/codenation/ecommerce/controller"))
            {

                CtClass ctClass = classPool.getCtClass(className.replaceAll("/", "."));
               // ctClass.addField(CtField.make("public static java.util.logging.Logger logger1 = java.util.logging.Logger.getLogger(\"LOGGER\");", ctClass));
                CtBehavior[] methods = ctClass.getDeclaredBehaviors();
                for (int i = 0; i < methods.length; i++) {
                    System.out.println(methods[i].getLongName());
                    if (methods[i].isEmpty() == false) {
                        changeMethod(methods[i]);
                    }
                }
                classfileBuffer = ctClass.toBytecode();
                ctClass.detach();
                return classfileBuffer;
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classfileBuffer;

    }

    private void changeMethod(CtBehavior method) throws NotFoundException, CannotCompileException {
        String methodName = method.getLongName();
        method.insertBefore("{for (int i=0; i < $args.length; i++) {System.out.println($args[i]);}}");
        method.insertBefore("{System.out.println(\"Entering " + methodName + "\");}");
        method.insertAfter("{System.out.println(\"Returning \" + $_);}");
        method.insertAfter("{System.out.println(\"Exiting " + methodName + "\");}");
        method.insertAfter("{System.out.println();}");

    }

    private void changeMethod1(CtBehavior method) throws NotFoundException, CannotCompileException {
        String methodName = method.getLongName();

        method.insertBefore("String str = \"\"; " +
                            "for (int i=0; i < $args.length; i++) str += $args[i].toString() + \" \";" +
                            "logger1.info(\"PARAMETER VALUES  :\" + str + \"\");");

        method.insertBefore("logger1.info(\"ENTERING : " + methodName + "\");");

        method.insertAfter("{logger1.info(\"RETURNING : \" + $_);}");
        method.insertAfter("{logger1.info(\"EXITING : " + methodName + "\");}");
        method.insertAfter("{System.out.println(\"\\n\");}");

    }
}
