package com.company.myapplication

import dagger.Component

@Component(modules = [Module::class])
interface Component {
    fun inject(target: Target)
}
