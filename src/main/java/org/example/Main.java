package org.example;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        String str = "Test";

        // 정적
        Test test = new Test();
        test.setTestStr(str);
        System.out.println("정적 : " + test.getTestStr());

        // 동적
        Test test2 = new Test();
        dynamicSetterMethod(test2, "testStr", str);
        Object testStr = dynamicGetterMethod(test2, "testStr");
        System.out.println("동적 : " + testStr);

        // 인스턴스 변수 정적
        Test2 test3 = new Test2();
        test3.testStr = str;
        System.out.println("정적 인스턴스변수 : " + test3.testStr);

        // 인스턴스 변수 동적
        Test2 test4 = new Test2();
        dynamicSetterInstanceVar(test4, "testStr", str);
        Object testStr1 = dynamicGetterInstanceVar(test4, "testStr");
        System.out.println(testStr1);
    }

    public static void dynamicSetterMethod(Object obj, String key, String value){
        try {
            Class<?> aClass = obj.getClass();
            String methodName = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
            Method method = aClass.getMethod(methodName, String.class);
            method.invoke(obj, value);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object dynamicGetterMethod(Object obj, String key) {
        Object result = null;
        try {
            Class<?> aClass = obj.getClass();
            String methodName = "get" + key.substring(0, 1).toUpperCase() + key.substring(1);
            Method method = aClass.getMethod(methodName);
            result = method.invoke(obj);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static void dynamicSetterInstanceVar(Object obj, String key, String value){
        try {
            Class<?> aClass = obj.getClass();
            Field field = aClass.getDeclaredField(key);
            field.setAccessible(true);
            field.set(obj, value);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Object dynamicGetterInstanceVar(Object obj, String key){
        Object result = null;
        try {
            Class<?> aClass = obj.getClass();
            Field field = aClass.getDeclaredField(key);
            result = field.get(obj);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}