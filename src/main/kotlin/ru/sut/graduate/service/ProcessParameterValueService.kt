package ru.sut.graduate.service

import org.springframework.stereotype.Service
import ru.sut.graduate.entity.ProcessParameterValue
import ru.sut.graduate.repository.ProcessParameterValueRepository

@Service
class ProcessParameterValueService : GenericService<ProcessParameterValue, ProcessParameterValueRepository>()