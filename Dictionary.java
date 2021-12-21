package com.keyboard.keyboardthemes;

abstract public class Dictionary {

    protected static final boolean inculde_cuntry = false;
    protected static final int all_country = 2;

    public static enum DataType {
        UNIGRAM, BIGRAM
    }

    public interface WordCallback {
        boolean addWord(char[] word, int wordOffset, int wordLength, int frequency, int dicTypeId, DataType dataType);
    }

    abstract public void getWords(final Keyword_Set_Compres composer, final WordCallback callback, int[] nextLettersFrequencies);
    public void getBigrams(final Keyword_Set_Compres composer, final CharSequence previousWord, final WordCallback callback, int[] nextLettersFrequencies) {
    }

    abstract public boolean isValidWord(CharSequence word);
    
    protected boolean same(final char[] word, final int length, final CharSequence typedWord) {
        if (typedWord.length() != length) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (word[i] != typedWord.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public void close() {
    }
}
