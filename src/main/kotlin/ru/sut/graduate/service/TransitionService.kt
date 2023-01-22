package ru.sut.graduate.service

import org.springframework.stereotype.Service
import ru.sut.graduate.ValidationException
import ru.sut.graduate.entity.Transition
import ru.sut.graduate.repository.TransitionRepository

@Service
class TransitionService : GenericService<Transition, TransitionRepository>() {

    override fun validate(entity: Transition) {
        validateFieldUniqueness(
            entity,
            Transition::name,
            "Переход с таким названием уже существует"
        )
        if(entity.fromStage?.id == entity.toStage?.id) {
            throw ValidationException("Начальное и конечное состояния должны различаться")
        }
        val existingTransitions = findAllButThis(entity)
        val sameStagesExist = existingTransitions.any {
            it.fromStage?.id == entity.fromStage?.id && it.toStage?.id == entity.toStage?.id
        }
        if(sameStagesExist) {
            throw ValidationException("Переход с такими состояниями уже существует")
        }
    }

}