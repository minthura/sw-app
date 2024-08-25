package com.minthuya.sample.di

import com.minthuya.networkkit.NetworkKit

interface SampleDependencies {
    fun provideNetworkKit(): NetworkKit
}