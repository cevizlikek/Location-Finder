package com.keyboard.keyboardthemes;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.provider.UserDictionary.Words;
import android.util.Log;

public class User_keybord_Spiling extends Keybord_Typing_Spiling_expebnd {
    
    private static final String[] PROJECTION = {
        Words._ID,
        Words.WORD,
        Words.FREQUENCY
    };
    
    private static final int ONE_NAME = 1;
    private static final int TWO_PERMISSION = 2;
    private static final String TAG = "HK/User_keybord_Spiling";
    private ContentObserver contected;
    private String stringvalue;

    public User_keybord_Spiling(Context appcont, String stringname) {
        super(appcont, My_Keyword_Inform_app.DIC_USER);
        stringvalue = stringname;
        ContentResolver resolve = appcont.getContentResolver();
        
        resolve.registerContentObserver(Words.CONTENT_URI, true, contected = new ContentObserver(null) {
            @Override
            public void onChange(boolean change) {
                setRequiresReload(true);
            }
        });

        loadDictionary();
    }

    @Override
    public synchronized void close() {
        if (contected != null) {
            getContext().getContentResolver().unregisterContentObserver(contected);
            contected = null;
        }
        super.close();
    }

    @Override
    public void loadDictionaryAsync() {
        Cursor lode = getContext().getContentResolver().query(Words.CONTENT_URI, PROJECTION, "(locale IS NULL) or (locale=?)", new String[] {stringvalue}, null);
        addWords(lode);
    }

    @Override
    public synchronized void addWord(String spling, int addspling) {
        if (getRequiresReload()) loadDictionaryAsync();
        if (spling.length() >= getMaxWordLength()) return;

        super.addWord(spling, addspling);
        final ContentValues contet = new ContentValues(5);
        contet.put(Words.WORD, spling);
        contet.put(Words.FREQUENCY, addspling);
        contet.put(Words.LOCALE, stringvalue);
        contet.put(Words.APP_ID, 0);

        final ContentResolver splingmathod = getContext().getContentResolver();
        new Thread("addWord") {
            public void run() {
                splingmathod.insert(Words.CONTENT_URI, contet);
            }
        }.start();
        setRequiresReload(false);
    }

    @Override
    public synchronized void getWords(final Keyword_Set_Compres nvalue, final WordCallback number, int[] nextLettersFrequencies) {
        super.getWords(nvalue, number, nextLettersFrequencies);
    }

    @Override
    public synchronized boolean isValidWord(CharSequence name) {
        return super.isValidWord(name);
    }

    private void addWords(Cursor cursor) {
        if (cursor == null) {
            Log.w(TAG, "Unexpected null cursor in addWords()");
            return;
        }
        clearDictionary();

        final int maxWordLength = getMaxWordLength();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String word = cursor.getString(ONE_NAME);
                int frequency = cursor.getInt(TWO_PERMISSION);
                if (word.length() < maxWordLength) {
                    super.addWord(word, frequency);
                }
                cursor.moveToNext();
            }
        }
        cursor.close();
    }
}
