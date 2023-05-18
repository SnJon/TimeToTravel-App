package ru.wildberries.timetotravel.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.wildberries.timetotravel.activity.FlightFragment.Companion.tokenArg
import ru.wildberries.timetotravel.R
import ru.wildberries.timetotravel.adapter.FlightAdapter
import ru.wildberries.timetotravel.adapter.OnInteractionListener
import ru.wildberries.timetotravel.databinding.FragmentFeedBinding
import ru.wildberries.timetotravel.dto.Flight
import ru.wildberries.timetotravel.viewmodel.FlightViewModel

class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding: FragmentFeedBinding
        get() = _binding!!

    private val viewModel: FlightViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    private val interaction = object : OnInteractionListener {

        override fun onLike(flight: Flight) {
            viewModel.likeByToken(flight.searchToken)
        }

        override fun onOpen(flight: Flight) {
            findNavController().navigate(
                R.id.action_feedFragment_to_flightFragment,
                Bundle().apply { tokenArg = flight.searchToken })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe(binding)
    }

    private fun subscribe(binding: FragmentFeedBinding) {
        val adapter = FlightAdapter(interaction)
        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.flights)
            binding.progress.isVisible = state.loading
            binding.errorGroup.isVisible = state.error
            binding.emptyText.isVisible = state.empty
        }

        binding.retryButton.setOnClickListener {
            viewModel.loadFlights()
        }

        binding.refresh.setOnRefreshListener {
            viewModel.loadFlights()
            binding.refresh.isRefreshing = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}