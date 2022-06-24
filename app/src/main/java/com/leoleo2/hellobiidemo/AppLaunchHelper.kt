package com.leoleo2.hellobiidemo

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.widget.Toast

internal class AppLaunchHelper constructor(private val context: Context) {
    fun launchMapApp(latitude: Double, longitude: Double, query: String) {
        launchExternalApp(onAction = {
            val uri = Uri.parse("geo:$latitude,$longitude?q=$query")
            Intent(Intent.ACTION_VIEW, uri).apply {
                addFlags(FLAG_ACTIVITY_NEW_TASK)
            }.let {
                context.startActivity(it)
            }
        }, onErrorAction = {
            showToast(context.getString(R.string.error_message_map_app))
        })
    }

    private fun launchExternalApp(onAction: () -> Unit, onErrorAction: (String) -> Unit) {
        kotlin.runCatching {
            onAction.invoke()
        }.onFailure {
            when (it) {
                is ActivityNotFoundException -> {
                    onErrorAction(it.localizedMessage ?: "launch app error.")
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}