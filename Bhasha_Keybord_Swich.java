package com.keyboard.keyboardthemes;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.util.Locale;

public class Bhasha_Keybord_Swich {

    private static final String TAG_bhasha = "HK/Bhasha_Keybord_Swich";
    private Locale[] bhashalocal;
    private LatinIME latinme;
    private String[] bhashaarray;
    private String choisebhasha;
    private int exitevalue = 0;
    private String inbhasha;
    private Locale inlocal;
    private Locale localsytem;

    public Bhasha_Keybord_Swich(LatinIME keyword) {
        latinme = keyword;
        bhashalocal = new Locale[0];
    }

    public Locale[]  getLocales() {
        return bhashalocal;
    }

    public int getLocaleCount() {
        return bhashalocal.length;
    }

    public boolean loadLocales(SharedPreferences CONTENT_sp) {
        String stringbhasha = CONTENT_sp.getString(LatinIME.PREF_SELECTED_LANGUAGES, null);
        String stringinbhasha   = CONTENT_sp.getString(LatinIME.PREF_INPUT_LANGUAGE, null);
        if (stringbhasha == null || stringbhasha.length() < 1) {
            loadDefaults();
            if (bhashalocal.length == 0) {
                return false;
            }
            bhashalocal = new Locale[0];
            return true;
        }
        if (stringbhasha.equals(choisebhasha)) {
            return false;
        }
        bhashaarray = stringbhasha.split(",");
        choisebhasha = stringbhasha;
        constructLocales();
        exitevalue = 0;
        if (stringinbhasha != null) {
            exitevalue = 0;
            for (int i = 0; i < bhashalocal.length; i++) {
                if (bhashaarray[i].equals(stringinbhasha)) {
                    exitevalue = i;
                    break;
                }
            }
        }
        return true;
    }

    private void loadDefaults() {
        inlocal = latinme.getResources().getConfiguration().locale;
        String words = inlocal.getCountry();
        inbhasha = inlocal.getLanguage() + (TextUtils.isEmpty(words) ? "" : "_" + words);
    }

    private void constructLocales() {
        bhashalocal = new Locale[bhashaarray.length];
        for (int i = 0; i < bhashalocal.length; i++) {
            final String lang = bhashaarray[i];
            bhashalocal[i] = new Locale(lang.substring(0, 2), lang.length() > 4 ? lang.substring(3, 5) : "");
        }
    }

    public String getInputLanguage() {
        if (getLocaleCount() == 0) return inbhasha;
        return bhashaarray[exitevalue];
    }

    public boolean allowAutoCap() {
        String autocap = getInputLanguage();
        if (autocap.length() > 2) autocap = autocap.substring(0, 2);
        return !My_Keyboard_Set_Bhasha.NOCAPS_LANGUAGES.contains(autocap);
    }
    
    public boolean allowDeadKeys() {
        String deadkeys = getInputLanguage();
        if (deadkeys.length() > 2) deadkeys = deadkeys.substring(0, 2);
        return !My_Keyboard_Set_Bhasha.NODEADKEY_LANGUAGES.contains(deadkeys);
    }
    
    public boolean allowAutoSpace() {
        String autospace = getInputLanguage();
        if (autospace.length() > 2) autospace = autospace.substring(0, 2);
        return !My_Keyboard_Set_Bhasha.NOAUTOSPACE_LANGUAGES.contains(autospace);
    }
    
    public String[] getEnabledLanguages() {
        return bhashaarray;
    }

    public Locale getInputLocale() {
        Locale imgloc;
        if (getLocaleCount() == 0) {
            imgloc = inlocal;
        } else {
            imgloc = bhashalocal[exitevalue];
        }
        LatinIME.service.defultevalue = (imgloc != null) ? imgloc : Locale.getDefault();
        return imgloc;
    }

    public Locale getNextInputLocale() {
        if (getLocaleCount() == 0) return inlocal;
        return bhashalocal[(exitevalue + 1) % bhashalocal.length];
    }

    public void setSystemLocale(Locale locale) {
        localsytem = locale;
    }

    public Locale getSystemLocale() {
        return localsytem;
    }

    public Locale getPrevInputLocale() {
        if (getLocaleCount() == 0) return inlocal;

        return bhashalocal[(exitevalue - 1 + bhashalocal.length) % bhashalocal.length];
    }

    public void reset() {
        exitevalue = 0;
        choisebhasha = "";
        loadLocales(PreferenceManager.getDefaultSharedPreferences(latinme));
    }

    public void next() {
        exitevalue++;
        if (exitevalue >= bhashalocal.length) exitevalue = 0;
    }

    public void prev() {
        exitevalue--;
        if (exitevalue < 0) exitevalue = bhashalocal.length - 1;
    }

    public void persist() {
        SharedPreferences refernce = PreferenceManager.getDefaultSharedPreferences(latinme);
        Editor edit = refernce.edit();
        edit.putString(LatinIME.PREF_INPUT_LANGUAGE, getInputLanguage());
        Uplode_Refernce_Keyword.apply(edit);
    }

    static String toTitleCase(String title) {
        if (title.length() == 0) {
            return title;
        }

        return Character.toUpperCase(title.charAt(0)) + title.substring(1);
    }
}
