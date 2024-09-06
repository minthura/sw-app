package com.minthuya.sw.navigator

import androidx.navigation.NavController
import com.minthuya.sw.R

interface SWInternalNavigator {
    fun navigateToListScreen()
}

class SWInternalNavigatorImpl(
    private val navController: NavController
) : SWInternalNavigator {
    override fun navigateToListScreen() {
        navController.navigate(R.id.action_entry_fragment_to_list_fragment)
    }
}