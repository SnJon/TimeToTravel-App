package ru.wildberries.timetotravel.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.wildberries.timetotravel.R
import ru.wildberries.timetotravel.databinding.CardFlightBinding
import ru.wildberries.timetotravel.dto.Flight
import ru.wildberries.timetotravel.util.dateTimeFormat

class FlightAdapter(private val onInteractionListener: OnInteractionListener) :
    ListAdapter<Flight, FlightViewHolder>(FlightDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightViewHolder {
        val binding = CardFlightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FlightViewHolder(binding, onInteractionListener)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: FlightViewHolder, position: Int) {
        val flight = getItem(position)
        holder.bind(flight)
    }
}

interface OnInteractionListener {
    fun onLike(flight: Flight) {}
    fun onOpen(flight: Flight) {}
}

class FlightViewHolder(
    private val binding: CardFlightBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(flight: Flight) {
        with(binding) {
            headerCity.text = flight.endCity
            startDate.text = root.context.getString(R.string.there_text, flight.startDate.dateTimeFormat())
            thereStartCity.text = flight.startCity
            thereStartLocationCode.text = flight.startLocationCode
            thereEndCity.text = flight.endCity
            thereEndLocationCode.text = flight.endLocationCode
            endDate.text = root.context.getString(R.string.back_text, flight.endDate.dateTimeFormat())
            backStartCity.text = flight.endCity
            backStartLocationCode.text = flight.endLocationCode
            backEndCity.text = flight.startCity
            backEndLocationCode.text = flight.startLocationCode
            price.text = root.context.getString(R.string.price_text, flight.price.toString())
            like.isChecked = flight.likeByMe

            like.setOnClickListener {
                onInteractionListener.onLike(flight)
            }

            card.setOnClickListener {
                onInteractionListener.onOpen(flight)
            }

        }
    }
}

class FlightDiffCallBack : DiffUtil.ItemCallback<Flight>() {
    override fun areItemsTheSame(oldItem: Flight, newItem: Flight): Boolean {
        return oldItem.searchToken == newItem.searchToken
    }

    override fun areContentsTheSame(oldItem: Flight, newItem: Flight): Boolean {
        return oldItem == newItem
    }
}