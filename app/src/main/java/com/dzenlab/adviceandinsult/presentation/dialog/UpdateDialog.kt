package com.dzenlab.adviceandinsult.presentation.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.dzenlab.adviceandinsult.R
import com.dzenlab.adviceandinsult.databinding.DialogUpdateBinding

class UpdateDialog : DialogFragment() {

    private var _binding: DialogUpdateBinding? = null

    private val binding get() = _binding!!

    companion object {

        const val UPDATE_DIALOG_TAG = "update_dialog_tag"

        const val ID_BUNDLE_KEY = "update_dialog_id_key"

        const val TEXT_BUNDLE_KEY = "update_dialog_text_key"

        fun newInstance(manager: FragmentManager, id: Long, text: String) {

            val bundle = Bundle()

            bundle.putLong(ID_BUNDLE_KEY, id)

            bundle.putString(TEXT_BUNDLE_KEY, text)

            val updateDialog = UpdateDialog()

            updateDialog.arguments = bundle

            updateDialog.show(manager, UPDATE_DIALOG_TAG)
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let { activity ->

            val builder = AlertDialog.Builder(activity)

            _binding = DialogUpdateBinding.inflate(activity.layoutInflater, null, false)

            builder.setView(binding.root)

            builder.setPositiveButton(R.string.dialog_update_ok, listenerPositiveButton)

            builder.setNegativeButton(R.string.dialog_cancel, listenerNegativeButton)

            builder.create()

        } ?: super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        arguments?.getString(TEXT_BUNDLE_KEY)?.let { text ->

            binding.dialogUpdateEditText.hint = text
        }

        return binding.root
    }

    override fun onDestroyView() {

        super.onDestroyView()

        _binding = null
    }

    private val listenerPositiveButton: DialogInterface.OnClickListener = DialogInterface
        .OnClickListener { _, _ ->

            val oldText = arguments?.getString(TEXT_BUNDLE_KEY) ?: binding.dialogUpdateEditText.hint

            val newText = binding.dialogUpdateEditText.text.toString()

            if(newText != "" && oldText != newText) {

                val bundle = Bundle()

                bundle.putLong(ID_BUNDLE_KEY, arguments?.getLong(ID_BUNDLE_KEY) ?: 0)

                bundle.putString(TEXT_BUNDLE_KEY, newText)

                parentFragmentManager.setFragmentResult(UPDATE_DIALOG_TAG, bundle)
            }

            dismiss()
        }

    private val listenerNegativeButton: DialogInterface.OnClickListener = DialogInterface
        .OnClickListener { _, _ ->

            dismiss()
        }
}