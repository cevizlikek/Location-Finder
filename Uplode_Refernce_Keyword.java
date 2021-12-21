
package com.keyboard.keyboardthemes;

import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Uplode_Refernce_Keyword {

    private static final Method enterrefrence = findApplyMethod();

    private static Method findApplyMethod() {
        try {
            return SharedPreferences.Editor.class.getMethod("apply");
        } catch (NoSuchMethodException unused) {
        }
        return null;
    }

    public static void apply(SharedPreferences.Editor maply) {
        if (enterrefrence != null) {
            try {
                enterrefrence.invoke(maply);
                return;
            } catch (InvocationTargetException unused) {
            } catch (IllegalAccessException unused) {
            }
        }
        maply.commit();
    }
}
