package taskaround.presentation.routing.resource

import io.ktor.resources.*

@Resource("/projects")
class Projects {
    @Resource("{id}")
    class Id(val parent: Projects = Projects(), val id: String) {
        @Resource("members")
        class Members(val parent: Id)

        @Resource("add-task")
        class AddTask(val parent: Id)
    }
}
