package com.minthuya.sw.util

import com.minthuya.component.base.BaseFragment
import com.minthuya.sw.SwActivity

fun BaseFragment<*>.setTopNavBarVisibility(isVisible: Boolean) {
    (activity as? SwActivity)?.setTopNavBarVisibility(isVisible)
}

fun BaseFragment<*>.setSettingsVisible(isVisible: Boolean) {
    (activity as? SwActivity)?.setSettingsVisible(isVisible)
}

fun BaseFragment<*>.setNavigationIconVisibility(isVisible: Boolean) {
    (activity as? SwActivity)?.setNavigationIconVisibility(isVisible)
}

fun BaseFragment<*>.setTopNavBarTitle(title: String) {
    (activity as? SwActivity)?.setTopNavBarTitle(title)
}