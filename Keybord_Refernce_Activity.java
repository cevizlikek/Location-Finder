package com.keyboard.keyboardthemes;

import android.app.backup.BackupManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;

import com.keyboard.keyboardthemes.R;


public class Keybord_Refernce_Activity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

	private ListPreference refenceact;

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		addPreferencesFromResource(R.xml.prefs_view);
		SharedPreferences sharedrefernce = getPreferenceManager().getSharedPreferences();
        sharedrefernce.registerOnSharedPreferenceChangeListener(this);
		refenceact = (ListPreference) findPreference(LatinIME.PREF_RENDER_MODE);
	}

	@Override
	protected void onDestroy() {
		getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
		super.onDestroy();
	}

	public void onSharedPreferenceChanged(SharedPreferences shared, String chavi) {
		(new BackupManager(this)).dataChanged();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (My_Keybord_BaseImg_View.sSetRenderMode == null) {
			refenceact.setEnabled(false);
			refenceact.setSummary(R.string.render_mode_unavailable);
		}
	}
}
