package currencyexchange.common


/**
 * Created by Tan N. Truong, on 12 April, 2023
 * Email: ludugz@gmail.com
 */
sealed class NetworkState<T>(val resource: T? = null, val exception: Exception? = null) {
    class Loading<T> : NetworkState<T>()
    class Success<T>(resource: T) : NetworkState<T>(resource = resource)
    class Failure<T>(exception: Exception?, val message: String) :
        NetworkState<T>(exception = exception)
}

open class UIState<T>(
    var resource: T? = null,
    var exception: Exception? = null,
    var message: String? = null,
) {
    class Loading<T> : UIState<T>()
    class Success<T>(resource: T?) : UIState<T>(resource = resource)
    class Failure<T>(exception: Exception?, message: String?) :
        UIState<T>(exception = exception, message = message)
}