package com.project.weatherAroundTheWorld.utils

data class DataResource<T>(val status: WeatherDataState, val data: T?, val message: String?) {

    companion object {

        fun <T> success(data: T?): DataResource<T> {
            return DataResource(WeatherDataState.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): DataResource<T> {
            return DataResource(WeatherDataState.ERROR, data, msg)
        }

        fun <T> loading(data: T?): DataResource<T> {
            return DataResource(WeatherDataState.LOADING, data, null)
        }
    }
}