
package com.keyboard.keyboardthemes;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.keyboard.keyboardthemes.R;

import java.util.ArrayList;
import java.util.List;



public class KeyTuttoraialBord implements OnTouchListener {
    
    private List<Bubble> keyybords = new ArrayList<Bubble>();
    private View tuttorayle;
    private LatinIME latine;
    private int[] arraya = new int[2];
    private static final int MESSEGE_VALUE = 0;
    private int intkeybordvalue;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message mesge) {
            switch (mesge.what) {
                case MESSEGE_VALUE:
                    Bubble object = (Bubble) mesge.obj;
                    object.show(arraya[0], arraya[1]);
                    break;
            }
        }
    };

    class Bubble {
        Drawable keybordbg;
        int x;
        int y;
        int width;
        int gravity;
        CharSequence text;
        boolean dismissOnTouch;
        boolean dismissOnClose;
        PopupWindow window;
        TextView textView;
        View inputView;
        
        Bubble(Context imgcont, View inputView, int bg, int intMA, int intNB, int textResource1, int textResource2) {
            keybordbg = imgcont.getResources().getDrawable(bg);
            x = intMA;
            y = intNB;
            width = (int) (inputView.getWidth() * 0.9);
            this.gravity = Gravity.TOP | Gravity.LEFT;
            text = new SpannableStringBuilder()
                .append(imgcont.getResources().getText(textResource1))
                .append("\n") 
                .append(imgcont.getResources().getText(textResource2));
            this.dismissOnTouch = true;
            this.dismissOnClose = false;
            this.inputView = inputView;
            window = new PopupWindow(imgcont);
            window.setBackgroundDrawable(null);
            LayoutInflater layout = (LayoutInflater) imgcont.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            textView = (TextView) layout.inflate(R.layout.keybord_text_style, null);
            textView.setBackgroundDrawable(keybordbg);
            textView.setText(text);
            window.setContentView(textView);
            window.setFocusable(false);
            window.setTouchable(true);
            window.setOutsideTouchable(false);
        }

        private int chooseSize(PopupWindow window, View parentView, CharSequence name, TextView tv) {
            int weth = tv.getPaddingLeft() + tv.getPaddingRight();
            int hight = tv.getPaddingTop() + tv.getPaddingBottom();
            int addad = width - weth;

            Layout screen = new StaticLayout(name, tv.getPaint(), addad, Layout.Alignment.ALIGN_NORMAL, 1, 0, true);
            float max = 0;
            for (int i = 0; i < screen.getLineCount(); i++) {
                max = Math.max(max, screen.getLineWidth(i));
            }
            window.setWidth(width);
            window.setHeight(hight + screen.getHeight());
            return screen.getHeight();
        }

        void show(int showm, int shown) {
            int nameht = chooseSize(window, inputView, text, textView);
            shown -= textView.getPaddingTop() + nameht;
            if (inputView.getVisibility() == View.VISIBLE && inputView.getWindowVisibility() == View.VISIBLE) {
                try {
                    if ((gravity & Gravity.BOTTOM) == Gravity.BOTTOM) shown -= window.getHeight();
                    if ((gravity & Gravity.RIGHT) == Gravity.RIGHT) showm -= window.getWidth();
                    textView.setOnTouchListener(new OnTouchListener() {
                        public boolean onTouch(View view, MotionEvent event) {
                            KeyTuttoraialBord.this.next();
                            return true;
                        }
                    });
                    window.showAtLocation(inputView, Gravity.NO_GRAVITY, x + showm, y + shown);
                } catch (Exception e) {
                }
            }
        }
        
        void hide() {
            if (window.isShowing()) {
                textView.setOnTouchListener(null);
                window.dismiss();
            }
        }
        
        boolean isShowing() {
            return window.isShowing();
        }
    }
    
    public KeyTuttoraialBord(LatinIME applatin, My_Keybord_Img_Key inputView) {
        Context appcont = inputView.getContext();
        latine = applatin;
        int inputWidth = inputView.getWidth();
        final int x = inputWidth / 20;
        Bubble stepA = new Bubble(appcont, inputView, R.drawable.dialog_bubble_step02, x, 0, R.string.tip_to_open_keyboard, R.string.touch_to_continue);
        keyybords.add(stepA);
        Bubble stepB = new Bubble(appcont, inputView,R.drawable.dialog_bubble_step02, x, 0, R.string.tip_to_view_accents, R.string.touch_to_continue);
        keyybords.add(stepB);
        Bubble stepC = new Bubble(appcont, inputView, R.drawable.dialog_bubble_step07, x, 0, R.string.tip_to_open_symbols, R.string.touch_to_continue);
        keyybords.add(stepC);
        Bubble stepD = new Bubble(appcont, inputView, R.drawable.dialog_bubble_step07, x, 0, R.string.tip_to_close_symbols, R.string.touch_to_continue);
        keyybords.add(stepD);
        Bubble service = new Bubble(appcont, inputView, R.drawable.dialog_bubble_step07, x, 0, R.string.tip_to_launch_settings, R.string.touch_to_continue);
        keyybords.add(service);
        Bubble steptrue = new Bubble(appcont, inputView, R.drawable.dialog_bubble_step02, x, 0, R.string.tip_to_start_typing, R.string.touch_to_finish);
        keyybords.add(steptrue);
        tuttorayle = inputView;
    }
    
    void start() {
        tuttorayle.getLocationInWindow(arraya);
        intkeybordvalue = -1;
        tuttorayle.setOnTouchListener(this);
        next();
    }

    boolean next() {
        if (intkeybordvalue >= 0) {
            if (!keyybords.get(intkeybordvalue).isShowing()) {
                return true;
            }
            for (int i = 0; i <= intkeybordvalue; i++) {
                keyybords.get(i).hide();
            }
        }
        intkeybordvalue++;
        if (intkeybordvalue >= keyybords.size()) {
            tuttorayle.setOnTouchListener(null);
            latine.sendDownUpKeyEvents(-1);
            latine.tutorialDone();
            return false;
        }
        if (intkeybordvalue == 3 || intkeybordvalue == 4) {
            latine.mytypingword.toggleSymbols();
        }
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MESSEGE_VALUE, keyybords.get(intkeybordvalue)), 500);
        return true;
    }
    
    void hide() {
        for (int i = 0; i < keyybords.size(); i++) {
            keyybords.get(i).hide();
        }
        tuttorayle.setOnTouchListener(null);
    }

    boolean close() {
        mHandler.removeMessages(MESSEGE_VALUE);
        hide();
        return true;
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            next();
        }
        return true;
    }
}
