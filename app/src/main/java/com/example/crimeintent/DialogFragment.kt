package com.example.crimeintent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.crimeintent.databinding.FragmentDialogBinding
import kotlinx.coroutines.launch
import java.io.File

class DialogFragment : Fragment() {

    private val args : DialogFragmentArgs by navArgs()

    private var _binding: FragmentDialogBinding? = null

    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null, Is the view visible?"
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDialogBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            setFragmentResult(
                REQUEST_KEY_PHOTO,
                bundleOf(BUNDLE_KEY_PHOTO to args.crimePhoto)
            )
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                updatePhoto(args.crimePhoto)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updatePhoto(photoFileName: String?) {
        if (binding.photo.tag != photoFileName) {
            val photoFile = photoFileName?.let {
                File(requireContext().applicationContext.filesDir, it)
            }
            if (photoFile?.exists() == true) {
                binding.photo.doOnLayout { measuredView ->
                    val scaledBitmap = getScaledBitmap(
                        photoFile.path,
                        measuredView.width,
                        measuredView.height
                    )
                    binding.photo.setImageBitmap(scaledBitmap)
                    binding.photo.tag = photoFileName
                }
            } else {
                binding.photo.setImageBitmap(null)
                binding.photo.tag = null
            }
        }
    }

    companion object {
        const val REQUEST_KEY_PHOTO = "REQUEST_KEY_PHOTO"
        const val BUNDLE_KEY_PHOTO = "BUNDLE_KEY_PHOTO"
    }

}