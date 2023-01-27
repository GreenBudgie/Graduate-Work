package ru.sut.graduate.service

import org.springframework.stereotype.Service
import ru.sut.graduate.entity.Schema
import ru.sut.graduate.repository.SchemaRepository

@Service
class SchemaService : GenericService<Schema, SchemaRepository>() {

    override fun validate(entity: Schema) {
        validateFieldUniqueness(
            entity,
            Schema::name,
            "Схема данных с таким наименованием уже существует"
        )
    }

}