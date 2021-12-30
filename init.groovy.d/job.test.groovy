/* TODO
Adicionar parametros
Adicionar autenticacao
Adicionar integração com database (classe com interface)
Adicionar validacao de dados
Adicionar looping para criação dos jobs
*/

import hudson.plugins.git.*
import jenkins.*
import jenkins.model.*
import hudson.model.*
import com.cloudbees.hudson.plugins.folder.Folder
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.jenkinsci.plugins.gwt.*

def jenkins     = Jenkins.getInstance()
def application = jenkins.createProject(Folder, "Mercurio")
def component   = application.createProject(Folder, "PrimeiroPlaneta")
def job         = component.createProject(WorkflowJob, "Version")
def job_desc    = "Job Jenkins as code do Rodrigo"
def job_url     = "https://gitlab.com/rmarquesa/planetas.git"
def job_token   = "57f1cd76740b4922b9aa46d4306cc892"
def job_trigger = "gitlab"
def scm_script  = "Jenkinsfile.Rodrigo"
def scm_branch  = ["master"]
def scm_secret  = "devops"

switch(job_trigger) {
    case "gitlab":
        t = new com.dabsquared.gitlabjenkins.GitLabPushTrigger()
        t.setBranchFilterType(com.dabsquared.gitlabjenkins.trigger.filter.BranchFilterType.NameBasedFilter)
        t.setBranchFilterName(scm_branch)        
        t.setSecretToken(job_token ?: "")
    break
}

scm = new hudson.plugins.git.GitSCM(job_url)
scm.branches = scm_branch
scm_definition = new org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition(scm,scm_script)
scm_definition.scm.userRemoteConfigs[0].credentialsId = scm_secret

job.setDescription(job_desc)
job.authToken = new BuildAuthorizationToken(job_token)
job.addTrigger(t)
job.setDefinition(scm_definition)

job.save()

teste = new com.dabsquared.git