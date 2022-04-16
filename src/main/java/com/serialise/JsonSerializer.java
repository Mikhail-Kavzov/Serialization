package com.serialise;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import static java.util.stream.Collectors.toList;

public final class JsonSerializer {

    private static final String arrs="(\\[(("+RegularExpressions.stroka+"|"+RegularExpressions.primitive+"),?)*])";
    private  static final String valueOfField = "("+arrs+"|"+RegularExpressions.stroka+"|"+RegularExpressions.primitive+")";
    private  static final String nameObj="([A-Za-z_.]([A-Za-z_.\\d])*)";
    private  static final String nameField="([A-Za-z_]([A-Za-z_\\d])*)";
    private  static final String objectPattern="\\{"+nameObj+"(\""+nameField+"\":"+valueOfField+",?)*}";




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




    public static String Serialize(Object obj) throws IllegalAccessException {

        var cls= obj.getClass();
        List<Field> allFields = Arrays.stream(cls.getDeclaredFields()).collect(toList());
        String str="{"+cls.getName();
        allFields.addAll(getPublicProtectedFields(cls.getSuperclass()));

        for (Field field:allFields)
        {
            field.setAccessible(true);
            String res;
            var typ=field.getType();
            var valField = field.get(obj);

            if (typ.equals(String.class)) // -_-
            {
                res="\""+valField+"\"";
            }
            else if (typ.equals(String[].class))
            {    String[] arr = (String[]) valField;
                res="[";
                for (String val:arr)
                {
                    res+="\""+val+"\",";
                }
                res=res.substring(0,res.length()-3);
                res+="\"]";
            }
            else if (typ.isArray() && typ.getComponentType().isPrimitive())
            {
                int length = Array.getLength(valField);
                res="[";
                for (int i = 0; i < length; i++) {
                    res+=Array.get(valField, i).toString()+',';
                }
                res=res.substring(0,res.length()-2);
                res+="]";
            }
            else res=field.get(obj).toString();

            str+= "\""+field.getName()+"\":"+res+",";
        }
        str=str.substring(0,str.length()-1);
        str+="}\n";
        return str;
    }


    private static String HighlightWord(AtomicInteger i, String str)
    {   String word="";
        while (str.charAt(i.get()) != '"') {
            word += str.charAt(i.get());
            i.set(i.get()+1);
        }
        return word;
    }
    private static int RecursiveFunc(int i, String str, List values,Class typeOfField) {

        switch (str.charAt(i)) {
            case '[': //массив
            {
                while (str.charAt(i) != ']') {
                    i++;
                    i=RecursiveFunc(i,str,values,typeOfField);
                }
                i++;  //позиция на запятой или }
                break;
            }
            case '"': //начало строки
            {   AtomicInteger a = new AtomicInteger();
                ++i;
                a.set(i);
                String  val=HighlightWord(a, str);
                i=a.get();
                ++i;
                values.add(val);


                break;

            }
            default:    //иначе это примитив
            {   String val="";

                while ((str.charAt(i) != ',') && (str.charAt(i) != ']') && (str.charAt(i) != '}')) //следующее за примитивом - это примитив или поле класса,конец массива, конец объекта
                {
                    val+=str.charAt(i);
                    ++i;
                }


                Object nval=null;


                if (typeOfField.equals(long.class)) //спасибо "нормальному" приведению типов java
                {
                    nval=Long.parseLong(val);
                }
                else if(typeOfField.equals(int.class))
                {
                    nval=Integer.parseInt(val);
                }
                else if(typeOfField.equals(short.class))
                {
                    nval=Short.parseShort(val);
                }
                else if(typeOfField.equals(byte.class))
                {
                    nval=Byte.parseByte(val);
                }
                else if(typeOfField.equals(double.class))
                {
                    nval=Double.parseDouble(val);
                }
                else if(typeOfField.equals(float.class))
                {
                    nval=Float.parseFloat(val);
                }
                else nval=Boolean.parseBoolean(val);
                values.add(nval);


            }

        }
        return i;
    }
    public static Object Deserialize(String str)
    {   try
    {
        str=str.trim();
        if (!str.matches(objectPattern))
            return null;

        int i = 1;
        AtomicInteger a = new AtomicInteger();
        a.set(i);
        String  objectName=HighlightWord(a, str); //выделяем имя класса
        i=a.get();
        Class cls = Class.forName(objectName); //получаем класс
        var clsInstance = cls.getDeclaredConstructor().newInstance();

        //
        List<Field> allFields = Arrays.stream(cls.getDeclaredFields()).collect(toList());
        allFields.addAll(getPublicProtectedFields(cls.getSuperclass())); //список всех полей

        ++i;
        Field fld=null;
        List values = new ArrayList<>();
        while (i<str.length()) //пока не конец исходной строки
        {  a.set(i);
            String  nameOfField=HighlightWord(a, str); //имя поля
            boolean flag=false;



            for (Field field:allFields)
            {
                if (nameOfField.equals(field.getName())) {
                    fld=field;
                    flag=true;
                    break;
                }
            }
            if (flag==false) //поля нет - не создаем объект
                return null;
            i=a.get();
            i+=2;
            fld.setAccessible(true);
            var typeOfField=fld.getType();
            if (typeOfField.isArray())
                typeOfField=typeOfField.getComponentType();
            i=RecursiveFunc(i,str,values,typeOfField); //значения полей
            if (values.size()==1) //String or primitive
            {

                fld.set(clsInstance,values.get(0));
            }
            else //is Array
                fld.set(clsInstance, values.toArray((Object[]) Array.newInstance(typeOfField, values.size())));
            values.clear();
            i+=2;
        }
        return clsInstance;
    }
    catch (Exception ex)
    {
        return null;
    }

    }
}
