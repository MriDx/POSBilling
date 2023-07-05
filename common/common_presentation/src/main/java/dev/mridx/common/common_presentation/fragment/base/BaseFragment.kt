package dev.mridx.common.common_presentation.fragment.base

import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment<ViewBinding> : Fragment() {

    var currentSnackbar: Snackbar? = null

    var currentDialog: BottomSheetDialogFragment? = null

    var binding_: ViewBinding? = null
    val binding get() = binding_!!



    override fun onDestroyView() {
        currentDialog?.dismiss()
        currentSnackbar?.dismiss()
        binding_ = null
        super.onDestroyView()
    }

}