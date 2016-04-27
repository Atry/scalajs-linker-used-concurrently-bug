enablePlugins(PlayScala)

enablePlugins(BuildInfoPlugin)

lazy val js = project

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