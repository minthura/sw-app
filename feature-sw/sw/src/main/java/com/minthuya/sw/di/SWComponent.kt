package com.minthuya.sw.di

import android.content.Context
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import com.minthuya.component.FragmentScope
import com.minthuya.sw.SwActivity
import com.minthuya.sw.ui.list.StationsListFragment
import com.minthuya.sw.ui.setting.SettingsFragment
import com.minthuya.sw.ui.sync.SyncStationsFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [SWModule::class],
    dependencies = [SWDependencies::class]
)
@FragmentScope
interface SWComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance
            owner: ViewModelStoreOwner,
            @BindsInstance
            context: Context,
            @BindsInstance
            navController: NavController,
            parent: SWDependencies
        ): SWComponent
    }
    fun inject(activity: SwActivity)
    fun inject(fragment: SyncStationsFragment)
    fun inject(fragment: StationsListFragment)
    fun inject(fragment: SettingsFragment)
}