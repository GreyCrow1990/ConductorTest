
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
        sh "myDocker image prune -f"
        sh "myDocker stop test.image"
    } catch(error){}
}

def imageBuild(){
    sh "myDocker build -t test.image -f ./DockerFile ."
    echo "Image build complete"
}

def runApp() {
    sh "myDocker run --it --rm -v "$PWD":/application test.image"
}