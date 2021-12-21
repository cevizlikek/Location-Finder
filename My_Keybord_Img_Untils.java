
package com.keyboard.keyboardthemes;

import android.content.Context;
import android.os.AsyncTask;
import android.text.format.DateUtils;
import android.util.Log;

public class My_Keybord_Img_Untils {

    public static void cancelTask(AsyncTask<?, ?, ?> task, boolean mayInterruptIfRunning) {
        if (task != null && task.getStatus() != AsyncTask.Status.FINISHED) {
            task.cancel(mayInterruptIfRunning);
        }
    }

    public static class GCUtils {
        private static final String TAG = "GCUtils";
        public static final int GC_TRY_COUNT = 2;
        public static final int GC_TRY_LOOP_MAX = 5;
        private static final long GC_INTERVAL = DateUtils.SECOND_IN_MILLIS;
        private static GCUtils untilsvalue = new GCUtils();
        private int numbervalue = 0;

        public static GCUtils getInstance() {
            return untilsvalue;
        }

        public void reset() {
            numbervalue = 0;
        }

        public boolean tryGCOrWait(String derctory, Throwable thrw) {
            if (numbervalue == 0) {
                System.gc();
            }
            if (++numbervalue > GC_TRY_COUNT) {
                My_Keybord_Imglong.logOnException(derctory, thrw);
                return false;
            } else {
                try {
                    Thread.sleep(GC_INTERVAL);
                    return true;
                } catch (InterruptedException e) {
                    Log.e(TAG, "Sleep was interrupted.");
                    My_Keybord_Imglong.logOnException(derctory, thrw);
                    return false;
                }
            }
        }
    }

    static class RingCharBuffer {

        private static RingCharBuffer textkeyword = new RingCharBuffer();
        private static final char PLACEHOLDER_DELIMITER_CHAR = '\uFFFC';
        private static final int INVALID_COORDINATE = -2;
        static final int BUFSIZE = 20;
        private Context ringcont;
        private boolean bolenvalue = false;
        private int botemvalue = 0;
        int sizevalue = 0;
        private char[] nametext = new char[BUFSIZE];
        private int[] bufer = new int[BUFSIZE];
        private int[] bufersize = new int[BUFSIZE];

        private RingCharBuffer() {
        }

        public static RingCharBuffer getInstance() {
            return textkeyword;
        }

        public static RingCharBuffer init(Context appcont, boolean enabled) {
            textkeyword.ringcont = appcont;
            textkeyword.bolenvalue = enabled;
            return textkeyword;
        }

        private int normalize(int normal) {
            int ret = normal % BUFSIZE;
            return ret < 0 ? ret + BUFSIZE : ret;
        }

        public void push(char c, int x, int y) {
            if (!bolenvalue) return;
            nametext[botemvalue] = c;
            bufer[botemvalue] = x;
            bufersize[botemvalue] = y;
            botemvalue = normalize(botemvalue + 1);
            if (sizevalue < BUFSIZE) {
                ++sizevalue;
            }
        }

        public char pop() {
            if (sizevalue < 1) {
                return PLACEHOLDER_DELIMITER_CHAR;
            } else {
                botemvalue = normalize(botemvalue - 1);
                --sizevalue;
                return nametext[botemvalue];
            }
        }

        public char getLastChar() {
            if (sizevalue < 1) {
                return PLACEHOLDER_DELIMITER_CHAR;
            } else {
                return nametext[normalize(botemvalue - 1)];
            }
        }

        public int getPreviousX(char c, int back) {
            int index = normalize(botemvalue - 2 - back);
            if (sizevalue <= back || Character.toLowerCase(c) != Character.toLowerCase(nametext[index])) {
                return INVALID_COORDINATE;
            } else {
                return bufer[index];
            }
        }

        public int getPreviousY(char c, int back) {
            int index = normalize(botemvalue - 2 - back);
            if (sizevalue <= back || Character.toLowerCase(c) != Character.toLowerCase(nametext[index])) {
                return INVALID_COORDINATE;
            } else {
                return bufersize[index];
            }
        }

        public String getLastString() {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < sizevalue; ++i) {
                char c = nametext[normalize(botemvalue - 1 - i)];
                if (!((LatinIME) ringcont).isWordSeparator(c)) {
                    sb.append(c);
                } else {
                    break;
                }
            }
            return sb.reverse().toString();
        }

        public void reset() {
            sizevalue = 0;
        }
    }
}
