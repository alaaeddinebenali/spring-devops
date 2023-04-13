pipeline{
    agent any
    tools {
        maven 'M2_HOME'
    }
    environment {
      // This can be nexus3 or nexus2
      NEXUS_VERSION = "nexus3"
      // This can be http or https
      NEXUS_PROTOCOL = "http"
      // Where your Nexus is running
      NEXUS_URL = "192.168.1.112:8081"
      // Repository where we will upload the artifact
      NEXUS_REPOSITORY = "maven-spring-boot-snapshots"
      // Jenkins credential id to authenticate to Nexus OSS
      NEXUS_CREDENTIAL_ID = "nexus-user-credentials"

      registry = "alaaeddinebenali/spring-tp-achat-project"

      registryCredential = 'dckr_pat_gHtCYGm2_UqL13ySb1MptKUB1to'
      DOCKERHUB_CREDENTIALS = credentials('docker-hub-creds')

      dockerImage = ''
     }
    stages{
        stage("Checkout Project"){
            steps{
                echo "Pulling ..."
                echo "git --version"
                git branch: 'alaa-branch',
                credentialsId: 'e2246c89-5063-4ed2-b81e-4f7a2d61f866',
                url: 'https://github.com/alaaeddinebenali/spring-devops'

            }
        }

        stage('Clean Project') {
            steps {
                echo "mvn --version"
                sh "mvn --version"
                echo "Cleaning ..."
                sh "mvn clean"
            }
        }

        stage('Packaging Project') {
            steps {
                echo "Packging ..."
                sh "mvn package"
            }
        }

        stage('Test & Jacoco Static Analysis') {
            steps {
                echo 'Code Coverage'
                jacoco()
            }
        }

        stage('Sonar Scanner Coverage') {
            steps{
                echo "Sonar analysing quality code ..."
                sh "mvn sonar:sonar -Dsonar.projectKey=spring-boot -Dsonar.login=ba90109aa9d3264d45cd1832b599edab1bdd691c"
            }
        }

        stage("publish to nexus") {
            steps {
                script {
                    // Read POM xml file using 'readMavenPom' step , this step 'readMavenPom' is included in: https://plugins.jenkins.io/pipeline-utility-steps
                    pom = readMavenPom file: "pom.xml";
                    // Find built artifact under target folder
                    filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                    // Print some info from the artifact found
                    echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                    // Extract the path from the File found
                    artifactPath = filesByGlob[0].path;
                    // Assign to a boolean response verifying If the artifact name exists
                    artifactExists = fileExists artifactPath;

                    if(artifactExists) {
                        echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";

                        nexusArtifactUploader(
                            nexusVersion: NEXUS_VERSION,
                            protocol: NEXUS_PROTOCOL,
                            nexusUrl: NEXUS_URL,
                            groupId: pom.groupId,
                            version: pom.version,
                            repository: NEXUS_REPOSITORY,
                            credentialsId: NEXUS_CREDENTIAL_ID,
                            artifacts: [
                                // Artifact generated such as .jar, .ear and .war files.
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: artifactPath,
                                type: pom.packaging],

                                // Lets upload the pom.xml file for additional information for Transitive dependencies
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: "pom.xml",
                                type: "pom"]
                            ]
                        );

                    } else {
                        error "*** File: ${artifactPath}, could not be found";
                    }
                }
            }
        }

        /*stage ('Login to docker hub') {
            steps {
                sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
            }
        }*/

        stage('Building our image') {
            steps {
                script {
                    dockerImage = docker.build(registry + ":$BUILD_NUMBER")
                }
            }
        }

        stage('Push') {
            steps {
                sh 'docker push alaaeddinebenali/spring-tp-achat-project'
            }
        }

        stage('Deploy our image') {
            /*withCredentials([usernamePassword(credentialsId: 'docker-hub-creds', passwordVariable: 'DOCKER_HUB_PASSWORD', usernameVariable: 'DOCKER_HUB_USERNAME')]) {
                sh "docker login -u $DOCKER_HUB_USERNAME -p $DOCKER_HUB_PASSWORD"
                sh "docker build -t your-docker-username/your-docker-repo-name ."
                sh "docker push your-docker-username/your-docker-repo-name"
            }*/
            steps {
                script {
                    docker.withRegistry( 'https://registry.hub.docker.com', registryCredential ) {
                        dockerImage.push()
                    }
                }
             }
        }
    }

    post {
        always {
            echo 'JENKINS PIPELINE'
        }
        success {
            echo 'JENKINS PIPELINE SUCCESSFUL'
        }
        failure {
            echo 'JENKINS PIPELINE FAILED'
        }
        unstable {
            echo 'JENKINS PIPELINE WAS MARKED AS UNSTABLE'
        }
        changed {
            echo 'JENKINS PIPELINE STATUS HAS CHANGED SINCE LAST EXECUTION'
        }
    }
}