package com.diatomicsoft.navigation3.local_storage.converter

import androidx.room.TypeConverter
import com.diatomicsoft.navigation3.data.model.Address
import com.diatomicsoft.navigation3.data.model.Company
import com.diatomicsoft.navigation3.data.model.Geo
import com.diatomicsoft.navigation3.data.model.ModelUser
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory


class UserTypeConverter : BaseTypeConverter() {

    private val addressAdapter = moshi.adapter(Address::class.java)
    private val companyAdapter = moshi.adapter(Company::class.java)
    private val geoAdapter = moshi.adapter(Geo::class.java)

    @TypeConverter
    fun fromAddress(address: Address): String = addressAdapter.toJson(address)

    @TypeConverter
    fun toAddress(addressJson: String): Address =
        addressAdapter.fromJson(addressJson) ?: throw IllegalArgumentException("Invalid Address JSON")

    @TypeConverter
    fun fromCompany(company: Company): String = companyAdapter.toJson(company)

    @TypeConverter
    fun toCompany(companyJson: String): Company =
        companyAdapter.fromJson(companyJson) ?: throw IllegalArgumentException("Invalid Company JSON")

    @TypeConverter
    fun fromGeo(geo: Geo): String = geoAdapter.toJson(geo)

    @TypeConverter
    fun toGeo(geoJson: String): Geo =
        geoAdapter.fromJson(geoJson) ?: throw IllegalArgumentException("Invalid Geo JSON")

}


