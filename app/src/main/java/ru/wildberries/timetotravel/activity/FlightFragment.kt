package ru.wildberries.timetotravel.activity

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.wildberries.timetotravel.R
import ru.wildberries.timetotravel.databinding.FragmentFlightBinding
import ru.wildberries.timetotravel.util.TokenArg
import ru.wildberries.timetotravel.util.dateTimeFormat
import ru.wildberries.timetotravel.viewmodel.FlightViewModel

class FlightFragment : Fragment() {

    companion object {
        var Bundle.tokenArg: String? by TokenArg
    }

    private var _binding: FragmentFlightBinding? = null

    private val binding: FragmentFlightBinding
        get() = _binding!!

    private val viewModel: FlightViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFlightBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val flightToken = arguments?.tokenArg ?: ""
        viewModel.data.observe(viewLifecycleOwner) { state ->
            val flight = state.flights.find { it.searchToken == flightToken } ?: return@observe

            with(binding) {
                fragmentCard.headerCity.text = flight.endCity
                fragmentCard.startDate.text =
                    root.context.getString(R.string.there_text, flight.startDate.dateTimeFormat())
                fragmentCard.thereStartCity.text = flight.startCity
                fragmentCard.thereStartLocationCode.text = flight.startLocationCode
                fragmentCard.thereEndCity.text = flight.endCity
                fragmentCard.thereEndLocationCode.text = flight.endLocationCode
                fragmentCard.endDate.text =
                    root.context.getString(R.string.back_text, flight.endDate.dateTimeFormat())
                fragmentCard.backStartCity.text = flight.endCity
                fragmentCard.backStartLocationCode.text = flight.endLocationCode
                fragmentCard.backEndCity.text = flight.startCity
                fragmentCard.backEndLocationCode.text = flight.startLocationCode
                fragmentCard.price.text =
                    root.context.getString(R.string.price_text, flight.price.toString())
                fragmentCard.like.isChecked = flight.likeByMe

                fragmentCard.like.setOnClickListener {
                    viewModel.likeByToken(flightToken)
                }

                binding.back.setOnClickListener {
                    findNavController().navigateUp()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}