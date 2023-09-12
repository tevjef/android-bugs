package com.company.myapplication

import dagger.assisted.AssistedFactory

@AssistedFactory
interface Factory  {
    fun create(id: Int): BugDemonstration
}
