package ru.abyzbaev.airwetenghelper.autentification.features.pdffiles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.barteksc.pdfviewer.PDFView
import ru.abyzbaev.airwetenghelper.databinding.PdfviewFragmentBinding

class PDFViewFragment: Fragment() {
    private var _binding: PdfviewFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var pdfView: PDFView
    private var pdfFileName: String? = null

    private fun displayPdf() {
        pdfFileName?.let {
            pdfView.fromAsset(it)
                .load()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = PdfviewFragmentBinding.inflate(layoutInflater)
        val view = binding.root

        arguments?.let {
            pdfFileName = it.getString("PDFFile", "")
            if(pdfFileName == "") {
                throw IllegalStateException("No file name 0_0")
            } else {
                pdfView = binding.pdfView
                displayPdf()
            }
        }
        return view
    }
}