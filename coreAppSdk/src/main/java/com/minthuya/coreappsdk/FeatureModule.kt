package com.minthuya.coreappsdk

import com.minthuya.localdb.di.LocalDbModule
import com.minthuya.sample.di.SampleNavModule
import com.minthuya.sw.di.SWNavModule
import dagger.Module

@Module(
    includes = [
        SampleNavModule::class,
        SWNavModule::class,
        LocalDbModule::class,
    ]
)
object FeatureModule