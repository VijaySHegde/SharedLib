
def call(userid){
sh "curl -X PUT http://18.221.205.57:8181/v1/data/myapi/acl --data-binary @open-policy-agent/JENKINS/BuildPolicy/jenkins-acl.json"
sh "curl -X PUT http://18.221.205.57:8181/v1/policies/myapi --data-binary @open-policy-agent/JENKINS/BuildPolicy/jenkins-policy.rego"
sh "echo '${userid}'"
//sh """curl --location --request POST 'http://18.221.205.57:8181/v1/data/myapi/policy/allow' --header 'Content-Type: application/json' --data-raw '{ "input": { "user": "'${userid}'", "access": "build" } }'"""
String response = sh(script:"""curl --location --request POST 'http://18.221.205.57:8181/v1/data/myapi/policy/allow' --header 'Content-Type: application/json' --data-raw '{ "input": { "user": "'${userid}'", "access": "build" } }'""", returnStdout: true)
println(response)
if ( response == "{\"result\":true}" ){
println("You can build a job")

}
else{
error("Build failed beacuse you do not have permission to trigger a build")
}
}
}