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
                sh 'docker compose up -d --build'
                sh '''
                    echo "Waiting for the calculator to become healthy..."
                    for i in $(seq 1 20); do
                        status=$(docker inspect --format '{{.State.Health.Status}}' calculator 2>/dev/null || echo none)
                        if [ "$status" = "healthy" ]; then
                            echo "Calculator container is healthy"
                            if docker exec calculator-proxy wget -q -O - http://localhost/actuator/health | grep -q '"status":"UP"'; then
                                echo "Verified end-to-end through the nginx proxy"
                            fi
                            docker compose ps
                            exit 0
                        fi
                        echo "  status: $status (attempt $i)"
                        sleep 4
                    done
                    echo "Service did not become healthy in time"
                    docker compose ps
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
