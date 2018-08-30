
node {

    stage('Initialize'){
        def dockerHome = tool 'myDocker'
    }

    stage('Checkout') {
        checkout scm
    }

    stage("Image Prune"){
        imagePrune()
    }

    stage('Image Build'){
        imageBuild()
    }

    stage('Run App'){
        runApp()
    }
}

def imagePrune(){
    try {
        sh 'id -un'
        sh "/usr/local/bin/docker image prune -f"
        sh "/usr/local/bin/docker stop test.image"
    } catch(error){}
}

def imageBuild(){
    sh "/usr/local/bin/docker build -t test.image -f ./DockerFile ."
    echo "Image build complete"
}

def runApp() {
    sh "/usr/local/bin/docker run --it --rm -v "$PWD":/application test.image"
}