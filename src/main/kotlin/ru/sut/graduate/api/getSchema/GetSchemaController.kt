package ru.sut.graduate.api.getSchema

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.sut.graduate.entity.*
import ru.sut.graduate.service.SchemaService

@RestController
@RequestMapping("api/getSchema")
class GetSchemaController(
    private val schemaService: SchemaService
) {

    @GetMapping
    fun execute(@RequestParam id: Long): GetSchemaResponse {
        val schema = schemaService.findById(id)
        return convertToResponse(schema)
    }

    private fun convertToResponse(schema: Schema): GetSchemaResponse {
        val processParameters = schema.parameters
        val processParametersData = processParameters.map { convertToProcessParameterData(it) }
        return GetSchemaResponse(
            schema.id!!,
            schema.name!!,
            processParametersData
        )
    }

    private fun convertToProcessParameterData(processParameter: ProcessParameter): ProcessParameterData {
        return ProcessParameterData(processParameter.id!!, processParameter.name!!)
    }

}