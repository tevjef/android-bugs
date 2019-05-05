package com.example.tevjef.gradienttest

import javax.inject.Inject

class SomeDependency @Inject constructor() {
    fun hello(): String = "Hello Wolrd"
}