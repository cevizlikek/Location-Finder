package com.keyboard.keyboardthemes;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.inputmethodservice.InputMethodService;
import android.media.AudioManager;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.os.Vibrator;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.PrintWriterPrinter;
import android.util.Printer;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.keyboard.keyboardthemes.moden.Enter_Img_Keybord;
import com.keyboard.keyboardthemes.R;
import com.keyboard.keyboardthemes.other.My_Global_Service;
import com.keyboard.keyboardthemes.voiceme.Voise_service_methed_triger;
import com.keyboard.keyboardthemes.xml_file.My_Keyboard_dibug_Service;



public class LatinIME extends InputMethodService implements Img_My_Keyboard_Line, My_Keybord_BaseImg_View.OnKeyboardActionListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private static final boolean PERF_DEBUG = false;
    static final boolean DEBUG = false;
    static final boolean TRACE = false;
    static Map<Integer, String> ESC_SEQUENCES;
    static Map<Integer, Integer> CTRL_SEQUENCES;
    private static final String PREF_VIBRATE_ON = "vibrate_on";
    static final String PREF_VIBRATE_LEN = "vibrate_len";
    private static final String PREF_AUTO_CAP = "auto_cap";
    private List<CharSequence> chraecterlist;
    private int keybordon;
    private Bhasha_Keybord_Swich bhashaof;
    private static final String PREF_SOUND_ON = "sound_on";
    private static final String PREF_POPUP_ON = "popup_on";
    private int keybordbottem;
    private boolean otocrunt;
    private ModifierKeyState modyfiy = new ModifierKeyState();
    private boolean boolenright;
    private CharSequence popuchareter;
    private static final String PREF_QUICK_FIXES = "quick_fixes";
    private static final String PREF_SHOW_SUGGESTIONS = "show_suggestions";
    static final String PREF_FULLSCREEN_OVERRIDE = "fullscreen_override";
    private ModifierKeyState modifyimg = new ModifierKeyState();
    private ModifierKeyState modyfiyname = new ModifierKeyState();
    static final String PREF_HEIGHT_LANDSCAPE = "settings_height_landscape";
    static final String PREF_FORCE_KEYBOARD_ON = "force_keyboard_on";
    private boolean bolenspling;
    private String dabisidestring;
    private String volumstring;
    public static final My_Global_Service service = new My_Global_Service();
    static LatinIME keywordstring;
    private int ingintvalue;
    private int latintspling;
    private int splinghit;
    static final String PREF_VOL_UP = "pref_vol_up";
    static final String PREF_VOL_DOWN = "pref_vol_down";
    static final String PREF_SWIPE_RIGHT = "pref_swipe_right";
    private int numbervalue = 3;
    private static final int MSG_UPDATE_SUGGESTIONS = 0;
    private static final int MSG_START_TUTORIAL = 1;
    private static final int DELETE_ACCELERATE_AT = 20;
    private boolean typingoto;
    static final int ASCII_ENTER = '\n';
    private static final int MSG_VOICE_RESULTS = 3;
    static final int ASCII_SPACE = ' ';
    static final int ASCII_PERIOD = '.';
    static final String PREF_SUGGESTIONS_IN_LANDSCAPE = "suggestions_in_landscape";
    static final String PREF_CONNECTBOT_TAB_HACK = "connectbot_tab_hack";
    private static final int QUICK_PRESS = 200;
    static final String PREF_LONGPRESS_TIMEOUT = "pref_long_press_duration";
    private boolean review;
    private static final int POS_METHOD = 0;
    private static final int POS_SETTINGS = 1;
    private CompletionInfo[] offvalue;
    private AlertDialog alert;
    Typing_Switch mytypingword;
    private static final int MSG_UPDATE_SHIFT_STATE = 2;
    private static final int MSG_UPDATE_OLD_SUGGESTIONS = 4;
    private Resources valuer;
    private String localvaribale;
    private String stringvalue;
    static final String PREF_HEIGHT_PORTRAIT = "settings_height_portrait";
    static final String PREF_RENDER_MODE = "pref_render_mode";
    static final String PREF_SWIPE_UP = "pref_swipe_up";
    private boolean otoscreen;
    private boolean valuescreen;
    private boolean valuecomplite;
    private boolean layoutkey;
    private LinearLayout lenearkeybord;
    private Imgview_MyKeyboard mCandidateView;
    private My_Keyword_Inform_app mSuggest;
    static final String PREF_SWIPE_DOWN = "pref_swipe_down";
    static final String PREF_SWIPE_LEFT = "pref_swipe_left";
    private StringBuilder mComposing = new StringBuilder();
    private Keyword_Set_Compres compress = new Keyword_Set_Compres();
    private int intsize;
    private boolean mPredicting;
    private boolean soundvalue;
    private CharSequence allspling;
    private boolean mPredictionOnForMode;
    private boolean booleanmode;
    private static final String PREF_AUTO_COMPLETE = "auto_complete";
    private boolean mCompletionOn;
    private boolean filevalue;
    private boolean mAutoSpace;
    private static final String IME_OPTION_NO_MICROPHONE = "nm";
    private boolean bolenjagya;
    private boolean ototextletter;
    private boolean texthistry;
    private final boolean letterrevieu = false;
    public static final String PREF_SELECTED_LANGUAGES = "selected_languages";
    private boolean mAutoCorrectOn;
    // TODO move this state variable outside LatinIME
    private boolean keycontrol;
    private boolean keyalter;
    private boolean keymaeta;
    private boolean keyfunction;
    private static final String PREF_VOICE_MODE = "voice_mode";
    private int kayadd;
    private boolean keylockid;
    private boolean mVibrateOn;
    private User_keybord_Spiling fileprov;
    private User_keybord_Spiling_biigraam letterspling;
    private Outo_Spiling_Keybord splingotelater;
    private int soundsize;
    public static final String PREF_INPUT_LANGUAGE = "input_language";
    private boolean mSoundOn;
    private boolean mPopupOn;
    private boolean sploingfile;
    private boolean letterstart;
    private boolean lettertextfun;
    private boolean lettertextreview;
    private static final String PREF_RECORRECTION_ENABLED = "recorrection_enabled";
    private ModifierKeyState mFnKeyState = new ModifierKeyState();
    private boolean leterrteviewstart;
    private boolean letterofreview;
    private String topstring;
    private String endstring;
    private String jamnisidestring;
    static final String PREF_KEYBOARD_NOTIFICATION = "keyboard_notification";
    private boolean mRefreshKeyboardRequired;
    private int mKeyboardModeOverrideLandscape;
    private int mCorrectionMode;
    private Img_My_Keyboard_Base keybordebase = new Set_Symbole_Keybord(this);
    private boolean soundeffect = true;
    private boolean soundlocakl;
    private int speshstring;
    static final String PREF_HINT_MODE = "pref_hint_mode";
    private int removenum;
    private long endchavi;
    private ModifierKeyState modyfiykeytext = new ModifierKeyState();
    private String botemsound;
    private boolean letervalue = false;
    private Img_My_Keyboard_Base symbole = new Symbole_Keyword(this);
    private KeyTuttoraialBord latme;
    private AudioManager spekermng;
    private ModifierKeyState modyfiyimg = new ModifierKeyState();
    private final float FX_VOLUME = -1.0f;
    private final float FX_VOLUME_RANGE_DB = 72.0f;
    private boolean mSilentMode;
    String splingsound;
    static final String PREF_FULL_KEYBOARD_IN_PORTRAIT = "full_keyboard_in_portrait";
    private String wordlater;
    private boolean mConfigurationChanging;
    private CharSequence addlater;
    private static final String TAG = "PCKeyboardIME";
    private Map<String, List<CharSequence>> spelingstyle = new HashMap<String, List<CharSequence>>();
    private ArrayList<WordAlternatives> splinglist = new ArrayList<WordAlternatives>();
    private My_Keybord_Mnager latermng;
    private My_Keyword_Notification msgreview;
    private Voise_service_methed_triger soundkeyeffect;


    public abstract static class WordAlternatives {
        protected CharSequence mChosenWord;

        public WordAlternatives() {
        }

        public WordAlternatives(CharSequence chosenWord) {
            mChosenWord = chosenWord;
        }

        @Override
        public int hashCode() {
            return mChosenWord.hashCode();
        }

        public abstract CharSequence getOriginalWord();

        public CharSequence getChosenWord() {
            return mChosenWord;
        }

        public abstract List<CharSequence> getAlternatives();
    }

    public class TypedWordAlternatives extends WordAlternatives {
        private Keyword_Set_Compres word;

        public TypedWordAlternatives() {
        }

        public TypedWordAlternatives(CharSequence chosenWord,
                                     Keyword_Set_Compres wordComposer) {
            super(chosenWord);
            word = wordComposer;
        }

        @Override
        public CharSequence getOriginalWord() {
            return word.getTypedWord();
        }

        @Override
        public List<CharSequence> getAlternatives() {
            return getTypedSuggestions(word);
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UPDATE_SUGGESTIONS:
                    updateSuggestions();
                    break;
                case MSG_UPDATE_OLD_SUGGESTIONS:
                    setOldSuggestions();
                    break;
                case MSG_START_TUTORIAL:
                    if (latme == null) {
                        if (mytypingword.getInputView().isShown()) {
                            latme = new KeyTuttoraialBord(LatinIME.this,
                                    mytypingword.getInputView());
                            latme.start();
                        } else {
                            sendMessageDelayed(obtainMessage(MSG_START_TUTORIAL),
                                    100);
                        }
                    }
                    break;
                case MSG_UPDATE_SHIFT_STATE:
                    updateShiftKeyState(getCurrentInputEditorInfo());
                    break;
            }
        }
    };

    @Override
    public void onCreate() {
        Log.i("PCKeyboard", "onCreate(), os.version=" + System.getProperty("os.version"));
        My_Keybord_Imglong.init(this);
        Typing_Switch.init(this);
        super.onCreate();
        keywordstring = this;
        valuer = getResources();
        final Configuration conf = valuer.getConfiguration();
        speshstring = conf.orientation;
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        bhashaof = new Bhasha_Keybord_Swich(this);
        bhashaof.loadLocales(prefs);
        mytypingword = Typing_Switch.getInstance();
        mytypingword.setLanguageSwitcher(bhashaof);
        stringvalue = conf.locale.toString();
        bhashaof.setSystemLocale(conf.locale);
        String inputLanguage = bhashaof.getInputLanguage();
        if (inputLanguage == null) {
            inputLanguage = conf.locale.toString();
        }
        Resources res = getResources();
        texthistry = prefs.getBoolean(PREF_RECORRECTION_ENABLED, res.getBoolean(R.bool.default_recorrection_enabled));
        sploingfile = prefs.getBoolean(PREF_CONNECTBOT_TAB_HACK, res.getBoolean(R.bool.default_connectbot_tab_hack));
        layoutkey = prefs.getBoolean(PREF_FULLSCREEN_OVERRIDE, res.getBoolean(R.bool.default_fullscreen_override));
        letterstart = prefs.getBoolean(PREF_FORCE_KEYBOARD_ON, res.getBoolean(R.bool.default_force_keyboard_on));
        lettertextfun = prefs.getBoolean(PREF_KEYBOARD_NOTIFICATION, res.getBoolean(R.bool.default_keyboard_notification));
        lettertextreview = prefs.getBoolean(PREF_SUGGESTIONS_IN_LANDSCAPE, res.getBoolean(R.bool.default_suggestions_in_landscape));
        latintspling = getHeight(prefs, PREF_HEIGHT_PORTRAIT, res.getString(R.string.default_height_portrait));
        splinghit = getHeight(prefs, PREF_HEIGHT_LANDSCAPE, res.getString(R.string.default_height_landscape));
        LatinIME.service.haelpspling = Integer.parseInt(prefs.getString(PREF_HINT_MODE, res.getString(R.string.default_hint_mode)));
        LatinIME.service.seekbarwatch = getPrefInt(prefs, PREF_LONGPRESS_TIMEOUT, res.getString(R.string.default_long_press_duration));
        LatinIME.service.readingmode = getPrefInt(prefs, PREF_RENDER_MODE, res.getString(R.string.default_render_mode));
        topstring = prefs.getString(PREF_SWIPE_UP, res.getString(R.string.default_swipe_up));
        endstring = prefs.getString(PREF_SWIPE_DOWN, res.getString(R.string.default_swipe_down));
        jamnisidestring = prefs.getString(PREF_SWIPE_LEFT, res.getString(R.string.default_swipe_left));
        dabisidestring = prefs.getString(PREF_SWIPE_RIGHT, res.getString(R.string.default_swipe_right));
        volumstring = prefs.getString(PREF_VOL_UP, res.getString(R.string.default_vol_up));
        botemsound = prefs.getString(PREF_VOL_DOWN, res.getString(R.string.default_vol_down));
        service.initPrefs(prefs, res);
        soundkeyeffect = new Voise_service_methed_triger(this);
        updateKeyboardOptions();
        My_Keybord_Mnager.getPluginDictionaries(getApplicationContext());
        latermng = new My_Keybord_Mnager(this);
        final IntentFilter pFilter = new IntentFilter();
        pFilter.addDataScheme("package");
        pFilter.addAction("android.intent.action.PACKAGE_ADDED");
        pFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        pFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        registerReceiver(latermng, pFilter);
        My_Keybord_Img_Untils.GCUtils.getInstance().reset();
        boolean tryGC = true;
        for (int i = 0; i < My_Keybord_Img_Untils.GCUtils.GC_TRY_LOOP_MAX && tryGC; ++i) {
            try {
                initSuggest(inputLanguage);
                tryGC = false;
            } catch (OutOfMemoryError e) {
                tryGC = My_Keybord_Img_Untils.GCUtils.getInstance().tryGCOrWait(inputLanguage, e);
            }
        }
        speshstring = conf.orientation;
        IntentFilter intentet = new IntentFilter(AudioManager.RINGER_MODE_CHANGED_ACTION);
        registerReceiver(mReceiver, intentet);
        prefs.registerOnSharedPreferenceChangeListener(this);
        setNotification(lettertextfun);
    }

    private int getKeyboardModeNum(int origMode, int override) {
        if (numbervalue == 2 && origMode == 2) origMode = 1;
        int num = (origMode + override) % numbervalue;
        if (numbervalue == 2 && num == 1) num = 2;
        return num;
    }

    private void updateKeyboardOptions() {
        boolean isPortrait = isPortrait();
        int kbMode;
        numbervalue = service.compactModeEnabled ? 3 : 2; // FIXME!
        if (isPortrait) {
            kbMode = getKeyboardModeNum(service.typingbordzero, ingintvalue);
        } else {
            kbMode = getKeyboardModeNum(service.typingvaluetwo, mKeyboardModeOverrideLandscape);
        }
        int screenHeightPercent = isPortrait ? latintspling : splinghit;
        LatinIME.service.keyboardMode = kbMode;
        LatinIME.service.typingwordhit = (float) screenHeightPercent;
    }

    private void setNotification(boolean visible) {
        final String ACTION = "com.keyboard.keyboardthemes.SHOW";
        final int ID = 1;
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);

        if (visible && msgreview == null) {
            int icon = R.drawable.icon;
            CharSequence text = "Keyboard notification enabled.";
            long when = System.currentTimeMillis();
            Notification notification = new Notification(icon, text, when);

            // TODO: clean this up?
            msgreview = new My_Keyword_Notification(this);
            final IntentFilter keybord = new IntentFilter(ACTION);
            registerReceiver(msgreview, keybord);
            Intent notificationIntent = new Intent(ACTION);
            PendingIntent contentIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, notificationIntent, 0);
            String name = "Show Exo Keyboard";
            String frem = "Select this to open the keyboard. Disable in settings.";
            notification.flags |= Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;


            Notification noti = new Notification.Builder(this.getApplicationContext())
                    .setContentTitle(name)
                    .setStyle(new Notification.BigTextStyle().bigText(frem))
                    .setContentText(frem)
                    .setContentIntent(contentIntent).build();

            mNotificationManager.notify(ID, noti);
        } else if (msgreview != null) {
            mNotificationManager.cancel(ID);
            unregisterReceiver(msgreview);
            msgreview = null;
        }
    }

    private boolean isPortrait() {
        return (speshstring == Configuration.ORIENTATION_PORTRAIT);
    }

    private boolean suggestionsDisabled() {
        if (letterofreview) return true;
        if (leterrteviewstart) return false;
        return !(lettertextreview || isPortrait());
    }

    static int[] getDictionary(Resources res) {
        String packageName = LatinIME.class.getPackage().getName();
        XmlResourceParser xrp = res.getXml(R.xml.dictionary);
        ArrayList<Integer> dictionaries = new ArrayList<Integer>();

        try {
            int current = xrp.getEventType();
            while (current != XmlResourceParser.END_DOCUMENT) {
                if (current == XmlResourceParser.START_TAG) {
                    String tag = xrp.getName();
                    if (tag != null) {
                        if (tag.equals("part")) {
                            String dictFileName = xrp.getAttributeValue(null,
                                    "name");
                            dictionaries.add(res.getIdentifier(dictFileName,
                                    "raw", packageName));
                        }
                    }
                }
                xrp.next();
                current = xrp.getEventType();
            }
        } catch (XmlPullParserException e) {
            Log.e(TAG, "Dictionary XML parsing failure");
        } catch (IOException e) {
            Log.e(TAG, "Dictionary XML IOException");
        }

        int count = dictionaries.size();
        int[] dict = new int[count];
        for (int i = 0; i < count; i++) {
            dict[i] = dictionaries.get(i);
        }

        return dict;
    }

    private void initSuggest(String locale) {
        localvaribale = locale;

        Resources orig = getResources();
        Configuration conf = orig.getConfiguration();
        Locale saveLocale = conf.locale;
        conf.locale = new Locale(locale);
        orig.updateConfiguration(conf, orig.getDisplayMetrics());
        if (mSuggest != null) {
            mSuggest.close();
        }
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(this);
        valuecomplite = sp.getBoolean(PREF_QUICK_FIXES, getResources()
                .getBoolean(R.bool.default_quick_fixes));

        int[] dictionaries = getDictionary(orig);
        mSuggest = new My_Keyword_Inform_app(this, dictionaries);
        updateAutoTextEnabled(saveLocale);
        if (fileprov != null)
            fileprov.close();
        fileprov = new User_keybord_Spiling(this, localvaribale);
        if (splingotelater != null) {
            splingotelater.close();
        }
        splingotelater = new Outo_Spiling_Keybord(this, this, localvaribale, My_Keyword_Inform_app.DIC_AUTO);
        if (letterspling != null) {
            letterspling.close();
        }
        letterspling = new User_keybord_Spiling_biigraam(this, this, localvaribale, My_Keyword_Inform_app.DIC_USER);
        mSuggest.setUserBigramDictionary(letterspling);
        mSuggest.setUserDictionary(fileprov);
        mSuggest.setAutoDictionary(splingotelater);
        updateCorrectionMode();
        splingsound = valuer.getString(R.string.word_separators);
        wordlater = valuer.getString(R.string.sentence_separators);
        initSuggestPuncList();

        conf.locale = saveLocale;
        orig.updateConfiguration(conf, orig.getDisplayMetrics());
    }

    @Override
    public void onDestroy() {
        if (fileprov != null) {
            fileprov.close();
        }
        unregisterReceiver(mReceiver);
        unregisterReceiver(latermng);
        if (msgreview != null) {
            unregisterReceiver(msgreview);
            msgreview = null;
        }
        My_Keybord_Imglong.commit();
        My_Keybord_Imglong.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration conf) {
        Log.i("PCKeyboard", "onConfigurationChanged()");
        final String systemLocale = conf.locale.toString();
        if (!TextUtils.equals(systemLocale, stringvalue)) {
            stringvalue = systemLocale;
            if (bhashaof != null) {
                bhashaof.loadLocales(PreferenceManager.getDefaultSharedPreferences(this));
                bhashaof.setSystemLocale(conf.locale);
                toggleLanguage(true, true);
            } else {
                reloadKeyboards();
            }
        }
        if (conf.orientation != speshstring) {
            InputConnection conction = getCurrentInputConnection();
            commitTyped(conction, true);
            if (conction != null)
                conction.finishComposingText();
            speshstring = conf.orientation;
            reloadKeyboards();
            removeCandidateViewContainer();
        }
        mConfigurationChanging = true;
        super.onConfigurationChanged(conf);
        mConfigurationChanging = false;
    }

    @Override
    public View onCreateInputView() {
        setCandidatesViewShown(false);
        mytypingword.recreateInputView();
        mytypingword.makeKeyboards(true);
        mytypingword.setKeyboardMode(Typing_Switch.ONE_VALUE, 0, shouldShowVoiceButton(getCurrentInputEditorInfo()));
        return mytypingword.getInputView();
    }

    @Override
    public AbstractInputMethodImpl onCreateInputMethodInterface() {
        return new MyInputMethodImpl();
    }

    IBinder mToken;

    public class MyInputMethodImpl extends InputMethodImpl {
        @Override
        public void attachToken(IBinder token) {
            super.attachToken(token);
            Log.i(TAG, "attachToken " + token);
            if (mToken == null) {
                mToken = token;
            }
        }
    }

    @Override
    public View onCreateCandidatesView() {
        if (lenearkeybord == null) {
            lenearkeybord = (LinearLayout) getLayoutInflater().inflate(R.layout.img_keybord, null);
            mCandidateView = (Imgview_MyKeyboard) lenearkeybord.findViewById(R.id.candidates);
            mCandidateView.setPadding(0, 0, 0, 0);
            mCandidateView.setService(this);
            setCandidatesView(lenearkeybord);
        }
        return lenearkeybord;
    }

    private void removeCandidateViewContainer() {
        if (lenearkeybord != null) {
            lenearkeybord.removeAllViews();
            ViewParent parent = lenearkeybord.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(lenearkeybord);
            }
            lenearkeybord = null;
            mCandidateView = null;
        }
        resetPrediction();
    }

    private void resetPrediction() {
        mComposing.setLength(0);
        mPredicting = false;
        removenum = 0;
        bolenjagya = false;
    }

    @Override
    public void onStartInputView(EditorInfo attribute, boolean restarting) {
        service.entertypingtext = attribute.packageName;
        service.etertextname = attribute.fieldName;
        service.entertextid = attribute.fieldId;
        service.entertextmethed = attribute.inputType;

        My_Keybord_Img_Key inputView = mytypingword.getInputView();
        if (inputView == null) {
            return;
        }

        if (mRefreshKeyboardRequired) {
            mRefreshKeyboardRequired = false;
            toggleLanguage(true, true);
        }

        mytypingword.makeKeyboards(false);

        TextEntryState.newSession(this);

        keylockid = false;
        int variation = attribute.inputType & EditorInfo.TYPE_MASK_VARIATION;
        if (variation == EditorInfo.TYPE_TEXT_VARIATION_PASSWORD
                || variation == EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                || variation == 0xe0
                ) {
            if ((attribute.inputType & EditorInfo.TYPE_MASK_CLASS) == EditorInfo.TYPE_CLASS_TEXT) {
                keylockid = true;
            }
        }

        soundvalue = shouldShowVoiceButton(attribute);
        final boolean enableVoiceButton = soundvalue && soundeffect;

        if (soundkeyeffect != null) {
            soundkeyeffect.onStartInputView();
        }

        otocrunt = false;
        mPredictionOnForMode = false;
        mCompletionOn = false;
        offvalue = null;
        keycontrol = false;
        keyalter = false;
        keymaeta = false;
        keyfunction = false;
        addlater = null;
        leterrteviewstart = false;
        letterofreview = false;
        ingintvalue = 0;
        mKeyboardModeOverrideLandscape = 0;
        service.bolenglobemode = false;

        switch (attribute.inputType & EditorInfo.TYPE_MASK_CLASS) {
            case EditorInfo.TYPE_CLASS_NUMBER:
            case EditorInfo.TYPE_CLASS_DATETIME:
                // TODO: Use a dedicated number entry keypad here when we get one.
            case EditorInfo.TYPE_CLASS_PHONE:
                mytypingword.setKeyboardMode(Typing_Switch.THREE_VALUE,
                        attribute.imeOptions, enableVoiceButton);
                break;
            case EditorInfo.TYPE_CLASS_TEXT:
                mytypingword.setKeyboardMode(Typing_Switch.ONE_VALUE,
                        attribute.imeOptions, enableVoiceButton);
                mPredictionOnForMode = true;
                if (keylockid) {
                    mPredictionOnForMode = false;
                }
                if (variation == EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                        || variation == EditorInfo.TYPE_TEXT_VARIATION_PERSON_NAME
                        || !bhashaof.allowAutoSpace()) {
                    mAutoSpace = false;
                } else {
                    mAutoSpace = true;
                }
                if (variation == EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS) {
                    mPredictionOnForMode = false;
                    mytypingword.setKeyboardMode(Typing_Switch.FIVE_VALUE,
                            attribute.imeOptions, enableVoiceButton);
                } else if (variation == EditorInfo.TYPE_TEXT_VARIATION_URI) {
                    mPredictionOnForMode = false;
                    mytypingword.setKeyboardMode(Typing_Switch.FOURE_VALUE,
                            attribute.imeOptions, enableVoiceButton);
                } else if (variation == EditorInfo.TYPE_TEXT_VARIATION_SHORT_MESSAGE) {
                    mytypingword.setKeyboardMode(Typing_Switch.SIX_VALUE,
                            attribute.imeOptions, enableVoiceButton);
                } else if (variation == EditorInfo.TYPE_TEXT_VARIATION_FILTER) {
                    mPredictionOnForMode = false;
                } else if (variation == EditorInfo.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT) {
                    mytypingword.setKeyboardMode(Typing_Switch.SEVEN_VALUE,
                            attribute.imeOptions, enableVoiceButton);
                    if ((attribute.inputType & EditorInfo.TYPE_TEXT_FLAG_AUTO_CORRECT) == 0) {
                        otocrunt = true;
                    }
                }

                if ((attribute.inputType & EditorInfo.TYPE_TEXT_FLAG_NO_SUGGESTIONS) != 0) {
                    mPredictionOnForMode = false;
                    otocrunt = true;
                }
                if ((attribute.inputType & EditorInfo.TYPE_TEXT_FLAG_AUTO_CORRECT) == 0
                        && (attribute.inputType & EditorInfo.TYPE_TEXT_FLAG_MULTI_LINE) == 0) {
                    otocrunt = true;
                }
                if ((attribute.inputType & EditorInfo.TYPE_TEXT_FLAG_AUTO_COMPLETE) != 0) {
                    mPredictionOnForMode = false;
                    mCompletionOn = isFullscreenMode();
                }
                break;
            default:
                mytypingword.setKeyboardMode(Typing_Switch.ONE_VALUE,
                        attribute.imeOptions, enableVoiceButton);
        }
        inputView.closing();
        resetPrediction();
        loadSettings();
        updateShiftKeyState(attribute);

        booleanmode = (mCorrectionMode > 0 || review);
        setCandidatesViewShownInternal(isCandidateStripVisible()
                || mCompletionOn, false);
        updateSuggestions();

        filevalue = mSuggest.hasMainDictionary();

        updateCorrectionMode();

        inputView.setPreviewEnabled(mPopupOn);
        inputView.setProximityCorrectionEnabled(true);
        checkReCorrectionOnStart();
        checkTutorial(attribute.privateImeOptions);
        if (TRACE)
            Debug.startMethodTracing("/data/trace/latinime");
    }

    private boolean shouldShowVoiceButton(EditorInfo attribute) {
        // TODO Auto-generated method stub
        return true;
    }

    private void checkReCorrectionOnStart() {
        if (texthistry && isPredictionOn()) {
            InputConnection ic = getCurrentInputConnection();
            if (ic == null)
                return;
            ExtractedTextRequest etr = new ExtractedTextRequest();
            etr.token = 0;
            ExtractedText et = ic.getExtractedText(etr, 0);
            if (et == null)
                return;

            keybordon = et.startOffset + et.selectionStart;
            keybordbottem = et.startOffset + et.selectionEnd;

            if (!TextUtils.isEmpty(et.text) && isCursorTouchingWord()) {
                postUpdateOldSuggestions();
            }
        }
    }

    @Override
    public void onFinishInput() {
        super.onFinishInput();

        My_Keybord_Imglong.commit();
        onAutoCompletionStateChanged(false);

        if (mytypingword.getInputView() != null) {
            mytypingword.getInputView().closing();
        }
        if (splingotelater != null)
            splingotelater.flushPendingWrites();
        if (letterspling != null)
            letterspling.flushPendingWrites();
    }

    @Override
    public void onFinishInputView(boolean finishingInput) {
        super.onFinishInputView(finishingInput);
        mHandler.removeMessages(MSG_UPDATE_SUGGESTIONS);
        mHandler.removeMessages(MSG_UPDATE_OLD_SUGGESTIONS);
    }

    @Override
    public void onUpdateExtractedText(int token, ExtractedText text) {
        super.onUpdateExtractedText(token, text);
        InputConnection ic = getCurrentInputConnection();
    }

    @Override
    public void onUpdateSelection(int oldSelStart, int oldSelEnd,
                                  int newSelStart, int newSelEnd, int candidatesStart,
                                  int candidatesEnd) {
        super.onUpdateSelection(oldSelStart, oldSelEnd, newSelStart, newSelEnd,
                candidatesStart, candidatesEnd);

        if (DEBUG) {
            Log.i(TAG, "onUpdateSelection: oss=" + oldSelStart + ", ose="
                    + oldSelEnd + ", nss=" + newSelStart + ", nse=" + newSelEnd
                    + ", cs=" + candidatesStart + ", ce=" + candidatesEnd);
        }

        if ((((mComposing.length() > 0 && mPredicting))
                && (newSelStart != candidatesEnd || newSelEnd != candidatesEnd) && keybordon != newSelStart)) {
            mComposing.setLength(0);
            mPredicting = false;
            postUpdateSuggestions();
            TextEntryState.reset();
            InputConnection ic = getCurrentInputConnection();
            if (ic != null) {
                ic.finishComposingText();
            }
        } else if (!mPredicting && !boolenright) {
            switch (TextEntryState.getState()) {
                case ACCEPTED_DEFAULT:
                    TextEntryState.reset();
                case SPACE_AFTER_PICKED:
                    bolenjagya = false;
                    break;
            }
        }
        boolenright = false;
        postUpdateShiftKeyState();
        keybordon = newSelStart;
        keybordbottem = newSelEnd;

        if (texthistry) {
            if (mytypingword != null
                    && mytypingword.getInputView() != null
                    && mytypingword.getInputView().isShown()) {
                if (isPredictionOn()
                        && popuchareter == null
                        && (candidatesStart == candidatesEnd
                        || newSelStart != oldSelStart || TextEntryState
                        .isCorrecting())
                        && (newSelStart < newSelEnd - 1 || (!mPredicting))) {
                    if (isCursorTouchingWord()
                            || keybordon < keybordbottem) {
                        postUpdateOldSuggestions();
                    } else {
                        abortCorrection(false);
                        if (mCandidateView != null
                                && !chraecterlist.equals(mCandidateView
                                .getSuggestions())
                                && !mCandidateView
                                .isShowingAddToDictionaryHint()) {
                            setNextSuggestions();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onExtractedTextClicked() {
        if (texthistry && isPredictionOn())
            return;

        super.onExtractedTextClicked();
    }

    @Override
    public void onExtractedCursorMovement(int dx, int dy) {
        if (texthistry && isPredictionOn())
            return;

        super.onExtractedCursorMovement(dx, dy);
    }

    @Override
    public void hideWindow() {
        My_Keybord_Imglong.commit();
        onAutoCompletionStateChanged(false);

        if (TRACE)
            Debug.stopMethodTracing();
        if (alert != null && alert.isShowing()) {
            alert.dismiss();
            alert = null;
        }
        spelingstyle.clear();
        splinglist.clear();
        super.hideWindow();
        TextEntryState.endSession();
    }

    @Override
    public void onDisplayCompletions(CompletionInfo[] completions) {
        if (DEBUG) {
            Log.i("foo", "Received completions:");
            for (int i = 0; i < (completions != null ? completions.length : 0); i++) {
                Log.i("foo", "  #" + i + ": " + completions[i]);
            }
        }
        if (mCompletionOn) {
            offvalue = completions;
            if (completions == null) {
                clearSuggestions();
                return;
            }

            List<CharSequence> stringList = new ArrayList<CharSequence>();
            for (int i = 0; i < (completions != null ? completions.length : 0); i++) {
                CompletionInfo ci = completions[i];
                if (ci != null)
                    stringList.add(ci.getText());
            }
            setSuggestions(stringList, true, true, true);
            allspling = null;
            setCandidatesViewShown(true);
        }
    }

    private void setCandidatesViewShownInternal(boolean shown,
                                                boolean needsInputViewShown) {
        // TODO: Remove this if we support candidates with hard keyboard
        boolean visible = shown
                && onEvaluateInputViewShown()
                && mytypingword.getInputView() != null
                && isPredictionOn()
                && (needsInputViewShown
                ? mytypingword.getInputView().isShown()
                : true);
        if (visible) {
            if (lenearkeybord == null) {
                onCreateCandidatesView();
                setNextSuggestions();
            }
        } else {
            if (lenearkeybord != null) {
                removeCandidateViewContainer();
                commitTyped(getCurrentInputConnection(), true);
            }
        }
        super.setCandidatesViewShown(visible);
    }

    @Override
    public void onFinishCandidatesView(boolean finishingInput) {
        super.onFinishCandidatesView(finishingInput);
        if (lenearkeybord != null) {
            removeCandidateViewContainer();
        }
    }

    @Override
    public boolean onEvaluateInputViewShown() {
        boolean parent = super.onEvaluateInputViewShown();
        boolean wanted = letterstart || parent;
        return wanted;
    }

    @Override
    public void setCandidatesViewShown(boolean shown) {
        setCandidatesViewShownInternal(shown, true);
    }

    @Override
    public void onComputeInsets(Insets outInsets) {
        super.onComputeInsets(outInsets);
        if (!isFullscreenMode()) {
            outInsets.contentTopInsets = outInsets.visibleTopInsets;
        }
    }

    @Override
    public boolean onEvaluateFullscreenMode() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        float displayHeight = dm.heightPixels;
        float dimen = getResources().getDimension(
                R.dimen.max_height_for_fullscreen);
        if (displayHeight > dimen || layoutkey || isConnectbot()) {
            return false;
        } else {
            return super.onEvaluateFullscreenMode();
        }
    }

    public boolean isKeyboardVisible() {
        return (mytypingword != null
                && mytypingword.getInputView() != null
                && mytypingword.getInputView().isShown());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (event.getRepeatCount() == 0
                        && mytypingword.getInputView() != null) {
                    if (mytypingword.getInputView().handleBack()) {
                        return true;
                    } else if (latme != null) {
                        latme.close();
                        latme = null;
                    }
                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
            case KeyEvent.KEYCODE_DPAD_UP:
            case KeyEvent.KEYCODE_DPAD_LEFT:
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (latme != null) {
                    return true;
                }
                break;
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (!volumstring.equals("none") && isKeyboardVisible()) {
                    return true;
                }
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (!botemsound.equals("none") && isKeyboardVisible()) {
                    return true;
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
            case KeyEvent.KEYCODE_DPAD_UP:
            case KeyEvent.KEYCODE_DPAD_LEFT:
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (latme != null) {
                    return true;
                }
                My_Keybord_Img_Key inputView = mytypingword.getInputView();
                if (inputView != null && inputView.isShown()
                        && inputView.getShiftState() == Keyboard.SHIFT_ON) {
                    event = new KeyEvent(event.getDownTime(), event.getEventTime(),
                            event.getAction(), event.getKeyCode(), event
                            .getRepeatCount(), event.getDeviceId(), event
                            .getScanCode(), KeyEvent.META_SHIFT_LEFT_ON
                            | KeyEvent.META_SHIFT_ON);
                    InputConnection ic = getCurrentInputConnection();
                    if (ic != null)
                        ic.sendKeyEvent(event);
                    return true;
                }
                break;
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (!volumstring.equals("none") && isKeyboardVisible()) {
                    return doSwipeAction(volumstring);
                }
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (!botemsound.equals("none") && isKeyboardVisible()) {
                    return doSwipeAction(botemsound);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    private void reloadKeyboards() {
        mytypingword.setLanguageSwitcher(bhashaof);
        if (mytypingword.getInputView() != null
                && mytypingword.getKeyboardMode() != Typing_Switch.ZERO_VALUE) {
            mytypingword.setVoiceMode(soundeffect && soundvalue,
                    soundlocakl);
        }
        updateKeyboardOptions();
        mytypingword.makeKeyboards(true);
    }

    private void commitTyped(InputConnection inputConnection, boolean manual) {
        if (mPredicting) {
            mPredicting = false;
            if (mComposing.length() > 0) {
                if (inputConnection != null) {
                    inputConnection.commitText(mComposing, 1);
                }
                intsize = mComposing.length();
                if (manual) {
                    TextEntryState.manualTyped(mComposing);
                } else {
                    TextEntryState.acceptedTyped(mComposing);
                }
                addToDictionaries(mComposing,
                        Outo_Spiling_Keybord.FREQUENCY_FOR_TYPED);
            }
            updateSuggestions();
        }
    }

    private void postUpdateShiftKeyState() {
        // TODO(klausw): disabling, I have no idea what this is supposed to accomplish.
        // FIXME: why the delay?
        // TODO: Should remove this 300ms delay?
    }

    public void updateShiftKeyState(EditorInfo attr) {
        InputConnection ic = getCurrentInputConnection();
        if (ic != null && attr != null && mytypingword.isAlphabetMode()) {
            int oldState = getShiftState();
            boolean isShifted = modyfiykeytext.isChording();
            boolean isCapsLock = (oldState == Keyboard.SHIFT_CAPS_LOCKED || oldState == Keyboard.SHIFT_LOCKED);
            boolean isCaps = isCapsLock || getCursorCapsMode(ic, attr) != 0;
            int newState = Keyboard.SHIFT_OFF;
            if (isShifted) {
                newState = (kayadd == Keyboard.SHIFT_LOCKED) ? Keyboard.SHIFT_CAPS : Keyboard.SHIFT_ON;
            } else if (isCaps) {
                newState = isCapsLock ? getCapsOrShiftLockState() : Keyboard.SHIFT_CAPS;
            }
            mytypingword.setShiftState(newState);
        }
        if (ic != null) {
            int states =
                    KeyEvent.META_FUNCTION_ON
                            | KeyEvent.META_ALT_MASK
                            | KeyEvent.META_CTRL_MASK
                            | KeyEvent.META_META_MASK
                            | KeyEvent.META_SYM_ON;
            ic.clearMetaKeyStates(states);
        }
    }

    private int getShiftState() {
        if (mytypingword != null) {
            My_Keybord_Img_Key view = mytypingword.getInputView();
            if (view != null) {
                return view.getShiftState();
            }
        }
        return Keyboard.SHIFT_OFF;
    }

    private boolean isShiftCapsMode() {
        if (mytypingword != null) {
            My_Keybord_Img_Key view = mytypingword.getInputView();
            if (view != null) {
                return view.isShiftCaps();
            }
        }
        return false;
    }

    private int getCursorCapsMode(InputConnection ic, EditorInfo attr) {
        int caps = 0;
        EditorInfo ei = getCurrentInputEditorInfo();
        if (otoscreen && ei != null && ei.inputType != EditorInfo.TYPE_NULL) {
            caps = ic.getCursorCapsMode(attr.inputType);
        }
        return caps;
    }

    private void swapPunctuationAndSpace() {
        final InputConnection ic = getCurrentInputConnection();
        if (ic == null)
            return;
        CharSequence lastTwo = ic.getTextBeforeCursor(2, 0);
        if (lastTwo != null && lastTwo.length() == 2
                && lastTwo.charAt(0) == ASCII_SPACE
                && isSentenceSeparator(lastTwo.charAt(1))) {
            ic.beginBatchEdit();
            ic.deleteSurroundingText(2, 0);
            ic.commitText(lastTwo.charAt(1) + " ", 1);
            ic.endBatchEdit();
            updateShiftKeyState(getCurrentInputEditorInfo());
            bolenjagya = true;
        }
    }

    private void reswapPeriodAndSpace() {
        final InputConnection ic = getCurrentInputConnection();
        if (ic == null)
            return;
        CharSequence lastThree = ic.getTextBeforeCursor(3, 0);
        if (lastThree != null && lastThree.length() == 3
                && lastThree.charAt(0) == ASCII_PERIOD
                && lastThree.charAt(1) == ASCII_SPACE
                && lastThree.charAt(2) == ASCII_PERIOD) {
            ic.beginBatchEdit();
            ic.deleteSurroundingText(3, 0);
            ic.commitText(" ..", 1);
            ic.endBatchEdit();
            updateShiftKeyState(getCurrentInputEditorInfo());
        }
    }

    private void doubleSpace() {
        if (mCorrectionMode == My_Keyword_Inform_app.CORRECTION_NONE)
            return;
        final InputConnection ic = getCurrentInputConnection();
        if (ic == null)
            return;
        CharSequence lastThree = ic.getTextBeforeCursor(3, 0);
        if (lastThree != null && lastThree.length() == 3
                && Character.isLetterOrDigit(lastThree.charAt(0))
                && lastThree.charAt(1) == ASCII_SPACE
                && lastThree.charAt(2) == ASCII_SPACE) {
            ic.beginBatchEdit();
            ic.deleteSurroundingText(2, 0);
            ic.commitText(". ", 1);
            ic.endBatchEdit();
            updateShiftKeyState(getCurrentInputEditorInfo());
            bolenjagya = true;
        }
    }

    private void maybeRemovePreviousPeriod(CharSequence text) {
        final InputConnection ic = getCurrentInputConnection();
        if (ic == null || text.length() == 0)
            return;

        CharSequence lastOne = ic.getTextBeforeCursor(1, 0);
        if (lastOne != null && lastOne.length() == 1
                && lastOne.charAt(0) == ASCII_PERIOD
                && text.charAt(0) == ASCII_PERIOD) {
            ic.deleteSurroundingText(1, 0);
        }
    }

    private void removeTrailingSpace() {
        final InputConnection ic = getCurrentInputConnection();
        if (ic == null)
            return;

        CharSequence lastOne = ic.getTextBeforeCursor(1, 0);
        if (lastOne != null && lastOne.length() == 1
                && lastOne.charAt(0) == ASCII_SPACE) {
            ic.deleteSurroundingText(1, 0);
        }
    }

    public boolean addWordToDictionary(String word) {
        fileprov.addWord(word, 128);
        postUpdateSuggestions();
        return true;
    }

    private boolean isAlphabet(int code) {
        if (Character.isLetter(code)) {
            return true;
        } else {
            return false;
        }
    }

    private void showInputMethodPicker() {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .showInputMethodPicker();
    }

    private void onOptionKeyPressed() {
        if (!isShowingOptionDialog()) {
            showOptionsMenu();
        }
    }

    private void onOptionKeyLongPressed() {
        if (!isShowingOptionDialog()) {
            showInputMethodPicker();
        }
    }

    private boolean isShowingOptionDialog() {
        return alert != null && alert.isShowing();
    }

    private boolean isConnectbot() {
        EditorInfo ei = getCurrentInputEditorInfo();
        String pkg = ei.packageName;
        if (ei == null || pkg == null) return false;
        return ((pkg.equalsIgnoreCase("org.connectbot")
                || pkg.equalsIgnoreCase("org.woltage.irssiconnectbot")
                || pkg.equalsIgnoreCase("com.pslib.connectbot")
                || pkg.equalsIgnoreCase("sk.vx.connectbot")
        ) && ei.inputType == 0); // FIXME
    }

    private int getMetaState(boolean shifted) {
        int meta = 0;
        if (shifted) meta |= KeyEvent.META_SHIFT_ON | KeyEvent.META_SHIFT_LEFT_ON;
        if (keycontrol) meta |= KeyEvent.META_CTRL_ON | KeyEvent.META_CTRL_LEFT_ON;
        if (keyalter) meta |= KeyEvent.META_ALT_ON | KeyEvent.META_ALT_LEFT_ON;
        if (keymaeta) meta |= KeyEvent.META_META_ON | KeyEvent.META_META_LEFT_ON;
        return meta;
    }

    private void sendKeyDown(InputConnection ic, int key, int meta) {
        long now = System.currentTimeMillis();
        if (ic != null) ic.sendKeyEvent(new KeyEvent(
                now, now, KeyEvent.ACTION_DOWN, key, 0, meta));
    }

    private void sendKeyUp(InputConnection ic, int key, int meta) {
        long now = System.currentTimeMillis();
        if (ic != null) ic.sendKeyEvent(new KeyEvent(
                now, now, KeyEvent.ACTION_UP, key, 0, meta));
    }

    private void sendModifiedKeyDownUp(int key, boolean shifted) {
        InputConnection ic = getCurrentInputConnection();
        int meta = getMetaState(shifted);
        sendModifierKeysDown(shifted);
        sendKeyDown(ic, key, meta);
        sendKeyUp(ic, key, meta);
        sendModifierKeysUp(shifted);
    }

    private boolean isShiftMod() {
        if (modyfiykeytext.isChording()) return true;
        if (mytypingword != null) {
            My_Keybord_Img_Key kb = mytypingword.getInputView();
            if (kb != null) return kb.isShiftAll();
        }
        return false;
    }

    private boolean delayChordingCtrlModifier() {
        return service.ymglobezero == 0;
    }

    private boolean delayChordingAltModifier() {
        return service.interchavi == 0;
    }

    private boolean delayChordingMetaModifier() {
        return service.mtadta == 0;
    }

    private void sendModifiedKeyDownUp(int key) {
        sendModifiedKeyDownUp(key, isShiftMod());
    }

    private void sendShiftKey(InputConnection ic, boolean isDown) {
        int key = KeyEvent.KEYCODE_SHIFT_LEFT;
        int meta = KeyEvent.META_SHIFT_ON | KeyEvent.META_SHIFT_LEFT_ON;
        if (isDown) {
            sendKeyDown(ic, key, meta);
        } else {
            sendKeyUp(ic, key, meta);
        }
    }

    private void sendCtrlKey(InputConnection ic, boolean isDown, boolean chording) {
        if (chording && delayChordingCtrlModifier()) return;

        int key = service.ymglobezero;
        if (key == 0) key = KeyEvent.KEYCODE_CTRL_LEFT;
        int meta = KeyEvent.META_CTRL_ON | KeyEvent.META_CTRL_LEFT_ON;
        if (isDown) {
            sendKeyDown(ic, key, meta);
        } else {
            sendKeyUp(ic, key, meta);
        }
    }

    private void sendAltKey(InputConnection ic, boolean isDown, boolean chording) {
        if (chording && delayChordingAltModifier()) return;

        int key = service.interchavi;
        if (key == 0) key = KeyEvent.KEYCODE_ALT_LEFT;
        int meta = KeyEvent.META_ALT_ON | KeyEvent.META_ALT_LEFT_ON;
        if (isDown) {
            sendKeyDown(ic, key, meta);
        } else {
            sendKeyUp(ic, key, meta);
        }
    }

    private void sendMetaKey(InputConnection ic, boolean isDown, boolean chording) {
        if (chording && delayChordingMetaModifier()) return;

        int key = service.mtadta;
        if (key == 0) key = KeyEvent.KEYCODE_META_LEFT;
        int meta = KeyEvent.META_META_ON | KeyEvent.META_META_LEFT_ON;
        if (isDown) {
            sendKeyDown(ic, key, meta);
        } else {
            sendKeyUp(ic, key, meta);
        }
    }

    private void sendModifierKeysDown(boolean shifted) {
        InputConnection ic = getCurrentInputConnection();
        if (shifted) {
            sendShiftKey(ic, true);
        }
        if (keycontrol && (!modifyimg.isChording() || delayChordingCtrlModifier())) {
            sendCtrlKey(ic, true, false);
        }
        if (keyalter && (!modyfiy.isChording() || delayChordingAltModifier())) {
            sendAltKey(ic, true, false);
        }
        if (keymaeta && (!modyfiyname.isChording() || delayChordingMetaModifier())) {
            sendMetaKey(ic, true, false);
        }
    }

    private void handleModifierKeysUp(boolean shifted, boolean sendKey) {
        InputConnection ic = getCurrentInputConnection();
        if (keymaeta && (!modyfiyname.isChording() || delayChordingMetaModifier())) {
            if (sendKey) sendMetaKey(ic, false, false);
            if (!modyfiyname.isChording()) setModMeta(false);
        }
        if (keyalter && (!modyfiy.isChording() || delayChordingAltModifier())) {
            if (sendKey) sendAltKey(ic, false, false);
            if (!modyfiy.isChording()) setModAlt(false);
        }
        if (keycontrol && (!modifyimg.isChording() || delayChordingCtrlModifier())) {
            if (sendKey) sendCtrlKey(ic, false, false);
            if (!modifyimg.isChording()) setModCtrl(false);
        }
        if (shifted) {
            if (sendKey) sendShiftKey(ic, false);
            int shiftState = getShiftState();
            if (!(modyfiykeytext.isChording() || shiftState == Keyboard.SHIFT_LOCKED)) {
                resetShift();
            }
        }
    }

    private void sendModifierKeysUp(boolean shifted) {
        handleModifierKeysUp(shifted, true);
    }

    private void sendSpecialKey(int code) {
        if (!isConnectbot()) {
            commitTyped(getCurrentInputConnection(), true);
            sendModifiedKeyDownUp(code);
            return;
        }

        // TODO(klausw): properly support xterm sequences for Ctrl/Alt modifiers?
        if (ESC_SEQUENCES == null) {
            ESC_SEQUENCES = new HashMap<Integer, String>();
            CTRL_SEQUENCES = new HashMap<Integer, Integer>();

            ESC_SEQUENCES.put(-My_Keybord_Img_Key.KEYCODE_HOME, "[1~");
            ESC_SEQUENCES.put(-My_Keybord_Img_Key.KEYCODE_END, "[4~");
            ESC_SEQUENCES.put(-My_Keybord_Img_Key.KEYCODE_PAGE_UP, "[5~");
            ESC_SEQUENCES.put(-My_Keybord_Img_Key.KEYCODE_PAGE_DOWN, "[6~");
            ESC_SEQUENCES.put(-My_Keybord_Img_Key.KEYCODE_FKEY_F1, "OP");
            ESC_SEQUENCES.put(-My_Keybord_Img_Key.KEYCODE_FKEY_F2, "OQ");
            ESC_SEQUENCES.put(-My_Keybord_Img_Key.KEYCODE_FKEY_F3, "OR");
            ESC_SEQUENCES.put(-My_Keybord_Img_Key.KEYCODE_FKEY_F4, "OS");
            ESC_SEQUENCES.put(-My_Keybord_Img_Key.KEYCODE_FKEY_F5, "[15~");
            ESC_SEQUENCES.put(-My_Keybord_Img_Key.KEYCODE_FKEY_F6, "[17~");
            ESC_SEQUENCES.put(-My_Keybord_Img_Key.KEYCODE_FKEY_F7, "[18~");
            ESC_SEQUENCES.put(-My_Keybord_Img_Key.KEYCODE_FKEY_F8, "[19~");
            ESC_SEQUENCES.put(-My_Keybord_Img_Key.KEYCODE_FKEY_F9, "[20~");
            ESC_SEQUENCES.put(-My_Keybord_Img_Key.KEYCODE_FKEY_F10, "[21~");
            ESC_SEQUENCES.put(-My_Keybord_Img_Key.KEYCODE_FKEY_F11, "[23~");
            ESC_SEQUENCES.put(-My_Keybord_Img_Key.KEYCODE_FKEY_F12, "[24~");
            ESC_SEQUENCES.put(-My_Keybord_Img_Key.KEYCODE_FORWARD_DEL, "[3~");
            ESC_SEQUENCES.put(-My_Keybord_Img_Key.KEYCODE_INSERT, "[2~");
            CTRL_SEQUENCES.put(-My_Keybord_Img_Key.KEYCODE_FKEY_F1, KeyEvent.KEYCODE_1);
            CTRL_SEQUENCES.put(-My_Keybord_Img_Key.KEYCODE_FKEY_F2, KeyEvent.KEYCODE_2);
            CTRL_SEQUENCES.put(-My_Keybord_Img_Key.KEYCODE_FKEY_F3, KeyEvent.KEYCODE_3);
            CTRL_SEQUENCES.put(-My_Keybord_Img_Key.KEYCODE_FKEY_F4, KeyEvent.KEYCODE_4);
            CTRL_SEQUENCES.put(-My_Keybord_Img_Key.KEYCODE_FKEY_F5, KeyEvent.KEYCODE_5);
            CTRL_SEQUENCES.put(-My_Keybord_Img_Key.KEYCODE_FKEY_F6, KeyEvent.KEYCODE_6);
            CTRL_SEQUENCES.put(-My_Keybord_Img_Key.KEYCODE_FKEY_F7, KeyEvent.KEYCODE_7);
            CTRL_SEQUENCES.put(-My_Keybord_Img_Key.KEYCODE_FKEY_F8, KeyEvent.KEYCODE_8);
            CTRL_SEQUENCES.put(-My_Keybord_Img_Key.KEYCODE_FKEY_F9, KeyEvent.KEYCODE_9);
            CTRL_SEQUENCES.put(-My_Keybord_Img_Key.KEYCODE_FKEY_F10, KeyEvent.KEYCODE_0);
        }
        InputConnection ic = getCurrentInputConnection();
        Integer ctrlseq = null;
        if (sploingfile) {
            ctrlseq = CTRL_SEQUENCES.get(code);
        }
        String seq = ESC_SEQUENCES.get(code);

        if (ctrlseq != null) {
            if (keyalter) {
                ic.commitText(Character.toString((char) 27), 1);
            }
            ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,
                    KeyEvent.KEYCODE_DPAD_CENTER));
            ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP,
                    KeyEvent.KEYCODE_DPAD_CENTER));
            ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,
                    ctrlseq));
            ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP,
                    ctrlseq));
        } else if (seq != null) {
            if (keyalter) {
                ic.commitText(Character.toString((char) 27), 1);
            }
            ic.commitText(Character.toString((char) 27), 1);
            ic.commitText(seq, 1);
        } else {
            sendDownUpKeyEvents(code);
        }
        handleModifierKeysUp(false, false);
    }

    private final static int asciiToKeyCode[] = new int[127];
    private final static int KF_MASK = 0xffff;
    private final static int KF_SHIFTABLE = 0x10000;
    private final static int KF_UPPER = 0x20000;
    private final static int KF_LETTER = 0x40000;

    {
        asciiToKeyCode['\n'] = KeyEvent.KEYCODE_ENTER | KF_SHIFTABLE;

        asciiToKeyCode[' '] = KeyEvent.KEYCODE_SPACE | KF_SHIFTABLE;
        asciiToKeyCode['#'] = KeyEvent.KEYCODE_POUND;
        asciiToKeyCode['\''] = KeyEvent.KEYCODE_APOSTROPHE;
        asciiToKeyCode['*'] = KeyEvent.KEYCODE_STAR;
        asciiToKeyCode['+'] = KeyEvent.KEYCODE_PLUS;
        asciiToKeyCode[','] = KeyEvent.KEYCODE_COMMA;
        asciiToKeyCode['-'] = KeyEvent.KEYCODE_MINUS;
        asciiToKeyCode['.'] = KeyEvent.KEYCODE_PERIOD;
        asciiToKeyCode['/'] = KeyEvent.KEYCODE_SLASH;
        asciiToKeyCode[';'] = KeyEvent.KEYCODE_SEMICOLON;
        asciiToKeyCode['='] = KeyEvent.KEYCODE_EQUALS;
        asciiToKeyCode['@'] = KeyEvent.KEYCODE_AT;
        asciiToKeyCode['['] = KeyEvent.KEYCODE_LEFT_BRACKET;
        asciiToKeyCode['\\'] = KeyEvent.KEYCODE_BACKSLASH;
        asciiToKeyCode[']'] = KeyEvent.KEYCODE_RIGHT_BRACKET;
        asciiToKeyCode['`'] = KeyEvent.KEYCODE_GRAVE;


        for (int i = 0; i <= 25; ++i) {
            asciiToKeyCode['a' + i] = KeyEvent.KEYCODE_A + i | KF_LETTER;
            asciiToKeyCode['A' + i] = KeyEvent.KEYCODE_A + i | KF_UPPER | KF_LETTER;
        }

        for (int i = 0; i <= 9; ++i) {
            asciiToKeyCode['0' + i] = KeyEvent.KEYCODE_0 + i;
        }
    }

    public void sendModifiableKeyChar(char ch) {
        boolean modShift = isShiftMod();
        if ((modShift || keycontrol || keyalter || keymaeta) && ch > 0 && ch < 127) {
            InputConnection ic = getCurrentInputConnection();
            if (isConnectbot()) {
                if (keyalter) {
                    ic.commitText(Character.toString((char) 27), 1);
                }
                if (keycontrol) {
                    int code = ch & 31;
                    if (code == 9) {
                        sendTab();
                    } else {
                        ic.commitText(Character.toString((char) code), 1);
                    }
                } else {
                    ic.commitText(Character.toString(ch), 1);
                }
                handleModifierKeysUp(false, false);
                return;
            }


            int combinedCode = asciiToKeyCode[ch];
            if (combinedCode > 0) {
                int code = combinedCode & KF_MASK;
                boolean shiftable = (combinedCode & KF_SHIFTABLE) > 0;
                boolean upper = (combinedCode & KF_UPPER) > 0;
                boolean letter = (combinedCode & KF_LETTER) > 0;
                boolean shifted = modShift && (upper || shiftable);
                if (letter && !keycontrol && !keyalter && !keymaeta) {
                    ic.commitText(Character.toString(ch), 1);
                    handleModifierKeysUp(false, false);
                } else {
                    sendModifiedKeyDownUp(code, shifted);
                }
                return;
            }
        }

        if (ch >= '0' && ch <= '9') {
            InputConnection ic = getCurrentInputConnection();
            ic.clearMetaKeyStates(KeyEvent.META_SHIFT_ON | KeyEvent.META_ALT_ON | KeyEvent.META_SYM_ON);
        }

        sendKeyChar(ch);
    }

    private void sendTab() {
        InputConnection ic = getCurrentInputConnection();
        boolean tabHack = isConnectbot() && sploingfile;

        // FIXME: tab and ^I don't work in connectbot, hackish workaround
        if (tabHack) {
            if (keyalter) {
                ic.commitText(Character.toString((char) 27), 1);
            }
            ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,
                    KeyEvent.KEYCODE_DPAD_CENTER));
            ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP,
                    KeyEvent.KEYCODE_DPAD_CENTER));
            ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,
                    KeyEvent.KEYCODE_I));
            ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP,
                    KeyEvent.KEYCODE_I));
        } else {
            sendModifiedKeyDownUp(KeyEvent.KEYCODE_TAB);
        }
    }

    private void sendEscape() {
        if (isConnectbot()) {
            sendKeyChar((char) 27);
        } else {
            sendModifiedKeyDownUp(111);
        }
    }

    private boolean processMultiKey(int primaryCode) {
        if (keybordebase.composeBuffer.length() > 0) {
            keybordebase.execute(primaryCode);
            keybordebase.clear();
            return true;
        }
        if (letervalue) {
            letervalue = symbole.execute(primaryCode);
            return true;
        }
        return false;
    }


    public void onKey(int primaryCode, int[] keyCodes, int x, int y) {
        long when = SystemClock.uptimeMillis();
        if (primaryCode != Keyboard.KEYCODE_DELETE
                || when > endchavi + QUICK_PRESS) {
            removenum = 0;
        }
        endchavi = when;
        final boolean distinctMultiTouch = mytypingword
                .hasDistinctMultitouch();
        switch (primaryCode) {
            case Keyboard.KEYCODE_DELETE:
                if (processMultiKey(primaryCode)) {
                    break;
                }
                handleBackspace();
                removenum++;
                My_Keybord_Imglong.logOnDelete();
                break;
            case Keyboard.KEYCODE_SHIFT:
                if (!distinctMultiTouch)
                    handleShift();
                break;
            case Keyboard.KEYCODE_MODE_CHANGE:
                if (!distinctMultiTouch)
                    changeKeyboardMode();
                break;
            case My_Keybord_Img_Key.KEYCODE_CTRL_LEFT:
                if (!distinctMultiTouch)
                    setModCtrl(!keycontrol);
                break;
            case My_Keybord_Img_Key.KEYCODE_ALT_LEFT:
                if (!distinctMultiTouch)
                    setModAlt(!keyalter);
                break;
            case My_Keybord_Img_Key.KEYCODE_META_LEFT:
                if (!distinctMultiTouch)
                    setModMeta(!keymaeta);
                break;
            case My_Keybord_Img_Key.KEYCODE_FN:
                if (!distinctMultiTouch)
                    setModFn(!keyfunction);
                break;
            case Keyboard.KEYCODE_CANCEL:
                if (!isShowingOptionDialog()) {
                    handleClose();
                }
                break;
            case My_Keybord_Img_Key.KEYCODE_OPTIONS:
                onOptionKeyPressed();
                break;
            case My_Keybord_Img_Key.KEYCODE_OPTIONS_LONGPRESS:
                onOptionKeyLongPressed();
                break;
            case My_Keybord_Img_Key.KEYCODE_COMPOSE:
                letervalue = !letervalue;
                symbole.clear();
                break;
            case My_Keybord_Img_Key.KEYCODE_NEXT_LANGUAGE:
                toggleLanguage(false, true);
                break;
            case My_Keybord_Img_Key.KEYCODE_PREV_LANGUAGE:
                toggleLanguage(false, false);
                break;
            case My_Keybord_Img_Key.KEYCODE_VOICE:
                if (soundkeyeffect.isInstalled()) {
                    soundkeyeffect.startVoiceRecognition();
                }
                break;
            case 9:
                if (processMultiKey(primaryCode)) {
                    break;
                }
                sendTab();
                break;
            case My_Keybord_Img_Key.KEYCODE_ESCAPE:
                if (processMultiKey(primaryCode)) {
                    break;
                }
                sendEscape();
                break;
            case My_Keybord_Img_Key.KEYCODE_DPAD_UP:
            case My_Keybord_Img_Key.KEYCODE_DPAD_DOWN:
            case My_Keybord_Img_Key.KEYCODE_DPAD_LEFT:
            case My_Keybord_Img_Key.KEYCODE_DPAD_RIGHT:
            case My_Keybord_Img_Key.KEYCODE_DPAD_CENTER:
            case My_Keybord_Img_Key.KEYCODE_HOME:
            case My_Keybord_Img_Key.KEYCODE_END:
            case My_Keybord_Img_Key.KEYCODE_PAGE_UP:
            case My_Keybord_Img_Key.KEYCODE_PAGE_DOWN:
            case My_Keybord_Img_Key.KEYCODE_FKEY_F1:
            case My_Keybord_Img_Key.KEYCODE_FKEY_F2:
            case My_Keybord_Img_Key.KEYCODE_FKEY_F3:
            case My_Keybord_Img_Key.KEYCODE_FKEY_F4:
            case My_Keybord_Img_Key.KEYCODE_FKEY_F5:
            case My_Keybord_Img_Key.KEYCODE_FKEY_F6:
            case My_Keybord_Img_Key.KEYCODE_FKEY_F7:
            case My_Keybord_Img_Key.KEYCODE_FKEY_F8:
            case My_Keybord_Img_Key.KEYCODE_FKEY_F9:
            case My_Keybord_Img_Key.KEYCODE_FKEY_F10:
            case My_Keybord_Img_Key.KEYCODE_FKEY_F11:
            case My_Keybord_Img_Key.KEYCODE_FKEY_F12:
            case My_Keybord_Img_Key.KEYCODE_FORWARD_DEL:
            case My_Keybord_Img_Key.KEYCODE_INSERT:
            case My_Keybord_Img_Key.KEYCODE_SYSRQ:
            case My_Keybord_Img_Key.KEYCODE_BREAK:
            case My_Keybord_Img_Key.KEYCODE_NUM_LOCK:
            case My_Keybord_Img_Key.KEYCODE_SCROLL_LOCK:
                if (processMultiKey(primaryCode)) {
                    break;
                }
                sendSpecialKey(-primaryCode);
                break;
            default:
                if (!letervalue && valuescreen && Character.getType(primaryCode) == Character.NON_SPACING_MARK) {
                    if (!keybordebase.execute(primaryCode)) {
                        break;
                    }
                    updateShiftKeyState(getCurrentInputEditorInfo());
                    break;
                }
                if (processMultiKey(primaryCode)) {
                    break;
                }
                if (primaryCode != ASCII_ENTER) {
                    bolenjagya = false;
                }
                My_Keybord_Img_Untils.RingCharBuffer.getInstance().push((char) primaryCode, x, y);
                My_Keybord_Imglong.logOnInputChar();
                if (isWordSeparator(primaryCode)) {
                    handleSeparator(primaryCode);
                } else {
                    handleCharacter(primaryCode, keyCodes);
                }
                popuchareter = null;
        }
        mytypingword.onKey(primaryCode);
        addlater = null;
        // FIXME
    }

    public void onText(CharSequence text) {
        // FIXME
        InputConnection ic = getCurrentInputConnection();
        if (ic == null)
            return;
        if (mPredicting && text.length() == 1) {
            int c = text.charAt(0);
            if (!isWordSeparator(c)) {
                int[] codes = {c};
                handleCharacter(c, codes);
                return;
            }
        }
        abortCorrection(false);
        ic.beginBatchEdit();
        if (mPredicting) {
            commitTyped(ic, true);
        }
        maybeRemovePreviousPeriod(text);
        ic.commitText(text, 1);
        ic.endBatchEdit();
        updateShiftKeyState(getCurrentInputEditorInfo());
        mytypingword.onKey(0);
        popuchareter = null;
        bolenjagya = false;
        addlater = text;
    }

    public void onCancel() {
        mytypingword.onCancelInput();
    }

    private void handleBackspace() {
        boolean deleteChar = false;
        InputConnection ic = getCurrentInputConnection();
        if (ic == null)
            return;

        ic.beginBatchEdit();

        if (mPredicting) {
            final int length = mComposing.length();
            if (length > 0) {
                mComposing.delete(length - 1, length);
                compress.deleteLast();
                ic.setComposingText(mComposing, 1);
                if (mComposing.length() == 0) {
                    mPredicting = false;
                }
                postUpdateSuggestions();
            } else {
                ic.deleteSurroundingText(1, 0);
            }
        } else {
            deleteChar = true;
        }
        postUpdateShiftKeyState();
        TextEntryState.backspace();
        if (TextEntryState.getState() == TextEntryState.State.UNDO_COMMIT) {
            revertLastWord(deleteChar);
            ic.endBatchEdit();
            return;
        } else if (addlater != null
                && sameAsTextBeforeCursor(ic, addlater)) {
            ic.deleteSurroundingText(addlater.length(), 0);
        } else if (deleteChar) {
            if (mCandidateView != null
                    && mCandidateView.dismissAddToDictionaryHint()) {
                revertLastWord(deleteChar);
            } else {
                sendDownUpKeyEvents(KeyEvent.KEYCODE_DEL);
                if (removenum > DELETE_ACCELERATE_AT) {
                    sendDownUpKeyEvents(KeyEvent.KEYCODE_DEL);
                }
            }
        }
        popuchareter = null;
        ic.endBatchEdit();
    }

    private void setModCtrl(boolean val) {
        mytypingword.setCtrlIndicator(val);
        keycontrol = val;
    }

    private void setModAlt(boolean val) {
        mytypingword.setAltIndicator(val);
        keyalter = val;
    }

    private void setModMeta(boolean val) {
        mytypingword.setMetaIndicator(val);
        keymaeta = val;
    }

    private void setModFn(boolean val) {
        keyfunction = val;
        mytypingword.setFn(val);
        mytypingword.setCtrlIndicator(keycontrol);
        mytypingword.setAltIndicator(keyalter);
        mytypingword.setMetaIndicator(keymaeta);
    }

    private void startMultitouchShift() {
        int newState = Keyboard.SHIFT_ON;
        if (mytypingword.isAlphabetMode()) {
            kayadd = getShiftState();
            if (kayadd == Keyboard.SHIFT_LOCKED) newState = Keyboard.SHIFT_CAPS;
        }
        handleShiftInternal(true, newState);
    }

    private void commitMultitouchShift() {
        if (mytypingword.isAlphabetMode()) {
            int newState = nextShiftState(kayadd, true);
            handleShiftInternal(true, newState);
        } else {
        }
    }

    private void resetMultitouchShift() {
        int newState = Keyboard.SHIFT_OFF;
        if (kayadd == Keyboard.SHIFT_CAPS_LOCKED || kayadd == Keyboard.SHIFT_LOCKED) {
            newState = kayadd;
        }
        handleShiftInternal(true, newState);
    }

    private void resetShift() {
        handleShiftInternal(true, Keyboard.SHIFT_OFF);
    }

    private void handleShift() {
        handleShiftInternal(false, -1);
    }

    private static int getCapsOrShiftLockState() {
        return service.selectedkeybord ? Keyboard.SHIFT_CAPS_LOCKED : Keyboard.SHIFT_LOCKED;
    }

    private static int nextShiftState(int prevState, boolean allowCapsLock) {
        if (allowCapsLock) {
            if (prevState == Keyboard.SHIFT_OFF) {
                return Keyboard.SHIFT_ON;
            } else if (prevState == Keyboard.SHIFT_ON) {
                return getCapsOrShiftLockState();
            } else {
                return Keyboard.SHIFT_OFF;
            }
        } else {
            if (prevState == Keyboard.SHIFT_OFF) {
                return Keyboard.SHIFT_ON;
            } else {
                return Keyboard.SHIFT_OFF;
            }
        }
    }

    private void handleShiftInternal(boolean forceState, int newState) {
        mHandler.removeMessages(MSG_UPDATE_SHIFT_STATE);
        Typing_Switch switcher = mytypingword;
        if (switcher.isAlphabetMode()) {
            if (forceState) {
                switcher.setShiftState(newState);
            } else {
                switcher.setShiftState(nextShiftState(getShiftState(), true));
            }
        } else {
            switcher.toggleShift();
        }
    }

    private void abortCorrection(boolean force) {
        if (force || TextEntryState.isCorrecting()) {
            getCurrentInputConnection().finishComposingText();
            clearSuggestions();
        }
    }

    private void handleCharacter(int primaryCode, int[] keyCodes) {
        if (keybordon == keybordbottem
                && TextEntryState.isCorrecting()) {
            abortCorrection(false);
        }

        if (isAlphabet(primaryCode) && isPredictionOn()
                && !keycontrol && !keyalter && !keymaeta
                && !isCursorTouchingWord()) {
            if (!mPredicting) {
                mPredicting = true;
                mComposing.setLength(0);
                saveWordInHistory(allspling);
                compress.reset();
            }
        }

        if (keycontrol || keyalter || keymaeta) {
            commitTyped(getCurrentInputConnection(), true);
        }

        if (mPredicting) {
            if (isShiftCapsMode()
                    && mytypingword.isAlphabetMode()
                    && mComposing.length() == 0) {
                compress.setFirstCharCapitalized(true);
            }
            mComposing.append((char) primaryCode);
            compress.add(primaryCode, keyCodes);
            InputConnection ic = getCurrentInputConnection();
            if (ic != null) {
                if (compress.size() == 1) {
                    compress.setAutoCapitalized(getCursorCapsMode(ic,
                            getCurrentInputEditorInfo()) != 0);
                }
                ic.setComposingText(mComposing, 1);
            }
            postUpdateSuggestions();
        } else {
            sendModifiableKeyChar((char) primaryCode);
        }
        updateShiftKeyState(getCurrentInputEditorInfo());
        if (LatinIME.PERF_DEBUG)
            measureCps();
        TextEntryState.typedCharacter((char) primaryCode,
                isWordSeparator(primaryCode));
    }

    private void handleSeparator(int primaryCode) {

        if (mCandidateView != null
                && mCandidateView.dismissAddToDictionaryHint()) {
            postUpdateSuggestions();
        }

        boolean pickedDefault = false;
        InputConnection ic = getCurrentInputConnection();
        if (ic != null) {
            ic.beginBatchEdit();
            abortCorrection(false);
        }
        if (mPredicting) {
            if (mAutoCorrectOn
                    && primaryCode != '\''
                    && (popuchareter == null
                    || popuchareter.length() == 0
                    || popuchareter.charAt(0) != primaryCode)) {
                pickedDefault = pickDefaultSuggestion();
                if (primaryCode == ASCII_SPACE) {
                    if (ototextletter) {
                        bolenjagya = true;
                    } else {
                        TextEntryState.manualTyped("");
                    }
                }
            } else {
                commitTyped(ic, true);
            }
        }
        if (bolenjagya && primaryCode == ASCII_ENTER) {
            removeTrailingSpace();
            bolenjagya = false;
        }
        sendModifiableKeyChar((char) primaryCode);

        if (TextEntryState.getState() == TextEntryState.State.PUNCTUATION_AFTER_ACCEPTED
                && primaryCode == ASCII_PERIOD) {
            reswapPeriodAndSpace();
        }

        TextEntryState.typedCharacter((char) primaryCode, true);
        if (TextEntryState.getState() == TextEntryState.State.PUNCTUATION_AFTER_ACCEPTED
                && primaryCode != ASCII_ENTER) {
            swapPunctuationAndSpace();
        } else if (isPredictionOn() && primaryCode == ASCII_SPACE) {
            doubleSpace();
        }
        if (pickedDefault) {
            TextEntryState.backToAcceptedDefault(compress.getTypedWord());
        }
        updateShiftKeyState(getCurrentInputEditorInfo());
        if (ic != null) {
            ic.endBatchEdit();
        }
    }

    private void handleClose() {
        commitTyped(getCurrentInputConnection(), true);
        requestHideSelf(0);
        if (mytypingword != null) {
            My_Keybord_Img_Key inputView = mytypingword.getInputView();
            if (inputView != null) {
                inputView.closing();
            }
        }
        TextEntryState.endSession();
    }

    private void saveWordInHistory(CharSequence result) {
        if (compress.size() <= 1) {
            compress.reset();
            return;
        }
        if (TextUtils.isEmpty(result)) {
            return;
        }

        final String resultCopy = result.toString();
        TypedWordAlternatives entry = new TypedWordAlternatives(resultCopy,
                new Keyword_Set_Compres(compress));
        splinglist.add(entry);
    }

    private void postUpdateSuggestions() {
        mHandler.removeMessages(MSG_UPDATE_SUGGESTIONS);
        mHandler.sendMessageDelayed(mHandler
                .obtainMessage(MSG_UPDATE_SUGGESTIONS), 100);
    }

    private void postUpdateOldSuggestions() {
        mHandler.removeMessages(MSG_UPDATE_OLD_SUGGESTIONS);
        mHandler.sendMessageDelayed(mHandler
                .obtainMessage(MSG_UPDATE_OLD_SUGGESTIONS), 300);
    }

    private boolean isPredictionOn() {
        return mPredictionOnForMode && isPredictionWanted();
    }

    private boolean isPredictionWanted() {
        return (review || leterrteviewstart) && !suggestionsDisabled();
    }

    private boolean isCandidateStripVisible() {
        return isPredictionOn();
    }

    private void switchToKeyboardView() {
        mHandler.post(new Runnable() {
            public void run() {
                My_Keybord_Img_Key view = mytypingword.getInputView();
                if (view != null) {
                    ViewParent p = view.getParent();
                    if (p != null && p instanceof ViewGroup) {
                        ((ViewGroup) p).removeView(view);
                    }
                    setInputView(mytypingword.getInputView());
                }
                setCandidatesViewShown(true);
                updateInputViewShown();
                postUpdateSuggestions();
            }
        });
    }

    private void clearSuggestions() {
        setSuggestions(null, false, false, false);
    }

    private void setSuggestions(List<CharSequence> suggestions,
                                boolean completions, boolean typedWordValid,
                                boolean haveMinimalSuggestion) {

        if (bolenspling) {
            setCandidatesViewShown(true);
            bolenspling = false;
        }

        if (mCandidateView != null) {
            mCandidateView.setSuggestions(suggestions, completions,
                    typedWordValid, haveMinimalSuggestion);
        }
    }

    private void updateSuggestions() {
        My_Keybord_Img_Key inputView = mytypingword.getInputView();
        ((LatinMy_Keybord) inputView.getKeyboard()).setPreferredLetters(null);

        if ((mSuggest == null || !isPredictionOn())) {
            return;
        }

        if (!mPredicting) {
            setNextSuggestions();
            return;
        }
        showSuggestions(compress);
    }

    private List<CharSequence> getTypedSuggestions(Keyword_Set_Compres word) {
        List<CharSequence> stringList = mSuggest.getSuggestions(
                mytypingword.getInputView(), word, false, null);
        return stringList;
    }

    private void showCorrections(WordAlternatives alternatives) {
        List<CharSequence> stringList = alternatives.getAlternatives();
        ((LatinMy_Keybord) mytypingword.getInputView().getKeyboard())
                .setPreferredLetters(null);
        showSuggestions(stringList, alternatives.getOriginalWord(), false,
                false);
    }

    private void showSuggestions(Keyword_Set_Compres word) {
        // TODO Maybe need better way of retrieving previous spling
        CharSequence prevWord = Enter_Img_Keybord.getPreviousWord(
                getCurrentInputConnection(), splingsound);
        List<CharSequence> stringList = mSuggest.getSuggestions(
                mytypingword.getInputView(), word, false, prevWord);

        int[] nextLettersFrequencies = mSuggest.getNextLettersFrequencies();

        ((LatinMy_Keybord) mytypingword.getInputView().getKeyboard())
                .setPreferredLetters(nextLettersFrequencies);

        boolean correctionAvailable = !otocrunt
                && mSuggest.hasMinimalCorrection();
        CharSequence typedWord = word.getTypedWord();
        boolean typedWordValid = mSuggest.isValidWord(typedWord)
                || (preferCapitalization() && mSuggest.isValidWord(typedWord
                .toString().toLowerCase()));
        if (mCorrectionMode == My_Keyword_Inform_app.CORRECTION_FULL
                || mCorrectionMode == My_Keyword_Inform_app.CORRECTION_FULL_BIGRAM) {
            correctionAvailable |= typedWordValid;
        }
        correctionAvailable &= !word.isMostlyCaps();
        correctionAvailable &= !TextEntryState.isCorrecting();

        showSuggestions(stringList, typedWord, typedWordValid,
                correctionAvailable);
    }

    private void showSuggestions(List<CharSequence> stringList,
                                 CharSequence typedWord, boolean typedWordValid,
                                 boolean correctionAvailable) {
        setSuggestions(stringList, false, typedWordValid, correctionAvailable);
        if (stringList.size() > 0) {
            if (correctionAvailable && !typedWordValid && stringList.size() > 1) {
                allspling = stringList.get(1);
            } else {
                allspling = typedWord;
            }
        } else {
            allspling = null;
        }
        setCandidatesViewShown(isCandidateStripVisible() || mCompletionOn);
    }

    private boolean pickDefaultSuggestion() {
        if (mHandler.hasMessages(MSG_UPDATE_SUGGESTIONS)) {
            mHandler.removeMessages(MSG_UPDATE_SUGGESTIONS);
            updateSuggestions();
        }
        if (allspling != null && allspling.length() > 0) {
            TextEntryState.acceptedDefault(compress.getTypedWord(), allspling);
            boolenright = true;
            pickSuggestion(allspling, false);
            addToDictionaries(allspling, Outo_Spiling_Keybord.FREQUENCY_FOR_TYPED);
            return true;

        }
        return false;
    }

    public void pickSuggestionManually(int index, CharSequence suggestion) {
        List<CharSequence> suggestions = mCandidateView.getSuggestions();

        final boolean correcting = TextEntryState.isCorrecting();
        InputConnection ic = getCurrentInputConnection();
        if (ic != null) {
            ic.beginBatchEdit();
        }
        if (mCompletionOn && offvalue != null && index >= 0
                && index < offvalue.length) {
            CompletionInfo ci = offvalue[index];
            if (ic != null) {
                ic.commitCompletion(ci);
            }
            intsize = suggestion.length();
            if (mCandidateView != null) {
                mCandidateView.clear();
            }
            updateShiftKeyState(getCurrentInputEditorInfo());
            if (ic != null) {
                ic.endBatchEdit();
            }
            return;
        }

        if (suggestion.length() == 1
                && (isWordSeparator(suggestion.charAt(0)) || isSuggestedPunctuation(suggestion
                .charAt(0)))) {
            My_Keybord_Imglong.logOnManualSuggestion("", suggestion.toString(),
                    index, suggestions);
            final char primaryCode = suggestion.charAt(0);
            onKey(primaryCode, new int[]{primaryCode},
                    My_Keybord_BaseImg_View.NOT_A_TOUCH_COORDINATE,
                    My_Keybord_BaseImg_View.NOT_A_TOUCH_COORDINATE);
            if (ic != null) {
                ic.endBatchEdit();
            }
            return;
        }
        boolenright = true;
        pickSuggestion(suggestion, correcting);
        if (index == 0) {
            addToDictionaries(suggestion, Outo_Spiling_Keybord.FREQUENCY_FOR_PICKED);
        } else {
            addToBigramDictionary(suggestion, 1);
        }
        My_Keybord_Imglong.logOnManualSuggestion(mComposing.toString(), suggestion
                .toString(), index, suggestions);
        TextEntryState.acceptedSuggestion(mComposing.toString(), suggestion);
        if (mAutoSpace && !correcting) {
            sendSpace();
            bolenjagya = true;
        }

        final boolean showingAddToDictionaryHint = index == 0
                && mCorrectionMode > 0 && !mSuggest.isValidWord(suggestion)
                && !mSuggest.isValidWord(suggestion.toString().toLowerCase());

        if (!correcting) {
            TextEntryState.typedCharacter((char) ASCII_SPACE, true);
            setNextSuggestions();
        } else if (!showingAddToDictionaryHint) {
            clearSuggestions();
            postUpdateOldSuggestions();
        }
        if (showingAddToDictionaryHint) {
            mCandidateView.showAddToDictionaryHint(suggestion);
        }
        if (ic != null) {
            ic.endBatchEdit();
        }
    }

    private void rememberReplacedWord(CharSequence suggestion) {
    }

    private void pickSuggestion(CharSequence suggestion, boolean correcting) {
        My_Keybord_Img_Key inputView = mytypingword.getInputView();
        int shiftState = getShiftState();
        if (shiftState == Keyboard.SHIFT_LOCKED || shiftState == Keyboard.SHIFT_CAPS_LOCKED) {
            suggestion = suggestion.toString().toUpperCase();
        }
        InputConnection ic = getCurrentInputConnection();
        if (ic != null) {
            rememberReplacedWord(suggestion);
            ic.commitText(suggestion, 1);
        }
        saveWordInHistory(suggestion);
        mPredicting = false;
        intsize = suggestion.length();
        ((LatinMy_Keybord) inputView.getKeyboard()).setPreferredLetters(null);
        if (!correcting) {
            setNextSuggestions();
        }
        updateShiftKeyState(getCurrentInputEditorInfo());
    }

    private boolean applyTypedAlternatives(Enter_Img_Keybord.SelectedWord touching) {
        Keyword_Set_Compres foundWord = null;
        WordAlternatives alternatives = null;
        for (WordAlternatives entry : splinglist) {
            if (TextUtils.equals(entry.getChosenWord(), touching.spling)) {
                if (entry instanceof TypedWordAlternatives) {
                    foundWord = ((TypedWordAlternatives) entry).word;
                }
                alternatives = entry;
                break;
            }
        }
        if (foundWord == null
                && (mSuggest.isValidWord(touching.spling) || mSuggest
                .isValidWord(touching.spling.toString().toLowerCase()))) {
            foundWord = new Keyword_Set_Compres();
            for (int i = 0; i < touching.spling.length(); i++) {
                foundWord.add(touching.spling.charAt(i),
                        new int[]{touching.spling.charAt(i)});
            }
            foundWord.setFirstCharCapitalized(Character
                    .isUpperCase(touching.spling.charAt(0)));
        }
        if (foundWord != null || alternatives != null) {
            if (alternatives == null) {
                alternatives = new TypedWordAlternatives(touching.spling,
                        foundWord);
            }
            showCorrections(alternatives);
            if (foundWord != null) {
                compress = new Keyword_Set_Compres(foundWord);
            } else {
                compress.reset();
            }
            return true;
        }
        return false;
    }

    private void setOldSuggestions() {
        if (mCandidateView != null
                && mCandidateView.isShowingAddToDictionaryHint()) {
            return;
        }
        InputConnection ic = getCurrentInputConnection();
        if (ic == null)
            return;
        if (!mPredicting) {
            Enter_Img_Keybord.SelectedWord touching = Enter_Img_Keybord
                    .getWordAtCursorOrSelection(ic, keybordon,
                            keybordbottem, splingsound);

            abortCorrection(true);
            setNextSuggestions();
        } else {
            abortCorrection(true);
        }
    }

    private void setNextSuggestions() {
        setSuggestions(chraecterlist, false, false, false);
    }

    private void addToDictionaries(CharSequence suggestion, int frequencyDelta) {
        checkAddToDictionary(suggestion, frequencyDelta, false);
    }

    private void addToBigramDictionary(CharSequence suggestion,
                                       int frequencyDelta) {
        checkAddToDictionary(suggestion, frequencyDelta, true);
    }

    private void checkAddToDictionary(CharSequence suggestion,
                                      int frequencyDelta, boolean addToBigramDictionary) {
        if (suggestion == null || suggestion.length() < 1)
            return;
        if (!(mCorrectionMode == My_Keyword_Inform_app.CORRECTION_FULL || mCorrectionMode == My_Keyword_Inform_app.CORRECTION_FULL_BIGRAM)) {
            return;
        }
        if (suggestion != null) {
            if (!addToBigramDictionary
                    && splingotelater.isValidWord(suggestion)
                    || (!mSuggest.isValidWord(suggestion.toString()) && !mSuggest
                    .isValidWord(suggestion.toString().toLowerCase()))) {
                splingotelater.addWord(suggestion.toString(), frequencyDelta);
            }

            if (letterspling != null) {
                CharSequence prevWord = Enter_Img_Keybord.getPreviousWord(
                        getCurrentInputConnection(), wordlater);
                if (!TextUtils.isEmpty(prevWord)) {
                    letterspling.addBigrams(prevWord.toString(),
                            suggestion.toString());
                }
            }
        }
    }

    private boolean isCursorTouchingWord() {
        InputConnection ic = getCurrentInputConnection();
        if (ic == null)
            return false;
        CharSequence toLeft = ic.getTextBeforeCursor(1, 0);
        CharSequence toRight = ic.getTextAfterCursor(1, 0);
        if (!TextUtils.isEmpty(toLeft) && !isWordSeparator(toLeft.charAt(0))
                && !isSuggestedPunctuation(toLeft.charAt(0))) {
            return true;
        }
        if (!TextUtils.isEmpty(toRight) && !isWordSeparator(toRight.charAt(0))
                && !isSuggestedPunctuation(toRight.charAt(0))) {
            return true;
        }
        return false;
    }

    private boolean sameAsTextBeforeCursor(InputConnection ic, CharSequence text) {
        CharSequence beforeText = ic.getTextBeforeCursor(text.length(), 0);
        return TextUtils.equals(text, beforeText);
    }

    public void revertLastWord(boolean deleteChar) {
        final int length = mComposing.length();
        if (!mPredicting && length > 0) {
            final InputConnection ic = getCurrentInputConnection();
            mPredicting = true;
            popuchareter = ic.getTextBeforeCursor(1, 0);
            if (deleteChar)
                ic.deleteSurroundingText(1, 0);
            int toDelete = intsize;
            CharSequence toTheLeft = ic
                    .getTextBeforeCursor(intsize, 0);
            if (toTheLeft != null && toTheLeft.length() > 0
                    && isWordSeparator(toTheLeft.charAt(0))) {
                toDelete--;
            }
            ic.deleteSurroundingText(toDelete, 0);
            ic.setComposingText(mComposing, 1);
            TextEntryState.backspace();
            postUpdateSuggestions();
        } else {
            sendDownUpKeyEvents(KeyEvent.KEYCODE_DEL);
            popuchareter = null;
        }
    }

    protected String getWordSeparators() {
        return splingsound;
    }

    public boolean isWordSeparator(int code) {
        String separators = getWordSeparators();
        return separators.contains(String.valueOf((char) code));
    }

    private boolean isSentenceSeparator(int code) {
        return wordlater.contains(String.valueOf((char) code));
    }

    private void sendSpace() {
        sendModifiableKeyChar((char) ASCII_SPACE);
        updateShiftKeyState(getCurrentInputEditorInfo());
    }

    public boolean preferCapitalization() {
        return compress.isFirstCharCapitalized();
    }

    void toggleLanguage(boolean reset, boolean next) {
        if (reset) {
            bhashaof.reset();
        } else {
            if (next) {
                bhashaof.next();
            } else {
                bhashaof.prev();
            }
        }
        int currentKeyboardMode = mytypingword.getKeyboardMode();
        reloadKeyboards();
        mytypingword.makeKeyboards(true);
        mytypingword.setKeyboardMode(currentKeyboardMode, 0,
                soundvalue && soundeffect);
        initSuggest(bhashaof.getInputLanguage());
        bhashaof.persist();
        otoscreen = typingoto && bhashaof.allowAutoCap();
        valuescreen = bhashaof.allowDeadKeys();
        updateShiftKeyState(getCurrentInputEditorInfo());
        setCandidatesViewShown(isPredictionOn());
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {
        Log.i("PCKeyboard", "onSharedPreferenceChanged()");
        boolean needReload = false;
        Resources res = getResources();

        service.sharedPreferenceChanged(sharedPreferences, key);
        if (service.hasFlag(My_Global_Service.FLAG_PREF_NEED_RELOAD)) {
            needReload = true;
        }
        if (service.hasFlag(My_Global_Service.FLAG_PREF_NEW_PUNC_LIST)) {
            initSuggestPuncList();
        }
        if (service.hasFlag(My_Global_Service.FLAG_PREF_RECREATE_INPUT_VIEW)) {
            mytypingword.recreateInputView();
        }
        if (service.hasFlag(My_Global_Service.FLAG_PREF_RESET_MODE_OVERRIDE)) {
            mKeyboardModeOverrideLandscape = 0;
            ingintvalue = 0;
        }
        if (service.hasFlag(My_Global_Service.FLAG_PREF_RESET_KEYBOARDS)) {
            toggleLanguage(true, true);
        }
        int unhandledFlags = service.unhandledFlags();
        if (unhandledFlags != My_Global_Service.FLAG_PREF_NONE) {
            Log.w(TAG, "Not all flag settings handled, remaining=" + unhandledFlags);
        }

        if (PREF_SELECTED_LANGUAGES.equals(key)) {
            bhashaof.loadLocales(sharedPreferences);
            mRefreshKeyboardRequired = true;
        } else if (PREF_RECORRECTION_ENABLED.equals(key)) {
            texthistry = sharedPreferences.getBoolean(
                    PREF_RECORRECTION_ENABLED, res
                            .getBoolean(R.bool.default_recorrection_enabled));
            if (texthistry) {
                Toast.makeText(getApplicationContext(),
                        res.getString(R.string.recorrect_warning), Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (PREF_CONNECTBOT_TAB_HACK.equals(key)) {
            sploingfile = sharedPreferences.getBoolean(
                    PREF_CONNECTBOT_TAB_HACK, res
                            .getBoolean(R.bool.default_connectbot_tab_hack));
        } else if (PREF_FULLSCREEN_OVERRIDE.equals(key)) {
            layoutkey = sharedPreferences.getBoolean(
                    PREF_FULLSCREEN_OVERRIDE, res
                            .getBoolean(R.bool.default_fullscreen_override));
            needReload = true;
        } else if (PREF_FORCE_KEYBOARD_ON.equals(key)) {
            letterstart = sharedPreferences.getBoolean(
                    PREF_FORCE_KEYBOARD_ON, res
                            .getBoolean(R.bool.default_force_keyboard_on));
            needReload = true;
        } else if (PREF_KEYBOARD_NOTIFICATION.equals(key)) {
            lettertextfun = sharedPreferences.getBoolean(
                    PREF_KEYBOARD_NOTIFICATION, res
                            .getBoolean(R.bool.default_keyboard_notification));
            setNotification(lettertextfun);
        } else if (PREF_SUGGESTIONS_IN_LANDSCAPE.equals(key)) {
            lettertextreview = sharedPreferences.getBoolean(
                    PREF_SUGGESTIONS_IN_LANDSCAPE, res
                            .getBoolean(R.bool.default_suggestions_in_landscape));
            letterofreview = false;
            leterrteviewstart = false;
            setCandidatesViewShown(isPredictionOn());
        } else if (PREF_SHOW_SUGGESTIONS.equals(key)) {
            review = sharedPreferences.getBoolean(
                    PREF_SHOW_SUGGESTIONS, res.getBoolean(R.bool.default_suggestions));
            letterofreview = false;
            leterrteviewstart = false;
            needReload = true;
        } else if (PREF_HEIGHT_PORTRAIT.equals(key)) {
            latintspling = getHeight(sharedPreferences,
                    PREF_HEIGHT_PORTRAIT, res.getString(R.string.default_height_portrait));
            needReload = true;
        } else if (PREF_HEIGHT_LANDSCAPE.equals(key)) {
            splinghit = getHeight(sharedPreferences,
                    PREF_HEIGHT_LANDSCAPE, res.getString(R.string.default_height_landscape));
            needReload = true;
        } else if (PREF_HINT_MODE.equals(key)) {
            LatinIME.service.haelpspling = Integer.parseInt(sharedPreferences.getString(PREF_HINT_MODE,
                    res.getString(R.string.default_hint_mode)));
            needReload = true;
        } else if (PREF_LONGPRESS_TIMEOUT.equals(key)) {
            LatinIME.service.seekbarwatch = getPrefInt(sharedPreferences, PREF_LONGPRESS_TIMEOUT,
                    res.getString(R.string.default_long_press_duration));
        } else if (PREF_RENDER_MODE.equals(key)) {
            LatinIME.service.readingmode = getPrefInt(sharedPreferences, PREF_RENDER_MODE,
                    res.getString(R.string.default_render_mode));
            needReload = true;
        } else if (PREF_SWIPE_UP.equals(key)) {
            topstring = sharedPreferences.getString(PREF_SWIPE_UP, res.getString(R.string.default_swipe_up));
        } else if (PREF_SWIPE_DOWN.equals(key)) {
            endstring = sharedPreferences.getString(PREF_SWIPE_DOWN, res.getString(R.string.default_swipe_down));
        } else if (PREF_SWIPE_LEFT.equals(key)) {
            jamnisidestring = sharedPreferences.getString(PREF_SWIPE_LEFT, res.getString(R.string.default_swipe_left));
        } else if (PREF_SWIPE_RIGHT.equals(key)) {
            dabisidestring = sharedPreferences.getString(PREF_SWIPE_RIGHT, res.getString(R.string.default_swipe_right));
        } else if (PREF_VOL_UP.equals(key)) {
            volumstring = sharedPreferences.getString(PREF_VOL_UP, res.getString(R.string.default_vol_up));
        } else if (PREF_VOL_DOWN.equals(key)) {
            botemsound = sharedPreferences.getString(PREF_VOL_DOWN, res.getString(R.string.default_vol_down));
        } else if (PREF_VIBRATE_LEN.equals(key)) {
            soundsize = getPrefInt(sharedPreferences, PREF_VIBRATE_LEN, getResources().getString(R.string.vibrate_duration_ms));
        }

        updateKeyboardOptions();
        if (needReload) {
            mytypingword.makeKeyboards(true);
        }
    }

    private boolean doSwipeAction(String action) {
        if (action == null || action.equals("") || action.equals("none")) {
            return false;
        } else if (action.equals("close")) {
            handleClose();
        } else if (action.equals("settings")) {
            launchSettings();
        } else if (action.equals("suggestions")) {
            if (leterrteviewstart) {
                leterrteviewstart = false;
                letterofreview = true;
            } else if (letterofreview) {
                leterrteviewstart = true;
                letterofreview = false;
            } else if (isPredictionWanted()) {
                letterofreview = true;
            } else {
                leterrteviewstart = true;
            }
            setCandidatesViewShown(isPredictionOn());
        } else if (action.equals("lang_prev")) {
            toggleLanguage(false, false);
        } else if (action.equals("lang_next")) {
            toggleLanguage(false, true);
        } else if (action.equals("debug_auto_play")) {
            if (My_Keybord_Img_Key.DEBUG_AUTO_PLAY) {
                ClipboardManager cm = ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE));
                CharSequence text = cm.getText();
                if (!TextUtils.isEmpty(text)) {
                    mytypingword.getInputView().startPlaying(text.toString());
                }
            }
        } else if (action.equals("full_mode")) {
            if (isPortrait()) {
                ingintvalue = (ingintvalue + 1) % numbervalue;
            } else {
                mKeyboardModeOverrideLandscape = (mKeyboardModeOverrideLandscape + 1) % numbervalue;
            }
            toggleLanguage(true, true);
        } else if (action.equals("extension")) {
            service.bolenglobemode = !service.bolenglobemode;
            reloadKeyboards();
        } else if (action.equals("height_up")) {
            if (isPortrait()) {
                latintspling += 5;
                if (latintspling > 70) latintspling = 70;
            } else {
                splinghit += 5;
                if (splinghit > 70) splinghit = 70;
            }
            toggleLanguage(true, true);
        } else if (action.equals("height_down")) {
            if (isPortrait()) {
                latintspling -= 5;
                if (latintspling < 15) latintspling = 15;
            } else {
                splinghit -= 5;
                if (splinghit < 15) splinghit = 15;
            }
            toggleLanguage(true, true);
        } else {
            Log.i(TAG, "Unsupported swipe action config: " + action);
        }
        return true;
    }

    public boolean swipeRight() {
        return doSwipeAction(dabisidestring);
    }

    public boolean swipeLeft() {
        return doSwipeAction(jamnisidestring);
    }

    public boolean swipeDown() {
        return doSwipeAction(endstring);
    }

    public boolean swipeUp() {
        return doSwipeAction(topstring);
    }

    public void onPress(int primaryCode) {
        InputConnection ic = getCurrentInputConnection();
        if (mytypingword.isVibrateAndSoundFeedbackRequired()) {
            vibrate();
            playKeyClick(primaryCode);
        }
        final boolean distinctMultiTouch = mytypingword
                .hasDistinctMultitouch();
        if (distinctMultiTouch && primaryCode == Keyboard.KEYCODE_SHIFT) {
            modyfiykeytext.onPress();
            startMultitouchShift();
        } else if (distinctMultiTouch
                && primaryCode == Keyboard.KEYCODE_MODE_CHANGE) {
            changeKeyboardMode();
            modyfiyimg.onPress();
            mytypingword.setAutoModeSwitchStateMomentary();
        } else if (distinctMultiTouch
                && primaryCode == My_Keybord_Img_Key.KEYCODE_CTRL_LEFT) {
            setModCtrl(!keycontrol);
            modifyimg.onPress();
            sendCtrlKey(ic, true, true);
        } else if (distinctMultiTouch
                && primaryCode == My_Keybord_Img_Key.KEYCODE_ALT_LEFT) {
            setModAlt(!keyalter);
            modyfiy.onPress();
            sendAltKey(ic, true, true);
        } else if (distinctMultiTouch
                && primaryCode == My_Keybord_Img_Key.KEYCODE_META_LEFT) {
            setModMeta(!keymaeta);
            modyfiyname.onPress();
            sendMetaKey(ic, true, true);
        } else if (distinctMultiTouch
                && primaryCode == My_Keybord_Img_Key.KEYCODE_FN) {
            setModFn(!keyfunction);
            mFnKeyState.onPress();
        } else {
            modyfiykeytext.onOtherKeyPressed();
            modyfiyimg.onOtherKeyPressed();
            modifyimg.onOtherKeyPressed();
            modyfiy.onOtherKeyPressed();
            modyfiyname.onOtherKeyPressed();
            mFnKeyState.onOtherKeyPressed();
        }
    }

    public void onRelease(int primaryCode) {
        ((LatinMy_Keybord) mytypingword.getInputView().getKeyboard())
                .keyReleased();
        final boolean distinctMultiTouch = mytypingword
                .hasDistinctMultitouch();
        InputConnection ic = getCurrentInputConnection();
        if (distinctMultiTouch && primaryCode == Keyboard.KEYCODE_SHIFT) {
            if (modyfiykeytext.isChording()) {
                resetMultitouchShift();
            } else {
                commitMultitouchShift();
            }
            modyfiykeytext.onRelease();
        } else if (distinctMultiTouch
                && primaryCode == Keyboard.KEYCODE_MODE_CHANGE) {
            if (mytypingword.isInChordingAutoModeSwitchState())
                changeKeyboardMode();
            modyfiyimg.onRelease();
        } else if (distinctMultiTouch
                && primaryCode == My_Keybord_Img_Key.KEYCODE_CTRL_LEFT) {
            if (modifyimg.isChording()) {
                setModCtrl(false);
            }
            sendCtrlKey(ic, false, true);
            modifyimg.onRelease();
        } else if (distinctMultiTouch
                && primaryCode == My_Keybord_Img_Key.KEYCODE_ALT_LEFT) {
            if (modyfiy.isChording()) {
                setModAlt(false);
            }
            sendAltKey(ic, false, true);
            modyfiy.onRelease();
        } else if (distinctMultiTouch
                && primaryCode == My_Keybord_Img_Key.KEYCODE_META_LEFT) {
            if (modyfiyname.isChording()) {
                setModMeta(false);
            }
            sendMetaKey(ic, false, true);
            modyfiyname.onRelease();
        } else if (distinctMultiTouch
                && primaryCode == My_Keybord_Img_Key.KEYCODE_FN) {
            if (mFnKeyState.isChording()) {
                setModFn(false);
            }
            mFnKeyState.onRelease();
        }
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateRingerMode();
        }
    };

    private void updateRingerMode() {
        if (spekermng == null) {
            spekermng = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        }
        if (spekermng != null) {
            mSilentMode = (spekermng.getRingerMode() != AudioManager.RINGER_MODE_NORMAL);
        }
    }

    private float getKeyClickVolume() {
        if (spekermng == null) return 0.0f;


        int method = service.chaviselected;
        if (method == 0) return FX_VOLUME;

        float targetVol = service.soundeffect;

        if (method > 1) {
            // TODO(klausw): on some devices the media volume controls the click volume?
            int mediaMax = spekermng.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            int mediaVol = spekermng.getStreamVolume(AudioManager.STREAM_MUSIC);
            float channelVol = (float) mediaVol / mediaMax;
            if (method == 2) {
                targetVol *= channelVol;
            } else if (method == 3) {
                if (channelVol == 0) return 0.0f;
                targetVol = Math.min(targetVol / channelVol, 1.0f);
            }
        }
        float vol = (float) Math.pow(10.0, FX_VOLUME_RANGE_DB * (targetVol - 1) / 20);
        return vol;
    }

    private void playKeyClick(int primaryCode) {
        if (spekermng == null) {
            if (mytypingword.getInputView() != null) {
                updateRingerMode();
            }
        }
        if (mSoundOn && !mSilentMode) {
            // FIXME: Volume and enable should come from UI settings
            // FIXME: These should be triggered after auto-repeat logic
            int sound = AudioManager.FX_KEYPRESS_STANDARD;
            switch (primaryCode) {
                case Keyboard.KEYCODE_DELETE:
                    sound = AudioManager.FX_KEYPRESS_DELETE;
                    break;
                case ASCII_ENTER:
                    sound = AudioManager.FX_KEYPRESS_RETURN;
                    break;
                case ASCII_SPACE:
                    sound = AudioManager.FX_KEYPRESS_SPACEBAR;
                    break;
            }
            spekermng.playSoundEffect(sound, getKeyClickVolume());
        }
    }

    private void vibrate() {
        if (!mVibrateOn) {
            return;
        }
        vibrate(soundsize);
    }

    void vibrate(int len) {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (v != null) {
            v.vibrate(len);
            return;
        }

        if (mytypingword.getInputView() != null) {
            mytypingword.getInputView().performHapticFeedback(
                    HapticFeedbackConstants.KEYBOARD_TAP,
                    HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
        }
    }

    private void checkTutorial(String privateImeOptions) {
        if (privateImeOptions == null)
            return;
        if (privateImeOptions.equals("com.android.setupwizard:ShowTutorial")) {
            if (latme == null)
                startTutorial();
        } else if (privateImeOptions
                .equals("com.android.setupwizard:HideTutorial")) {
            if (latme != null) {
                if (latme.close()) {
                    latme = null;
                }
            }
        }
    }

    private void startTutorial() {
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_START_TUTORIAL),
                500);
    }

    void tutorialDone() {
        latme = null;
    }

    void promoteToUserDictionary(String word, int frequency) {
        if (fileprov.isValidWord(word))
            return;
        fileprov.addWord(word, frequency);
    }

    Keyword_Set_Compres getCurrentWord() {
        return compress;
    }

    boolean getPopupOn() {
        return mPopupOn;
    }

    private void updateCorrectionMode() {
        filevalue = mSuggest != null ? mSuggest.hasMainDictionary()
                : false;
        mAutoCorrectOn = (ototextletter || valuecomplite)
                && !otocrunt && filevalue;
        mCorrectionMode = (mAutoCorrectOn && ototextletter) ? My_Keyword_Inform_app.CORRECTION_FULL
                : (mAutoCorrectOn ? My_Keyword_Inform_app.CORRECTION_BASIC
                : My_Keyword_Inform_app.CORRECTION_NONE);
        mCorrectionMode = (letterrevieu && mAutoCorrectOn && ototextletter) ? My_Keyword_Inform_app.CORRECTION_FULL_BIGRAM
                : mCorrectionMode;
        if (suggestionsDisabled()) {
            mAutoCorrectOn = false;
            mCorrectionMode = My_Keyword_Inform_app.CORRECTION_NONE;
        }
        if (mSuggest != null) {
            mSuggest.setCorrectionMode(mCorrectionMode);
        }
    }

    private void updateAutoTextEnabled(Locale systemLocale) {
        if (mSuggest == null)
            return;
        boolean different = !systemLocale.getLanguage().equalsIgnoreCase(
                localvaribale.substring(0, 2));
        mSuggest.setAutoTextEnabled(!different && valuecomplite);
    }

    protected void launchSettings() {
        launchSettings(My_Keybord_Img_Service.class);
    }

    public void launchDebugSettings() {
        launchSettings(My_Keyboard_dibug_Service.class);
    }

    protected void launchSettings(
            Class<? extends PreferenceActivity> settingsClass) {
        handleClose();
        Intent intent = new Intent();
        intent.setClass(LatinIME.this, settingsClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void loadSettings() {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(this);
        mVibrateOn = sp.getBoolean(PREF_VIBRATE_ON, false);
        soundsize = getPrefInt(sp, PREF_VIBRATE_LEN, getResources().getString(R.string.vibrate_duration_ms));
        mSoundOn = sp.getBoolean(PREF_SOUND_ON, false);
        mPopupOn = sp.getBoolean(PREF_POPUP_ON, valuer
                .getBoolean(R.bool.default_popup_preview));
        typingoto = sp.getBoolean(PREF_AUTO_CAP, getResources().getBoolean(
                R.bool.default_auto_cap));
        valuecomplite = sp.getBoolean(PREF_QUICK_FIXES, true);

        review = sp.getBoolean(PREF_SHOW_SUGGESTIONS, valuer
                .getBoolean(R.bool.default_suggestions));

        final String voiceMode = sp.getString(PREF_VOICE_MODE,
                getString(R.string.voice_mode_main));
        boolean enableVoice = !voiceMode
                .equals(getString(R.string.voice_mode_off))
                && soundvalue;
        boolean voiceOnPrimary = voiceMode
                .equals(getString(R.string.voice_mode_main));
        if (mytypingword != null
                && (enableVoice != soundeffect || voiceOnPrimary != soundlocakl)) {
            mytypingword.setVoiceMode(enableVoice, voiceOnPrimary);
        }
        soundeffect = enableVoice;
        soundlocakl = voiceOnPrimary;

        ototextletter = sp.getBoolean(PREF_AUTO_COMPLETE, valuer
                .getBoolean(R.bool.enable_autocorrect))
                & review;
        updateCorrectionMode();
        updateAutoTextEnabled(valuer.getConfiguration().locale);
        bhashaof.loadLocales(sp);
        otoscreen = typingoto && bhashaof.allowAutoCap();
        valuescreen = bhashaof.allowDeadKeys();
    }

    private void initSuggestPuncList() {
        chraecterlist = new ArrayList<CharSequence>();
        String suggestPuncs = service.reviewsymbole;
        String defaultPuncs = getResources().getString(R.string.suggested_punctuations_default);
        if (suggestPuncs.equals(defaultPuncs) || suggestPuncs.equals("")) {
            suggestPuncs = getResources().getString(R.string.suggested_punctuations);
        }
        if (suggestPuncs != null) {
            for (int i = 0; i < suggestPuncs.length(); i++) {
                chraecterlist.add(suggestPuncs.subSequence(i, i + 1));
            }
        }
        setNextSuggestions();
    }

    private boolean isSuggestedPunctuation(int code) {
        return service.reviewsymbole.contains(String.valueOf((char) code));
    }

    private void showOptionsMenu() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setIcon(R.drawable.ic_dialog_keyboard);
        builder.setNegativeButton(android.R.string.cancel, null);
        CharSequence itemSettings = getString(R.string.english_ime_settings);
        CharSequence itemInputMethod = getString(R.string.selectInputMethod);
        builder.setItems(new CharSequence[]{itemInputMethod, itemSettings},
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface di, int position) {
                        di.dismiss();
                        switch (position) {
                            case POS_SETTINGS:
                                launchSettings();
                                break;
                            case POS_METHOD:
                                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                                        .showInputMethodPicker();
                                break;
                        }
                    }
                });
        builder.setTitle(valuer
                .getString(R.string.english_ime_input_options));
        alert = builder.create();
        Window window = alert.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.token = mytypingword.getInputView().getWindowToken();
        lp.type = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG;
        window.setAttributes(lp);
        window.addFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        alert.show();
    }

    public void changeKeyboardMode() {
        Typing_Switch switcher = mytypingword;
        if (switcher.isAlphabetMode()) {
            kayadd = getShiftState();
        }
        switcher.toggleSymbols();
        if (switcher.isAlphabetMode()) {
            switcher.setShiftState(kayadd);
        }

        updateShiftKeyState(getCurrentInputEditorInfo());
    }

    public static <E> ArrayList<E> newArrayList(E... elements) {
        int capacity = (elements.length * 110) / 100 + 5;
        ArrayList<E> list = new ArrayList<E>(capacity);
        Collections.addAll(list, elements);
        return list;
    }

    @Override
    protected void dump(FileDescriptor fd, PrintWriter fout, String[] args) {
        super.dump(fd, fout, args);

        final Printer p = new PrintWriterPrinter(fout);
        p.println("LatinIME state :");
        p.println("  Keyboard mode = " + mytypingword.getKeyboardMode());
        p.println("  mComposing=" + mComposing.toString());
        p.println("  mPredictionOnForMode=" + mPredictionOnForMode);
        p.println("  mCorrectionMode=" + mCorrectionMode);
        p.println("  mPredicting=" + mPredicting);
        p.println("  mAutoCorrectOn=" + mAutoCorrectOn);
        p.println("  mAutoSpace=" + mAutoSpace);
        p.println("  mCompletionOn=" + mCompletionOn);
        p.println("  TextEntryState.state=" + TextEntryState.getState());
        p.println("  mSoundOn=" + mSoundOn);
        p.println("  mVibrateOn=" + mVibrateOn);
        p.println("  mPopupOn=" + mPopupOn);
    }


    private long mLastCpsTime;
    private static final int CPS_BUFFER_SIZE = 16;
    private long[] mCpsIntervals = new long[CPS_BUFFER_SIZE];
    private int mCpsIndex;
    private static Pattern NUMBER_RE = Pattern.compile("(\\d+).*");

    private void measureCps() {
        long now = System.currentTimeMillis();
        if (mLastCpsTime == 0)
            mLastCpsTime = now - 100;
        mCpsIntervals[mCpsIndex] = now - mLastCpsTime;
        mLastCpsTime = now;
        mCpsIndex = (mCpsIndex + 1) % CPS_BUFFER_SIZE;
        long total = 0;
        for (int i = 0; i < CPS_BUFFER_SIZE; i++)
            total += mCpsIntervals[i];
        System.out.println("CPS = " + ((CPS_BUFFER_SIZE * 1000f) / total));
    }

    public void onAutoCompletionStateChanged(boolean isAutoCompletion) {
        mytypingword.onAutoCompletionStateChanged(isAutoCompletion);
    }

    static int getIntFromString(String val, int defVal) {
        Matcher num = NUMBER_RE.matcher(val);
        if (!num.matches()) return defVal;
        return Integer.parseInt(num.group(1));
    }

    static int getPrefInt(SharedPreferences prefs, String prefName, int defVal) {
        String prefVal = prefs.getString(prefName, Integer.toString(defVal));
        return getIntFromString(prefVal, defVal);
    }

    static int getPrefInt(SharedPreferences prefs, String prefName, String defStr) {
        int defVal = getIntFromString(defStr, 0);
        return getPrefInt(prefs, prefName, defVal);
    }

    static int getHeight(SharedPreferences prefs, String prefName, String defVal) {
        int val = getPrefInt(prefs, prefName, defVal);
        if (val < 15)
            val = 15;
        if (val > 75)
            val = 75;
        return val;
    }
}
