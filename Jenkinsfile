
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
        sh "/usr/local/bin/docker image prune -f"
        sh "/usr/local/bin/docker stop testImage"
    } catch(error){}
}

def imageBuild(){
    sh "/usr/local/bin/docker build --rm -t testImage -f ./DockerFile ."
    echo "Image build complete"
}

def runApp() {
    sh "/usr/local/bin/docker run --it --rm -v "$PWD":/application packsdkandroiddocker.image"
}