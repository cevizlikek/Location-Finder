package com.keyboard.keyboardthemes;

import android.content.res.Resources;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

import com.keyboard.keyboardthemes.Keyboard.Key;
import com.keyboard.keyboardthemes.My_Keybord_BaseImg_View.OnKeyboardActionListener;
import com.keyboard.keyboardthemes.My_Keybord_BaseImg_View.UIHandler;
import com.keyboard.keyboardthemes.R;


public class My_Keybord_Img_Treker {

    private static final String TAG = "My_Keybord_Img_Treker";
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_MOVE = false;
    private static final int NOT_A_KEY = My_Keybord_BaseImg_View.NOT_A_KEY;
    private static final int[] KEY_DELETE = {Keyboard.KEYCODE_DELETE};

    public interface UIProxy {

        public void invalidateKey(Key key);

        public void showPreview(int keyIndex, My_Keybord_Img_Treker tracker);
        public boolean hasDistinctMultitouch();

    }
    public final int treker;
    private final int priviresvalues;
    private final int allvaluebahar;
    private final UIProxy freevalueA;
    private final UIHandler uiheder;
    private final Set_dirKeyword diraction;
    private OnKeyboardActionListener clicklis;
    private final Typing_Switch typingword;
    private final boolean allclickword;
    private Key[] bords;
    private int textstyle = -1;
    private final KeyState texttrecer;
    private boolean textremove;
    private boolean textlaterloding;
    private boolean doublekeyword;
    private boolean swabviewtext;
    private int endvalue;
    private int numberleter;
    private long endwatchtime;
    private boolean bigvalue;
    private final StringBuilder beforebilder = new StringBuilder(1);
    private int beforenotkey = NOT_A_KEY;
    private static boolean swabkey;
    private static List<Key> swabelode = new ArrayList<Key>(10);

    private static class KeyState {
        private final Set_dirKeyword derctionvalue;
        private int onvalueA;
        private int onvalueB;
        private long endtime;
        private int chaviindex = NOT_A_KEY;
        private int chavevalueA;
        private int chavevalueB;
        private int endvalueA;
        private int endvalueB;

        public KeyState(Set_dirKeyword keyDetecor) {
            derctionvalue = keyDetecor;
        }

        public int getKeyIndex() {
            return chaviindex;
        }

        public int getKeyX() {
            return chavevalueA;
        }

        public int getKeyY() {
            return chavevalueB;
        }

        public int getStartX() {
            return onvalueA;
        }

        public int getStartY() {
            return onvalueB;
        }

        public long getDownTime() {
            return endtime;
        }

        public int getLastX() {
            return endvalueA;
        }

        public int getLastY() {
            return endvalueB;
        }

        public int onDownKey(int x, int y, long eventTime) {
            onvalueA = x;
            onvalueB = y;
            endtime = eventTime;

            return onMoveToNewKey(onMoveKeyInternal(x, y), x, y);
        }

        private int onMoveKeyInternal(int x, int y) {
            endvalueA = x;
            endvalueB = y;
            return derctionvalue.getKeyIndexAndNearbyCodes(x, y, null);
        }

        public int onMoveKey(int x, int y) {
            return onMoveKeyInternal(x, y);
        }

        public int onMoveToNewKey(int keyIndex, int x, int y) {
            chaviindex = keyIndex;
            chavevalueA = x;
            chavevalueB = y;
            return keyIndex;
        }

        public int onUpKey(int x, int y) {
            return onMoveKeyInternal(x, y);
        }
    }

    public My_Keybord_Img_Treker(int traker, UIHandler uihder, Set_dirKeyword dirctoin, UIProxy proxy, Resources rid, boolean slideKeyHack) {
        if (proxy == null || uihder == null || dirctoin == null) throw new NullPointerException();
        treker = traker;
        freevalueA = proxy;
        uiheder = uihder;
        diraction = dirctoin;
        typingword = Typing_Switch.getInstance();
        texttrecer = new KeyState(dirctoin);
        allclickword = proxy.hasDistinctMultitouch();
        priviresvalues = rid.getInteger(R.integer.config_delay_before_key_repeat_start);
        allvaluebahar = rid.getInteger(R.integer.config_multi_tap_key_timeout);
        swabkey = slideKeyHack;
        resetMultiTap();
    }

    public void setOnKeyboardActionListener(OnKeyboardActionListener listener) {
        clicklis = listener;
    }

    public void setKeyboard(Key[] keys, float keyHysteresisDistance) {
        if (keys == null || keyHysteresisDistance < 0)
            throw new IllegalArgumentException();
        bords = keys;
        textstyle = (int) (keyHysteresisDistance * keyHysteresisDistance);
        textremove = true;
    }

    public boolean isInSlidingKeyInput() {
        return swabviewtext;
    }

    public void setSlidingKeyInputState(boolean state) {
        swabviewtext = state;
    }

    private boolean isValidKeyIndex(int keyIndex) {
        return keyIndex >= 0 && keyIndex < bords.length;
    }

    public Key getKey(int keyIndex) {
        return isValidKeyIndex(keyIndex) ? bords[keyIndex] : null;
    }

    private boolean isModifierInternal(int keyIndex) {
        Key key = getKey(keyIndex);
        if (key == null || key.codes == null)
            return false;
        int primaryCode = key.codes[0];
        return primaryCode == Keyboard.KEYCODE_SHIFT || primaryCode == Keyboard.KEYCODE_MODE_CHANGE || primaryCode == My_Keybord_Img_Key.KEYCODE_CTRL_LEFT || primaryCode == My_Keybord_Img_Key.KEYCODE_ALT_LEFT || primaryCode == My_Keybord_Img_Key.KEYCODE_META_LEFT || primaryCode == My_Keybord_Img_Key.KEYCODE_FN;
    }

    public boolean isModifier() {
        return isModifierInternal(texttrecer.getKeyIndex());
    }

    public boolean isOnModifierKey(int x, int y) {
        return isModifierInternal(diraction.getKeyIndexAndNearbyCodes(x, y, null));
    }

    public boolean isSpaceKey(int keyIndex) {
        Key key = getKey(keyIndex);
        return key != null && key.codes != null && key.codes[0] == LatinIME.ASCII_SPACE;
    }

    public void updateKey(int keyIndex) {
        if (textlaterloding)
            return;
        int oldKeyIndex = beforenotkey;
        beforenotkey = keyIndex;
        if (keyIndex != oldKeyIndex) {
            if (isValidKeyIndex(oldKeyIndex)) {
                final boolean inside = (keyIndex == NOT_A_KEY);
                bords[oldKeyIndex].onReleased(inside);
                freevalueA.invalidateKey(bords[oldKeyIndex]);
            }
            if (isValidKeyIndex(keyIndex)) {
                bords[keyIndex].onPressed();
                freevalueA.invalidateKey(bords[keyIndex]);
            }
        }
    }

    public void setAlreadyProcessed() {
        textlaterloding = true;
    }

    public void onTouchEvent(int action, int x, int y, long eventTime) {
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                onMoveEvent(x, y, eventTime);
                break;
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                onDownEvent(x, y, eventTime);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                onUpEvent(x, y, eventTime);
                break;
            case MotionEvent.ACTION_CANCEL:
                onCancelEvent(x, y, eventTime);
                break;
        }
    }

    public void onDownEvent(int x, int y, long eventTime) {
        if (DEBUG)
            debugLog("onDownEvent:", x, y);
        int keyIndex = texttrecer.onDownKey(x, y, eventTime);
        textremove = false;
        textlaterloding = false;
        doublekeyword = false;
        swabviewtext = false;
        checkMultiTap(eventTime, keyIndex);
        if (clicklis != null) {
            if (isValidKeyIndex(keyIndex)) {
                Key chavi = bords[keyIndex];
                if (chavi.codes != null) clicklis.onPress(chavi.getPrimaryCode());
                if (textremove) {
                    textremove = false;
                    keyIndex = texttrecer.onDownKey(x, y, eventTime);
                }
            }
        }
        if (isValidKeyIndex(keyIndex)) {
            if (bords[keyIndex].repeatable) {
                repeatKey(keyIndex);
                uiheder.startKeyRepeatTimer(priviresvalues, keyIndex, this);
                doublekeyword = true;
            }
            startLongPressTimer(keyIndex);
        }
        showKeyPreviewAndUpdateKey(keyIndex);
    }

    private static void addSlideKey(Key key) {
        if (!swabkey || LatinIME.service.uplodekeybord == 0) return;
        if (key == null) return;
        if (key.modifier) {
            clearSlideKeys();
        } else {
            swabelode.add(key);
        }
    }

    static void clearSlideKeys() {
        swabelode.clear();
    }

    void sendSlideKeys() {
        if (!swabkey) return;
        int slideMode = LatinIME.service.uplodekeybord;
        if ((slideMode & 4) > 0) {
            for (Key key : swabelode) {
                detectAndSendKey(key, key.x, key.y, -1);
            }
        } else {
            int n = swabelode.size();
            if (n > 0 && (slideMode & 1) > 0) {
                Key key = swabelode.get(0);
                detectAndSendKey(key, key.x, key.y, -1);
            }
            if (n > 1 && (slideMode & 2) > 0) {
                Key chavi = swabelode.get(n - 1);
                detectAndSendKey(chavi, chavi.x, chavi.y, -1);
            }
        }
        clearSlideKeys();
    }

    public void onMoveEvent(int x, int y, long eventTime) {
        if (DEBUG_MOVE)
            debugLog("onMoveEvent:", x, y);
        if (textlaterloding)
            return;
        final KeyState keyState = texttrecer;
        int keyIndex = keyState.onMoveKey(x, y);
        final Key oldKey = getKey(keyState.getKeyIndex());
        if (isValidKeyIndex(keyIndex)) {
            boolean isMinorMoveBounce = isMinorMoveBounce(x, y, keyIndex);
            if (DEBUG_MOVE)
                Log.i(TAG, "isMinorMoveBounce=" + isMinorMoveBounce + " oldKey=" + (oldKey == null ? "null" : oldKey));
            if (oldKey == null) {
                if (clicklis != null) {
                    Key key = getKey(keyIndex);
                    if (key.codes != null) clicklis.onPress(key.getPrimaryCode());
                    if (textremove) {
                        textremove = false;
                        keyIndex = keyState.onMoveKey(x, y);
                    }
                }
                keyState.onMoveToNewKey(keyIndex, x, y);
                startLongPressTimer(keyIndex);
            } else if (!isMinorMoveBounce) {
                swabviewtext = true;
                if (clicklis != null && oldKey.codes != null)
                    clicklis.onRelease(oldKey.getPrimaryCode());
                resetMultiTap();
                if (clicklis != null) {
                    Key key = getKey(keyIndex);
                    if (key.codes != null) clicklis.onPress(key.getPrimaryCode());
                    if (textremove) {
                        textremove = false;
                        keyIndex = keyState.onMoveKey(x, y);
                    }
                    addSlideKey(oldKey);
                }
                keyState.onMoveToNewKey(keyIndex, x, y);
                startLongPressTimer(keyIndex);
            }
        } else {
            if (oldKey != null && !isMinorMoveBounce(x, y, keyIndex)) {
                swabviewtext = true;
                if (clicklis != null && oldKey.codes != null)
                    clicklis.onRelease(oldKey.getPrimaryCode());
                resetMultiTap();
                keyState.onMoveToNewKey(keyIndex, x, y);
                uiheder.cancelLongPressTimer();
            }
        }
        showKeyPreviewAndUpdateKey(keyState.getKeyIndex());
    }

    public void onUpEvent(int x, int y, long eventTime) {
        if (DEBUG) debugLog("onUpEvent  :", x, y);
        uiheder.cancelKeyTimers();
        uiheder.cancelPopupPreview();
        showKeyPreviewAndUpdateKey(NOT_A_KEY);
        swabviewtext = false;
        sendSlideKeys();
        if (textlaterloding)
            return;
        int keyIndex = texttrecer.onUpKey(x, y);
        if (isMinorMoveBounce(x, y, keyIndex)) {
            keyIndex = texttrecer.getKeyIndex();
            x = texttrecer.getKeyX();
            y = texttrecer.getKeyY();
        }
        if (!doublekeyword) {
            detectAndSendKey(keyIndex, x, y, eventTime);
        }

        if (isValidKeyIndex(keyIndex))
            freevalueA.invalidateKey(bords[keyIndex]);
    }

    public void onCancelEvent(int x, int y, long eventTime) {
        if (DEBUG) debugLog("onCancelEvt:", x, y);
        uiheder.cancelKeyTimers();
        uiheder.cancelPopupPreview();
        showKeyPreviewAndUpdateKey(NOT_A_KEY);
        swabviewtext = false;
        int keyIndex = texttrecer.getKeyIndex();
        if (isValidKeyIndex(keyIndex))
            freevalueA.invalidateKey(bords[keyIndex]);
    }

    public void repeatKey(int keyIndex) {
        Key chavi = getKey(keyIndex);
        if (chavi != null) {
            detectAndSendKey(keyIndex, chavi.x, chavi.y, -1);
        }
    }

    public int getLastX() {
        return texttrecer.getLastX();
    }

    public int getLastY() {
        return texttrecer.getLastY();
    }

    public long getDownTime() {
        return texttrecer.getDownTime();
    }

    int getStartX() {
        return texttrecer.getStartX();
    }

    int getStartY() {
        return texttrecer.getStartY();
    }

    private boolean isMinorMoveBounce(int x, int y, int newKey) {
        if (bords == null || textstyle < 0)
            throw new IllegalStateException("keyboard and/or hysteresis not set");
        int curKey = texttrecer.getKeyIndex();
        if (newKey == curKey) {
            return true;
        } else if (isValidKeyIndex(curKey)) {
// TODO(klausw): tweak this?
            return getSquareDistanceToKeyEdge(x, y, bords[curKey]) < textstyle;
        } else {
            return false;
        }
    }

    private static int getSquareDistanceToKeyEdge(int x, int y, Key key) {
        final int left = key.x;
        final int right = key.x + key.width;
        final int top = key.y;
        final int bottom = key.y + key.height;
        final int edgeX = x < left ? left : (x > right ? right : x);
        final int edgeY = y < top ? top : (y > bottom ? bottom : y);
        final int dx = x - edgeX;
        final int dy = y - edgeY;
        return dx * dx + dy * dy;
    }

    private void showKeyPreviewAndUpdateKey(int keyIndex) {
        updateKey(keyIndex);
        if (allclickword && isModifier()) {
            freevalueA.showPreview(NOT_A_KEY, this);
        } else {
            freevalueA.showPreview(keyIndex, this);
        }
    }

    private void startLongPressTimer(int keyIndex) {
        if (typingword.isInMomentaryAutoModeSwitchState()) {
            uiheder.startLongPressTimer(LatinIME.service.seekbarwatch * 3, keyIndex, this);
        } else {
            uiheder.startLongPressTimer(LatinIME.service.seekbarwatch, keyIndex, this);
        }
    }

    private void detectAndSendKey(int index, int x, int y, long eventTime) {
        detectAndSendKey(getKey(index), x, y, eventTime);
        endvalue = index;
    }

    private void detectAndSendKey(Key key, int x, int y, long eventTime) {
        final OnKeyboardActionListener listener = clicklis;

        if (key == null) {
            if (listener != null)
                listener.onCancel();
        } else {
            if (key.text != null) {
                if (listener != null) {
                    listener.onText(key.text);
                    listener.onRelease(0);
                }
            } else {
                if (key.codes == null) return;
                int code = key.getPrimaryCode();
                int[] codes = diraction.newCodeArray();
                diraction.getKeyIndexAndNearbyCodes(x, y, codes);
                if (bigvalue) {
                    if (numberleter != -1) {
                        clicklis.onKey(Keyboard.KEYCODE_DELETE, KEY_DELETE, x, y);
                    } else {
                        numberleter = 0;
                    }
                    code = key.codes[numberleter];
                }
                if (codes.length >= 2 && codes[0] != code && codes[1] == code) {
                    codes[1] = codes[0];
                    codes[0] = code;
                }
                if (listener != null) {
                    listener.onKey(code, codes, x, y);
                    listener.onRelease(code);
                }
            }
            endwatchtime = eventTime;
        }
    }

    public CharSequence getPreviewText(Key key) {
        if (bigvalue) {
            beforebilder.setLength(0);
            beforebilder.append((char) key.codes[numberleter < 0 ? 0 : numberleter]);
            return beforebilder;
        } else {
            if (key.isDeadKey()) {
                return Set_Symbole_Keybord.normalize(" " + key.label);
            } else {
                return key.label;
            }
        }
    }

    private void resetMultiTap() {
        endvalue = NOT_A_KEY;
        numberleter = 0;
        endwatchtime = -1;
        bigvalue = false;
    }

    private void checkMultiTap(long eventTime, int keyIndex) {
        Key chavi = getKey(keyIndex);
        if (chavi == null || chavi.codes == null)
            return;

        final boolean isMultiTap = (eventTime < endwatchtime + allvaluebahar && keyIndex == endvalue);
        if (chavi.codes.length > 1) {
            bigvalue = true;
            if (isMultiTap) {
                numberleter = (numberleter + 1) % chavi.codes.length;
                return;
            } else {
                numberleter = -1;
                return;
            }
        }
        if (!isMultiTap) {
            resetMultiTap();
        }
    }

    private void debugLog(String title, int x, int y) {
        int keyIndex = diraction.getKeyIndexAndNearbyCodes(x, y, null);
        Key chavi = getKey(keyIndex);
        final String code;
        if (chavi == null || chavi.codes == null) {
            code = "----";
        } else {
            int primaryCode = chavi.codes[0];
            code = String.format((primaryCode < 0) ? "%4d" : "0x%02x", primaryCode);
        }
        Log.d(TAG, String.format("%s%s[%d] %3d,%3d %3d(%s) %s", title, (textlaterloding ? "-" : " "), treker, x, y, keyIndex, code, (isModifier() ? "modifier" : "")));
    }
}
