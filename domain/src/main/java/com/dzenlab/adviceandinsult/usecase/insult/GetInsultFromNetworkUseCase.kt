package com.dzenlab.adviceandinsult.usecase.insult

import com.dzenlab.adviceandinsult.models.InsultNet
import com.dzenlab.adviceandinsult.models.ResponseNet
import com.dzenlab.adviceandinsult.models.ResponseVariable
import com.dzenlab.adviceandinsult.repository.InsultRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

//import java.util.logging.Level
//import java.util.logging.Logger
//Logger.getLogger("MY_LOG").log(Level.INFO, "")

class GetInsultFromNetworkUseCase(private val insultRepository: InsultRepository) {

    fun execute(): Flow<ResponseNet> = flow {

        coroutineScope {

            val responseVariable = ResponseVariable<InsultNet>()

            launch {

                when(val data = insultRepository.getInsultFromNetwork()) {

                    is ResponseNet.Success<*> -> responseVariable.data = data.data as InsultNet

                    is ResponseNet.Error -> responseVariable.error = data.error

                    is ResponseNet.Progress -> {}
                }
            }

            responseVariable.startProgressBar(this@flow)
        }
    }
}