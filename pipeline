pipeline {
    agent any

    triggers {
        pollSCM("H/2 * * * *")
    }

    stages {
        stage ("Check git") {
            steps {
                git branch: "jenkins", url: "https://github.com/vlad-gal/GiftSertificateSystem.git"
            }
        }
        stage ("Build, tests, make bootWar"){
            steps{
                withGradle {
                    bat 'gradle clean build bootWar'
                }
            }
        }
        stage ("SonarQube"){
            environment {
                scannerHome = tool 'scanner'
            }
            steps{
                withSonarQubeEnv(installationName: 'sonarqube') {
                     bat "${scannerHome}\\bin\\sonar-scanner.bat"
                }
            }
        }
        stage ("Deploy"){
            steps{
                deploy adapters: [tomcat9(credentialsId: 'f495704f-7a4c-40f5-82ae-9bc9f3f846cb', path: '', url: 'http://localhost:8087/')], contextPath: '/', onFailure: false, war: 'rest/build/libs/*.war'
            }
        }
    }
}