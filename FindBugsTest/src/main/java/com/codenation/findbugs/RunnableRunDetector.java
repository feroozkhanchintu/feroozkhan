package com.codenation.findbugs;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.BytecodeScanningDetector;
import edu.umd.cs.findbugs.Priorities;
import edu.umd.cs.findbugs.bcel.OpcodeStackDetector;
import edu.umd.cs.findbugs.classfile.*;
import edu.umd.cs.findbugs.classfile.analysis.ClassNameAndSuperclassInfo;

/**
 * Created by Ferooz on 22/07/16.
 */
public class RunnableRunDetector extends BytecodeScanningDetector {

    private BugReporter reporter;

    public RunnableRunDetector(BugReporter reporter) {
        this.reporter = reporter;
    }

    public void sawMethod() {
        MethodDescriptor invokedMethod = getMethodDescriptorOperand();
        ClassDescriptor invokedClass = getClassDescriptorOperand();

        Class<?> classType = null;
        try {
            classType = Class.forName(invokedClass.getXClass().toString().replaceAll("/","."));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (CheckedAnalysisException e) {
            e.printStackTrace();
        }


        if (invokedMethod != null && "run".equals(invokedMethod.getName())
                &&  Runnable.class.isAssignableFrom(classType) && !Thread.class.isAssignableFrom(classType))
                reporter.reportBug(
                        new BugInstance("THREAD_RUN_BUG_2", Priorities.NORMAL_PRIORITY)
                                .addClassAndMethod(this).addSourceLine(this));

    }


}


