package com.dzenlab.adviceandinsult.usecase.advice

import com.dzenlab.adviceandinsult.models.*
import com.dzenlab.adviceandinsult.repository.AdviceRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

//import java.util.logging.Level
//import java.util.logging.Logger
//Logger.getLogger("MY_LOG").log(Level.INFO, "")

class GetAdviceFromNetworkUseCase(private val adviceRepository: AdviceRepository) {

    fun execute(): Flow<ResponseNet> = flow {

        coroutineScope {

            val responseVariable = ResponseVariable<AdviceNet>()

            launch {

                when(val data = adviceRepository.getAdviceFromNetwork()) {

                    is ResponseNet.Success<*> -> responseVariable.data = data.data as AdviceNet

                    is ResponseNet.Error -> responseVariable.error = data.error

                    is ResponseNet.Progress -> {}
                }
            }

            responseVariable.startProgressBar(this@flow)
        }
    }
}