
package com.keyboard.keyboardthemes;


import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

import com.keyboard.keyboardthemes.Dictionary.DataType;

public class My_Keybord_Imglong implements SharedPreferences.OnSharedPreferenceChangeListener {

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    }

    public static void init(Context context) {
    }

    public static void commit() {
    }

    public static void onDestroy() {
    }

    public static void logOnManualSuggestion(
            String before, String after, int position, List<CharSequence> suggestions) {
   }

    public static void logOnAutoSuggestion(String before, String after) {
    }

    public static void logOnAutoSuggestionCanceled() {
    }

    public static void logOnDelete() {
    }

    public static void logOnInputChar() {
    }

    public static void logOnException(String metaData, Throwable e) {
    }

    public static void logOnWarning(String warning) {
    }

    public static void onStartSuggestion(CharSequence previousWords) {
    }

    public static void onAddSuggestedWord(String word, int typeId, DataType dataType) {
    }

    public static void onSetKeyboard(Keyboard kb) {
    }

}
