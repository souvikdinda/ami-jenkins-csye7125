multibranchPipelineJob('my-github-multibranch-pipeline') {
    branchSources {
        git {
            id('github-token')
            remote('https://github.com/csye7125-fall2023-group07/webapp')
            includes('*')
        }
        // git {
        //     id('github-webapp-db-token')
        //     remote('https://github.com/csye7125-fall2023-group07/webapp-db')
        //     includes('*')
        // }
    }
    orphanedItemStrategy {
        discardOldItems {
            numToKeep(10)
            daysToKeep(7)
        }
    }
}