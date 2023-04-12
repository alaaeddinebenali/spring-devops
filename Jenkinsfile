pipeline {


 agent any


        environment {

                    NEXUS_USERNAME = 'admin'
                    NEXUS_PASSWORD ='marwenjeridi'
                    DOCKER_HUB_USERNAME= 'jeridipixel'
                    DOCKER_HUB_PASSWORD= 'dckr_pat_iN3M9CP3V2_jz32XjOq3uCfWHHs'
                    DOCKER_HUB_SPRING_REPO= 'marwen-jeridi-repo-docker'
                    MYSQL_ROOT_PASSWORD = 'root'
                    MYSQL_PASSWORD = 'toor'
                    MYSQL_DATABASE = 'tpachato'


        }

        stages {



        stage("Launch SonarQube and Nexus containers") {
            steps {
                sh ' sudo docker compose -f  /home/vagrant/SonarAndNexus/docker-compose.yml start'
                sh 'echo "date and time BEFORE  sleep() for two minutes" '
                sh 'date +"%m_%d_%Y_%M:%S"'
                sleep(time: 2, unit: "MINUTES")

            }
        }






            stage ('GIT Checkout for Spring Boot App') {
                    steps {
                        echo '...Pulling...';
                        git branch: 'marwenspring',
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
                        sh  "mvn sonar:sonar -Dsonar.projectKey=tpAchatProject -Dsonar.host.url=http://72.100.0.140:9000 -Dsonar.login=8141a4e2160b1ac55882ad543046c4eb0ff436ce"
                    }
            }



            stage('Build Spring Boot image') {

                    steps {
                    sh 'declare MY_DOCKER_BUILD_VERSION=$(date +%s-%A-%B)'
                    sh 'docker build -t tp-achat-project:${MY_DOCKER_BUILD_VERSION} .'

                    }
            }


            stage('Push Spring Boot image to Nexus') {
                steps {
                        sh 'docker tag tp-achat-project:${MY_DOCKER_BUILD_VERSION} 72.100.0.140:8082/docker-devops-repo/tp-achat-project:${MY_DOCKER_BUILD_VERSION}'
                        sh 'docker login -u $NEXUS_USERNAME -p $NEXUS_PASSWORD 72.100.0.140:8082'
                        sh 'docker push 72.100.0.140:8082/docker-devops-repo/tp-achat-project:${MY_DOCKER_BUILD_VERSION}'
                }
            }


            stage('Push Spring Boot image to Docker Hub') {
                steps {
                        sh 'docker tag tp-achat-project:${MY_DOCKER_BUILD_VERSION} docker.io/${DOCKER_HUB_USERNAME}/${DOCKER_HUB_SPRING_REPO}:${MY_DOCKER_BUILD_VERSION} '
                        sh 'docker login -u $DOCKER_HUB_USERNAME -p $DOCKER_HUB_PASSWORD'
                        sh 'docker push  docker.io/${DOCKER_HUB_USERNAME}/${DOCKER_HUB_SPRING_REPO}:${MY_DOCKER_BUILD_VERSION}'

                }
            }

           stage("Stop SonarQube and Nexus containers") {
            steps {
                sh ' sudo docker compose -f  /home/vagrant/SonarAndNexus/docker-compose.yml stop'
                 }
            }

            stage('create Spring boot, ANgular and Mysql App') {
              steps {

                  sh 'sudo  DOCKER_HUB_USERNAME=${DOCKER_HUB_USERNAME}  DOCKER_HUB_ANGULAR_REPO=${DOCKER_HUB_ANGULAR_REPO}  DOCKER_HUB_SPRING_REPO=${DOCKER_HUB_SPRING_REPO}   MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD}     MYSQL_DATABASE=${MYSQL_DATABASE} MY_DOCKER_BUILD_VERSION=${MY_DOCKER_BUILD_VERSION} docker compose -f /home/vagrant/Docker-Compose-springmysql.yml up -d '
                sh 'unset MY_DOCKER_BUILD_VERSION'

              }
            }


          stage('LOGOUT'){
                steps {
                  sh 'docker logout docker.io'
                  sh "docker logout ${DOCKER_HUB_USERNAME}"
                  sh 'docker logout 72.100.0.140:8082'
                }
            }


    }
}

