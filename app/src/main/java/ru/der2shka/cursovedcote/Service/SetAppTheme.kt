package ru.der2shka.cursovedcote.Service

import android.content.Context
import android.content.res.Configuration

/**
 *  Change mobile app's color theme
 *  by context and boolean value of night theme
 *  @param context current app's context.
 *  @param languageCode switch to night theme?
 * **/
fun setAppTheme(context: Context, isNightTheme: Boolean) {

    val nightModeFlags = if (isNightTheme) {
        Configuration.UI_MODE_NIGHT_YES
    } else {
        Configuration.UI_MODE_NIGHT_NO
    }

    val config = Configuration(context.resources.configuration)
    config.uiMode = nightModeFlags or (
                    config.uiMode and Configuration.UI_MODE_NIGHT_MASK.inv()
            )

    context.resources.updateConfiguration(
        config,
        context.resources.displayMetrics
    )
}