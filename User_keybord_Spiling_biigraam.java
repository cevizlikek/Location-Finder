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
import java.util.HashSet;
import java.util.Iterator;

public class User_keybord_Spiling_biigraam extends Keybord_Typing_Spiling_expebnd {

    private static final String TAG = "User_keybord_Spiling_biigraam";
    private static final int USER_TYPING = 2;
    private static final int USER_MAXIMUM = 127;
    protected static final int REVIEW_TYPE = 6 * USER_TYPING;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "userbigram_dict.db";
    private static final String MAIN_TABLE_NAME = "main";
    // TODO: Consume less space by using a unique id for locale instead of the whole
    private static final String MAIN_HORZONTAL_ID = BaseColumns._ID;
    private static final String USER_HORZONTAL_WORD1 = "word1";
    private static final String USER_HORZONTAL_WORD2 = "word2";
    private static final String MAIN_HORZONTAL_LOCALE = "locale";
    private static final String USER_TABLE_TEXT = "frequency";
    private static final String USER_HORZONTAL_ID = BaseColumns._ID;
    private static final String USER_HORZONTAL_PAIR_ID = "pair_id";
    private static final String USER_HORZONTAL_FREQUENCY = "freq";
    private static int sMaxUserBigrams = 10000;
    private static int removevalue = 1000;
    private final LatinIME nlatinme;
    private String stringfun;
    private HashSet<Bigram> hashsetgram = new HashSet<Bigram>();
    private final Object userlodevalue = new Object();
    private static volatile boolean sharevoltile = false;
    private final static HashMap<String, String> derictoinnaksho;
    private static DatabaseHelper ondatabase = null;

    static {
        derictoinnaksho = new HashMap<String, String>();
        derictoinnaksho.put(MAIN_HORZONTAL_ID, MAIN_HORZONTAL_ID);
        derictoinnaksho.put(USER_HORZONTAL_WORD1, USER_HORZONTAL_WORD1);
        derictoinnaksho.put(USER_HORZONTAL_WORD2, USER_HORZONTAL_WORD2);
        derictoinnaksho.put(MAIN_HORZONTAL_LOCALE, MAIN_HORZONTAL_LOCALE);
        derictoinnaksho.put(USER_HORZONTAL_ID, USER_HORZONTAL_ID);
        derictoinnaksho.put(USER_HORZONTAL_PAIR_ID, USER_HORZONTAL_PAIR_ID);
        derictoinnaksho.put(USER_HORZONTAL_FREQUENCY, USER_HORZONTAL_FREQUENCY);
    }



    private static class Bigram {
        String mtyping;
        String ntyping;
        int redusion;

        Bigram(String word1, String word2, int frequency) {
            this.mtyping = word1;
            this.ntyping = word2;
            this.redusion = frequency;
        }

        @Override
        public boolean equals(Object gramer) {
            Bigram gramervalue = (Bigram) gramer;
            return (mtyping.equals(gramervalue.mtyping) && ntyping.equals(gramervalue.ntyping));
        }

        @Override
        public int hashCode() {
            return (mtyping + " " + ntyping).hashCode();
        }
    }

    public void setDatabaseMax(int maxUserBigram) {
        sMaxUserBigrams = maxUserBigram;
    }

    public void setDatabaseDelete(int deleteUserBigram) {
        removevalue = deleteUserBigram;
    }

    public User_keybord_Spiling_biigraam(Context appcont, LatinIME latin, String locale, int deraction) {
        super(appcont, deraction);
        nlatinme = latin;
        stringfun = locale;
        if (ondatabase == null) {
            ondatabase = new DatabaseHelper(getContext());
        }
        if (stringfun != null && stringfun.length() > 1) {
            loadDictionary();
        }
    }

    @Override
    public void close() {
        flushPendingWrites();
        super.close();
    }

    public int addBigrams(String mtext, String ntyping) {
        if (nlatinme != null && nlatinme.getCurrentWord().isAutoCapitalized()) {
            ntyping = Character.toLowerCase(ntyping.charAt(0)) + ntyping.substring(1);
        }

        int freq = super.addBigram(mtext, ntyping, USER_TYPING);
        if (freq > USER_MAXIMUM) freq = USER_MAXIMUM;
        synchronized (userlodevalue) {
            if (freq == USER_TYPING || hashsetgram.isEmpty()) {
                hashsetgram.add(new Bigram(mtext, ntyping, freq));
            } else {
                Bigram spling = new Bigram(mtext, ntyping, freq);
                hashsetgram.remove(spling);
                hashsetgram.add(spling);
            }
        }

        return freq;
    }

    public void flushPendingWrites() {
        synchronized (userlodevalue) {
            if (hashsetgram.isEmpty()) return;
            new UpdateDbTask(getContext(), ondatabase, hashsetgram, stringfun).execute();
            hashsetgram = new HashSet<Bigram>();
        }
    }

    void waitUntilUpdateDBDone() {
        synchronized (userlodevalue) {
            while (sharevoltile) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
            return;
        }
    }

    @Override
    public void loadDictionaryAsync() {
        Cursor derction = query(MAIN_HORZONTAL_LOCALE + "=?", new String[] {stringfun});
        try {
            if (derction.moveToFirst()) {
                int atext = derction.getColumnIndex(USER_HORZONTAL_WORD1);
                int btext = derction.getColumnIndex(USER_HORZONTAL_WORD2);
                int frequencyIndex = derction.getColumnIndex(USER_HORZONTAL_FREQUENCY);
                while (!derction.isAfterLast()) {
                    String aname = derction.getString(atext);
                    String bname = derction.getString(btext);
                    int radiynce = derction.getInt(frequencyIndex);
                    if (aname.length() < TYPINGSPLING && bname.length() < TYPINGSPLING) {
                        super.setBigram(aname, bname, radiynce);
                    }
                    derction.moveToNext();
                }
            }
        } finally {
            derction.close();
        }
    }
    private Cursor query(String selection, String[] selectionArgs) {
        SQLiteQueryBuilder query = new SQLiteQueryBuilder();

        query.setTables(MAIN_TABLE_NAME + " INNER JOIN " + USER_TABLE_TEXT + " ON (" + MAIN_TABLE_NAME + "." + MAIN_HORZONTAL_ID + "=" + USER_TABLE_TEXT + "." + USER_HORZONTAL_PAIR_ID +")");
        query.setProjectionMap(derictoinnaksho);

        SQLiteDatabase database = ondatabase.getReadableDatabase();
        Cursor name = query.query(database, new String[] {USER_HORZONTAL_WORD1, USER_HORZONTAL_WORD2, USER_HORZONTAL_FREQUENCY},
                selection, selectionArgs, null, null, null);
        return name;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context name) {
            super(name, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase data) {
            data.execSQL("PRAGMA foreign_keys = ON;");
            data.execSQL("CREATE TABLE " + MAIN_TABLE_NAME + " ("
                    + MAIN_HORZONTAL_ID + " INTEGER PRIMARY KEY,"
                    + USER_HORZONTAL_WORD1 + " TEXT,"
                    + USER_HORZONTAL_WORD2 + " TEXT,"
                    + MAIN_HORZONTAL_LOCALE + " TEXT"
                    + ");");
            data.execSQL("CREATE TABLE " + USER_TABLE_TEXT + " ("
                    + USER_HORZONTAL_ID + " INTEGER PRIMARY KEY,"
                    + USER_HORZONTAL_PAIR_ID + " INTEGER,"
                    + USER_HORZONTAL_FREQUENCY + " INTEGER,"
                    + "FOREIGN KEY(" + USER_HORZONTAL_PAIR_ID + ") REFERENCES " + MAIN_TABLE_NAME
                    + "(" + MAIN_HORZONTAL_ID + ")" + " ON DELETE CASCADE"
                    + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase database, int oldVersion, int relode) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + relode + ", which will destroy all old data");
            database.execSQL("DROP TABLE IF EXISTS " + MAIN_TABLE_NAME);
            database.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_TEXT);
            onCreate(database);
        }
    }
    private static class UpdateDbTask extends AsyncTask<Void, Void, Void> {
        private final HashSet<Bigram> text;
        private final DatabaseHelper database;
        private final String name;

        public UpdateDbTask(Context appcont, DatabaseHelper data, HashSet<Bigram> textname, String locale) {
            text = textname;
            name = locale;
            database = data;
        }

        private void checkPruneData(SQLiteDatabase data) {
            data.execSQL("PRAGMA foreign_keys = ON;");
            Cursor name = data.query(USER_TABLE_TEXT, new String[] {USER_HORZONTAL_PAIR_ID}, null, null, null, null, null);
            try {
                int totalRowCount = name.getCount();
                if (totalRowCount > sMaxUserBigrams) {
                    int numDeleteRows = (totalRowCount - sMaxUserBigrams) + removevalue;
                    int pairIdColumnId = name.getColumnIndex(USER_HORZONTAL_PAIR_ID);
                    name.moveToFirst();
                    int count = 0;
                    while (count < numDeleteRows && !name.isAfterLast()) {
                        String nameid = name.getString(pairIdColumnId);
                        data.delete(MAIN_TABLE_NAME, MAIN_HORZONTAL_ID + "=?", new String[] { nameid });
                        name.moveToNext();
                        count++;
                    }
                }
            } finally {
                name.close();
            }
        }

        @Override
        protected void onPreExecute() {
            sharevoltile = true;
        }

        @Override
        protected Void doInBackground(Void... v) {
            SQLiteDatabase data = database.getWritableDatabase();
            data.execSQL("PRAGMA foreign_keys = ON;");
            Iterator<Bigram> gramer = text.iterator();
            while (gramer.hasNext()) {
                Bigram btext = gramer.next();
                Cursor name = data.query(MAIN_TABLE_NAME, new String[] {MAIN_HORZONTAL_ID}, USER_HORZONTAL_WORD1 + "=? AND " + USER_HORZONTAL_WORD2 + "=? AND "
                        + MAIN_HORZONTAL_LOCALE + "=?", new String[] { btext.mtyping, btext.ntyping, this.name}, null, null, null);

                int backg;
                if (name.moveToFirst()) {
                    backg = name.getInt(name.getColumnIndex(MAIN_HORZONTAL_ID));
                    data.delete(USER_TABLE_TEXT, USER_HORZONTAL_PAIR_ID + "=?",
                            new String[] { Integer.toString(backg) });
                } else {
                    Long bg = data.insert(MAIN_TABLE_NAME, null, getContentValues(btext.mtyping, btext.ntyping, this.name));
                    backg = bg.intValue();
                }
                name.close();

                data.insert(USER_TABLE_TEXT, null, getFrequencyContentValues(backg, btext.redusion));
            }
            checkPruneData(data);
            sharevoltile = false;
            return null;
        }

        private ContentValues getContentValues(String atext, String btext, String locale) {
            ContentValues contect = new ContentValues(3);
            contect.put(USER_HORZONTAL_WORD1, atext);
            contect.put(USER_HORZONTAL_WORD2, btext);
            contect.put(MAIN_HORZONTAL_LOCALE, locale);
            return contect;
        }

        private ContentValues getFrequencyContentValues(int pairId, int frequency) {
           ContentValues contect = new ContentValues(2);
           contect.put(USER_HORZONTAL_PAIR_ID, pairId);
           contect.put(USER_HORZONTAL_FREQUENCY, frequency);
           return contect;
        }
    }
}
