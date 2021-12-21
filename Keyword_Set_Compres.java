package com.keyboard.keyboardthemes;

import java.util.ArrayList;

public class Keyword_Set_Compres {

    private final ArrayList<int[]> arrayid;
    private String splingset;
    private final StringBuilder bilderstring;
    private int numcomopress;
    private boolean otocompress;
    private boolean onechareter;

    public Keyword_Set_Compres() {
        arrayid = new ArrayList<int[]>(12);
        bilderstring = new StringBuilder(20);
    }

    Keyword_Set_Compres(Keyword_Set_Compres tocoppy) {
        arrayid = new ArrayList<int[]>(tocoppy.arrayid);
        splingset = tocoppy.splingset;
        bilderstring = new StringBuilder(tocoppy.bilderstring);
        numcomopress = tocoppy.numcomopress;
        otocompress = tocoppy.otocompress;
        onechareter = tocoppy.onechareter;
    }

    public void reset() {
        arrayid.clear();
        onechareter = false;
        splingset = null;
        bilderstring.setLength(0);
        numcomopress = 0;
    }

    public int size()
    {
        return arrayid.size();
    }
    public int[] getCodesAt(int index) {
        return arrayid.get(index);
    }
    public void add(int primaryCode, int[] codes) {
        bilderstring.append((char) primaryCode);
        correctPrimaryJuxtapos(primaryCode, codes);
        correctCodesCase(codes);
        arrayid.add(codes);
        if (Character.isUpperCase((char) primaryCode)) numcomopress++;
    }

    private void correctPrimaryJuxtapos(int intid, int[] intsetid) {
        if (intsetid.length < 2) return;
        if (intsetid[0] > 0 && intsetid[1] > 0 && intsetid[0] != intid && intsetid[1] == intid) {
            intsetid[1] = intsetid[0];
            intsetid[0] = intid;
        }
    }

    private void correctCodesCase(int[] correct) {
        for (int i = 0; i < correct.length; ++i) {
            int code = correct[i];
            if (code > 0) correct[i] = Character.toLowerCase(code);
        }
    }
    
    public void deleteLast() {
        final int codesSize = arrayid.size();
        if (codesSize > 0) {
            arrayid.remove(codesSize - 1);
            final int endposizen = bilderstring.length() - 1;
            char last = bilderstring.charAt(endposizen);
            bilderstring.deleteCharAt(endposizen);
            if (Character.isUpperCase(last)) numcomopress--;
        }
    }

    public CharSequence getTypedWord() {
        int wordSize = arrayid.size();
        if (wordSize == 0) {
            return null;
        }
        return bilderstring;
    }

    public void setFirstCharCapitalized(boolean capitalized) {
        onechareter = capitalized;
    }
    
    public boolean isFirstCharCapitalized() {
        return onechareter;
    }
    public boolean isAllUpperCase() {
        return (numcomopress > 0) && (numcomopress == size());
    }

   public void setPreferredWord(String preferred) {
        splingset = preferred;
    }
    public CharSequence getPreferredWord() {
        return splingset != null ? splingset : getTypedWord();
    }

    public boolean isMostlyCaps() {
        return numcomopress > 1;
    }
    public void setAutoCapitalized(boolean auto) {
        otocompress = auto;
    }
    public boolean isAutoCapitalized() {
        return otocompress;
    }
}
