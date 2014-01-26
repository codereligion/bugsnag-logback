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

## Example xml configuration
```xml
    <appender name="BUGSNAG" class="com.codereligion.bugsnag.logback.Appender">
        <!-- required -->
        <apiKey>yourKey</apiKey>

        <!-- optional, defaults to false -->
        <sslEnabled>true</sslEnabled>

        <!-- optional, defaults to production -->
        <releaseStage>staging</releaseStage>

        <!-- optional, comma separated set of stage names, defaults to an empty set -->
        <notifyReleaseStages>staging,production</notifyReleaseStages>

        <!-- optional, comma separated set of package names, defaults to an empty set-->
        <projectPackages>com.your.project.package.name</projectPackages>

        <!-- optional, comma separated set of package names, defaults to an empty set -->
        <ignoreClasses>com.some.ignored.package,com.some.other.package</ignoreClasses>

        <!-- optional, comma separated set of to be filtered words, defaults to an empty set -->
        <filters>password,key</filters>

        <!-- optional, fully qualified name of your meta data provider class -->
        <metaDataProviderClassName>com.your.project.MetaDataProvider</metaDataProviderClassName>

        <!-- optional, defaults to notify.bugsnag.com -->
        <endpoint>notify.bugsnag.com</endpoint>
    </appender>
```

## Example meta data provider
```java
package com.codereligion.bugsnag.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.codereligion.bugsnag.logback.model.MetaDataVO;

public class ExampleMetaDataProvider implements MetaDataProvider {

    @Override
    public MetaDataVO provide(ILoggingEvent loggingEvent) {
        return new MetaDataVO()
                .addToTab("Logging Details", "message", loggingEvent.getFormattedMessage())
                .addToTab("Logging Details", "level", loggingEvent.getLevel());
    }
}

```