package taskaround.presentation.routing.resource

import io.ktor.resources.*

@Resource("/tasks")
class Task {
    @Resource("{taskId}")
    class TaskId(val parent: Task = Task(), val taskId: String) {
        @Resource("person-hours")
        class PersonHours(val parent: TaskId)
    }
}
