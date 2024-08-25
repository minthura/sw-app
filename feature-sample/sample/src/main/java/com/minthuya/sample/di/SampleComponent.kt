package com.minthuya.sample.di

import androidx.lifecycle.ViewModelStoreOwner
import com.minthuya.component.FragmentScope
import com.minthuya.sample.SampleActivity
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [SampleModule::class],
    dependencies = [SampleDependencies::class]
)
@FragmentScope
interface SampleComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance
            owner: ViewModelStoreOwner,
            parent: SampleDependencies
        ): SampleComponent
    }
    fun inject(activity: SampleActivity)
}