package com.broccoli.bnc.data.common

sealed class RemoteSourceException(val messageResource: Any?) : RuntimeException() {
    class Unexpected(messageResource: Int) : RemoteSourceException(messageResource)
    class Client(messageResource: Int) : RemoteSourceException(messageResource)
    class Server(messageResource: Any?) : RemoteSourceException(messageResource)
}
