package ru.sut.graduate.service

import org.springframework.stereotype.Service
import ru.sut.graduate.entity.Transition
import ru.sut.graduate.repository.TransitionRepository
import ru.sut.graduate.ui.component.ClosableNotification

@Service
class TransitionService : GenericService<Transition, TransitionRepository>() {

    override fun validate(entity: Transition): Boolean {
        if(isFieldNotUnique(entity, Transition::name)) {
            ClosableNotification.error("Переход с таким названием уже существует")
            return false
        }
        if(entity.fromStage?.id == entity.toStage?.id) {
            ClosableNotification.error("Начальное и конечное состояния должны различаться")
            return false
        }
        val existingTransitions = findAll()
        val sameStagesExist = existingTransitions.any {
            it.fromStage?.id == entity.fromStage?.id && it.toStage?.id == entity.toStage?.id
        }
        if(sameStagesExist) {
            ClosableNotification.error("Переход с такими состояниями уже существует")
            return false
        }
        return true
    }

}