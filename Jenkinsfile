pipeline {
    agent any
    stages {
        stage('Build & Test') {
            steps {
                echo 'Building applications...'
            }
        }
        stage('Création Image Docker') {
            steps {
                sh 'docker build -t amiradouzi/projets-backend:latest ./backend'
                sh 'docker build -t amiradouzi/projets-frontend:latest ./frontend'
            }
        }
        stage('Push DockerHub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USER')]) {
                    sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USER --password-stdin'
                    sh 'docker push amiradouzi/projets-backend:latest'
                    sh 'docker push amiradouzi/projets-frontend:latest'
                }
            }
        }
        stage('Conteneur') {
            steps {
                sh 'docker compose down || true'
                sh 'docker compose up -d'
            }
        }
    }
}
