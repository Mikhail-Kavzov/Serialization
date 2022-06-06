package com.serialise;

public class RegularExpressions {

    public static final String intType="(\\d+)";
    public static final String boolType="((true)|(false))";
    public static final String floatType="((\\d+)\\.(\\d+))";
    public static final String primitive = "("+intType+"|"+boolType+"|"+floatType+")";
    public static final String stroka = "((.)*)";
    public static final String arraysOfString="\\[("+stroka+"(, )?)*]";
    public static final String arraysOfInt="\\[("+intType+",?(,\\s)?)*]";
    public static final String arraysOfFloat="\\[("+floatType+"(,\\s)?)*]";
    public static final String arraysOfBool="\\[("+boolType+",?(,\\s)?)*]";

}
