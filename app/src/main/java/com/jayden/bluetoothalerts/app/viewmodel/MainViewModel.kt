package com.jayden.bluetoothalerts.app.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MainViewModel() : ViewModel() {
    fun onClickMonitorModeSetting(option: String) {
        // do you know what to do? Just flip the option in store!
        // but idk how, the different representations of the same information
        // maybe don't hold many ways to represent information?
        // just make a way that the main activity doesn't have to lie and there's one way to change it!
    }
    fun onClickFoo(state: Boolean) { /* TODO: Update UI, this is foo */ }
}