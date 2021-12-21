package com.keyboard.keyboardthemes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

public class My_Keyword_Notification extends BroadcastReceiver {
	static final String TAG = "PCKeyboard/Notification";
    private LatinIME notifaction;

	My_Keyword_Notification(LatinIME ime) {
	 	super();
    	notifaction = ime;
		Log.i(TAG, "My_Keyword_Notification created, ime=" + notifaction);
	}
	
    @Override
    public void onReceive(Context context, Intent intent) {
		Log.i(TAG, "My_Keyword_Notification.onReceive called");

		InputMethodManager inputmng = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (inputmng != null) {
			inputmng.showSoftInputFromInputMethod(notifaction.mToken, InputMethodManager.SHOW_FORCED);
		}
	}
}
