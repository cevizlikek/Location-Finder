
package com.keyboard.keyboardthemes;

class ModifierKeyState {
    private static final int MODIFIZERO = 0;
    private static final int MODIFIONE = 1;
    private static final int MODIFITWO = 2;

    private int mState = MODIFIZERO;

    public void onPress() {
        mState = MODIFIONE;
    }

    public void onRelease() {
        mState = MODIFIZERO;
    }

    public void onOtherKeyPressed() {
        if (mState == MODIFIONE)
            mState = MODIFITWO;
    }

    public boolean isChording() {
        return mState == MODIFITWO;
    }
    
    public String toString() {
    	return "ModifierKeyState:" + mState;
    }
}
