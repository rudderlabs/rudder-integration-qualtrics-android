# What is RudderStack?

[RudderStack](https://rudderstack.com/) is a **customer data pipeline** tool for collecting, routing and processing data from your websites, apps, cloud tools, and data warehouse.

More information on RudderStack can be found [here](https://github.com/rudderlabs/rudder-server).

## Integrating Qualtrics for RudderStack's Android SDK

1. Add [Qualtrics](https://www.qualtrics.com/) as a destination in the [RudderStack dashboard](https://app.rudderstack.com/) and define the Project ID and Brand ID.

2. Add the following `dependencies` to your `app/build.gradle` file as shown:

```groovy
implementation 'com.rudderstack.android.sdk:core:1.+'
implementation 'com.rudderstack.android.integration:qualtrics:1.0.0'
implementation 'com.google.code.gson:gson:2.8.6'

// Qualtrics dependency
implementation 'com.qualtrics:digital:2.0.0'
```

3. Add a new maven repository for the App Feedback package.

```groovy
allprojects {
    repositories {
        google()
        jcenter()
        maven {
            url "https://s3-us-west-2.amazonaws.com/si-mobile-sdks/android/"
        }
    }
}
```

4. Open the AndroidManifest.xml file from the src/main folder in the project root and add the following XML snippet after the existing activity:

```xml
<activity android:name=
    "com.qualtrics.digital.QualtricsSurveyActivity"/>
<activity android:name=
    "com.qualtrics.digital.QualtricsPopOverActivity" 
          android:theme="@style/Qualtrics.Theme.Transparent"/>
```

5. Finally change the initialization of your `RudderClient` in your `Application` class:

```groovy
val rudderClient = RudderClient.getInstance(
    this,
    <YOUR_WRITE_KEY>,
    RudderConfig.Builder()
        .withDataPlaneUrl(<YOUR_DATA_PLANE_URL>)
        .withFactory(QualtricsIntegrationFactory.FACTORY)
        .build()
)
```

## Send Events

Follow the steps from the [RudderStack Android SDK](https://github.com/rudderlabs/rudder-sdk-android).

## Contact Us

If you come across any issues while configuring or using this integration, please feel free to start a conversation on our [Slack](https://resources.rudderstack.com/join-rudderstack-slack) channel. We will be happy to help you.
