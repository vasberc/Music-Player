package com.vasberc.presentation.componets

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AudioFile
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

//Permission Rationale -> https://medium.com/@myofficework000/permissions-with-jetpack-compose-79820a249ae7
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermissionBaseScreen(onPermissionGrantedContent: @Composable () -> Unit) {
    val permissions = rememberPermissionState(
        permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_AUDIO  // Android 13+
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE  // Older versions
        }
    )

    if (permissions.status != PermissionStatus.Granted) {
        val activity = LocalActivity.current!!
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions.permission)) {
            PermissionRationale(activity)
        } else {
            LaunchedEffect(Unit) {
                permissions.launchPermissionRequest()
            }

        }
    } else {
        onPermissionGrantedContent()
    }
}


@Composable
fun PermissionRationale(activity: Activity) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(Modifier.padding(vertical = 120.dp, horizontal = 16.dp)) {
            Icon(Icons.Rounded.AudioFile, contentDescription = null)
            Spacer(Modifier.height(8.dp))
            Text("Files permission required")
            Spacer(Modifier.height(4.dp))
            Text("This is required in order for the app to work properly")
        }
        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            onClick = {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", activity.packageName, null)
                }
                activity.startActivity(intent)
            }
        ) {
            Text("Go to settings")
        }
    }
}