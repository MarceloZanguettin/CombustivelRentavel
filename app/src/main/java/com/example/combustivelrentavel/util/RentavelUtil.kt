package com.example.combustivelrentavel.util


import kotlin.math.pow

data class CustoRentabilidade(
    val custoPorKmComb1: Double,
    val custoPorKmComb2: Double
)

fun RentavelUtil(comsumoComb1: Double, comsumoComb2: Double, precoComb1: Double, precoComb2: Double): CustoRentabilidade {

    if (comsumoComb1 <= 0.0 || comsumoComb2 <= 0.0) {
        return CustoRentabilidade(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
    }

    val custoKm1 = precoComb1 / comsumoComb1
    val custoKm2 = precoComb2 / comsumoComb2


    return CustoRentabilidade(
        custoPorKmComb1 = custoKm1,
        custoPorKmComb2 = custoKm2
    )
}