package com.lesehankoding.library.internal

import android.graphics.Typeface
import android.widget.TextView
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat
import com.lesehankoding.library.internal.CustomFontCache

/**
 * Typeface container
 *
 * @property typeFaceUrl
 * @property typeFaceResource
 * @constructor Create empty Typeface container
 */
internal data class TypefaceContainer(
    var typeFaceUrl: String? = null,
    @FontRes var typeFaceResource: Int = 0
) {

    /**
     * Apply to
     *
     * @param textView
     */
    fun applyTo(textView: TextView?) {
        if (textView == null || textView.context == null) {
            return
        }
        if (typeFaceUrl == null && typeFaceResource == 0) {
            return
        }

        // Callback to font retrieval
        val callback = object : ResourcesCompat.FontCallback() {
            override fun onFontRetrievalFailed(reason: Int) {
                // Don't be panic, just do nothing.
            }
            override fun onFontRetrieved(typeface: Typeface) {
                textView.typeface = typeface
            }
        }

        // We give priority to the FontRes here.
        if (typeFaceResource != 0) {
            ResourcesCompat.getFont(textView.context, typeFaceResource, callback, null)
        } else {
            CustomFontCache.getFont(textView.context, typeFaceUrl, callback)
        }
    }
}
