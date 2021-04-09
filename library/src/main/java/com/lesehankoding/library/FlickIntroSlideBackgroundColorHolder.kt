package com.lesehankoding.library

import androidx.annotation.ColorInt

/**
 * Flick intro slide background color holder
 *
 * @constructor Create empty Flick intro slide background color holder
 */
interface FlickIntroSlideBackgroundColorHolder {

    /**
     * Returns the default background color of the slide
     *
     * @return The default background color of the slide
     */
    @get:ColorInt
    val defaultBackgroundColor: Int

    /**
     * Set background color
     *
     * @param backgroundColor
     */
    fun setBackgroundColor(@ColorInt backgroundColor: Int)
}
