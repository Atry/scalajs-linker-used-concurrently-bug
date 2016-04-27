enablePlugins(PlayScala)

enablePlugins(BuildInfoPlugin)

scalaVersion in Global := "2.11.8"

lazy val js = project

import org.scalajs.core.tools.io.FileVirtualJSFile

resourceGenerators in Assets += Def.task[Seq[java.io.File]] {
  val linked = (scalaJSLinkedFile in js in Compile).value.asInstanceOf[FileVirtualJSFile]
  Seq(linked.file, linked.sourceMapFile, (packageJSDependencies in js in Compile).value)
}.taskValue

buildInfoKeys := Seq[BuildInfoKey](
  BuildInfoKey.map(scalaJSLinkedFile in js in Compile) {
    case (_, linked: FileVirtualJSFile) => "jsFileName" -> linked.file
  },
  BuildInfoKey(packageJSDependencies in js in Compile)
)
