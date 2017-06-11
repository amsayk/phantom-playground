import cbt._

object Versions {
  val logback = "1.2.3"
  val phantom = "2.9.2"
}

class Build(val context: Context) extends BaseBuild{
  override def dependencies = (
    super.dependencies ++ // don't forget super.dependencies here for scala-library, etc.
    Seq(
      // source dependency
      // DirectoryDependency( projectDirectory ++ "/subProject" )
    ) ++
    // pick resolvers explicitly for individual dependencies (and their transitive dependencies)
    Resolver( mavenCentral, sonatypeReleases ).bind(
      ScalaDependency( "com.outworkers", "phantom-dsl", Versions.phantom ),
      ScalaDependency( "com.outworkers", "phantom-connectors", Versions.phantom ),
      ScalaDependency( "com.outworkers", "phantom-jdk8", Versions.phantom ),
      MavenDependency( "ch.qos.logback", "logback-classic", Versions.logback )
      // CBT-style Scala dependencies
      // ScalaDependency( "com.lihaoyi", "ammonite-ops", "0.5.5" )
      // MavenDependency( "com.lihaoyi", "ammonite-ops_2.11", "0.5.5" )

      // SBT-style dependencies
      // "com.lihaoyi" %% "ammonite-ops" % "0.5.5"
      // "com.lihaoyi" % "ammonite-ops_2.11" % "0.5.5"
    )
  )
}
