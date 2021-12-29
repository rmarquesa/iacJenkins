import hudson.plugins.git.*
import jenkins.*
import jenkins.model.*
import hudson.model.*

def jenkins = Jenkins.instance
def job = new org.jenkinsci.plugins.workflow.job.WorkflowJob(jenkins, "Test Job")
job.save()

jenkins.reload()