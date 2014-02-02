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
	<version>${project.version}</version>
</dependency>
```

## Configuration
For a detailed description of the configuration parameters check the [wiki](https://github.com/codereligion/bugsnag-logback/wiki).
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
By implementing and registering a ```MetaDataProvider``` one can add groups of key/value pairs to each event. Each group
manifests itself as an additional tab on the bugsnag view of an event. Each tab contains a list of key/value pairs.
Keys are expected to be strings, whereas value can be complex objects.

Example:
```java
${exampleMetaDataProvider}
```