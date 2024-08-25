package com.minthuya.codex.di

import android.app.Application
import com.minthuya.codex.CodexApplication
import com.minthuya.codex.MainActivity
import com.minthuya.component.AppScope
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
    modules = [
        ApplicationModule::class
    ]
)
interface ApplicationComponent {

    // Factory to create instances of the AppComponent
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Application): ApplicationComponent
    }
    fun inject(application: CodexApplication)
    fun inject(activity: MainActivity)
}