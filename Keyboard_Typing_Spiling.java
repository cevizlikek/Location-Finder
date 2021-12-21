package com.keyboard.keyboardthemes;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.Channels;
import java.util.Arrays;


public class Keyboard_Typing_Spiling extends Dictionary {

    protected static final int typingvalue = 48;
    private static final String TAG_spiling = "Keyboard_Typing_Spiling";
    private static final int INTMAX_ROTED = 16;
    private static final int INTMAX_SPILING = 18;
    private static final int INTMAX_GRAMEER = 60;
    private static final int INT_TYPING_WORD = 2;
    private static final boolean RONG_LATTER = true;
    private int intdecsnary;
    private int fulldescnary;
    private int descnarysize;
    private int[] typingcode = new int[typingvalue * INTMAX_ROTED];
    private char[] intoutname = new char[typingvalue * INTMAX_SPILING];
    private char[] intoutgramer = new char[typingvalue * INTMAX_GRAMEER];
    private int[] intspiling = new int[INTMAX_SPILING];
    private int[] intsplinggramer = new int[INTMAX_GRAMEER];
    private ByteBuffer bytesplingbufer;

    static {
        try {
            System.loadLibrary("jni_pckeyboard");
            Log.i("PCKeyboard", "loaded jni_pckeyboard");
        } catch (UnsatisfiedLinkError ule) {
            Log.e("Keyboard_Typing_Spiling", "Could not load native library jni_pckeyboard");
        }
    }

    public Keyboard_Typing_Spiling(Context mcont, int[] intid, int valueid) {
        if (intid != null && intid.length > 0 && intid[0] != 0) {
            loadDictionary(mcont, intid);
        }
        intdecsnary = valueid;
    }

    public Keyboard_Typing_Spiling(Context mcont, InputStream[] input, int decsnaryi) {
        if (input != null && input.length > 0) {
            loadDictionary(input);
        }
        intdecsnary = decsnaryi;
    }

    public Keyboard_Typing_Spiling(Context mcont, ByteBuffer btbafe, int decsnaryi) {
        if (btbafe != null) {
            if (btbafe.isDirect()) {
                bytesplingbufer = btbafe;
            } else {
                bytesplingbufer = ByteBuffer.allocateDirect(btbafe.capacity());
                btbafe.rewind();
                bytesplingbufer.put(btbafe);
            }
            descnarysize = btbafe.capacity();
            fulldescnary = openNative(bytesplingbufer, INT_TYPING_WORD, all_country, descnarysize);
        }
        intdecsnary = decsnaryi;
    }

    private native int openNative(ByteBuffer bb, int typedLetterMultiplier, int fullWordMultiplier, int dictSize);
    private native void closeNative(int dict);
    private native boolean isValidWordNative(int nativeData, char[] word, int wordLength);
    private native int getSuggestionsNative(int dict, int[] inputCodes, int codesSize, char[] outputChars, int[] frequencies, int maxWordLength, int maxWords, int maxAlternatives, int skipPos, int[] nextLettersFrequencies, int nextLettersSize);
    private native int getBigramsNative(int dict, char[] prevWord, int prevWordLength, int[] inputCodes, int inputCodesLength, char[] outputChars, int[] frequencies, int maxWordLength, int maxBigrams, int maxAlternatives);

    private final void loadDictionary(InputStream[] is) {
        try {
            int total = 0;

            for (int i = 0; i < is.length; i++) {
                total += is[i].available();
            }

            bytesplingbufer = ByteBuffer.allocateDirect(total).order(ByteOrder.nativeOrder());
            int got = 0;
            for (int i = 0; i < is.length; i++) {
                got += Channels.newChannel(is[i]).read(bytesplingbufer);
            }
            if (got != total) {
                Log.e(TAG_spiling, "Read " + got + " bytes, expected " + total);
            } else {
                fulldescnary = openNative(bytesplingbufer, INT_TYPING_WORD, all_country, total);
                descnarysize = total;
            }
            if (descnarysize > 10000) Log.i("PCKeyboard", "Loaded dictionary, len=" + descnarysize);
        } catch (IOException e) {
            Log.w(TAG_spiling, "No available memory for binary dictionary");
        } catch (UnsatisfiedLinkError e) {
            Log.w(TAG_spiling, "Failed to load native dictionary", e);
        } finally {
            try {
                if (is != null) {
                    for (int i = 0; i < is.length; i++) {
                        is[i].close();
                    }
                }
            } catch (IOException e) {
                Log.w(TAG_spiling, "Failed to close input stream");
            }
        }
    }
    
    private final void loadDictionary(Context mcont, int[] intid) {
        InputStream[] input = null;
        input = new InputStream[intid.length];
        for (int i = 0; i < intid.length; i++) {
            input[i] = mcont.getResources().openRawResource(intid[i]);
        }
        loadDictionary(input);
    }


    @Override
    public void getBigrams(final Keyword_Set_Compres codes, final CharSequence previousWord, final WordCallback callback, int[] nextLettersFrequencies) {

        char[] charary = previousWord.toString().toCharArray();
        Arrays.fill(intoutgramer, (char) 0);
        Arrays.fill(intsplinggramer, 0);

        int codesSize = codes.size();
        Arrays.fill(typingcode, -1);
        int[] alternatives = codes.getCodesAt(0);
        System.arraycopy(alternatives, 0, typingcode, 0, Math.min(alternatives.length, INTMAX_ROTED));
        int count = getBigramsNative(fulldescnary, charary, charary.length, typingcode, codesSize, intoutgramer, intsplinggramer, typingvalue, INTMAX_GRAMEER, INTMAX_ROTED);

        for (int j = 0; j < count; j++) {
            if (intsplinggramer[j] < 1) break;
            int start = j * typingvalue;
            int len = 0;
            while (intoutgramer[start + len] != 0) {
                len++;
            }
            if (len > 0) {
                callback.addWord(intoutgramer, start, len, intsplinggramer[j], intdecsnary, DataType.BIGRAM);
            }
        }
    }

    @Override
    public void getWords(final Keyword_Set_Compres codes, final WordCallback callback, int[] nextLettersFrequencies) {
        final int codesSize = codes.size();
        if (codesSize > typingvalue - 1) return;
        Arrays.fill(typingcode, -1);
        for (int i = 0; i < codesSize; i++) {
            int[] alternatives = codes.getCodesAt(i);
            System.arraycopy(alternatives, 0, typingcode, i * INTMAX_ROTED, Math.min(alternatives.length, INTMAX_ROTED));
        }
        Arrays.fill(intoutname, (char) 0);
        Arrays.fill(intspiling, 0);

        if (fulldescnary == 0)
            return;

        int intnum = getSuggestionsNative(fulldescnary, typingcode, codesSize, intoutname, intspiling, typingvalue, INTMAX_SPILING, INTMAX_ROTED, -1, nextLettersFrequencies, nextLettersFrequencies != null ? nextLettersFrequencies.length : 0);

        if (RONG_LATTER && intnum < 5) {
            for (int skip = 0; skip < codesSize; skip++) {
                int tempCount = getSuggestionsNative(fulldescnary, typingcode, codesSize, intoutname, intspiling, typingvalue, INTMAX_SPILING, INTMAX_ROTED, skip, null, 0);
                intnum = Math.max(intnum, tempCount);
                if (tempCount > 0) break;
            }
        }

        for (int j = 0; j < intnum; j++) {
            if (intspiling[j] < 1) break;
            int start = j * typingvalue;
            int len = 0;
            while (intoutname[start + len] != 0) {
                len++;
            }
            if (len > 0) {
                callback.addWord(intoutname, start, len, intspiling[j], intdecsnary, DataType.UNIGRAM);
            }
        }
    }

    @Override
    public boolean isValidWord(CharSequence spling) {
        if (spling == null || fulldescnary == 0) return false;
        char[] chars = spling.toString().toCharArray();
        return isValidWordNative(fulldescnary, chars, chars.length);
    }

    public int getSize() {
        return descnarysize;
    }

    @Override
    public synchronized void close() {
        if (fulldescnary != 0) {
            closeNative(fulldescnary);
            fulldescnary = 0;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }
}
