import jenkins.model.Jenkins

def instance = Jenkins.getInstance()

instance.getDescriptor("jenkins.CLI").get().setEnabled(false)

instance.save()