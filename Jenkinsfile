pipeline {
    agent any
    tools {
        maven 'Maven-3.3.9'
        docker 'Docker-latest'
    }
    stages {
        stage('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }

        stage('Build') {
            steps {
                sh 'mvn -Dmaven.test.failure.ignore=true -Dspring.profiles.active=jenkins install'
            }
            post {
                success {
                    junit 'target/surefire-reports/**/*.xml'
                }
            }
            steps {
                docker.build "gallery-manager:${env.BUILD_TAG}"
            }
        }
    }
}