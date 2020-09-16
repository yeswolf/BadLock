package com.yeswolf.badlock.network

import android.app.DownloadManager
import android.content.ActivityNotFoundException
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.widget.Toast
import androidx.compose.ui.viewinterop.viewModel
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import com.yeswolf.badlock.ui.ApkListViewModel

class DownloadResultReceiver(var viewModel: ApkListViewModel) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == action) {
            val downloadId = intent.getLongExtra(
                DownloadManager.EXTRA_DOWNLOAD_ID, 0
            )
            if (context != null) {
                openDownloadedAttachment(context, downloadId)
            }
        }
    }
    private fun openDownloadedAttachment(context: Context, downloadId: Long) {
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val query = DownloadManager.Query()
        query.setFilterById(downloadId)
        val cursor: Cursor = downloadManager.query(query)
        if (cursor.moveToFirst()) {
            val downloadStatus: Int =
                cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
            val downloadLocalUri: String =
                cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI))
            val downloadMimeType: String =
                cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE))
            if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL) {
                openDownloadedAttachment(context, Uri.parse(downloadLocalUri), downloadMimeType)
            }
        }
        cursor.close()
    }

    private fun openDownloadedAttachment(
        context: Context,
        attachmentUri: Uri,
        attachmentMimeType: String
    ) {
        val contentURI = FileProvider.getUriForFile(
            context,
            context.applicationContext.packageName.toString() + ".provider",
            attachmentUri.toFile()
        )
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(contentURI, attachmentMimeType)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        try {
            context.startActivity(intent)
            viewModel.onLoadingUpdated(false)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                context,
                "Cannot open file",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}