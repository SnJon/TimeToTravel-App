package ru.wildberries.timetotravel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.wildberries.timetotravel.R
import ru.wildberries.timetotravel.databinding.CardFlightBinding
import ru.wildberries.timetotravel.dto.Flight

class FlightAdapter(private val onInteractionListener: OnInteractionListener) :
    ListAdapter<Flight, FlightViewHolder>(FlightDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightViewHolder {
        val binding = CardFlightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FlightViewHolder(binding, onInteractionListener)
    }

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
    fun bind(flight: Flight) {
        with(binding) {
            headerCity.text = flight.endCity
            startDate.text = root.context.getString(R.string.there_text, flight.startDate)
            thereStartCity.text = flight.startCity
            thereStartLocationCode.text = flight.startLocationCode
            thereEndCity.text = flight.endCity
            thereEndLocationCode.text = flight.endLocationCode
            endDate.text = root.context.getString(R.string.back_text, flight.endDate)
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
        return oldItem.token == newItem.token
    }

    override fun areContentsTheSame(oldItem: Flight, newItem: Flight): Boolean {
        return oldItem == newItem
    }
}