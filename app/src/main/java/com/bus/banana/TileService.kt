package com.bus.banana

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.support.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.N)
class TileService: TileService() {

    private var tileEnabled = false

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
        tileEnabled = !tileEnabled
        /*val tile = qsTile
        tile.label = if(tileEnabled) "Recording..." else "Record Screen"
        tile.state = if(tileEnabled) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        tile.updateTile()*/
        if(tileEnabled)
            initDialog()
        super.onClick()
    }

    private fun initDialog(){

    }
}