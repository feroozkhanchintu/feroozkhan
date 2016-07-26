import understand
db = understand.open("/Users/Ferooz/CNU2016/MyUnderstandProject1.udb")

for method in db.ents("~Unknown Method"):
    if method.name().endswith("run"):
        called_run_methods = [ref for ref in method.refs('CallBy')]
        defined_run_class = method.ref('DefineIn').ent()
        if 'Anon' not in defined_run_class.name():
            extended_class_list = [ref.ent().name() for ref in defined_run_class.refs('Java Extend Couple')]
            implemented_class_list = [ref.ent().name() for ref in defined_run_class.refs('Java Implement Couple')]
            threadcheck = False
            runnablecheck = False
            for extendedclass in extended_class_list:
                if "Thread" in extendedclass:
                    threadcheck = True
            for implementedclass in implemented_class_list:
                if "Runnable" in implementedclass:
                    runnablecheck = True

            if threadcheck:
                for ref in called_run_methods:
                    run_caller_method = ref.ent()
                    run_file_name = ref.file()
                    run_method_location = (ref.line(), ref.column())
                    print('Method: ', method.longname(), 'in class', defined_run_class.longname(),
                          'which extends Thread is called by:', run_caller_method.longname(),
                          'at (line, col):', run_method_location,
                          'Did you mean to call start() instead?')
            elif runnablecheck:
                for ref in called_run_methods:
                    run_caller_method = ref.ent()
                    caller_extended_class_list = [ref.ent().name() for ref in run_caller_method.refs('Java Extend Couple')]
                    executor_check = False
                    for item in caller_extended_class_list:
                        if "Executor" in item:
                            executorcheck = True
                    if not executorcheck:
                        run_file_name = ref.file()
                        run_method_location = (ref.line(), ref.column())
                        print('Method: ', method.longname(), 'is class', defined_run_class.longname(),
                              'which implements Runnable is called by', run_caller_method.longname(),
                              'at (line, col):', run_method_location,
                              'Did you intend to put run() method in new thread?')