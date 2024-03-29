package com.saifer.storyapp.ui.post

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.saifer.storyapp.R
import com.saifer.storyapp.data.remote.model.PostModel
import com.saifer.storyapp.databinding.ActivityNewStoryBinding
import com.saifer.storyapp.session.SessionManager
import com.saifer.storyapp.ui.list.StoryActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.schedule

class NewStoryActivity : AppCompatActivity() {

    private val filenameFormat = "dd-MMM-yyyy"
    private lateinit var currentPhotoPath: String
    private lateinit var binding: ActivityNewStoryBinding
    private var getFile: File? = null
    private lateinit var sessionManager: SessionManager
    private val requestedCameraPermission = Manifest.permission.CAMERA
    private val requestedMediaPermission = Manifest.permission.READ_EXTERNAL_STORAGE

    private val viewModel: NewStoryViewModel by viewModels {
        NewStoryViewModelFactory()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(
                    this@NewStoryActivity,
                    getString(R.string.toast_access_granted),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this@NewStoryActivity,
                    getString(R.string.toast_access_denied),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding = ActivityNewStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this@NewStoryActivity)

        val lat = intent.getStringExtra("lat")?.toRequestBody("float".toMediaTypeOrNull())
        val lon = intent.getStringExtra("lon")?.toRequestBody("float".toMediaTypeOrNull())

        binding.btnCamera.setOnClickListener {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    baseContext,
                    requestedCameraPermission
                ) -> {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    intent.resolveActivity(packageManager)

                    createCustomTempFile(application).also {
                        val photoUri: Uri = FileProvider.getUriForFile(
                            this@NewStoryActivity,
                            "com.saifer.storyapp",
                            it
                        )
                        currentPhotoPath = it.absolutePath
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                        launcherIntentCamera.launch(intent)
                    }
                }
                else -> {
                    requestPermissionLauncher.launch(requestedCameraPermission)
                }
            }
        }

        binding.btnGallery.setOnClickListener {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    baseContext,
                    requestedMediaPermission
                ) -> {
                    val intent = Intent()
                    intent.action = Intent.ACTION_GET_CONTENT
                    intent.type = "image/*"
                    val picker = Intent.createChooser(intent, "Pick a Picture")
                    launcherIntentGallery.launch(picker)
                }
                else -> {
                    requestPermissionLauncher.launch(requestedMediaPermission)
                }
            }
        }

        binding.btnAdd.setOnClickListener {
            if (getFile == null){
                Toast.makeText(
                    this@NewStoryActivity,
                    "Take or Pick your Photo First",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (binding.edAddDescription.text.toString() == ""){
                binding.edAddDescription.error = "Please Add Description"
            } else {
                binding.progressBar.visibility = View.VISIBLE
                val post = PostModel(
                    getFile,
                    binding.edAddDescription.text.toString().toRequestBody("text/plain".toMediaType()),
                    lat,
                    lon
                )
                viewModel.post(post, sessionManager.getToken()!!)

                Timer().schedule(2000){
                    val i = Intent(this@NewStoryActivity, StoryActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    finish()
                    startActivity(i)
                }
            }
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            getFile = myFile

            val result = rotateBitmap(
                BitmapFactory.decodeFile(myFile.path),
                true
            )

            Glide.with(this@NewStoryActivity)
                .load(result)
                .apply( RequestOptions().override(1000,1000))
                .into(binding.imgPreview)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri

            val myFile = uriToFile(selectedImg, this@NewStoryActivity)

            getFile = myFile

            binding.imgPreview.setImageURI(selectedImg)
        }
    }

    private fun rotateBitmap(bitmap: Bitmap, isBackCamera: Boolean = false): Bitmap {
        val matrix = Matrix()
        return if (isBackCamera) {
            matrix.postRotate(90f)
            Bitmap.createBitmap(
                bitmap,
                0,
                0,
                bitmap.width,
                bitmap.height,
                matrix,
                true
            )
        } else {
            matrix.postRotate(-90f)
            matrix.postScale(-1f, 1f, bitmap.width / 2f, bitmap.height / 2f)
            Bitmap.createBitmap(
                bitmap,
                0,
                0,
                bitmap.width,
                bitmap.height,
                matrix,
                true
            )
        }
    }

    private fun uriToFile(selectedImg: Uri, context: Context): File {
        val contentResolver: ContentResolver = context.contentResolver
        val myFile = createCustomTempFile(context)

        val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
        val outputStream: OutputStream = FileOutputStream(myFile)
        val buf = ByteArray(1024)
        var len: Int
        while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
        outputStream.close()
        inputStream.close()

        return myFile
    }

    private fun createCustomTempFile(context: Context): File {
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(timeStamp, ".jpg", storageDir)
    }

    private val timeStamp: String = SimpleDateFormat(
        filenameFormat,
        Locale.US
    ).format(System.currentTimeMillis())
}