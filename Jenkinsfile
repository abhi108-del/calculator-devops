pipeline {
    agent any

    environment {
        IMAGE   = "2023108/calculator-devops"
        REGISTRY = "https://index.docker.io/v1/"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh './mvnw -B clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh './mvnw -B test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t $IMAGE:$BUILD_NUMBER -t $IMAGE:latest .'
            }
        }

        stage('Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DH_USER', passwordVariable: 'DH_PASS')]) {
                    sh 'echo "$DH_PASS" | docker login -u "$DH_USER" --password-stdin'
                    sh 'docker push $IMAGE:$BUILD_NUMBER'
                    sh 'docker push $IMAGE:latest'
                }
            }
        }

        stage('Deploy') {
            steps {
                sh 'docker compose up -d'
                sh '''
                    echo "Waiting for the stack to become healthy..."
                    for i in $(seq 1 15); do
                        if curl -sf http://host.docker.internal:8090/actuator/health > /dev/null; then
                            echo "Service is UP"
                            exit 0
                        fi
                        sleep 4
                    done
                    echo "Service did not become healthy in time"
                    exit 1
                '''
            }
        }
    }

    post {
        always {
            sh 'docker logout || true'
        }
    }
}
