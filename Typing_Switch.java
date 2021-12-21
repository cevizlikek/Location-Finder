package com.keyboard.keyboardthemes;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.InflateException;

import com.keyboard.keyboardthemes.R;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;



public class Typing_Switch implements SharedPreferences.OnSharedPreferenceChangeListener {

	private static String TAG = "PCKeyboardKbSw";
	public static final int ZERO_VALUE = 0;
	public static final int ONE_VALUE = 1;
	public static final int TWO_VALUE = 2;
	public static final int THREE_VALUE = 3;
	public static final int FOURE_VALUE = 4;
	public static final int FIVE_VALUE = 5;
	public static final int SIX_VALUE = 6;
	public static final int SEVEN_VALUE = 7;
	public static final int TYPING_EAZEY = R.id.mode_normal;
	public static final int TYPING_LINK = R.id.mode_url;
	public static final int TYPING_EID = R.id.mode_email;
	public static final int TYPING_ENTER = R.id.mode_im;
	public static final int TYPING_NETWORK = R.id.mode_webentry;
	public static final int TYPING_EAZEY_AND_SERVISES = R.id.mode_normal_with_settings_key;
	public static final int TYPING_LINK_AND_SERVISES = R.id.mode_url_with_settings_key;
	public static final int TYPING_EID_AND_SERVISES = R.id.mode_email_with_settings_key;
	public static final int TYPING_TEXT_AND_SERVISES = R.id.mode_im_with_settings_key;
	public static final int TYPING_NETWORK_AND_SERVISES = R.id.mode_webentry_with_settings_key;
	public static final int TYPING_IMG = R.id.mode_symbols;
	public static final int TYPING_IMG_AND_SERVISES = R.id.mode_symbols_with_settings_key;
	public static String ZERO_VALUE_SCREEN = "0";
	public static final String TYPING_WORD = "pref_keyboard_layout";
	private static final int TYPING_MOBILE = R.xml.kbd_phone;
	private static final int TYPING_MOBILE_IMG = R.xml.kbd_phone_symbols;
	private static final int TYPING_IMGVIEW = R.xml.kbd_symbols;
	private static final int TYPING_IMGSIFT = R.xml.kbd_symbols_shift;
	private static final int TYPING_ERROR = R.xml.kbd_qwerty;
	private static final int TYPING_ALLSCREEN = R.xml.kbd_full;
	private static final int TYPING_FUNCTON = R.xml.kbd_full_fn;
	private static final int TYPING_COMPECT = R.xml.kbd_compact;
	private static final int TYPING_COMFUNCTION = R.xml.kbd_compact_fn;
	private static My_Keybord_Img_Key intypingbord;
	private static LatinIME LATINMESETING;
	private KeyboardId IMGVIEWID;
	private KeyboardId TYPING_IMGVIEWID;
	private KeyboardId exitebode;
	private final HashMap<KeyboardId, SoftReference<LatinMy_Keybord>> mytypingbord = new HashMap<KeyboardId, SoftReference<LatinMy_Keybord>>();
	private int mMode = ZERO_VALUE;
	private int imgselected;
	private boolean idimgview;
	private int intfulmode;
	private boolean otogenrtedtext;
	private boolean soundview;
	private boolean soundprim;
	private boolean refreneimg;
	private static final int AUTO_MODE_SWITCH_STATE_ALPHA = 0;
	private static final int AUTO_MODE_SWITCH_STATE_SYMBOL_BEGIN = 1;
	private static final int AUTO_MODE_SWITCH_STATE_SYMBOL = 2;
	private static final int AUTO_MODE_SWITCH_STATE_MOMENTARY = 3;
	private static final int AUTO_MODE_SWITCH_STATE_CHORDING = 4;
	private int ototextspling = AUTO_MODE_SWITCH_STATE_ALPHA;
	private boolean serviseschavi;
	private static final int SETTINGS_KEY_MODE_AUTO = R.string.settings_key_mode_auto;
	private static final int SETTINGS_KEY_MODE_ALWAYS_SHOW = R.string.settings_key_mode_always_show;
	private static final int DEFAULT_SETTINGS_KEY_MODE = SETTINGS_KEY_MODE_AUTO;
	private int screenweth;
	private Bhasha_Keybord_Swich bhahsacheng;
	private static int screenname;

	private static final int[] STYLESBORD = new int[] {
			R.layout.keyboard_style_a, R.layout.keyboard_style_b,
			R.layout.keyboard_style_c, R.layout.keyboard_style_d,
			R.layout.keyboard_style_e, R.layout.keyboard_style_f,
			R.layout.keyboard_style_g, R.layout.keyboard_style_h,
			R.layout.keyboard_style_i, R.layout.keyboard_style_j,
			R.layout.keyboard_style_k, R.layout.keyboard_style_l,
			R.layout.keyboard_style_m, R.layout.keyboard_style_n,
			R.layout.keyboard_style_o, R.layout.keyboard_style_p,
			R.layout.keyboard_style_q, R.layout.keyboard_style_r,
			R.layout.keyboard_style_s, R.layout.keyboard_style_t,
			R.layout.keyboard_style_u, R.layout.keyboard_style_v,
			R.layout.keyboard_style_w, R.layout.keyboard_style_x,
			R.layout.keyboard_style_y, R.layout.keyboard_style_z,
			R.layout.keyboard_style_za, R.layout.keyboard_style_zb,};

	private static final int[] BHASAH_NAME = {TYPING_EAZEY,
			TYPING_LINK, TYPING_EID, TYPING_ENTER,
			TYPING_NETWORK, TYPING_EAZEY_AND_SERVISES,
			TYPING_LINK_AND_SERVISES,
			TYPING_EID_AND_SERVISES,
			TYPING_TEXT_AND_SERVISES,
			TYPING_NETWORK_AND_SERVISES};


	private static final Typing_Switch sInstance = new Typing_Switch();

	public static Typing_Switch getInstance() {
		return sInstance;
	}

	private Typing_Switch() {
	}

	public static void init(LatinIME latme) {
		sInstance.LATINMESETING = latme;

		final SharedPreferences refrence = PreferenceManager.getDefaultSharedPreferences(latme);

		int myIntValue = refrence.getInt("SET_THEMES", 0);
		Log.d("KEY_VAlYue", String.valueOf(myIntValue));
		ZERO_VALUE_SCREEN = String.valueOf(myIntValue);

		sInstance.screenname = Integer.valueOf(refrence.getString(TYPING_WORD, ZERO_VALUE_SCREEN));

		sInstance.updateSettingsKeyState(refrence);
        refrence.registerOnSharedPreferenceChangeListener(sInstance);

		sInstance.IMGVIEWID = sInstance.makeSymbolsId(false);
		sInstance.TYPING_IMGVIEWID = sInstance.makeSymbolsShiftedId(false);
	}

	public void setLanguageSwitcher(Bhasha_Keybord_Swich bhashachenge) {
		bhahsacheng = bhashachenge;
		bhashachenge.getInputLocale();
	}

	private KeyboardId makeSymbolsId(boolean sound) {
		if (intfulmode == 1) {
			return new KeyboardId(TYPING_COMFUNCTION, TYPING_IMG, true, sound);
		} else if (intfulmode == 2) {
			return new KeyboardId(TYPING_FUNCTON, TYPING_IMG, true, sound);
		}
		return new KeyboardId(TYPING_IMGVIEW, serviseschavi ? TYPING_IMG_AND_SERVISES : TYPING_IMG, false, sound);
	}

	private KeyboardId makeSymbolsShiftedId(boolean hasVoice) {
		if (intfulmode > 0)
			return null;
		return new KeyboardId(TYPING_IMGSIFT,
				serviseschavi ? TYPING_IMG_AND_SERVISES : TYPING_IMG, false, hasVoice);
	}

	public void makeKeyboards(boolean forceCreate) {
		intfulmode = LatinIME.service.keyboardMode;
		IMGVIEWID = makeSymbolsId(soundview && !soundprim);
		TYPING_IMGVIEWID = makeSymbolsShiftedId(soundview && !soundprim);

		if (forceCreate)
			mytypingbord.clear();
		int displayWidth = LATINMESETING.getMaxWidth();
		if (displayWidth == screenweth)
			return;
		screenweth = displayWidth;
		if (!forceCreate)
			mytypingbord.clear();
	}

	private static class KeyboardId {
		// TODO: should have locale and portrait/landscape orientation?
		public final int file;
		public final int typingword;
		public final boolean shifkeyvalue;
		public final boolean soundbord;
		public final float typingbordhit;
		public final boolean usetypingbord;

		private final int mHashCode;

		public KeyboardId(int xml, int mode, boolean enableShiftLock, boolean hasVoice) {
			this.file = xml;
			this.typingword = mode;
			this.shifkeyvalue = enableShiftLock;
			this.soundbord = hasVoice;
			this.typingbordhit = LatinIME.service.typingwordhit;
			this.usetypingbord = LatinIME.service.bolenglobemode;

			this.mHashCode = Arrays.hashCode(new Object[] { xml, mode,
					enableShiftLock, hasVoice });
		}

		@Override
		public boolean equals(Object anoth) {
			return anoth instanceof KeyboardId && equals((KeyboardId) anoth);
		}

		private boolean equals(KeyboardId another) {
			return another != null && another.file == this.file
					&& another.typingword == this.typingword
					&& another.usetypingbord == this.usetypingbord
					&& another.shifkeyvalue == this.shifkeyvalue
					&& another.soundbord == this.soundbord;
		}

		@Override
		public int hashCode() {
			return mHashCode;
		}
	}

	public void setVoiceMode(boolean enableVoice, boolean voiceOnPrimary) {
		if (enableVoice != soundview || voiceOnPrimary != soundprim) {
			mytypingbord.clear();
		}
		soundview = enableVoice;
		soundprim = voiceOnPrimary;
		setKeyboardMode(mMode, imgselected, soundview, idimgview);
	}

	private boolean hasVoiceButton(boolean isSymbols) {
		return soundview && (isSymbols != soundprim);
	}

	public void setKeyboardMode(int mode, int imeOptions, boolean enableVoice) {
		ototextspling = AUTO_MODE_SWITCH_STATE_ALPHA;
		refreneimg = mode == TWO_VALUE;
		if (mode == TWO_VALUE) {
			mode = ONE_VALUE;
		}
		try {
			setKeyboardMode(mode, imeOptions, enableVoice, refreneimg);
		} catch (RuntimeException run) {
			My_Keybord_Imglong.logOnException(mode + "," + imeOptions + "," + refreneimg, run);
		}
	}

	private void setKeyboardMode(int mode, int imeOptions, boolean enableVoice, boolean isSymbols) {
		if (intypingbord == null)
			return;
		mMode = mode;
		imgselected = imeOptions;
		if (enableVoice != soundview) {
			// TODO clean up this unnecessary recursive call.
			setVoiceMode(enableVoice, soundprim);
		}

		idimgview = isSymbols;
		intypingbord.setPreviewEnabled(LATINMESETING.getPopupOn());
		KeyboardId name = getKeyboardId(mode, imeOptions, isSymbols);
		LatinMy_Keybord typingbord = null;
		typingbord = getKeyboard(name);

		if (mode == THREE_VALUE) {
			intypingbord.setPhoneKeyboard(typingbord);
		}

		exitebode = name;
		intypingbord.setKeyboard(typingbord);
		typingbord.setShiftState(Keyboard.SHIFT_OFF);
		typingbord.setImeOptions(LATINMESETING.getResources(), mMode, imeOptions);
		typingbord.updateSymbolIcons(otogenrtedtext);
	}

	private LatinMy_Keybord getKeyboard(KeyboardId id) {
		SoftReference<LatinMy_Keybord> softrefrence = mytypingbord.get(id);
		LatinMy_Keybord typingbord = (softrefrence == null) ? null : softrefrence.get();
		if (typingbord == null) {
			Resources srvises = LATINMESETING.getResources();
			Configuration greation = srvises.getConfiguration();
			Locale addvalue = greation.locale;
			greation.locale = LatinIME.service.defultevalue;
			srvises.updateConfiguration(greation, null);
			typingbord = new LatinMy_Keybord(LATINMESETING, id.file, id.typingword, id.typingbordhit);
			typingbord.setVoiceMode(hasVoiceButton(id.file == R.xml.kbd_symbols), soundview);
			typingbord.setLanguageSwitcher(bhahsacheng, otogenrtedtext);

			if (id.shifkeyvalue) {
				typingbord.enableShiftLock();
			}
			mytypingbord.put(id, new SoftReference<LatinMy_Keybord>(typingbord));
			greation.locale = addvalue;
			srvises.updateConfiguration(greation, null);
		}
		return typingbord;
	}

	public boolean isFullMode() {
		return intfulmode > 0;
	}

	private KeyboardId getKeyboardId(int mode, int imgketbord, boolean img) {
		boolean hasVoice = hasVoiceButton(img);
		if (intfulmode > 0) {
			switch (mode) {
			case ONE_VALUE:
			case FOURE_VALUE:
			case FIVE_VALUE:
			case SIX_VALUE:
			case SEVEN_VALUE:
				return new KeyboardId(intfulmode == 1 ? TYPING_COMPECT : TYPING_ALLSCREEN,
						TYPING_EAZEY, true, hasVoice);
			}
		}
		// TODO: generalize for any KeyboardId
		int keyboardRowsResId = TYPING_ERROR;
		if (img) {
			if (mode == THREE_VALUE) {
				return new KeyboardId(TYPING_MOBILE_IMG, 0, false, hasVoice);
			} else {
				return new KeyboardId(
						TYPING_IMGVIEW, serviseschavi ? TYPING_IMG_AND_SERVISES : TYPING_IMG, false, hasVoice);
			}
		}
		switch (mode) {

			case ZERO_VALUE:
			My_Keybord_Imglong.logOnWarning("getKeyboardId:" + mode + "," + imgketbord + "," + img);

		case ONE_VALUE:
			return new KeyboardId(keyboardRowsResId,
					serviseschavi ? TYPING_EAZEY_AND_SERVISES : TYPING_EAZEY, true, hasVoice);

		case TWO_VALUE:
			return new KeyboardId(TYPING_IMGVIEW,
					serviseschavi ? TYPING_IMG_AND_SERVISES : TYPING_IMG, false, hasVoice);

		case THREE_VALUE:
			return new KeyboardId(TYPING_MOBILE, 0, false, hasVoice);

		case FOURE_VALUE:
			return new KeyboardId(keyboardRowsResId,
					serviseschavi ? TYPING_LINK_AND_SERVISES : TYPING_LINK, true, hasVoice);

		case FIVE_VALUE:
			return new KeyboardId(keyboardRowsResId, serviseschavi ? TYPING_EID_AND_SERVISES : TYPING_EID, true, hasVoice);

		case SIX_VALUE:
			return new KeyboardId(keyboardRowsResId,
					serviseschavi ? TYPING_TEXT_AND_SERVISES : TYPING_ENTER, true, hasVoice);

		case SEVEN_VALUE:
			return new KeyboardId(keyboardRowsResId,
					serviseschavi ? TYPING_NETWORK_AND_SERVISES : TYPING_NETWORK, true, hasVoice);
		}
		return null;
	}

	public int getKeyboardMode() {
		return mMode;
	}

	public boolean isAlphabetMode() {
		if (exitebode == null) {
			return false;
		}
		int currentMode = exitebode.typingword;
		if (intfulmode > 0 && currentMode == TYPING_EAZEY)
			return true;
		for (Integer mode : BHASAH_NAME) {
			if (currentMode == mode) {
				return true;
			}
		}
		return false;
	}

	public void setShiftState(int shiftState) {
		if (intypingbord != null) {
			intypingbord.setShiftState(shiftState);
		}
	}

	public void setFn(boolean useFn) {
		if (intypingbord == null)
			return;
		int oldShiftState = intypingbord.getShiftState();
		if (useFn) {
			LatinMy_Keybord typingbord = getKeyboard(IMGVIEWID);
			typingbord.enableShiftLock();
			exitebode = IMGVIEWID;
			intypingbord.setKeyboard(typingbord);
			intypingbord.setShiftState(oldShiftState);
		} else {
			setKeyboardMode(mMode, imgselected, soundview, false);
			intypingbord.setShiftState(oldShiftState);
		}
	}

	public void setCtrlIndicator(boolean stratup) {
		if (intypingbord == null)
			return;
		intypingbord.setCtrlIndicator(stratup);
	}

	public void setAltIndicator(boolean stratup) {
		if (intypingbord == null)
			return;
		intypingbord.setAltIndicator(stratup);
	}

	public void setMetaIndicator(boolean stratup) {
		if (intypingbord == null)
			return;
		intypingbord.setMetaIndicator(stratup);
	}

	public void toggleShift() {
		if (isAlphabetMode())
			return;
		if (intfulmode > 0) {
			boolean shifted = intypingbord.isShiftAll();
			intypingbord.setShiftState(shifted ? Keyboard.SHIFT_OFF : Keyboard.SHIFT_ON);
			return;
		}
		if (exitebode.equals(IMGVIEWID) || !exitebode.equals(TYPING_IMGVIEWID)) {
			LatinMy_Keybord imgtypingbord = getKeyboard(TYPING_IMGVIEWID);
			exitebode = TYPING_IMGVIEWID;
			intypingbord.setKeyboard(imgtypingbord);
			imgtypingbord.enableShiftLock();
			imgtypingbord.setShiftState(Keyboard.SHIFT_LOCKED);
			imgtypingbord.setImeOptions(LATINMESETING.getResources(), mMode, imgselected);
		} else {
			LatinMy_Keybord latinmeimgview = getKeyboard(IMGVIEWID);
			exitebode = IMGVIEWID;
			intypingbord.setKeyboard(latinmeimgview);
			latinmeimgview.enableShiftLock();
			latinmeimgview.setShiftState(Keyboard.SHIFT_OFF);
			latinmeimgview.setImeOptions(LATINMESETING.getResources(), mMode, imgselected);
		}
	}

	public void onCancelInput() {
		if (ototextspling == AUTO_MODE_SWITCH_STATE_MOMENTARY && getPointerCount() == 1)
			LATINMESETING.changeKeyboardMode();
	}

	public void toggleSymbols() {
		setKeyboardMode(mMode, imgselected, soundview, !idimgview);
		if (idimgview && !refreneimg) {
			ototextspling = AUTO_MODE_SWITCH_STATE_SYMBOL_BEGIN;
		} else {
			ototextspling = AUTO_MODE_SWITCH_STATE_ALPHA;
		}
	}

	public boolean hasDistinctMultitouch() {
		return intypingbord != null && intypingbord.hasDistinctMultitouch();
	}

	public void setAutoModeSwitchStateMomentary() {
		ototextspling = AUTO_MODE_SWITCH_STATE_MOMENTARY;
	}

	public boolean isInMomentaryAutoModeSwitchState() {
		return ototextspling == AUTO_MODE_SWITCH_STATE_MOMENTARY;
	}

	public boolean isInChordingAutoModeSwitchState() {
		return ototextspling == AUTO_MODE_SWITCH_STATE_CHORDING;
	}

	public boolean isVibrateAndSoundFeedbackRequired() {
		return intypingbord != null && !intypingbord.isInSlidingKeyInput();
	}

	private int getPointerCount() {
		return intypingbord == null ? 0 : intypingbord.getPointerCount();
	}

	public void onKey(int chavi) {
		switch (ototextspling) {
		case AUTO_MODE_SWITCH_STATE_MOMENTARY:
			if (chavi == LatinMy_Keybord.KEYCODE_MODE_CHANGE) {
				if (idimgview) {
					ototextspling = AUTO_MODE_SWITCH_STATE_SYMBOL_BEGIN;
				} else {
					ototextspling = AUTO_MODE_SWITCH_STATE_ALPHA;
				}
			} else if (getPointerCount() == 1) {
				LATINMESETING.changeKeyboardMode();
			} else {
				ototextspling = AUTO_MODE_SWITCH_STATE_CHORDING;
			}
			break;
		case AUTO_MODE_SWITCH_STATE_SYMBOL_BEGIN:
			if (chavi != LatinIME.ASCII_SPACE && chavi != LatinIME.ASCII_ENTER && chavi >= 0) {
				ototextspling = AUTO_MODE_SWITCH_STATE_SYMBOL;
			}
			break;
		case AUTO_MODE_SWITCH_STATE_SYMBOL:
			if (chavi == LatinIME.ASCII_ENTER || chavi == LatinIME.ASCII_SPACE) {
				LATINMESETING.changeKeyboardMode();
			}
			break;
		}
	}

	public My_Keybord_Img_Key getInputView() {
		return intypingbord;
	}

	public void recreateInputView() {
		changeLatinKeyboardView(screenname, true);
	}

	public static void changeLatinKeyboardView(int screen, boolean forceReset) {
		if (screenname != screen || intypingbord == null || forceReset) {
			if (intypingbord != null) {
				intypingbord.closing();
			}
			if (STYLESBORD.length <= screen) {
				screen = Integer.valueOf(ZERO_VALUE_SCREEN);
			}

			My_Keybord_Img_Untils.GCUtils.getInstance().reset();
			boolean background = true;
			for (int i = 0; i < My_Keybord_Img_Untils.GCUtils.GC_TRY_LOOP_MAX && background; ++i) {
				try {
					intypingbord = (My_Keybord_Img_Key) LATINMESETING.getLayoutInflater().inflate(STYLESBORD[screen], null);
					background = false;
				} catch (OutOfMemoryError errorr) {
					background = My_Keybord_Img_Untils.GCUtils.getInstance().tryGCOrWait(screenname + "," + screen, errorr);
				} catch (InflateException e) {
					background = My_Keybord_Img_Untils.GCUtils.getInstance().tryGCOrWait(screenname + "," + screen, e);
				}
			}
			My_Keybord_Img_Key typingbeground = (My_Keybord_Img_Key) intypingbord.findViewById(R.id.LatinkeyboardBaseView);
			typingbeground.setBackgroundResource(R.drawable.img_defa);
			File derectory = new File("/sdcard/KeyBoard_Custom/bg_keyboard.jpg");
			if (derectory.exists()) {
				Bitmap bitfactory = BitmapFactory.decodeFile(derectory.getAbsolutePath());
				Drawable imgvioewid = new BitmapDrawable(sInstance.LATINMESETING.getResources(), bitfactory);
				typingbeground.setBackgroundDrawable(imgvioewid);
			} else {
				typingbeground.setBackgroundResource(R.drawable.img_defa);
			}
			intypingbord.setExtensionLayoutResId(STYLESBORD[screen]);
			intypingbord.setOnKeyboardActionListener(LATINMESETING);
			intypingbord.setPadding(0, 0, 0, 0);
			screenname = screen;
		}
		LATINMESETING.mHandler.post(new Runnable() {
			public void run() {
				if (intypingbord != null) {
					LATINMESETING.setInputView(intypingbord);
				}
				LATINMESETING.updateInputViewShown();
			}
		});
	}

	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if (My_Keybord_Img_Service.PREF_SETTINGS_KEY.equals(key)) {
			updateSettingsKeyState(sharedPreferences);
			recreateInputView();
		}else {
			changeLatinKeyboardView(sharedPreferences.getInt("SET_THEMES", 0), true);
		}
	}

	public void onAutoCompletionStateChanged(boolean isAutoCompletion) {
		if (isAutoCompletion != otogenrtedtext) {
			My_Keybord_Img_Key typingview = getInputView();
			otogenrtedtext = isAutoCompletion;
			typingview.invalidateKey(((LatinMy_Keybord) typingview.getKeyboard()).onAutoCompletionStateChanged(isAutoCompletion));
		}
	}

	private void updateSettingsKeyState(SharedPreferences prefs) {
		Resources update = LATINMESETING.getResources();
		final String servises = prefs.getString(My_Keybord_Img_Service.PREF_SETTINGS_KEY, update.getString(DEFAULT_SETTINGS_KEY_MODE));
		if (servises.equals(update.getString(SETTINGS_KEY_MODE_ALWAYS_SHOW)) || (servises.equals(update.getString(SETTINGS_KEY_MODE_AUTO)))) {
			serviseschavi = true;
		} else {
			serviseschavi = false;
		}
	}
}
