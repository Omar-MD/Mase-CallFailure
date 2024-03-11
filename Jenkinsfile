pipeline {
    agent any

    environment {
        TARGET_JAR = "/Users/omarduadu/tools/jenkins/workspace/Cipher-Project_US-B/target/mase-project-0.0.1-SNAPSHOT.jar"
        REMOTE_USER = "ec2-user"
        REMOTE_HOST = "51.20.108.108"
        REMOTE_DIR = "/home/ec2-user/call_failure_project/"
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    checkout scm
                }
            }
        }

        stage('Build & Test') {
            steps {
                script {
                    sh 'mvn clean package'
                }
            }
        }

        stage('SonarQube') {
            steps {
                withSonarQubeEnv('sonarqube-server'){
                     sh "mvn sonar:sonar -Dsonar.projectKey=mase-project -Dsonar.projectName='Mase-Project'"
                }
            }
        }
        
        stage('Deploy(SCP EC2') {
            steps {
                withCredentials([sshUserPrivateKey(credentialsId: 'ec2-sshKey', keyFileVariable: 'SSH_KEY')]) {
                    sh '''
                    scp -i $SSH_KEY ${TARGET_JAR} ${REMOTE_USER}@${REMOTE_HOST}:${REMOTE_DIR}
                    '''
                }
            }
        }   
    }
    
    post {
        success {
            echo 'Pipeline successful!'
        }
        
        failure {
            echo 'Pipeline failed!'
        }
    }
}
