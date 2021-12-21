
package com.keyboard.keyboardthemes;


import java.util.Arrays;
import java.util.List;

import com.keyboard.keyboardthemes.Keyboard.Key;

abstract class Set_dirKeyword {

    protected Keyboard mytyping;
    private Key[] chavi;
    protected int keyuseA;
    protected int keyuseB;
    protected boolean positionvalue;
    protected int positionvaluetext;

    public Key[] setKeyboard(Keyboard keyboard, float correctionX, float correctionY) {
        if (keyboard == null)
            throw new NullPointerException();
        keyuseA = (int)correctionX;
        keyuseB = (int)correctionY;
        mytyping = keyboard;
        List<Key> chavi = mytyping.getKeys();
        Key[] array = chavi.toArray(new Key[chavi.size()]);
        this.chavi = array;
        return array;
    }

    protected int getTouchX(int x) {
        return x + keyuseA;
    }

    protected int getTouchY(int y) {
        return y + keyuseB;
    }

    protected Key[] getKeys() {
        if (chavi == null)
            throw new IllegalStateException("keyboard isn't set");
        return chavi;
    }

    public void setProximityCorrectionEnabled(boolean enabled) {
        positionvalue = enabled;
    }

    public boolean isProximityCorrectionEnabled() {
        return positionvalue;
    }

    public void setProximityThreshold(int threshold) {
        positionvaluetext = threshold * threshold;
    }

    public int[] newCodeArray() {
        int[] values = new int[getMaxNearbyKeys()];
        Arrays.fill(values, My_Keybord_BaseImg_View.NOT_A_KEY);
        return values;
    }

    abstract protected int getMaxNearbyKeys();

    abstract public int getKeyIndexAndNearbyCodes(int x, int y, int[] allKeys);
}
