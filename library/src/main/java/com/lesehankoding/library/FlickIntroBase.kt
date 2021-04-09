@file:Suppress("TooManyFunctions")

package com.github.appintro

import android.animation.ArgbEvaluator
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import android.view.*
import android.widget.Button
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.lesehankoding.library.*
import com.lesehankoding.library.internal.FlickIntroViewPager
import com.lesehankoding.library.internal.LayoutUtil
import com.lesehankoding.library.internal.LogHelper
import com.lesehankoding.library.internal.viewpager.FlickIntroPagerAdapter
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

/**
 * Flick intro base
 *
 * @constructor Create empty Flick intro base
 */
abstract class FlickIntroBase : AppCompatActivity(), FlickIntroViewPagerListener {

    @get:LayoutRes
    protected abstract val layoutId: Int

    private var mfinishedClickListener: OnFinishedClick? = null

    /**
     * Add finished click listener
     *
     * @param callback
     */
    fun addFinishedClickListener(callback: OnFinishedClick) {
        this.mfinishedClickListener = callback
    }

    /**
     * On finished click
     *
     * @constructor Create empty On finished click
     */
    interface OnFinishedClick {
        /**
         * On finished click
         *
         */
        fun onFinishedClick()
    }



    protected var isButtonsEnabled: Boolean = true
        set(value) {
            field = value
            updateButtonsVisibility()
        }

    protected var isSkipButtonEnabled = true
        set(value) {
            field = value
            updateButtonsVisibility()
        }

    protected var isWizardMode: Boolean = false
        set(value) {
            field = value
            this.isSkipButtonEnabled = !value
            updateButtonsVisibility()
        }


    protected var isSystemBackButtonLocked = false

    protected var isColorTransitionsEnabled = false

    protected var vibrateDuration = DEFAULT_VIBRATE_DURATION

    protected var isVibrate = false


    private lateinit var flickIntroPagerAdapter: FlickIntroPagerAdapter
    private lateinit var pager: FlickIntroViewPager
    private var slidesNumber: Int = 0
    private var savedCurrentItem: Int = 0
    private var currentlySelectedItem = -1
    private val fragments: MutableList<Fragment> = mutableListOf()

    private lateinit var btn: Button

    private val currentSlideNumber: Int
        get() = pager.getCurrentSlideNumber(fragments.size)


    private var retainIsButtonsEnabled = true

    private lateinit var vibrator: Vibrator
    private val argbEvaluator = ArgbEvaluator()

    internal val isRtl: Boolean
        get() = LayoutUtil.isRtl(applicationContext)

    /**
     * Add slide
     *
     * @param fragment
     */
    protected fun addSlide(fragment: Fragment) {
        if (isRtl) {
            fragments.add(0, fragment)
        } else {
            fragments.add(fragment)
        }
        if (isWizardMode) {
            pager.offscreenPageLimit = fragments.size
        }
        flickIntroPagerAdapter.notifyDataSetChanged()
    }


    /**
     * Go to previous slide
     *
     */
    protected fun goToPreviousSlide() {
        pager.goToPreviousSlide()
    }

    /**
     * Go to next slide
     *
     * @param isLastSlide
     */
    protected fun goToNextSlide(isLastSlide: Boolean = pager.isLastSlide(fragments.size)) {
        if (isLastSlide) {
            onIntroFinished()
        } else {
            pager.goToNextSlide()
            onNextSlide()
        }
    }

    /**
     * Set immersive mode
     *
     */
    protected fun setImmersiveMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                )
        }
    }


    /**
     * Set status bar color
     *
     * @param color
     */
    protected fun setStatusBarColor(@ColorInt color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = color
        }
    }

    /**
     * Set status bar color res
     *
     * @param color
     */
    protected fun setStatusBarColorRes(@ColorRes color: Int) {
        setStatusBarColor(ContextCompat.getColor(this, color))
    }

    /**
     * Set nav bar color
     *
     * @param color
     */
    protected fun setNavBarColor(@ColorInt color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = color
        }
    }

    /**
     * Set nav bar color res
     *
     * @param color
     */
    protected fun setNavBarColorRes(@ColorRes color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = ContextCompat.getColor(this, color)
        }
    }

    /**
     * Show status bar
     *
     * @param show
     */
    protected fun showStatusBar(show: Boolean) {
        if (show) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    /**
     * Set next page swipe lock
     *
     * @param lock
     */
    @Deprecated(
            "setNextPageSwipeLock has been deprecated in favor of setSwipeLock or SlidePolicy",
            ReplaceWith("setSwipeLock"),
            DeprecationLevel.ERROR
    )
    protected fun setNextPageSwipeLock(lock: Boolean) {
        LogHelper.w(
                TAG,
                "Calling setNextPageSwipeLock has not effect here. Please switch to setSwipeLock or SlidePolicy",
        )
    }

    /**
     * Set scroll duration factor
     *
     * @param factor
     */
    protected fun setScrollDurationFactor(factor: Int) {
        pager.setScrollDurationFactor(factor.toDouble())
    }

    /**
     * Set transformer
     *
     * @param flickIntroTransformer
     */
    protected fun setTransformer(flickIntroTransformer: FlickIntroAnimationType) {
        pager.setAppIntroPageTransformer(flickIntroTransformer)
    }

    /**
     * Set custom transformer
     *
     * @param transformer
     */
    protected fun setCustomTransformer(transformer: ViewPager.PageTransformer?) {
        pager.setPageTransformer(true, transformer)
    }

    /**
     * On user disabled permission
     *
     * @param permissionName
     */
    protected open fun onUserDisabledPermission(permissionName: String) {}

    /**
     * On user denied permission
     *
     * @param permissionName
     */
    protected open fun onUserDeniedPermission(permissionName: String) {}

    /**
     * On page selected
     *
     * @param position
     */
    protected open fun onPageSelected(position: Int) {}

    /**
     * On done pressed
     *
     * @param currentFragment
     */
    protected open fun onDonePressed(currentFragment: Fragment?) {}

    /**
     * On next pressed
     *
     * @param currentFragment
     */
    protected open fun onNextPressed(currentFragment: Fragment?) {}

    /**
     * On skip pressed
     *
     * @param currentFragment
     */
    protected open fun onSkipPressed(currentFragment: Fragment?) {}

    /**
     * On next slide
     *
     */
    protected open fun onNextSlide() {}

    /**
     * On intro finished
     *
     */
    protected open fun onIntroFinished() {}

    /**
     * On slide changed
     *
     * @param oldFragment
     * @param newFragment
     */
    protected open fun onSlideChanged(oldFragment: Fragment?, newFragment: Fragment?) {}


    @SuppressLint("NewApi")
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && hasFocus) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }


        showStatusBar(false)

        setContentView(layoutId)

        btn = findViewById(R.id.btn) ?: error("Missing Next button: R.id.btn")

        if (isRtl) {
            btn.scaleX = -1f
        }

        vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        flickIntroPagerAdapter = FlickIntroPagerAdapter(supportFragmentManager, fragments)
        pager = findViewById(R.id.view_pager)




        pager.adapter = this.flickIntroPagerAdapter
        pager.addOnPageChangeListener(OnPageChangeListener())
        pager.onNextPageRequestedListener = this

        setScrollDurationFactor(DEFAULT_SCROLL_DURATION_FACTOR)

        val dotsIndicator = findViewById<DotsIndicator>(R.id.indicator)
        dotsIndicator!!.setViewPager(viewPager = pager)


    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        slidesNumber = fragments.size
        retainIsButtonsEnabled = isButtonsEnabled

        if (isRtl) {
            pager.currentItem = fragments.size - savedCurrentItem
        } else {
            pager.currentItem = savedCurrentItem
        }

        pager.post {
            val fragment = flickIntroPagerAdapter.getItem(pager.currentItem)
            if (fragment != null) {
                dispatchSlideChangedCallbacks(
                        null,
                        flickIntroPagerAdapter
                                .getItem(pager.currentItem)
                )
            } else {
                finish()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            putInt(ARG_BUNDLE_SLIDES_NUMBER, slidesNumber)
            putBoolean(ARG_BUNDLE_RETAIN_IS_BUTTONS_ENABLED, retainIsButtonsEnabled)
            putBoolean(ARG_BUNDLE_IS_BUTTONS_ENABLED, isButtonsEnabled)
            putBoolean(ARG_BUNDLE_IS_SKIP_BUTTON_ENABLED, isSkipButtonEnabled)
            putInt(ARG_BUNDLE_CURRENT_ITEM, pager.currentItem)
            putBoolean(ARG_BUNDLE_IS_FULL_PAGING_ENABLED, pager.isFullPagingEnabled)
            putBoolean(ARG_BUNDLE_COLOR_TRANSITIONS_ENABLED, isColorTransitionsEnabled)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        with(savedInstanceState) {
            slidesNumber = getInt(ARG_BUNDLE_SLIDES_NUMBER)
            retainIsButtonsEnabled = getBoolean(ARG_BUNDLE_RETAIN_IS_BUTTONS_ENABLED)
            isButtonsEnabled = getBoolean(ARG_BUNDLE_IS_BUTTONS_ENABLED)
            isSkipButtonEnabled = getBoolean(ARG_BUNDLE_IS_SKIP_BUTTON_ENABLED)
            savedCurrentItem = getInt(ARG_BUNDLE_CURRENT_ITEM)
            pager.isFullPagingEnabled = getBoolean(ARG_BUNDLE_IS_FULL_PAGING_ENABLED)
            isColorTransitionsEnabled = getBoolean(ARG_BUNDLE_COLOR_TRANSITIONS_ENABLED)
        }
    }

    override fun onKeyDown(code: Int, event: KeyEvent): Boolean {
        if (code == KeyEvent.KEYCODE_ENTER ||
            code == KeyEvent.KEYCODE_BUTTON_A ||
            code == KeyEvent.KEYCODE_DPAD_CENTER
        ) {
            val isLastSlide = pager.isLastSlide(fragments.size)
            goToNextSlide(isLastSlide)
            if (isLastSlide) {
                onDonePressed(flickIntroPagerAdapter.getItem(pager.currentItem))
            }
            return false
        }
        return super.onKeyDown(code, event)
    }

    override fun onBackPressed() {
        if (isSystemBackButtonLocked) {
            return
        }
        if (pager.isFirstSlide(fragments.size)) {
            super.onBackPressed()
        } else {
            pager.goToPreviousSlide()
        }
    }


    /**
     * On finished click
     *
     * @param callback
     * @receiver
     */
    fun onFinishedClick(
            callback: () -> Unit)
    {
        if(btn.text.equals(R.string.fickintro_done_button)){
            btn.setOnClickListener {
                callback.invoke()
            }
        }
    }

    /**
     * On finished click
     *
     */
    fun onFinishedClick(){

    }

    private fun updateButtonsVisibility() {
        val isLastSlide = pager.isLastSlide(fragments.size)
        val isFirstSlide = pager.isFirstSlide(fragments.size)

        if(isLastSlide){
            btn.text = getString(R.string.fickintro_done_button)
            Log.d("layoutId", "updateButtonsVisibility: isLastSlide");

            btn.setOnClickListener {
                mfinishedClickListener!!.onFinishedClick()
            }
        }else{
            Log.d("layoutId", "updateButtonsVisibility: isFirstSlide");
            btn.text = getString(R.string.fickintro_skip_button)
            btn.setOnClickListener {
                dispatchVibration()
                pager.goToNextSlide() }
        }
    }

    override fun onCanRequestNextPage(): Boolean {
        val currentFragment = flickIntroPagerAdapter.getItem(pager.currentItem)
        return if (currentFragment is FlickIntroSlidePolicy && !currentFragment.isPolicyRespected) {
            LogHelper.d(TAG, "Slide policy not respected, denying change request.")
            false
        } else {
            LogHelper.d(TAG, "Change request will be allowed.")
            true
        }
    }

    override fun onIllegallyRequestedNextPage() {
        val currentFragment = flickIntroPagerAdapter.getItem(pager.currentItem)
        if (currentFragment is FlickIntroSlidePolicy) {
            if (!currentFragment.isPolicyRespected) {
                currentFragment.onUserIllegallyRequestedNextPage()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun dispatchVibration() {
        if (isVibrate) {
            vibrator.vibrate(vibrateDuration)
        }
    }

    private fun dispatchSlideChangedCallbacks(oldFragment: Fragment?, newFragment: Fragment?) {
        if (oldFragment is FlickIntroSlideSelectionListener) {
            oldFragment.onSlideDeselected()
        }
        if (newFragment is FlickIntroSlideSelectionListener) {
            newFragment.onSlideSelected()
        }
        onSlideChanged(oldFragment, newFragment)
    }

    private fun performColorTransition(
            currentSlide: Fragment?,
            nextSlide: Fragment?,
            positionOffset: Float
    ) {
        if (currentSlide is FlickIntroSlideBackgroundColorHolder &&
            nextSlide is FlickIntroSlideBackgroundColorHolder
        ) {
            if (currentSlide.isAdded && nextSlide.isAdded) {
                val newColor = argbEvaluator.evaluate(
                        positionOffset,
                        currentSlide.defaultBackgroundColor,
                        nextSlide.defaultBackgroundColor
                ) as Int
                currentSlide.setBackgroundColor(newColor)
                nextSlide.setBackgroundColor(newColor)
            }
        } else {
            error("Color transitions are only available if all slides implement SlideBackgroundColorHolder.")
        }
    }

    private inner class NextSlideOnClickListener(var isLastSlide: Boolean) : View.OnClickListener {
        override fun onClick(view: View) {
            dispatchVibration()
            // Check if changing to the next slide is allowed
            if (!onCanRequestNextPage()) {
                onIllegallyRequestedNextPage()
                return
            }
            val currentFragment = flickIntroPagerAdapter.getItem(pager.currentItem)
            if (isLastSlide) {
                onDonePressed(currentFragment)
            } else {
                onNextPressed(currentFragment)
            }
            goToNextSlide(isLastSlide)
        }
    }

    /**
     * On page change listener
     *
     * @constructor Create empty On page change listener
     */
    internal inner class OnPageChangeListener : ViewPager.OnPageChangeListener {

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            if (isColorTransitionsEnabled && position < flickIntroPagerAdapter.count - 1) {
                val currentSlide = flickIntroPagerAdapter.getItem(position)
                val nextSlide = flickIntroPagerAdapter.getItem(position + 1)
                performColorTransition(currentSlide, nextSlide, positionOffset)
            }
        }

        override fun onPageSelected(position: Int) {

            updateButtonsVisibility()
            this@FlickIntroBase.onPageSelected(position)
            if (slidesNumber > 0) {
                if (currentlySelectedItem == -1) {
                    dispatchSlideChangedCallbacks(null, flickIntroPagerAdapter.getItem(position))
                } else {
                    dispatchSlideChangedCallbacks(
                            flickIntroPagerAdapter.getItem(currentlySelectedItem),
                            flickIntroPagerAdapter.getItem(pager.currentItem)
                    )
                }
            }
            currentlySelectedItem = position
        }

        override fun onPageScrollStateChanged(state: Int) {}
    }

    private companion object {
        private val TAG = LogHelper.makeLogTag(FlickIntroBase::class.java)

        private const val DEFAULT_SCROLL_DURATION_FACTOR = 1
        private const val DEFAULT_VIBRATE_DURATION = 20L
        private const val ARG_BUNDLE_COLOR_TRANSITIONS_ENABLED = "colorTransitionEnabled"
        private const val ARG_BUNDLE_CURRENT_ITEM = "currentItem"
        private const val ARG_BUNDLE_IS_BUTTONS_ENABLED = "isButtonsEnabled"
        private const val ARG_BUNDLE_IS_FULL_PAGING_ENABLED = "isFullPagingEnabled"
        private const val ARG_BUNDLE_IS_SKIP_BUTTON_ENABLED = "isSkipButtonsEnabled"
        private const val ARG_BUNDLE_RETAIN_IS_BUTTONS_ENABLED = "retainIsButtonsEnabled"
        private const val ARG_BUNDLE_SLIDES_NUMBER = "slidesNumber"
    }
}

