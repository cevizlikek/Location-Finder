package com.keyboard.keyboardthemes;

import android.app.backup.BackupAgentHelper;
import android.app.backup.SharedPreferencesBackupHelper;

public class Backup_Img_My_Keybord extends BackupAgentHelper {

    @Override
    public void onCreate() {
        addHelper("shared_pref", new SharedPreferencesBackupHelper(this, getPackageName() + "_preferences"));
    }
}
