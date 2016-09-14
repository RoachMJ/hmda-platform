package hmda.persistence

import akka.actor.{ ActorRef, ActorSystem, Props, Terminated }
import hmda.api.processing.LocalHmdaEventProcessor
import hmda.model.fi.SubmissionId
import hmda.persistence.institutions.{ FilingPersistence, InstitutionPersistence, SubmissionPersistence }
import hmda.persistence.processing.{ HmdaFileParser, HmdaFileValidator, HmdaRawFile, SingleLarValidation }

object HmdaSupervisor {

  case class FindActorByName(name: String)
  case class FindFilings(name: String, institutionId: String)
  case class FindProcessingActor(name: String, submissionId: SubmissionId)

  def props(): Props = Props(new HmdaSupervisor)

  def createSupervisor(system: ActorSystem): ActorRef = {
    system.actorOf(HmdaSupervisor.props(), "supervisor")
  }
}

class HmdaSupervisor extends HmdaActor {

  import HmdaSupervisor._

  var hmdaPersistentActors = Map.empty[String, ActorRef]

  override def receive: Receive = {
    case FindActorByName(name) =>
      sender() ! findActorByName(name)

    case FindFilings(name, id) =>
      sender() ! findFilings(name, id)

    case FindProcessingActor(name, submissionId) =>
      sender() ! findProcessingActor(name, submissionId)

    case Terminated(ref) =>
      log.debug(s"actor ${ref.path} terminated")
      hmdaPersistentActors = hmdaPersistentActors.filterNot { case (_, value) => value == ref }
  }

  private def findActorByName(name: String): ActorRef = hmdaPersistentActors.getOrElse(name, createActor(name))

  private def findFilings(name: String, id: String): ActorRef = hmdaPersistentActors.getOrElse(s"$name-$id", createFilings(name, id))

  private def findProcessingActor(name: String, submissionId: SubmissionId): ActorRef = hmdaPersistentActors.getOrElse(name, createProcessingActor(name, submissionId))

  private def createActor(name: String): ActorRef = name match {
    case id @ SingleLarValidation.name =>
      val actor = context.actorOf(SingleLarValidation.props, id)
      supervise(actor, id)
    case id @ InstitutionPersistence.name =>
      val actor = context.actorOf(InstitutionPersistence.props, id)
      supervise(actor, id)
    case id @ LocalHmdaEventProcessor.name =>
      val actor = context.actorOf(LocalHmdaEventProcessor.props(), id)
      supervise(actor, id)
  }

  private def createFilings(name: String, id: String): ActorRef = name match {
    case FilingPersistence.name =>
      val filingsId = s"$name-$id"
      val actor = context.actorOf(FilingPersistence.props(id), s"${FilingPersistence.name}-$id")
      supervise(actor, filingsId)
  }

  private def createProcessingActor(name: String, submissionId: SubmissionId): ActorRef = name match {
    case id @ HmdaRawFile.name =>
      val actor = context.actorOf(HmdaRawFile.props(submissionId), s"${HmdaRawFile.name}-${submissionId.toString}")
      supervise(actor, id)
    case id @ HmdaFileParser.name =>
      val actor = context.actorOf(HmdaFileParser.props(submissionId))
      supervise(actor, id)
    case id @ HmdaFileValidator.name =>
      val actor = context.actorOf(HmdaFileValidator.props(submissionId))
      supervise(actor, id)
    case id @ SubmissionPersistence.name =>
      val actorId = s"${SubmissionPersistence.name}-${submissionId.toString}"
      val actor = context.actorOf(SubmissionPersistence.props(submissionId.institutionId, submissionId.period), actorId)
      supervise(actor, actorId)
  }

  private def supervise(actorRef: ActorRef, id: String): ActorRef = {
    hmdaPersistentActors += id -> actorRef
    context watch actorRef
    actorRef
  }

}
