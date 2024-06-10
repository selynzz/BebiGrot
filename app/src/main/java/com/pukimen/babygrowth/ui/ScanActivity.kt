package com.pukimen.babygrowth.ui


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.pukimen.babygrowth.R
import com.pukimen.babygrowth.data.remote.response.ScanResponse
import com.pukimen.babygrowth.data.remote.retrofit.ApiConfig
import com.pukimen.babygrowth.databinding.ActivityScanBinding
import com.pukimen.babygrowth.utils.reduceFileImage
import com.pukimen.babygrowth.utils.uriToFile
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
//
//class ScanActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityScanBinding
//    private var currentImageUri: Uri? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityScanBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val imageUriString = intent.getStringExtra("IMAGE_URI")
//        if (imageUriString != null) {
//            currentImageUri = Uri.parse(imageUriString)
//            Log.d("ScanActivity", "Received image URI: $currentImageUri")
//        } else {
//            Log.e("ScanActivity", "No image URI received")
//            showToast(getString(R.string.empty_image_warning))
//            finish()
//        }
//
//        currentImageUri?.let {
//            binding.ivScan.setImageURI(it)
//            uploadImage()
//        }
//    }
//
//    private fun uploadImage() {
//        currentImageUri?.let { uri ->
//            val imageFile = uriToFile(uri, this).reduceFileImage()
//            showLoading(true)
//            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
//            val multipartBody = MultipartBody.Part.createFormData(
//                "photo",
//                imageFile.name,
//                requestImageFile
//            )
//            lifecycleScope.launch {
//                try {
//                    val apiService = ApiConfig.getScanApiService()
//                    val successResponse = apiService.uploadImage(multipartBody)
//                    with(successResponse.data) {
//                        binding.resultText.text = if (isAboveThreshold == true) {
//                            showToast(successResponse.message.toString())
//                            String.format("%s with %.2f%%", result, confidenceScore)
//                        } else {
//                            showToast("Model is predicted successfully but under threshold.")
//                            String.format("Please use the correct picture because the confidence score is %.2f%%", confidenceScore)
//                        }
//                    }
//                    showLoading(false)
//                } catch (e: HttpException) {
//                    val errorBody = e.response()?.errorBody()?.string()
//                    val errorResponse = Gson().fromJson(errorBody, ScanResponse::class.java)
//                    showToast(errorResponse.message.toString())
//                    showLoading(false)
//                }
//            }
//        } ?: showToast(getString(R.string.empty_image_warning))
//    }
//
//    private fun showLoading(isLoading: Boolean) {
//        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//    }
//
//    private fun showToast(message: String) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
//    }
//}


//

class ScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanBinding
    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUriString = intent.getStringExtra("IMAGE_URI")
        if (imageUriString != null) {
            currentImageUri = Uri.parse(imageUriString)
            binding.ivScan.setImageURI(currentImageUri)
            uploadImage()
        } else {
            showToast(getString(R.string.empty_image_warning))
            finish()
        }
    }

    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()
            showLoading(true)
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "photo",
                imageFile.name,
                requestImageFile
            )
            lifecycleScope.launch {
                try {
                    val apiService = ApiConfig.getScanApiService()
                    val successResponse = apiService.uploadImage(multipartBody)
                    with(successResponse.data) {
                        binding.resultText.text = if (isAboveThreshold == true) {
                            showToast(successResponse.message.toString())
                            String.format("%s with %.2f%%", result, confidenceScore)
                        } else {
                            showToast("Model is predicted successfully but under threshold.")
                            String.format("Please use the correct picture because the confidence score is %.2f%%", confidenceScore)
                        }
                    }
                    showLoading(false)
                } catch (e: HttpException) {
                    val errorBody = e.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, ScanResponse::class.java)
                    showToast(errorResponse.message.toString())
                    showLoading(false)
                }
            }
        } ?: showToast(getString(R.string.empty_image_warning))
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}