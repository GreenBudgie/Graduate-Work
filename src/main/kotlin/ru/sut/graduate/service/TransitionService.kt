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
        if(entity.toStage == null) {
            throw EntityValidationException("Укажите конечное состояние")
        }
    }

}