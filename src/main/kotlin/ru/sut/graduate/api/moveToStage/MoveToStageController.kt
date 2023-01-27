package ru.sut.graduate.api.moveToStage

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.sut.graduate.service.ProcessParameterService
import ru.sut.graduate.service.TransitionService
import ru.sut.graduate.service.process.ProcessService

@RestController
@RequestMapping("api/moveToStage")
class MoveToStageController(
    private val processService: ProcessService,
    private val transitionService: TransitionService,
    private val processParameterService: ProcessParameterService
) {

    @PostMapping
    fun execute(@RequestBody request: MoveToStageRequest): MoveToStageResponse {
        val process = processService.findById(request.processId)
        val transition = transitionService.findById(request.transitionId)
        val movedProcess = processService.moveToStage(process, transition, request.processContext)
        return MoveToStageResponse(
            movedProcess.id!!,
            movedProcess.name!!,
            movedProcess.workflow!!.id!!,
            movedProcess.stage!!.id!!
        )
    }

}