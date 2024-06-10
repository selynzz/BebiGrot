package com.pukimen.babygrowth.ui


import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.pukimen.babygrowth.R
import com.pukimen.babygrowth.databinding.ActivityScanBinding
import com.pukimen.babygrowth.utils.Results
import com.pukimen.babygrowth.utils.reduceFileImage
import com.pukimen.babygrowth.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody


class ScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanBinding
    private var currentImageUri: Uri? = null

    private lateinit var scanViewModel: ScanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelFactory = ViewModelFactory.getInstance(this, application)
        scanViewModel = ViewModelProvider(this, viewModelFactory).get(ScanViewModel::class.java)

        val imageUriString = intent.getStringExtra("IMAGE_URI")
        if (imageUriString != null) {
            currentImageUri = Uri.parse(imageUriString)
            binding.ivScan.setImageURI(currentImageUri)
            uploadImage()
        } else {
            showToast(getString(R.string.empty_image_warning))
            finish()
        }

        scanViewModel.uploadResult.observe(this) { results ->
            when (results) {
                is Results.Loading -> showLoading(true)
                is Results.Success -> {
                    val successResponse = results.data
                    with(successResponse.data) {
                        val resultText = "$label, $suggestion"
                        binding.resultText.text = resultText
                        showToast(successResponse.message)
                    }
                    showLoading(false)
                }
                is Results.Error -> {
                    showToast(results.error)
                    showLoading(false)
                    Log.e("ScanActivity", "Error: ${results.error}")
                }
            }
        }
    }

    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "photo",
                imageFile.name,
                requestImageFile
            )
            scanViewModel.uploadImage(multipartBody)
        } ?: showToast(getString(R.string.empty_image_warning))
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
