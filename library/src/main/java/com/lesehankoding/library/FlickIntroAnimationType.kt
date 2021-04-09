package com.lesehankoding.library

/**
 * Flick intro animation type
 *
 * @constructor Create empty Flick intro animation type
 */
sealed class FlickIntroAnimationType {

    /** Sets the animation of the intro to a flow animation */
    object Flow : FlickIntroAnimationType()

    /** Sets the animation of the intro to a depth animation */
    object Depth : FlickIntroAnimationType()

    /** Sets the animation of the intro to a zoom animation */
    object Zoom : FlickIntroAnimationType()

    /** Sets the animation of the intro to a slide over animation */
    object SlideOver : FlickIntroAnimationType()

    /** Sets the animation of the intro to a fade animation */
    object Fade : FlickIntroAnimationType()

    /**
     * Parallax
     *
     * @property titleParallaxFactor
     * @property imageParallaxFactor
     * @property descriptionParallaxFactor
     * @constructor Create empty Parallax
     */
    class Parallax(
        val titleParallaxFactor: Double = 1.0,
        val imageParallaxFactor: Double = -1.0,
        val descriptionParallaxFactor: Double = 2.0
    ) : FlickIntroAnimationType()
}
