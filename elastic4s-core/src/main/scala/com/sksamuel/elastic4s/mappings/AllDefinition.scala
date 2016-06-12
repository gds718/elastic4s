package com.sksamuel.elastic4s.mappings

case class AllDefinition(
  enabled: Option[Boolean],
  store: Option[String],
  termVector: Option[TermVector],
  indexAnalyzer: Option[String],
  searchAnalyzer: Option[String]
)

object AllDefinition {
  def apply(enabled: Boolean): AllDefinition = apply(Some(enabled), None, None, None, None)
}
