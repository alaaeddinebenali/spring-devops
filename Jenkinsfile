pipeline {


 agent any


        environment {

                    NEXUS_USERNAME = 'admin'
                    NEXUS_PASSWORD ='Adnen**91'
                    DOCKER_HUB_USERNAME= 'adnendeveloper'
                    DOCKER_HUB_PASSWORD= 'dckr_pat_4GalsJoNAgjYHRb61dRJknhjUHc'
                    DOCKER_HUB_ANGULAR_REPO= 'angular-esprit-repo'
                    DOCKER_HUB_SPRING_REPO= 'spring-boot-esprit-repo'
                    MYSQL_ROOT_PASSWORD = 'root'
                    MYSQL_PASSWORD = 'root'
                    MYSQL_DATABASE = 'tpachato'


        }

        stages {

                    stage ('GIT Checkout for Angular App') {
                steps {
                    echo '...Pulling angular app';
                    git branch: 'main',
                    url : 'https://github.com/Adnen-Developer/esprit-devops-angular'
                   // credentialsId:  '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';
                }
            }

        stage("Launch SonarQube and Nexus containers") {
            steps {
                sh ' sudo docker compose -f  /home/vagrant/SonarAndNexus/docker-compose.yml start'
                sh 'echo "date and time BEFORE  sleep() for two minutes" '
                sh 'date +"%m_%d_%Y_%M:%S"'
                sleep(time: 2, unit: "MINUTES")

            }
        }



               stage('SonarQube check for Angular') {
                    steps {
                            sh 'echo "date and time AFTER  sleep() for two minutes" '
                            sh 'date +"%m_%d_%Y_%M:%S"'
                            sh 'npm install sonar-scanner --save-dev'
                            sh 'npm run sonar'
                    }
                }



                stage('Build Angular image') {
                    steps {
                      //  sh 'chmod 666 /var/run/docker.sock'
                        sh 'declare BUILD_TAG=$(date +%s-%A-%B)'
                        sh 'docker build -t esprit-devops-angular:${BUILD_TAG} .'
                    }
                }

                stage('Push Angular image to Nexus') {
                steps {

                        sh 'docker tag  esprit-devops-angular:${BUILD_TAG} 72.10.0.140:8082/docker-devops-repo/esprit-devops-angular:${BUILD_TAG}'
                        sh 'docker login -u $NEXUS_USERNAME -p $NEXUS_PASSWORD 72.10.0.140:8082'
                        sh 'docker push 72.10.0.140:8082/docker-devops-repo/esprit-devops-angular:${BUILD_TAG}'
                }
            }


            stage('Push Angular image to Docker Hub') {
                steps {

                        sh 'docker tag esprit-devops-angular:${BUILD_TAG} docker.io/${DOCKER_HUB_USERNAME}/${DOCKER_HUB_ANGULAR_REPO}:${BUILD_TAG} '
                        sh 'docker login -u $DOCKER_HUB_USERNAME -p $DOCKER_HUB_PASSWORD'
                        sh 'docker push  docker.io/${DOCKER_HUB_USERNAME}/${DOCKER_HUB_ANGULAR_REPO}:${BUILD_TAG}'

                }
            }





            stage ('GIT Checkout for Spring Boot App') {
                    steps {
                        echo '...Pulling...';
                        git branch: 'adnen',
                        url : 'https://github.com/alaaeddinebenali/spring-devops'
                       // credentialsId:  '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';
                    }
            }


            stage ('Build') {
                    steps {
                       sh "mvn -B -DskipTests clean package"
                    }
            }

            stage ('Test') {
                    steps {
                        sh "mvn test"
                    }
            }

            stage ('Package') {
                    steps {
                        sh "mvn package"
                    }
            }




            stage("build & SonarQube analysis") {
                    steps {
                        sh  "mvn sonar:sonar -Dsonar.projectKey=tpAchatProject -Dsonar.host.url=http://72.10.0.140:9000 -Dsonar.login=8141a4e2160b1ac55882ad543046c4eb0ff436ce"
                    }
            }



            stage('Build Spring Boot image') {

                    steps {
          //          sh 'declare BUILD_TAG=$(date +%s-%A-%B)'
                    sh 'docker build -t tp-achat-project:${BUILD_TAG} .'

                    }
            }


            stage('Push Spring Boot image to Nexus') {
                steps {
                        sh 'docker tag tp-achat-project:${BUILD_TAG} 72.10.0.140:8082/docker-devops-repo/tp-achat-project:${BUILD_TAG}'
                        sh 'docker login -u $NEXUS_USERNAME -p $NEXUS_PASSWORD 72.10.0.140:8082'
                        sh 'docker push 72.10.0.140:8082/docker-devops-repo/tp-achat-project:${BUILD_TAG}'
                }
            }


            stage('Push Spring Boot image to Docker Hub') {
                steps {
                        sh 'docker tag tp-achat-project:${BUILD_TAG} docker.io/${DOCKER_HUB_USERNAME}/${DOCKER_HUB_SPRING_REPO}:${BUILD_TAG} '
                        sh 'docker login -u $DOCKER_HUB_USERNAME -p $DOCKER_HUB_PASSWORD'
                        sh 'docker push  docker.io/${DOCKER_HUB_USERNAME}/${DOCKER_HUB_SPRING_REPO}:${BUILD_TAG}'

                }
            }

           stage("Stop SonarQube and Nexus containers") {
            steps {
                sh ' sudo docker compose -f  /home/vagrant/SonarAndNexus/docker-compose.yml stop'
                 }
            }

            stage('create Spring boot, ANgular and Mysql App') {
              steps {

                  sh 'sudo  DOCKER_HUB_USERNAME=${DOCKER_HUB_USERNAME}  DOCKER_HUB_ANGULAR_REPO=${DOCKER_HUB_ANGULAR_REPO}  DOCKER_HUB_SPRING_REPO=${DOCKER_HUB_SPRING_REPO}   MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD}     MYSQL_DATABASE=${MYSQL_DATABASE} BUILD_TAG=${BUILD_TAG} docker compose -f /home/vagrant/dockerComposeDeployment/Docker-Compose-Deployment.yml up -d '
                sh 'unset BUILD_TAG'

              }
            }


          stage('LOGOUT'){
                steps {
                  sh 'docker logout docker.io'
                  sh "docker logout ${DOCKER_HUB_USERNAME}"
                  sh 'docker logout 72.10.0.140:8082'
                }
            }


    }
}

