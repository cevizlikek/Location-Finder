package com.keyboard.keyboardthemes;

import android.view.inputmethod.EditorInfo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

interface Img_My_Keyboard_Line {

    public void onText(CharSequence text);
    public void updateShiftKeyState(EditorInfo attr);
    public EditorInfo getCurrentInputEditorInfo();
}

public abstract class Img_My_Keyboard_Base {
    private static final String TAG = "HK/Img_My_Keyboard_Base";
    
    protected static final Map<String, String> mMap =
    	new HashMap<String, String>();

    protected static final Set<String> mPrefixes =
    	new HashSet<String>();

    protected static String get(String key) {
        if (key == null || key.length() == 0) {
            return null;
        }
        return mMap.get(key);
    }

    private static String showString(String in) {
        // TODO Auto-generated method stub
        StringBuilder bulider = new StringBuilder(in);
        bulider.append("{");
        for (int i = 0; i < in.length(); ++i) {
            if (i > 0) bulider.append(",");
            bulider.append((int) in.charAt(i));
        }
        bulider.append("}");
        return bulider.toString();
    }

    private static boolean isValid(String valide) {
        if (valide == null || valide.length() == 0) {
            return false;
        }
        return mPrefixes.contains(valide);
    }

    protected static void put(String key, String value) {
    	mMap.put(key, value);
    	for (int i = 1; i < key.length(); ++i) {
    		mPrefixes.add(key.substring(0, i));
    	}
    }

    protected StringBuilder composeBuffer = new StringBuilder(10);
    protected Img_My_Keyboard_Line composeUser;

    protected void init(Img_My_Keyboard_Line user) {
        clear();
        composeUser = user;
    }

    public void clear() {
        composeBuffer.setLength(0);
    }

    public void bufferKey(char code) {
    	composeBuffer.append(code);
    }

    public String executeToString(int execute) {
        Typing_Switch ks = Typing_Switch.getInstance();
        if (ks.getInputView().isShiftCaps() && ks.isAlphabetMode() && Character.isLowerCase(execute)) {
            execute = Character.toUpperCase(execute);
        }
        bufferKey((char) execute);
        composeUser.updateShiftKeyState(composeUser.getCurrentInputEditorInfo());

        String buffer = get(composeBuffer.toString());
        if (buffer != null) {
            return buffer;
        } else if (!isValid(composeBuffer.toString())) {
            return "";
        }
        return null;
    }

    public boolean execute(int code) {
        String tostring = executeToString(code);
        if (tostring != null) {
            clear();
            composeUser.onText(tostring);
            return false;
        }
        return true;
    }

    public boolean execute(CharSequence sequence) {
        int i, len = sequence.length();
        boolean result = true;
        for (i = 0; i < len; ++i) {
            result = execute(sequence.charAt(i));
        }
        return result;
    }
}
