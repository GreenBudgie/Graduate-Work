package ru.sut.graduate.api.getTransition

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.sut.graduate.entity.*
import ru.sut.graduate.service.TransitionService

@RestController
@RequestMapping("api/getTransition")
class GetTransitionController(
    private val transitionService: TransitionService
) {

    @GetMapping
    fun execute(@RequestParam id: Long): GetTransitionResponse {
        val transition = transitionService.findById(id)
        return GetTransitionResponse(
            transition.id!!,
            transition.name!!,
            transition.fromStage?.id,
            transition.toStage!!.id!!,
            transition.schema?.id,
        )
    }

}