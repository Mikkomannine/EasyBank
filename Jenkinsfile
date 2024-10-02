/*pipeline {
    agent any
    tools {
        maven 'Maven3.9.9'  // Use the correct Maven tool name
        jdk 'JDK 21'        // Use the correct JDK tool name
    }
    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/Mikkomannine/EasyBank.git'
            }
        }
        stage('Build') {
            steps {
                bat 'mvn clean package'
            }
        }
        stage('Run Unit Tests') {
            steps {
                bat 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'  // Capture test reports
                }
            }
        }
        stage('Code Coverage Report') {
            steps {
                bat 'mvn jacoco:report'
            }
            post {
                always {
                    jacoco execPattern: 'target/jacoco.exec'
                }
            }
        }
    }
}*/
pipeline {
    agent any
    tools {
        maven 'Maven3.9.9'  // Use the correct Maven tool name
        jdk 'JDK 21'        // Use the correct JDK tool name
    }
    environment {
        // Define Docker Hub credentials ID
        DOCKERHUB_CREDENTIALS_ID ='dockerhub_credentials'
        // Define Docker Hub repository name
        DOCKERHUB_REPO ='mikktma/easybank'
        // Define Docker image tag
        DOCKER_IMAGE_TAG ='latest'
    }
    stages {
        stage('Checkout Code') {
            steps {
                // Checkout code from Git repository
                git branch: 'main', url: 'https://github.com/Mikkomannine/EasyBank.git'
            }
        }
        stage('Build') {
            steps {
                // Build the project
                bat 'mvn clean package' // Use 'sh' if on Unix-like systems
            }
        }
        stage('Run Unit Tests') {
            steps {
                // Run tests and generate JaCoCo report
                bat 'mvn test' // Use 'sh' if on Unix-like systems
            }
            post {
                always {
                    // Capture test reports
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('Code Coverage Report') {
            steps {
                // Generate JaCoCo report
                bat 'mvn jacoco:report' // Use 'sh' if on Unix-like systems
            }
            post {
                always {
                    // Publish the JaCoCo report
                    jacoco execPattern: 'target/jacoco.exec'
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                // Build Docker image
                script {
                    docker.build("${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}")
                }
            }
        }
        stage('Push Docker Image to Docker Hub') {
            steps {
                // Push Docker image to Docker Hub
                script {
                    docker.withRegistry('https://index.docker.io/v1/', DOCKERHUB_CREDENTIALS_ID) {
                        docker.image("${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}").push()
                    }
                }
            }
        }
    }
    post {
        always {
            // Cleanup Docker images if needed
            script {
                docker.image("${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}").remove()
            }
        }
    }
}
