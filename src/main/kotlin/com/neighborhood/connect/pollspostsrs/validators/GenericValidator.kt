package com.neighborhood.connect.pollspostsrs.validators

import com.neighborhood.connect.errorhandlerlib.ErrorCode
import com.neighborhood.connect.errorhandlerlib.models.Error
import com.neighborhood.connect.errorhandlerlib.models.FieldErrors

open class GenericValidator {
    private var obj: Any? = null
    private var fieldErrors: FieldErrors = FieldErrors(HashMap())
    private lateinit var fieldName: String
    private var hasError = false

    fun rule(obj: Any?, fieldName: String): GenericValidator {
        this.obj = obj
        this.fieldName = fieldName
        return this
    }

    fun isValueBlank(): GenericValidator {
        if (this.obj is String && (this.obj as String).isBlank()) {
            val error = Error(
                id = ErrorCode.BLANK_FIELD.errorCode,
                description = ErrorCode.BLANK_FIELD.errorDescription
            )
            addToFieldErrors(error)
            hasError = true
        }
        return this
    }

    fun isArrayListSizeZero(): GenericValidator {
        if (this.obj is ArrayList<*> && (this.obj as ArrayList<*>).isEmpty()) {
            val error = Error(
                    id = ErrorCode.EMPTY_ARRAY.errorCode,
                    description = ErrorCode.EMPTY_ARRAY.errorDescription
            )
            addToFieldErrors(error)
            hasError = true
        }
        return this
    }

    private fun addToFieldErrors(error: Error) {
        if (fieldErrors.errors[fieldName] != null)
            fieldErrors.errors[fieldName]?.add(error)
        else fieldErrors.errors[fieldName] = mutableListOf(error)
    }

    fun getFieldErrors(): FieldErrors {
        return fieldErrors
    }
}
