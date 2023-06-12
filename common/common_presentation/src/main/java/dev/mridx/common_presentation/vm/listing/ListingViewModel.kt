package dev.mridx.common_presentation.vm.listing

import androidx.lifecycle.viewModelScope
import dev.mridx.common.common_data.data.local.model.ui_error.UIErrorModel
import dev.mridx.common_presentation.vm.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class ListingViewModel<Event, State> : BaseViewModel<Event, State>() {


    private var errorState_ = Channel<UIErrorModel>()
    val errorState = errorState_.receiveAsFlow()

    open fun setErrorState(uiError: UIErrorModel) {
        viewModelScope.launch(Dispatchers.IO) {
            errorState_.send(uiError)
        }
    }


}