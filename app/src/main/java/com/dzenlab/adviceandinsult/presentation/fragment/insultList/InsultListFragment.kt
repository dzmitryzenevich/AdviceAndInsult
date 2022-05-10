package com.dzenlab.adviceandinsult.presentation.fragment.insultList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dzenlab.adviceandinsult.R
import com.dzenlab.adviceandinsult.databinding.FragmentInsultListBinding
import com.dzenlab.adviceandinsult.models.InsultGetDB
import com.dzenlab.adviceandinsult.presentation.adapter.insult.InsultAdapter
import com.dzenlab.adviceandinsult.presentation.dialog.UpdateDialog
import com.dzenlab.adviceandinsult.presentation.models.ResponseVM
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InsultListFragment : Fragment() {

    private var _binding: FragmentInsultListBinding? = null

    private val binding get() = _binding!!

    private val viewModel: InsultListViewModel by viewModels()

    private var snackBar: Snackbar? = null


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        _binding = FragmentInsultListBinding.inflate(inflater, container, false)

        val root: View = binding.root

        val recyclerView: RecyclerView = binding.adviceRecyclerView

        val textError: MaterialTextView = binding.textError

        val insultAdapter = InsultAdapter(InsultAdapter.InsultDiff(),
            object : InsultAdapter.ClickCallback {

            override fun onClickListener(id: Long, insult: String) {

                UpdateDialog.newInstance(parentFragmentManager, id = id, text = insult)
            }
        })

        recyclerView.adapter = insultAdapter

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){

            override fun onMove(recyclerView: RecyclerView,
                                viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                viewModel.deleteInsult(insultAdapter.getAdviceId(viewHolder.adapterPosition))
            }

        }).attachToRecyclerView(recyclerView)

        viewModel.insultList.observe(viewLifecycleOwner) { response ->

            when(response) {

                is ResponseVM.Success<*> -> {

                    @Suppress("UNCHECKED_CAST", "UNCHECKED_CAST")
                    val list = response.data as List<InsultGetDB>

                    insultAdapter.submitList(list)

                    viewModel.emptyList(list.isEmpty())
                }

                is ResponseVM.Exceptions -> {

                    insultAdapter.submitList(ArrayList())

                    textError.visibility = View.VISIBLE

                    val stringBuilder = StringBuilder()

                    stringBuilder.append(resources.getString(R.string
                        .fragment_insult_list_get_advice_exception))

                    response.message?.let { message ->

                        if(message != "") {

                            stringBuilder.append(": ")

                            stringBuilder.append(message)
                        }
                    }

                    textError.text = stringBuilder.toString()
                }

                is ResponseVM.Error<*> -> {}
            }
        }

        viewModel.deleteInsultResponse.observe(viewLifecycleOwner) { response ->

            response?.let { responseNotNull ->

                when(responseNotNull) {

                    is ResponseVM.Success<*> -> {}

                    is ResponseVM.Exceptions -> {

                        val stringBuilder = StringBuilder()

                        stringBuilder.append(resources.getString(R.string
                            .fragment_insult_list_get_advice_exception))

                        responseNotNull.message?.let { message ->

                            if(message != "") {

                                stringBuilder.append(": ")

                                stringBuilder.append(message)
                            }
                        }

                        snackBar = Snackbar.make(binding.constraintLayout,
                            stringBuilder.toString(), Snackbar.LENGTH_LONG)

                        snackBar?.show()

                        viewModel.deleteInsultResponse.value = null
                    }

                    is ResponseVM.Error<*> -> {}
                }
            }
        }

        viewModel.emptyList.observe(viewLifecycleOwner) {

            if(it) {

                textError.visibility = View.VISIBLE

                textError.setText(R.string.fragment_insult_list_is_empty)

            } else {

                textError.visibility = View.INVISIBLE

                textError.text = ""
            }
        }

        return root
    }

    override fun onDestroyView() {

        super.onDestroyView()

        snackBar?.let {

            if(it.isShown) {

                it.dismiss()
            }
        }

        snackBar = null

        _binding = null
    }

    override fun onStart() {

        super.onStart()

        parentFragmentManager.setFragmentResultListener(UpdateDialog.UPDATE_DIALOG_TAG,
            viewLifecycleOwner, changeInsultListener)
    }

    override fun onStop() {

        super.onStop()

        parentFragmentManager.clearFragmentResultListener(UpdateDialog.UPDATE_DIALOG_TAG)
    }

    private val changeInsultListener : FragmentResultListener = FragmentResultListener {
            requestKey, result ->

        if(requestKey == UpdateDialog.UPDATE_DIALOG_TAG) {

            val id = result.getLong(UpdateDialog.ID_BUNDLE_KEY)

            result.getString(UpdateDialog.TEXT_BUNDLE_KEY)?.let { insult ->

                if(id != 0L) viewModel.updateInsult(id = id, insult = insult)
            }
        }
    }
}