package com.company.myapplication

import javax.inject.Inject

class Target @Inject constructor(factory: BugDemonstration.Factory) {

    @Inject
    lateinit var factory: BugDemonstration.Factory
}
