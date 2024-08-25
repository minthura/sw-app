package com.minthuya.codex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.minthuya.codex.di.DaggerApplicationComponent
import com.minthuya.coreappsdk.core.CoreApp
import com.minthuya.navigationkit.CoreAppNavType
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var coreApp: CoreApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerApplicationComponent.factory().create(application).inject(this)
        coreApp.navigateTo(this, CoreAppNavType.FEATURE_SAMPLE)
        finish()
    }
}