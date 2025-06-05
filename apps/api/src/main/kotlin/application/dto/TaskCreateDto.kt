package taskaround.application.dto

data class TaskCreateDto(
    val projectId: String? = null,
    val taskName: String? = null,
    val description: String? = null,
    val finishDate: String? = null,
    val accountId: Int? = null,
    val statusId: Int? = null,
)
