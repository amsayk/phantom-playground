package code

import scala.concurrent.Future

import java.util.UUID

import com.outworkers.phantom.connectors
import com.outworkers.phantom.connectors.CassandraConnection
import com.outworkers.phantom.dsl._

class DB(override val connector: CassandraConnection) extends Database[DB](connector) {
  object People extends People with Connector
}

object DB extends DB(connectors.ContactPoint.local.keySpace("people"))


case class Person(id: UUID, name: String, firstName: String)

abstract class People extends Table[People, Person] {
  object id extends UUIDColumn with PartitionKey
  object name extends StringColumn
  object firstName extends StringColumn

  // def peopleByFirstName(firstName: String): Future[Seq[Person]] = {
  //   select.where(_.firstName eqs firstName).limit(5000).fetch()
  // }

  def findById(id: UUID): Future[Option[Person]] = {
    select.where(_.id eqs id).one()
  }

  def listAll(): Future[List[Person]] = select.fetch()
}

object Main{
  def main( args: Array[String] ): Unit = {
    DB.People.listAll().onSuccess{
      case people =>

        people.foreach(s => {
          println(s)
        })
    }

    scala.io.StdIn.readLine();
  }
}

