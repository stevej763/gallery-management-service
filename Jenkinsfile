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
            environment {
                DOCKER_CREDS = credentials('docker-creds')
            }
            steps {
                sh 'mvn clean package -DskipTests=true -Dspring.profiles.active=docker'
                sh "docker build -t gallery-manager:0.${env.BUILD_ID} ."
                sh "docker login -u ${DOCKER_CREDS_USR} -p ${DOCKER_CREDS_PSW}"
                sh "docker push ${DOCKER_CREDS_USR}/gallery-manager:0.${env.BUILD_ID}"
            }
        }

    }
}