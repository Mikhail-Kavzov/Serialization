package com.pack.fields;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

import static java.util.stream.Collectors.toList;

public class FieldClass {

    private static List<Field> getPublicProtectedFields(Class cls) {
        if (cls == null) {
            return Collections.emptyList();
        }
        List<Field> result = new ArrayList<>(getPublicProtectedFields(cls.getSuperclass()));
        List filteredFields = Arrays.stream(cls.getDeclaredFields())
                .filter(f -> Modifier.isPublic(f.getModifiers()) || Modifier.isProtected(f.getModifiers())).toList();
        result.addAll(filteredFields);
        return result;
    }

    public static List<Field> getAllFields(Class cls)
    {
        List<Field> allFields = Arrays.stream(cls.getDeclaredFields()).collect(toList());
        allFields.addAll(getPublicProtectedFields(cls.getSuperclass()));
        return allFields;
    }

    public static Set<String> getFieldsName(Set<Field> fields)
    {
        Set<String> fieldsName= new LinkedHashSet<>();
        for (var fld:fields)
        {
            fieldsName.add(fld.getName());
        }
        return fieldsName;
    }

}
