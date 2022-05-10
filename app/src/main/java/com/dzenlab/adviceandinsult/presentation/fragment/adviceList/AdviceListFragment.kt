package com.dzenlab.adviceandinsult.presentation.fragment.adviceList

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dzenlab.adviceandinsult.R
import com.dzenlab.adviceandinsult.databinding.FragmentAdviceListBinding
import com.dzenlab.adviceandinsult.models.AdviceGetDB
import com.dzenlab.adviceandinsult.presentation.adapter.advice.AdviceAdapter
import com.dzenlab.adviceandinsult.presentation.models.ResponseVM
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdviceListFragment : Fragment() {

    private var _binding: FragmentAdviceListBinding? = null

    private val binding get() = _binding!!

    private val viewModel: AdviceListViewModel by viewModels()

    private var snackBar: Snackbar? = null


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        _binding = FragmentAdviceListBinding.inflate(inflater, container, false)

        val root: View = binding.root

        val recyclerView: RecyclerView = binding.adviceRecyclerView

        val textError: MaterialTextView = binding.textError

        val adviceAdapter = AdviceAdapter(AdviceAdapter.AdviceDiff(),
            viewModel.deleteList.value ?: ArrayList(),
            object : AdviceAdapter.ClickCallback {

                override fun onLongClickListener(id: Long) {

                    viewModel.addDelDeleteItem(id)
                }
        })

        recyclerView.adapter = adviceAdapter

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){

            override fun onMove(recyclerView: RecyclerView,
                                viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                viewModel.deleteAdvice(adviceAdapter.getAdviceId(viewHolder.adapterPosition))
            }

        }).attachToRecyclerView(recyclerView)

        viewModel.adviceList.observe(viewLifecycleOwner) { response ->

            when(response) {

                is ResponseVM.Success<*> -> {

                    @Suppress("UNCHECKED_CAST", "UNCHECKED_CAST")
                    val list = response.data as List<AdviceGetDB>

                    recyclerView.setItemViewCacheSize(list.size)

                    adviceAdapter.submitList(list)

                    viewModel.emptyList(list.isEmpty())
                }

                is ResponseVM.Exceptions -> {

                    adviceAdapter.submitList(ArrayList())

                    textError.visibility = View.VISIBLE

                    val stringBuilder = StringBuilder()

                    stringBuilder.append(resources.getString(R.string
                        .fragment_advice_list_get_advice_exception))

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

        viewModel.deleteAdviceResponse.observe(viewLifecycleOwner) { response ->

            response?.let { responseNotNull ->

                when(responseNotNull) {

                    is ResponseVM.Success<*> -> {}

                    is ResponseVM.Exceptions -> {

                        val stringBuilder = StringBuilder()

                        stringBuilder.append(resources.getString(R.string
                            .fragment_advice_list_get_advice_exception))

                        responseNotNull.message?.let { message ->

                            if(message != "") {

                                stringBuilder.append(": ")

                                stringBuilder.append(message)
                            }
                        }

                        snackBar = Snackbar.make(binding.constraintLayout,
                            stringBuilder.toString(), Snackbar.LENGTH_LONG)

                        snackBar?.show()

                        viewModel.deleteAdviceResponse.value = null
                    }

                    is ResponseVM.Error<*> -> {}
                }
            }
        }

        viewModel.deleteList.observe(viewLifecycleOwner) { list ->

            setHasOptionsMenu(list.isNotEmpty())
        }

        viewModel.emptyList.observe(viewLifecycleOwner) {

            if(it) {

                textError.visibility = View.VISIBLE

                textError.setText(R.string.fragment_advice_list_is_empty)

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.fragment_advice_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {

        R.id.action_delete -> {

            viewModel.deleteChecked()

            true
        }

        else -> super.onOptionsItemSelected(item)
    }
}