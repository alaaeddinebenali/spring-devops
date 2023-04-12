pipeline {


 agent any


        environment {

                    NEXUS_USERNAME = 'admin'
                    NEXUS_PASSWORD ='marwenjeridi'
                    DOCKER_HUB_USERNAME= 'jeridipixel'
                    DOCKER_HUB_PASSWORD= 'dckr_pat_iN3M9CP3V2_jz32XjOq3uCfWHHs'
                    DOCKER_HUB_SPRING_REPO= 'marwen-jeridi-repo-docker'
                    MYSQL_ROOT_PASSWORD = 'root'
                    MYSQL_PASSWORD = 'root'
                    MYSQL_DATABASE = 'tpachato'
                    MY_DOCKER_BUILD_VERSION=1234


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
                       sh "sudo mvn -B -DskipTests clean package"
                    }
            }

            stage ('Test') {
                    steps {
                        sh "sudo mvn test"
                    }
            }

            stage ('Package') {
                    steps {
                        sh "sudo mvn package"
                    }
            }




            stage("build & SonarQube analysis") {
                    steps {
                        sh  "sudo mvn sonar:sonar -Dsonar.projectKey=springProjectMarwen  -Dsonar.host.url=http://72.100.0.140:9000  -Dsonar.login=335e8b5a68e2bdc341a2792b4a66d454ef9e164e"
                   

                    }
            }



            stage('Build Spring Boot image') {

                    steps {
                    sh 'sudo docker build -t tp-achat-project:${MY_DOCKER_BUILD_VERSION} .'

                    }
            }


            stage('Push Spring Boot image to Nexus') {
                steps {
                        sh 'sudo docker tag tp-achat-project:${MY_DOCKER_BUILD_VERSION} 72.100.0.140:8082/marwen-jeridi-nexus/tp-achat-project:${MY_DOCKER_BUILD_VERSION}'
                        sh 'sudo docker login -u $NEXUS_USERNAME -p $NEXUS_PASSWORD 72.100.0.140:8082'
                        sh 'sudo docker push 72.100.0.140:8082/marwen-jeridi-nexus/tp-achat-project:${MY_DOCKER_BUILD_VERSION}'
                }
            }


            stage('Push Spring Boot image to Docker Hub') {
                steps {
                        sh 'sudo docker tag tp-achat-project:${MY_DOCKER_BUILD_VERSION} docker.io/${DOCKER_HUB_USERNAME}/${DOCKER_HUB_SPRING_REPO}:${MY_DOCKER_BUILD_VERSION} '
                        sh 'sudo docker login -u $DOCKER_HUB_USERNAME -p $DOCKER_HUB_PASSWORD'
                        sh 'sudo docker push  docker.io/${DOCKER_HUB_USERNAME}/${DOCKER_HUB_SPRING_REPO}:${MY_DOCKER_BUILD_VERSION}'

                }
            }

           stage("Stop SonarQube and Nexus containers") {
            steps {
                sh ' sudo docker compose -f  /home/vagrant/SonarAndNexus/docker-compose.yml stop'
                 }
            }

            stage('create Spring boot and Mysql App') {
              steps {

                  sh 'sudo  DOCKER_HUB_USERNAME=${DOCKER_HUB_USERNAME}    DOCKER_HUB_SPRING_REPO=${DOCKER_HUB_SPRING_REPO}   MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD}     MYSQL_DATABASE=${MYSQL_DATABASE} MY_DOCKER_BUILD_VERSION=${MY_DOCKER_BUILD_VERSION} docker compose -f /home/vagrant/Docker-Compose-springmysql.yml up -d '

              }
            }


          stage('LOGOUT'){
                steps {
                  sh 'sudo docker logout docker.io'
                  sh "sudo docker logout ${DOCKER_HUB_USERNAME}"
                  sh 'sudo docker logout 72.100.0.140:8082'
                }
            }


    }
}

