# حفظ فئات Room و LiveData
-keep class androidx.room.** { *; }
-keep class * extends androidx.room.RoomDatabase
-keep class * implements androidx.room.** { *; }
-keepclassmembers class * { @androidx.room.* *; }

# حفظ فئات Hilt (Dagger)
-keep class com.google.dagger.hilt.** { *; }
-keep class * extends com.google.dagger.hilt.android.HiltAndroidApp
-keepclassmembers class * { @com.google.dagger.hilt.* *; }

# حفظ فئات Retrofit/OkHttp
-keep class com.squareup.okhttp.** { *; }
-keep class okhttp3.** { *; }
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes *Annotation*
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# حفظ فئات Glide
-keep public class * implements com.bumptech.glide.module.AppGlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
    <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

# حفظ فئات WorkManager
-keep class androidx.work.** { *; }
-keepclassmembers class * extends androidx.work.Worker {
    public <init>(...);
    public *;
}

# حفظ فئات المشروع الأساسية
-keep class com.example.mard.** { *; }
-keepclassmembers class com.example.mard.** {
    public *;
}

# حفظ فئات Material Components
-keep class com.google.android.material.** { *; }

# حفظ فئات Kotlin
-keep class kotlin.** { *; }
-keep class kotlinx.** { *; }
-keepclassmembers class **$WhenMappings {
    <fields>;
}
-keepclassmembers class kotlinx.coroutines.** {
    volatile <fields>;
}

# القواعد العامة
-dontwarn kotlinx.coroutines.**
-dontwarn org.jetbrains.annotations.**
-dontwarn com.google.errorprone.annotations.**
-dontwarn javax.annotation.**
-dontwarn java.lang.invoke.**

# حفظ سجلات Timber
-keep class timber.log.** { *; }
-keepclassmembers class timber.log.Timber {
    public static timber.log.Timber$Tree *;
}
-keepclassmembers class * extends timber.log.Timber$Tree {
    <init>(...);
}

# حفظ فئات ViewBinding
-keep class * extends androidx.viewbinding.ViewBinding {
    public static * inflate(...);
    public static * bind(...);
}

# حفظ فئات LiveData و ViewModel
-keep class androidx.lifecycle.** { *; }
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    <init>(...);
}