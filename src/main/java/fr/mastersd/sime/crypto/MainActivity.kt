package fr.mastersd.sime.crypto

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import fr.mastersd.sime.crypto.databinding.ActivityMainBinding

import androidx.activity.viewModels
import CryptoViewModel.CryptoViewModel
import android.view.View


class MainActivity : AppCompatActivity() {
    private val cryptoViewModel: CryptoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.encryptButton.setOnClickListener {
            cryptoViewModel.encrypt(
                binding.inputText.text.toString()
            )
        }

        cryptoViewModel.encryptionResult.observe(this){value ->
            when(value) {

                is CryptoViewModel.EncryptionResult.Empty -> {
                    binding.encryptedText.visibility = View.GONE
                    binding.warningIcon.visibility = View.GONE
                    binding.resultText.visibility = View.GONE
                }

                is CryptoViewModel.EncryptionResult.Failed -> {
                    // hide the err if ok
                    binding.resultText.visibility = View.GONE
                    binding.warningIcon.visibility = View.VISIBLE
                    binding.encryptedText.visibility = View.VISIBLE
                    //getString comme printf pour afficher apres on add le value chercher
                    binding.encryptedText.text = getString(R.string.warning_text, value.faulty)
                }

                is CryptoViewModel.EncryptionResult.Encrypted -> {
                    binding.resultText.visibility = View.VISIBLE

                    binding.warningIcon.visibility = View.GONE
                    binding.encryptedText.visibility = View.GONE

                    binding.resultText.text = value.text
                }
            }

        }

    }

}