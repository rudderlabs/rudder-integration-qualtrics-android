package com.rudderstack.android.sample.kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.qualtrics.digital.IQualtricsProjectEvaluationCallback
import com.qualtrics.digital.Qualtrics
import com.qualtrics.digital.TargetingResult
import com.rudderlabs.android.sample.kotlin.R
import com.rudderstack.android.sdk.core.RudderClient
import com.rudderstack.android.sdk.core.RudderTraits

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        supportActionBar!!.title = "Activity 2"

        val iQualtricsProjectEvaluationCallback =
            IQualtricsProjectEvaluationCallback { targetingResults: Map<String?, TargetingResult> ->
                for ((key, value) in targetingResults) if (value.passed()) {
                    Qualtrics.instance().displayIntercept(RudderClient.getApplication(), key)
                }
            }

        val traits = RudderTraits()
        traits.put("firstName", "Random_2")

        val buttonTrack = findViewById<View>(R.id.button3) as Button
        if (buttonTrack != null) {
            buttonTrack.setOnClickListener(View.OnClickListener {
                MainApplication.rudderClient.identify(traits)
                Qualtrics.instance().evaluateProject(iQualtricsProjectEvaluationCallback)
            })
        }

    }

    fun openMainActivity(view: View) {
        startActivity(Intent(this, MainActivity::class.java))
    }


}