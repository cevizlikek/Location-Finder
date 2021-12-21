package com.keyboard.keyboardthemes;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import com.keyboard.keyboardthemes.Keyboard.Key;

public class TextEntryState {
    
    private static final boolean entery = false;
    private static final String TAG = "TextEntryState";
    private static boolean conected = false;
    private static int restordata = 0;
    private static int ototextnumbervalue = 0;
    private static int otorefrencevalue = 0;
    private static int textenteryvalue = 0;
    private static int textsplingcount = 0;
    private static int texttypingwordnumber = 0;
    private static int entersplingname;
    private static int orgnalspling;
    private static State world = State.UNKNOWN;
    private static FileOutputStream outputstremdeta;
    private static FileOutputStream derctoryoutputmethed;

    public enum State {
        UNKNOWN,
        START,
        IN_WORD,
        ACCEPTED_DEFAULT,
        PICKED_SUGGESTION,
        PUNCTUATION_AFTER_WORD,
        PUNCTUATION_AFTER_ACCEPTED,
        SPACE_AFTER_ACCEPTED,
        SPACE_AFTER_PICKED,
        UNDO_COMMIT,
        CORRECTING,
        PICKED_CORRECTION;

    }

    public static void newSession(Context appcont) {
        texttypingwordnumber++;
        ototextnumbervalue = 0;
        restordata = 0;
        otorefrencevalue = 0;
        textenteryvalue = 0;
        textsplingcount = 0;
        entersplingname = 0;
        orgnalspling = 0;
        world = State.START;
        
        if (conected) {
            try {
                outputstremdeta = appcont.openFileOutput("key.txt", Context.MODE_APPEND);
                derctoryoutputmethed = appcont.openFileOutput("action.txt", Context.MODE_APPEND);
            } catch (IOException ioe) {
                Log.e("TextEntryState", "Couldn't open file for output: " + ioe);
            }
        }
    }
    
    public static void endSession() {
        if (outputstremdeta == null) {
            return;
        }
        try {
            outputstremdeta.close();
            String getclose = DateFormat.format("MM:dd hh:mm:ss", Calendar.getInstance().getTime()).toString()
                    + " BS: " + restordata
                    + " auto: " + ototextnumbervalue
                    + " manual: " + textenteryvalue
                    + " typed: " + textsplingcount
                    + " undone: " + otorefrencevalue
                    + " saved: " + ((float) (orgnalspling - entersplingname) / orgnalspling) + "\n";
            derctoryoutputmethed.write(getclose.getBytes());
            derctoryoutputmethed.close();
            outputstremdeta = null;
            derctoryoutputmethed = null;

        } catch (IOException ioe) {
        }
    }
    
    public static void acceptedDefault(CharSequence enterspling, CharSequence typing) {
        if (enterspling == null) return;
        if (!enterspling.equals(typing)) {
            ototextnumbervalue++;
        }
        entersplingname += enterspling.length();
        orgnalspling += typing.length();
        world = State.ACCEPTED_DEFAULT;
        My_Keybord_Imglong.logOnAutoSuggestion(enterspling.toString(), typing.toString());
        displayState();
    }

    public static void backToAcceptedDefault(CharSequence spling) {
        if (spling == null) return;
        switch (world) {
            case SPACE_AFTER_ACCEPTED:
            case PUNCTUATION_AFTER_ACCEPTED:
            case IN_WORD:
                world = State.ACCEPTED_DEFAULT;
                break;
        }
        displayState();
    }

    public static void manualTyped(CharSequence typedWord) {
        world = State.START;
        displayState();
    }

    public static void acceptedTyped(CharSequence typedWord) {
        textsplingcount++;
        world = State.PICKED_SUGGESTION;
        displayState();
    }

    public static void acceptedSuggestion(CharSequence spling, CharSequence sequence) {
        textenteryvalue++;
        State oldState = world;
        if (spling.equals(sequence)) {
            acceptedTyped(spling);
        }
        if (oldState == State.CORRECTING || oldState == State.PICKED_CORRECTION) {
            world = State.PICKED_CORRECTION;
        } else {
            world = State.PICKED_SUGGESTION;
        }
        displayState();
    }

    public static void selectedForCorrection() {
        world = State.CORRECTING;
        displayState();
    }

    public static void typedCharacter(char name, boolean isSeparator) {
        boolean isSpace = name == ' ';
        switch (world) {
            case IN_WORD:
                if (isSpace || isSeparator) {
                    world = State.START;
                } else {
                }
                break;
            case ACCEPTED_DEFAULT:
            case SPACE_AFTER_PICKED:
                if (isSpace) {
                    world = State.SPACE_AFTER_ACCEPTED;
                } else if (isSeparator) {
                    world = State.PUNCTUATION_AFTER_ACCEPTED;
                } else {
                    world = State.IN_WORD;
                }
                break;
            case PICKED_SUGGESTION:
            case PICKED_CORRECTION:
                if (isSpace) {
                    world = State.SPACE_AFTER_PICKED;
                } else if (isSeparator) {
                    world = State.PUNCTUATION_AFTER_ACCEPTED;
                } else {
                    world = State.IN_WORD;
                }
                break;
            case START:
            case UNKNOWN:
            case SPACE_AFTER_ACCEPTED:
            case PUNCTUATION_AFTER_ACCEPTED:
            case PUNCTUATION_AFTER_WORD:
                if (!isSpace && !isSeparator) {
                    world = State.IN_WORD;
                } else {
                    world = State.START;
                }
                break;
            case UNDO_COMMIT:
                if (isSpace || isSeparator) {
                    world = State.ACCEPTED_DEFAULT;
                } else {
                    world = State.IN_WORD;
                }
                break;
            case CORRECTING:
                world = State.START;
                break;
        }
        displayState();
    }
    
    public static void backspace() {
        if (world == State.ACCEPTED_DEFAULT) {
            world = State.UNDO_COMMIT;
            otorefrencevalue++;
            My_Keybord_Imglong.logOnAutoSuggestionCanceled();
        } else if (world == State.UNDO_COMMIT) {
            world = State.IN_WORD;
        }
        restordata++;
        displayState();
    }

    public static void reset() {
        world = State.START;
        displayState();
    }

    public static State getState() {
        if (entery) {
            Log.d(TAG, "Returning state = " + world);
        }
        return world;
    }

    public static boolean isCorrecting() {
        return world == State.CORRECTING || world == State.PICKED_CORRECTION;
    }

    public static void keyPressedAt(Key key, int x, int y) {
        if (conected && outputstremdeta != null && key.codes[0] >= 32) {
            String out = 
                    "KEY: " + (char) key.codes[0] 
                    + " X: " + x 
                    + " Y: " + y
                    + " MX: " + (key.x + key.width / 2)
                    + " MY: " + (key.y + key.height / 2) 
                    + "\n";
            try {
                outputstremdeta.write(out.getBytes());
            } catch (IOException ioe) {
                // TODO: May run out of space
            }
        }
    }

    private static void displayState() {
        if (entery) {
            Log.i(TAG, "State = " + world);
        }
    }
}

