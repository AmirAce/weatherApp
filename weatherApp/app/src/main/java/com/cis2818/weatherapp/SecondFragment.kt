package com.cis2818.weatherapp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.android.volley.toolbox.Volley
import com.cis2818.weatherapp.databinding.FragmentSecondBinding
import com.squareup.picasso.Picasso

class SecondFragment : Fragment() {

    companion object {
        fun newInstance() = SecondFragment()
    }
    private lateinit var binding:FragmentSecondBinding
    private lateinit var viewModel: SecondViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SecondViewModel::class.java)
        var cityName=arguments?.getString("cityName").toString()
        var queue = Volley.newRequestQueue(context)
        viewModel.setCurrentWeather(cityName, queue)

        binding.tvCity.text=cityName

        val tempObserver = Observer<String>{temp-> binding.tvTemp.text=temp}
        viewModel.getTemp().observe(viewLifecycleOwner,tempObserver)

        val dateObserver = Observer<String>{date-> binding.tvDate.text=date}
        viewModel.getDate().observe(viewLifecycleOwner,dateObserver)

        val descObserver = Observer<String>{desc-> binding.tvDesc.text=desc}
        viewModel.getDesc().observe(viewLifecycleOwner,descObserver)

        val iconObserver = Observer<String>{icon->Picasso.with(context).load(icon).into(binding.ivIcon)}
        viewModel.getIcon().observe(viewLifecycleOwner,iconObserver)

    }

}