/**
 *  XILLIO CONFIDENTIAL
 *  __________________
 *
 *  [2017] XILLIO NEDERLAND B.V.
 *  All Rights Reserved.
 *
 *  NOTICE:  All information contained herein is, and remains
 *  the property of Xillio Nederland B.V. and its suppliers,
 *  if any.  The intellectual and technical concepts contained
 *  herein are proprietary to Xillio Nederland B.V. and its suppliers
 *  and are protected by trade secret or copyright law.
 *  Dissemination of this information or reproduction of this material
 *  is strictly forbidden unless prior written permission is obtained
 *  from Xillio Nederland B.V.
 */
def mvn(String goals) {
    def args = []

    if(params.SKIP_TESTS) {
        args.add("-DskipTests")
    }

    sh "mvn -B ${args.join(' ')} $goals"
}

pipeline {
    agent any
    options {
        timeout(time: 1, unit: 'HOURS')
        buildDiscarder(logRotator(numToKeepStr: '20', artifactNumToKeepStr: '5'))
    }
    parameters {
        booleanParam(name: 'SKIP_TESTS', defaultValue: false, description: 'Skip all maven tests')
        booleanParam(name: 'NO_SONAR', defaultValue: false, description: 'Skip sonar analysis')
    }
    stages {
        /**
         * Build all modules
         */
        stage('Build') {
            failFast true
            parallel {
                stage('Maven Build') {
                    agent {
                        dockerfile {
                            dir 'buildagent'
                            label 'dockerhost'
                            args '-u 0:0 -v /home/jenkins/.m2/repository:/root/.m2/repository'
                        }
                    }
                    steps {
                        mvn 'clean'
                        script {
                            mvn "install -e --fail-at-end"
                        }
                    }
                    post {
                        always {
                            junit allowEmptyResults: true, testResults: '**/target/*-reports/*.xml'
                            jacoco(execPattern: 'target/jacoco.exec')
                        }
                    }
                }
            }
        }
        /**
         * Run the sonar analysis
         */
        stage('Run Sonar') {
            agent {
                dockerfile {
                    dir 'buildagent'
                    label 'dockerhost'
                    args '-u 0:0 -v /home/jenkins/.m2/repository:/root/.m2/repository'
                }
            }
            environment {
                SONAR_TOKEN = credentials('SONAR_TOKEN')
            }
            when {
                expression {
                    !params.NO_SONAR
                }
            }
            steps {
                script {
                    def sonarOptions = [
                            "-Dsonar.login=${env.SONAR_TOKEN}",
                            "-Dsonar.branch=${env.BRANCH_NAME}",
                            "-Dsonar.host.url=https://sonarcloud.io"
                    ]

                    mvn "dependency:copy-dependencies sonar:sonar ${sonarOptions.join(' ')}"
                }
            }
        }
    }
}
