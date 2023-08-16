package org.example;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        String str = "Test";

        Test test = new Test();
        test.setTestStr(str);
        System.out.println(test.getTestStr());

        // 동적
        Test test2 = new Test();
        dynamicSetterMethod(test2, "testStr", str);
        Object testStr = dynamicGetterMethod(test2, "testStr");
        System.out.println(testStr);
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
}