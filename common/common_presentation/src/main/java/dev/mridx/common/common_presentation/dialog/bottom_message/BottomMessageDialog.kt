package dev.mridx.common.common_presentation.dialog.bottom_message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.mridx.common.common_presentation.databinding.BottomMessageDialogBinding
import dev.mridx.common.common_presentation.dialog.rounded_dialog.RoundedDialogFragment
import dev.mridx.common.common_presentation.fragment.builder.FragmentBuilder


class BottomMessageDialog : RoundedDialogFragment() {


    class Builder : FragmentBuilder<BottomMessageDialog>() {

        private val args = bundleOf()

        private var listener_: ((dialog: BottomSheetDialogFragment) -> Unit)? = null

        fun setMessage(message: String): Builder {
            args.putString("message", message)
            return this
        }

        fun setAction(text: String, listener: (dialog: BottomSheetDialogFragment) -> Unit) {
            listener_ = listener
            args.putString("action_text", text)
        }

        fun setActionClicked(listener: ((dialog: BottomSheetDialogFragment) -> Unit)): Builder {
            listener_ = listener
            return this
        }

        fun show(fragmentManager: FragmentManager, tag: String = "BottomMessageDialog") {
            build().show(fragmentManager, tag)
        }

        override fun build(): BottomMessageDialog {
            return BottomMessageDialog().apply {
                arguments = args
                onDoneListener = listener_
            }
        }


    }


    private var binding_: BottomMessageDialogBinding? = null
    private val binding get() = binding_!!


    var onDoneListener: ((dialog: BottomSheetDialogFragment) -> Unit)? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding_ = BottomMessageDialogBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isCancelable = false

        val message = arguments?.getString("message")
        val actionText = arguments?.getString("action_text")

        binding.successMessageView.text = message

        binding.doneBtn.text = actionText

        binding.doneBtn.setOnClickListener {
            if (onDoneListener == null) {
                dismiss()
                return@setOnClickListener
            }
            onDoneListener!!.invoke(this)
        }

    }


    override fun onDestroyView() {
        binding_ = null
        super.onDestroyView()
    }


}