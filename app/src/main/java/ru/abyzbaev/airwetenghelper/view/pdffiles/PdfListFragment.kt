package ru.abyzbaev.airwetenghelper.view.pdffiles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import ru.abyzbaev.airwetenghelper.R

class PdfListFragment : Fragment() {

    private lateinit var pdfListView: ListView
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
    ): View? {
        val view = inflater.inflate(R.layout.pdf_list_fragment, container, false)
        pdfListView = view.findViewById(R.id.listViewPdfFiles)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, pdfFiles)
        pdfListView.adapter = adapter
        pdfListView.setOnItemClickListener { _, _, position, _ ->
            val selectedPdf = pdfFiles[position]
            val pdfViewFragment = PDFViewFragment.newInstance(selectedPdf)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, pdfViewFragment)
                .addToBackStack(null)
                .commit()
        }
        return view
    }
}