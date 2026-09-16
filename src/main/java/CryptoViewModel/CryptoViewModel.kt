package CryptoViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import backend.CryptoUtil
import androidx.lifecycle.ViewModel
import backend.IllegalCharException

class CryptoViewModel : ViewModel() {

    private val cryptoUtil = CryptoUtil()
    private val _encryptionResult = MutableLiveData<EncryptionResult>(EncryptionResult.Empty)
    val encryptionResult : LiveData<EncryptionResult> = _encryptionResult
/// func encrypt un string en input
    fun encrypt(text: String){
        // if y a rien _encryptionResult sera vide
        try {
            if (text.isEmpty()){
                _encryptionResult.value = EncryptionResult.Empty
            }
            // sinon we call cesar fun from cryptoUtil
            else{
                // val pour stock
                val encryptedText = cryptoUtil.cesar(text)
                // resutl
                _encryptionResult.value = EncryptionResult.Encrypted(encryptedText)
            }
        }
        catch (e: IllegalCharException){
            _encryptionResult.value = EncryptionResult.Failed(e.message.last())
        }

    }

    sealed interface EncryptionResult {
        data object Empty : EncryptionResult
        @JvmInline
        value class Encrypted(val text: String) : EncryptionResult
        @JvmInline
        value class Failed(val faulty: Char) : EncryptionResult
    }




}