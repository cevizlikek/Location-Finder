package com.keyboard.keyboardthemes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.AsyncTask;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class Outo_Spiling_Keybord extends Keybord_Typing_Spiling_expebnd {

    static final int FREQUENCY_FOR_PICKED = 3;
    static final int FREQUENCY_FOR_TYPED = 1;
    static final int FREQUENCY_FOR_AUTO_ADD = 250;
    private static final int VALIDITY_THRESHOLD = 2 * FREQUENCY_FOR_PICKED;
    private static final int PROMOTION_THRESHOLD = 4 * FREQUENCY_FOR_PICKED;
    private LatinIME otospling;
    private String stringspling;
    private HashMap<String,Integer> intergerspling = new HashMap<String,Integer>();
    private final Object otosplingvalue = new Object();
    private static final String DATABASE_NAME = "auto_dict.db";
    private static final int DATABASE_VERSION = 1;
    // TODO: Consume less space by using a unique id for locale instead of the whole
    private static final String COLUMN_ID = BaseColumns._ID;
    private static final String COLUMN_WORD = "word";
    private static final String COLUMN_FREQUENCY = "freq";
    private static final String COLUMN_LOCALE = "locale";
    public static final String DEFAULT_SORT_ORDER = COLUMN_FREQUENCY + " DESC";
    private static final String AUTODICT_TABLE_NAME = "words";
    private static HashMap<String, String> splingvaluetext;

    static {
        splingvaluetext = new HashMap<String, String>();
        splingvaluetext.put(COLUMN_ID, COLUMN_ID);
        splingvaluetext.put(COLUMN_WORD, COLUMN_WORD);
        splingvaluetext.put(COLUMN_FREQUENCY, COLUMN_FREQUENCY);
        splingvaluetext.put(COLUMN_LOCALE, COLUMN_LOCALE);
    }

    private static DatabaseHelper sOpenHelper = null;

    public Outo_Spiling_Keybord(Context appcont, LatinIME photo, String locale, int dicTypeId) {
        super(appcont, dicTypeId);
        otospling = photo;
        stringspling = locale;
        if (sOpenHelper == null) {
            sOpenHelper = new DatabaseHelper(getContext());
        }
        if (stringspling != null && stringspling.length() > 1) {
            loadDictionary();
        }
    }

    @Override
    public boolean isValidWord(CharSequence seqnce) {
        final int chareter = getWordFrequency(seqnce);
        return chareter >= VALIDITY_THRESHOLD;
    }

    @Override
    public void close() {
        flushPendingWrites();
        super.close();
    }

    @Override
    public void loadDictionaryAsync() {
        Cursor arow = query(COLUMN_LOCALE + "=?", new String[] {stringspling});
        try {
            if (arow.moveToFirst()) {
                int wordIndex = arow.getColumnIndex(COLUMN_WORD);
                int frequencyIndex = arow.getColumnIndex(COLUMN_FREQUENCY);
                while (!arow.isAfterLast()) {
                    String word = arow.getString(wordIndex);
                    int frequency = arow.getInt(frequencyIndex);
                    if (word.length() < getMaxWordLength()) {
                        super.addWord(word, frequency);
                    }
                    arow.moveToNext();
                }
            }
        } finally {
            arow.close();
        }
    }

    @Override
    public void addWord(String spling, int addFrequency) {
        final int length = spling.length();
        if (length < 2 || length > getMaxWordLength()) return;
        if (otospling.getCurrentWord().isAutoCapitalized()) {
            spling = Character.toLowerCase(spling.charAt(0)) + spling.substring(1);
        }
        int freq = getWordFrequency(spling);
        freq = freq < 0 ? addFrequency : freq + addFrequency;
        super.addWord(spling, freq);

        if (freq >= PROMOTION_THRESHOLD) {
            otospling.promoteToUserDictionary(spling, FREQUENCY_FOR_AUTO_ADD);
            freq = 0;
        }

        synchronized (otosplingvalue) {
            intergerspling.put(spling, freq == 0 ? null : new Integer(freq));
        }
    }

    public void flushPendingWrites() {
        synchronized (otosplingvalue) {
            if (intergerspling.isEmpty()) return;
            new UpdateDbTask(getContext(), sOpenHelper, intergerspling, stringspling).execute();
            intergerspling = new HashMap<String, Integer>();
        }
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context appcont) {
            super(appcont, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sql) {
            sql.execSQL("CREATE TABLE " + AUTODICT_TABLE_NAME + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_WORD + " TEXT,"
                    + COLUMN_FREQUENCY + " INTEGER,"
                    + COLUMN_LOCALE + " TEXT"
                    + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase data, int oldVersion, int newVersion) {
            Log.w("Outo_Spiling_Keybord", "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
            data.execSQL("DROP TABLE IF EXISTS " + AUTODICT_TABLE_NAME);
            onCreate(data);
        }
    }

    private Cursor query(String selection, String[] selectionArgs) {
        SQLiteQueryBuilder data = new SQLiteQueryBuilder();
        data.setTables(AUTODICT_TABLE_NAME);
        data.setProjectionMap(splingvaluetext);

        SQLiteDatabase list = sOpenHelper.getReadableDatabase();
        Cursor arow = data.query(list, null, selection, selectionArgs, null, null, DEFAULT_SORT_ORDER);
        return arow;
    }

    private static class UpdateDbTask extends AsyncTask<Void, Void, Void> {
        private final HashMap<String, Integer> stringvalue;
        private final DatabaseHelper usefor;
        private final String stringtext;

        public UpdateDbTask(Context context, DatabaseHelper openHelper, HashMap<String, Integer> txetname, String locale) {
            stringvalue = txetname;
            stringtext = locale;
            usefor = openHelper;
        }

        @Override
        protected Void doInBackground(Void... v) {
            SQLiteDatabase data = usefor.getWritableDatabase();
            Set<Entry<String,Integer>> mEntries = stringvalue.entrySet();
            for (Entry<String,Integer> entry : mEntries) {
                Integer pos = entry.getValue();
                data.delete(AUTODICT_TABLE_NAME, COLUMN_WORD + "=? AND " + COLUMN_LOCALE + "=?", new String[] { entry.getKey(), stringtext});
                if (pos != null) {
                    data.insert(AUTODICT_TABLE_NAME, null, getContentValues(entry.getKey(), pos, stringtext));
                }
            }
            return null;
        }

        private ContentValues getContentValues(String word, int frequency, String locale) {
            ContentValues conten = new ContentValues(4);
            conten.put(COLUMN_WORD, word);
            conten.put(COLUMN_FREQUENCY, frequency);
            conten.put(COLUMN_LOCALE, locale);
            return conten;
        }
    }
}
