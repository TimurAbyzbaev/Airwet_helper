package ru.abyzbaev.airwetenghelper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.listener.OnErrorListener
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener
import com.github.barteksc.pdfviewer.util.FitPolicy
import ru.abyzbaev.airwetenghelper.databinding.DescriptonsFragmentBinding
import ru.abyzbaev.airwetenghelper.databinding.SensorsFragmentBinding

class DescriptionsFragment: Fragment() {
    private var _binding: DescriptonsFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DescriptonsFragmentBinding.inflate(layoutInflater)
        val view = binding.root


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var pdfView = requireView().findViewById<PDFView>(R.id.pdfView)
        // Загрузите PDF файл из ресурсов
        pdfView.fromAsset("V5_errors.pdf")
            .defaultPage(0)
            .enableSwipe(true)
            .swipeHorizontal(false)
            .onLoad(object : OnLoadCompleteListener {
                override fun loadComplete(nbPages: Int) {
                    Toast.makeText(requireContext(), "Load ended", Toast.LENGTH_SHORT).show()
                }
            })
            .onError(object : OnErrorListener {
                override fun onError(t: Throwable) {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
            })
            .enableAnnotationRendering(false)
            .password(null)
            .scrollHandle(null)
            .enableAntialiasing(true)
            .spacing(0)
            .autoSpacing(false)
            .pageFitPolicy(FitPolicy.WIDTH)
            .fitEachPage(false)
            .nightMode(false)
            .pageFling(false)
            .pageSnap(false)
            .load()
    }
}