package dev.mridx.common.common_data.di.qualifier

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.PROPERTY)
annotation class BaseUrl


@Qualifier
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.PROPERTY)
annotation class ApiBaseUrl



@Qualifier
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
annotation class CommonInterceptor


@Qualifier
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
annotation class AuthInterceptor


@Qualifier
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
annotation class ResponseInterceptor


@Qualifier
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.PROPERTY)
annotation class AppPreference

@Qualifier
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.PROPERTY)
annotation class PermissionPreference