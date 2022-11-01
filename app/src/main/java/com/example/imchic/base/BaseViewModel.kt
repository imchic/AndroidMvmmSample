package com.example.imchic.base

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imchic.view.dialog.LoadingDialogFragment
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

/**
 * @property _eventFlow MutableSharedFlow<Event>
 * @property eventFlow SharedFlow<Event>
 * @property _theme MutableStateFlow<String>
 * @property theme MutableStateFlow<String>
 * @property _themePos MutableStateFlow<Int>
 * @property themePos MutableStateFlow<Int>
 */

open class BaseViewModel(val context: Context) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _theme = MutableStateFlow("")
    val theme: MutableStateFlow<String> = _theme

    private val _themePos = MutableStateFlow(0)
    val themePos: MutableStateFlow<Int> = _themePos


    fun setTheme(getTheme: String) {
        viewModelScope.launch {
            _theme.value = getTheme
        }
    }

    fun setThemePos(themePos: Int) {
        viewModelScope.launch {
            _themePos.value = themePos
        }
    }

    //    fun setTheme(theme: String) = event(Event.SetTheme(theme))
    fun showLoadingBar(bool: Boolean) = event(Event.ShowLoadingBar(bool))
    fun showSnackbar(stringResourceId: Int) = event(Event.ShowSnackBar(stringResourceId))
    fun showSnackbarString(str: String) = event(Event.ShowSnackbarString(str))
    fun showToast(stringResourceId: Int) = event(Event.ShowToast(stringResourceId))
    fun showToastString(str: String) = event(Event.ShowToastString(str))
    fun showAlertDialog(data: ArrayList<String>) = event(Event.ShowAlertDialog(data))
    fun themeSelectAlertDialog(data: Array<String>) = event(Event.ThemeSelectAlertDialog(data))

    private fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

    sealed class Event {
        data class ShowSnackBar(val text: Int) : Event()
        data class ShowSnackbarString(val text: String) : Event()
        data class ShowToast(val text: Int) : Event()
        data class ShowToastString(val text: String) : Event()
        data class ShowAlertDialog(val data: ArrayList<String>) : Event()
        data class ThemeSelectAlertDialog(val data: Array<String>) : Event()
        data class ShowLoadingBar(val isShow: Boolean) : Event()

    }

}