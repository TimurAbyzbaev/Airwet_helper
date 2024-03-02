package ru.abyzbaev.airwetenghelper.autentification.features.pdffiles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.abyzbaev.airwetenghelper.R
import ru.abyzbaev.airwetenghelper.databinding.PdfListFragmentBinding
import ru.abyzbaev.airwetenghelper.databinding.SensorsFragmentBinding

class PdfListFragment : Fragment() {

    private var _binding: PdfListFragmentBinding? = null
    private val binding get() = _binding!!

    private val pdfFiles = arrayOf(
        "Ошибки.pdf",
        "Параметры.pdf",
        "Полное руководство.pdf",
        "Инструкция Airwet Touch C.pdf",
        "Инструкция HC60.pdf",
        "Настройка датчиков Airwet Sens.pdf",
        "Подключение драйверов.pdf",
        "Справка по подключению системы отопления к термогигростату Airwet Touch C.pdf",
        "Шпаргалка по монтажу датчиков.pdf"
    ) // PDF файлы в папке assets

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PdfListFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, pdfFiles)
        binding.listViewPdfFiles.adapter = adapter
        binding.listViewPdfFiles.setOnItemClickListener { _, _, position, _ ->
            val selectedPdf = pdfFiles[position]
            val action = PdfListFragmentDirections.actionPdfListFragmentToPdfViewFragment(selectedPdf)

            findNavController().navigate(action)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    findNavController().navigate(R.id.action_list_pdf_to_home)
                    true
                }
                R.id.navigation_pdf_list -> {
                    true
                }
                else -> false
            }
        }
    }
}