package com.example.studen_android.helpers

sealed class HttpErrors(val alertMessage: String?, val statusCode: Int): Throwable(alertMessage) {
    object Unauthorized: HttpErrors("Ваша сессия устарела. Попробуйте авторизоваться заново", 401)
    object Empty: HttpErrors("Невозможно выполнить данное действие. Свяжитесь с поддержкой", 400)
    object NoRoute: HttpErrors("Данное действие невозможно, так как серверный рут устарел. Обновите приложение", 404)
    object ServerError: HttpErrors("Ошибка на стороне сервера. Статус код 500", 500)
    object EmailAlreadyRegistred: HttpErrors("На данный логин уже зарегистрирован аккаунт", 400)
    data class LocalizedError(val text: String?): HttpErrors(text, 400)
    object UnknownError: HttpErrors("Неизвестная ошибка. Попробуйте перезапустить приложение", 4444)
    object IncorrectPassOrLogin: HttpErrors("Некорректный логин или пароль", 400)
}
