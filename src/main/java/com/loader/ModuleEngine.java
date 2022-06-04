package com.loader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ModuleEngine {

    private static final Properties properties = new Properties();

    public static List<Class> updateModules(String modulePath) throws IOException {
        ModuleLoader loader = new ModuleLoader(modulePath, ClassLoader.getSystemClassLoader());
        File dir = new File(modulePath);
        String[] modules = dir.list();
        List<Class> classes= new ArrayList<>();
        if (modules != null) {
            for (String module: modules) {
                try {
                    Class currLoadingClass = loader.loadClass(module);
                    classes.add(currLoadingClass);

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return classes;
    }
}
