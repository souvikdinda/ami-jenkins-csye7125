pipelineJob('webapp-build') {
    displayName('WebApp Build')
    description('Creates docker image with release on webapp repository')
    logRotator {
        daysToKeep(30)
        numToKeep(10)
    }
    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url('https://github.com/csye7125-fall2023-group07/webapp.git')
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

pipelineJob('webapp-db-build') {
    displayName('WebApp DB Build')
    description('Creates docker image with release on webapp-db repository')
    logRotator {
        daysToKeep(30)
        numToKeep(10)
    }
    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url('https://github.com/csye7125-fall2023-group07/webapp-db.git')
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

pipelineJob('producer-app-build') {
    displayName('Producer App Build')
    description('Creates docker image with release on producer-app repository')
    logRotator {
        daysToKeep(30)
        numToKeep(10)
    }
    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url('https://github.com/csye7125-fall2023-group07/producer-app.git')
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

pipelineJob('consumer-app-build') {
    displayName('Consumer App Build')
    description('Creates docker image with release on consumer-app repository')
    logRotator {
        daysToKeep(30)
        numToKeep(10)
    }
    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url('https://github.com/csye7125-fall2023-group07/consumer-app.git')
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

pipelineJob('infra-helm-chart-build') {
    displayName('infra-helm-chart-build')
    description('Pipeline to build helm chart for infra - kafka and consumer app')
    logRotator {
        daysToKeep(30)
        numToKeep(10)
    }
    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url('https://github.com/csye7125-fall2023-group07/infra-helm-chart.git')
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

// Multibranch Pipelines

// multibranchPipelineJob('webapp-build') {
//     displayName('webapp-build')
//     description('Creates docker image with release on webapp repository')
//     branchSources {
//         github {
//             id('webapp-build')
//             apiUri('https://api.github.com')
//             repoOwner('csye7125-fall2023-group07')
//             repository('webapp')
//             scanCredentialsId('github_token')
//             includes('*')
//         }
//     }

//     configure {
//         def traits = it / 'sources' / 'data' / 'jenkins.branch.BranchSource' / 'source' / 'traits'
//         traits << 'org.jenkinsci.plugins.github__branch__source.TagDiscoveryTrait' {}
//     }

//     configure { node ->
//      def webhookTrigger = node / triggers / 'com.igalg.jenkins.plugins.mswt.trigger.ComputedFolderWebHookTrigger' {
//                 spec('')
//                 token("WebappJenkinsBuild")
//             }
//     }
// }

// multibranchPipelineJob('webapp-db-build') {
//     displayName('webapp-db-build')
//     description('Creates docker image with release on webapp-db repository')
//     branchSources {
//         github {
//             id('webapp-db-build')
//             apiUri('https://api.github.com')
//             repoOwner('csye7125-fall2023-group07')
//             repository('webapp-db')
//             scanCredentialsId('github_token')
//             includes('*')
//         }
//     }

//     configure {
//         def traits = it / 'sources' / 'data' / 'jenkins.branch.BranchSource' / 'source' / 'traits'
//         traits << 'org.jenkinsci.plugins.github__branch__source.TagDiscoveryTrait' {}
//     }
//     configure { node ->
//      def webhookTrigger = node / triggers / 'com.igalg.jenkins.plugins.mswt.trigger.ComputedFolderWebHookTrigger' {
//                 spec('')
//                 token("MyWebappDBToken")
//             }
//     }
// }

// multibranchPipelineJob('producer-app-build') {
//     displayName('producer-app-build')
//     description('Creates docker image with release on producer-app repository')
//     branchSources {
//         github {
//             id('producer-app-build')
//             apiUri('https://api.github.com')
//             repoOwner('csye7125-fall2023-group07')
//             repository('producer-app')
//             scanCredentialsId('github_token')
//             includes('*')
//         }
//     }

//     configure {
//         def traits = it / 'sources' / 'data' / 'jenkins.branch.BranchSource' / 'source' / 'traits'
//         traits << 'org.jenkinsci.plugins.github__branch__source.TagDiscoveryTrait' {}
//     }
//     configure { node ->
//      def webhookTrigger = node / triggers / 'com.igalg.jenkins.plugins.mswt.trigger.ComputedFolderWebHookTrigger' {
//                 spec('')
//                 token("ProducerAppBuild")
//             }
//     }
// }

// multibranchPipelineJob('consumer-app-build') {
//     displayName('consumer-app-build')
//     description('Creates docker image with release on consumer-app repository')
//     branchSources {
//         github {
//             id('consumer-app-build')
//             apiUri('https://api.github.com')
//             repoOwner('csye7125-fall2023-group07')
//             repository('consumer-app')
//             scanCredentialsId('github_token')
//             includes('*')
//         }
//     }

//     configure {
//         def traits = it / 'sources' / 'data' / 'jenkins.branch.BranchSource' / 'source' / 'traits'
//         traits << 'org.jenkinsci.plugins.github__branch__source.TagDiscoveryTrait' {}
//     }
//     configure { node ->
//      def webhookTrigger = node / triggers / 'com.igalg.jenkins.plugins.mswt.trigger.ComputedFolderWebHookTrigger' {
//                 spec('')
//                 token("ConsumerAppBuild")
//             }
//     }
// }