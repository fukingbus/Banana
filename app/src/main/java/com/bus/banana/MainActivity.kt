package com.bus.banana

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Bundle
import android.view.SurfaceView
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var projectionStatusText: TextView? = null
    private var virtualDisplayStatusText: TextView? = null
    private var previewSurface: SurfaceView? = null
    //
    private var projectionManager: MediaProjectionManager? = null
    private var mediaProjection: MediaProjection?= null
    private var virtualDisplay: VirtualDisplay?=null
    //
    private companion object {
        const val REQUEST_PROJECTION = 27015
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolBar))
        init()
    }

    private fun init(){
        val projectionSwitch = findViewById<SwitchCompat>(R.id.mediaProjectionSwitch)
        val virtualDisplaySwitch = findViewById<SwitchCompat>(R.id.virtualDisplaySwitch)
        projectionSwitch.setOnCheckedChangeListener { compoundButton, isChecked ->
            if(isChecked){
                requestProjection()
            }
            else{
                projectionStatusText?.text = "Idle"
                mediaProjection?.stop()
                mediaProjection = null
                virtualDisplaySwitch.isEnabled = false
                virtualDisplaySwitch.isChecked = false
            }
        }
        virtualDisplaySwitch.setOnCheckedChangeListener { compoundButton, isChecked ->
            if(isChecked){
                requestMirroring()
            }
            else{
                stopMirroring()
            }
        }
        projectionStatusText = findViewById(R.id.mediaProjectionStatusText)
        virtualDisplayStatusText = findViewById(R.id.virtualDisplayStatusText)
        previewSurface = findViewById(R.id.previewSurface)
    }

    private fun stopMirroring(){
        virtualDisplayStatusText?.text = "Idle"
        virtualDisplay?.release()
        virtualDisplay = null
    }

    private fun requestProjection(){
        if(mediaProjection == null){
            projectionManager = getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
            startActivityForResult(projectionManager!!.createScreenCaptureIntent(),REQUEST_PROJECTION)
        }
    }

    private fun requestMirroring(){
        val metrics = resources.displayMetrics
        virtualDisplay = mediaProjection?.createVirtualDisplay(
                "PREVIEW",
                previewSurface!!.width,previewSurface!!.height, metrics.densityDpi,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                previewSurface?.holder?.surface, null, null
        )
        virtualDisplayStatusText?.text = "Mirroring"
    }

    private fun readyForRecord(){
        projectionStatusText!!.text = "Ready"
        virtualDisplaySwitch!!.isEnabled = true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            REQUEST_PROJECTION -> {
                if(resultCode == Activity.RESULT_OK){
                    mediaProjection = projectionManager!!.getMediaProjection(resultCode,data)
                    readyForRecord()
                }
            }
        }
    }
}
