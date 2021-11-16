rootProject.name = "pos"


include("openapi-spring")

for (project in rootProject.children) {
    if (project.name.startsWith("openapi-")) {
        project.projectDir = file("gradle/" + project.name.split("-").joinToString("/"))
    }
}