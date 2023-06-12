package dev.mridx.common.common_data.data.local.model.ui_error

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class UIErrorModel(
    val showError: Boolean = false,
    val errorMessage: String? = null,
) : Parcelable {

    companion object {
        fun show(message: String): UIErrorModel {
            return UIErrorModel(showError = true, errorMessage = message)
        }

        fun hide(): UIErrorModel {
            return UIErrorModel(showError = false)
        }
    }


}