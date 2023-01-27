package ru.sut.graduate.api.getSchema

data class GetSchemaResponse(
    val id: Long,
    val name: String,
    val requiredParameters: List<ProcessParameterData>
)

data class ProcessParameterData(
    val id: Long,
    val name: String
)