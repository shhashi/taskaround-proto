package taskaround.application.dto

data class TaskUpdateDto(
    val taskId: String? = null,
    val taskName: String? = null,
    val description: String? = null,
    val finishDate: String? = null,
    val accountId: Int? = null,
    val statusId: Int? = null,
)
