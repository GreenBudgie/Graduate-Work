package ru.sut.graduate.api.startNewProcess

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sut.graduate.entity.ProcessParameterValue
import ru.sut.graduate.service.ProcessParameterService
import ru.sut.graduate.service.TransitionService
import ru.sut.graduate.service.WorkflowService
import ru.sut.graduate.service.process.NewProcessContext
import ru.sut.graduate.service.process.ProcessService

@RestController
@RequestMapping("api/startNewProcess")
class StartNewProcessController(
    private val processService: ProcessService,
    private val workflowService: WorkflowService,
    private val transitionService: TransitionService,
    private val processParameterService: ProcessParameterService
) {

    @PostMapping
    fun execute(@RequestBody request: StartNewProcessRequest): StartNewProcessResponse {
        val context = getNewProcessContext(request)
        val process = processService.startNewProcess(context)
        return StartNewProcessResponse(
            process.id!!,
            process.name!!,
            process.workflow!!.id!!,
            process.stage!!.id!!
        )
    }

    private fun getNewProcessContext(request: StartNewProcessRequest): NewProcessContext {
        val workflow = workflowService.findById(request.workflowId)
        val initialTransition = transitionService.findById(request.initialTransitionId)
        val processParameterValues = request.processContext.map { (name, value) ->
            convertToProcessParameterValue(name, value)
        }
        return NewProcessContext(
            request.name,
            workflow,
            initialTransition,
            processParameterValues
        )
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

}