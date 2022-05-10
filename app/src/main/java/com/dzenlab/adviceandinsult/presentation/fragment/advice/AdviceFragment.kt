package com.dzenlab.adviceandinsult.presentation.fragment.advice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.dzenlab.adviceandinsult.R
import com.dzenlab.adviceandinsult.databinding.FragmentAdviceBinding
import com.dzenlab.adviceandinsult.presentation.models.CreateItemError
import com.dzenlab.adviceandinsult.presentation.models.ResponseVM
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdviceFragment : Fragment() {

    private var _binding: FragmentAdviceBinding? = null

    private val binding get() = _binding!!

    private val viewModel: AdviceViewModel by viewModels()

    private var snackBar: Snackbar? = null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        _binding = FragmentAdviceBinding.inflate(inflater, container, false)

        val root: View = binding.root

        val textView: MaterialTextView = binding.textAdvice

        val getButton: MaterialButton = binding.button

        val saveButton: MaterialButton = binding.saveButton

        val listButton: MaterialButton = binding.listButton

        val progressBar: CircularProgressIndicator = binding.circularProgressIndicator

        viewModel.getAdviceResponse.observe(viewLifecycleOwner) { response ->

            when(response) {

                is ResponseVM.Success<*> -> {

                    textView.text = response.data as String
                }

                is ResponseVM.Exceptions -> {

                    val stringBuilder = StringBuilder()

                    stringBuilder.append(resources.getString(R.string
                        .fragment_advice_get_advice_exception))

                    response.message?.let { message ->

                        if(message != "") {

                            stringBuilder.append(": ")

                            stringBuilder.append(message)
                        }
                    }

                    textView.text = stringBuilder.toString()
                }

                is ResponseVM.Error<*> -> {

                    val stringBuilder = StringBuilder()

                    stringBuilder.append(resources.getString(R.string
                        .fragment_advice_get_advice_error))

                    textView.text = stringBuilder.toString()
                }
            }
        }

        viewModel.saveAdviceResponse.observe(viewLifecycleOwner) { response ->

            response?.let { responseNotNull ->

                val stringBuilder = StringBuilder()

                when(responseNotNull) {

                    is ResponseVM.Success<*> -> {

                        stringBuilder.append(resources.getString(R.string
                            .fragment_advice_save_in_database_ok))

                        stringBuilder.append(": ")

                        val count = responseNotNull.data as Long

                        stringBuilder.append(count.toString())
                    }

                    is ResponseVM.Exceptions -> {

                        stringBuilder.append(resources.getString(R.string
                            .fragment_advice_save_in_database_exception))

                        responseNotNull.message?.let { message ->

                            if(message != "") {

                                stringBuilder.append(": ")

                                stringBuilder.append(message)
                            }
                        }
                    }

                    is ResponseVM.Error<*> -> {

                        stringBuilder.append(resources.getString(R.string
                            .fragment_advice_save_in_database_error))

                        stringBuilder.append(": ")

                        when(responseNotNull.code as CreateItemError) {

                            CreateItemError.NEW_ITEM ->
                                stringBuilder.append(resources.getString(R.string
                                    .fragment_advice_save_in_database_error_new_advice))

                            CreateItemError.ITEM_IS_EXIST ->
                                stringBuilder.append(resources.getString(R.string
                                    .fragment_advice_save_in_database_error_advice_is_exist))
                        }
                    }
                }

                snackBar = Snackbar.make(binding.constraintLayout,
                    stringBuilder.toString(), Snackbar.LENGTH_LONG)

                snackBar?.show()

                viewModel.saveAdviceResponse.value = null
            }
        }

        viewModel.processing.observe(viewLifecycleOwner) {

            if(it) {

                progressBar.show()

            } else {

                progressBar.hide()
            }
        }

        viewModel.buttonGet.observe(viewLifecycleOwner) {

            if(it) {

                getButton.setText(R.string.fragment_button_get_text)

            } else {

                getButton.setText(R.string.fragment_button_cancel_text)
            }
        }

        viewModel.progress.observe(viewLifecycleOwner) {

            progressBar.progress = it
        }

        getButton.setOnClickListener {

            dismissSnackBar()

            viewModel.clickGetButton()
        }

        saveButton.setOnClickListener {

            dismissSnackBar()

            viewModel.clickSaveButton()
        }

        listButton.setOnClickListener {

            dismissSnackBar()

            it.findNavController().navigate(R.id.action_nav_advice_to_nav_advice_list)
        }

        return root
    }

    override fun onDestroyView() {

        super.onDestroyView()

        dismissSnackBar()

        _binding = null
    }

    override fun onResume() {

        super.onResume()

        viewModel.processing.value?.let {

            if(it) {

                binding.circularProgressIndicator.show()

            } else {

                binding.circularProgressIndicator.hide()
            }
        }
    }

    override fun onPause() {

        super.onPause()

        binding.circularProgressIndicator.hide()
    }

    private fun dismissSnackBar() {

        snackBar?.let {

            if(it.isShown) {

                it.dismiss()
            }
        }

        snackBar = null
    }
}