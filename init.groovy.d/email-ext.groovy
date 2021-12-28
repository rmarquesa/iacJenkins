import jenkins.model.Jenkins

def instance = Jenkins.getInstance()
def emailExt = instance.getDescriptor("hudson.plugins.emailext.ExtendedEmailPublisher")

emailExt.setSmtpAuth("username","password")
emailExt.setDefaultReplyTo("jenkins@example.com")
emailExt.setSmtpServer("smtp.example.com")
emailExt.setUseSsl(true)
emailExt.setSmtpPort("587")
emailExt.setCharset("utf-8")
emailExt.setDefaultRecipients("someone@example.com")
emailExt.save()