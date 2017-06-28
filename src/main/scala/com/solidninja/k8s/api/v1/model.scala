package com.solidninja.k8s.api.v1

import java.time.ZonedDateTime

import io.circe.Json

// FIXME: Incomplete mappings

case class Timestamp(v: ZonedDateTime) extends AnyVal
case class Namespace(v: String) extends AnyVal
case class Annotations(v: Map[String, Json]) extends AnyVal
case class Uid(v: String) extends AnyVal
case class Version(v: String) extends AnyVal
case class Path(v: String) extends AnyVal
case class ImageName(v: String) extends AnyVal {
  // TODO: mechanism for extracting version information?
}

trait V1Object {
  val apiVersion = "v1"
}

/**
  * @see [[https://kubernetes.io/docs/api-reference/v1.5/#pod-v1 Pod v1]]
  */
case class Pod(metadata: ObjectMeta, spec: PodSpec) extends V1Object

/**
  * @see [[https://kubernetes.io/docs/api-reference/v1.5/#podspec-v1 PodSpec v1]]
  */
case class PodSpec(volumes: Option[List[Volume]], containers: List[Container])

/**
  * @see [[https://kubernetes.io/docs/api-reference/v1.5/#container-v1 Container v1]]
  */
case class Container(image: ImageName,
                     imagePullPolicy: String,
                     args: Option[List[String]],
                     command: Option[List[String]],
                     env: Option[List[EnvVar]])

// FIXME: ImagePullPolicy not a string

/**
  * @see [[https://kubernetes.io/docs/api-reference/v1.5/#volume-v1 Volume v1]]
  */
case class Volume(name: String)

/**
  * @see [[https://kubernetes.io/docs/api-reference/v1.5/#envvar-v1 EnvVar]]
  */
case class EnvVar(name: String, value: String)

/**
  * @see [[https://kubernetes.io/docs/api-reference/v1.5/#objectmeta-v1 ObjectMeta v1]]
  */
case class ObjectMeta(name: Option[String],
                      namespace: Option[Namespace],
                      labels: Map[String, String],
                      annotations: Annotations,
                      uid: Option[Uid],
                      resourceVersion: Option[Version],
                      creationTimestamp: Option[Timestamp],
                      selfLink: Option[Path])

object ObjectMeta {
  def apply(name: String, namespace: Namespace, labels: Map[String, String], annotations: Annotations): ObjectMeta =
    ObjectMeta(Some(name),
               Some(namespace),
               labels,
               annotations,
               uid = None,
               resourceVersion = None,
               creationTimestamp = None,
               selfLink = None)
}
