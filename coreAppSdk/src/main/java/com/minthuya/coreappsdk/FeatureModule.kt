package com.minthuya.coreappsdk

import com.minthuya.sample.di.SampleNavModule
import dagger.Module

@Module(
    includes = [
        SampleNavModule::class
    ]
)
object FeatureModule