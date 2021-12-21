
package com.keyboard.keyboardthemes;


import java.util.Arrays;

import com.keyboard.keyboardthemes.Keyboard.Key;

class My_Keybord_proximity extends Set_dirKeyword {

    private static final int MAX_NEARBY_KEYS = 12;
    private int[] proximity = new int[MAX_NEARBY_KEYS];

    @Override
    protected int getMaxNearbyKeys() {
        return MAX_NEARBY_KEYS;
    }

    @Override
    public int getKeyIndexAndNearbyCodes(int x, int y, int[] allKeys) {
        final Key[] keys = getKeys();
        final int clickA = getTouchX(x);
        final int clickB = getTouchY(y);
        int valuename = My_Keybord_BaseImg_View.NOT_A_KEY;
        int offchavi = My_Keybord_BaseImg_View.NOT_A_KEY;
        int offderaction = positionvaluetext + 1;
        int[] jagya = proximity;
        Arrays.fill(jagya, Integer.MAX_VALUE);
        int [] valuclicktext = mytyping.getNearestKeys(clickA, clickB);
        final int chavinumber = valuclicktext.length;
        for (int i = 0; i < chavinumber; i++) {
            final Key chavi = keys[valuclicktext[i]];
            int position = 0;
            boolean isInside = chavi.isInside(clickA, clickB);
            if (isInside) {
                valuename = valuclicktext[i];
            }

            if (((positionvalue && (position = chavi.squaredDistanceFrom(clickA, clickB)) < positionvaluetext) || isInside) && chavi.codes[0] > 32) {
                final int nCodes = chavi.codes.length;
                if (position < offderaction) {
                    offderaction = position;
                    offchavi = valuclicktext[i];
                }

                if (allKeys == null) continue;

                for (int j = 0; j < jagya.length; j++) {
                    if (jagya[j] > position) {
                        System.arraycopy(jagya, j, jagya, j + nCodes, jagya.length - j - nCodes);
                        System.arraycopy(allKeys, j, allKeys, j + nCodes, allKeys.length - j - nCodes);
                        System.arraycopy(chavi.codes, 0, allKeys, j, nCodes);
                        Arrays.fill(jagya, j, j + nCodes, position);
                        break;
                    }
                }
            }
        }
        if (valuename == My_Keybord_BaseImg_View.NOT_A_KEY) {
            valuename = offchavi;
        }
        return valuename;
    }
}
