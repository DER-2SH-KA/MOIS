package ru.der2shka.cursovedcote.Service

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import java.util.Locale


/**
 *  Change mobile app's language
 *  by context and locale code as string
 *  @param context current app's context.
 *  @param languageCode language code which to switch.
 * **/
fun setLocaleForApp(context: Context, languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)

    val resources: Resources = context.resources
    val config: Configuration = Configuration(resources.configuration)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        config.setLocale(locale)
    } else {
        config.locale = locale
    }

    resources.updateConfiguration(config, resources.displayMetrics)
}