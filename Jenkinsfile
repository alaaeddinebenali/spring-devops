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

        DOCKER_HUB_USERNAME= 'alaaeddinebenali'
        DOCKER_HUB_PASSWORD= 'dckr_pat_gHtCYGm2_UqL13ySb1MptKUB1to'
        DOCKER_HUB_SPRING_REPO= 'spring-tp-achat-project'
        DOCKERHUB_CREDENTIALS = credentials('docker-hub-creds')
        VERSION_NUMBER=11
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

        stage('Packaging Project') {
            steps {
                echo "Packging ..."
                sh "mvn package"
            }
        }

        stage ('Start Sonar and Nexus') {
            steps {
                echo '...Stopping Sonar and Nexus...';
                sh 'docker-compose /home/vagrant/SonarAndNexus/docker-compose.yml up -d'
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

        stage ('Stop Sonar and Nexus') {
            steps {
                echo '...Stopping Sonar and Nexus...';
                sh "docker-compose /home/vagrant/SonarAndNexus/docker-compose.yml down"
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

        stage('Build image') {
            steps {
                echo '...Building Image...';
                sh 'docker build -t ${DOCKER_HUB_SPRING_REPO}:${VERSION_NUMBER} . '
            }
        }

        stage('Push image to Docker Hub') {
            steps {
                    echo '...Pushing SpringBoot image ==> Docker Hub...';
                    sh 'docker tag ${DOCKER_HUB_SPRING_REPO}:${VERSION_NUMBER} docker.io/${DOCKER_HUB_USERNAME}/${DOCKER_HUB_SPRING_REPO}:${VERSION_NUMBER} '
                    sh 'docker login -u $DOCKER_HUB_USERNAME -p $DOCKER_HUB_PASSWORD'
                    sh 'docker push  docker.io/${DOCKER_HUB_USERNAME}/${DOCKER_HUB_SPRING_REPO}:${VERSION_NUMBER}'

            }
        }
    }

    post {
        always {
            echo 'JENKINS PIPELINE'
            sh 'docker logout'
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