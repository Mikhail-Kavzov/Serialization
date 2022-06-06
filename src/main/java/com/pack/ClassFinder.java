package com.pack;

import com.pack.fields.FieldClass;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ClassFinder {
    private String path;
    private final int javaExt=6;
    public ClassFinder(String path)
    {
        this.path=path;
    }
    public Set<String> getFileName()
    {
        File ClassPathList=new File(path);
        File[] classesInDirectory=ClassPathList.listFiles();
        Set<String> javaClasses=new LinkedHashSet<>();

        for (var ClassFile:classesInDirectory )
        {   String temp=ClassFile.getName();
            javaClasses.add(temp.substring(0,temp.length()-javaExt));
        }
        return javaClasses;
    }
    public Set<Class> getNewClasses(Set<String>javaClasses, URLClassLoader urlLoader) throws ClassNotFoundException {
        Set<Class>classOfDerivate= new LinkedHashSet<>();
        for (String cl:javaClasses)
        {
            classOfDerivate.add(urlLoader.loadClass(cl));
        }
        return classOfDerivate;
    }
    public Set<Field> getFieldsClasses(Set<Class> classOfDerivate)
    {
        Set<Field> uniqueFields = new LinkedHashSet<>();

        for (Class cls: classOfDerivate)
        {
            uniqueFields.addAll(FieldClass.getAllFields(cls));

        }
        return uniqueFields;
    }
}
