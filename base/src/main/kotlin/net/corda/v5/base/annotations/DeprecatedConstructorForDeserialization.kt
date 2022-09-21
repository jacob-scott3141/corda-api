package net.corda.v5.base.annotations

/**
 * This annotation is a marker to indicate which secondary constructors should be considered, and in which
 * order, for evolving objects during their deserialization.
 *
 * Versions will be considered in descending order, currently duplicate versions will result in
 * non-deterministic behaviour when deserializing objects
 *
 * @property version integer defining order that these annotations will be applied
 */
@Target(AnnotationTarget.CONSTRUCTOR)
@Retention(AnnotationRetention.RUNTIME)
annotation class DeprecatedConstructorForDeserialization(val version: Int)
