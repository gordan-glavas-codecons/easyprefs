# EasyPrefs
EasyPrefs uses annotations to generate Android SharedPreferences boilerplate code. Simply annotate classes, methods or fields, and EasyPrefs will generate a class containing static methods for storing and retrieving to and from SharedPreferences. Check out sample code below to see it in action.

### Installation

EasyPrefs is hosted on JitPack - just add the JitPack maven repository to your list of repositories, and then add the EasyPrefs dependency:

```gradle
allprojects {
  repositories {
      jcenter()
      maven { url "https://jitpack.io" }
   }
}
...
dependencies {
   compile 'com.github.globulus:easyprefs:-SNAPSHOT'
}
```
### Sample Code

SharedPrefs.java
```java
// Add fields from this class to EasyPrefs, but only those annotated with @Pref.
// Add fields directly to EasyPrefs, and not inside an enclosing static class.
@PrefClass(autoInclude = false, staticClass = false)
public class SharedPrefs {

    private static SharedPreferences sSecureInstance;

    //
    // PrefMaster annotation is placed on a public static method returning an instance of
    // SharedPreferences, and this method will be used in generated code to retrieve the
    // SharedPreferences instance we want to work with.
    //
    @PrefMaster
    public static SharedPreferences getSecureInstance(@NonNull Context context) {
        if (sSecureInstance == null) {
           sSecureInstance = PreferenceManager.getDefaultSharedPreferences(context);
        }
        return sSecureInstance;
    }

    int test1; // This field won't be included

    @Pref
    String test2; // This field will be included.

    @Pref(key = "notTest3")
    long test3; // This field will be included with a custom key.
}
```

MorePrefs.java
```java
// Automatically add all fields from this class to EasyPrefs.
// Make an in inner static class in EasyPrefs to group these fields.
@PrefClass
public class MorePrefs {

    int intField;
    final String stringFieldWithDefaultValue = "asss";
    final long longFieldWithDefaultValue = 3;

    // Don't add this field to clear() or clearAll() methods
    // Add a comment above its getter and setter
    // When invoking put, autoset the value to true.
    @Pref(clear = false, comment = "Proba 4", autoset = "true")
    boolean dontClearAddCommentAutoSetToTrue;
    
    //
    // Expose these two methods in EasyPrefs so that you can have a centralized prefs manager.
    //
    @PrefMethod
    public static void putThisPref(Context context, int pref) {
        EasyPrefs.putPreferencesField(context, "thisPref", pref);
    }

    @PrefMethod
    public static int getThisPref(Context context) {
        return EasyPrefs.getPreferencesField(context, "thisPref", 1);
    }

    // Classes that don't go into SharedPreferences by default can be added to it
    // via mapper classes, which transform the custom class into one of the
    // SharedPreferences-friendly types.
    @Pref(function = JsonObjectString.class, rawDefaultValue = "(String) null")
    JsonObject jsonTest;

    public static class JsonObjectString implements PrefFunction<JsonObject, String> {

        @Override
        public String put(JsonObject jsonObject) {
            if (jsonObject == null) {
                return null;
            }
            return jsonObject.toString();
        }

        @Override
        public JsonObject get(String s) {
            if (s == null) {
                return null;
            }
            return new Gson().fromJson(s, JsonObject.class);
        }
    }
}
```
EasyPrefs.java (generated automatically when making the project)
```java
package net.globulus.easyprefs;

import java.util.Set;
import java.util.HashSet;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Generated class by @EasyPrefs . Do not modify this code!
 */
public class EasyPrefs {

  private EasyPrefs() {
  }

  public static SharedPreferences getPrefs(Context context) {
    return net.globulus.easyprefssample.SharedPrefs.getSecureInstance(context);
  }

  // Basic storage methods

  public static void removePreferencesField(Context context, String key) {
    SharedPreferences.Editor editor = getPrefs(context).edit();
    editor.remove(key);
    editor.commit();
  }

  public static void putPreferencesField(Context context, String key, int value) {
    SharedPreferences.Editor editor = getPrefs(context).edit();
    editor.putInt(key, value);
    editor.commit();
  }

  public static int getPreferencesField(Context context, String key, int defaultValue) {
    return getPrefs(context).getInt(key, defaultValue);
  }

  public static void putPreferencesField(Context context, String key, long value) {
    SharedPreferences.Editor editor = getPrefs(context).edit();
    editor.putLong(key, value);
    editor.commit();
  }

  public static long getPreferencesField(Context context, String key, long defaultValue) {
    return getPrefs(context).getLong(key, defaultValue);
  }

  public static void putPreferencesField(Context context, String key, float value) {
    SharedPreferences.Editor editor = getPrefs(context).edit();
    editor.putFloat(key, value);
    editor.commit();
  }

  public static float getPreferencesField(Context context, String key, float defaultValue) {
    return getPrefs(context).getFloat(key, defaultValue);
  }

  public static void putPreferencesField(Context context, String key, boolean value) {
    SharedPreferences.Editor editor = getPrefs(context).edit();
    editor.putBoolean(key, value);
    editor.commit();
  }

  public static boolean getPreferencesField(Context context, String key, boolean defaultValue) {
    return getPrefs(context).getBoolean(key, defaultValue);
  }

  public static void putPreferencesField(Context context, String key, String value) {
    SharedPreferences.Editor editor = getPrefs(context).edit();
    editor.putString(key, value);
    editor.commit();
  }

  public static String getPreferencesField(Context context, String key, String defaultValue) {
    return getPrefs(context).getString(key, defaultValue);
  }

  public static void putPreferencesField(Context context, String key, Set<String> value) {
    SharedPreferences.Editor editor = getPrefs(context).edit();
    editor.putStringSet(key, value);
    editor.commit();
  }

  public static Set<String> getPreferencesField(Context context, String key, Set<String> defaultValue) {
    return getPrefs(context).getStringSet(key, defaultValue);
  }

  public static void addToPreferencesField(Context context, String key, String value) {
    Set<String> set = getPreferencesField(context, key, new HashSet<String>());
    set.add(value);
    putPreferencesField(context, key, set);
  }

  public static void removeFromPreferencesField(Context context, String key, String value) {
    Set<String> set = getPreferencesField(context, key, new HashSet<String>());
    set.remove(value);
    putPreferencesField(context, key, set);
  }

  // Generated methods


  public static class MorePrefs {


    public static int getIntField(Context context) {
      return getPreferencesField(context, "intField", 0);
    }

    public static void putIntField(Context context, int value) {
      putPreferencesField(context, "intField", value);
    }

    public static String getStringFieldWithDefaultValue(Context context) {
      return getPreferencesField(context, "stringFieldWithDefaultValue", "asss");
    }

    public static void putStringFieldWithDefaultValue(Context context, String value) {
      putPreferencesField(context, "stringFieldWithDefaultValue", value);
    }

    public static long getLongFieldWithDefaultValue(Context context) {
      return getPreferencesField(context, "longFieldWithDefaultValue", 3);
    }

    public static void putLongFieldWithDefaultValue(Context context, long value) {
      putPreferencesField(context, "longFieldWithDefaultValue", value);
    }

    // 
    // Proba 4
    // 

    public static boolean getDontClearAddCommentAutoSetToTrue(Context context) {
      return getPreferencesField(context, "dontClearAddCommentAutoSetToTrue", false);
    }

    public static void putDontClearAddCommentAutoSetToTrue(Context context) {
      putPreferencesField(context, "dontClearAddCommentAutoSetToTrue", true);
    }

    // 
    // END Proba 4
    // 

    public static com.google.gson.JsonObject getJsonTest(Context context) {
      net.globulus.easyprefssample.MorePrefs.JsonObjectString mapper = new net.globulus.easyprefssample.MorePrefs.JsonObjectString();
      return mapper.get(getPreferencesField(context, "jsonTest", (String) null));
    }

    public static void putJsonTest(Context context, com.google.gson.JsonObject value) {
      net.globulus.easyprefssample.MorePrefs.JsonObjectString mapper = new net.globulus.easyprefssample.MorePrefs.JsonObjectString();
      putPreferencesField(context, "jsonTest", mapper.put(value));
    }

    public static void clear(Context context) {
      SharedPreferences.Editor editor = getPrefs(context).edit();
      editor.remove("intField");
      editor.remove("stringFieldWithDefaultValue");
      editor.remove("longFieldWithDefaultValue");
      editor.remove("jsonTest");
      editor.commit();
    }
  }


  public static String getTest2(Context context) {
    return getPreferencesField(context, "test2", "");
  }

  public static void putTest2(Context context, String value) {
    putPreferencesField(context, "test2", value);
  }

  public static long getTest3(Context context) {
    return getPreferencesField(context, "notTest3", 0L);
  }

  public static void putTest3(Context context, long value) {
    putPreferencesField(context, "notTest3", value);
  }
  
  public static void putThisPref(Context context0, int int1) {
    net.globulus.easyprefssample.MorePrefs.putThisPref(context0, int1);
  }

  public static int getThisPref(Context context0) {
    return net.globulus.easyprefssample.MorePrefs.getThisPref(context0);
  }

  public static void clear(Context context) {
    SharedPreferences.Editor editor = getPrefs(context).edit();
    editor.remove("test2");
    editor.remove("notTest3");
    editor.commit();
  }

  public static void clearAll(Context context) {
    clear(context);
    MorePrefs.clear(context);
  }
}
```
