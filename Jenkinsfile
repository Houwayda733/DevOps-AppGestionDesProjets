pipeline {
    agent any
    tools {
        maven 'Default Maven'
    }
    stages {
        stage('Build & Test') {
            steps {
                echo 'Building applications...'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                dir('backend') {
                    withSonarQubeEnv('SonarQube') {
                        sh 'mvn clean verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -DskipTests -DskipTests -Dsonar.projectKey=gestion-des-projets-backend'
                    }
                }
            }
        }
        stage('Création Image Docker') {
            steps {
                sh 'docker build -t houwayda12/projets-backend:latest ./backend'
                sh 'docker build -t houwayda12/projets-frontend:latest ./frontend'
            }
        }
        stage('Push DockerHub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USER')]) {
                    sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USER --password-stdin'
                    sh 'docker push houwayda12/projets-backend:latest'
                    sh 'docker push houwayda12/projets-frontend:latest'
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
