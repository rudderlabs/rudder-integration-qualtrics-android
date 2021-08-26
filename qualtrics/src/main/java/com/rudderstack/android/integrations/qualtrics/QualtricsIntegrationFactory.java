package com.rudderstack.android.integrations.qualtrics;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.qualtrics.digital.Qualtrics;
import com.qualtrics.digital.QualtricsLogLevel;
import com.rudderstack.android.sdk.core.MessageType;
import com.rudderstack.android.sdk.core.RudderClient;
import com.rudderstack.android.sdk.core.RudderConfig;
import com.rudderstack.android.sdk.core.RudderContext;
import com.rudderstack.android.sdk.core.RudderIntegration;
import com.rudderstack.android.sdk.core.RudderLogger;
import com.rudderstack.android.sdk.core.RudderMessage;

import java.util.Map;


public class QualtricsIntegrationFactory extends RudderIntegration<Qualtrics> {
    private static final String QUALTRICS_KEY = "Qualtrics";
    private Qualtrics qualtrics = Qualtrics.instance();

    public static Factory FACTORY = new Factory() {
        @Override
        public RudderIntegration<?> create(Object settings, RudderClient client, RudderConfig rudderConfig) {
            return new QualtricsIntegrationFactory(settings, rudderConfig);
        }

        @Override
        public String key() {
            return QUALTRICS_KEY;
        }
    };

    private QualtricsIntegrationFactory(@NonNull Object config, RudderConfig rudderConfig) {
        if (RudderClient.getApplication() == null) {
            RudderLogger.logError("Application is null. Aborting Qualtrics initialization.");
            return;
        }

        Gson gson = new Gson();
        QualtricsDestinationConfig destinationConfig = gson.fromJson(
                gson.toJson(config),
                QualtricsDestinationConfig.class
        );

        if (TextUtils.isEmpty(destinationConfig.brandId) || TextUtils.isEmpty(destinationConfig.projectId)) {
            RudderLogger.logError("Invalid Qualtrics Account Credentials, Aborting");
            return;
        }

        //Debugger of Qualtrics
        if (rudderConfig.getLogLevel() >= RudderLogger.RudderLogLevel.INFO) {
            qualtrics.setLogLevel(QualtricsLogLevel.INFO);
        }

        qualtrics.initializeProject(destinationConfig.brandId, destinationConfig.projectId, RudderClient.getApplication());
        RudderLogger.logInfo("Initialized Qualtrics SDK");
    }

    private void processRudderEvent(RudderMessage element) {
        String type = element.getType();
        if (type != null) {
            switch (type) {
                case MessageType.IDENTIFY:
                    RudderContext rudderContext = element.getContext();
                    Map<String, Object> traits = rudderContext.getTraits();
                    for (String property : traits.keySet()) {
                        Qualtrics.instance().properties.setString(property, (String) traits.get(property));
                    }
                    break;
                default:
                    RudderLogger.logWarn("MessageType is not specified or supported");
                    break;
            }
        }
    }

    @Override
    public void reset() {
        RudderLogger.logDebug("Qualtrics Factory doesn't support Reset Call");
    }

    @Override
    public void dump(@Nullable RudderMessage element) {
        try {
            if (element != null) {
                processRudderEvent(element);
            }
        } catch (Exception e) {
            RudderLogger.logError(e);
        }
    }

    @Override
    public Qualtrics getUnderlyingInstance() {
        return qualtrics;
    }
}