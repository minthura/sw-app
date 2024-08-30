package com.minthuya.sw.di

import android.content.Context
import androidx.lifecycle.ViewModelStoreOwner
import com.minthuya.component.FragmentScope
import com.minthuya.sw.SwActivity
import com.minthuya.sw.ui.EntryFragment
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
            parent: SWDependencies
        ): SWComponent
    }
    fun inject(fragment: EntryFragment)
}