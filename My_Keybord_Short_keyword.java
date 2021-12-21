
package com.keyboard.keyboardthemes;

import com.keyboard.keyboardthemes.Keyboard.Key;

class My_Keybord_Short_keyword extends Set_dirKeyword {

    private static final int MAX_NEARBY_KEYS = 1;
    private final int shortkeybord;
    private final int swabshortkeybord;

    public My_Keybord_Short_keyword(float slideAllowance) {
        super();
        shortkeybord = (int)(slideAllowance * slideAllowance);
        swabshortkeybord = shortkeybord * 2;
    }

    @Override
    protected int getMaxNearbyKeys() {
        return MAX_NEARBY_KEYS;
    }

    @Override
    public int getKeyIndexAndNearbyCodes(int x, int y, int[] allKeys) {
        final Key[] chavi = getKeys();
        final int clickA = getTouchX(x);
        final int clickB = getTouchY(y);
        int notvalueA = My_Keybord_BaseImg_View.NOT_A_KEY;
        int offshort = (y < 0) ? swabshortkeybord : shortkeybord;
        final int chavinumber = chavi.length;
        for (int i = 0; i < chavinumber; i++) {
            final Key intchavi = chavi[i];
            int clickvalue = intchavi.squaredDistanceFrom(clickA, clickB);
            if (clickvalue < offshort) {
                notvalueA = i;
                offshort = clickvalue;
            }
        }
        if (allKeys != null && notvalueA != My_Keybord_BaseImg_View.NOT_A_KEY)
            allKeys[0] = chavi[notvalueA].getPrimaryCode();
        return notvalueA;
    }
}
