package com.dzenlab.adviceandinsult.models

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.FlowCollector

class ResponseVariable<T>(var data: T? = null,
                          var error: String? = null) {

    suspend fun startProgressBar(flow: FlowCollector<ResponseNet>) {

        var progress = 0

        var progressReverse = false

        while (progress <= 100) {

            if((progress == 100 && !progressReverse) || (progress == 0 && progressReverse)) {

                if(this.data == null && this.error == null) {

                    progressReverse = !progressReverse

                    if(progressReverse) {

                        flow.emit(ResponseNet.Progress(progress = progress--))

                    } else {

                        flow.emit(ResponseNet.Progress(progress = progress++))
                    }

                    delay(5)

                } else {

                    this.data?.let { dataNotNull: T ->

                        flow.emit(ResponseNet.Success(data = dataNotNull))

                        return

                    } ?: this.error?.let { errorNotNull: String ->

                        flow.emit(ResponseNet.Error(error = errorNotNull))

                        return
                    }
                }

            } else {

                if(progressReverse) {

                    flow.emit(ResponseNet.Progress(progress = progress--))

                } else {

                    flow.emit(ResponseNet.Progress(progress = progress++))
                }

                delay(5)
            }
        }
    }
}