package dev.mridx.common_presentation.dialog.bottom_loading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.mridx.common_presentation.databinding.BottomLoadingDialogBinding
import dev.mridx.common_presentation.dialog.rounded_dialog.RoundedDialogFragment

class BottomLoadingDialog : RoundedDialogFragment() {


    class Builder {

        private val args = bundleOf()

        private var cancellable: Boolean = false

        fun cancelable(cancellable: Boolean): Builder {
            this.cancellable = cancellable
            return this
        }


        fun show(
            fragmentManager: FragmentManager,
            tag: String = "BottomMessageDialog"
        ): BottomSheetDialogFragment {
            val dialog = BottomLoadingDialog().apply {
                arguments = args
                //onDoneListener = listener_
            }
            dialog.isCancelable = cancellable
            dialog.show(fragmentManager, tag)
            return dialog
        }

    }

    private var binding_: BottomLoadingDialogBinding? = null
    private val binding get() = binding_!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding_ = BottomLoadingDialogBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }


    override fun onDestroyView() {
        binding_ = null
        super.onDestroyView()
    }


}