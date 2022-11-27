package com.actia.myapplication.util

import okhttp3.mockwebserver.MockWebServer
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

interface IMockWebServer {

    fun runningMockWebServer(serverClosure: (MockWebServer) -> Unit) {
        val mockWebServer = MockWebServer()

        serverClosure(mockWebServer)

        // Finish web server
        mockWebServer.shutdown()
    }
}