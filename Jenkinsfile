#!groovy
import sun.nio.fs.GnomeFileTypeDetector

pipeline {
   agent{
      label("master")
   }
   options{
      timestamps()
   }
   stages {
      stage('Git clone project') {
         steps {
            //Delete Workspace before build project
            cleanWs()
            // Get code from a GitHub repository
            git 'https://github.com/ausard/devops176.git'
         }
      }
      stage('Build project'){
         steps{
            // Run Maven on a Unix agent.
            //sh "./mvnw install -DskipTests=true -Dmaven.javadoc.skip=true -B -V"
            sh label: 'Build project ', script: "mvn clean package"
            //interrupt with error
            // exit 1
         }
      }
   }
   post {
      success{
         stage('docker') {
            agent {
               dockerfile true
            }
            steps {
               sh 'java --version'
            }
      }
      failure
      {
         echo currentBuild.result
         //email with log and Build's status
         emailext (
             attachLog: true,
             compressLog: true,
             mimeType: 'text/html',
             subject: "Job '${env.JOB_NAME} ${env.BUILD_NUMBER}' is ${currentBuild.result}",
             body: """<p>Status buiid is "${currentBuild.result}"</p>
             <p>Check console output at <a href="${env.BUILD_URL}">${env.JOB_NAME}</a></p>""",
             to: "ausard@yandex.ru"
         )
      }
   }
}