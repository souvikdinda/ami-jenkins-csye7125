import jenkins.model.Jenkins
import jenkins.branch.MultiBranchProject
import jenkins.branch.MultiBranchProjectDescriptor
import jenkins.plugins.git.GitSCMSource
import jenkins.scm.api.SCMSource

def jenkins = Jenkins.getInstance()

def createMultibranchPipeline = { projectName, repoUrl, credentialsId, scanInterval, triggerToken ->
    def project = jenkins.getItemByFullName(projectName, MultiBranchProject.class)
    if (project == null) {
        project = jenkins.createProject(MultiBranchProjectDescriptor.class, projectName)
    }

    def source = new GitSCMSource("1", repoUrl, credentialsId, "*", "", null, false)
    source.setTraits(SCMSource.SourceBySCM.SourceBySCMBuilder.prototype().build())
    
    project.getProjectFactory().setSourcesList([source])
    project.getSourcesList().replaceBy([source])

    project.setCategories([""])
    project.setBuildStrategies([""])
    project.getSourcesList().forEach {
        it.setOwner(project)
    }

    project.save()
    project.scheduleBuild2(scanInterval.toMillis())

    // Add the GitHub Push trigger
    def triggers = project.getProperties().get(hudson.model.JobProperty.all()).find { it instanceof org.jenkinsci.plugins.workflow.multibranch.SCMBinder.Binding }.getSCMBinder()
    def branchSource = triggers.branchSources.get(0)
    branchSource.buildStrategies = [new org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject.DescriptorImpl.WorkflowMultiBranchProjectFactoryDescriptor().newInstance(project, "Jenkinsfile")]
    def triggersExtension = triggers.getDescriptor().newInstance()
    triggersExtension.token = triggerToken
    triggersExtension.spec = triggerToken
    triggers.trigger("githubPush", triggersExtension)
}

createMultibranchPipeline(
    "webapp-build",
    "https://github.com/csye7125-fall2023-group07/webapp.git",
    "github_token",
    1,
    "WebappJenkinsBuild"
)
