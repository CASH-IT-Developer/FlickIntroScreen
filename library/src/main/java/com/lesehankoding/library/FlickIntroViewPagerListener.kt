package com.lesehankoding.library

/**
 * Flick intro view pager listener
 *
 * @constructor Create empty Flick intro view pager listener
 */
interface FlickIntroViewPagerListener {
    /**
     * On can request next page
     *
     * @return
     */
    fun onCanRequestNextPage(): Boolean

    /**
     * On illegally requested next page
     *
     */
    fun onIllegallyRequestedNextPage()

//    fun onUserRequestedPermissionsDialog()
}
