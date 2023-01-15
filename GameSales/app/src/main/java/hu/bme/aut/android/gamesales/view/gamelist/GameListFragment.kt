package hu.bme.aut.android.gamesales.view.gamelist

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.devhoony.lottieproegressdialog.LottieProgressDialog
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.android.gamesales.R
import hu.bme.aut.android.gamesales.databinding.FragmentGameListBinding
import hu.bme.aut.android.gamesales.adapter.GameListAdapter
import hu.bme.aut.android.gamesales.database.DealInfoDatabase
import hu.bme.aut.android.gamesales.model.DealState
import hu.bme.aut.android.gamesales.model.StoreState
import hu.bme.aut.android.gamesales.model.response.DealInfo
import hu.bme.aut.android.gamesales.network.NetworkManager
import hu.bme.aut.android.gamesales.viewmodel.gamelist.GameListFragmentViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class GameListFragment : Fragment(), GameListAdapter.OnDealSelectedListener {

    private lateinit var binding: FragmentGameListBinding

    private val viewModel: GameListFragmentViewModel by viewModels()

    private lateinit var adapter: GameListAdapter

    private lateinit var loadingDialog: LottieProgressDialog

    private lateinit var database: DealInfoDatabase

    private var state: Parcelable? = null

    private var priceFilter = arrayOf(0, 25)
    private var percentFilter = arrayOf(75, 100)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = DealInfoDatabase.getDatabase(requireContext())

        adapter = GameListAdapter(requireContext(), this)

        viewModel.deals.observe(viewLifecycleOwner
        ) { jobDetailState ->
            render(jobDetailState)
        }

        //reloadFromDatabase()

        loadingDialog = LottieProgressDialog(
            context = requireContext(),
            isCancel = true,
            dialogWidth = null,
            dialogHeight = null,
            animationViewWidth = null,
            animationViewHeight = null,
            fileName = LottieProgressDialog.SAMPLE_8,
            title = getString(R.string.loading),
            titleVisible = null
        )

        binding.gamesRecyclerView.adapter = adapter

        binding.expandButton.setOnClickListener {
            if (binding.expandableLayout.isExpanded) {
                binding.expandableLayout.collapse()
                binding.collapseArrow.rotation = 90f
            }
            else {
                binding.expandableLayout.expand()
                binding.collapseArrow.rotation = 0f
            }
        }

        binding.rsPriceFilter.addOnChangeListener { slider, value, fromUser ->
            priceFilter = arrayOf(slider.values[0].toInt(), slider.values[1].toInt())
            filterList()
        }
        binding.rsSalePercentFilter.addOnChangeListener { slider, value, fromUser ->
            percentFilter = arrayOf(slider.values[0].toInt(), slider.values[1].toInt())
            filterList()
        }
    }

    private fun filterList() {
        adapter.filterItems(priceFilter, percentFilter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGameListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.gamesRecyclerView.getLayoutManager()?.onRestoreInstanceState(state)
    }

    private fun render(state: DealState) {
        when (state) {
            is DealState.inProgress -> {
                loadingDialog.show()
            }
            is DealState.dealsResponseSuccess -> {
                loadingDialog.dismiss()
                adapter.addDeals(state.data)
                replaceDatabase(state.data)
            }
            is DealState.dealsResponseError -> {
                loadingDialog.dismiss()
                reloadFromDatabase()
                Snackbar.make(requireView(), getString(R.string.loaded_from_database), Snackbar.LENGTH_SHORT).show()

            }
        }
    }

    override fun onDealSelected(deal: DealInfo?) {
        val bundle = Bundle()
        bundle.putSerializable("selectedDeal", deal)

        findNavController().navigate(R.id.action_gameListFragment_to_gameDetailsFragment, bundle)
    }

    fun replaceDatabase(deals: List<DealInfo>) {
        GlobalScope.launch {
            database.dealInfoDao().deleteAll()
            deals.forEach {
                database.dealInfoDao().insert(it)
            }
        }
    }

    private fun reloadFromDatabase(){
        val list: MutableList<DealInfo> = mutableListOf()
        GlobalScope.launch {
            list.addAll(database.dealInfoDao().getAll())
        }.invokeOnCompletion {
            requireActivity().runOnUiThread {
                if (adapter.itemCount == 0) {
                    adapter.addDeals(list)
                }
            }
        }

    }
}