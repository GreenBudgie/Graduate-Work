package ru.sut.graduate.service

import org.springframework.stereotype.Service
import ru.sut.graduate.entity.Stage
import ru.sut.graduate.repository.StageRepository

@Service
class StageService : GenericService<Stage, StageRepository>() {

    override fun validate(entity: Stage) {
        validateFieldUniqueness(
            entity,
            Stage::name,
            "Состояние с таким наименованием уже существует"
        )
    }

}