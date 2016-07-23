package com.codenation.findbugs;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.BytecodeScanningDetector;
import edu.umd.cs.findbugs.Priorities;
import edu.umd.cs.findbugs.classfile.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Ferooz on 22/07/16.
 */
public class ThreadRunDetector extends BytecodeScanningDetector {

    private BugReporter reporter;
    private static final ClassDescriptor THREAD_CLASS =
            DescriptorFactory.createClassDescriptor(Thread.class);

    public ThreadRunDetector(BugReporter reporter) {
        this.reporter = reporter;
    }

    public void sawMethod() {
        MethodDescriptor invokedMethod = getMethodDescriptorOperand();
        ClassDescriptor invokedClass = getClassDescriptorOperand();
        ClassDescriptor classDescriptor = getClassDescriptor();

        Class<?> classType = null;

        try {
            classType = Class.forName(invokedClass.getXClass().toString().replaceAll("/","."));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (CheckedAnalysisException e) {
            e.printStackTrace();
        }

        if (invokedMethod != null && "run".equals(invokedMethod.getName()) &&
                Thread.class.isAssignableFrom(classType)) {
            reporter.reportBug(
                    new BugInstance("THREAD_RUN_BUG_1", Priorities.HIGH_PRIORITY)
                            .addClassAndMethod(this).addSourceLine(this));

        }
    }


}



