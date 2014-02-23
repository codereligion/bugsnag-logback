# Bugsnag-Logback [![Build Status](https://ssl.webpack.de/secure-jenkins.codereligion.com/buildStatus/icon?job=bugsnag-logback-master-build-flow)](http://jenkins.codereligion.com/view/bugsnag-logback/job/bugsnag-logback-master-build-flow/)

A logback appender which pushes any event containing an exception to bugsnag.

## Requirements
* Java 1.5 or higher
* logback
* dependencies see [maven pom](pom.xml)

## Maven ##
```xml
<dependency>
	<groupId>com.codereligion</groupId>
	<artifactId>bugsnag-logback</artifactId>
	<version>1.0.0-SNAPSHOT</version>
</dependency>
```

## Configuration
```xml
<appender name="BUGSNAG" class="com.codereligion.bugsnag.logback.Appender">
    <!-- required, the api key from your project's settings page -->
    <apiKey>yourKey</apiKey>

    <!-- optional (default: false), allows switching between http and https -->
    <sslEnabled>true</sslEnabled>

    <!-- optional (default: production), the current release stage for the application -->
    <releaseStage>staging</releaseStage>

    <!-- optional (default: empty), comma separated set of stage names which is matched against
    the releaseStage to decide whether to send the notification or not -->
    <notifyReleaseStages>staging,production</notifyReleaseStages>

    <!-- optional (default: empty), comma separated set of package names which is used to
    highlight project specific lines of exception stacktraces -->
    <projectPackages>com.your.project.package.name</projectPackages>

    <!-- optional (default: empty), comma separated set of fully qualified class names which is
    used to ignore specific throwables -->
    <ignoreClasses>com.some.ignored.package,com.some.other.package</ignoreClasses>

    <!-- optional (default: empty), comma separated set of strings which will be removed from
    any (nested) object provided by a MetaDataProvider -->
    <filters>password,key</filters>

    <!-- optional, fully qualified name of your MetaDataProvider implementation -->
    <metaDataProvider>com.your.project.MetaDataProvider</metaDataProvider>

    <!-- optional (default: notify.bugsnag.com), the url to notify -->
    <endpoint>notify.bugsnag.com</endpoint>
</appender>
```

## Meta data

### Bugsnag proprietary meta data
Each logback event is mapped to a bugsnag event. Bugsnag events can contain the following optional meta data fields:

* ```userId``` - A unique identifier for a user affected by this event. This could be any distinct identifier that
makes sense for your application/platform.
* ```appVersion``` - The version number of the application which generated the error.
* ```osVersion``` - The operating system version of the client that the error was generated on.
* ```context``` - A string representing what was happening in the application at the time of the error. This string
could be used for grouping purposes, depending on the event. Usually this would represent the controller and
action in a server based project. It could represent the screen that the user was interacting with in a client side
project.
* ```groupingHash``` - All errors with the same groupingHash will be grouped together within the bugsnag dashboard.
This gives a notifier more control as to how grouping should be performed. Bugsnag recommends including the errorClass
of the exception in here so a different class of error will be grouped separately.

On constructions of bugsnag events the appender tries to find those values in the following scopes:

* system properties
* logger context
* mapped diagnostic context (aka. MDC)

In case a property appears in more than one scope the more detailed scope overrules the less detailed one:<br/>
mapped diagnostic context > logger context > system properties

### Custom meta data fields
By implementing and registering a ```MetaDataProvider``` you can add groups of key/value pairs to each event. Each group
manifests itself as an additional tab on the bugsnag view of an event. Each tab contains a list of key/value pairs.
Keys are expected to be strings, whereas value can be complex objects.

Example:
```java
package com.codereligion.bugsnag.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.codereligion.bugsnag.logback.model.MetaDataVO;
import java.util.Map;

public class ExampleMetaDataProvider implements MetaDataProvider {

    @Override
    public MetaDataVO provide(ILoggingEvent loggingEvent) {
        final Map<String,String> mdcMap = loggingEvent.getMDCPropertyMap();
        final Map<String, String> loggerContextMap = loggingEvent.getLoggerContextVO().getPropertyMap();

        return new MetaDataVO()
                // add some details about the logging event
                .addToTab("Logging Details", "message", loggingEvent.getFormattedMessage())
                .addToTab("Logging Details", "level", loggingEvent.getLevel())

                // add some information from the MDC
                .addToTab("User Details", "name", mdcMap.get("userName"))
                .addToTab("User Details", "email", mdcMap.get("email"))

                // add some information from the LoggerContext
                .addToTab("Application Details", "releaseDate", loggerContextMap.get("appReleaseDate"));
    }
}

```