//REFERENCIA: https://github.com/thbkrkr/jks/blob/master/init.groovy.d/11-configure-pipeline-global-shared.groovy

import org.jenkinsci.plugins.workflow.libs.SCMSourceRetriever;
import org.jenkinsci.plugins.workflow.libs.LibraryConfiguration;
import jenkins.plugins.git.GitSCMSource;
import jenkins.model.Jenkins
import groovy.json.JsonSlurper

def globalLibsDesc = Jenkins.instance.getDescriptor('org.jenkinsci.plugins.workflow.libs.GlobalLibraries')

def librariesPath = './config'

def librariesJson = new JsonSlurper().parse(new File('libraries.json'))

def libs = []

librariesJson?.repositories?.each { libraryRepo -> 

    def libraryRepoPath = libraryRepo.key
    def libraryRepoName = libraryRepoPath.drop(++libraryRepoPath.lastIndexOf('/'))
    def libraryName = libraryRepo.value.name
    def libraryImplicitLoad = libraryRepo.value.implicit ?: false
    def libraryRepoRevision = libraryRepo.value.revision

    println "Setting library ${libraryName} for ${libraryRepoPath}..."

    if(!libraryRepoRevision) {
        throw new IllegalStateException("Revision not found for repository ${libraryRepoPath}")
    }

    // SCMSourceRetriever
    def retriever = new SCMSourceRetriever(new GitSCMSource(
        libraryRepoName,
        System.getenv('GITHUB_ENDPOINT') + libraryRepoPath,
        'gitlab',
        '*',
        '',
        false))

    // LibraryConfiguration
    def lib = new LibraryConfiguration(libraryName, retriever)
    lib.with {
        defaultVersion = libraryRepoRevision
        implicit = libraryImplicitLoad
    }

    libs << lib
}

globalLibsDesc.setLibraries(libs)
globalLibsDesc.save()