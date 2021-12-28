import jenkins.*
import jenkins.model.*
import hudson.*
import hudson.model.*

jenkins = Jenkins.getInstance()
gnp = jenkins.getGlobalNodeProperties()
envVarsNodePropertyList = gnp .getAll( hudson.slaves.EnvironmentVariablesNodeProperty.class)
newEnvVarsNodeProperty = null
envVars = null

if ( envVarsNodePropertyList == null || envVarsNodePropertyList.size() == 0 ) {
  newEnvVarsNodeProperty = new hudson.slaves.EnvironmentVariablesNodeProperty();
  gnp.add(newEnvVarsNodeProperty)
  envVars = newEnvVarsNodeProperty.getEnvVars()
} else {
  envVars = envVarsNodePropertyList.get(0).getEnvVars()
}

def envVarsToPropagate = [
  "fixedValue": [
    "GITLAB_ENDPOINT": "https://gitlab.com/",
    "GITHUB_ENDPOINT": "https://github.com/",
  ]
]

envVarsToPropagate.fixedValue.each {
  println("Setting fixed value env var: " + it.key)
  envVars.put(it.key, it.value)
}

jenkins.save()