package ru.sut.graduate.service

import org.springframework.stereotype.Service
import ru.sut.graduate.entity.Stage
import ru.sut.graduate.repository.StageRepository
import ru.sut.graduate.ui.component.ClosableNotification

@Service
class StageService : GenericService<Stage, StageRepository>() {

    override fun validate(entity: Stage): Boolean {
        if(isFieldNotUnique(entity, Stage::name)) {
            ClosableNotification.error("Состояние с таким наименованием уже существует")
            return false
        }
        return true
    }

}