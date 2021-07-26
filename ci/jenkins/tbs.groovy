/* 
Generate TBS images for every commit identified in the gitops repo for the branch identified below.
*/
def env = [
    'stage': [
        buildPipeline: 'ci/jenkins/pipelines/tbs-update-env.pipeline'
    ]
]

env.each { name, appInfo ->

    folder(name)
    pipelineJob(name+"/tbs-update") {
       description("Job to build '$name'. Generated by the Seed Job, please do not change !!!")
       environmentVariables(
            ENV_NAME:  name
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
       