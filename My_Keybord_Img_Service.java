
package com.keyboard.keyboardthemes;

import android.app.Dialog;
import android.app.backup.BackupManager;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;
import android.text.AutoText;
import android.text.InputType;
import android.util.Log;

import com.keyboard.keyboardthemes.R;

import java.util.HashMap;
import java.util.Map;



public class My_Keybord_Img_Service extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener, DialogInterface.OnDismissListener {

    private static final String QUICK_FIXES_KEY = "quick_fixes";
    private static final String PREDICTION_SETTINGS_KEY = "prediction_settings";
    private static final String VOICE_SETTINGS_KEY = "voice_mode";
    static final String PREF_SETTINGS_KEY = "settings_key";
    static final String INPUT_CONNECTION_INFO = "input_connection_info";
    private static final String TAG = "My_Keybord_Img_Service";
    private static final int VOICE_INPUT_CONFIRM_DIALOG = 0;

    private CheckBoxPreference chekbox;
    private ListPreference layoutscreen;
    private ListPreference servises;
    private ListPreference references;
    private ListPreference valueimgseting;
    private Preference imginformation;
    private boolean soundstart;
    private boolean selectedv = false;
    private String soundend;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.prefs);
        chekbox = (CheckBoxPreference) findPreference(QUICK_FIXES_KEY);
        layoutscreen = (ListPreference) findPreference(VOICE_SETTINGS_KEY);
        servises = (ListPreference) findPreference(PREF_SETTINGS_KEY);
        imginformation = (Preference) findPreference(INPUT_CONNECTION_INFO);

        // TODO(klausw): remove these when no longer needed
        references = (ListPreference) findPreference("pref_keyboard_mode_portrait");
        valueimgseting = (ListPreference) findPreference("pref_keyboard_mode_landscape");

        SharedPreferences refernce = getPreferenceManager().getSharedPreferences();
        refernce.registerOnSharedPreferenceChangeListener(this);

        soundend = getString(R.string.voice_mode_off);
        soundstart = !(refernce.getString(VOICE_SETTINGS_KEY, soundend).equals(soundend));
    }

    @Override
    protected void onResume() {
        super.onResume();
        int autoTextSize = AutoText.getSize(getListView());
        if (autoTextSize < 1) {
            ((PreferenceGroup) findPreference(PREDICTION_SETTINGS_KEY)).removePreference(chekbox);
        }

        Log.i(TAG, "compactModeEnabled=" + LatinIME.service.compactModeEnabled);
        if (!LatinIME.service.compactModeEnabled) {
            CharSequence[] values = references.getEntries();
            CharSequence[] entry = references.getEntryValues();

            if (values.length > 2) {
                CharSequence[] imgentery = new CharSequence[]{values[0], values[2]};
                CharSequence[] imgvalu = new CharSequence[]{entry[0], entry[2]};
                references.setEntries(imgentery);
                references.setEntryValues(imgvalu);
                valueimgseting.setEntries(imgentery);
                valueimgseting.setEntryValues(imgvalu);
            }
        }

        updateSummaries();
    }

    @Override
    protected void onDestroy() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }

    public void onSharedPreferenceChanged(SharedPreferences refernce, String key) {
        (new BackupManager(this)).dataChanged();
        if (key.equals(VOICE_SETTINGS_KEY) && !soundstart) {
            if (!refernce.getString(VOICE_SETTINGS_KEY, soundend).equals(soundend)) {
                showVoiceConfirmation();
            }
        }
        soundstart = !(refernce.getString(VOICE_SETTINGS_KEY, soundend).equals(soundend));
        updateVoiceModeSummary();
        updateSummaries();
    }

    static Map<Integer, String> INPUT_CLASSES = new HashMap<Integer, String>();
    static Map<Integer, String> DATETIME_VARIATIONS = new HashMap<Integer, String>();
    static Map<Integer, String> TEXT_VARIATIONS = new HashMap<Integer, String>();
    static Map<Integer, String> NUMBER_VARIATIONS = new HashMap<Integer, String>();

    static {
        INPUT_CLASSES.put(0x00000004, "DATETIME");
        INPUT_CLASSES.put(0x00000002, "NUMBER");
        INPUT_CLASSES.put(0x00000003, "PHONE");
        INPUT_CLASSES.put(0x00000001, "TEXT");
        INPUT_CLASSES.put(0x00000000, "NULL");
        DATETIME_VARIATIONS.put(0x00000010, "DATE");
        DATETIME_VARIATIONS.put(0x00000020, "TIME");
        NUMBER_VARIATIONS.put(0x00000010, "PASSWORD");
        TEXT_VARIATIONS.put(0x00000020, "EMAIL_ADDRESS");
        TEXT_VARIATIONS.put(0x00000030, "EMAIL_SUBJECT");
        TEXT_VARIATIONS.put(0x000000b0, "FILTER");
        TEXT_VARIATIONS.put(0x00000050, "LONG_MESSAGE");
        TEXT_VARIATIONS.put(0x00000080, "PASSWORD");
        TEXT_VARIATIONS.put(0x00000060, "PERSON_NAME");
        TEXT_VARIATIONS.put(0x000000c0, "PHONETIC");
        TEXT_VARIATIONS.put(0x00000070, "POSTAL_ADDRESS");
        TEXT_VARIATIONS.put(0x00000040, "SHORT_MESSAGE");
        TEXT_VARIATIONS.put(0x00000010, "URI");
        TEXT_VARIATIONS.put(0x00000090, "VISIBLE_PASSWORD");
        TEXT_VARIATIONS.put(0x000000a0, "WEB_EDIT_TEXT");
        TEXT_VARIATIONS.put(0x000000d0, "WEB_EMAIL_ADDRESS");
        TEXT_VARIATIONS.put(0x000000e0, "WEB_PASSWORD");

    }

    private static void addBit(StringBuffer bafeer, int bit, String str) {
        if (bit != 0) {
            bafeer.append("|");
            bafeer.append(str);
        }
    }

    private static String inputTypeDesc(int type) {
        int room = type & 0x0000000f;
        int value = type & 0x00fff000;
        int vrybale = type & 0x00000ff0;


        StringBuffer end = new StringBuffer();
        String text = INPUT_CLASSES.get(room);
        end.append(text != null ? text : "?");

        if (room == InputType.TYPE_CLASS_TEXT) {
            String vrabletext = TEXT_VARIATIONS.get(vrybale);
            if (vrabletext != null) {
                end.append(".");
                end.append(vrabletext);
            }
            addBit(end, value & 0x00010000, "AUTO_COMPLETE");
            addBit(end, value & 0x00008000, "AUTO_CORRECT");
            addBit(end, value & 0x00001000, "CAP_CHARACTERS");
            addBit(end, value & 0x00004000, "CAP_SENTENCES");
            addBit(end, value & 0x00002000, "CAP_WORDS");
            addBit(end, value & 0x00040000, "IME_MULTI_LINE");
            addBit(end, value & 0x00020000, "MULTI_LINE");
            addBit(end, value & 0x00080000, "NO_SUGGESTIONS");
        } else if (room == InputType.TYPE_CLASS_NUMBER) {
            String vrabletext = NUMBER_VARIATIONS.get(vrybale);
            if (vrabletext != null) {
                end.append(".");
                end.append(vrabletext);
            }
            addBit(end, value & 0x00002000, "DECIMAL");
            addBit(end, value & 0x00001000, "SIGNED");
        } else if (room == InputType.TYPE_CLASS_DATETIME) {
            String vrabletext = DATETIME_VARIATIONS.get(vrybale);
            if (vrabletext != null) {
                end.append(".");
                end.append(vrabletext);
            }
        }
        return end.toString();
    }

    private void updateSummaries() {
        Resources ridvalue = getResources();
        servises.setSummary(ridvalue.getStringArray(R.array.settings_key_modes)[servises.findIndexOfValue(servises.getValue())]);
        imginformation.setSummary(String.format("%s type=%s", LatinIME.service.entertypingtext, inputTypeDesc(LatinIME.service.entertextmethed)
        ));
    }

    private void showVoiceConfirmation() {
        selectedv = false;
        showDialog(VOICE_INPUT_CONFIRM_DIALOG);
    }

    private void updateVoiceModeSummary() {
        layoutscreen.setSummary(getResources().getStringArray(R.array.voice_input_modes_summary)[layoutscreen.findIndexOfValue(layoutscreen.getValue())]);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            default:
                Log.e(TAG, "unknown dialog " + id);
                return null;
        }
    }

    public void onDismiss(DialogInterface dialog) {
        if (!selectedv) {
            layoutscreen.setValue(soundend);
        }
    }

    private void updateVoicePreference() {
    }
}
