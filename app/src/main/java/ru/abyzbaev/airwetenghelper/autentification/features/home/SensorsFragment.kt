package ru.abyzbaev.airwetenghelper.autentification.features.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.abyzbaev.airwetenghelper.R
import ru.abyzbaev.airwetenghelper.databinding.SensorsFragmentBinding

class SensorsFragment : Fragment() {

    private var _binding: SensorsFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SensorsViewModel by activityViewModels()
    private lateinit var spinners: Array<Spinner>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SensorsFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        spinners = arrayOf(
            binding.zone1spinner, binding.zone2spinner, binding.zone3spinner, binding.zone4spinner,
            binding.zone5spinner, binding.zone6spinner, binding.zone7spinner, binding.zone8spinner,
            binding.zone9spinner, binding.zone10spinner
        )

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    true
                }
                R.id.navigation_pdf_list -> {
                    findNavController().navigate(R.id.action_home_to_pdf_list_fragment)
                    true
                }
                else -> false
            }
        }

        observeViewModel()
        initView()
    }

    private fun initView() {
        //firstRun
        val items: ArrayList<Int> = arrayListOf(0, 1, 2, 3)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        for (spinner in spinners) {
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    selectedItemView: View?,
                    position: Int,
                    id: Long
                ) {
                    val zone = spinners.indexOf(spinner) + 1
                    val firstGroup = zone in 1..5
                    viewModel.updateSensorCount(zone, items[position], firstGroup)
                    val sensorMap = if (firstGroup) viewModel.zones1_5.value else viewModel.zones6_10.value
                    val binaryNumber = viewModel.calculateNumber(sensorMap!!, firstGroup)
                    if (firstGroup) {
                        binding.textView15Parameter.text = getString(R.string.Parameter1_5, binaryNumber.toString(10))
                        changeAddressVisibility(firstGroup)
                    } else {
                        binding.textView610Parameter.text = getString(R.string.Parameter6_10, binaryNumber.toString(10))
                        changeAddressVisibility(firstGroup)
                    }
                }

                override fun onNothingSelected(parentView: AdapterView<*>?) {}
            }
        }
    }

    private fun observeViewModel() {
        viewModel.zones1_5.observe(viewLifecycleOwner) { selectedItems ->
            Log.d("liveData", "zones1_5 $selectedItems")
            selectedItems.entries.forEach { entry ->
                val index = entry.key - 1
                val selectedItem = entry.value
                spinners.getOrNull(index)?.setSelection(selectedItem)
            }
        }

        viewModel.zones6_10.observe(viewLifecycleOwner) { selectedItems ->
            selectedItems.entries.forEach { entry ->
                Log.d("liveData", "zones6_9 $selectedItems")
                val index = entry.key - 6
                val selectedItem = entry.value
                spinners.getOrNull(index + 5)?.setSelection(selectedItem)
            }
        }
    }

    private fun changeAddressVisibility(isGroup1: Boolean) {

        val startZone = if (isGroup1) 1 else 6
        val endZone = if (isGroup1) 5 else 10

        for (zone in startZone..endZone) {
            val firstAddress = 2 + (zone - 1) * 3

            val sensorMap = if (isGroup1) viewModel.zones1_5.value else viewModel.zones6_10.value
            val sensorCount = sensorMap?.get(zone) ?: 0

            val textViewIds = mutableListOf<Int>()
            for (i in 0..2) {
                val textViewId = resources.getIdentifier("tv${firstAddress + i}", "id", requireContext().packageName)
                textViewIds.add(textViewId)
            }

            for (i in 0..2) {
                val textView = requireView().findViewById<TextView>(textViewIds[i])
                textView.visibility = if (i < sensorCount) View.VISIBLE else View.INVISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
