#!/usr/bin/env sh

##############################################################################
##
## Gradle wrapper script
##
##############################################################################

DIR="$( cd "$( dirname "$0" )" && pwd )"

CLASSPATH=$DIR/gradle/wrapper/gradle-wrapper.jar

JAVA_CMD="java"

exec "$JAVA_CMD" -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"