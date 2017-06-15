package com.github.martinlechner1.database

sealed trait SortBy {def name: String}

case object Id extends SortBy { val name = "id" }
case object Title extends SortBy { val name = "title" }
case object Price extends SortBy { val name = "price" }
case object Fuel extends SortBy { val name = "fuel" }
case object New extends SortBy { val name = "new" }
case object Mileage extends SortBy { val name = "mileage" }
case object FirstRegistration extends SortBy { val name = "firstRegistration" }

