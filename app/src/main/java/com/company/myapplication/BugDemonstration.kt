package com.company.myapplication

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class BugDemonstration @AssistedInject constructor(
    @Assisted private val id: Int,
) {

    @AssistedFactory
    interface Factory  {
        fun create(id: Int): BugDemonstration
    }
}
