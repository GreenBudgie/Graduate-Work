package ru.sut.graduate.service.process

import org.springframework.stereotype.Service
import ru.sut.graduate.entity.Process
import ru.sut.graduate.entity.ProcessParameterValue
import ru.sut.graduate.entity.Schema
import ru.sut.graduate.entity.Transition
import ru.sut.graduate.exception.SchemaValidationException
import ru.sut.graduate.exception.ServiceException
import ru.sut.graduate.repository.ProcessRepository
import ru.sut.graduate.service.GenericService
import ru.sut.graduate.service.ProcessParameterService
import ru.sut.graduate.service.ProcessParameterValueService

@Service
class ProcessService(
    private val processParameterValueService: ProcessParameterValueService,
    private val processParameterService: ProcessParameterService
) : GenericService<Process, ProcessRepository>() {

    fun startNewProcess(newProcessContext: NewProcessContext): Process {
        val process = Process(
            name = newProcessContext.name,
            workflow = newProcessContext.workflow
        )
        return moveToStage(
            process,
            newProcessContext.initialTransition,
            newProcessContext.processContext
        )
    }

    fun moveToStage(process: Process,
                    transition: Transition,
                    processContext: Map<String, String>): Process {
        if(process.stage != transition.fromStage) {
            throw ServiceException(
                "Текущее состояние заявки \"${process.stage?.name}\" " +
                        "отличается от начального состояния перехода \"${transition.fromStage?.name}\""
            )
        }

        val workflow = process.workflow!!
        if(!process.workflow!!.transitions.contains(transition)) {
            throw ServiceException(
                "Маршрут \"${workflow.name}\" не содержит перехода \"${transition.name}\""
            )
        }

        val parameterValues = getParameterValuesFromProcessContext(transition.schema, processContext)

        process.stage = transition.toStage
        val savedProcess = save(process)
        parameterValues.forEach {
            it.process = savedProcess
            processParameterValueService.save(it)
        }

        return findById(process.id!!)
    }

    private fun getParameterValuesFromProcessContext(
        schema: Schema?,
        processContext: Map<String, String>
    ): List<ProcessParameterValue> {
        validateSchema(schema, processContext)
        return processContext.map { (name, value) ->
            convertToProcessParameterValue(name, value)
        }
    }

    private fun convertToProcessParameterValue(
        parameterName: String,
        parameterValue: String
    ): ProcessParameterValue {
        val processParameter = processParameterService.findByName(parameterName)
        return ProcessParameterValue(
            processParameter = processParameter,
            value = parameterValue
        )
    }

    private fun validateSchema(schema: Schema?, processContext: Map<String, String>) {
        val requiredParameters = schema?.parameters
            ?.map { it.name!! }
            ?.sorted() ?: emptyList()
        val providedParameters = processContext
            .map { it.key }
            .sorted()
        if(requiredParameters != providedParameters) {
            throw SchemaValidationException(requiredParameters.toSet(), providedParameters.toSet())
        }
    }

}