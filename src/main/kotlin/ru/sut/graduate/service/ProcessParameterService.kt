package ru.sut.graduate.service

import org.springframework.stereotype.Service
import ru.sut.graduate.entity.ProcessParameter
import ru.sut.graduate.repository.ProcessParameterRepository

@Service
class ProcessParameterService : GenericService<ProcessParameter, ProcessParameterRepository>() {

    override fun validate(entity: ProcessParameter) {
        validateFieldUniqueness(
            entity,
            ProcessParameter::name,
            "Параметр заявки с таким наименованием уже существует"
        )
    }

    fun findByName(name: String): ProcessParameter {
        return repository.findByName(name)
    }

}