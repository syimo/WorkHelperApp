package com.vance.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

/**
 *
 * @author VanceKing
 * @since 19-8-11.
 */

object PermissionUtil {
    const val PERMISSION_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
    const val PERMISSION_CAMERA = Manifest.permission.CAMERA
    const val PERMISSION_PHONE = Manifest.permission.CALL_PHONE
    const val PERMISSION_SEND_SMS = Manifest.permission.SEND_SMS
    const val PERMISSION_READ_SMS = Manifest.permission.READ_SMS

    @JvmStatic
    fun hasPermissions(context: Context, vararg permissions: String): Boolean {
        for (permission in permissions) {
            if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(context, permission)) {
                return false
            }
        }
        return true
    }
}
