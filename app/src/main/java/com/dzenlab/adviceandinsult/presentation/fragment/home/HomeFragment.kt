package com.dzenlab.adviceandinsult.presentation.fragment.home

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.dzenlab.adviceandinsult.R
import com.dzenlab.adviceandinsult.databinding.FragmentHomeBinding
import com.dzenlab.adviceandinsult.presentation.models.ResponseVM
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var menuHost: MenuHost

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val root: View = binding.root

        (requireActivity() as MenuHost).also { menuHost = it }

        menuHost.addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.RESUMED)

        val textView: MaterialTextView = binding.textHome

        val adviceButton: MaterialButton = binding.buttonAdvice

        val insultButton: MaterialButton = binding.buttonInsult

        textView.setText(R.string.fragment_main_text)

        viewModel.countAdvice.observe(viewLifecycleOwner) { response ->

            val stringBuilder = StringBuilder()

            when(response) {

                is ResponseVM.Success<*> -> {

                    stringBuilder.append(resources.getString(R.string
                        .fragment_main_get_count_advice_ok))

                    stringBuilder.append(": ")

                    stringBuilder.append(response.data as String)
                }

                is ResponseVM.Exceptions -> {

                    stringBuilder.append(resources.getString(R.string
                        .fragment_main_get_count_advice_exception))

                    response.message?.let { message ->

                        if(message != "") {

                            stringBuilder.append(": ")

                            stringBuilder.append(message)
                        }
                    }
                }

                is ResponseVM.Error<*> ->
                    stringBuilder.append(resources.getString(R.string
                        .fragment_main_get_count_advice_error))
            }

            adviceButton.text = stringBuilder.toString()
        }

        viewModel.countInsult.observe(viewLifecycleOwner) { response ->

            val stringBuilder = StringBuilder()

            when(response) {

                is ResponseVM.Success<*> -> {

                    stringBuilder.append(resources.getString(R.string
                        .fragment_main_get_count_insult_ok))

                    stringBuilder.append(": ")

                    stringBuilder.append(response.data as String)
                }

                is ResponseVM.Exceptions -> {

                    stringBuilder.append(resources.getString(R.string
                        .fragment_main_get_count_insult_exception))

                    response.message?.let { message ->

                        if(message != "") {

                            stringBuilder.append(": ")

                            stringBuilder.append(message)
                        }
                    }
                }

                is ResponseVM.Error<*> ->
                    stringBuilder.append(resources.getString(R.string
                        .fragment_main_get_count_insult_error))
            }

            insultButton.text = stringBuilder.toString()
        }

        adviceButton.setOnClickListener {

            it.findNavController().navigate(R.id.action_nav_home_to_nav_advice_list)
        }

        insultButton.setOnClickListener {

            it.findNavController().navigate(R.id.action_nav_home_to_nav_insult_list)
        }

        return root
    }

    override fun onDestroyView() {

        super.onDestroyView()

        menuHost.removeMenuProvider(menuProvider)

        _binding = null
    }

    private val menuProvider: MenuProvider = object : MenuProvider {

        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

            menuInflater.inflate(R.menu.fragment_main, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean  = when(menuItem.itemId) {

            R.id.action_advice -> {

                binding.constraintLayout
                    .findNavController().navigate(R.id.action_nav_home_to_nav_advice)

                true
            }

            R.id.action_insult -> {

                binding.constraintLayout
                    .findNavController().navigate(R.id.action_nav_home_to_nav_insult)

                true
            }

            else -> false
        }
    }
}