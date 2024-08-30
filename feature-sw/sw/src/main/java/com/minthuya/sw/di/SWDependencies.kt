package com.minthuya.sw.di

import com.minthuya.localdbkit.LocalDbKit
import com.minthuya.networkkit.NetworkKit

interface SWDependencies {
    fun provideNetworkKit(): NetworkKit
    fun provideLocalDbKit(): LocalDbKit
}