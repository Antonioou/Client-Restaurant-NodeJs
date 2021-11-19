package com.ntncode.restaurantclient.model

data class CustomerData(
    val id_customer: String,
    val uid_customer: String,
    val firstname_customer: String,
    val lastname_customer: String,
    val phone_customer: Int,
    val email_customer: String,
    val photo_customer: String,
    val state_customer: Int,
    val key_notification_customer: String
)

data class CustomerResponse(
    val code_server: Int,
    val code_status: String,
    val message_server: String,
    val response: CustomerData
)