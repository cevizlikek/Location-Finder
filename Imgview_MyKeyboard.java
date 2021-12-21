package com.keyboard.keyboardthemes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.keyboard.keyboardthemes.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class Imgview_MyKeyboard extends View {

    private static final int img_country = -1;
    private LatinIME latinsrting;
    private final ArrayList<CharSequence> chararraylist = new ArrayList<CharSequence>();
    private boolean imgcomplite;
    private CharSequence charseqenceimg;
    private static final int click = -1;
    private int chioseindex;
    private int mTouchX = click;
    private final Drawable choise_imgdrawable;
    private boolean itemcountryid;
    private boolean img_review;
    private Rect rectbg;
    private final TextView beforename;
    private final PopupWindow beforegroup;
    private int intcountryidname;
    private Drawable drawebaleimgbg;
    private static final int sugetionimgvalue = 32;
    private static final int scoorallvalue = 20;
    private final int[] countrysize_w = new int[sugetionimgvalue];
    private final int[] countryA = new int[sugetionimgvalue];
    private int beforevalueA;
    private int beforevalueB;
    private static final int space = 10;
    private final int roungnorml;
    private final int recomendrung;
    private final int runganother;
    private final Paint colorpaint;
    private final int imgdeseent;
    private boolean bollensorl;
    private boolean addimgspeling;
    private CharSequence addimgsplinghint;
    private int targtetvalue;
    private final int clicksizeW;
    private int allladd_w;
    private final GestureDetector imgdercotry;

    public Imgview_MyKeyboard(Context imgncont, AttributeSet atrsset) {
        super(imgncont, atrsset);
        choise_imgdrawable = imgncont.getResources().getDrawable(R.drawable.list_selector_background_pressed);
        LayoutInflater inflate = (LayoutInflater) imgncont.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resources resourse = imgncont.getResources();
        beforegroup = new PopupWindow(imgncont);
        beforename = (TextView) inflate.inflate(R.layout.keybode_btn_demo, null);
        beforegroup.setWindowLayoutMode(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        beforegroup.setContentView(beforename);
        beforegroup.setBackgroundDrawable(null);
        beforegroup.setAnimationStyle(R.style.KeyPreviewAnimation);
        roungnorml = resourse.getColor(R.color.candidate_normal);
        recomendrung = resourse.getColor(R.color.candidate_recommended);
        runganother = resourse.getColor(R.color.candidate_other);
        drawebaleimgbg = resourse.getDrawable(R.drawable.keyboard_suggest_strip_divider);
        addimgsplinghint = resourse.getString(R.string.hint_add_to_dictionary);

        colorpaint = new Paint();
        colorpaint.setColor(roungnorml);
        colorpaint.setAntiAlias(true);
        colorpaint.setTextSize(beforename.getTextSize() * LatinIME.service.symbolekey);
        colorpaint.setStrokeWidth(0);
        colorpaint.setTextAlign(Align.CENTER);
        imgdeseent = (int) colorpaint.descent();
        clicksizeW = (int)resourse.getDimension(R.dimen.candidate_min_touchable_width);
        
        imgdercotry = new GestureDetector(new CandidateStripGestureListener(clicksizeW));
        setWillNotDraw(false);
        setHorizontalScrollBarEnabled(false);
        setVerticalScrollBarEnabled(false);
        scrollTo(0, getScrollY());
    }

    private class CandidateStripGestureListener extends GestureDetector.SimpleOnGestureListener {
        private final int mTouchSlopSquare;

        public CandidateStripGestureListener(int touchSlop) {
            mTouchSlopSquare = touchSlop * touchSlop;
        }

        @Override
        public void onLongPress(MotionEvent event) {
            if (chararraylist.size() > 0) {
                if (event.getX() + getScrollX() < countrysize_w[0] && getScrollX() < 10) {
                    longPressFirstWord();
                }
            }
        }

        @Override
        public boolean onDown(MotionEvent event) {
            bollensorl = false;
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                float distanceX, float distanceY) {
            if (!bollensorl) {
                final int deltaX = (int) (e2.getX() - e1.getX());
                final int deltaY = (int) (e2.getY() - e1.getY());
                final int distance = (deltaX * deltaX) + (deltaY * deltaY);
                if (distance < mTouchSlopSquare) {
                    return true;
                }
                bollensorl = true;
            }

            final int width = getWidth();
            bollensorl = true;
            int scrollX = getScrollX();
            scrollX += (int) distanceX;
            if (scrollX < 0) {
                scrollX = 0;
            }
            if (distanceX > 0 && scrollX + width > allladd_w) {
                scrollX -= (int) distanceX;
            }
            targtetvalue = scrollX;
            scrollTo(scrollX, getScrollY());
            hidePreview();
            invalidate();
            return true;
        }
    }

    public void setService(LatinIME listener) {
        latinsrting = listener;
    }
    
    @Override
    public int computeHorizontalScrollRange() {
        return allladd_w;
    }

    @Override
    protected void onDraw(Canvas draw) {
        if (draw != null) {
            super.onDraw(draw);
        }
        allladd_w = 0;
        
        final int height = getHeight();
        if (rectbg == null) {
            rectbg = new Rect(0, 0, 0, 0);
            if (getBackground() != null) {
                getBackground().getPadding(rectbg);
            }
            drawebaleimgbg.setBounds(0, 0, drawebaleimgbg.getIntrinsicWidth(),
                    drawebaleimgbg.getIntrinsicHeight());
        }

        final int count = chararraylist.size();
        final Rect rectbegr = rectbg;
        final Paint color = colorpaint;
        final int inttuch = mTouchX;
        final int scrollX = getScrollX();
        final boolean boollen = bollensorl;
        final boolean itemcountry = itemcountryid;
        final int y = (int) (height + colorpaint.getTextSize() - imgdeseent) / 2;

        boolean existsAutoCompletion = false;

        int x = 0;
        for (int i = 0; i < count; i++) {
            CharSequence seqench = chararraylist.get(i);
            if (seqench == null) continue;
            final int wordLength = seqench.length();

            color.setColor(roungnorml);
            if (img_review && ((i == 1 && !itemcountry) || (i == 0 && itemcountry))) {
                color.setTypeface(Typeface.DEFAULT_BOLD);
                color.setColor(recomendrung);
                existsAutoCompletion = true;
            } else if (i != 0 || (wordLength == 1 && count > 1)) {
                color.setColor(runganother);
            }
            int wordWidth;
            if ((wordWidth = countrysize_w[i]) == 0) {
                float textWidth =  color.measureText(seqench, 0, wordLength);
                wordWidth = Math.max(clicksizeW, (int) textWidth + space * 2);
                countrysize_w[i] = wordWidth;
            }

            countryA[i] = x;

            if (inttuch != click && !boollen && inttuch + scrollX >= x && inttuch + scrollX < x + wordWidth) {
                if (draw != null && !addimgspeling) {
                    draw.translate(x, 0);
                    choise_imgdrawable.setBounds(0, rectbegr.top, wordWidth, height);
                    choise_imgdrawable.draw(draw);
                    draw.translate(-x, 0);
                }
                charseqenceimg = seqench;
                chioseindex = i;
            }

            if (draw != null) {
                draw.drawText(seqench, 0, wordLength, x + wordWidth / 2, y, color);
                color.setColor(runganother);
                draw.translate(x + wordWidth, 0);
                if (!(addimgspeling && i == 1)) {
                    drawebaleimgbg.draw(draw);
                }
                draw.translate(-x - wordWidth, 0);
            }
            color.setTypeface(Typeface.DEFAULT);
            x += wordWidth;
        }
        latinsrting.onAutoCompletionStateChanged(existsAutoCompletion);
        allladd_w = x;
        if (targtetvalue != scrollX) {
            scrollToTarget();
        }
    }
    
    private void scrollToTarget() {
        int target = getScrollX();
        if (targtetvalue > target) {
            target += scoorallvalue;
            if (target >= targtetvalue) {
                target = targtetvalue;
                scrollTo(target, getScrollY());
                requestLayout();
            } else {
                scrollTo(target, getScrollY());
            }
        } else {
            target -= scoorallvalue;
            if (target <= targtetvalue) {
                target = targtetvalue;
                scrollTo(target, getScrollY());
                requestLayout();
            } else {
                scrollTo(target, getScrollY());
            }
        }
        invalidate();
    }
    
    @SuppressLint("WrongCall")
	public void setSuggestions(List<CharSequence> suggestions, boolean complite, boolean countryset, boolean haveMinimalSuggestion) {
        clear();
        if (suggestions != null) {
            int insertCount = Math.min(suggestions.size(), sugetionimgvalue);
            for (CharSequence suggestion : suggestions) {
                chararraylist.add(suggestion);
                if (--insertCount == 0)
                    break;
            }
        }
        imgcomplite = complite;
        itemcountryid = countryset;
        scrollTo(0, getScrollY());
        targtetvalue = 0;
        img_review = haveMinimalSuggestion;
        onDraw(null);
        invalidate();
        requestLayout();
    }

    public boolean isShowingAddToDictionaryHint() {
        return addimgspeling;
    }

    public void showAddToDictionaryHint(CharSequence hint) {
        ArrayList<CharSequence> suggestions = new ArrayList<CharSequence>();
        suggestions.add(hint);
        suggestions.add(addimgsplinghint);
        setSuggestions(suggestions, false, false, false);
        addimgspeling = true;
    }

    public boolean dismissAddToDictionaryHint() {
        if (!addimgspeling) return false;
        clear();
        return true;
    }

    List<CharSequence> getSuggestions() {
        return chararraylist;
    }

    public void clear() {
        chararraylist.clear();
        mTouchX = click;
        charseqenceimg = null;
        chioseindex = -1;
        addimgspeling = false;
        invalidate();
        Arrays.fill(countrysize_w, 0);
        Arrays.fill(countryA, 0);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent me) {

        if (imgdercotry.onTouchEvent(me)) {
            return true;
        }

        int action = me.getAction();
        int x = (int) me.getX();
        int y = (int) me.getY();
        mTouchX = x;

        switch (action) {
        case MotionEvent.ACTION_DOWN:
            invalidate();
            break;

        case MotionEvent.ACTION_MOVE:
            if (y <= 0) {
                if (charseqenceimg != null) {
                    if (!imgcomplite) {
                    }
                    latinsrting.pickSuggestionManually(chioseindex, charseqenceimg);
                    charseqenceimg = null;
                    chioseindex = -1;
                }
            }
            break;
        case MotionEvent.ACTION_UP:
            if (!bollensorl) {
                if (charseqenceimg != null) {
                    if (addimgspeling) {
                        longPressFirstWord();
                        clear();
                    } else {
                        if (!imgcomplite) {
                        }
                        latinsrting.pickSuggestionManually(chioseindex, charseqenceimg);
                    }
                }
            }
            charseqenceimg = null;
            chioseindex = -1;
            requestLayout();
            hidePreview();
            invalidate();
            break;
        }
        return true;
    }

    private void hidePreview() {
        mTouchX = click;
        intcountryidname = img_country;
        beforegroup.dismiss();
    }
    
    private void showPreview(int wordIndex, String altText) {
        int oldWordIndex = intcountryidname;
        intcountryidname = wordIndex;
        if (oldWordIndex != intcountryidname || altText != null) {
            if (wordIndex == img_country) {
                hidePreview();
            } else {
                CharSequence seqence = altText != null? altText : chararraylist.get(wordIndex);
                beforename.setText(seqence);
                beforename.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                int wordWidth = (int) (colorpaint.measureText(seqence, 0, seqence.length()) + space * 2);
                final int popupWidth = wordWidth + beforename.getPaddingLeft() + beforename.getPaddingRight();
                final int popupHeight = beforename.getMeasuredHeight();
                beforevalueA = countryA[wordIndex] - beforename.getPaddingLeft() - getScrollX() + (countrysize_w[wordIndex] - wordWidth) / 2;
                beforevalueB = - popupHeight;
                int [] offsetInWindow = new int[2];
                getLocationInWindow(offsetInWindow);
                if (beforegroup.isShowing()) {
                    beforegroup.update(beforevalueA, beforevalueB + offsetInWindow[1], popupWidth, popupHeight);
                } else {
                    beforegroup.setWidth(popupWidth);
                    beforegroup.setHeight(popupHeight);
                    beforegroup.showAtLocation(this, Gravity.NO_GRAVITY, beforevalueA, beforevalueB + offsetInWindow[1]);
                }
                beforename.setVisibility(VISIBLE);
            }
        }
    }

    private void longPressFirstWord() {
        CharSequence seqence = chararraylist.get(0);
        if (seqence.length() < 2) return;
        if (latinsrting.addWordToDictionary(seqence.toString())) {
            showPreview(0, getContext().getResources().getString(R.string.added_word, seqence));
        }
    }
    
    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        hidePreview();
    }
}
