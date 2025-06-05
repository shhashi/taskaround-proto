package taskaround.application.dto

data class ProjectCreateDto(
    val projectId: String,
    val projectName: String,
    val createdByAccountId: Int,
)
