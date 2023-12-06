package com.example.budget.model.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider

import java.io.File


fun shareFile(context: Context, fromDirectory: String, fileName : String ){
    val TAG = "ShareFile"
    val reportPath = File(context.filesDir, fromDirectory)
    Log.i(TAG, "onCreate: path exist ${reportPath.exists()}")
    if(!reportPath.exists()) {
        throw IllegalArgumentException("Report path not found")
    }
    val requestFile = File(reportPath, fileName)
    if(!requestFile.exists()) {
        throw IllegalArgumentException("File report not found")
    } else {
        Log.i(TAG, "FileName - ${requestFile} exist")
        Log.i(TAG, "File length = ${requestFile.length()}")
        val fileUri: Uri? = try {
            FileProvider.getUriForFile(
                context,
                "com.ruslanakhmetov.myapplication.fileprovider",
                requestFile
            )
        } catch (e:IllegalArgumentException){
            e.printStackTrace()
            Log.i(
                TAG,
                "The selected file can't be shared: $requestFile - ${e.printStackTrace()}")
            null
        }
        Log.i(TAG, "FileUri: ${fileUri.toString()}")

        if (fileUri != null){
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("application/xls")
            intent.putExtra(Intent.EXTRA_SUBJECT, "Share file report.xls ")
            intent.putExtra(Intent.EXTRA_STREAM, fileUri)
            context.startActivity(intent)
        }
    }

}