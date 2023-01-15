package hu.bme.aut.android.gamesales.view.gamedetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.devhoony.lottieproegressdialog.LottieProgressDialog
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.android.gamesales.R
import hu.bme.aut.android.gamesales.databinding.FragmentGameDetailsBinding
import hu.bme.aut.android.gamesales.model.GameInfoState
import hu.bme.aut.android.gamesales.model.StoreState
import hu.bme.aut.android.gamesales.model.response.DealInfo
import hu.bme.aut.android.gamesales.viewmodel.gamedetails.GameDetailsFragmentViewModel


class GameDetailsFragment : Fragment() {

    private lateinit var binding: FragmentGameDetailsBinding

    private val viewModel: GameDetailsFragmentViewModel by viewModels()

    private lateinit var selectedDeal: DealInfo

    private lateinit var loadingDialog: LottieProgressDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            selectedDeal = it.getSerializable("selectedDeal") as DealInfo
        }

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

        Glide.with(requireContext()).load(selectedDeal.thumb).into(binding.ivGameImage);
        binding.tvTitle.text = selectedDeal.title
        binding.tvNormalPrice.text = selectedDeal.normalPrice
        binding.tvSalePrice.text = selectedDeal.salePrice
        val normalPrice:Float = selectedDeal.normalPrice?.toFloat() ?: 0f
        val salePrice = selectedDeal.salePrice?.toFloat() ?: 0f
        binding.tvSalePercent.text = "-${((1f - (salePrice / normalPrice)) * 100).toInt()}%"

        binding.btnStore.setOnClickListener {
            val id = selectedDeal.dealID ?: ""
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(viewModel.getStoreLink(id)))
            startActivity(browserIntent)
        }

        viewModel.getStores().observe(viewLifecycleOwner
        ) { jobDetailState ->
            render(jobDetailState)
        }


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

        Log.i("STEAM_ID", "" + selectedDeal?.steamAppID)
        if ( selectedDeal.steamAppID == null) {
            noAdditionalData()
        }
        selectedDeal.steamAppID?.let {
            viewModel.getGameInfo(it).observe(viewLifecycleOwner
            ) { jobDetailState ->
                render(jobDetailState)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGameDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun render(state: StoreState) {
        when (state) {
            is StoreState.inProgress -> {
                loadingDialog.show()
            }
            is StoreState.dealsResponseSuccess -> {
                state.data.forEach {
                    if (it.storeID == selectedDeal.storeID) {
                        Glide.with(requireContext()).load("https://www.cheapshark.com/${it.images.logo}").into(binding.ivStoreImage);
                        binding.tvStoreName.text = it.storeName
                    }
                }
                loadingDialog.dismiss()
            }
            is StoreState.dealsResponseError -> {
                loadingDialog.dismiss()
                Snackbar.make(requireView(), getString(R.string.no_internet_connection), Snackbar.LENGTH_SHORT).show()
                noAdditionalData()
                noStoreData()
            }
        }
    }

    private fun render(state: GameInfoState) {
        when (state) {
            is GameInfoState.inProgress -> {
            }
            is GameInfoState.gameResponseSuccess -> {
                binding.tvGameDescreption.text = viewModel.getDescription(state.data, selectedDeal.steamAppID!!)

                binding.tvPcMinRequirements.text = viewModel.getMinRequirements(state.data, selectedDeal.steamAppID!!)
                binding.tvPcRecommendedRequirements.text = viewModel.getRecommendedRequirements(state.data, selectedDeal.steamAppID!!)
            }
            is GameInfoState.gameResponseError -> {
            }
        }
    }

    private fun noAdditionalData() {
        binding.tvGameDescreption.visibility = View.GONE
        binding.expandableLayout.visibility = View.GONE
        binding.expandButton.visibility = View.GONE
    }

    private fun noStoreData() {
        binding.tvStoreName.visibility = View.GONE
        binding.tvStoreLabel.visibility = View.GONE
        binding.ivStoreImage.visibility = View.GONE
        binding.btnStore.visibility = View.GONE
    }

}