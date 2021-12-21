
package com.keyboard.keyboardthemes;

import java.text.Normalizer;

public class Set_Symbole_Keybord extends Img_My_Keyboard_Base {

    private static final String TAG = "HK/DeadAccent";
    public Set_Symbole_Keybord(Img_My_Keyboard_Line user) {
        init(user);
    }
    
    private static void putAccent(String nonSpacing, String spacing, String stringvalue) {
        if (stringvalue == null) stringvalue = spacing;
        put("" + nonSpacing + " ", stringvalue);
        put(nonSpacing + nonSpacing, spacing);
        put(Keyboard.DEAD_KEY_PLACEHOLDER + nonSpacing, spacing);
    }
    
    public static String getSpacing(char nonSpacing) {
        String jagya = get("" + Keyboard.DEAD_KEY_PLACEHOLDER + nonSpacing);
        if (jagya == null) jagya = Set_Symbole_Keybord.normalize(" " + nonSpacing);
        if (jagya == null) return "" + nonSpacing;
        return jagya;
    }
    
    static {
        putAccent("\u0300", "\u02cb", "`");
        putAccent("\u0301", "\u02ca", "´");
        putAccent("\u0302", "\u02c6", "^");
        putAccent("\u0303", "\u02dc", "~");
        putAccent("\u0304", "\u02c9", "¯");
        putAccent("\u0305", "\u00af", "¯");
        putAccent("\u0306", "\u02d8", null);
        putAccent("\u0307", "\u02d9", null);
        putAccent("\u0308", "\u00a8", "¨");
        putAccent("\u0309", "\u02c0", null);
        putAccent("\u030a", "\u02da", "°");
        putAccent("\u030b", "\u02dd", "\"");
        putAccent("\u030c", "\u02c7", null);
        putAccent("\u030d", "\u02c8", null);
        putAccent("\u030e", "\"", "\"");
        putAccent("\u0313", "\u02bc", null);
        putAccent("\u0314", "\u02bd", null);

        put("\u0308\u0301\u03b9", "\u0390");
        put("\u0301\u0308\u03b9", "\u0390");
        put("\u0301\u03ca", "\u0390");
        put("\u0308\u0301\u03c5", "\u03b0");
        put("\u0301\u0308\u03c5", "\u03b0");
        put("\u0301\u03cb", "\u03b0");


   }
	
    public static String normalize(String input) {
    	String normal = mMap.get(input);
    	if (normal != null) return normal;
    	return Normalizer.normalize(input, Normalizer.Form.NFC);
    }
    
    public boolean execute(int code) {
    	String sexecut = executeToString(code);
    	if (sexecut != null) {
    		if (sexecut.equals("")) {
    			int name = composeBuffer.codePointAt(composeBuffer.length() - 1);
    			if (Character.getType(name) != Character.NON_SPACING_MARK) {
    				composeBuffer.reverse();
    				sexecut = Normalizer.normalize(composeBuffer.toString(), Normalizer.Form.NFC);
    				if (sexecut.equals("")) {
    					return true;
    				}
    			} else {
    				return true;
    			}
    		}

    		clear();
    		composeUser.onText(sexecut);
    		return false;
    	}
    	return true;
    }
}
