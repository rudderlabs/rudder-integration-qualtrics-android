package com.rudderstack.android.sample.kotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.qualtrics.digital.IQualtricsProjectEvaluationCallback
import com.qualtrics.digital.Qualtrics
import com.qualtrics.digital.TargetingResult
import com.rudderlabs.android.sample.kotlin.R
import com.rudderstack.android.sdk.core.RudderClient
import com.rudderstack.android.sdk.core.RudderTraits
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.title = "Activity 1"

        val iQualtricsProjectEvaluationCallback =
            IQualtricsProjectEvaluationCallback { targetingResults: Map<String?, TargetingResult> ->
                for ((key, value) in targetingResults) if (value.passed()) {
                    Qualtrics.instance().displayIntercept(RudderClient.getApplication(), key)
                }
            }

        val traits = RudderTraits()
        traits.put("firstName", "Random_1")

        val buttonTrack = findViewById<View>(R.id.button2) as Button
        if (buttonTrack != null) {
            buttonTrack.setOnClickListener(View.OnClickListener {
                MainApplication.rudderClient.identify(traits)
                Qualtrics.instance().evaluateProject(iQualtricsProjectEvaluationCallback)
            })
        }

        val buttonReset = findViewById<View>(R.id.reset) as Button
        if (buttonReset != null) {
            buttonReset.setOnClickListener(View.OnClickListener {
                MainApplication.rudderClient.reset()
            })
        }

    }

    fun openMainActivity2(view: View) {
        startActivity(Intent(this, MainActivity2::class.java))
    }
}
