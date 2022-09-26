pipeline {
    agent any
    tools { 
        maven 'MAVEN_3_8_6'
        jdk 'JDK_17'
    }

    environment {
        DB_DATASOURCE_URL = credentials('DB_DATASOURCE_URL_ERENTCAR')
        DB_DATASOURCE_DIALECT = credentials('DB_DATASOURCE_DIALECT_ERENTCAR')
        AUTHORIZATION_JWT_SECRET = credentials('AUTHORIZATION_JWT_SECRET_ERENTCAR')
        AUTHORIZATION_JWT_EXPIRATION_DAYS = credentials('AUTHORIZATION_JWT_EXPIRATION_DAYS_ERENTCAR')
        //SECRET_PROPERTIES = credentials('SECRET_PROPERTIES_ERENTCAR')
        //SECRET_PROPERTIES_PATH = credentials('SECRET_PROPERTIES_PATH_ERENTCAR')
    }
	
    stages {
        stage('Setup environment') {
            steps {
                //bat 'echo ${env.DB_DATASOURCE_URL}'
                //bat 'echo spring.datasource.url=${env.DB_DATASOURCE_URL} >> src/main/resources/application.properties'
                //bat 'echo spring.datasource.dialect=${env.DB_DATASOURCE_DIALECT} >> src/main/resources/application.properties'
                //bat 'echo authorization.jwt.secret=$AUTHORIZATION_JWT_SECRET >> src/main/resources/application.properties'
                //bat 'echo authorization.jwt.expirationDays=$AUTHORIZATION_JWT_EXPIRATION_DAYS >> src/main/resources/application.properties'
                //bat 'echo %DB_DATASOURCE_URL%'
                bat 'echo spring.datasource.url=%DB_DATASOURCE_URL% >> src/main/resources/application.properties'
                bat 'echo spring.datasource.dialect=%DB_DATASOURCE_DIALECT% >> src/main/resources/application.properties'
                bat 'echo authorization.jwt.secret=%AUTHORIZATION_JWT_SECRET% >> src/main/resources/application.properties'
                bat 'echo authorization.jwt.expirationDays=%AUTHORIZATION_JWT_EXPIRATION_DAYS% >> src/main/resources/application.properties'
                //echo "secret file: ${SECRET_PROPERTIES}"
                //bat 'powershell Copy-Item ${SECRET_PROPERTIES_PATH} -Destination src/main/resources'
            }
        }

        stage ('Compile Stage') {

            steps {
                withMaven(maven : 'MAVEN_3_8_6') {
                    bat 'mvn clean compile'
                }
            }
        }

        stage ('Testing Stage') {

            steps {
                withMaven(maven : 'MAVEN_3_8_6') {
                    bat 'mvn test'
                }
            }
        }

        stage ('package Stage') {
            steps {
                withMaven(maven : 'MAVEN_3_8_6') {
                    bat 'mvn package'
                }
            }
        }
    }
}