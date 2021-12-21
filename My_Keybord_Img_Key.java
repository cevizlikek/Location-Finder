
package com.keyboard.keyboardthemes;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

import com.keyboard.keyboardthemes.Keyboard.Key;
import com.keyboard.keyboardthemes.R;


public class My_Keybord_Img_Key extends My_Keybord_BaseImg_View {
	static final int KEYCODE_FKEY_F11 = -141;
	static final int KEYCODE_FKEY_F10 = -140;
	static final int KEYCODE_FKEY_F12 = -142;

	static final String TAG = "HK/LatinKeyboardView";
	static final int KEYCODE_VOICE = -102;
	static final int KEYCODE_COMPOSE = -10024;
	static final int KEYCODE_DPAD_UP = -19;
	static final int KEYCODE_DPAD_DOWN = -20;
	static final int KEYCODE_PAGE_DOWN = -93;
	static final int KEYCODE_ESCAPE = -111;
	static final int KEYCODE_FKEY_F1 = -131;
	static final int KEYCODE_OPTIONS = -100;
	static final int KEYCODE_FKEY_F2 = -132;
	static final int KEYCODE_FORWARD_DEL = -112;
	static final int KEYCODE_CTRL_LEFT = -113;
	static final int KEYCODE_CAPS_LOCK = -115;
	static final int KEYCODE_SCROLL_LOCK = -116;
	static final int KEYCODE_OPTIONS_LONGPRESS = -101;
	static final int KEYCODE_FKEY_F7 = -137;
	static final int KEYCODE_META_LEFT = -117;
	static final int KEYCODE_NUM_LOCK = -143;
	static final int KEYCODE_DPAD_LEFT = -21;
	static final int KEYCODE_FKEY_F5 = -135;
	static final int KEYCODE_FN = -119;
	static final int KEYCODE_SYSRQ = -120;
	static final int KEYCODE_FKEY_F6 = -136;
	static final int KEYCODE_BREAK = -121;
	static final int KEYCODE_HOME = -122;
	static final int KEYCODE_END = -123;
	static final int KEYCODE_F1 = -103;
	static final int KEYCODE_NEXT_LANGUAGE = -104;
	static final int KEYCODE_INSERT = -124;
	static final int KEYCODE_FKEY_F3 = -133;
	static final int KEYCODE_FKEY_F8 = -138;
	static final int KEYCODE_FKEY_F4 = -134;
	static final int KEYCODE_PREV_LANGUAGE = -105;
	static final int KEYCODE_FKEY_F9 = -139;
	static final int KEYCODE_DPAD_RIGHT = -22;
	static final int KEYCODE_DPAD_CENTER = -23;
	static final int KEYCODE_ALT_LEFT = -57;
	static final int KEYCODE_PAGE_UP = -92;
    static final boolean DEBUG_AUTO_PLAY = false;
    static final boolean DEBUG_LINE = false;
    private static final int MSG_TOUCH_DOWN = 1;
    private static final int MSG_TOUCH_UP = 2;

    Handler mHandler2;

    private String leteron;
    private int intkeybordtext;
    private boolean endtextleter;
    private Key[] masciikeys = new Key[256];
    private boolean textleterstart;
    private int endAvalue;
    private int endBvalue;
    private Paint color;

	private Keyboard mobilekey;
	private boolean bolenmobile;
	private My_Keybord_Img_Key imgkeyword;
	private PopupWindow positionscreen;
	private boolean screenstyle;
	private boolean onevalueev;
	private boolean removeimgvalue;
	private boolean hidevaluekeybord;
	private int maximumvalue = Integer.MAX_VALUE;
	private int endvartcalA;
	private int zerovalueint = 0;
	private LatinMy_Keybord latinewords;

	public My_Keybord_Img_Key(Context mcont, AttributeSet matrs) {
		this(mcont, matrs, 0);
	}

	public My_Keybord_Img_Key(Context mcont, AttributeSet matrs, int defult) {
		super(mcont, matrs, defult);

		// TODO(klausw): migrate attribute styles to LatinKeyboardView?
		TypedArray type = mcont.obtainStyledAttributes(matrs, R.styleable.My_Keybord_BaseImg_View, defult, R.style.LatinKeyboardBaseView);
		LayoutInflater layoutscreen = (LayoutInflater) mcont.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		int previewLayout = 0;
		int n = type.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = type.getIndex(i);

			switch (attr) {
			case R.styleable.My_Keybord_BaseImg_View_keyPreviewLayout:
				previewLayout = type.getResourceId(attr, 0);
				if (previewLayout == R.layout.empty_activity)
					previewLayout = 0;
				break;

			case R.styleable.My_Keybord_BaseImg_View_keyPreviewOffset:
				before_effect = type.getDimensionPixelOffset(attr, 0);
				break;

			case R.styleable.My_Keybord_BaseImg_View_keyPreviewHeight:
				beforehit = type.getDimensionPixelSize(attr, 80);
				break;

			case R.styleable.My_Keybord_BaseImg_View_popupLayout:
				intscreen = type.getResourceId(attr, 0);
				if (intscreen == R.layout.empty_activity)
					intscreen = 0;
				break;
			}
		}

		final Resources ridvalue = getResources();
		if (previewLayout != 0) {
			mPreviewPopup = new PopupWindow(mcont);
			Log.i(TAG, "new mPreviewPopup " + mPreviewPopup + " from " + this);
			beforelater = (TextView) layoutscreen.inflate(previewLayout, null);
			beforenamelenth = (int) ridvalue
					.getDimension(R.dimen.key_preview_text_size_large);
			mPreviewPopup.setContentView(beforelater);
			mPreviewPopup.setBackgroundDrawable(null);
			mPreviewPopup.setTouchable(false);
			mPreviewPopup.setAnimationStyle(R.style.KeyPreviewAnimation);
		} else {
			beforeshedow = false;
		}

		if (intscreen != 0) {
			shortkeybase = this;
			mMiniKeyboardPopup = new PopupWindow(mcont);
			Log.i(TAG, "new mMiniKeyboardPopup " + mMiniKeyboardPopup + " from " + this);
			mMiniKeyboardPopup.setBackgroundDrawable(null);
			mMiniKeyboardPopup.setAnimationStyle(R.style.MiniKeyboardAnimation);
			shortvaisablevalue = false;
		}

	}

	public void setPhoneKeyboard(Keyboard phoneKeyboard) {
		mobilekey = phoneKeyboard;
	}

	public void setExtensionLayoutResId(int id) {
		zerovalueint = id;
	}

	@Override
	public void setPreviewEnabled(boolean previewEnabled) {
		if (getKeyboard() == mobilekey) {
			super.setPreviewEnabled(false);
		} else {
			super.setPreviewEnabled(previewEnabled);
		}
	}

	@Override
	public void setKeyboard(Keyboard newKeyboard) {
		final Keyboard junubord = getKeyboard();
		if (junubord instanceof LatinMy_Keybord) {
			((LatinMy_Keybord) junubord).keyReleased();
		}
		super.setKeyboard(newKeyboard);
		maximumvalue = newKeyboard.getMinWidth() / 7;
		maximumvalue *= maximumvalue;
		int numRows = newKeyboard.intraownum;
		endvartcalA = (newKeyboard.getHeight() * (numRows - 1)) / numRows;
		latinewords = ((LatinMy_Keybord) newKeyboard).getExtension();
		if (latinewords != null && imgkeyword != null)
			imgkeyword.setKeyboard(latinewords);
		setKeyboardLocal(newKeyboard);
	}

	@Override
boolean enableSlideKeyHack() {
		return true;
	}

	@Override
	protected boolean onLongPress(Key key) {
		My_Keybord_Img_Treker.clearSlideKeys();

		int primaryCode = key.codes[0];
		if (primaryCode == KEYCODE_OPTIONS) {
			return invokeOnKey(KEYCODE_OPTIONS_LONGPRESS);
		} else if (primaryCode == KEYCODE_DPAD_CENTER) {
			return invokeOnKey(KEYCODE_COMPOSE);
		} else if (primaryCode == '0' && getKeyboard() == mobilekey) {
			return invokeOnKey('+');
		} else {
			return super.onLongPress(key);
		}
	}

	private boolean invokeOnKey(int primaryCode) {
		getOnKeyboardActionListener().onKey(primaryCode, null, NOT_A_TOUCH_COORDINATE, NOT_A_TOUCH_COORDINATE);
		return true;
	}

	private boolean handleSuddenJump(MotionEvent eventbord) {
		final int action = eventbord.getAction();
		final int x = (int) eventbord.getX();
		final int y = (int) eventbord.getY();
		boolean result = false;

		if (eventbord.getPointerCount() > 1) {
			hidevaluekeybord = true;
		}
		if (hidevaluekeybord) {
			if (action == MotionEvent.ACTION_UP)
				hidevaluekeybord = false;
			return false;
		}

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			removeimgvalue = false;
			hidevaluekeybord = false;
			break;
		case MotionEvent.ACTION_MOVE:
			final int distanceSquare = (endAvalue - x) * (endAvalue - x) + (endBvalue - y) * (endBvalue - y);

			if (distanceSquare > maximumvalue && (endBvalue < endvartcalA || y < endvartcalA)) {
				if (!removeimgvalue) {
					removeimgvalue = true;
					MotionEvent bhasha = MotionEvent.obtain(eventbord.getEventTime(), eventbord.getEventTime(), MotionEvent.ACTION_UP, endAvalue, endBvalue, eventbord.getMetaState());
					super.onTouchEvent(bhasha);
					bhasha.recycle();
				}
				result = true;
			} else if (removeimgvalue) {
				result = true;
			}
			break;
		case MotionEvent.ACTION_UP:
			if (removeimgvalue) {
				MotionEvent bhasha = MotionEvent.obtain(eventbord.getEventTime(), eventbord.getEventTime(), MotionEvent.ACTION_DOWN, x, y, eventbord.getMetaState());
				super.onTouchEvent(bhasha);
				bhasha.recycle();
				removeimgvalue = false;
			}
			break;
		}
		endAvalue = x;
		endBvalue = y;
		return result;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		LatinMy_Keybord keyboard = (LatinMy_Keybord) getKeyboard();
		if (LatinIME.service.clickview || DEBUG_LINE) {
			endAvalue = (int) event.getX();
			endBvalue = (int) event.getY();
			invalidate();
		}
		if (!bolenmobile && !screenstyle && handleSuddenJump(event))
			return true;
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			keyboard.keyReleased();
		}

		if (event.getAction() == MotionEvent.ACTION_UP) {
			int languageDirection = keyboard.getLanguageChangeDirection();
			if (languageDirection != 0) {
				getOnKeyboardActionListener().onKey(languageDirection == 1 ? KEYCODE_NEXT_LANGUAGE : KEYCODE_PREV_LANGUAGE, null, endAvalue, endBvalue);
				event.setAction(MotionEvent.ACTION_CANCEL);
				keyboard.keyReleased();
				return super.onTouchEvent(event);
			}
		}

		if (keyboard.getExtension() == null) {
			return super.onTouchEvent(event);
		}
		if (event.getY() < 0 && (bolenmobile || event.getAction() != MotionEvent.ACTION_UP)) {
			if (bolenmobile) {
				int action = event.getAction();
				if (onevalueev) action = MotionEvent.ACTION_DOWN;
				onevalueev = false;
				MotionEvent translated = MotionEvent.obtain(event.getEventTime(), event.getEventTime(), action, event.getX(), event.getY() + imgkeyword.getHeight(), event.getMetaState());
				if (event.getActionIndex() > 0)
				    return true;
				boolean result = imgkeyword.onTouchEvent(translated);
				translated.recycle();
				if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
					closeExtension();
				}
				return result;
			} else {
				if (swipeUp()) {
					return true;
				} else if (openExtension()) {
					MotionEvent close = MotionEvent.obtain(event.getDownTime(), event.getEventTime(), MotionEvent.ACTION_CANCEL, event.getX() - 100, event.getY() - 100, 0);
					super.onTouchEvent(close);
					close.recycle();
					if (imgkeyword.getHeight() > 0) {
						MotionEvent bhasha = MotionEvent.obtain(event.getEventTime(), event.getEventTime(), MotionEvent.ACTION_DOWN, event.getX(), event.getY() + imgkeyword.getHeight(), event.getMetaState());
						imgkeyword.onTouchEvent(bhasha);
						bhasha.recycle();
					} else {
						onevalueev = true;
					}
					hidevaluekeybord = true;
				}
				return true;
			}
		} else if (bolenmobile) {
			closeExtension();
			MotionEvent end = MotionEvent.obtain(event.getEventTime(), event.getEventTime(), MotionEvent.ACTION_DOWN, event.getX(), event.getY(), event.getMetaState());
			super.onTouchEvent(end, true);
			end.recycle();
			return super.onTouchEvent(event);
		} else {
			return super.onTouchEvent(event);
		}
	}

	private void setExtensionType(boolean isExtensionType) {
		screenstyle = isExtensionType;
	}

	private boolean openExtension() {
		if (!isShown() || popupKeyboardIsShowing()) {
			return false;
		}
		My_Keybord_Img_Treker.clearSlideKeys();
		if (((LatinMy_Keybord) getKeyboard()).getExtension() == null)
			return false;
		makePopupWindow();
		bolenmobile = true;
		return true;
	}

	private void makePopupWindow() {
		dismissPopupKeyboard();
		if (positionscreen == null) {
			int[] windowLocation = new int[2];
			positionscreen = new PopupWindow(getContext());
			positionscreen.setBackgroundDrawable(null);
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			imgkeyword = (My_Keybord_Img_Key) inflater.inflate(zerovalueint == 0 ? R.layout.text_style_trans_keybord : zerovalueint, null);
			Keyboard latinwords = latinewords;
			imgkeyword.setKeyboard(latinwords);
			imgkeyword.setExtensionType(true);
			imgkeyword.setPadding(0, 0, 0, 0);
			imgkeyword.setOnKeyboardActionListener(new ExtensionKeyboardListener(getOnKeyboardActionListener()));
			imgkeyword.setPopupParent(this);
			imgkeyword.setPopupOffset(0, -windowLocation[1]);
			positionscreen.setContentView(imgkeyword);
			positionscreen.setWidth(getWidth());
			positionscreen.setHeight(latinwords.getHeight());
			positionscreen.setAnimationStyle(-1);
			getLocationInWindow(windowLocation);
			// TODO: Fix the "- 30".
			imgkeyword.setPopupOffset(0, -windowLocation[1] - 30);
			positionscreen.showAtLocation(this, 0, 0, -latinwords.getHeight() + windowLocation[1] + this.getPaddingTop());
		} else {
			imgkeyword.setVisibility(VISIBLE);
		}
		imgkeyword.setShiftState(getShiftState());
	}

	@Override
	public void closing() {
		super.closing();
		if (positionscreen != null && positionscreen.isShowing()) {
			positionscreen.dismiss();
			positionscreen = null;
		}
	}

	private void closeExtension() {
		imgkeyword.closing();
		imgkeyword.setVisibility(INVISIBLE);
		bolenmobile = false;
	}

	private static class ExtensionKeyboardListener implements OnKeyboardActionListener {
		private OnKeyboardActionListener traker;

		ExtensionKeyboardListener(OnKeyboardActionListener target) {
			traker = target;
		}
		public void onKey(int primaryCode, int[] keyCodes, int x, int y) {
			traker.onKey(primaryCode, keyCodes, x, y);
		}

		public void onPress(int primaryCode) {
			traker.onPress(primaryCode);
		}

		public void onRelease(int primaryCode) {
			traker.onRelease(primaryCode);
		}

		public void onText(CharSequence text) {
			traker.onText(text);
		}

		public void onCancel() {
			traker.onCancel();
		}

		public boolean swipeDown() {
			return true;
		}

		public boolean swipeLeft() {
			return true;
		}

		public boolean swipeRight() {
			return true;
		}

		public boolean swipeUp() {
			return true;
		}
	}


	private void setKeyboardLocal(Keyboard bord) {
		if (DEBUG_AUTO_PLAY) {
			findKeys();
			if (mHandler2 == null) {
				mHandler2 = new Handler() {
					@Override
					public void handleMessage(Message msg) {
						removeMessages(MSG_TOUCH_DOWN);
						removeMessages(MSG_TOUCH_UP);
						if (textleterstart == false)
							return;

						switch (msg.what) {

						    case MSG_TOUCH_DOWN:
							if (intkeybordtext >= leteron.length()) {
								textleterstart = false;
								return;
							}
							char name = leteron.charAt(intkeybordtext);
							while (name > 255 || masciikeys[name] == null) {
								intkeybordtext++;
								if (intkeybordtext >= leteron.length()) {
									textleterstart = false;
									return;
								}
								name = leteron.charAt(intkeybordtext);
							}
							int x = masciikeys[name].x + 10;
							int y = masciikeys[name].y + 26;
							MotionEvent me = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, x, y, 0);
							My_Keybord_Img_Key.this.dispatchTouchEvent(me);
							me.recycle();
							sendEmptyMessageDelayed(MSG_TOUCH_UP, 500);
							endtextleter = true;
							break;

						case MSG_TOUCH_UP:
							char cUp = leteron.charAt(intkeybordtext);
							int x2 = masciikeys[cUp].x + 10;
							int y2 = masciikeys[cUp].y + 26;
							intkeybordtext++;

							MotionEvent motivation = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, x2, y2, 0);
							My_Keybord_Img_Key.this.dispatchTouchEvent(motivation);
							motivation.recycle();
							sendEmptyMessageDelayed(MSG_TOUCH_DOWN, 500);
							endtextleter = false;
							break;
						}
					}
				};

			}
		}
	}

	private void findKeys() {
		List<Key> chavi = getKeyboard().getKeys();
		for (int i = 0; i < chavi.size(); i++) {
			int code = chavi.get(i).codes[0];
			if (code >= 0 && code <= 255) {
				masciikeys[code] = chavi.get(i);
			}
		}
	}

	public void startPlaying(String plying) {
		if (DEBUG_AUTO_PLAY) {
			if (plying == null)
				return;
			leteron = plying.toLowerCase();
			textleterstart = true;
			endtextleter = false;
			intkeybordtext = 0;
			mHandler2.sendEmptyMessageDelayed(MSG_TOUCH_DOWN, 10);
		}
	}

	@Override
	public void draw(Canvas img) {
		My_Keybord_Img_Untils.GCUtils.getInstance().reset();
		boolean loop = true;
		for (int i = 0; i < My_Keybord_Img_Untils.GCUtils.GC_TRY_LOOP_MAX && loop; ++i) {
			try {
				super.draw(img);
				loop = false;
			} catch (OutOfMemoryError e) {
				loop = My_Keybord_Img_Untils.GCUtils.getInstance().tryGCOrWait("LatinKeyboardView", e);
			}
		}
		if (DEBUG_AUTO_PLAY) {
			if (textleterstart) {
				mHandler2.removeMessages(MSG_TOUCH_DOWN);
				mHandler2.removeMessages(MSG_TOUCH_UP);
				if (endtextleter) {
					mHandler2.sendEmptyMessageDelayed(MSG_TOUCH_UP, 20);
				} else {
					mHandler2.sendEmptyMessageDelayed(MSG_TOUCH_DOWN, 20);
				}
			}
		}
		if (LatinIME.service.clickview || DEBUG_LINE) {
			if (color == null) {
				color = new Paint();
				color.setColor(0x80FFFFFF);
				color.setAntiAlias(false);
			}
			img.drawLine(endAvalue, 0, endAvalue, getHeight(), color);
			img.drawLine(0, endBvalue, getWidth(), endBvalue, color);
		}
	}
}
