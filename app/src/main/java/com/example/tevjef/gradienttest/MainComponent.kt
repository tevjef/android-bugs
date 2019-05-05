package com.example.tevjef.gradienttest

import dagger.Component

@Component
interface MainComponent {
    fun inject(activity: MainActivity)
}