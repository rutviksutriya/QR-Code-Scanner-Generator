# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-dontwarn rx.**
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }

-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao** {
    public static void dropTable(org.greenrobot.greendao.database.Database**, boolean);
    public static void createTable(org.greenrobot.greendao.database.Database**, boolean);
}
-dontwarn net.sqlcipher.database.**
-dontwarn com.squareup.okhttp.**

-dontwarn com.google.android.gms.**
-keep class com.google.android.gms.* { *; }
-keep class com.google.gson.stream.** { *; }

-keep class com.google.gson.examples.android.model.** { <fields>; }

-keep class com.qrcodegenerator.qrcodereader.barcodescanner.qrreader.scanqrcode.model.* {
  *;
}
-dontwarn java.lang.invoke.StringConcatFactory

-dontwarn org.apache.commons.**
-dontwarn org.apache.http.**
-keep class android.net.http.** { *; }
-keep interface org.apache.** { *; }
-keep enum org.apache.** { *; }
-keep class org.apache.** { *; }
-keep class org.apache.commons.** { *; }
-keep class org.apache.http.** { *; }
-keep class org.apache.harmony.* {*;}


-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**



-dontwarn com.airbnb.lottie.**
-keep class com.airbnb.lottie.** {*;}

-dontwarn org.apache.http.**
-dontwarn android.net.http.AndroidHttpClient.**
-keep class org.apache.http.** { *; }
-keep class android.net.http.** { *; }

-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static *** d(...);
    public static *** w(...);
    public static *** v(...);
    public static *** i(...);
    public static *** e(...);
}
-keepattributes LineNumberTable

-keep public class com.google.android.gms.ads.**{
   public *;
}
-printmapping missing_rules.txt
-ignorewarnings

-obfuscationdictionary "class_encode_dictionary.txt"
-classobfuscationdictionary "class_encode_dictionary.txt"
-packageobfuscationdictionary "class_encode_dictionary.txt"