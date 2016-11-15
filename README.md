# accessibilitools [![Build](https://ci.novoda.com/buildStatus/icon?job=accessibilitools)](https://ci.novoda.com/job/accessibilitools/lastBuild/console) [![Download](https://api.bintray.com/packages/novoda/maven/accessibilitools/images/download.svg)](https://bintray.com/novoda/maven/accessibilitools/_latestVersion) [![License](https://raw.githubusercontent.com/novoda/novoda/master/assets/btn_apache_lisence.png)](LICENSE.txt)

EZ tools to help make your app accessible.

## Description

This is a library of utility classes and methods to help you develop accessible Android apps.

It contains a set of accessibility-related classes that are common to our projects:

- `AccessibilityServices` - check which services are currently enabled, by type (e.g. spoken feedback)
- `Actions` - an abstraction around actions that can be performed on a UI element to implement custom accessibility actions

## Adding to your project

To start using this library, add these lines to the `build.gradle` of your project:

```groovy
repositories {
    jcenter()
}

dependencies {
    compile 'com.novoda:accessibilitools:<latest-version>'
}
```

## Simple usage

As accessibilitools is a library of utility classes and methods, usage and examples can be found in the [Github Wiki](https://github.com/novoda/accessibilitools/wiki).


## Links

Here are a list of useful links:

 * We always welcome people to contribute new features or bug fixes, [here is how](https://github.com/novoda/novoda/blob/master/CONTRIBUTING.md)
 * If you have a problem check the [Issues Page](https://github.com/novoda/accessibilitools/issues) first to see if we are working on it
 * For further usage or to delve more deeply checkout the [Project Wiki](https://github.com/novoda/accessibilitools/wiki)
