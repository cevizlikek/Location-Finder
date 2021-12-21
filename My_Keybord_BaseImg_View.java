package com.keyboard.keyboardthemes;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.WeakHashMap;

import com.keyboard.keyboardthemes.Keyboard.Key;
import com.keyboard.keyboardthemes.R;


public class My_Keybord_BaseImg_View extends View implements My_Keybord_Img_Treker.UIProxy {

    private static final String TAG = "HK/LatinKeyboardBaseView";
    private static final boolean DEBUG = false;
    public static final int NOT_A_TOUCH_COORDINATE = -1;
    static final int NOT_A_KEY = -1;
    private static final int NUMBER_HINT_VERTICAL_ADJUSTMENT_PIXEL = -1;
    protected final WeakHashMap<Key, Keyboard> mMiniKeyboardCacheMain = new WeakHashMap<Key, Keyboard>();
    protected final WeakHashMap<Key, Keyboard> mMiniKeyboardCacheShift = new WeakHashMap<Key, Keyboard>();
    protected final WeakHashMap<Key, Keyboard> mMiniKeyboardCacheCaps = new WeakHashMap<Key, Keyboard>();
    private final float KEY_LABEL_VERTICAL_ADJUSTMENT_FACTOR = 0.55f;
    private final String KEY_LABEL_HEIGHT_REFERENCE_CHAR = "H";

    public interface OnKeyboardActionListener {
        void onPress(int primaryCode);
        void onRelease(int primaryCode);
        void onKey(int primaryCode, int[] keyCodes, int x, int y);
        void onText(CharSequence text);
        void onCancel();
        boolean swipeLeft();
        boolean swipeRight();

        boolean swipeDown();
        boolean swipeUp();
    }
    private final int basikimgview;
    private float namelenth;
    private float letersize = 1.0f;
    private int namering;
    private int hintrungname;
    private int chaviarorung;
    private boolean rungstyle;
    private Typeface nametheam = Typeface.DEFAULT;
    private float floleterlenth;
    private int letrstylevalue = 0;
    private int shedorung;
    private float shedovalue;
    private Drawable imgbg;
    private int leterbg;
    private float bgrupes;
    private float chavihistry;
    private float row_flote;
    protected int before_effect;
    protected int beforehit;
    protected int intscreen;
    private Keyboard typingwords;
    private Key[] chavi;
    // TODO this attribute should be gotten from Keyboard.
    private int roegape;
    protected TextView beforelater;
    protected PopupWindow mPreviewPopup;
    protected int beforenamelenth;
    protected int[] integerlayout;
    protected int befourenotkey = NOT_A_KEY;
    protected boolean beforeshedow = true;
    protected boolean clickshedo = true;
    protected int befourevalueA;
    protected int befourevalueB;
    protected int lauoytA;
    protected int SscreenvalueB;
    protected final int displyvalue;
    protected final int displyvaluejagaya;
    protected final int nextvalue;
    protected PopupWindow mMiniKeyboardPopup;
    protected My_Keybord_BaseImg_View shortkeyletar;
    protected View shortkeysizeview;
    protected View shortkeybase;
    protected boolean shortvaisablevalue;
    protected int shortvalueA;
    protected int shortvalueB;
    protected long shortkeytimae;
    protected int[] screenendsize;
    protected final float floteallkeysizeefectvalue;
    protected int shortnameidvalue;
    private OnKeyboardActionListener onlistner;
    private final ArrayList<My_Keybord_Img_Treker> onpoaintervalue = new ArrayList<My_Keybord_Img_Treker>();
    private boolean imgtrakervalue = false;  // TODO: Let the My_Keybord_Img_Treker class manage this pointer queue
    private final PointerQueue mykeybord = new PointerQueue();
    private final boolean clickleter;
    private int apll_splingtrek = 1;
    protected Set_dirKeyword splinggramer = new My_Keybord_proximity();
    private GestureDetector gramerlater;
    private final Next_Traacker_KeyWord nextwords = new Next_Traacker_KeyWord();
    private final int selectkeybord;
    private final boolean slderimgbhasha;
    private boolean img_add;
    private final Rect baseimgview = new Rect();
    private Bitmap bitmapvalue;
    private boolean removeandadd;
    private Key unusedkey;
    private Canvas appcan;
    private final Paint valueimg;
    private final Paint valuesize;
    private final Rect valuesettime;
    private final Rect watchlater = new Rect(0, 0, 0, 0);
    private int sizekeybord;
    private final HashMap<Integer, Integer> mTextHeightCache = new HashMap<Integer, Integer>();
    static Method sSetRenderMode;
    private static int sPrevRenderMode = -1;
    private final UIHandler unkelder = new UIHandler();



    class UIHandler extends Handler {
        private static final int MSG_POPUP_PREVIEW = 1;
        private static final int MSG_DISMISS_PREVIEW = 2;
        private static final int MSG_REPEAT_KEY = 3;
        private static final int MSG_LONGPRESS_KEY = 4;
        private boolean mInKeyRepeat;

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_POPUP_PREVIEW:
                    showKey(msg.arg1, (My_Keybord_Img_Treker) msg.obj);
                    break;
                case MSG_DISMISS_PREVIEW:
                    mPreviewPopup.dismiss();
                    break;
                case MSG_REPEAT_KEY: {
                    final My_Keybord_Img_Treker tracker = (My_Keybord_Img_Treker) msg.obj;
                    tracker.repeatKey(msg.arg1);
                    startKeyRepeatTimer(basikimgview, msg.arg1, tracker);
                    break;
                }
                case MSG_LONGPRESS_KEY: {
                    final My_Keybord_Img_Treker tracker = (My_Keybord_Img_Treker) msg.obj;
                    openPopupIfRequired(msg.arg1, tracker);
                    break;
                }
            }
        }

        public void popupPreview(long delay, int keyIndex, My_Keybord_Img_Treker tracker) {
            removeMessages(MSG_POPUP_PREVIEW);
            if (mPreviewPopup.isShowing() && beforelater.getVisibility() == VISIBLE) {
                showKey(keyIndex, tracker);
            } else {
                sendMessageDelayed(obtainMessage(MSG_POPUP_PREVIEW, keyIndex, 0, tracker),
                        delay);
            }
        }

        public void cancelPopupPreview() {
            removeMessages(MSG_POPUP_PREVIEW);
        }

        public void dismissPreview(long delay) {
            if (mPreviewPopup.isShowing()) {
                sendMessageDelayed(obtainMessage(MSG_DISMISS_PREVIEW), delay);
            }
        }

        public void cancelDismissPreview() {
            removeMessages(MSG_DISMISS_PREVIEW);
        }

        public void startKeyRepeatTimer(long delay, int keyIndex, My_Keybord_Img_Treker tracker) {
            mInKeyRepeat = true;
            sendMessageDelayed(obtainMessage(MSG_REPEAT_KEY, keyIndex, 0, tracker), delay);
        }

        public void cancelKeyRepeatTimer() {
            mInKeyRepeat = false;
            removeMessages(MSG_REPEAT_KEY);
        }

        public boolean isInKeyRepeat() {
            return mInKeyRepeat;
        }

        public void startLongPressTimer(long delay, int keyIndex, My_Keybord_Img_Treker tracker) {
            removeMessages(MSG_LONGPRESS_KEY);
            sendMessageDelayed(obtainMessage(MSG_LONGPRESS_KEY, keyIndex, 0, tracker), delay);
        }

        public void cancelLongPressTimer() {
            removeMessages(MSG_LONGPRESS_KEY);
        }

        public void cancelKeyTimers() {
            cancelKeyRepeatTimer();
            cancelLongPressTimer();
        }

        public void cancelAllMessages() {
            cancelKeyTimers();
            cancelPopupPreview();
            cancelDismissPreview();
        }
    }

    static class PointerQueue {
        private LinkedList<My_Keybord_Img_Treker> mQueue = new LinkedList<My_Keybord_Img_Treker>();

        public void add(My_Keybord_Img_Treker tracker) {
            mQueue.add(tracker);
        }

        public int lastIndexOf(My_Keybord_Img_Treker tracker) {
            LinkedList<My_Keybord_Img_Treker> queue = mQueue;
            for (int index = queue.size() - 1; index >= 0; index--) {
                My_Keybord_Img_Treker t = queue.get(index);
                if (t == tracker)
                    return index;
            }
            return -1;
        }

        public void releaseAllPointersOlderThan(My_Keybord_Img_Treker tracker, long eventTime) {
            LinkedList<My_Keybord_Img_Treker> queue = mQueue;
            int oldestPos = 0;
            for (My_Keybord_Img_Treker t = queue.get(oldestPos); t != tracker; t = queue.get(oldestPos)) {
                if (t.isModifier()) {
                    oldestPos++;
                } else {
                    t.onUpEvent(t.getLastX(), t.getLastY(), eventTime);
                    t.setAlreadyProcessed();
                    queue.remove(oldestPos);
                }
            }
        }

        public void releaseAllPointersExcept(My_Keybord_Img_Treker tracker, long eventTime) {
            for (My_Keybord_Img_Treker t : mQueue) {
                if (t == tracker)
                    continue;
                t.onUpEvent(t.getLastX(), t.getLastY(), eventTime);
                t.setAlreadyProcessed();
            }
            mQueue.clear();
            if (tracker != null)
                mQueue.add(tracker);
        }

        public void remove(My_Keybord_Img_Treker tracker) {
            mQueue.remove(tracker);
        }

        public boolean isInSlidingKeyInput() {
            for (final My_Keybord_Img_Treker tracker : mQueue) {
                if (tracker.isInSlidingKeyInput())
                    return true;
            }
            return false;
        }
    }

    static {
        initCompatibility();
    }

    static void initCompatibility() {
        try {
            sSetRenderMode = View.class.getMethod("setLayerType", int.class, Paint.class);
            Log.i(TAG, "setRenderMode is supported");
        } catch (SecurityException e) {
            Log.w(TAG, "unexpected SecurityException", e);
        } catch (NoSuchMethodException e) {
            // ignore, not supported by API level pre-Honeycomb
            Log.i(TAG, "ignoring render mode, not supported");
        }
    }

    private void setRenderModeIfPossible(int mode) {
        if (sSetRenderMode != null && mode != sPrevRenderMode) {
            try {
                sSetRenderMode.invoke(this, mode, null);
                sPrevRenderMode = mode;
                Log.i(TAG, "render mode set to " + LatinIME.service.readingmode);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public My_Keybord_BaseImg_View(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.keyboardViewStyle);
    }

    public My_Keybord_BaseImg_View(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        Log.i(TAG, "Creating new LatinKeyboardBaseView " + this);
        setRenderModeIfPossible(LatinIME.service.readingmode);

        TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.My_Keybord_BaseImg_View, defStyle, R.style.LatinKeyboardBaseView);

        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);

            switch (attr) {
                case R.styleable.My_Keybord_BaseImg_View_keyBackground:
                    imgbg = a.getDrawable(attr);
                    break;
                case R.styleable.My_Keybord_BaseImg_View_keyHysteresisDistance:
                    chavihistry = a.getDimensionPixelOffset(attr, 0);
                    break;
                case R.styleable.My_Keybord_BaseImg_View_verticalCorrection:
                    row_flote = a.getDimensionPixelOffset(attr, 0);
                    break;
                case R.styleable.My_Keybord_BaseImg_View_keyTextSize:
                    namelenth = a.getDimensionPixelSize(attr, 18);
                    break;
                case R.styleable.My_Keybord_BaseImg_View_keyTextColor:
                    namering = a.getColor(attr, 0xFF000000);
                    break;
                case R.styleable.My_Keybord_BaseImg_View_keyHintColor:
                    hintrungname = a.getColor(attr, 0xFFBBBBBB);
                    break;
                case R.styleable.My_Keybord_BaseImg_View_keyCursorColor:
                    chaviarorung = a.getColor(attr, 0xFF000000);
                    break;
                case R.styleable.My_Keybord_BaseImg_View_recolorSymbols:
                    rungstyle = a.getBoolean(attr, false);
                    break;
                case R.styleable.My_Keybord_BaseImg_View_labelTextSize:
                    floleterlenth = a.getDimensionPixelSize(attr, 14);
                    break;
                case R.styleable.My_Keybord_BaseImg_View_shadowColor:
                    shedorung = a.getColor(attr, 0);
                    break;
                case R.styleable.My_Keybord_BaseImg_View_shadowRadius:
                    shedovalue = a.getFloat(attr, 0f);
                    break;
                // TODO: Use Theme (android.R.styleable.Theme_backgroundDimAmount)
                case R.styleable.My_Keybord_BaseImg_View_backgroundDimAmount:
                    bgrupes = a.getFloat(attr, 0.5f);
                    break;
                case R.styleable.My_Keybord_BaseImg_View_backgroundAlpha:
                    leterbg = a.getInteger(attr, 255);
                    break;
                case R.styleable.My_Keybord_BaseImg_View_keyTextStyle:
                    int textStyle = a.getInt(attr, 0);
                    switch (textStyle) {
                        case 0:
                            nametheam = Typeface.DEFAULT;
                            break;
                        case 1:
                            nametheam = Typeface.DEFAULT_BOLD;
                            break;
                        default:
                            nametheam = Typeface.defaultFromStyle(textStyle);
                            break;
                    }
                    break;
                case R.styleable.My_Keybord_BaseImg_View_symbolColorScheme:
                    letrstylevalue = a.getInt(attr, 0);
                    break;
            }
        }

        final Resources res = getResources();

        beforeshedow = false;
        displyvalue = res.getInteger(R.integer.config_delay_before_preview);
        displyvaluejagaya = res.getInteger(R.integer.config_delay_before_space_preview);
        nextvalue = res.getInteger(R.integer.config_delay_after_preview);

        intscreen = 0;

        valueimg = new Paint();
        valueimg.setAntiAlias(true);
        valueimg.setTextAlign(Align.CENTER);
        valueimg.setAlpha(255);

        valuesize = new Paint();
        valuesize.setAntiAlias(true);
        valuesize.setTextAlign(Align.RIGHT);
        valuesize.setAlpha(255);
        valuesize.setTypeface(Typeface.DEFAULT_BOLD);

        valuesettime = new Rect(0, 0, 0, 0);
        imgbg.getPadding(valuesettime);

        selectkeybord = (int) (300 * res.getDisplayMetrics().density);
        // TODO: Refer frameworks/base/core/res/res/values/config.xml
        // TODO(klausw): turn off slderimgbhasha if no swipe actions are set?
        slderimgbhasha = res.getBoolean(R.bool.config_swipeDisambiguation);
        floteallkeysizeefectvalue = res.getDimension(R.dimen.mini_keyboard_slide_allowance);

        GestureDetector.SimpleOnGestureListener listener =
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onFling(MotionEvent me1, MotionEvent me2, float velocityX,
                                           float velocityY) {
                        final float absX = Math.abs(velocityX);
                        final float absY = Math.abs(velocityY);
                        float deltaX = me2.getX() - me1.getX();
                        float deltaY = me2.getY() - me1.getY();
                        nextwords.computeCurrentVelocity(1000);
                        final float endingVelocityX = nextwords.getXVelocity();
                        final float endingVelocityY = nextwords.getYVelocity();
                        int travelX = getWidth() / 3;
                        int travelY = getHeight() / 3;
                        int travelMin = Math.min(travelX, travelY);
                        if (velocityX > selectkeybord && absY < absX && deltaX > travelMin) {
                            if (slderimgbhasha && endingVelocityX >= velocityX / 4) {
                                if (swipeRight()) return true;
                            }
                        } else if (velocityX < -selectkeybord && absY < absX && deltaX < -travelMin) {
                            if (slderimgbhasha && endingVelocityX <= velocityX / 4) {
                                if (swipeLeft()) return true;
                            }
                        } else if (velocityY < -selectkeybord && absX < absY && deltaY < -travelMin) {
                            if (slderimgbhasha && endingVelocityY <= velocityY / 4) {
                                if (swipeUp()) return true;
                            }
                        } else if (velocityY > selectkeybord && absX < absY / 2 && deltaY > travelMin) {
                            if (slderimgbhasha && endingVelocityY >= velocityY / 4) {
                                if (swipeDown()) return true;
                            }
                        }
                        return false;
                    }
                };

        final boolean ignoreMultitouch = true;
        gramerlater = new GestureDetector(getContext(), listener, null, ignoreMultitouch);
        gramerlater.setIsLongpressEnabled(false);

        clickleter = context.getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_DISTINCT);
        basikimgview = res.getInteger(R.integer.config_key_repeat_interval);
    }

    private boolean showHints7Bit() {
        return LatinIME.service.haelpspling >= 1;
    }

    private boolean showHintsAll() {
        return LatinIME.service.haelpspling >= 2;
    }

    public void setOnKeyboardActionListener(OnKeyboardActionListener listener) {
        onlistner = listener;
        for (My_Keybord_Img_Treker tracker : onpoaintervalue) {
            tracker.setOnKeyboardActionListener(listener);
        }
    }

    protected OnKeyboardActionListener getOnKeyboardActionListener() {
        return onlistner;
    }

    public void setKeyboard(Keyboard keyboard) {
        if (typingwords != null) {
            dismissKeyPreview();
        }
        unkelder.cancelKeyTimers();
        unkelder.cancelPopupPreview();
        typingwords = keyboard;
        My_Keybord_Imglong.onSetKeyboard(keyboard);
        chavi = splinggramer.setKeyboard(keyboard, -getPaddingLeft(),
                -getPaddingTop() + row_flote);
        roegape = (int) getResources().getDimension(R.dimen.key_bottom_gap);
        for (My_Keybord_Img_Treker tracker : onpoaintervalue) {
            tracker.setKeyboard(chavi, chavihistry);
        }
        letersize = LatinIME.service.refernctext;
        if (keyboard.intrawo >= 4) letersize *= 5.0f / keyboard.intrawo;
        requestLayout();
        removeandadd = true;
        invalidateAllKeys();
        computeProximityThreshold(keyboard);
        mMiniKeyboardCacheMain.clear();
        mMiniKeyboardCacheShift.clear();
        mMiniKeyboardCacheCaps.clear();
        setRenderModeIfPossible(LatinIME.service.readingmode);
        imgtrakervalue = true;
    }


    public Keyboard getKeyboard() {
        return typingwords;
    }

    public boolean hasDistinctMultitouch() {
        return clickleter;
    }

    public boolean setShiftState(int shiftState) {
        if (typingwords != null) {
            if (typingwords.setShiftState(shiftState)) {
                invalidateAllKeys();
                return true;
            }
        }
        return false;
    }

    public void setCtrlIndicator(boolean active) {
        if (typingwords != null) {
            invalidateKey(typingwords.setCtrlIndicator(active));
        }
    }

    public void setAltIndicator(boolean active) {
        if (typingwords != null) {
            invalidateKey(typingwords.setAltIndicator(active));
        }
    }

    public void setMetaIndicator(boolean active) {
        if (typingwords != null) {
            invalidateKey(typingwords.setMetaIndicator(active));
        }
    }

    public int getShiftState() {
        if (typingwords != null) {
            return typingwords.getShiftState();
        }
        return Keyboard.SHIFT_OFF;
    }

    public boolean isShiftCaps() {
        return getShiftState() != Keyboard.SHIFT_OFF;
    }

    public boolean isShiftAll() {
        int state = getShiftState();
        if (LatinIME.service.keyshifevalue) {
            return state == Keyboard.SHIFT_ON || state == Keyboard.SHIFT_LOCKED;
        } else {
            return state == Keyboard.SHIFT_ON;
        }
    }

    public void setPreviewEnabled(boolean previewEnabled) {
        beforeshedow = previewEnabled;
    }

    public boolean isPreviewEnabled() {
        return beforeshedow;
    }

    private boolean isBlackSym() {
        return letrstylevalue == 1;
    }

    public void setPopupParent(View v) {
        shortkeybase = v;
    }

    public void setPopupOffset(int x, int y) {
        befourevalueA = x;
        befourevalueB = y;
        if (mPreviewPopup != null) mPreviewPopup.dismiss();
    }

    public void setProximityCorrectionEnabled(boolean enabled) {
        splinggramer.setProximityCorrectionEnabled(enabled);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (typingwords == null) {
            setMeasuredDimension(
                    getPaddingLeft() + getPaddingRight(), getPaddingTop() + getPaddingBottom());
        } else {
            int width = typingwords.getMinWidth() + getPaddingLeft() + getPaddingRight();
            if (MeasureSpec.getSize(widthMeasureSpec) < width + 10) {
                int badWidth = MeasureSpec.getSize(widthMeasureSpec);
                if (badWidth != width) Log.i(TAG, "ignoring unexpected width=" + badWidth);
            }
            Log.i(TAG, "onMeasure width=" + width);
            setMeasuredDimension(
                    width, typingwords.getHeight() + getPaddingTop() + getPaddingBottom());
        }
    }

    public boolean isProximityCorrectionEnabled() {
        return splinggramer.isProximityCorrectionEnabled();
    }

    private void computeProximityThreshold(Keyboard keyboard) {
        if (keyboard == null) return;
        final Key[] keys = chavi;
        if (keys == null) return;
        int length = keys.length;
        int dimensionSum = 0;
        for (int i = 0; i < length; i++) {
            Key key = keys[i];
            dimensionSum += Math.min(key.width, key.height + roegape) + key.gap;
        }
        if (dimensionSum < 0 || length == 0) return;
        splinggramer.setProximityThreshold((int) (dimensionSum * 1.4f / length));
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG, "onSizeChanged, w=" + w + ", h=" + h);
        sizekeybord = w;
        bitmapvalue = null;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        appcan = canvas;
        if (img_add || bitmapvalue == null || removeandadd) {
            onBufferDraw(canvas);
        }
        if (bitmapvalue != null) canvas.drawBitmap(bitmapvalue, 0, 0, null);
    }

    private void drawDeadKeyLabel(Canvas canvas, String hint, int x, float baseline, Paint paint) {
        char c = hint.charAt(0);
        String accent = Set_Symbole_Keybord.getSpacing(c);
        canvas.drawText(Keyboard.DEAD_KEY_PLACEHOLDER_STRING, x, baseline, paint);
        canvas.drawText(accent, x, baseline, paint);
    }

    private int getLabelHeight(Paint paint, int labelSize) {
        Integer labelHeightValue = mTextHeightCache.get(labelSize);
        if (labelHeightValue != null) {
            return labelHeightValue;
        } else {
            Rect textBounds = new Rect();
            paint.getTextBounds(KEY_LABEL_HEIGHT_REFERENCE_CHAR, 0, 1, textBounds);
            int labelHeight = textBounds.height();
            mTextHeightCache.put(labelSize, labelHeight);
            return labelHeight;
        }
    }

    private void onBufferDraw(Canvas canvas) {
        if (removeandadd) {
            typingwords.setKeyboardWidth(sizekeybord);
            invalidateAllKeys();
            removeandadd = false;
        }
        canvas.getClipBounds(baseimgview);

        if (typingwords == null) return;

        final Paint paint = valueimg;
        final Paint paintHint = valuesize;
        paintHint.setColor(hintrungname);
        final Drawable keyBackground = imgbg;
        final Rect clipRegion = watchlater;
        final Rect padding = valuesettime;
        final int kbdPaddingLeft = getPaddingLeft();
        final int kbdPaddingTop = getPaddingTop();
        final Key[] keys = chavi;
        final Key invalidKey = unusedkey;

        ColorFilter iconColorFilter = null;
        ColorFilter shadowColorFilter = null;
        if (rungstyle) {
            // TODO: cache these?
            iconColorFilter = new PorterDuffColorFilter(
                    namering, PorterDuff.Mode.SRC_ATOP);
            shadowColorFilter = new PorterDuffColorFilter(
                    shedorung, PorterDuff.Mode.SRC_ATOP);
        }

        boolean drawSingleKey = false;
        if (invalidKey != null && canvas.getClipBounds(clipRegion)) {
            // TODO we should use Rect.inset and Rect.contains here.
            if (invalidKey.x + kbdPaddingLeft - 1 <= clipRegion.left &&
                    invalidKey.y + kbdPaddingTop - 1 <= clipRegion.top &&
                    invalidKey.x + invalidKey.width + kbdPaddingLeft + 1 >= clipRegion.right &&
                    invalidKey.y + invalidKey.height + kbdPaddingTop + 1 >= clipRegion.bottom) {
                drawSingleKey = true;
            }
        }
        final int keyCount = keys.length;

        int keysDrawn = 0;
        for (int i = 0; i < keyCount; i++) {
            final Key key = keys[i];
            if (drawSingleKey && invalidKey != key) {
                continue;
            }
            if (!baseimgview.intersects(
                    key.x + kbdPaddingLeft,
                    key.y + kbdPaddingTop,
                    key.x + key.width + kbdPaddingLeft,
                    key.y + key.height + kbdPaddingTop)) {
                continue;
            }
            keysDrawn++;
            paint.setColor(key.isCursor ? chaviarorung : namering);

            int[] drawableState = key.getCurrentDrawableState();
            keyBackground.setState(drawableState);

            String label = key.getCaseLabel();

            float yscale = 1.0f;
            final Rect bounds = keyBackground.getBounds();
            if (key.width != bounds.right || key.height != bounds.bottom) {
                int minHeight = keyBackground.getMinimumHeight();
                if (minHeight > key.height) {
                    yscale = (float) key.height / minHeight;
                    keyBackground.setBounds(0, 0, key.width, minHeight);
                } else {
                    keyBackground.setBounds(0, 0, key.width, key.height);
                }
            }
            canvas.translate(key.x + kbdPaddingLeft, key.y + kbdPaddingTop);
            if (yscale != 1.0f) {
                canvas.save();
                canvas.scale(1.0f, yscale);
            }
            if (leterbg != 255) {
                keyBackground.setAlpha(leterbg);
            }
            keyBackground.draw(canvas);
            if (yscale != 1.0f) canvas.restore();

            boolean shouldDrawIcon = true;
            if (label != null) {
                final int labelSize;
                if (label.length() > 1 && key.codes.length < 2) {
                    labelSize = (int) (floleterlenth * letersize);
                    paint.setTypeface(Typeface.DEFAULT);
                } else {
                    labelSize = (int) (namelenth * letersize);
                    paint.setTypeface(nametheam);
                }
                paint.setFakeBoldText(key.isCursor);
                paint.setTextSize(labelSize);

                final int labelHeight = getLabelHeight(paint, labelSize);

                paint.setShadowLayer(shedovalue, 0, 0, shedorung);

                String hint = key.getHintLabel(showHints7Bit(), showHintsAll());
                if (!hint.equals("") && !(key.isShifted() && key.shiftLabel != null && hint.charAt(0) == key.shiftLabel.charAt(0))) {
                    int hintTextSize = (int) (namelenth * 0.6 * letersize);
                    paintHint.setTextSize(hintTextSize);

                    final int hintLabelHeight = getLabelHeight(paintHint, hintTextSize);
                    int x = key.width - padding.right;
                    int baseline = padding.top + hintLabelHeight * 12 / 10;
                    if (Character.getType(hint.charAt(0)) == Character.NON_SPACING_MARK) {
                        drawDeadKeyLabel(canvas, hint, x, baseline, paintHint);
                    } else {
                        canvas.drawText(hint, x, baseline, paintHint);
                    }
                }

                String altHint = key.getAltHintLabel(showHints7Bit(), showHintsAll());
                if (!altHint.equals("")) {
                    int hintTextSize = (int) (namelenth * 0.6 * letersize);
                    paintHint.setTextSize(hintTextSize);

                    final int hintLabelHeight = getLabelHeight(paintHint, hintTextSize);
                    int x = key.width - padding.right;
                    int baseline = padding.top + hintLabelHeight * (hint.equals("") ? 12 : 26) / 10;
                    if (Character.getType(altHint.charAt(0)) == Character.NON_SPACING_MARK) {
                        drawDeadKeyLabel(canvas, altHint, x, baseline, paintHint);
                    } else {
                        canvas.drawText(altHint, x, baseline, paintHint);
                    }
                }

                final int centerX = (key.width + padding.left - padding.right) / 2;
                final int centerY = (key.height + padding.top - padding.bottom) / 2;
                final float baseline = centerY
                        + labelHeight * KEY_LABEL_VERTICAL_ADJUSTMENT_FACTOR;
                if (key.isDeadKey()) {
                    drawDeadKeyLabel(canvas, label, centerX, baseline, paint);
                } else {
                    canvas.drawText(label, centerX, baseline, paint);
                }
                if (key.isCursor) {
                    paint.setShadowLayer(0, 0, 0, 0);

                    canvas.drawText(label, centerX + 0.5f, baseline, paint);
                    canvas.drawText(label, centerX - 0.5f, baseline, paint);
                    canvas.drawText(label, centerX, baseline + 0.5f, paint);
                    canvas.drawText(label, centerX, baseline - 0.5f, paint);
                }

                paint.setShadowLayer(0, 0, 0, 0);

                shouldDrawIcon = shouldDrawLabelAndIcon(key);
            }
            Drawable icon = key.icon;
            if (icon != null && shouldDrawIcon) {
                final int drawableWidth;
                final int drawableHeight;
                final int drawableX;
                final int drawableY;
                if (shouldDrawIconFully(key)) {
                    drawableWidth = key.width;
                    drawableHeight = key.height;
                    drawableX = 0;
                    drawableY = NUMBER_HINT_VERTICAL_ADJUSTMENT_PIXEL;
                } else {
                    drawableWidth = icon.getIntrinsicWidth();
                    drawableHeight = icon.getIntrinsicHeight();
                    drawableX = (key.width + padding.left - padding.right - drawableWidth) / 2;
                    drawableY = (key.height + padding.top - padding.bottom - drawableHeight) / 2;
                }
                canvas.translate(drawableX, drawableY);
                icon.setBounds(0, 0, drawableWidth, drawableHeight);

                if (shadowColorFilter != null && iconColorFilter != null) {
                    BlurMaskFilter shadowBlur = new BlurMaskFilter(shedovalue, BlurMaskFilter.Blur.OUTER);
                    Paint blurPaint = new Paint();
                    blurPaint.setMaskFilter(shadowBlur);
                    Bitmap tmpIcon = Bitmap.createBitmap(key.width, key.height, Bitmap.Config.ARGB_8888);
                    Canvas tmpCanvas = new Canvas(tmpIcon);
                    icon.draw(tmpCanvas);
                    int[] offsets = new int[2];
                    Bitmap shadowBitmap = tmpIcon.extractAlpha(blurPaint, offsets);
                    Paint shadowPaint = new Paint();
                    shadowPaint.setColorFilter(shadowColorFilter);
                    canvas.drawBitmap(shadowBitmap, offsets[0], offsets[1], shadowPaint);

                    icon.setColorFilter(iconColorFilter);
                    icon.draw(canvas);
                    icon.setColorFilter(null);
                } else {
                    icon.draw(canvas);
                }
                canvas.translate(-drawableX, -drawableY);
            }
            canvas.translate(-key.x - kbdPaddingLeft, -key.y - kbdPaddingTop);
        }
        unusedkey = null;
        if (shortvaisablevalue) {
            paint.setColor((int) (bgrupes * 0xFF) << 24);
            canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
        }

        if (LatinIME.service.clickview || DEBUG) {
            if (LatinIME.service.clickview || clickshedo) {
                for (My_Keybord_Img_Treker tracker : onpoaintervalue) {
                    int startX = tracker.getStartX();
                    int startY = tracker.getStartY();
                    int lastX = tracker.getLastX();
                    int lastY = tracker.getLastY();
                    paint.setAlpha(128);
                    paint.setColor(0xFFFF0000);
                    canvas.drawCircle(startX, startY, 3, paint);
                    canvas.drawLine(startX, startY, lastX, lastY, paint);
                    paint.setColor(0xFF0000FF);
                    canvas.drawCircle(lastX, lastY, 3, paint);
                    paint.setColor(0xFF00FF00);
                    canvas.drawCircle((startX + lastX) / 2, (startY + lastY) / 2, 2, paint);
                }
            }
        }

        img_add = false;
        baseimgview.setEmpty();
    }

    // TODO: clean up this method.
    private void dismissKeyPreview() {
        for (My_Keybord_Img_Treker tracker : onpoaintervalue)
            tracker.updateKey(NOT_A_KEY);
        showPreview(NOT_A_KEY, null);
    }

    public void showPreview(int keyIndex, My_Keybord_Img_Treker tracker) {
        int oldKeyIndex = befourenotkey;
        befourenotkey = keyIndex;
        final boolean isLanguageSwitchEnabled = (typingwords instanceof LatinMy_Keybord)
                && ((LatinMy_Keybord) typingwords).isLanguageSwitchEnabled();
        final boolean hidePreviewOrShowSpaceKeyPreview = (tracker == null)
                || tracker.isSpaceKey(keyIndex) || tracker.isSpaceKey(oldKeyIndex);
        if (oldKeyIndex != keyIndex
                && (beforeshedow
                || (hidePreviewOrShowSpaceKeyPreview && isLanguageSwitchEnabled))) {
            if (keyIndex == NOT_A_KEY) {
                unkelder.cancelPopupPreview();
                unkelder.dismissPreview(nextvalue);
            } else if (tracker != null) {
                int delay = beforeshedow ? displyvalue : displyvaluejagaya;
                unkelder.popupPreview(delay, keyIndex, tracker);
            }
        }
    }

    private void showKey(final int keyIndex, My_Keybord_Img_Treker tracker) {
        Key key = tracker.getKey(keyIndex);
        if (key == null)
            return;
        Drawable icon = key.icon;
        if (icon != null && !shouldDrawLabelAndIcon(key)) {
            beforelater.setCompoundDrawables(null, null, null,
                    key.iconPreview != null ? key.iconPreview : icon);
            beforelater.setText(null);
        } else {
            beforelater.setCompoundDrawables(null, null, null, null);
            beforelater.setText(key.getCaseLabel());
            if (key.label.length() > 1 && key.codes.length < 2) {
                beforelater.setTextSize(TypedValue.COMPLEX_UNIT_PX, namelenth);
                beforelater.setTypeface(Typeface.DEFAULT_BOLD);
            } else {
                beforelater.setTextSize(TypedValue.COMPLEX_UNIT_PX, beforenamelenth);
                beforelater.setTypeface(nametheam);
            }
        }
        beforelater.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        int popupWidth = Math.max(beforelater.getMeasuredWidth(), key.width
                + beforelater.getPaddingLeft() + beforelater.getPaddingRight());
        final int popupHeight = beforehit;
        LayoutParams lp = beforelater.getLayoutParams();
        if (lp != null) {
            lp.width = popupWidth;
            lp.height = popupHeight;
        }

        int popupPreviewX = key.x - (popupWidth - key.width) / 2;
        int popupPreviewY = key.y - popupHeight + before_effect;

        unkelder.cancelDismissPreview();
        if (integerlayout == null) {
            integerlayout = new int[2];
            getLocationInWindow(integerlayout);
            integerlayout[0] += befourevalueA;
            integerlayout[1] += befourevalueB;
            int[] windowLocation = new int[2];
            getLocationOnScreen(windowLocation);
            lauoytA = windowLocation[1];
        }
        boolean hasPopup = (getLongPressKeyboard(key) != null);
        beforelater.setBackgroundDrawable(getResources().getDrawable(hasPopup ? R.drawable.keyboard_key_feedback_more_background : R.drawable.keyboard_key_feedback_background));
        popupPreviewX += integerlayout[0];
        popupPreviewY += integerlayout[1];
        if (popupPreviewY + lauoytA < 0) {
            if (key.x + key.width <= getWidth() / 2) {
                popupPreviewX += (int) (key.width * 2.5);
            } else {
                popupPreviewX -= (int) (key.width * 2.5);
            }
            popupPreviewY += popupHeight;
        }

        if (mPreviewPopup.isShowing()) {
            mPreviewPopup.update(popupPreviewX, popupPreviewY, popupWidth, popupHeight);
        } else {
            mPreviewPopup.setWidth(popupWidth);
            mPreviewPopup.setHeight(popupHeight);
            mPreviewPopup.showAtLocation(shortkeybase, Gravity.NO_GRAVITY,
                    popupPreviewX, popupPreviewY);
        }
        SscreenvalueB = popupPreviewY;
        beforelater.setVisibility(VISIBLE);
    }

    public void invalidateAllKeys() {
        baseimgview.union(0, 0, getWidth(), getHeight());
        img_add = true;
        invalidate();
    }

    public void invalidateKey(Key key) {
        if (key == null)
            return;
        unusedkey = key;
        // TODO we should clean up this and record key's region to use in onBufferDraw.
        baseimgview.union(key.x + getPaddingLeft(), key.y + getPaddingTop(),
                key.x + key.width + getPaddingLeft(), key.y + key.height + getPaddingTop());
        invalidate(key.x + getPaddingLeft(), key.y + getPaddingTop(),
                key.x + key.width + getPaddingLeft(), key.y + key.height + getPaddingTop());
    }

    private boolean openPopupIfRequired(int keyIndex, My_Keybord_Img_Treker tracker) {
        if (intscreen == 0) {
            return false;
        }

        Key popupKey = tracker.getKey(keyIndex);
        if (popupKey == null)
            return false;
        if (tracker.isInSlidingKeyInput())
            return false;
        boolean result = onLongPress(popupKey);
        if (result) {
            dismissKeyPreview();
            shortnameidvalue = tracker.treker;
            tracker.setAlreadyProcessed();
            mykeybord.remove(tracker);
        }
        return result;
    }

    private void inflateMiniKeyboardContainer() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View container = inflater.inflate(intscreen, null);

        shortkeyletar =
                (My_Keybord_BaseImg_View) container.findViewById(R.id.basiimgview);
        shortkeyletar.setOnKeyboardActionListener(new OnKeyboardActionListener() {
            public void onKey(int primaryCode, int[] keyCodes, int x, int y) {
                onlistner.onKey(primaryCode, keyCodes, x, y);
                dismissPopupKeyboard();
            }

            public void onText(CharSequence text) {
                onlistner.onText(text);
                dismissPopupKeyboard();
            }

            public void onCancel() {
                onlistner.onCancel();
                dismissPopupKeyboard();
            }

            public boolean swipeLeft() {
                return false;
            }

            public boolean swipeRight() {
                return false;
            }

            public boolean swipeUp() {
                return false;
            }

            public boolean swipeDown() {
                return false;
            }

            public void onPress(int primaryCode) {
                onlistner.onPress(primaryCode);
            }

            public void onRelease(int primaryCode) {
                onlistner.onRelease(primaryCode);
            }
        });
        shortkeyletar.splinggramer = new My_Keybord_Short_keyword(floteallkeysizeefectvalue);
        shortkeyletar.gramerlater = null;

        shortkeyletar.setPopupParent(this);

        shortkeysizeview = container;
    }

    private static boolean isOneRowKeys(List<Key> keys) {
        if (keys.size() == 0) return false;
        final int edgeFlags = keys.get(0).edgeFlags;
        return (edgeFlags & Keyboard.EDGE_TOP) != 0 && (edgeFlags & Keyboard.EDGE_BOTTOM) != 0;
    }

    private Keyboard getLongPressKeyboard(Key popupKey) {
        final WeakHashMap<Key, Keyboard> cache;
        if (popupKey.isDistinctCaps()) {
            cache = mMiniKeyboardCacheCaps;
        } else if (popupKey.isShifted()) {
            cache = mMiniKeyboardCacheShift;
        } else {
            cache = mMiniKeyboardCacheMain;
        }
        Keyboard kbd = cache.get(popupKey);
        if (kbd == null) {
            kbd = popupKey.getPopupKeyboard(getContext(), getPaddingLeft() + getPaddingRight());
            if (kbd != null) cache.put(popupKey, kbd);
        }
        return kbd;
    }

    protected boolean onLongPress(Key popupKey) {
        // TODO if popupKey.popupCharacters has only one letter, send it as key without opening

        if (intscreen == 0) return false;

        Keyboard kbd = getLongPressKeyboard(popupKey);
        if (kbd == null) return false;

        if (shortkeysizeview == null) {
            inflateMiniKeyboardContainer();
        }
        if (shortkeyletar == null) return false;
        shortkeyletar.setKeyboard(kbd);
        shortkeysizeview.measure(MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.AT_MOST),
                MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.AT_MOST));

        if (screenendsize == null) {
            screenendsize = new int[2];
            getLocationInWindow(screenendsize);
        }


        final List<Key> miniKeys = shortkeyletar.getKeyboard().getKeys();
        final int miniKeyWidth = miniKeys.size() > 0 ? miniKeys.get(0).width : 0;

        int popupX = popupKey.x + screenendsize[0];
        popupX += getPaddingLeft();
        if (shouldAlignLeftmost(popupKey)) {
            popupX += popupKey.width - miniKeyWidth;
            popupX -= shortkeysizeview.getPaddingLeft();
        } else {
            popupX += miniKeyWidth;
            popupX -= shortkeysizeview.getMeasuredWidth();
            popupX += shortkeysizeview.getPaddingRight();
        }
        int popupY = popupKey.y + screenendsize[1];
        popupY += getPaddingTop();
        popupY -= shortkeysizeview.getMeasuredHeight();
        popupY += shortkeysizeview.getPaddingBottom();
        final int x = popupX;
        final int y = beforeshedow && isOneRowKeys(miniKeys) ? SscreenvalueB : popupY;

        int adjustedX = x;
        if (x < 0) {
            adjustedX = 0;
        } else if (x > (getMeasuredWidth() - shortkeysizeview.getMeasuredWidth())) {
            adjustedX = getMeasuredWidth() - shortkeysizeview.getMeasuredWidth();
        }
        shortvalueA = adjustedX + shortkeysizeview.getPaddingLeft() - screenendsize[0];
        shortvalueB = y + shortkeysizeview.getPaddingTop() - screenendsize[1];
        shortkeyletar.setPopupOffset(adjustedX, y);
        shortkeyletar.setShiftState(getShiftState());
        shortkeyletar.setPreviewEnabled(false);
        mMiniKeyboardPopup.setContentView(shortkeysizeview);
        mMiniKeyboardPopup.setWidth(shortkeysizeview.getMeasuredWidth());
        mMiniKeyboardPopup.setHeight(shortkeysizeview.getMeasuredHeight());
        mMiniKeyboardPopup.showAtLocation(this, Gravity.NO_GRAVITY, x, y);
        shortvaisablevalue = true;

        long eventTime = SystemClock.uptimeMillis();
        shortkeytimae = eventTime;
        MotionEvent downEvent = generateMiniKeyboardMotionEvent(MotionEvent.ACTION_DOWN, popupKey.x
                + popupKey.width / 2, popupKey.y + popupKey.height / 2, eventTime);
        shortkeyletar.onTouchEvent(downEvent);
        downEvent.recycle();

        invalidateAllKeys();
        return true;
    }

    private boolean shouldDrawIconFully(Key key) {
        return isNumberAtEdgeOfPopupChars(key) || isLatinF1Key(key)
                || LatinMy_Keybord.hasPuncOrSmileysPopup(key);
    }

    private boolean shouldDrawLabelAndIcon(Key key) {
        return isNonMicLatinF1Key(key)
                || LatinMy_Keybord.hasPuncOrSmileysPopup(key);
    }

    private boolean shouldAlignLeftmost(Key key) {
        return !key.popupReversed;
    }

    private boolean isLatinF1Key(Key key) {
        return (typingwords instanceof LatinMy_Keybord) && ((LatinMy_Keybord) typingwords).isF1Key(key);
    }

    private boolean isNonMicLatinF1Key(Key key) {
        return isLatinF1Key(key) && key.label != null;
    }

    private static boolean isNumberAtEdgeOfPopupChars(Key key) {
        return isNumberAtLeftmostPopupChar(key) || isNumberAtRightmostPopupChar(key);
    }

    static boolean isNumberAtLeftmostPopupChar(Key key) {
        if (key.popupCharacters != null && key.popupCharacters.length() > 0
                && isAsciiDigit(key.popupCharacters.charAt(0))) {
            return true;
        }
        return false;
    }

    static boolean isNumberAtRightmostPopupChar(Key key) {
        if (key.popupCharacters != null && key.popupCharacters.length() > 0
                && isAsciiDigit(key.popupCharacters.charAt(key.popupCharacters.length() - 1))) {
            return true;
        }
        return false;
    }

    private static boolean isAsciiDigit(char c) {
        return (c < 0x80) && Character.isDigit(c);
    }

    private MotionEvent generateMiniKeyboardMotionEvent(int action, int x, int y, long eventTime) {
        return MotionEvent.obtain(shortkeytimae, eventTime, action,
                x - shortvalueA, y - shortvalueB, 0);
    }

    boolean enableSlideKeyHack() {
        return false;
    }

    private My_Keybord_Img_Treker getPointerTracker(final int id) {
        final ArrayList<My_Keybord_Img_Treker> pointers = onpoaintervalue;
        final Key[] keys = chavi;
        final OnKeyboardActionListener listener = onlistner;

        for (int i = pointers.size(); i <= id; i++) {
            final My_Keybord_Img_Treker tracker =
                    new My_Keybord_Img_Treker(i, unkelder, splinggramer, this, getResources(), enableSlideKeyHack());
            if (keys != null)
                tracker.setKeyboard(keys, chavihistry);
            if (listener != null)
                tracker.setOnKeyboardActionListener(listener);
            pointers.add(tracker);
        }

        return pointers.get(id);
    }

    public boolean isInSlidingKeyInput() {
        if (shortvaisablevalue) {
            return shortkeyletar.isInSlidingKeyInput();
        } else {
            return mykeybord.isInSlidingKeyInput();
        }
    }

    public int getPointerCount() {
        return apll_splingtrek;
    }

    @Override
    public boolean onTouchEvent(MotionEvent me) {
        return onTouchEvent(me, false);
    }

    public boolean onTouchEvent(MotionEvent me, boolean continuing) {
        final int action = me.getActionMasked();
        final int pointerCount = me.getPointerCount();
        final int oldPointerCount = apll_splingtrek;
        apll_splingtrek = pointerCount;

        // TODO: cleanup this code into a multi-touch to single-touch event converter class?
        if (!clickleter && pointerCount > 1 && oldPointerCount > 1) {
            return true;
        }

        nextwords.addMovement(me);

        if (!shortvaisablevalue
                && gramerlater != null && gramerlater.onTouchEvent(me)) {
            dismissKeyPreview();
            unkelder.cancelKeyTimers();
            return true;
        }

        final long eventTime = me.getEventTime();
        final int index = me.getActionIndex();
        final int id = me.getPointerId(index);
        final int x = (int) me.getX(index);
        final int y = (int) me.getY(index);

        if (shortvaisablevalue) {
            final int miniKeyboardPointerIndex = me.findPointerIndex(shortnameidvalue);
            if (miniKeyboardPointerIndex >= 0 && miniKeyboardPointerIndex < pointerCount) {
                final int miniKeyboardX = (int) me.getX(miniKeyboardPointerIndex);
                final int miniKeyboardY = (int) me.getY(miniKeyboardPointerIndex);
                MotionEvent translated = generateMiniKeyboardMotionEvent(action,
                        miniKeyboardX, miniKeyboardY, eventTime);
                shortkeyletar.onTouchEvent(translated);
                translated.recycle();
            }
            return true;
        }

        if (unkelder.isInKeyRepeat()) {
            if (action == MotionEvent.ACTION_MOVE) {
                return true;
            }
            final My_Keybord_Img_Treker tracker = getPointerTracker(id);
            if (pointerCount > 1 && !tracker.isModifier()) {
                unkelder.cancelKeyRepeatTimer();
            }
        }

        // TODO: cleanup this code into a multi-touch to single-touch event converter class?
        if (!clickleter) {
            My_Keybord_Img_Treker tracker = getPointerTracker(0);
            if (pointerCount == 1 && oldPointerCount == 2) {
                tracker.onDownEvent(x, y, eventTime);
            } else if (pointerCount == 2 && oldPointerCount == 1) {
                tracker.onUpEvent(tracker.getLastX(), tracker.getLastY(), eventTime);
            } else if (pointerCount == 1 && oldPointerCount == 1) {
                tracker.onTouchEvent(action, x, y, eventTime);
            } else {
                Log.w(TAG, "Unknown touch panel behavior: pointer count is " + pointerCount
                        + " (old " + oldPointerCount + ")");
            }
            if (continuing)
                tracker.setSlidingKeyInputState(true);
            return true;
        }

        if (action == MotionEvent.ACTION_MOVE) {
            if (!imgtrakervalue) {
                for (int i = 0; i < pointerCount; i++) {
                    My_Keybord_Img_Treker tracker = getPointerTracker(me.getPointerId(i));
                    tracker.onMoveEvent((int) me.getX(i), (int) me.getY(i), eventTime);
                }
            }
        } else {
            My_Keybord_Img_Treker tracker = getPointerTracker(id);
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    imgtrakervalue = false;
                    onDownEvent(tracker, x, y, eventTime);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                    imgtrakervalue = false;
                    onUpEvent(tracker, x, y, eventTime);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    onCancelEvent(tracker, x, y, eventTime);
                    break;
            }
            if (continuing)
                tracker.setSlidingKeyInputState(true);
        }

        return true;
    }

    private void onDownEvent(My_Keybord_Img_Treker tracker, int x, int y, long eventTime) {
        if (tracker.isOnModifierKey(x, y)) {
            mykeybord.releaseAllPointersExcept(null, eventTime);
        }
        tracker.onDownEvent(x, y, eventTime);
        mykeybord.add(tracker);
    }

    private void onUpEvent(My_Keybord_Img_Treker tracker, int x, int y, long eventTime) {
        if (tracker.isModifier()) {
            mykeybord.releaseAllPointersExcept(tracker, eventTime);
        } else {
            int index = mykeybord.lastIndexOf(tracker);
            if (index >= 0) {
                mykeybord.releaseAllPointersOlderThan(tracker, eventTime);
            } else {
                Log.w(TAG, "onUpEvent: corresponding down event not found for pointer "
                        + tracker.treker);
            }
        }
        tracker.onUpEvent(x, y, eventTime);
        mykeybord.remove(tracker);
    }

    private void onCancelEvent(My_Keybord_Img_Treker tracker, int x, int y, long eventTime) {
        tracker.onCancelEvent(x, y, eventTime);
        mykeybord.remove(tracker);
    }

    protected boolean swipeRight() {
        return onlistner.swipeRight();
    }

    protected boolean swipeLeft() {
        return onlistner.swipeLeft();
    }

    boolean swipeUp() {
        return onlistner.swipeUp();
    }

    protected boolean swipeDown() {
        return onlistner.swipeDown();
    }

    public void closing() {
        Log.i(TAG, "closing " + this);
        if (mPreviewPopup != null) mPreviewPopup.dismiss();
        unkelder.cancelAllMessages();

        dismissPopupKeyboard();
        // TODO: destroy/recycle the views?

        // TODO(klausw): use a global bitmap repository, keeping two bitmaps permanently -
        mMiniKeyboardCacheMain.clear();
        mMiniKeyboardCacheShift.clear();
        mMiniKeyboardCacheCaps.clear();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        closing();
    }

    protected boolean popupKeyboardIsShowing() {
        return mMiniKeyboardPopup != null && mMiniKeyboardPopup.isShowing();
    }

    protected void dismissPopupKeyboard() {
        if (mMiniKeyboardPopup != null) {
            if (mMiniKeyboardPopup.isShowing()) {
                mMiniKeyboardPopup.dismiss();
            }
            shortvaisablevalue = false;
            invalidateAllKeys();
        }
    }

    public boolean handleBack() {
        if (mMiniKeyboardPopup != null && mMiniKeyboardPopup.isShowing()) {
            dismissPopupKeyboard();
            return true;
        }
        return false;
    }
}
