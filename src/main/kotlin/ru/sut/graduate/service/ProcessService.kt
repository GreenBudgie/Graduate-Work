package ru.sut.graduate.service

import org.springframework.stereotype.Service
import ru.sut.graduate.repository.ProcessRepository

@Service
class ProcessService(
    private val processRepository: ProcessRepository
) {



}