package com.keyboard.keyboardthemes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class My_Keybord_Mnager extends BroadcastReceiver {

    private static String TAG = "PCKeyboard";
    private static String HK_INTENT_DICT = "org.pocketworkstation.DICT";
    private static String SOFTKEYBOARD_INTENT_DICT = "com.menny.android.anysoftkeyboard.DICTIONARY";
    private LatinIME latin;
    
    private static Map<String, String> SOFTKEYBOARD_LANG_MAP = new HashMap<String, String>();

    static {
        SOFTKEYBOARD_LANG_MAP.put("dk", "da");
    }
    
    My_Keybord_Mnager(LatinIME latin) {
    	super();
    	this.latin = latin;
    }
    
    private static Map<String, DictPluginSpec> mPluginDicts =
        new HashMap<String, DictPluginSpec>();
    
    static interface DictPluginSpec {
        Keyboard_Typing_Spiling getDict(Context appcont);
    }

    static private abstract class DictPluginSpecBase implements DictPluginSpec {
        String appname;
        
        Resources getResources(Context context) {
            PackageManager appnamemng = context.getPackageManager();
            Resources rid = null;
            try {
                ApplicationInfo information = appnamemng.getApplicationInfo(appname, 0);
                rid = appnamemng.getResourcesForApplication(information);
            } catch (NameNotFoundException e) {
                Log.i(TAG, "couldn't get resources");
            }
            return rid;
        }

        abstract InputStream[] getStreams(Resources res);

        public Keyboard_Typing_Spiling getDict(Context appcont) {
            Resources rid = getResources(appcont);
            if (rid == null) return null;

            InputStream[] input = getStreams(rid);
            if (input == null) return null;
            Keyboard_Typing_Spiling derction = new Keyboard_Typing_Spiling(appcont, input, My_Keyword_Inform_app.DIC_MAIN);
            if (derction.getSize() == 0) return null;
            return derction;
        }
    }

    static private class DictPluginSpecHK extends DictPluginSpecBase {
        
        int[] pluging;

        public DictPluginSpecHK(String pakg, int[] name) {
            appname = pakg;
            pluging = name;
        }

        @Override
        InputStream[] getStreams(Resources res) {
            if (pluging == null || pluging.length == 0) return null;
            InputStream[] input = new InputStream[pluging.length];
            for (int i = 0; i < pluging.length; ++i) {
                input[i] = res.openRawResource(pluging[i]);
            }
            return input;
        }
    }
    
    static private class DictPluginSpecSoftKeyboard extends DictPluginSpecBase {
        
        String textlater;

        public DictPluginSpecSoftKeyboard(String pakge, String later) {
            appname = pakge;
            textlater = later;
        }

        @Override
        InputStream[] getStreams(Resources res) {
            if (textlater == null) return null;
            try {
                InputStream inpute = res.getAssets().open(textlater);
                return new InputStream[] {inpute};
            } catch (IOException e) {
                Log.e(TAG, "Dictionary asset loading failure");
                return null;
            }
        }
    }
    
    @Override
    public void onReceive(Context appcont, Intent intent) {
        Log.i(TAG, "Package information changed, updating dictionaries.");
        getPluginDictionaries(appcont);
        Log.i(TAG, "Finished updating dictionaries.");
        latin.toggleLanguage(true, true);
    }

    static void getSoftKeyboardDictionaries(PackageManager packg) {
        Intent softkeyword = new Intent(SOFTKEYBOARD_INTENT_DICT);
        List<ResolveInfo> resolve = packg.queryBroadcastReceivers(softkeyword, PackageManager.GET_RECEIVERS);
        for (ResolveInfo info : resolve) {
            ApplicationInfo information = info.activityInfo.applicationInfo;
            String apptext = information.packageName;
            boolean success = false;
            try {
                Resources idvalue = packg.getResourcesForApplication(information);
                int dictId = idvalue.getIdentifier("dictionaries", "xml", apptext);
                if (dictId == 0) continue;
                XmlResourceParser xmlfile = idvalue.getXml(dictId);

                String assetName = null;
                String lang = null;
                try {
                    int value = xmlfile.getEventType();
                    while (value != XmlResourceParser.END_DOCUMENT) {
                        if (value == XmlResourceParser.START_TAG) {
                            String tag = xmlfile.getName();
                            if (tag != null) {
                                if (tag.equals("Dictionary")) {
                                    lang = xmlfile.getAttributeValue(null, "locale");
                                    String mapvalue = SOFTKEYBOARD_LANG_MAP.get(lang);
                                    if (mapvalue != null) lang = mapvalue;
                                    String type = xmlfile.getAttributeValue(null, "type");
                                    if (type == null || type.equals("raw") || type.equals("binary")) {
                                        assetName = xmlfile.getAttributeValue(null, "dictionaryAssertName"); // sic
                                    } else {
                                        Log.w(TAG, "Unsupported AnySoftKeyboard dict type " + type);
                                    }
                                }
                            }
                        }
                        xmlfile.next();
                        value = xmlfile.getEventType();
                    }
                } catch (XmlPullParserException e) {
                    Log.e(TAG, "Dictionary XML parsing failure");
                } catch (IOException e) {
                    Log.e(TAG, "Dictionary XML IOException");
                }

                if (assetName == null || lang == null) continue;
                DictPluginSpec jagya = new DictPluginSpecSoftKeyboard(apptext, assetName);
                mPluginDicts.put(lang, jagya);
                Log.i(TAG, "Found plugin dictionary: lang=" + lang + ", pkg=" + apptext);
                success = true;
            } catch (NameNotFoundException e) {
                Log.i(TAG, "bad");
            } finally {
                if (!success) {
                    Log.i(TAG, "failed to load plugin dictionary spec from " + apptext);
                }
            }
        }
    }

    static void getHKDictionaries(PackageManager packageManager) {
        Intent intedick = new Intent(HK_INTENT_DICT);
        List<ResolveInfo> flag = packageManager.queryIntentActivities(intedick, 0);
        for (ResolveInfo info : flag) {
            ApplicationInfo appInfo = info.activityInfo.applicationInfo;
            String pakage = appInfo.packageName;
            boolean success = false;
            try {
                Resources idvalue = packageManager.getResourcesForApplication(appInfo);
                int langId = idvalue.getIdentifier("dict_language", "string", pakage);
                if (langId == 0) continue;
                String lang = idvalue.getString(langId);
                int[] rawIds = null;

                int rawId = idvalue.getIdentifier("main", "raw", pakage);
                if (rawId != 0) {
                    rawIds = new int[] { rawId };
                } else {
                    int parts = 0;
                    List<Integer> aray = new ArrayList<Integer>();
                    while (true) {
                        int name = idvalue.getIdentifier("main" + parts, "raw", pakage);
                        if (name == 0) break;
                        aray.add(name);
                        ++parts;
                    }
                    if (parts == 0) continue;
                    rawIds = new int[parts];
                    for (int i = 0; i < parts; ++i) rawIds[i] = aray.get(i);
                }
                DictPluginSpec jagaya = new DictPluginSpecHK(pakage, rawIds);
                mPluginDicts.put(lang, jagaya);
                Log.i(TAG, "Found plugin dictionary: lang=" + lang + ", pkg=" + pakage);
                success = true;
            } catch (NameNotFoundException e) {
                Log.i(TAG, "bad");
            } finally {
                if (!success) {
                    Log.i(TAG, "failed to load plugin dictionary spec from " + pakage);
                }
            }
        }
    }

    static void getPluginDictionaries(Context context) {
        mPluginDicts.clear();
        PackageManager pkgname = context.getPackageManager();
        getSoftKeyboardDictionaries(pkgname);
        getHKDictionaries(pkgname);
    }
    
    static Keyboard_Typing_Spiling getDictionary(Context context, String lang) {
        DictPluginSpec jagaya = mPluginDicts.get(lang);
        if (jagaya == null) jagaya = mPluginDicts.get(lang.substring(0, 2));
        if (jagaya == null) {
            return null;
        }
        Keyboard_Typing_Spiling spling = jagaya.getDict(context);
        Log.i(TAG, "Found plugin dictionary for " + lang + (spling == null ? " is null" : ", size=" + spling.getSize()));
        return spling;
    }
}
