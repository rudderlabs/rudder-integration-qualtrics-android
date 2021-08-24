package com.rudderstack.android.sample.kotlin

import android.app.Application
import com.rudderstack.android.integrations.qualtrics.QualtricsIntegrationFactory
import com.rudderstack.android.sdk.core.RudderClient
import com.rudderstack.android.sdk.core.RudderConfig
import com.rudderstack.android.sdk.core.RudderLogger

class MainApplication : Application() {
    companion object {
        private const val WRITE_KEY = "1s4gjAsjU2O41t6JwCGsCgZf6sg"
        private const val DATA_PLANE_URL = "https://a6516bec708b.ngrok.io"
        private const val CONTROL_PLANE_URL = "https://e91d300db7e0.ngrok.io"
        lateinit var rudderClient: RudderClient
    }

    override fun onCreate() {
        super.onCreate()

        rudderClient = RudderClient.getInstance(
            this,
            WRITE_KEY,
            RudderConfig.Builder()
                .withDataPlaneUrl(DATA_PLANE_URL)
                .withControlPlaneUrl(CONTROL_PLANE_URL)
                .withFactory(QualtricsIntegrationFactory.FACTORY)
                .withLogLevel(RudderLogger.RudderLogLevel.NONE)
                .withTrackLifecycleEvents(false)
                .build()
        )
    }
}