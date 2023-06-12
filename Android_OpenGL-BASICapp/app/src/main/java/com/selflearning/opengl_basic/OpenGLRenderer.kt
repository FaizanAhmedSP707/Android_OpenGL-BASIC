package com.selflearning.opengl_basic

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class OpenGLRenderer : GLSurfaceView.Renderer {
    // We initialise the rendering here
    override fun onSurfaceCreated(unused: GL10, config: EGLConfig) {
        // Set the background colour (red=0, green=0, blue=0, alpha=1)
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)

        // Enable depth testing - will cause nearer 3D objects to automatically
        // be drawn over further objects
        GLES20.glClearDepthf(1.0f)
        GLES20.glEnable(GLES20.GL_DEPTH_TEST)

        // Setting up shaders
        val vertexShaderSrc =
            "attribute vec4 aVertex;\n" +
                "void main(void)\n" +
                "{\n" +
                "gl_Position = aVertex;\n" +
                "}\n"

        val fragmentShaderSrc =
            "precision mediump float;\n" +
                "uniform vec4 uColour;\n" +
                "void main(void)\n" +
                "{\n" +
                "gl_FragColor = uColour;\n" +
                "}\n"

        fun compileShader(shaderType: Int, shaderCode: String) : Int {
            val shader = GLES20.glCreateShader(shaderType)
            GLES20.glShaderSource(shader, shaderCode)
            GLES20.glCompileShader(shader)
            return shader
        }

        fun linkShader(vertexShader: Int, fragmentShader: Int) : Int {
            val shaderProgram = GLES20.glCreateProgram()
            GLES20.glAttachShader(shaderProgram, vertexShader)
            GLES20.glAttachShader(shaderProgram, fragmentShader)
            GLES20.glLinkProgram(shaderProgram)
            GLES20.glUseProgram(shaderProgram)
            return shaderProgram
        }

        // Setting up a vertex buffer
        fun makeBuffer(vertices: FloatArray) : FloatBuffer {
            val byteBuf : ByteBuffer = ByteBuffer.allocateDirect(vertices.size * Float.SIZE_BYTES)
            byteBuf.order(ByteOrder.nativeOrder())
            val floatBuf : FloatBuffer = byteBuf.asFloatBuffer()

            // Insert vertices into the array
            floatBuf.put(vertices)
            floatBuf.position(0)
            return floatBuf
        }
    }

    // We draw our shapes here
    override fun onDrawFrame(unused: GL10) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)
    }

    // Used if the screen is resized
    override fun onSurfaceChanged(unused: GL10, w: Int, h: Int) {
        GLES20.glViewport(0, 0, w, h)
    }
}