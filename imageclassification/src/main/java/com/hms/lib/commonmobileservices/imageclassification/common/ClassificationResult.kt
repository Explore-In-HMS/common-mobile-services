package com.hms.lib.commonmobileservices.imageclassification.common

import com.hms.lib.commonmobileservices.core.ErrorModel

/**
 * A sealed class representing the result of a classification operation.
 * This class encapsulates either a successful result containing the classified data or an error with an error message
 * and an optional error model.
 *
 * @param T The type of data contained in the successful result.
 */
sealed class ClassificationResult<out T> {
    /**
     * Represents a successful classification result.
     *
     * @property data The data resulting from the successful classification operation.
     */
    data class Success<out T>(
        val data: T
    ) : ClassificationResult<T>()

    /**
     * Represents an error that occurred during the classification operation.
     *
     * @property errorMessage A descriptive message indicating the nature of the error.
     * @property errorModel An optional error model providing additional information about the error.
     */
    data class Error(
        val errorMessage: String? = null,
        val errorModel: ErrorModel? = null
    ) : ClassificationResult<Nothing>()
}
