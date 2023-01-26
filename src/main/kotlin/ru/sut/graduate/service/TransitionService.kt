package ru.sut.graduate.service

import org.springframework.stereotype.Service
import ru.sut.graduate.entity.Transition
import ru.sut.graduate.exception.EntityValidationException
import ru.sut.graduate.repository.TransitionRepository

@Service
class TransitionService : GenericService<Transition, TransitionRepository>() {

    override fun validate(entity: Transition) {
        validateFieldUniqueness(
            entity,
            Transition::name,
            "Переход с таким названием уже существует"
        )
        val existingTransitions = findAllButThis(entity)
        val sameStagesAndParametersExist = existingTransitions.any {
            hasSameStages(entity, it) && hasSameParameters(entity, it)
        }
        if(sameStagesAndParametersExist) {
            throw EntityValidationException(
                "Переход с такими состояниями и набором параметров заявки уже существует"
            )
        }
    }

    private fun hasSameStages(transition: Transition, other: Transition): Boolean {
        return transition.fromStage?.id == transition.fromStage?.id
                && transition.toStage?.id == transition.toStage?.id
    }

    private fun hasSameParameters(transition: Transition, other: Transition): Boolean {
        return transition.parameters == other.parameters
    }

}