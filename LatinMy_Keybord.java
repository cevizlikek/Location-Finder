
package com.keyboard.keyboardthemes;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.Log;
import android.view.ViewConfiguration;
import android.view.inputmethod.EditorInfo;

import com.keyboard.keyboardthemes.R;

import java.util.List;
import java.util.Locale;



public class LatinMy_Keybord extends Keyboard {

    private static final boolean DEBUG_PREFERRED_LETTER = true;
    private static final String TAG = "PCKeyboardLK";
    private static final int OPACITY_FULLY_OPAQUE = 255;
    private static final int SPACE_LED_LENGTH_PERCENT = 80;
    private static final float SPACEBAR_DRAG_THRESHOLD = 0.51f;
    private static final float OVERLAP_PERCENTAGE_LOW_PROB = 0.70f;
    private static final float OVERLAP_PERCENTAGE_HIGH_PROB = 0.85f;
    private static final float SPACEBAR_POPUP_MIN_RATIO = 0.4f;
    private static final float SPACEBAR_POPUP_MAX_RATIO = 0.4f;
    private static final float SPACEBAR_LANGUAGE_BASELINE = 0.6f;
    private static final float MINIMUM_SCALE_OF_LANGUAGE_NAME = 0.8f;
    private static int keybordjagya;
    private Drawable drawable_img;
    private Drawable drawable_shiftimg;
    private Drawable drawableimg;
    private Drawable drawable_jagya;
    private Drawable outo_drawable_img;
    private Drawable drawable_jagya_img;
    private Drawable drawableminiimg;
    private Drawable drawableimgview;
    private Drawable drawableservices;
    private Drawable drawableservicimg;
    private Drawable drawableminiimgvi;
    private Drawable drawablebeforview;
    private final Drawable drawablejamniimg;
    private final Drawable drawabledabiimg;
    private Key chaviimg;
    private Key chavienter;
    private Key chavifone;
    private final Drawable drawablehintimg;
    private Key jagyaimg;
    private Key numberchavi;
    private final int[] intarray;
    private int jagyaon;
    private int jagyavalue;
    private Locale publickvalue;
    private Bhasha_Keybord_Swich bhashakey;
    private final Resources valueresource;
    private final Context appcont;
    private int valueint;
    private boolean soundbtn;
    private boolean soundoff;
    private final boolean lettername;
    private final boolean letterkey;
    private final boolean alladdletter;
    private CharSequence numbesequnce;
    private boolean boolenjagya;
    private SlidingLocaleDrawable swabimg;
    private int[] integerfreqvancy;
    private int namevalue;
    private int nameA;
    private int nameB;
    private int intkeybord;
    private int mExtensionResId;
    // TODO: remove this attribute when either Keyboard.mDefaultVerticalGap or Key.parent becomes
    private final int columbint;
    private LatinMy_Keybord latinkey;


    public LatinMy_Keybord(Context context, int xmlLayoutResId) {
        this(context, xmlLayoutResId, 0, 0);
    }

    public LatinMy_Keybord(Context context, int xmlLayoutResId, int mode, float kbHeightPercent) {
        super(context, 0, xmlLayoutResId, mode, kbHeightPercent);
        final Resources res = context.getResources();
        appcont = context;
        valueint = mode;
        valueresource = res;
        drawable_img = res.getDrawable(R.drawable.keywourd_shiftlocked);
        drawable_shiftimg = res.getDrawable(R.drawable.keybord_shift_lockkey);
        setDefaultBounds(drawable_shiftimg);
        drawable_jagya = res.getDrawable(R.drawable.keywourd_speshh);
        outo_drawable_img = res.getDrawable(R.drawable.sym_keyboard_space_led);
        drawable_jagya_img = res.getDrawable(R.drawable.keybord_spesh);
        drawableminiimg = res.getDrawable(R.drawable.keybord_voice_spiker);
        drawableimgview = res.getDrawable(R.drawable.keybord_voich_sound);
        drawableservices = res.getDrawable(R.drawable.keywourd_servish);
        drawableservicimg = res.getDrawable(R.drawable.keybord_services);
        setDefaultBounds(drawableimgview);
        drawablejamniimg = res.getDrawable(R.drawable.keybord_leftside);
        drawabledabiimg = res.getDrawable(R.drawable.keybord_rightside);
        drawableminiimgvi = res.getDrawable(R.drawable.keybord_number);
        drawablebeforview = res.getDrawable(R.drawable.keybord_num_voice);
        drawablehintimg = res.getDrawable(R.drawable.hint_popup);
        setDefaultBounds(drawablebeforview);
        keybordjagya = res.getDimensionPixelOffset(
                R.dimen.spacebar_vertical_correction);
        lettername = xmlLayoutResId == R.xml.kbd_qwerty;
        letterkey = xmlLayoutResId == R.xml.kbd_full;
        alladdletter = xmlLayoutResId == R.xml.kbd_full_fn || xmlLayoutResId == R.xml.kbd_compact_fn;
        intarray = new int[] { indexOf(LatinIME.ASCII_SPACE) };
        // TODO remove this initialization after cleanup
        columbint = super.getVerticalGap();
    }

    @Override
    protected Key createKeyFromXml(Resources res, Row parent, int x, int y,
            XmlResourceParser parser) {
        Key key = new LatinKey(res, parent, x, y, parser);
        if (key.codes == null) return key;
        switch (key.codes[0]) {
        case LatinIME.ASCII_ENTER:
            chavienter = key;
            break;
        case My_Keybord_Img_Key.KEYCODE_F1:
            chavifone = key;
            break;
        case LatinIME.ASCII_SPACE:
            jagyaimg = key;
            break;
        case KEYCODE_MODE_CHANGE:
            numberchavi = key;
            numbesequnce = key.label;
            break;
        }

        return key;
    }

    void setImeOptions(Resources res, int mode, int options) {
        valueint = mode;
        // TODO should clean up this method
        if (chavienter != null) {
            chavienter.popupCharacters = null;
            chavienter.popupResId = 0;
            chavienter.text = null;
            switch (options&(EditorInfo.IME_MASK_ACTION|EditorInfo.IME_FLAG_NO_ENTER_ACTION)) {
                case EditorInfo.IME_ACTION_GO:
                    chavienter.iconPreview = null;
                    chavienter.icon = null;
                    chavienter.label = res.getText(R.string.label_go_key);
                    break;
                case EditorInfo.IME_ACTION_NEXT:
                    chavienter.iconPreview = null;
                    chavienter.icon = null;
                    chavienter.label = res.getText(R.string.label_next_key);
                    break;
                case EditorInfo.IME_ACTION_DONE:
                    chavienter.iconPreview = null;
                    chavienter.icon = null;
                    chavienter.label = res.getText(R.string.label_done_key);
                    break;
                case EditorInfo.IME_ACTION_SEARCH:
                    chavienter.iconPreview = res.getDrawable(
                            R.drawable.keybord_research);
                    chavienter.icon = res.getDrawable(R.drawable.keywourd_research);
                    chavienter.label = null;
                    break;
                case EditorInfo.IME_ACTION_SEND:
                    chavienter.iconPreview = null;
                    chavienter.icon = null;
                    chavienter.label = res.getText(R.string.label_send_key);
                    break;
                default:
                    chavienter.iconPreview = res.getDrawable(
                            R.drawable.keybord_riturn);
                    chavienter.icon = res.getDrawable(R.drawable.keywourd_return);
                    chavienter.label = null;
                    break;
            }
            if (chavienter.iconPreview != null) {
                setDefaultBounds(chavienter.iconPreview);
            }
        }
    }

    void enableShiftLock() {
        int index = getShiftKeyIndex();
        if (index >= 0) {
            chaviimg = getKeys().get(index);
            drawableimg = chaviimg.icon;
        }
    }

    @Override
    public boolean setShiftState(int shiftState) {
        if (chaviimg != null) {
            chaviimg.on = shiftState == SHIFT_ON || shiftState == SHIFT_LOCKED;
            chaviimg.locked = shiftState == SHIFT_LOCKED || shiftState == SHIFT_CAPS_LOCKED;
            chaviimg.icon = (shiftState == SHIFT_OFF || shiftState == SHIFT_ON || shiftState == SHIFT_LOCKED) ?
                    drawableimg : drawable_img;
            return super.setShiftState(shiftState, false);
        } else {
            return super.setShiftState(shiftState, true);
        }
    }

 boolean isAlphaKeyboard() {
        return lettername;
    }

    public void setExtension(LatinMy_Keybord extKeyboard) {
        latinkey = extKeyboard;
    }

    public LatinMy_Keybord getExtension() {
        return latinkey;
    }

    public void updateSymbolIcons(boolean isAutoCompletion) {
        updateDynamicKeys();
        updateSpaceBarForLocale(isAutoCompletion);
    }

    private void setDefaultBounds(Drawable drawable) {
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
    }

    public void setVoiceMode(boolean hasVoiceButton, boolean hasVoice) {
        soundbtn = hasVoiceButton;
        soundoff = hasVoice;
        updateDynamicKeys();
    }

    private void updateDynamicKeys() {
        update123Key();
        updateF1Key();
    }

    private void update123Key() {
        if (numberchavi != null && lettername) {
            if (soundoff && !soundbtn) {
                numberchavi.icon = drawableminiimgvi;
                numberchavi.iconPreview = drawablebeforview;
                numberchavi.label = null;
            } else {
                numberchavi.icon = null;
                numberchavi.iconPreview = null;
                numberchavi.label = numbesequnce;
            }
        }
    }

    private void updateF1Key() {
        if (chavifone == null)
            return;

        if (lettername) {
            if (valueint == Typing_Switch.FOURE_VALUE) {
                setNonMicF1Key(chavifone, "/", R.xml.popup_slash);
            } else if (valueint == Typing_Switch.FIVE_VALUE) {
                setNonMicF1Key(chavifone, "@", R.xml.popup_at);
            } else {
                if (soundoff && soundbtn) {
                    setMicF1Key(chavifone);
                } else {
                    setNonMicF1Key(chavifone, ",", R.xml.popup_comma);
                }
            }
        } else if (letterkey) {
        	if (soundoff && soundbtn) {
        		setMicF1Key(chavifone);
        	} else {
        		setSettingsF1Key(chavifone);
        	}
        } else if (alladdletter) {
    		setMicF1Key(chavifone);        	
        } else {
            if (soundoff && soundbtn) {
                setMicF1Key(chavifone);
            } else {
                setNonMicF1Key(chavifone, ",", R.xml.popup_comma);
            }
        }
    }

    private void setMicF1Key(Key key) {
        final Drawable micWithSettingsHintDrawable = new BitmapDrawable(valueresource,
                drawSynthesizedSettingsHintImage(key.width, key.height, drawableminiimg, drawablehintimg));

        if (key.popupResId == 0) {
            key.popupResId = R.xml.popup_mic;
        } else {
            key.modifier = true;
            if (key.label != null) {
                key.popupCharacters = (key.popupCharacters == null) ?
                        key.label + key.shiftLabel.toString() :
                            key.label + key.shiftLabel.toString() + key.popupCharacters.toString();
            }
        }
        key.label = null;
        key.shiftLabel = null;
        key.codes = new int[] { My_Keybord_Img_Key.KEYCODE_VOICE };
        key.icon = micWithSettingsHintDrawable;
        key.iconPreview = drawableimgview;
    }

    private void setSettingsF1Key(Key key) {
        if (key.shiftLabel != null && key.label != null) {
            key.codes = new int[] { key.label.charAt(0) };
            return;
        }
        final Drawable settingsHintDrawable = new BitmapDrawable(valueresource,
                drawSynthesizedSettingsHintImage(key.width, key.height, drawableservices, drawablehintimg));
    	key.label = null;
    	key.icon = settingsHintDrawable;
    	key.codes = new int[] { My_Keybord_Img_Key.KEYCODE_OPTIONS };
    	key.popupResId = R.xml.popup_mic;
    	key.iconPreview = drawableservicimg;
    }
    
    private void setNonMicF1Key(Key key, String label, int popupResId) {
        if (key.shiftLabel != null) {
            key.codes = new int[] { key.label.charAt(0) };
            return;
        }
        key.label = label;
        key.codes = new int[] { label.charAt(0) };
        key.popupResId = popupResId;
        key.icon = drawablehintimg;
        key.iconPreview = null;
    }

    public boolean isF1Key(Key key) {
        return key == chavifone;
    }

    public static boolean hasPuncOrSmileysPopup(Key key) {
        return key.popupResId == R.xml.popup_punctuation || key.popupResId == R.xml.popup_smileys;
    }

    public Key onAutoCompletionStateChanged(boolean isAutoCompletion) {
        updateSpaceBarForLocale(isAutoCompletion);
        return jagyaimg;
    }

    public boolean isLanguageSwitchEnabled() {
        return publickvalue != null;
    }

    private void updateSpaceBarForLocale(boolean isAutoCompletion) {
        if (jagyaimg == null) return;
        if (publickvalue != null) {
            jagyaimg.icon = new BitmapDrawable(valueresource,
                    drawSpaceBar(OPACITY_FULLY_OPAQUE, isAutoCompletion));
        } else {
            if (isAutoCompletion) {
                jagyaimg.icon = new BitmapDrawable(valueresource,
                        drawSpaceBar(OPACITY_FULLY_OPAQUE, isAutoCompletion));
            } else {
                jagyaimg.icon = valueresource.getDrawable(R.drawable.keywourd_speshh);
            }
        }
    }

    private static int getTextWidth(Paint paint, String text, float textSize, Rect bounds) {
        paint.setTextSize(textSize);
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.width();
    }

    private Bitmap drawSynthesizedSettingsHintImage(
            int width, int height, Drawable mainIcon, Drawable hintIcon) {
        if (mainIcon == null || hintIcon == null)
            return null;
        Rect hintIconPadding = new Rect(0, 0, 0, 0);
        hintIcon.getPadding(hintIconPadding);
        final Bitmap buffer = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(buffer);
        canvas.drawColor(valueresource.getColor(R.color.latinkeyboard_transparent), PorterDuff.Mode.CLEAR);

        final int drawableX = (width + hintIconPadding.left - hintIconPadding.right
                - mainIcon.getIntrinsicWidth()) / 2;
        final int drawableY = (height + hintIconPadding.top - hintIconPadding.bottom
                - mainIcon.getIntrinsicHeight()) / 2;
        setDefaultBounds(mainIcon);
        canvas.translate(drawableX, drawableY);
        mainIcon.draw(canvas);
        canvas.translate(-drawableX, -drawableY);

        hintIcon.setBounds(0, 0, width, height);
        hintIcon.draw(canvas);
        return buffer;
    }

    private static String layoutSpaceBar(Paint paint, Locale locale, Drawable lArrow,
            Drawable rArrow, int width, int height, float origTextSize,
            boolean allowVariableTextSize) {
        final float arrowWidth = lArrow.getIntrinsicWidth();
        final float arrowHeight = lArrow.getIntrinsicHeight();
        final float maxTextWidth = width - (arrowWidth + arrowWidth);
        final Rect bounds = new Rect();

        String language = Bhasha_Keybord_Swich.toTitleCase(locale.getDisplayLanguage(locale));
        int textWidth = getTextWidth(paint, language, origTextSize, bounds);
        float textSize = origTextSize * Math.min(maxTextWidth / textWidth, 1.0f);

        final boolean useShortName;
        if (allowVariableTextSize) {
            textWidth = getTextWidth(paint, language, textSize, bounds);
            useShortName = textSize / origTextSize < MINIMUM_SCALE_OF_LANGUAGE_NAME
                    || textWidth > maxTextWidth;
        } else {
            useShortName = textWidth > maxTextWidth;
            textSize = origTextSize;
        }
        if (useShortName) {
            language = Bhasha_Keybord_Swich.toTitleCase(locale.getLanguage());
            textWidth = getTextWidth(paint, language, origTextSize, bounds);
            textSize = origTextSize * Math.min(maxTextWidth / textWidth, 1.0f);
        }
        paint.setTextSize(textSize);

        final float baseline = height * SPACEBAR_LANGUAGE_BASELINE;
        final int top = (int)(baseline - arrowHeight);
        final float remains = (width - textWidth) / 2;
        lArrow.setBounds((int)(remains - arrowWidth), top, (int)remains, (int)baseline);
        rArrow.setBounds((int)(remains + textWidth), top, (int)(remains + textWidth + arrowWidth),
                (int)baseline);

        return language;
    }

    private Bitmap drawSpaceBar(int opacity, boolean isAutoCompletion) {
        final int width = jagyaimg.width;
        final int height = drawable_jagya.getIntrinsicHeight();
        final Bitmap buffer = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(buffer);
        canvas.drawColor(valueresource.getColor(R.color.latinkeyboard_transparent), PorterDuff.Mode.CLEAR);

        if (publickvalue != null) {
            final Paint paint = new Paint();
            paint.setAlpha(opacity);
            paint.setAntiAlias(true);
            paint.setTextAlign(Align.CENTER);

            final boolean allowVariableTextSize = true;
            Locale locale = bhashakey.getInputLocale();
            final String language = layoutSpaceBar(paint, locale,
                    drawablejamniimg, drawabledabiimg, width, height,
                    getTextSizeFromTheme(android.R.style.TextAppearance_Small, 14),
                    allowVariableTextSize);

            final int shadowColor = valueresource.getColor(R.color.latinkeyboard_bar_language_shadow_white);
            final float baseline = height * SPACEBAR_LANGUAGE_BASELINE;
            final float descent = paint.descent();
            paint.setColor(shadowColor);
            canvas.drawText(language, width / 2, baseline - descent - 1, paint);
            paint.setColor(valueresource.getColor(R.color.latinkeyboard_dim_color_white));

            canvas.drawText(language, width / 2, baseline - descent, paint);

            if (bhashakey.getLocaleCount() > 1) {
                drawablejamniimg.draw(canvas);
                drawabledabiimg.draw(canvas);
            }
        }

        if (isAutoCompletion) {
            final int iconWidth = width * SPACE_LED_LENGTH_PERCENT / 100;
            final int iconHeight = outo_drawable_img.getIntrinsicHeight();
            int x = (width - iconWidth) / 2;
            int y = height - iconHeight;
            outo_drawable_img.setBounds(x, y, x + iconWidth, y + iconHeight);
            outo_drawable_img.draw(canvas);
        } else {
            final int iconWidth = drawable_jagya.getIntrinsicWidth();
            final int iconHeight = drawable_jagya.getIntrinsicHeight();
            int x = (width - iconWidth) / 2;
            int y = height - iconHeight;
            drawable_jagya.setBounds(x, y, x + iconWidth, y + iconHeight);
            drawable_jagya.draw(canvas);
        }
        return buffer;
    }

    private int getSpacePreviewWidth() {
        final int width = Math.min(
        		Math.max(jagyaimg.width, (int)(getMinWidth() * SPACEBAR_POPUP_MIN_RATIO)), 
        		(int)(getScreenHeight() * SPACEBAR_POPUP_MAX_RATIO));
        return width;
    }
    
    private void updateLocaleDrag(int diff) {
        if (swabimg == null) {
            final int width = getSpacePreviewWidth();
            final int height = drawable_jagya_img.getIntrinsicHeight();
            swabimg = new SlidingLocaleDrawable(drawable_jagya_img, width, height);
            swabimg.setBounds(0, 0, width, height);
            jagyaimg.iconPreview = swabimg;
        }
        swabimg.setDiff(diff);
        if (Math.abs(diff) == Integer.MAX_VALUE) {
            jagyaimg.iconPreview = drawable_jagya_img;
        } else {
            jagyaimg.iconPreview = swabimg;
        }
        jagyaimg.iconPreview.invalidateSelf();
    }

    public int getLanguageChangeDirection() {
        if (jagyaimg == null || bhashakey.getLocaleCount() < 2
                || Math.abs(jagyavalue) < getSpacePreviewWidth() * SPACEBAR_DRAG_THRESHOLD) {
            return 0;
        }
        return jagyavalue > 0 ? 1 : -1;
    }

    public void setLanguageSwitcher(Bhasha_Keybord_Swich switcher, boolean isAutoCompletion) {
        bhashakey = switcher;
        Locale locale = bhashakey.getLocaleCount() > 0
                ? bhashakey.getInputLocale()
                : null;
        if (locale != null
                && bhashakey.getLocaleCount() == 1
                && bhashakey.getSystemLocale().getLanguage()
                   .equalsIgnoreCase(locale.getLanguage())) {
            locale = null;
        }
        publickvalue = locale;
        updateSymbolIcons(isAutoCompletion);
    }

    boolean isCurrentlyInSpace() {
        return boolenjagya;
    }

    void setPreferredLetters(int[] frequencies) {
        integerfreqvancy = frequencies;
        namevalue = 0;
    }

    void keyReleased() {
        boolenjagya = false;
        jagyavalue = 0;
        namevalue = 0;
        nameA = 0;
        nameB = 0;
        intkeybord = Integer.MAX_VALUE;
        if (jagyaimg != null) {
            updateLocaleDrag(Integer.MAX_VALUE);
        }
    }

    boolean isInside(LatinKey key, int x, int y) {
        final int code = key.codes[0];
        if (code == KEYCODE_SHIFT ||
                code == KEYCODE_DELETE) {
            y -= key.height / 10;
            if (code == KEYCODE_SHIFT) {
            	if (key.x == 0) {
            		x += key.width / 6;
            	} else {
            		x -= key.width / 6;
            	}
            }
            if (code == KEYCODE_DELETE) x -= key.width / 6;
        } else if (code == LatinIME.ASCII_SPACE) {
            y += LatinMy_Keybord.keybordjagya;
            if (bhashakey.getLocaleCount() > 1) {
                if (boolenjagya) {
                    int diff = x - jagyaon;
                    if (Math.abs(diff - jagyavalue) > 0) {
                        updateLocaleDrag(diff);
                    }
                    jagyavalue = diff;
                    return true;
                } else {
                    boolean insideSpace = key.isInsideSuper(x, y);
                    if (insideSpace) {
                        boolenjagya = true;
                        jagyaon = x;
                        updateLocaleDrag(0);
                    }
                    return insideSpace;
                }
            }
        } else if (integerfreqvancy != null) {
            if (nameA != x || nameB != y) {
                namevalue = 0;
                intkeybord = Integer.MAX_VALUE;
            }
            final int[] pref = integerfreqvancy;
            if (namevalue > 0) {
                if (DEBUG_PREFERRED_LETTER) {
                    if (namevalue == code && !key.isInsideSuper(x, y)) {
                        Log.d(TAG, "CORRECTED !!!!!!");
                    }
                }
                return namevalue == code;
            } else {
                final boolean inside = key.isInsideSuper(x, y);
                int[] nearby = getNearestKeys(x, y);
                List<Key> nearbyKeys = getKeys();
                if (inside) {
                    if (inPrefList(code, pref)) {
                        namevalue = code;
                        nameA = x;
                        nameB = y;
                        for (int i = 0; i < nearby.length; i++) {
                            Key k = nearbyKeys.get(nearby[i]);
                            if (k != key && inPrefList(k.codes[0], pref)) {
                                final int dist = distanceFrom(k, x, y);
                                if (dist < (int) (k.width * OVERLAP_PERCENTAGE_LOW_PROB) &&
                                        (pref[k.codes[0]] > pref[namevalue] * 3))  {
                                    namevalue = k.codes[0];
                                    intkeybord = dist;
                                    if (DEBUG_PREFERRED_LETTER) {
                                        Log.d(TAG, "CORRECTED ALTHOUGH PREFERRED !!!!!!");
                                    }
                                    break;
                                }
                            }
                        }

                        return namevalue == code;
                    }
                }

              for (int i = 0; i < nearby.length; i++) {
                    Key k = nearbyKeys.get(nearby[i]);
                    if (inPrefList(k.codes[0], pref)) {
                        final int dist = distanceFrom(k, x, y);
                        if (dist < (int) (k.width * OVERLAP_PERCENTAGE_HIGH_PROB)
                                && dist < intkeybord)  {
                            namevalue = k.codes[0];
                            nameA = x;
                            nameB = y;
                            intkeybord = dist;
                        }
                    }
                }
                if (namevalue == 0) {
                    return inside;
                } else {
                    return namevalue == code;
                }
            }
        }

        if (boolenjagya) return false;

        return key.isInsideSuper(x, y);
    }

    private boolean inPrefList(int code, int[] pref) {
        if (code < pref.length && code >= 0) return pref[code] > 0;
        return false;
    }

    private int distanceFrom(Key k, int x, int y) {
        if (y > k.y && y < k.y + k.height) {
            return Math.abs(k.x + k.width / 2 - x);
        } else {
            return Integer.MAX_VALUE;
        }
    }

    @Override
    public int[] getNearestKeys(int x, int y) {
        if (boolenjagya) {
            return intarray;
        } else {
            return super.getNearestKeys(Math.max(0, Math.min(x, getMinWidth() - 1)),
                    Math.max(0, Math.min(y, getHeight() - 1)));
        }
    }

    private int indexOf(int code) {
        List<Key> keys = getKeys();
        int count = keys.size();
        for (int i = 0; i < count; i++) {
            if (keys.get(i).codes[0] == code) return i;
        }
        return -1;
    }

    private int getTextSizeFromTheme(int style, int defValue) {
        TypedArray array = appcont.getTheme().obtainStyledAttributes(
                style, new int[] { android.R.attr.textSize });
        int resId = array.getResourceId(0, 0);
        if (resId >= array.length()) {
            Log.i(TAG, "getTextSizeFromTheme error: resId " + resId + " > " + array.length());
            return defValue;
        }
        int textSize = array.getDimensionPixelSize(resId, defValue);
        return textSize;
    }

    // TODO LatinKey could be static class
    class LatinKey extends Key {

        private final int[] KEY_STATE_FUNCTIONAL_NORMAL = {
                android.R.attr.state_single
        };

        private final int[] KEY_STATE_FUNCTIONAL_PRESSED = {
                android.R.attr.state_single,
                android.R.attr.state_pressed
        };

        public LatinKey(Resources res, Keyboard.Row parent, int x, int y,
                XmlResourceParser parser) {
            super(res, parent, x, y, parser);
        }

        private boolean isFunctionalKey() {
            return !sticky && modifier;
        }

        @Override
        public boolean isInside(int x, int y) {
            // TODO This should be done by parent.isInside(this, x, y)
            boolean result = LatinMy_Keybord.this.isInside(this, x, y);
            return result;
        }

        boolean isInsideSuper(int x, int y) {
            return super.isInside(x, y);
        }

        @Override
        public int[] getCurrentDrawableState() {
            if (isFunctionalKey()) {
                if (pressed) {
                    return KEY_STATE_FUNCTIONAL_PRESSED;
                } else {
                    return KEY_STATE_FUNCTIONAL_NORMAL;
                }
            }
            return super.getCurrentDrawableState();
        }

        @Override
        public int squaredDistanceFrom(int x, int y) {
            final int verticalGap = LatinMy_Keybord.this.columbint;
            final int xDist = this.x + width / 2 - x;
            final int yDist = this.y + (height + verticalGap) / 2 - y;
            return xDist * xDist + yDist * yDist;
        }
    }

    class SlidingLocaleDrawable extends Drawable {

        private final int mWidth;
        private final int mHeight;
        private final Drawable mBackground;
        private final TextPaint mTextPaint;
        private final int mMiddleX;
        private final Drawable mLeftDrawable;
        private final Drawable mRightDrawable;
        private final int mThreshold;
        private int mDiff;
        private boolean mHitThreshold;
        private String mCurrentLanguage;
        private String mNextLanguage;
        private String mPrevLanguage;

        public SlidingLocaleDrawable(Drawable background, int width, int height) {
            mBackground = background;
            setDefaultBounds(mBackground);
            mWidth = width;
            mHeight = height;
            mTextPaint = new TextPaint();
            mTextPaint.setTextSize(getTextSizeFromTheme(android.R.style.TextAppearance_Medium, 18));
            mTextPaint.setColor(valueresource.getColor(R.color.latinkeyboard_transparent));
            mTextPaint.setTextAlign(Align.CENTER);
            mTextPaint.setAlpha(OPACITY_FULLY_OPAQUE);
            mTextPaint.setAntiAlias(true);
            mMiddleX = (mWidth - mBackground.getIntrinsicWidth()) / 2;
            mLeftDrawable =
                    valueresource.getDrawable(R.drawable.keybord_line_leftside);
            mRightDrawable =
                    valueresource.getDrawable(R.drawable.keybord_line_rifghtside);
            mThreshold = ViewConfiguration.get(appcont).getScaledTouchSlop();
        }

        private void setDiff(int diff) {
            if (diff == Integer.MAX_VALUE) {
                mHitThreshold = false;
                mCurrentLanguage = null;
                return;
            }
            mDiff = diff;
            if (mDiff > mWidth) mDiff = mWidth;
            if (mDiff < -mWidth) mDiff = -mWidth;
            if (Math.abs(mDiff) > mThreshold) mHitThreshold = true;
            invalidateSelf();
        }

        private String getLanguageName(Locale locale) {
            return Bhasha_Keybord_Swich.toTitleCase(locale.getDisplayLanguage(locale));
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.save();
            if (mHitThreshold) {
                Paint paint = mTextPaint;
                final int width = mWidth;
                final int height = mHeight;
                final int diff = mDiff;
                final Drawable lArrow = mLeftDrawable;
                final Drawable rArrow = mRightDrawable;
                canvas.clipRect(0, 0, width, height);
                if (mCurrentLanguage == null) {
                    final Bhasha_Keybord_Swich languageSwitcher = bhashakey;
                    mCurrentLanguage = getLanguageName(languageSwitcher.getInputLocale());
                    mNextLanguage = getLanguageName(languageSwitcher.getNextInputLocale());
                    mPrevLanguage = getLanguageName(languageSwitcher.getPrevInputLocale());
                }
                final float baseline = mHeight * SPACEBAR_LANGUAGE_BASELINE - paint.descent();
                paint.setColor(valueresource.getColor(R.color.latinkeyboard_feedback_language_text));
                canvas.drawText(mCurrentLanguage, width / 2 + diff, baseline, paint);
                canvas.drawText(mNextLanguage, diff - width / 2, baseline, paint);
                canvas.drawText(mPrevLanguage, diff + width + width / 2, baseline, paint);

                setDefaultBounds(lArrow);
                rArrow.setBounds(width - rArrow.getIntrinsicWidth(), 0, width,
                        rArrow.getIntrinsicHeight());
                lArrow.draw(canvas);
                rArrow.draw(canvas);
            }
            if (mBackground != null) {
                canvas.translate(mMiddleX, 0);
                mBackground.draw(canvas);
            }
            canvas.restore();
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }

        @Override
        public void setAlpha(int alpha) {
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
        }

        @Override
        public int getIntrinsicWidth() {
            return mWidth;
        }

        @Override
        public int getIntrinsicHeight() {
            return mHeight;
        }
    }
}
