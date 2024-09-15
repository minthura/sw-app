package com.minthuya.sw.navigator

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.minthuya.sw.R

interface SWInternalNavigator {
    fun navigateToListScreen()
    fun navigateToSettings()
}

class SWInternalNavigatorImpl(
    private val navController: NavController
) : SWInternalNavigator {

    private val defaultNavOptions = NavOptions.Builder().apply {
        setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
        setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
        setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
        setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim)
    }.build()

    override fun navigateToListScreen() {
        navigate(R.id.action_entry_fragment_to_list_fragment)
    }

    override fun navigateToSettings() {
        navigate(R.id.swSettingsfragment)
    }

    private fun navigate(route: Int) {
        navController.navigate(route, null, defaultNavOptions)
    }
}