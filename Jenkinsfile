
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

def imagePrune(containerName){
    try {
        sh "docker image prune -f"
        sh "docker stop testImage"
    } catch(error){}
}

def imageBuild(){
    sh "docker build --rm -t testImage -f ./DockerFile ."
    echo "Image build complete"
}

def runApp() {
    sh "docker run --it --rm -v "$PWD":/application packsdkandroiddocker.image"
}