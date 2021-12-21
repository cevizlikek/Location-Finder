package com.keyboard.keyboardthemes;

import android.content.Context;
import android.text.AutoText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class My_Keyword_Inform_app implements Dictionary.WordCallback {

	private static String TAG = "PCKeyboard";
    public static final int APPROX_MAX_WORD_LENGTH = 32;
    public static final int CORRECTION_NONE = 0;
    public static final int CORRECTION_BASIC = 1;
    public static final int CORRECTION_FULL = 2;
    public static final int CORRECTION_FULL_BIGRAM = 3;
    public static final double BIGRAM_MULTIPLIER_MIN = 1.2;
    public static final double BIGRAM_MULTIPLIER_MAX = 1.5;
    public static final int MAXIMUM_BIGRAM_FREQUENCY = 127;
    public static final int DIC_USER_TYPED = 0;
    public static final int DIC_MAIN = 1;
    public static final int DIC_USER = 2;
    public static final int DIC_AUTO = 3;
    public static final int DIC_CONTACTS = 4;
    private static final int PREF_MAX_BIGRAMS = 60;
    public static final int DIC_TYPE_LAST_ID = 4;
    static final int LARGE_DICTIONARY_THRESHOLD = 200 * 1000;

    private Keyboard_Typing_Spiling splingkeybord;
    private Dictionary gramar;
    private Dictionary otosplinglater;
    private Dictionary splingwords;
    private Dictionary gramarsplingwords;
    private int splingreview = 12;
    private boolean otovaluespling;
    private int[] splingleter = new int[splingreview];
    private int[] maximamspling = new int[PREF_MAX_BIGRAMS];
    private int[] aftersplingvalue = new int[1280];
    private ArrayList<CharSequence> leterwordsreview = new ArrayList<CharSequence>();
    ArrayList<CharSequence> gramerreview = new ArrayList<CharSequence>();
    private ArrayList<CharSequence> texrtaray = new ArrayList<CharSequence>();
    private boolean controlleter;
    private CharSequence newwordleter;
    private String newsplingleter;
    // TODO: Remove these member variables by passing more context to addWord() callback method
    private boolean onevaluesplingtext;
    private boolean valuetextlater;
    private int valueletertextword = CORRECTION_BASIC;

    public My_Keyword_Inform_app(Context context, int[] dictionaryResId) {
        splingkeybord = new Keyboard_Typing_Spiling(context, dictionaryResId, DIC_MAIN);
        if (!hasMainDictionary()) {
            Locale object = context.getResources().getConfiguration().locale;
            Keyboard_Typing_Spiling spling = My_Keybord_Mnager.getDictionary(context, object.getLanguage());
            if (spling != null) {
                splingkeybord.close();
                splingkeybord = spling;
            }
        }
        initPool();
    }

    public My_Keyword_Inform_app(Context appcont, ByteBuffer buufer) {
        splingkeybord = new Keyboard_Typing_Spiling(appcont, buufer, DIC_MAIN);
        initPool();
    }

    private void initPool() {
        for (int i = 0; i < splingreview; i++) {
            StringBuilder bulder = new StringBuilder(getApproxMaxWordLength());
            texrtaray.add(bulder);
        }
    }

    public void setAutoTextEnabled(boolean enabled) {
        otovaluespling = enabled;
    }

    public int getCorrectionMode() {
        return valueletertextword;
    }

    public void setCorrectionMode(int mode) {
        valueletertextword = mode;
    }

    public boolean hasMainDictionary() {
        return splingkeybord.getSize() > LARGE_DICTIONARY_THRESHOLD;
    }

    public int getApproxMaxWordLength() {
        return APPROX_MAX_WORD_LENGTH;
    }

    public void setUserDictionary(Dictionary laubray) {
        gramar = laubray;
    }
    public void setContactsDictionary(Dictionary userDictionary) {
        splingwords = userDictionary;
    }
    
    public void setAutoDictionary(Dictionary autoDictionary) {
        otosplinglater = autoDictionary;
    }

    public void setUserBigramDictionary(Dictionary userBigramDictionary) {
        gramarsplingwords = userBigramDictionary;
    }
    public void setMaxSuggestions(int maxSuggestions) {
        if (maxSuggestions < 1 || maxSuggestions > 100) {
            throw new IllegalArgumentException("maxSuggestions must be between 1 and 100");
        }
        splingreview = maxSuggestions;
        splingleter = new int[splingreview];
        maximamspling = new int[PREF_MAX_BIGRAMS];
        collectGarbage(leterwordsreview, splingreview);
        while (texrtaray.size() < splingreview) {
            StringBuilder sb = new StringBuilder(getApproxMaxWordLength());
            texrtaray.add(sb);
        }
    }

    private boolean haveSufficientCommonality(String original, CharSequence suggestion) {
        final int sizespling = original.length();
        final int review = suggestion.length();
        final int minLength = Math.min(sizespling, review);
        if (minLength <= 2) return true;
        int matching = 0;
        int lessMatching = 0;
        int i;
        for (i = 0; i < minLength; i++) {
            final char origChar = Keybord_Typing_Spiling_expebnd.toLowerCase(original.charAt(i));
            if (origChar == Keybord_Typing_Spiling_expebnd.toLowerCase(suggestion.charAt(i))) {
                matching++;
                lessMatching++;
            } else if (i + 1 < review && origChar == Keybord_Typing_Spiling_expebnd.toLowerCase(suggestion.charAt(i + 1))) {
                lessMatching++;
            }
        }
        matching = Math.max(matching, lessMatching);

        if (minLength <= 4) {
            return matching >= 2;
        } else {
            return matching > minLength / 2;
        }
    }

    public List<CharSequence> getSuggestions(View view, Keyword_Set_Compres wordComposer, boolean includeTypedWordIfValid, CharSequence prevWordForBigram) {
        My_Keybord_Imglong.onStartSuggestion(prevWordForBigram);
        controlleter = false;
        onevaluesplingtext = wordComposer.isFirstCharCapitalized();
        valuetextlater = wordComposer.isAllUpperCase();
        collectGarbage(leterwordsreview, splingreview);
        Arrays.fill(splingleter, 0);
        Arrays.fill(aftersplingvalue, 0);
        newwordleter = wordComposer.getTypedWord();
        if (newwordleter != null) {
            final String mOriginalWordString = newwordleter.toString();
            newwordleter = mOriginalWordString;
            newsplingleter = mOriginalWordString.toLowerCase();
            My_Keybord_Imglong.onAddSuggestedWord(mOriginalWordString, My_Keyword_Inform_app.DIC_USER_TYPED, Dictionary.DataType.UNIGRAM);
        } else {
            newsplingleter = "";
        }

        if (wordComposer.size() == 1 && (valueletertextword == CORRECTION_FULL_BIGRAM || valueletertextword == CORRECTION_BASIC)) {
            Arrays.fill(maximamspling, 0);
            collectGarbage(gramerreview, PREF_MAX_BIGRAMS);

            if (!TextUtils.isEmpty(prevWordForBigram)) {
                CharSequence sequnce = prevWordForBigram.toString().toLowerCase();
                if (splingkeybord.isValidWord(sequnce)) {
                    prevWordForBigram = sequnce;
                }
                if (gramarsplingwords != null) {
                    gramarsplingwords.getBigrams(wordComposer, prevWordForBigram, this, aftersplingvalue);
                }
                if (splingwords != null) { splingwords.getBigrams(wordComposer, prevWordForBigram, this, aftersplingvalue);
                }
                if (splingkeybord != null) {
                    splingkeybord.getBigrams(wordComposer, prevWordForBigram, this, aftersplingvalue);
                }
                char currentChar = wordComposer.getTypedWord().charAt(0);
                char currentCharUpper = Character.toUpperCase(currentChar);
                int count = 0;
                int bigramSuggestionSize = gramerreview.size();
                for (int i = 0; i < bigramSuggestionSize; i++) {
                    if (gramerreview.get(i).charAt(0) == currentChar || gramerreview.get(i).charAt(0) == currentCharUpper) {
                        int poolSize = texrtaray.size();
                        StringBuilder bilder = poolSize > 0 ? (StringBuilder) texrtaray.remove(poolSize - 1) : new StringBuilder(getApproxMaxWordLength());
                        bilder.setLength(0);
                        bilder.append(gramerreview.get(i));
                        leterwordsreview.add(count++, bilder);
                        if (count > splingreview) break;
                    }
                }
            }

        } else if (wordComposer.size() > 1) {
            if (gramar != null || splingwords != null) {
                if (gramar != null) {
                    gramar.getWords(wordComposer, this, aftersplingvalue);
                }
                if (splingwords != null) {
                    splingwords.getWords(wordComposer, this, aftersplingvalue);
                }

                if (leterwordsreview.size() > 0 && isValidWord(newwordleter) && (valueletertextword == CORRECTION_FULL || valueletertextword == CORRECTION_FULL_BIGRAM)) {
                    controlleter = true;
                }
            }
            splingkeybord.getWords(wordComposer, this, aftersplingvalue);
            if ((valueletertextword == CORRECTION_FULL || valueletertextword == CORRECTION_FULL_BIGRAM) && leterwordsreview.size() > 0) {
                controlleter = true;
            }
        }
        if (newwordleter != null) {
            leterwordsreview.add(0, newwordleter.toString());
        }

        if (wordComposer.size() > 1 && leterwordsreview.size() > 1 && (valueletertextword == CORRECTION_FULL || valueletertextword == CORRECTION_FULL_BIGRAM)) {
            if (!haveSufficientCommonality(newsplingleter, leterwordsreview.get(1))) {
                controlleter = false;
            }
        }
        if (otovaluespling) {
            int i = 0;
            int max = 6;
            if (valueletertextword == CORRECTION_BASIC) max = 1;
            while (i < leterwordsreview.size() && i < max) {
                String reviewspling = leterwordsreview.get(i).toString().toLowerCase();
                CharSequence otoname = AutoText.get(reviewspling, 0, reviewspling.length(), view);
                boolean added = otoname != null;
                added &= !TextUtils.equals(otoname, leterwordsreview.get(i));
                if (added && i + 1 < leterwordsreview.size() && valueletertextword != CORRECTION_BASIC) {
                    added &= !TextUtils.equals(otoname, leterwordsreview.get(i + 1));
                }
                if (added) {
                    controlleter = true;
                    leterwordsreview.add(i + 1, otoname);
                    i++;
                }
                i++;
            }
        }
        removeDupes();
        return leterwordsreview;
    }

    public int[] getNextLettersFrequencies() {
        return aftersplingvalue;
    }

    private void removeDupes() {
        final ArrayList<CharSequence> suggestions = leterwordsreview;
        if (suggestions.size() < 2) return;
        int i = 1;
        while (i < suggestions.size()) {
            final CharSequence cur = suggestions.get(i);
            for (int j = 0; j < i; j++) {
                CharSequence befoure = suggestions.get(j);
                if (TextUtils.equals(cur, befoure)) {
                    removeFromSuggestions(i);
                    i--;
                    break;
                }
            }
            i++;
        }
    }

    private void removeFromSuggestions(int index) {
        CharSequence seqence = leterwordsreview.remove(index);
        if (seqence != null && seqence instanceof StringBuilder) {
            texrtaray.add(seqence);
        }
    }

    public boolean hasMinimalCorrection() {
        return controlleter;
    }

    private boolean compareCaseInsensitive(final String mLowerOriginalWord, final char[] word, final int offset, final int length) {
        final int originalLength = mLowerOriginalWord.length();
        if (originalLength == length && Character.isUpperCase(word[offset])) {
            for (int i = 0; i < originalLength; i++) {
                if (mLowerOriginalWord.charAt(i) != Character.toLowerCase(word[offset+i])) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean addWord(final char[] word, final int offset, final int length, int freq, final int dicTypeId, final Dictionary.DataType dataType) {
        Dictionary.DataType dataTypeForLog = dataType;
        ArrayList<CharSequence> review;
        int[] lybray;
        int prefMaxSuggestions;
        if(dataType == Dictionary.DataType.BIGRAM) {
            review = gramerreview;
            lybray = maximamspling;
            prefMaxSuggestions = PREF_MAX_BIGRAMS;
        } else {
            review = leterwordsreview;
            lybray = splingleter;
            prefMaxSuggestions = splingreview;
        }

        int pos = 0;

        if (compareCaseInsensitive(newsplingleter, word, offset, length)) {
            pos = 0;
        } else {
            if (dataType == Dictionary.DataType.UNIGRAM) {
                int bigramSuggestion = searchBigramSuggestion(word,offset,length);
                if(bigramSuggestion >= 0) {
                    dataTypeForLog = Dictionary.DataType.BIGRAM;
                    double multiplier = (((double) maximamspling[bigramSuggestion]) / MAXIMUM_BIGRAM_FREQUENCY) * (BIGRAM_MULTIPLIER_MAX - BIGRAM_MULTIPLIER_MIN) + BIGRAM_MULTIPLIER_MIN;
                    freq = (int)Math.round((freq * multiplier));
                }
            }
            if (lybray[prefMaxSuggestions - 1] >= freq) return true;
            while (pos < prefMaxSuggestions) {
                if (lybray[pos] < freq || (lybray[pos] == freq && length < review.get(pos).length())) {
                    break;
                }
                pos++;
            }
        }
        if (pos >= prefMaxSuggestions) {
            return true;
        }

        System.arraycopy(lybray, pos, lybray, pos + 1, prefMaxSuggestions - pos - 1);
        lybray[pos] = freq;
        int poolSize = texrtaray.size();
        StringBuilder bilder = poolSize > 0 ? (StringBuilder) texrtaray.remove(poolSize - 1) : new StringBuilder(getApproxMaxWordLength());
        bilder.setLength(0);
        if (valuetextlater) {
            bilder.append(new String(word, offset, length).toUpperCase());
        } else if (onevaluesplingtext) {
            bilder.append(Character.toUpperCase(word[offset]));
            if (length > 1) {
                bilder.append(word, offset + 1, length - 1);
            }
        } else {
            bilder.append(word, offset, length);
        }
        review.add(pos, bilder);
        if (review.size() > prefMaxSuggestions) {
            CharSequence sequnce = review.remove(prefMaxSuggestions);
            if (sequnce instanceof StringBuilder) {
                texrtaray.add(sequnce);
            }
        } else {
            My_Keybord_Imglong.onAddSuggestedWord(bilder.toString(), dicTypeId, dataTypeForLog);
        }
        return true;
    }

    private int searchBigramSuggestion(final char[] word, final int offset, final int length) {
        // TODO This is almost O(n^2). Might need fix.
        int bigramSuggestSize = gramerreview.size();
        for(int i = 0; i < bigramSuggestSize; i++) {
            if(gramerreview.get(i).length() == length) {
                boolean chk = true;
                for(int j = 0; j < length; j++) {
                    if(gramerreview.get(i).charAt(j) != word[offset+j]) {
                        chk = false;
                        break;
                    }
                }
                if(chk) return i;
            }
        }

        return -1;
    }

    public boolean isValidWord(final CharSequence word) {
        if (word == null || word.length() == 0) {
            return false;
        }
        return splingkeybord.isValidWord(word)
                || (gramar != null && gramar.isValidWord(word))
                || (otosplinglater != null && otosplinglater.isValidWord(word))
                || (splingwords != null && splingwords.isValidWord(word));
    }
    
    private void collectGarbage(ArrayList<CharSequence> suggestions, int prefMaxSuggestions) {
        int coolgesize = texrtaray.size();
        int gramer = suggestions.size();
        while (coolgesize < prefMaxSuggestions && gramer > 0) {
            CharSequence sequnce = suggestions.get(gramer - 1);
            if (sequnce != null && sequnce instanceof StringBuilder) {
                texrtaray.add(sequnce);
                coolgesize++;
            }
            gramer--;
        }
        if (coolgesize == prefMaxSuggestions + 1) {
            Log.w("My_Keyword_Inform_app", "String pool got too big: " + coolgesize);
        }
        suggestions.clear();
    }

    public void close() {
        if (splingkeybord != null) {
            splingkeybord.close();
        }
    }
}
