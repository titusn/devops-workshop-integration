#
#  XILLIO CONFIDENTIAL
#  __________________
#
#  [2017] XILLIO NEDERLAND B.V.
#  All Rights Reserved.
#
#  NOTICE:  All information contained herein is, and remains
#  the property of Xillio Nederland B.V. and its suppliers,
#  if any.  The intellectual and technical concepts contained
#  herein are proprietary to Xillio Nederland B.V. and its suppliers
#  and are protected by trade secret or copyright law.
#  Dissemination of this information or reproduction of this material
#  is strictly forbidden unless prior written permission is obtained
#  from Xillio Nederland B.V.
#

FROM ubuntu:xenial

# Install Packages
RUN apt-get update && \
    apt-get install -y \
    # Java Development Environment
    openjdk-8-jdk git maven curl \
    # Build Dependencies
    build-essential udev libexif-dev software-properties-common unzip jq exiftool

# Install Docker
RUN curl -sSL https://get.docker.com | bash
