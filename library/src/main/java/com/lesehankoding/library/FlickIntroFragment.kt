package com.lesehankoding.library

import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import com.lesehankoding.library.model.FlickSliderPage

/**
 * Flick intro fragment
 *
 * @constructor Create empty Flick intro fragment
 */
@Suppress("LongParameterList")
class FlickIntroFragment : AppIntroBaseFragment() {

    override val layoutId: Int get() = R.layout.flick_fragment_intro

    companion object {

        /**
         * Generates a new instance for [FlickIntroFragment]
         *
         * @param title CharSequence which will be the slide title
         * @param description CharSequence which will be the slide description
         * @param imageDrawable @DrawableRes (Integer) the image that will be
         *                             displayed, obtained from Resources
         * @param backgroundColor @ColorInt (Integer) custom background color
         * @param titleColor @ColorInt (Integer) custom title color
         * @param descriptionColor @ColorInt (Integer) custom description color
         * @param titleTypefaceFontRes @FontRes (Integer) custom title typeface obtained
         *                             from Resources
         * @param descriptionTypefaceFontRes @FontRes (Integer) custom description typeface obtained
         *                             from Resources
         * @param backgroundDrawable @DrawableRes (Integer) custom background drawable
         *
         * @return An [FlickIntroFragment] created instance
         */
        @JvmOverloads
        @JvmStatic
        fun newInstance(
            title: CharSequence? = null,
            description: CharSequence? = null,
            @DrawableRes imageDrawable: Int = 0,
            @ColorInt backgroundColor: Int = 0,
            @ColorInt titleColor: Int = 0,
            @ColorInt descriptionColor: Int = 0,
            @FontRes titleTypefaceFontRes: Int = 0,
            @FontRes descriptionTypefaceFontRes: Int = 0,
            @DrawableRes backgroundDrawable: Int = 0
        ): FlickIntroFragment {
            return newInstance(
                FlickSliderPage(
                    title = title,
                    description = description,
                    imageDrawable = imageDrawable,
                    backgroundColor = backgroundColor,
                    titleColor = titleColor,
                    descriptionColor = descriptionColor,
                    titleTypefaceFontRes = titleTypefaceFontRes,
                    descriptionTypefaceFontRes = descriptionTypefaceFontRes,
                    backgroundDrawable = backgroundDrawable
                )
            )
        }

        /**
         * Generates an [FlickIntroFragment] from a given [FlickSliderPage]
         *
         * @param flickSliderPage the [FlickSliderPage] object which contains all attributes for
         * the current slide
         *
         * @return An [FlickIntroFragment] created instance
         */
        @JvmStatic
        fun newInstance(flickSliderPage: FlickSliderPage): FlickIntroFragment {
            val slide = FlickIntroFragment()
            slide.arguments = flickSliderPage.toBundle()
            return slide
        }
    }
}
