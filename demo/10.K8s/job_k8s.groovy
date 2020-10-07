pipeline {

  environment {
    PROJECT = "REPLACE_WITH_YOUR_PROJECT_ID"
    JENKINS_CRED = "${PROJECT}"
  }

  agent {
    kubernetes {
      label 'sample-app'
      idleMinutes 5 
      yaml """
apiVersion: v1
kind: Pod
metadata:
  name: curltest
  namespace: ci-cd
spec:
  containers:
  - name: curltest
    image: tutum/curl
    imagePullPolicy: IfNotPresent
    command:
    - cat
    tty: true
  restartPolicy: Always
"""
}
  }
  stages {
    stage('Test') {
      steps {
        container('curltest') {
          sh 'curl --version'
        }
      }
    }
  }
}