package com.hms.lib.commonmobileservices.imageclassification.common

/**
 * A data class representing a labeled image along with the possibility score of the label.
 *
 * @property name The name or label of the image.
 * @property possibility The possibility score associated with the image label, indicating the likelihood
 * of the label being accurate or present in the image.
 */
data class ImageLabel(
    val name: String,
    val possibility: Float
)
