package com.example.kotlinsample

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.OnItemClickListener {

    companion object {

        private const val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 0

    }

    private val imageList = ArrayList<String>()
    private lateinit var adapter: RecyclerViewAdapter
    private lateinit var layoutManager: GridLayoutManager

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layoutManager = GridLayoutManager(applicationContext, 3)
        adapter = RecyclerViewAdapter(this@MainActivity, imageList, this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
            )
        } else {
            getImagePath()
            setImageList()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                getImagePath()
                setImageList()
            }
        }
    }

    private fun getImagePath() {
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.MediaColumns.DATA
        )

        val cursor: Cursor? =
            contentResolver.query(
                uri,
                projection,
                null,
                null,
                MediaStore.MediaColumns.DATE_ADDED + " desc"
            )

        cursor?.use {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)

            while (it.moveToNext()) {
                val absolutePathOfImage = it.getString(columnIndex)

                if (absolutePathOfImage.isNotEmpty()) {
                    imageList.add(absolutePathOfImage)
                }
            }
        }
    }

    private fun setImageList() {
        adapter.setItem(imageList)
    }

    override fun onItemClick(view: View, position: Int) {

        val imagePath: String = imageList[position]
        val iv_image: ImageView = view.findViewById(R.id.iv_image)
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("EXTRA_PATH", imagePath)

        val pairItem: Pair<View, String> = Pair.create(iv_image, iv_image.transitionName)
        val optionCompat =
            ActivityOptionsCompat.makeSceneTransitionAnimation(this@MainActivity, pairItem)
        startActivity(intent, optionCompat.toBundle())
    }
}
