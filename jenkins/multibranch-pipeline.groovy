multibranchPipelineJob('webapp-build') {
    displayName('webapp-build')
    description('Creates docker image with release on webapp repository')
    branchSources {
        github {
            id('webapp-build')
            apiUri('https://api.github.com')
            repoOwner('csye7125-fall2023-group07')
            repository('webapp')
            scanCredentialsId('github_token')
            includes('*')
        }
    }

    configure {
        def traits = it / 'sources' / 'data' / 'jenkins.branch.BranchSource' / 'source' / 'traits'
        traits << 'org.jenkinsci.plugins.github__branch__source.TagDiscoveryTrait' {}
    }

    configure { node ->
     def webhookTrigger = node / triggers / 'com.igalg.jenkins.plugins.mswt.trigger.ComputedFolderWebHookTrigger' {
                spec('')
                token("WebappJenkinsBuild")
            }
    }
}

multibranchPipelineJob('webapp-db-build') {
    displayName('webapp-db-build')
    description('Creates docker image with release on webapp-db repository')
    branchSources {
        github {
            id('webapp-db-build')
            apiUri('https://api.github.com')
            repoOwner('csye7125-fall2023-group07')
            repository('webapp-db')
            scanCredentialsId('github_token')
            includes('*')
        }
    }

    configure {
        def traits = it / 'sources' / 'data' / 'jenkins.branch.BranchSource' / 'source' / 'traits'
        traits << 'org.jenkinsci.plugins.github__branch__source.TagDiscoveryTrait' {}
    }
    configure { node ->
     def webhookTrigger = node / triggers / 'com.igalg.jenkins.plugins.mswt.trigger.ComputedFolderWebHookTrigger' {
                spec('')
                token("WebappDBJenkinsBuild")
            }
    }
}

pipelineJob('webapp-helm-chart-build') {
    displayName('webapp-helm-chart-build')
    description('Pipeline to build helm chart for webapp')
    logRotator {
        daysToKeep(30)
        numToKeep(10)
    }
    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url('https://github.com/csye7125-fall2023-group07/webapp-helm-chart.git')
                        credentials('github_token')
                    }
                    branch('*/main')
                }
                scriptPath('Jenkinsfile')
            }
        }
    }
    triggers{
        githubPush()
    }
}