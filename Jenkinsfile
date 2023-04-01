pipeline {
    agent any
    tools {
        maven 'Maven-3.3.9'
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

        stage('Test') {
            steps {
                sh 'mvn -Dspring.profiles.active=jenkins test'
            }
            post {
                always {
                    junit 'target/surefire-reports/**/*.xml'
                }
            }
        }
        stage('docker-build') {
            steps {
                sh 'mvn clean package -DskipTests=true -Dspring.profiles.active=docker'
                sh "docker build -t gallery-management-service:${env.BUILD_TAG} ."
            }
        }

    }
}