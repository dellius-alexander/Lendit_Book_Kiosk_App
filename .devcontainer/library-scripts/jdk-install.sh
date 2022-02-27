#!/usr/bin/env bash
###############################################################################
tar xzvf /tmp/library-scripts/packages/jdk-11.0.12_linux-x64_bin.tar  -C  /opt/
export JAVA_HOME='/opt/jdk-11.0.12/'
export PATH=$JAVA_HOME/bin:$PATH