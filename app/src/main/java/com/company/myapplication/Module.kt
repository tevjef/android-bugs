package com.company.myapplication

import dagger.Module
import dagger.Provides

@Module
class Module {

    @Provides
    fun provideBugDemonstration(factory: BugDemonstration.Factory): Int {
        println(factory.create(1))
        return 1
    }
}
