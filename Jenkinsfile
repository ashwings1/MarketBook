pipeline {
  agent any
  tools {
    jdk 'OpenJDK21' 
    nodejs 'Node20' 
    maven 'Maven3' 
  }
  stages {
    // Stage 1: Build React Frontend
    stage('Build Frontend') {
      steps {
        dir('frontend') { 
          sh 'npm install'
          sh 'npm run build'
        }
      }
    }

    // Stage 2: Build Spring Boot Backend
    stage('Build Backend') {
      steps {
        dir('backend') { 
          sh 'mvn clean package -DskipTests'
        }
      }
    }

    // Stage 3: Run Tests
    stage('Test') {
      steps {
        dir('backend') {
          sh 'mvn test' // Spring Boot tests
        }
        dir('frontend') {
          sh 'npm test -- --watchAll=false' // React tests
        }
      }
    }

    // Stage 4: Deploy 
    stage('Deploy') {
      steps {
              // Stop any running instances (optional but recommended)
          sh 'pkill -f "java -jar backend/target/*.jar" || true' // Kill existing Spring Boot app
          sh 'pkill -f "serve -s frontend/build" || true'       // Kill existing React server (if used)

          // Deploy Backend (Spring Boot)
          dir('backend') {
            sh 'java -jar target/*.jar &' // Run JAR in the background
          }

          // Deploy Frontend (React)
          dir('frontend') {
            sh 'npx serve -s build -l 3000 &' 
          }
    }
  }
}