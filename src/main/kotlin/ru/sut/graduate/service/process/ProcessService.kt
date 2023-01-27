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
import ru.sut.graduate.service.ProcessParameterValueService

@Service
class ProcessService(
    private val processParameterValueService: ProcessParameterValueService
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
                    processContext: List<ProcessParameterValue>): Process {
        if(process.stage != transition.fromStage) {
            throw ServiceException(
                "Текущее состояние заявки отличается от начального состояния перехода"
            )
        }

        validateSchema(transition.schema, processContext)

        process.stage = transition.toStage
        val savedProcess = save(process)
        processContext.forEach {
            it.process = savedProcess
            processParameterValueService.save(it)
        }

        return findById(process.id!!)
    }

    private fun validateSchema(schema: Schema?, processContext: List<ProcessParameterValue>) {
        val requiredParameters = schema?.parameters
            ?.map { it.name!! }
            ?.sorted() ?: emptyList()
        val providedParameters = processContext
            .map { it.processParameter!!.name!! }
            .sorted()
        if(requiredParameters != providedParameters) {
            throw SchemaValidationException(requiredParameters.toSet(), providedParameters.toSet())
        }
    }

}