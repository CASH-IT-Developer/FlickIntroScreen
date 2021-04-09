package com.lesehankoding.library.model

import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes

/**
 * Flick slider builder
 *
 * @constructor Create empty Flick slider builder
 */
class FlickSliderBuilder {

    private var title: CharSequence? = null

    private var description: CharSequence? = null

    @DrawableRes
    private var imageDrawable: Int = 0

    @ColorInt
    private var backgroundColor: Int = 0

    @ColorInt
    private var titleColor: Int = 0

    @ColorInt
    private var descriptionColor: Int = 0

    @FontRes
    private var titleTypefaceFontRes: Int = 0

    @FontRes
    private var descriptionTypefaceFontRes: Int = 0

    private var titleTypeface: String? = null

    private var descriptionTypeface: String? = null

    @DrawableRes
    private var backgroundDrawable: Int = 0

    /**
     * Title
     *
     * @param title
     * @return
     */
    fun title(title: CharSequence): FlickSliderBuilder {
        this.title = title
        return this
    }

    /**
     * Description
     *
     * @param description
     * @return
     */
    fun description(description: CharSequence): FlickSliderBuilder {
        this.description = description
        return this
    }

    /**
     * Image drawable
     *
     * @param imageDrawable
     * @return
     */
    fun imageDrawable(@DrawableRes imageDrawable: Int): FlickSliderBuilder {
        this.imageDrawable = imageDrawable
        return this
    }

    /**
     * Background color
     *
     * @param backgroundColor
     * @return
     */
    fun backgroundColor(@ColorInt backgroundColor: Int): FlickSliderBuilder {
        this.backgroundColor = backgroundColor
        return this
    }

    /**
     * Title color
     *
     * @param titleColor
     * @return
     */
    fun titleColor(@ColorInt titleColor: Int): FlickSliderBuilder {
        this.titleColor = titleColor
        return this
    }

    /**
     * Description color
     *
     * @param descriptionColor
     * @return
     */
    fun descriptionColor(@ColorInt descriptionColor: Int): FlickSliderBuilder {
        this.descriptionColor = descriptionColor
        return this
    }

    /**
     * Title typeface font res
     *
     * @param titleTypefaceFontRes
     * @return
     */
    fun titleTypefaceFontRes(@FontRes titleTypefaceFontRes: Int): FlickSliderBuilder {
        this.titleTypefaceFontRes = titleTypefaceFontRes
        return this
    }

    /**
     * Description typeface font res
     *
     * @param descriptionTypefaceFontRes
     * @return
     */
    fun descriptionTypefaceFontRes(@FontRes descriptionTypefaceFontRes: Int): FlickSliderBuilder {
        this.descriptionTypefaceFontRes = descriptionTypefaceFontRes
        return this
    }

    /**
     * Title typeface
     *
     * @param titleTypeface
     * @return
     */
    fun titleTypeface(titleTypeface: String): FlickSliderBuilder {
        this.titleTypeface = titleTypeface
        return this
    }

    /**
     * Description typeface
     *
     * @param descriptionTypeface
     * @return
     */
    fun descriptionTypeface(descriptionTypeface: String): FlickSliderBuilder {
        this.descriptionTypeface = descriptionTypeface
        return this
    }

    /**
     * Background drawable
     *
     * @param backgroundDrawable
     * @return
     */
    fun backgroundDrawable(@DrawableRes backgroundDrawable: Int): FlickSliderBuilder {
        this.backgroundDrawable = backgroundDrawable
        return this
    }

    /**
     * Build
     *
     */
    fun build() = FlickSliderPage(
        title = this.title,
        description = this.description,
        imageDrawable = this.imageDrawable,
        backgroundColor = this.backgroundColor,
        titleColor = this.titleColor,
        descriptionColor = this.descriptionColor,
        titleTypefaceFontRes = this.titleTypefaceFontRes,
        descriptionTypeface = this.descriptionTypeface,
        titleTypeface = this.titleTypeface,
        descriptionTypefaceFontRes = this.descriptionTypefaceFontRes,
        backgroundDrawable = this.backgroundDrawable
    )
}
