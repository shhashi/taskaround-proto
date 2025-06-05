package taskaround.application.dto

data class ProjectAddMemberDto(
    val projectId: String,
    val accountId: Int,
    val roleId: String? = null,
)
