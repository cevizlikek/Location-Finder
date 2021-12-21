package com.keyboard.keyboardthemes;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;

import com.keyboard.keyboardthemes.R;

import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;



public class Keyboard {

    static final String TAG = "Keyboard";
    public final static char DEAD_KEY_PLACEHOLDER = 0x25cc;
    public final static String DEAD_KEY_PLACEHOLDER_STRING = Character.toString(DEAD_KEY_PLACEHOLDER);
    private static final String TAG_KEYBOARD = "Keyboard";
    private static final String TAG_ROW = "Row";
    private static final String TAG_KEY = "Key";
    public static final int EDGE_LEFT = 0x01;
    public static final int EDGE_RIGHT = 0x02;
    public static final int EDGE_TOP = 0x04;
    public static final int EDGE_BOTTOM = 0x08;
    public static final int KEYCODE_SHIFT = -1;
    public static final int KEYCODE_MODE_CHANGE = -2;
    public static final int KEYCODE_CANCEL = -3;
    public static final int KEYCODE_DONE = -4;
    public static final int KEYCODE_DELETE = -5;
    public static final int KEYCODE_ALT_SYM = -6;
    public static final int DEFAULT_LAYOUT_ROWS = 4;
    public static final int DEFAULT_LAYOUT_COLUMNS = 10;
    public static final int POPUP_ADD_SHIFT = 1;
    public static final int POPUP_ADD_CASE = 2;
    public static final int POPUP_ADD_SELF = 4;
    public static final int POPUP_DISABLE = 256;
    public static final int POPUP_AUTOREPEAT = 512;
    private float horzontlflote;
    private float horzntlkeyword;
    private float keywordvrtcal;
    private float keywordW;
    private int keywordH;
    private int keywordspace;
    public static final int SHIFT_OFF = 0;
    public static final int SHIFT_ON = 1;
    public static final int SHIFT_LOCKED = 2;
    public static final int SHIFT_CAPS = 3;
    public static final int SHIFT_CAPS_LOCKED = 4;
    private int keywordoff = SHIFT_OFF;
    private Key keywordshift;
    private Key keywordalter;
    private Key keywordcontrol;
    private Key keywordmetr;
    private int shiftvalue = -1;
    private int allkeywordH;
    private int mTotalWidth;
    private List<Key> listkey;
    private List<Key> mModifierKeys;
    private int mDisplayWidth;
    private int mDisplayHeight;
    private int intkeywordHight;
    private int intkeywordmoad;
    private boolean boolenkeyword;
    public int intrawo;
    public int intcolum;
    public int intraownum = 1;
    public int keywordraownum = 0;
    private int intwethkey;
    private int inthightkey;
    private int[][] intgridview;
    private int intkeywordproxi;
    private static float SEARCH_DISTANCE = 1.8f;

    public static class Row {
        public float rowW;
        public int introwh;
        public float defaultHorizontalGap;
        public int verticalGap;
        public int mode;
        public boolean extension;
        private Keyboard parent;

        public Row(Keyboard parent) {
            this.parent = parent;
        }

        public Row(Resources keywordsourch, Keyboard keywordprsent, XmlResourceParser xmlresourch) {
            this.parent = keywordprsent;

            TypedArray array = keywordsourch.obtainAttributes(Xml.asAttributeSet(xmlresourch), R.styleable.Keyboard);
            rowW = getDimensionOrFraction(array, R.styleable.Keyboard_keyWidth, keywordprsent.mDisplayWidth, keywordprsent.keywordW);
            introwh = Math.round(getDimensionOrFraction(array, R.styleable.Keyboard_keyHeight, keywordprsent.mDisplayHeight, keywordprsent.keywordH));
            defaultHorizontalGap = getDimensionOrFraction(array, R.styleable.Keyboard_horizontalGap, keywordprsent.mDisplayWidth, keywordprsent.horzontlflote);
            verticalGap = Math.round(getDimensionOrFraction(array, R.styleable.Keyboard_verticalGap, keywordprsent.mDisplayHeight, keywordprsent.keywordspace));
            array.recycle();
            array = keywordsourch.obtainAttributes(Xml.asAttributeSet(xmlresourch), R.styleable.Keyboard_Row);
            mode = array.getResourceId(R.styleable.Keyboard_Row_keyboardMode, 0);
            extension = array.getBoolean(R.styleable.Keyboard_Row_extension, false);

            if (keywordprsent.intrawo >= 5) {
                boolean isTop = (extension || keywordprsent.intraownum - keywordprsent.keywordraownum <= 0);
                float topScale = LatinIME.service.stratsize;
                float scale = isTop ? topScale : 1.0f + (1.0f - topScale) / (keywordprsent.intrawo - 1);
                introwh = Math.round(introwh * scale);
            }
            array.recycle();
        }
    }

    public static class Key {
        public int[] codes;
        public CharSequence label;
        public int width;
        private float realWidth;
        public int height;
        public int gap;
        public Drawable icon;
        public CharSequence shiftLabel;
        public int y;
        public Drawable iconPreview;
        public boolean sticky;
        public int x;
        public String altHint;
        private float realGap;
        public int edgeFlags;
        public boolean on;
        public int popupResId;
        public CharSequence capsLabel;
        public boolean modifier;
        private float realX;
        public boolean locked;
        public boolean pressed;
        public CharSequence popupCharacters;
        public boolean popupReversed;
        public CharSequence text;
        public boolean isCursor;
        private Keyboard keyboard;
        private boolean isDistinctUppercase;
        public String hint;
        public boolean repeatable;
        private boolean isSimpleUppercase;

        private final static int[] KEY_STATE_NORMAL_ON = {
                android.R.attr.state_checkable,
                android.R.attr.state_checked
        };

        private final static int[] KEY_STATE_PRESSED_ON = {
                android.R.attr.state_pressed,
                android.R.attr.state_checkable,
                android.R.attr.state_checked
        };

        private final static int[] KEY_STATE_NORMAL_LOCK = {
                android.R.attr.state_active,
                android.R.attr.state_checkable,
                android.R.attr.state_checked
        };

        private final static int[] KEY_STATE_PRESSED_LOCK = {
                android.R.attr.state_active,
                android.R.attr.state_pressed,
                android.R.attr.state_checkable,
                android.R.attr.state_checked
        };

        private final static int[] KEY_STATE_NORMAL_OFF = {
                android.R.attr.state_checkable
        };

        private final static int[] KEY_STATE_PRESSED_OFF = {
                android.R.attr.state_pressed,
                android.R.attr.state_checkable
        };

        private final static int[] KEY_STATE_NORMAL = {
        };

        private final static int[] KEY_STATE_PRESSED = {
                android.R.attr.state_pressed
        };

        public Key(Row parent) {
            keyboard = parent.parent;
            height = parent.introwh;
            width = Math.round(parent.rowW);
            realWidth = parent.rowW;
            gap = Math.round(parent.defaultHorizontalGap);
            realGap = parent.defaultHorizontalGap;
        }

        public Key(Resources res, Row parent, int x, int y, XmlResourceParser parser) {
            this(parent);

            this.x = x;
            this.y = y;

            TypedArray array = res.obtainAttributes(Xml.asAttributeSet(parser), R.styleable.Keyboard);

            realWidth = getDimensionOrFraction(array, R.styleable.Keyboard_keyWidth, keyboard.mDisplayWidth, parent.rowW);
            float size = getDimensionOrFraction(array, R.styleable.Keyboard_keyHeight, keyboard.mDisplayHeight, parent.introwh);
            size -= parent.parent.keywordvrtcal;
            height = Math.round(size);
            this.y += parent.parent.keywordvrtcal / 2;
            realGap = getDimensionOrFraction(array, R.styleable.Keyboard_horizontalGap, keyboard.mDisplayWidth, parent.defaultHorizontalGap);
            realGap += parent.parent.horzntlkeyword;
            realWidth -= parent.parent.horzntlkeyword;
            width = Math.round(realWidth);
            gap = Math.round(realGap);
            array.recycle();
            array = res.obtainAttributes(Xml.asAttributeSet(parser), R.styleable.Keyboard_Key);
            this.realX = this.x + realGap - parent.parent.horzntlkeyword / 2;
            this.x = Math.round(this.realX);
            TypedValue codesValue = new TypedValue();
            array.getValue(R.styleable.Keyboard_Key_codes, codesValue);
            if (codesValue.type == TypedValue.TYPE_INT_DEC || codesValue.type == TypedValue.TYPE_INT_HEX) {
                codes = new int[]{codesValue.data};
            } else if (codesValue.type == TypedValue.TYPE_STRING) {
                codes = parseCSV(codesValue.string.toString());
            }

            iconPreview = array.getDrawable(R.styleable.Keyboard_Key_iconPreview);
            if (iconPreview != null) {
                iconPreview.setBounds(0, 0, iconPreview.getIntrinsicWidth(), iconPreview.getIntrinsicHeight());
            }
            popupCharacters = array.getText(R.styleable.Keyboard_Key_popupCharacters);
            popupResId = array.getResourceId(R.styleable.Keyboard_Key_popupKeyboard, 0);
            repeatable = array.getBoolean(R.styleable.Keyboard_Key_isRepeatable, false);
            modifier = array.getBoolean(R.styleable.Keyboard_Key_isModifier, false);
            sticky = array.getBoolean(R.styleable.Keyboard_Key_isSticky, false);
            isCursor = array.getBoolean(R.styleable.Keyboard_Key_isCursor, false);
            icon = array.getDrawable(R.styleable.Keyboard_Key_keyIcon);
            if (icon != null) {
                icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
            }
            label = array.getText(R.styleable.Keyboard_Key_keyLabel);
            shiftLabel = array.getText(R.styleable.Keyboard_Key_shiftLabel);
            if (shiftLabel != null && shiftLabel.length() == 0) shiftLabel = null;
            capsLabel = array.getText(R.styleable.Keyboard_Key_capsLabel);
            if (capsLabel != null && capsLabel.length() == 0) capsLabel = null;
            text = array.getText(R.styleable.Keyboard_Key_keyOutputText);

            if (codes == null && !TextUtils.isEmpty(label)) {
                codes = getFromString(label);
                if (codes != null && codes.length == 1) {
                    final Locale locale = LatinIME.service.defultevalue;
                    String upperLabel = label.toString().toUpperCase(locale);
                    if (shiftLabel == null) {
                        if (!upperLabel.equals(label.toString()) && upperLabel.length() == 1) {
                            shiftLabel = upperLabel;
                            isSimpleUppercase = true;
                        }
                    } else {
                        if (capsLabel != null) {
                            isDistinctUppercase = true;
                        } else if (upperLabel.equals(shiftLabel.toString())) {
                            isSimpleUppercase = true;
                        } else if (upperLabel.length() == 1) {
                            capsLabel = upperLabel;
                            isDistinctUppercase = true;
                        }
                    }
                }
                if ((LatinIME.service.zerobyone & POPUP_DISABLE) != 0) {
                    popupCharacters = null;
                    popupResId = 0;
                }
                if ((LatinIME.service.zerobyone & POPUP_AUTOREPEAT) != 0) {
                    repeatable = true;
                }
            }
            array.recycle();
        }

        public boolean isDistinctCaps() {
            return isDistinctUppercase && keyboard.isShiftCaps();
        }

        public boolean isShifted() {
            boolean shifted = keyboard.isShifted(isSimpleUppercase);
            //Log.i(TAG, "FIXME isShifted=" + shifted + " for " + this);
            return shifted;
        }

        public int getPrimaryCode(boolean isShiftCaps, boolean isShifted) {
            if (isDistinctUppercase && isShiftCaps) {
                return capsLabel.charAt(0);
            }
            if (isShifted && shiftLabel != null) {
                if (shiftLabel.charAt(0) == DEAD_KEY_PLACEHOLDER && shiftLabel.length() >= 2) {
                    return shiftLabel.charAt(1);
                } else {
                    return shiftLabel.charAt(0);
                }
            } else {
                return codes[0];
            }
        }

        public int getPrimaryCode() {
            return getPrimaryCode(keyboard.isShiftCaps(), keyboard.isShifted(isSimpleUppercase));
        }

        public boolean isDeadKey() {
            if (codes == null || codes.length < 1) return false;
            return Character.getType(codes[0]) == Character.NON_SPACING_MARK;
        }

        public int[] getFromString(CharSequence seqnce) {
            if (seqnce.length() > 1) {
                if (seqnce.charAt(0) == DEAD_KEY_PLACEHOLDER && seqnce.length() >= 2) {
                    return new int[]{seqnce.charAt(1)}; // FIXME: >1 length?
                } else {
                    text = seqnce; // TODO: add space?
                    return new int[]{0};
                }
            } else {
                char attres = seqnce.charAt(0);
                return new int[]{attres};
            }
        }

        public String getCaseLabel() {
            if (isDistinctUppercase && keyboard.isShiftCaps()) {
                return capsLabel.toString();
            }
            boolean isShifted = keyboard.isShifted(isSimpleUppercase);
            if (isShifted && shiftLabel != null) {
                return shiftLabel.toString();
            } else {
                return label != null ? label.toString() : null;
            }
        }

        private String getPopupKeyboardContent(boolean isShiftCaps, boolean isShifted, boolean addExtra) {
            int mainChar = getPrimaryCode(false, false);
            int shiftChar = getPrimaryCode(false, true);
            int capsChar = getPrimaryCode(true, true);

            if (shiftChar == mainChar) shiftChar = 0;
            if (capsChar == shiftChar || capsChar == mainChar) capsChar = 0;

            int popupLen = (popupCharacters == null) ? 0 : popupCharacters.length();
            StringBuilder popup = new StringBuilder(popupLen);
            for (int i = 0; i < popupLen; ++i) {
                char charecter = popupCharacters.charAt(i);
                if (isShifted || isShiftCaps) {
                    String upper = Character.toString(charecter).toUpperCase(LatinIME.service.defultevalue);
                    if (upper.length() == 1) charecter = upper.charAt(0);
                }

                if (charecter == mainChar || charecter == shiftChar || charecter == capsChar) continue;
                popup.append(charecter);
            }

            if (addExtra) {
                StringBuilder bulider = new StringBuilder(3 + popup.length());
                int keywords = LatinIME.service.zerobyone;
                if ((keywords & POPUP_ADD_SELF) != 0) {
                    if (isDistinctUppercase && isShiftCaps) {
                        if (capsChar > 0) {
                            bulider.append((char) capsChar);
                            capsChar = 0;
                        }
                    } else if (isShifted) {
                        if (shiftChar > 0) {
                            bulider.append((char) shiftChar);
                            shiftChar = 0;
                        }
                    } else {
                        if (mainChar > 0) {
                            bulider.append((char) mainChar);
                            mainChar = 0;
                        }
                    }
                }

                if ((keywords & POPUP_ADD_CASE) != 0) {
                    if (isDistinctUppercase && isShiftCaps) {
                        if (mainChar > 0) {
                            bulider.append((char) mainChar);
                            mainChar = 0;
                        }
                        if (shiftChar > 0) {
                            bulider.append((char) shiftChar);
                            shiftChar = 0;
                        }
                    } else if (isShifted) {
                        if (mainChar > 0) {
                            bulider.append((char) mainChar);
                            mainChar = 0;
                        }
                        if (capsChar > 0) {
                            bulider.append((char) capsChar);
                            capsChar = 0;
                        }
                    } else {
                        if (shiftChar > 0) {
                            bulider.append((char) shiftChar);
                            shiftChar = 0;
                        }
                        if (capsChar > 0) {
                            bulider.append((char) capsChar);
                            capsChar = 0;
                        }
                    }
                }

                if (!isSimpleUppercase && (keywords & POPUP_ADD_SHIFT) != 0) {
                    if (isShifted) {
                        if (mainChar > 0) {
                            bulider.append((char) mainChar);
                            mainChar = 0;
                        }
                    } else {
                        if (shiftChar > 0) {
                            bulider.append((char) shiftChar);
                            shiftChar = 0;
                        }
                    }
                }

                bulider.append(popup);
                return bulider.toString();
            }

            return popup.toString();
        }

        public Keyboard getPopupKeyboard(Context context, int padding) {
            if (popupCharacters == null) {
                if (popupResId != 0) {
                    return new Keyboard(context, keyboard.keywordH, popupResId);
                } else {
                    if (modifier) return null;
                }
            }

            if ((LatinIME.service.zerobyone & POPUP_DISABLE) != 0) return null;

            String popup = getPopupKeyboardContent(keyboard.isShiftCaps(), keyboard.isShifted(isSimpleUppercase), true);
            if (popup.length() > 0) {
                int resId = popupResId;
                if (resId == 0) resId = R.xml.kbd_popup_template;
                return new Keyboard(context, keyboard.keywordH, resId, popup, popupReversed, -1, padding);
            } else {
                return null;
            }
        }

        public String getHintLabel(boolean wantAscii, boolean wantAll) {
            if (hint == null) {
                hint = "";
                if (shiftLabel != null && !isSimpleUppercase) {
                    char c = shiftLabel.charAt(0);
                    if (wantAll || wantAscii && is7BitAscii(c)) {
                        hint = Character.toString(c);
                    }
                }
            }
            return hint;
        }

        public String getAltHintLabel(boolean wantAscii, boolean wantAll) {
            if (altHint == null) {
                altHint = "";
                String popup = getPopupKeyboardContent(false, false, false);
                if (popup.length() > 0) {
                    char c = popup.charAt(0);
                    if (wantAll || wantAscii && is7BitAscii(c)) {
                        altHint = Character.toString(c);
                    }
                }
            }
            return altHint;
        }

        private static boolean is7BitAscii(char c) {
            if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) return false;
            return c >= 32 && c < 127;
        }


        public void onPressed() {
            pressed = !pressed;
        }


        public void onReleased(boolean inside) {
            pressed = !pressed;
        }

        int[] parseCSV(String value) {
            int count = 0;
            int lastIndex = 0;
            if (value.length() > 0) {
                count++;
                while ((lastIndex = value.indexOf(",", lastIndex + 1)) > 0) {
                    count++;
                }
            }
            int[] values = new int[count];
            count = 0;
            StringTokenizer st = new StringTokenizer(value, ",");
            while (st.hasMoreTokens()) {
                try {
                    values[count++] = Integer.parseInt(st.nextToken());
                } catch (NumberFormatException nfe) {
                    Log.e(TAG, "Error parsing keycodes " + value);
                }
            }
            return values;
        }


        public boolean isInside(int x, int y) {
            boolean leftEdge = (edgeFlags & EDGE_LEFT) > 0;
            boolean rightEdge = (edgeFlags & EDGE_RIGHT) > 0;
            boolean topEdge = (edgeFlags & EDGE_TOP) > 0;
            boolean bottomEdge = (edgeFlags & EDGE_BOTTOM) > 0;
            if ((x >= this.x || (leftEdge && x <= this.x + this.width))
                    && (x < this.x + this.width || (rightEdge && x >= this.x))
                    && (y >= this.y || (topEdge && y <= this.y + this.height))
                    && (y < this.y + this.height || (bottomEdge && y >= this.y))) {
                return true;
            } else {
                return false;
            }
        }

        public int squaredDistanceFrom(int x, int y) {
            int xDist = this.x + width / 2 - x;
            int yDist = this.y + height / 2 - y;
            return xDist * xDist + yDist * yDist;
        }

        public int[] getCurrentDrawableState() {
            int[] states = KEY_STATE_NORMAL;

            if (locked) {
                if (pressed) {
                    states = KEY_STATE_PRESSED_LOCK;
                } else {
                    states = KEY_STATE_NORMAL_LOCK;
                }
            } else if (on) {
                if (pressed) {
                    states = KEY_STATE_PRESSED_ON;
                } else {
                    states = KEY_STATE_NORMAL_ON;
                }
            } else {
                if (sticky) {
                    if (pressed) {
                        states = KEY_STATE_PRESSED_OFF;
                    } else {
                        states = KEY_STATE_NORMAL_OFF;
                    }
                } else {
                    if (pressed) {
                        states = KEY_STATE_PRESSED;
                    }
                }
            }
            return states;
        }

        public String toString() {
            int code = (codes != null && codes.length > 0) ? codes[0] : 0;
            String edges = (
                    ((edgeFlags & Keyboard.EDGE_LEFT) != 0 ? "L" : "-") +
                            ((edgeFlags & Keyboard.EDGE_RIGHT) != 0 ? "R" : "-") +
                            ((edgeFlags & Keyboard.EDGE_TOP) != 0 ? "T" : "-") +
                            ((edgeFlags & Keyboard.EDGE_BOTTOM) != 0 ? "B" : "-"));
            return "KeyDebugFIXME(label=" + label +
                    (shiftLabel != null ? " shift=" + shiftLabel : "") +
                    (capsLabel != null ? " caps=" + capsLabel : "") +
                    (text != null ? " text=" + text : "") +
                    " code=" + code +
                    (code <= 0 || Character.isWhitespace(code) ? "" : ":'" + (char) code + "'") +
                    " x=" + x + ".." + (x + width) + " y=" + y + ".." + (y + height) +
                    " edgeFlags=" + edges +
                    (popupCharacters != null ? " pop=" + popupCharacters : "") +
                    " res=" + popupResId +
                    ")";
        }
    }
    public Keyboard(Context context, int defaultHeight, int xmlLayoutResId) {
        this(context, defaultHeight, xmlLayoutResId, 0);
    }

    public Keyboard(Context context, int defaultHeight, int xmlLayoutResId, int modeId) {
        this(context, defaultHeight, xmlLayoutResId, modeId, 0);
    }

    public Keyboard(Context context, int defaultHeight, int xmlLayoutResId, int modeId, float kbHeightPercent) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        mDisplayWidth = dm.widthPixels;
        mDisplayHeight = dm.heightPixels;
        Log.v(TAG, "keyboard's display metrics:" + dm + ", mDisplayWidth=" + mDisplayWidth);

        horzontlflote = 0;
        keywordW = mDisplayWidth / 10;
        keywordspace = 0;
        keywordH = defaultHeight;
        intkeywordHight = Math.round(mDisplayHeight * kbHeightPercent / 100);
        listkey = new ArrayList<Key>();
        mModifierKeys = new ArrayList<Key>();
        intkeywordmoad = modeId;
        boolenkeyword = LatinIME.service.bolenglobemode;
        loadKeyboard(context, context.getResources().getXml(xmlLayoutResId));
        setEdgeFlags();
        fixAltChars(LatinIME.service.defultevalue);
    }

    private Keyboard(Context context, int defaultHeight, int layoutTemplateResId,
                     CharSequence characters, boolean reversed, int columns, int horizontalPadding) {
        this(context, defaultHeight, layoutTemplateResId);
        int x = 0;
        int y = 0;
        int column = 0;
        mTotalWidth = 0;

        Row row = new Row(this);
        row.introwh = keywordH;
        row.rowW = keywordW;
        row.defaultHorizontalGap = horzontlflote;
        row.verticalGap = keywordspace;
        final int maxColumns = columns == -1 ? Integer.MAX_VALUE : columns;
        intrawo = 1;
        int start = reversed ? characters.length() - 1 : 0;
        int end = reversed ? -1 : characters.length();
        int step = reversed ? -1 : 1;
        for (int i = start; i != end; i += step) {
            char c = characters.charAt(i);
            if (column >= maxColumns
                    || x + keywordW + horizontalPadding > mDisplayWidth) {
                x = 0;
                y += keywordspace + keywordH;
                column = 0;
                ++intrawo;
            }
            final Key key = new Key(row);
            key.x = x;
            key.realX = x;
            key.y = y;
            key.label = String.valueOf(c);
            key.codes = key.getFromString(key.label);
            column++;
            x += key.width + key.gap;
            listkey.add(key);
            if (x > mTotalWidth) {
                mTotalWidth = x;
            }
        }
        allkeywordH = y + keywordH;
        intcolum = columns == -1 ? column : maxColumns;
        setEdgeFlags();
    }

    private void setEdgeFlags() {
        if (intraownum == 0) intraownum = 1;
        int row = 0;
        Key prevKey = null;
        int rowFlags = 0;
        for (Key key : listkey) {
            int keyFlags = 0;
            if (prevKey == null || key.x <= prevKey.x) {
                if (prevKey != null) {
                    prevKey.edgeFlags |= Keyboard.EDGE_RIGHT;
                }

                rowFlags = 0;
                if (row == 0) rowFlags |= Keyboard.EDGE_TOP;
                if (row == intraownum - 1) rowFlags |= Keyboard.EDGE_BOTTOM;
                ++row;

                keyFlags |= Keyboard.EDGE_LEFT;
            }
            key.edgeFlags = rowFlags | keyFlags;
            prevKey = key;
        }
        if (prevKey != null) prevKey.edgeFlags |= Keyboard.EDGE_RIGHT;

    }

    private void fixAltChars(Locale locale) {
        if (locale == null) locale = Locale.getDefault();
        Set<Character> mainKeys = new HashSet<Character>();
        for (Key key : listkey) {
            if (key.label != null && !key.modifier && key.label.length() == 1) {
                char c = key.label.charAt(0);
                mainKeys.add(c);
            }
        }

        for (Key key : listkey) {
            if (key.popupCharacters == null) continue;
            int popupLen = key.popupCharacters.length();
            if (popupLen == 0) {
                continue;
            }
            if (key.x >= mTotalWidth / 2) {
                key.popupReversed = true;
            }

            boolean needUpcase = key.label != null && key.label.length() == 1 && Character.isUpperCase(key.label.charAt(0));
            if (needUpcase) {
                key.popupCharacters = key.popupCharacters.toString().toUpperCase();
                popupLen = key.popupCharacters.length();
            }

            StringBuilder newPopup = new StringBuilder(popupLen);
            for (int i = 0; i < popupLen; ++i) {
                char c = key.popupCharacters.charAt(i);

                if (Character.isDigit(c) && mainKeys.contains(c))
                    continue;

                if ((key.edgeFlags & EDGE_TOP) == 0 && Character.isDigit(c)) continue;

                newPopup.append(c);
            }

            key.popupCharacters = newPopup.toString();
        }
    }

    public List<Key> getKeys() {
        return listkey;
    }

    public List<Key> getModifierKeys() {
        return mModifierKeys;
    }

    protected int getHorizontalGap() {
        return Math.round(horzontlflote);
    }

    protected void setHorizontalGap(int gap) {
        horzontlflote = gap;
    }

    protected int getVerticalGap() {
        return keywordspace;
    }

    protected void setVerticalGap(int gap) {
        keywordspace = gap;
    }

    protected int getKeyHeight() {
        return keywordH;
    }

    protected void setKeyHeight(int height) {
        keywordH = height;
    }

    protected int getKeyWidth() {
        return Math.round(keywordW);
    }

    protected void setKeyWidth(int width) {
        keywordW = width;
    }

    public int getHeight() {
        return allkeywordH;
    }

    public int getScreenHeight() {
        return mDisplayHeight;
    }

    public int getMinWidth() {
        return mTotalWidth;
    }

    public boolean setShiftState(int shiftState, boolean updateKey) {
        if (updateKey && keywordshift != null) {
            keywordshift.on = (shiftState != SHIFT_OFF);
        }
        if (keywordoff != shiftState) {
            keywordoff = shiftState;
            return true;
        }
        return false;
    }

    public boolean setShiftState(int shiftState) {
        return setShiftState(shiftState, true);
    }

    public Key setCtrlIndicator(boolean active) {
        if (keywordcontrol != null) keywordcontrol.on = active;
        return keywordcontrol;
    }

    public Key setAltIndicator(boolean active) {
        if (keywordalter != null) keywordalter.on = active;
        return keywordalter;
    }

    public Key setMetaIndicator(boolean active) {
        if (keywordmetr != null) keywordmetr.on = active;
        return keywordmetr;
    }

    public boolean isShiftCaps() {
        return keywordoff == SHIFT_CAPS || keywordoff == SHIFT_CAPS_LOCKED;
    }

    public boolean isShifted(boolean applyCaps) {
        if (applyCaps) {
            return keywordoff != SHIFT_OFF;
        } else {
            return keywordoff == SHIFT_ON || keywordoff == SHIFT_LOCKED;
        }
    }

    public int getShiftState() {
        return keywordoff;
    }

    public int getShiftKeyIndex() {
        return shiftvalue;
    }

    private void computeNearestNeighbors() {
        intwethkey = (getMinWidth() + intcolum - 1) / intcolum;
        inthightkey = (getHeight() + intrawo - 1) / intrawo;
        intgridview = new int[intcolum * intrawo][];
        int[] indices = new int[listkey.size()];
        final int gridWidth = intcolum * intwethkey;
        final int gridHeight = intrawo * inthightkey;
        for (int x = 0; x < gridWidth; x += intwethkey) {
            for (int y = 0; y < gridHeight; y += inthightkey) {
                int count = 0;
                for (int i = 0; i < listkey.size(); i++) {
                    final Key key = listkey.get(i);
                    boolean isSpace = key.codes != null && key.codes.length > 0 &&
                            key.codes[0] == LatinIME.ASCII_SPACE;
                    if (key.squaredDistanceFrom(x, y) < intkeywordproxi ||
                            key.squaredDistanceFrom(x + intwethkey - 1, y) < intkeywordproxi ||
                            key.squaredDistanceFrom(x + intwethkey - 1, y + inthightkey - 1)
                                    < intkeywordproxi ||
                            key.squaredDistanceFrom(x, y + inthightkey - 1) < intkeywordproxi ||
                            isSpace && !(
                                    x + intwethkey - 1 < key.x ||
                                            x > key.x + key.width ||
                                            y + inthightkey - 1 < key.y ||
                                            y > key.y + key.height)) {
                        indices[count++] = i;
                    }
                }
                int[] cell = new int[count];
                System.arraycopy(indices, 0, cell, 0, count);
                intgridview[(y / inthightkey) * intcolum + (x / intwethkey)] = cell;
            }
        }
    }

    public int[] getNearestKeys(int x, int y) {
        if (intgridview == null) computeNearestNeighbors();
        if (x >= 0 && x < getMinWidth() && y >= 0 && y < getHeight()) {
            int index = (y / inthightkey) * intcolum + (x / intwethkey);
            if (index < intrawo * intcolum) {
                return intgridview[index];
            }
        }
        return new int[0];
    }

    protected Row createRowFromXml(Resources res, XmlResourceParser parser) {
        return new Row(res, this, parser);
    }

    protected Key createKeyFromXml(Resources res, Row parent, int x, int y,
                                   XmlResourceParser parser) {
        return new Key(res, parent, x, y, parser);
    }

    private void loadKeyboard(Context context, XmlResourceParser parser) {
        boolean inKey = false;
        boolean inRow = false;
        float x = 0;
        int y = 0;
        Key key = null;
        Row currentRow = null;
        Resources res = context.getResources();
        boolean skipRow = false;
        intraownum = 0;

        try {
            int event;
            Key prevKey = null;
            while ((event = parser.next()) != XmlResourceParser.END_DOCUMENT) {
                if (event == XmlResourceParser.START_TAG) {
                    String tag = parser.getName();
                    if (TAG_ROW.equals(tag)) {
                        inRow = true;
                        x = 0;
                        currentRow = createRowFromXml(res, parser);
                        skipRow = currentRow.mode != 0 && currentRow.mode != intkeywordmoad;
                        if (currentRow.extension) {
                            if (boolenkeyword) {
                                ++keywordraownum;
                            } else {
                                skipRow = true;
                            }
                        }
                        if (skipRow) {
                            skipToEndOfRow(parser);
                            inRow = false;
                        }
                    } else if (TAG_KEY.equals(tag)) {
                        inKey = true;
                        key = createKeyFromXml(res, currentRow, Math.round(x), y, parser);
                        key.realX = x;
                        if (key.codes == null) {
                            if (prevKey != null) {
                                prevKey.width += key.width;
                            }
                        } else {
                            listkey.add(key);
                            prevKey = key;
                            if (key.codes[0] == KEYCODE_SHIFT) {
                                if (shiftvalue == -1) {
                                    keywordshift = key;
                                    shiftvalue = listkey.size() - 1;
                                }
                                mModifierKeys.add(key);
                            } else if (key.codes[0] == KEYCODE_ALT_SYM) {
                                mModifierKeys.add(key);
                            } else if (key.codes[0] == My_Keybord_Img_Key.KEYCODE_CTRL_LEFT) {
                                keywordcontrol = key;
                            } else if (key.codes[0] == My_Keybord_Img_Key.KEYCODE_ALT_LEFT) {
                                keywordalter = key;
                            } else if (key.codes[0] == My_Keybord_Img_Key.KEYCODE_META_LEFT) {
                                keywordmetr = key;
                            }
                        }
                    } else if (TAG_KEYBOARD.equals(tag)) {
                        parseKeyboardAttributes(res, parser);
                    }
                } else if (event == XmlResourceParser.END_TAG) {
                    if (inKey) {
                        inKey = false;
                        x += key.realGap + key.realWidth;
                        if (x > mTotalWidth) {
                            mTotalWidth = Math.round(x);
                        }
                    } else if (inRow) {
                        inRow = false;
                        y += currentRow.verticalGap;
                        y += currentRow.introwh;
                        intraownum++;
                    } else {
                        // TODO: error or extend?
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Parse error:" + e);
            e.printStackTrace();
        }
        allkeywordH = y - keywordspace;
    }

    public void setKeyboardWidth(int newWidth) {
        Log.i(TAG, "setKeyboardWidth newWidth=" + newWidth + ", mTotalWidth=" + mTotalWidth);
        if (newWidth <= 0) return;
        if (mTotalWidth <= newWidth) return;
        float scale = (float) newWidth / mDisplayWidth;
        Log.i("PCKeyboard", "Rescaling keyboard: " + mTotalWidth + " => " + newWidth);
        for (Key key : listkey) {
            key.x = Math.round(key.realX * scale);
        }
        mTotalWidth = newWidth;
    }

    private void skipToEndOfRow(XmlResourceParser parser)
            throws XmlPullParserException, IOException {
        int event;
        while ((event = parser.next()) != XmlResourceParser.END_DOCUMENT) {
            if (event == XmlResourceParser.END_TAG
                    && parser.getName().equals(TAG_ROW)) {
                break;
            }
        }
    }

    private void parseKeyboardAttributes(Resources res, XmlResourceParser parser) {
        TypedArray a = res.obtainAttributes(Xml.asAttributeSet(parser),
                R.styleable.Keyboard);

        keywordW = getDimensionOrFraction(a,
                R.styleable.Keyboard_keyWidth,
                mDisplayWidth, mDisplayWidth / 10);
        keywordH = Math.round(getDimensionOrFraction(a,
                R.styleable.Keyboard_keyHeight,
                mDisplayHeight, keywordH));
        horzontlflote = getDimensionOrFraction(a,
                R.styleable.Keyboard_horizontalGap,
                mDisplayWidth, 0);
        keywordspace = Math.round(getDimensionOrFraction(a,
                R.styleable.Keyboard_verticalGap,
                mDisplayHeight, 0));
        horzntlkeyword = getDimensionOrFraction(a, R.styleable.Keyboard_horizontalPad,
                mDisplayWidth, res.getDimension(R.dimen.key_horizontal_pad));
        keywordvrtcal = getDimensionOrFraction(a,
                R.styleable.Keyboard_verticalPad,
                mDisplayHeight, res.getDimension(R.dimen.key_vertical_pad));
        intrawo = a.getInteger(R.styleable.Keyboard_layoutRows, DEFAULT_LAYOUT_ROWS);
        intcolum = a.getInteger(R.styleable.Keyboard_layoutColumns, DEFAULT_LAYOUT_COLUMNS);
        if (keywordH == 0 && intkeywordHight > 0 && intrawo > 0) {
            keywordH = intkeywordHight / intrawo;
        }
        intkeywordproxi = (int) (keywordW * SEARCH_DISTANCE);
        intkeywordproxi = intkeywordproxi * intkeywordproxi;
        a.recycle();
    }

    static float getDimensionOrFraction(TypedArray a, int index, int base, float defValue) {
        TypedValue value = a.peekValue(index);
        if (value == null) return defValue;
        if (value.type == TypedValue.TYPE_DIMENSION) {
            return a.getDimensionPixelOffset(index, Math.round(defValue));
        } else if (value.type == TypedValue.TYPE_FRACTION) {
            return a.getFraction(index, base, base, defValue);
        }
        return defValue;
    }

    @Override
    public String toString() {
        return "Keyboard(" + intcolum + "x" + intrawo +
                " keys=" + listkey.size() +
                " rowCount=" + intraownum +
                " mode=" + intkeywordmoad +
                " size=" + mTotalWidth + "x" + allkeywordH +
                ")";

    }
}
