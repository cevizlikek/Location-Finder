package com.keyboard.keyboardthemes;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.keyboard.keyboardthemes.R;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;



public class My_Keyboard_Set_Bhasha extends PreferenceActivity {

    private static final String TAG = "PCKeyboardILS";
    private ArrayList<Loc> allbhasha = new ArrayList<Loc>();

    private static final String[] BLACKLIST_LANGUAGES = {
        "ko", "ja", "zh"
    };

    public static final Set<String> NOCAPS_LANGUAGES = new HashSet<String>();
    static {
        NOCAPS_LANGUAGES.add("ar");
        NOCAPS_LANGUAGES.add("iw");
        NOCAPS_LANGUAGES.add("th");
    }

    public static final Set<String> NODEADKEY_LANGUAGES = new HashSet<String>();
    static {
        NODEADKEY_LANGUAGES.add("ar");
        NODEADKEY_LANGUAGES.add("iw"); // TODO: currently no niqqud in the keymap?
        NODEADKEY_LANGUAGES.add("th");
    }

    public static final Set<String> NOAUTOSPACE_LANGUAGES = new HashSet<String>();
    static {
        NOAUTOSPACE_LANGUAGES.add("th");
    }

    private static final String[] KBD_LOCALIZATIONS = {
        "ar", "bg", "ca", "cs", "cs_QY", "da", "de", "el", "en", "en_DV",
        "en_GB", "es", "es_LA", "es_US", "fa", "fi", "fr", "fr_CA", "he",
        "hr", "hu", "hu_QY", "hy", "in", "it", "iw", "ja", "ka", "ko",
        "lo", "lt", "lv", "nb", "nl", "pl", "pt", "pt_PT", "rm", "ro",
        "ru", "ru_PH", "si", "sk", "sk_QY", "sl", "sr", "sv", "ta", "th",
        "tl", "tr", "uk", "vi", "zh_CN", "zh_TW"
    };

    private static final String[] KBD_5_ROW = {
        "ar", "bg", "cs", "cs_QY", "da", "de", "el", "en", "en_DV", "en_GB",
        "es", "es_LA", "fa", "fi", "fr", "fr_CA", "he", "hr", "hu", "hu_QY",
        "hy", "it", "iw", "lo", "nb", "pt_PT", "ro", "ru", "ru_PH", "si",
        "sk", "sk_QY", "sl", "sr", "sv", "ta", "th", "tr", "uk"
    };

    private static final String[] KBD_4_ROW = {
        "ar", "bg", "cs", "cs_QY", "da", "de", "el", "en", "en_DV", "es",
        "es_LA", "es_US", "fa", "fr", "fr_CA", "he", "hr", "hu", "hu_QY",
        "iw", "nb", "ru", "ru_PH", "sk", "sk_QY", "sl", "sr", "sv", "tr",
        "uk"
    };

    private static String getLocaleName(Locale getname) {
        String size = getname.getLanguage();
        String country = getname.getCountry();
        if (size.equals("en") && country.equals("DV")) {
            return "English (Dvorak)";
        } else if (size.equals("en") && country.equals("EX")) {
                return "English (4x11)";
        } else if (size.equals("es") && country.equals("LA")) {
            return "Español (Latinoamérica)";
        } else if (size.equals("cs") && country.equals("QY")) {
            return "Čeština (QWERTY)";
        } else if (size.equals("hu") && country.equals("QY")) {
            return "Magyar (QWERTY)";
        } else if (size.equals("sk") && country.equals("QY")) {
            return "Sloven�?ina (QWERTY)";
        } else if (size.equals("ru") && country.equals("PH")) {
            return "Ру�?�?кий (Phonetic)";
        } else {
            return Bhasha_Keybord_Swich.toTitleCase(getname.getDisplayName(getname));
        }
    }
    
    private static class Loc implements Comparable<Object> {
        static Collator sCollator = Collator.getInstance();

        String label;
        Locale locale;

        public Loc(String label, Locale locale) {
            this.label = label;
            this.locale = locale;
        }

        @Override
        public String toString() {
            return this.label;
        }

        public int compareTo(Object comper) {
            return sCollator.compare(this.label, ((Loc) comper).label);
        }
    }

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.language_prefs);
        SharedPreferences refrence = PreferenceManager.getDefaultSharedPreferences(this);
        String bhasha = refrence.getString(LatinIME.PREF_SELECTED_LANGUAGES, "");
        Log.i(TAG, "selected languages: " + bhasha);
        String[] languageList = bhasha.split(",");
        allbhasha = getUniqueLocales();
        Set<String> allbhashaad = new HashSet<String>();
        for (int i = 0; i < allbhasha.size(); i++) {
            Locale alphbet = allbhasha.get(i).locale;
            allbhashaad.add(get5Code(alphbet));
        }
        Set<String> bhashacoise = new HashSet<String>();
        for (int i = 0; i < languageList.length; ++i) {
            String leter = languageList[i];
            if (allbhashaad.contains(leter)) {
                bhashacoise.add(leter);
            } else if (leter.length() > 2) {
                String lang = leter.substring(0, 2);
                if (allbhashaad.contains(lang)) bhashacoise.add(lang);
            }
        }

        PreferenceGroup group = getPreferenceScreen();
        for (int i = 0; i < allbhasha.size(); i++) {
            CheckBoxPreference checkbox = new CheckBoxPreference(this);
            Locale bhsha = allbhasha.get(i).locale;
            checkbox.setTitle(allbhasha.get(i).label + " [" + bhsha.toString() + "]");
            String strinngid = get5Code(bhsha);
            String bhashaid = bhsha.getLanguage();
            boolean btrue = bhashacoise.contains(strinngid);
            checkbox.setChecked(btrue);
            boolean fourevarti = arrayContains(KBD_4_ROW, strinngid) || arrayContains(KBD_4_ROW, bhashaid);
            boolean fivevarti = arrayContains(KBD_5_ROW, strinngid) || arrayContains(KBD_5_ROW, bhashaid);
            List<String> history = new ArrayList<String>(3);
            if (fivevarti) history.add("5-row");
            if (fourevarti) history.add("4-row");
            if (hasDictionary(bhsha)) {
                history.add(getResources().getString(R.string.has_dictionary));
            }
            if (!history.isEmpty()) {
                StringBuilder bulder = new StringBuilder();
                for (int j = 0; j < history.size(); ++j) {
                        if (j > 0) bulder.append(", ");
                        bulder.append(history.get(j));
                }
                checkbox.setSummary(bulder.toString());
            }
            group.addPreference(checkbox);
        }
    }

    private boolean hasDictionary(Locale locale) {
        Resources rid = getResources();
        Configuration configration = rid.getConfiguration();
        Locale addids = configration.locale;
        boolean haveDictionary = false;
        configration.locale = locale;
        rid.updateConfiguration(configration, rid.getDisplayMetrics());

        int[] dictionaries = LatinIME.getDictionary(rid);
        Keyboard_Typing_Spiling typingvalue = new Keyboard_Typing_Spiling(this, dictionaries, My_Keyword_Inform_app.DIC_MAIN);

        if (typingvalue.getSize() > My_Keyword_Inform_app.LARGE_DICTIONARY_THRESHOLD / 4) {
            haveDictionary = true;
        } else {
            Keyboard_Typing_Spiling letter = My_Keybord_Mnager.getDictionary(getApplicationContext(), locale.getLanguage());
            if (letter != null) {
                typingvalue.close();
                typingvalue = letter;
                haveDictionary = true;
            }
        }

        typingvalue.close();
        configration.locale = addids;
        rid.updateConfiguration(configration, rid.getDisplayMetrics());
        return haveDictionary;
    }

    private String get5Code(Locale locale) {
        String world = locale.getCountry();
        return locale.getLanguage() + (TextUtils.isEmpty(world) ? "" : "_" + world);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        String checkedLanguages = "";
        PreferenceGroup refernce = getPreferenceScreen();
        int numbervalue = refernce.getPreferenceCount();
        for (int i = 0; i < numbervalue; i++) {
            CheckBoxPreference pref = (CheckBoxPreference) refernce.getPreference(i);
            if (pref.isChecked()) {
                Locale push = allbhasha.get(i).locale;
                checkedLanguages += get5Code(push) + ",";
            }
        }
        if (checkedLanguages.length() < 1) checkedLanguages = null; // Save null
        SharedPreferences refrence = PreferenceManager.getDefaultSharedPreferences(this);
        Editor enter = refrence.edit();
        enter.putString(LatinIME.PREF_SELECTED_LANGUAGES, checkedLanguages);
        Uplode_Refernce_Keyword.apply(enter);
    }

    private static String asString(Set<String> set) {
        StringBuilder off = new StringBuilder();
        off.append("set(");
        String[] types = new String[set.size()];
        types = set.toArray(types);
        Arrays.sort(types);
        for (int i = 0; i < types.length; ++i) {
                if (i > 0) off.append(", ");
                off.append(types[i]);
        }
        off.append(")");
        return off.toString();
    }
    
    ArrayList<Loc> getUniqueLocales() {
        Set<String> localeSet = new HashSet<String>();
        Set<String> langSet = new HashSet<String>();

        for (int i = 0; i < KBD_LOCALIZATIONS.length; ++i) {
                String localvalue = KBD_LOCALIZATIONS[i];
                if (localvalue.length() == 2 && langSet.contains(localvalue)) continue;
                if (localvalue.length() == 6) localvalue = localvalue.substring(0, 2) + "_" + localvalue.substring(4, 6);
                localeSet.add(localvalue);
        }
        Log.i(TAG, "localeSet=" + asString(localeSet));
        Log.i(TAG, "langSet=" + asString(langSet));

        String[] stringl = new String[localeSet.size()];
        stringl = localeSet.toArray(stringl);
        Arrays.sort(stringl);
        
        ArrayList<Loc> uniqueLocales = new ArrayList<Loc>();

        final int origSize = stringl.length;
        Loc[] preprocess = new Loc[origSize];
        int finalSize = 0;
        for (int i = 0 ; i < origSize; i++ ) {
            String s = stringl[i];
            int len = s.length();
            if (len == 2 || len == 5 || len == 6) {
                String language = s.substring(0, 2);
                Locale values;
                if (len == 5) {
                    String country = s.substring(3, 5);
                    values = new Locale(language, country);
                } else if (len == 6) {
                    values = new Locale(language, s.substring(4, 6));
                } else {
                    values = new Locale(language);
                }
                if (arrayContains(BLACKLIST_LANGUAGES, language)) continue;

                if (finalSize == 0) {
                    preprocess[finalSize++] = new Loc(Bhasha_Keybord_Swich.toTitleCase(values.getDisplayName(values)), values);
                } else {
                    if (preprocess[finalSize-1].locale.getLanguage().equals(language)) {
                        preprocess[finalSize-1].label = getLocaleName(preprocess[finalSize-1].locale);
                        preprocess[finalSize++] = new Loc(getLocaleName(values), values);
                    } else {
                        String screenname;
                        if (s.equals("zz_ZZ")) {
                        } else {
                            screenname = getLocaleName(values);
                            preprocess[finalSize++] = new Loc(screenname, values);
                        }
                    }
                }
            }
        }
        for (int i = 0; i < finalSize ; i++) {
            uniqueLocales.add(preprocess[i]);
        }
        return uniqueLocales;
    }

    private boolean arrayContains(String[] array, String value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equalsIgnoreCase(value)) return true;
        }
        return false;
    }
}