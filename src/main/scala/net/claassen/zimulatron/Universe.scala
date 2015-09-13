package net.claassen.zimulatron

import akka.actor._

// Idea: The entity interaction is triggered by world shards. I.e. An entity doesn't actively determine whether it can
// see another entity, but rather expects the world to tell it, thereby invoking it to act in a specific Tick.
// This way entities can respond to Tick with some long update delay and precalculuate its state in the absence of
// interaction. The world is divided into shards that are responsible to scan all its entities and see if they have
// potential interactions with other entities in its or a neighboring shard and trigger those entities, potentially
// forcing them to rollback their non-interactive state to the current tick and re-act.

class Universe extends App {

}

object ControlProtocol {

  case class SetUpdateInterval(id: Long, updateInterval: Int)
  case class Tick(world: World)
  case class Tock(id: Long)
}

case class World(time: Long, entities: Map[Long,Entity])

case class ExtendedWorld(time: Long, entities: Map[Long, (ActorRef, Entity)])

trait Entity {
  def id: Long
  def name: String
  def location: Location
}

case class Location(x: Int, y: Int)

class Control extends Actor {

  import ControlProtocol._
  import context._

  case class WorldState(world: World, addresses: Map[Long,ActorRef])
  case class TickState(worldState: WorldState, awaiting: Set[Long] )

  def receive = startTick(WorldState(World(0,Map.empty),Map.empty))

  def startTick(state: WorldState): Receive = {
    state.addresses.foreach {_._2 ! Tick(state.world)}
    processingTick(TickState(state, state.addresses.keys.toSet))
  }

  def processingTick(tickState: TickState): Receive = {
      case Tock(id) =>
        val remaining = tickState.awaiting - id
        become(processingTick(TickState()))
  }
}

class Agent(id: Long, control: ActorRef) extends Actor {
 import ControlProtocol._
  def receive = {
    case Tick(world) =>
  }
}
