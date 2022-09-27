pipeline {
    agent any
    tools { 
        maven 'MAVEN_3_8_6'
        jdk 'JDK_11'
    }

    environment {
        DB_DATASOURCE_URL = credentials('DB_DATASOURCE_URL_ERENTCAR')
        DB_DATASOURCE_DIALECT = credentials('DB_DATASOURCE_DIALECT_ERENTCAR')
        AUTHORIZATION_JWT_SECRET = credentials('AUTHORIZATION_JWT_SECRET_ERENTCAR')
        AUTHORIZATION_JWT_EXPIRATION_DAYS = credentials('AUTHORIZATION_JWT_EXPIRATION_DAYS_ERENTCAR')
    }
	
    stages {
        stage ('Compile Stage') {

            steps {
                withMaven(maven : 'MAVEN_3_8_6') {
                    sh 'echo "spring.datasource.url=${DB_DEV_URL}" >> src/main/resources/application.properties'
                    sh 'echo "spring.datasource.dialect=${DB_DEV_DIALECT}" >> src/main/resources/application.properties'
                    sh 'echo "authorization.jwt.secret=${AUTHORIZATION_JWT_SECRET}" >> src/main/resources/application.properties'
                    sh 'echo "authorization.jwt.expirationDays=${AUTHORIZATION_JWT_EXPIRATION_DAYS}" >> src/main/resources/application.properties'
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
