package com.minthuya.coreappsdk.di

import android.content.Context
import com.minthuya.component.AppScope
import com.minthuya.coreappsdk.FeatureComponent
import com.minthuya.coreappsdk.FeatureModule
import com.minthuya.coreappsdk.core.CoreAppImpl
import com.minthuya.navigation.di.CoreAppNavModule
import com.minthuya.network.di.NetworkModule
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
    modules = [
        CoreAppModule::class,
        FeatureModule::class,
        CoreAppNavModule::class,
        NetworkModule::class
    ]
)
interface CoreAppComponent : FeatureComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            coreAppModule: CoreAppModule
        ): CoreAppComponent
    }
    fun inject(core: CoreAppImpl)
}