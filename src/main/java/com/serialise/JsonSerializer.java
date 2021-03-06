package com.serialise;

import com.pack.fields.FieldClass;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public final  class JsonSerializer {

    private static final String arrs="(\\[(("+RegularExpressions.stroka+"|"+RegularExpressions.primitive+"),?)*])";
    private  static final String valueOfField = "("+arrs+"|"+RegularExpressions.stroka+"|"+RegularExpressions.primitive+")";
    private  static final String nameObj="([A-Za-z_.]([A-Za-z_.\\d])*)";
    private  static final String nameField="([A-Za-z_]([A-Za-z_\\d])*)";
    private  static final String objectPattern="\\{"+nameObj+"(\""+nameField+"\":"+valueOfField+",?)*}";


    public static String Serialize(Object obj) throws IllegalAccessException {

        var cls= obj.getClass();
        List<Field> allFields = FieldClass.getAllFields(cls);
        String str="{"+cls.getName();


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
                res=res.substring(0,res.length()-2);
                res+="\"]";
            }
            else if (typ.isArray() && typ.getComponentType().isPrimitive())
            {
                int length = Array.getLength(valField);
                res="[";
                for (int i = 0; i < length; i++) {
                    res+=Array.get(valField, i).toString()+',';
                }
                res=res.substring(0,res.length()-1);
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
    private static int RecursiveFunc(int i, String str, List values,Class typeOfField) throws Exception {

        switch (str.charAt(i)) {
            case '[': //????????????
            {
                while (str.charAt(i) != ']') {
                    i++;
                    i=RecursiveFunc(i,str,values,typeOfField);
                }
                i++;  //?????????????? ???? ?????????????? ?????? }
                break;
            }
            case '"': //???????????? ????????????
            {   AtomicInteger a = new AtomicInteger();
                ++i;
                a.set(i);
                String  val=HighlightWord(a, str);
                i=a.get();
                ++i;
                values.add(val);


                break;

            }
            default:    //?????????? ?????? ????????????????
            {   String val="";

                while ((str.charAt(i) != ',') && (str.charAt(i) != ']') && (str.charAt(i) != '}')) //?????????????????? ???? ???????????????????? - ?????? ???????????????? ?????? ???????? ????????????,?????????? ??????????????, ?????????? ??????????????
                {
                    val+=str.charAt(i);
                    ++i;
                }


                Object nval=null;


                if (typeOfField.equals(long.class)) //?????????????? "??????????????????????" ???????????????????? ?????????? java
                {

                    long lng = Long.parseLong(val);
                    if (lng<0)
                        throw new Exception("");
                    nval= lng;
                }
                else if(typeOfField.equals(int.class))
                {

                    int lng = Integer.parseInt(val);
                    if (lng<0)
                        throw new Exception("");
                    nval=lng;
                }
                else if(typeOfField.equals(short.class))
                {
                    short lng = Short.parseShort(val);
                    if (lng<0)
                        throw new Exception("");
                    nval=lng;
                }
                else if(typeOfField.equals(byte.class))
                {
                    byte lng = Byte.parseByte(val);
                    if (lng<0)
                        throw new Exception("");
                    nval=lng;
                }
                else if(typeOfField.equals(double.class))
                {

                    double lng = Double.parseDouble(val);
                    if (lng<0)
                        throw new Exception("");
                    nval=lng;

                }
                else if(typeOfField.equals(float.class))
                {
                    float lng = Float.parseFloat(val);
                    if (lng<0)
                        throw new Exception("");
                    nval=lng;
                }
                else nval=Boolean.parseBoolean(val);
                values.add(nval);



            }

        }
        return i;
    }
    public static Object Deserialize(String str, Set<URL> setUrl)
    {   try
    {
        str=str.trim();
        if (!str.matches(objectPattern))
            return null;

        int i = 1;
        AtomicInteger a = new AtomicInteger();
        a.set(i);
        String  objectName=HighlightWord(a, str); //???????????????? ?????? ????????????
        i=a.get();
        URLClassLoader urlClassLoader=new URLClassLoader(setUrl.toArray(new URL[0]));
        Class cls=urlClassLoader.loadClass(objectName);
        var clsInstance = cls.getDeclaredConstructor().newInstance();

        //
        List<Field> allFields = FieldClass.getAllFields(cls);//???????????? ???????? ??????????

        ++i;
        Field fld=null;
        List values = new ArrayList<>();
        while (i<str.length()) //???????? ???? ?????????? ???????????????? ????????????
        {  a.set(i);
            String  nameOfField=HighlightWord(a, str); //?????? ????????
            boolean flag=false;



            for (Field field:allFields)
            {
                if (nameOfField.equals(field.getName())) {
                    fld=field;
                    flag=true;
                    break;
                }
            }
            if (flag==false) //???????? ?????? - ???? ?????????????? ????????????
                return null;
            i=a.get();
            i+=2;
            fld.setAccessible(true);
            var typeOfField=fld.getType();
            if (typeOfField.isArray())
                typeOfField=typeOfField.getComponentType();
            i=RecursiveFunc(i,str,values,typeOfField); //???????????????? ??????????
            if (values.size()==1) //String or primitive
            {

                fld.set(clsInstance,values.get(0));
            }
            else {//is Array
                Object mArr =Array.newInstance(typeOfField, values.size());
                for (int j = 0; j < values.size(); j++) {
                    // value = ....
                    Array.set(mArr, j, values.get(j));
                }
                fld.set(clsInstance,mArr);
            }
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


