/** This pipeline will build the application and push the completed artifact
  * To the registry.  To add another app simply define the app name and add 
  * a path to the build pipeline.  The spring-boot pipeline should be 
  * sufficient to use the TBS for most cases Jenkins will poll this
  * repo for changes every minute
  */

def apps = [
    'spring-petclinic-vets-service': [
        buildPipeline: 'ci/jenkins/pipelines/spring-boot-app.pipeline'
    ],
    'spring-petclinic-api-gateway': [
        buildPipeline: 'ci/jenkins/pipelines/spring-boot-app.pipeline'
    ],
    'spring-petclinic-visits-service': [
        buildPipeline: 'ci/jenkins/pipelines/spring-boot-app.pipeline'
    ],
    'spring-petclinic-httpbin': [
        buildPipeline: 'ci/jenkins/pipelines/spring-boot-app.pipeline'
    ],
    'spring-petclinic-config-server': [
        buildPipeline: 'ci/jenkins/pipelines/spring-boot-app.pipeline'
    ]
]


apps.each { name, appInfo ->


    pipelineJob(name) {
       description("Job to build '$name'. Generated by the Seed Job, please do not change !!!")
       environmentVariables(
            APP_NAME: name
       )
       definition {
            cps {
                script(readFileFromWorkspace(appInfo.buildPipeline))
                sandbox()
            }
        }        
        triggers {
          scm('* * * * *')    
        }
        properties{
            disableConcurrentBuilds()
        }
    }
}
       