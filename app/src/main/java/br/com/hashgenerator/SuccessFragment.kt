package br.com.hashgenerator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import br.com.hashgenerator.databinding.FragmentSuccessBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SuccessFragment : Fragment() {

    private val args: SuccessFragmentArgs by navArgs()

    private var _binding: FragmentSuccessBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding =  FragmentSuccessBinding.inflate(inflater, container, false)

        binding.hashTextView.text = args.hash

        binding.copyButton.setOnClickListener {onCopyClicked()}

        return binding.root
    }

    private fun onCopyClicked() {

        lifecycleScope.launch {
            copyToClipboard(args.hash)
            applyAnimations()
        }

    }

    private fun copyToClipboard(hash: String) {
        val clipboardManager = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("Encrypted Text", hash)

        clipboardManager.setPrimaryClip(clipData)
    }

    private suspend fun applyAnimations() {

        binding.include.messageBackground.animate().alpha(1f).duration = 200L
        binding.include.messageTextView.animate().alpha(1f).duration = 200L

        delay(2000L)

        binding.include.messageBackground.animate().alpha(0f).duration = 500L
        binding.include.messageTextView.animate().alpha(0f).duration = 500L

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}