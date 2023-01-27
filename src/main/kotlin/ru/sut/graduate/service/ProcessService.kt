package ru.sut.graduate.service

import org.springframework.stereotype.Service
import ru.sut.graduate.entity.Process
import ru.sut.graduate.repository.ProcessRepository

@Service
class ProcessService : GenericService<Process, ProcessRepository>() {


    override fun validate(entity: Process) {

    }

}