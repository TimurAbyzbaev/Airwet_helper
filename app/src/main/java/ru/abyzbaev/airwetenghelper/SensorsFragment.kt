package ru.abyzbaev.airwetenghelper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.abyzbaev.airwetenghelper.databinding.SensorsFragmentBinding

class SensorsFragment: Fragment() {

    private var _binding: SensorsFragmentBinding? = null
    private val binding get() = _binding!!

    private var zones1_5: MutableMap<Int, Int> = mutableMapOf(1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0)
    private var zones6_10: MutableMap<Int, Int> = mutableMapOf(6 to 0, 7 to 0, 8 to 0, 9 to 0, 10 to 0)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SensorsFragmentBinding.inflate(layoutInflater)
        val view = binding.root

        initView()

        return view
    }


    private fun initView() {
        val items: ArrayList<Int> = arrayListOf(0, 1, 2, 3)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinners = arrayOf(
            binding.zone1spinner, binding.zone2spinner, binding.zone3spinner, binding.zone4spinner,
            binding.zone5spinner, binding.zone6spinner, binding.zone7spinner, binding.zone8spinner,
            binding.zone9spinner, binding.zone10spinner
        )

        for (spinner in spinners) {
            spinner.adapter = adapter
            spinner.setSelection(0)
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    selectedItemView: View?,
                    position: Int,
                    id: Long
                ) {
                    // Определение зоны по индексу массива 'spinners'
                    val zone = spinners.indexOf(spinner) + 1
                    // Вызов разных версий calculateNumber в зависимости от зоны
                    when (zone) {
                        in 1..5 -> zones1_5[zone] = items[position]
                        in 6..10 -> zones6_10[zone] = items[position]
                    }
                    if (zone in 1..5) {
                        calculateNumber(zones1_5, true)
                    } else if (zone in 6..10) {
                        calculateNumber(zones6_10, false)
                    }
                }

                override fun onNothingSelected(parentView: AdapterView<*>?) {}
            }
        }
    }

    fun calculateNumber(sensorMap: MutableMap<Int, Int>, firstGroup: Boolean) {
        // Создаем пустое двоичное число
        var binaryNumber = 0
        var offsetNumber = 1
        if (!firstGroup) {
            offsetNumber = 6
        }

        // Проходим по всем зонам в sensorMap
        for ((zone, sensorCount) in sensorMap) {
            // Вычисляем смещение для каждой зоны
            val offset = (zone - offsetNumber) * 3

            // Определяем первый адрес датчика в текущей зоне
            val firstAddress = 2 + (zone - 1) * 3

            // Выставляем соответствующие биты в зависимости от количества датчиков
            when (sensorCount) {
                0 -> {
                    // Если в зоне нет датчиков
                    binaryNumber = binaryNumber or (0 shl offset)
                    for (i in 0..2) {
                        val unusedTextViewId = resources.getIdentifier("tv${firstAddress + i}", "id", requireContext().packageName)
                        val unusedTextView = requireView().findViewById<TextView>(unusedTextViewId)
                        unusedTextView.visibility = View.INVISIBLE
                    }
                }
                1 -> {
                    // Если в зоне один датчик
                    binaryNumber = binaryNumber or (1 shl offset)
                    // Включаем видимость первого датчика в зоне
                    val textViewId = resources.getIdentifier("tv$firstAddress", "id", requireContext().packageName)
                    val textView = requireView().findViewById<TextView>(textViewId)
                    textView.visibility = View.VISIBLE
                    // Скрываем остальные адреса
                    for (i in 1..2) {
                        val unusedTextViewId = resources.getIdentifier("tv${firstAddress + i}", "id", requireContext().packageName)
                        val unusedTextView = requireView().findViewById<TextView>(unusedTextViewId)
                        unusedTextView.visibility = View.INVISIBLE
                    }
                }
                2 -> {
                    // Если в зоне два датчика
                    binaryNumber = binaryNumber or (3 shl offset)
                    // Включаем видимость первых двух датчиков в зоне
                    for (i in 0 until 2) {
                        val textViewId = resources.getIdentifier("tv${firstAddress + i}", "id", requireContext().packageName)
                        val textView = requireView().findViewById<TextView>(textViewId)
                        textView.visibility = View.VISIBLE
                    }
                    // Скрываем оставшийся адрес
                    val unusedTextViewId = resources.getIdentifier("tv${firstAddress + 2}", "id", requireContext().packageName)
                    val unusedTextView = requireView().findViewById<TextView>(unusedTextViewId)
                    unusedTextView.visibility = View.INVISIBLE
                }
                3 -> {
                    // Если в зоне три датчика
                    binaryNumber = binaryNumber or (7 shl offset)
                    // Включаем видимость всех датчиков в зоне
                    for (i in 0 until 3) {
                        val textViewId = resources.getIdentifier("tv${firstAddress + i}", "id", requireContext().packageName)
                        val textView = requireView().findViewById<TextView>(textViewId)
                        textView.visibility = View.VISIBLE
                    }
                }
            }
        }

        if(firstGroup) {
            binding.textView15Parameter.text = binaryNumber.toString(10)
        } else {
            binding.textView610Parameter.text = binaryNumber.toString(10)
        }
    }



//    fun calculateNumber(sensorMap: MutableMap<Int, Int>, firstGroup: Boolean) {
//        // Создаем пустое двоичное число
//        var binaryNumber = 0
//        var offsetNumber = 1
//        if (!firstGroup) {
//            offsetNumber = 6
//        }
//
//        // Проходим по всем зонам в sensorMap
//        for ((zone, sensorCount) in sensorMap) {
//            // Вычисляем смещение для каждой зоны
//            val offset = (zone - offsetNumber) * 3
//
//            // Определяем первый адрес датчика в текущей зоне
//            val firstAddress = 2 + (zone - 1) * 3
//
//            // Выставляем соответствующие биты в зависимости от количества датчиков
//            when (sensorCount) {
//                0 -> {
//                    // Если в зоне нет датчиков
//                    binaryNumber = binaryNumber or (0 shl offset)
//                }
//                1 -> {
//                    // Если в зоне один датчик
//                    binaryNumber = binaryNumber or (1 shl offset)
//                    // Включаем видимость первого датчика в зоне
//                    val textViewId = resources.getIdentifier("tv$firstAddress", "id", requireContext().packageName)
//                    val textView = requireView().findViewById<TextView>(textViewId)
//                    textView.visibility = View.VISIBLE
//                }
//                2 -> {
//                    // Если в зоне два датчика
//                    binaryNumber = binaryNumber or (3 shl offset)
//                    // Включаем видимость первых двух датчиков в зоне
//                    for (i in 0 until 2) {
//                        val textViewId = resources.getIdentifier("tv${firstAddress + i}", "id", requireContext().packageName)
//                        val textView = requireView().findViewById<TextView>(textViewId)
//                        textView.visibility = View.VISIBLE
//                    }
//                }
//                3 -> {
//                    // Если в зоне три датчика
//                    binaryNumber = binaryNumber or (7 shl offset)
//                    // Включаем видимость всех датчиков в зоне
//                    for (i in 0 until 3) {
//                        val textViewId = resources.getIdentifier("tv${firstAddress + i}", "id", requireContext().packageName)
//                        val textView = requireView().findViewById<TextView>(textViewId)
//                        textView.visibility = View.VISIBLE
//                    }
//                }
//            }
//        }
//
//        if(firstGroup) {
//            binding.textView15Parameter.text = binaryNumber.toString(10)
//        } else {
//            binding.textView610Parameter.text = binaryNumber.toString(10)
//        }
//    }



//    fun calculateNumber(sensorMap: MutableMap<Int, Int>, firstGroup: Boolean) {
//        // Создаем пустое двоичное число
//        var binaryNumber = 0
//        var offsetNumber = 1
//        if(!firstGroup){
//            offsetNumber = 6
//        }
//
//        // Проходим по всем зонам в sensorMap
//        for ((zone, sensorCount) in sensorMap) {
//            // Вычисляем смещение для каждой зоны
//            val offset = (zone - offsetNumber) * 3
//
//            // Выставляем соответствующие биты в зависимости от количества датчиков
//            when (sensorCount) {
//                0 -> { // Если в зоне нет датчиков
//                    // Выставляем биты в нули
//                    binaryNumber = binaryNumber or (0 shl offset)
//                }
//                1 -> { // Если в зоне один датчик
//                    // Выставляем первый бит в единицу, а остальные в нули
//                    binaryNumber = binaryNumber or (1 shl offset)
//                }
//                2 -> { // Если в зоне два датчика
//                    // Выставляем первые два бита в единицы, а третий в ноль
//                    binaryNumber = binaryNumber or (3 shl offset)
//                }
//                3 -> { // Если в зоне три датчика
//                    // Выставляем все три бита в единицы
//                    binaryNumber = binaryNumber or (7 shl offset)
//                }
//            }
//        }
//
//        if(firstGroup) {
//            binding.textView15Parameter.text = binaryNumber.toString(10)
//        } else {
//            binding.textView610Parameter.text = binaryNumber.toString(10)
//        }
//    }
}