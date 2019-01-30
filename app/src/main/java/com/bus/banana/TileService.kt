package com.bus.banana

import android.app.Activity
import android.app.Service
import android.content.Intent
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Build
import android.os.IBinder
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.N)
class TileService: TileService() {

    override fun onStartListening() {
        //
        UT.Log("START")
    }

    override fun onStopListening() {
        //
        UT.Log("STOP")
    }

    override fun onClick() {
        UT.Log("on tile click")
        val intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivityAndCollapse(intent)
        super.onClick()
    }
}