package com.shahnoza.galereyabilanishlash

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.shahnoza.galereyabilanishlash.databinding.ActivityMainBinding
import com.shahnoza.galereyabilanishlash.models.ImageUser
import com.shahnoza.galereyabilanishlash.offlinedatabase.MyBase
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FilterOutputStream

class MainActivity : AppCompatActivity() {
    var myUri: Uri?= null
    lateinit var myBase: MyBase
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        myBase=MyBase(this)
        binding.btnTakeImage.setOnClickListener {

            getImageNew()
        }

        binding.btnGetImage.setOnClickListener {

            val list = ArrayList<ImageUser>()
            list.addAll(myBase.getAllImage())
            if (list.isNotEmpty()){
                binding.imageView2.setImageURI(Uri.parse(list[0].absolutePath))

                val bitmap = BitmapFactory.decodeByteArray(list[0].image,0,list[0].image!!.size)
                binding.imageView2.setImageBitmap(bitmap)
            }

        }


        // Galereya bilan ishlash va rasmlarni offline Sqlite databasega saqlash

    }

    private val getImageContent =
        registerForActivityResult(ActivityResultContracts.GetContent()){
            it ?: return@registerForActivityResult
            binding.imageView.setImageURI(it)
            val inputStream = contentResolver?.openInputStream(it)
            val file = File(filesDir,"image.jpg")
            val fileOutputStream = FileOutputStream(file)
            inputStream?.copyTo(fileOutputStream)
            inputStream?.close()
            val absoult = file.absolutePath

            val fileInputStream = FileInputStream(file)
            val readBytes = fileInputStream.readBytes()
            val imageUser = ImageUser(absoult,readBytes)

           myBase.addImage(imageUser)
            myBase.getAllImage()

        }



    private fun getImageNew(){
getImageContent.launch("image/*")
    }
}