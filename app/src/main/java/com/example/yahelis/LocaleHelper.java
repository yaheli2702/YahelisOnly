package com.example.yahelis;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import java.util.Locale;

public class LocaleHelper {
    //מטרת המחלקה היא לאפשר שינוי של השפה באפליקציה כדי שהיא תתאים את התצוגה והטקסטים לשפה שנבחרה.
    public static void setLocale(Context context, Locale locale) {
        //מטרת הפעולה היא לעדכן את הלוקאל של האפליקציה.
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        Locale.setDefault(locale);
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
}