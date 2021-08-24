package com.rudderstack.android.integrations.qualtrics;

public class QualtricsDestinationConfig {
    final String brandId;
    String projectId;

    public QualtricsDestinationConfig(String brandId, String projectId) {
        this.brandId = brandId;
        this.projectId = projectId;
    }
}
