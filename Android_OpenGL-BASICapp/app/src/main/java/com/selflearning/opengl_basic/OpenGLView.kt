package com.selflearning.opengl_basic

import android.opengl.GLSurfaceView
import android.content.Context

class OpenGLView(ctx: Context) : GLSurfaceView(ctx) {
    init {
        setEGLContextClientVersion(2)  // Specify OpenGl ES 2.0
        val renderer = OpenGLRenderer()
        setRenderer(renderer)  // Set the renderer for this GLSurfaceView
    }
}