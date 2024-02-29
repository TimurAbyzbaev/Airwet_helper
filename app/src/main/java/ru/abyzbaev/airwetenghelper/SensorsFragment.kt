package ru.abyzbaev.airwetenghelper
// SensorsFragment.kt
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import ru.abyzbaev.airwetenghelper.databinding.SensorsFragmentBinding

class SensorsFragment : Fragment() {

    private var _binding: SensorsFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SensorsViewModel by viewModels()

    private lateinit var spinners: Array<Spinner>

    private val addressVisibilityLiveDataList: MutableList<MutableLiveData<Boolean>> = mutableListOf()

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

        initView()

        return view
    }

    private fun initView() {
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
                        binding.textView15Parameter.text = "Параметр 1-5:      ${binaryNumber.toString(10)}"
                        changeAddressVisibility(binaryNumber.toString(10), firstGroup)
                    } else {
                        binding.textView610Parameter.text = "Параметр 6-10:    ${binaryNumber.toString(10)}"
                        changeAddressVisibility(binaryNumber.toString(10), firstGroup)
                    }
                }

                override fun onNothingSelected(parentView: AdapterView<*>?) {}
            }

            // Создаем LiveData для управления видимостью адресов
            val addressVisibilityLiveData = MutableLiveData<Boolean>()
            addressVisibilityLiveDataList.add(addressVisibilityLiveData)
        }

        // Наблюдаем за LiveData для управления видимостью адресов
        observeAddressVisibility()
    }

    private fun changeAddressVisibility(parameterText: String, isGroup1: Boolean) {
        val binaryNumber = parameterText.toIntOrNull() ?: return

        val startZone = if (isGroup1) 1 else 6
        val endZone = if (isGroup1) 5 else 10

        for (zone in startZone..endZone) {
            val firstAddress = 2 + (zone - 1) * 3
            val sensorCount = if (isGroup1) viewModel.zones1_5.value?.get(zone) ?: 0 else viewModel.zones6_10.value?.get(zone) ?: 0

            when (sensorCount) {
                0 -> {
                    for (i in 0..2) {
                        val unusedTextViewId = resources.getIdentifier(
                            "tv${firstAddress + i}",
                            "id",
                            requireContext().packageName
                        )
                        val unusedTextView = requireView().findViewById<TextView>(unusedTextViewId)
                        unusedTextView.visibility = View.INVISIBLE
                    }
                }
                1 -> {
                    val textViewId = resources.getIdentifier(
                        "tv$firstAddress",
                        "id",
                        requireContext().packageName
                    )
                    val textView = requireView().findViewById<TextView>(textViewId)
                    textView.visibility = View.VISIBLE

                    for (i in 1..2) {
                        val unusedTextViewId = resources.getIdentifier(
                            "tv${firstAddress + i}",
                            "id",
                            requireContext().packageName
                        )
                        val unusedTextView = requireView().findViewById<TextView>(unusedTextViewId)
                        unusedTextView.visibility = View.INVISIBLE
                    }
                }
                2 -> {
                    for (i in 0 until 2) {
                        val textViewId = resources.getIdentifier(
                            "tv${firstAddress + i}",
                            "id",
                            requireContext().packageName
                        )
                        val textView = requireView().findViewById<TextView>(textViewId)
                        textView.visibility = View.VISIBLE
                    }
                    val unusedTextViewId = resources.getIdentifier(
                        "tv${firstAddress + 2}",
                        "id",
                        requireContext().packageName
                    )
                    val unusedTextView = requireView().findViewById<TextView>(unusedTextViewId)
                    unusedTextView.visibility = View.INVISIBLE
                }
                3 -> {
                    for (i in 0 until 3) {
                        val textViewId = resources.getIdentifier(
                            "tv${firstAddress + i}",
                            "id",
                            requireContext().packageName
                        )
                        val textView = requireView().findViewById<TextView>(textViewId)
                        textView.visibility = View.VISIBLE
                    }
                }
            }
        }

        if (isGroup1) {
            binding.textView15Parameter.text = "Параметр 1-5:      ${binaryNumber.toString(10)}"
        } else {
            binding.textView610Parameter.text = "Параметр 6-9:      ${binaryNumber.toString(10)}"
        }
    }







    private fun observeAddressVisibility() {
        for (i in spinners.indices) {
            addressVisibilityLiveDataList[i].observe(viewLifecycleOwner, Observer { isVisible ->
                val firstAddress = 2 + (i + 1) * 3
                for (j in 0..2) {
                    val textViewId = resources.getIdentifier("tv${firstAddress + j}", "id", requireContext().packageName)
                    val textView = requireView().findViewById<TextView>(textViewId)
                    textView.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
