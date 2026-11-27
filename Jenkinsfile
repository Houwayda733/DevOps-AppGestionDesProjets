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
                        withCredentials([string(credentialsId: 'sonarqube-token', variable: 'SONAR_TOKEN')]) {
                            sh 'mvn clean verify org.sonarsource.scanner.maven:sonar-maven-plugin:5.9.0.7382:sonar -DskipTests -Dsonar.projectKey=gestion-des-projets-backend -Dsonar.token=$SONAR_TOKEN'
                        }
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
                sh '''
                    docker compose down --remove-orphans || true
                    docker rm -f mysql-db backend-app frontend-app || true
                    docker compose up -d
                '''
            }
        }
    }
}
