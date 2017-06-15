package com.github.martinlechner1.app

import java.time.Instant

import org.json4s.CustomSerializer
import org.json4s.JsonAST.JString

/**
  * The API should support ISO-8601 Timestamps
  */
case object InstantSerializer extends CustomSerializer[Instant](_ => ( {
  case JString(s) => Instant.parse(s)
}, {
  case instant: Instant => JString(instant.toString)
}))
