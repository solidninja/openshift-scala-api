import Dependencies._
import build._

lazy val root = Project(
  id = "openshift-client",
  base = file("."),
  settings = commonSettings ++ publishSettings ++ Seq(
    libraryDependencies ++=
      circe ++ diffson ++ http4s ++ fs2 ++ scalatest ++ testBlazeHttp ++ runtime,
    scalafmtOnCompile := true,
    useGpg := true
  )
)
