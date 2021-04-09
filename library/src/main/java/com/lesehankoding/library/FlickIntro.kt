package com.lesehankoding.library

import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.FontRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.core.widget.TextViewCompat
import com.github.appintro.FlickIntroBase
import com.lesehankoding.library.internal.TypefaceContainer

/**
 * Flick intro
 *
 * @constructor Create empty Flick intro
 */
abstract class FlickIntro : FlickIntroBase() {

    override val layoutId = R.layout.flick_intro_layout

    /**
     * Set bar color
     *
     * @param color
     */
    fun setBarColor(@ColorInt color: Int) {
        val bottomBar = findViewById<View>(R.id.bottom)
        bottomBar.setBackgroundColor(color)
    }

    /**
     * Set done text
     *
     * @param text
     */
    fun setDoneText(text: CharSequence?) {
        val doneText = findViewById<TextView>(R.id.btn)
        doneText.text = text
    }

    /**
     * Set done text
     *
     * @param doneResId
     */
    fun setDoneText(@StringRes doneResId: Int) {
        val doneText = findViewById<TextView>(R.id.btn)
        doneText.setText(doneResId)
    }

    /**
     * Set done text typeface
     *
     * @param typeURL
     */
    fun setDoneTextTypeface(typeURL: String?) {
        val view = findViewById<TextView>(R.id.btn)
        TypefaceContainer(typeURL, 0).applyTo(view)
    }

    /**
     * Set done text typeface
     *
     * @param typeface
     */
    fun setDoneTextTypeface(@FontRes typeface: Int) {
        val view = findViewById<TextView>(R.id.btn)
        TypefaceContainer(null, typeface).applyTo(view)
    }

    /**
     * Set color done text
     *
     * @param colorDoneText
     */
    fun setColorDoneText(@ColorInt colorDoneText: Int) {
        val doneText = findViewById<TextView>(R.id.btn)
        doneText.setTextColor(colorDoneText)
    }


    /**
     * Set done text appearance
     *
     * @param textAppearance
     */
    fun setDoneTextAppearance(@StyleRes textAppearance: Int) {
        val doneText = findViewById<TextView>(R.id.btn)
        TextViewCompat.setTextAppearance(doneText, textAppearance)
    }


}
