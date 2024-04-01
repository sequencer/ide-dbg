package org.chipsalliance.chiselplugin.test

import chisel3._
import chisel3.experimental.hierarchy.{Definition, Instance, instantiable, public}
import org.chipsalliance.chiselplugin.test.AddOne.AddOneInjected.fromInstance

class AddTwo(width: Int) extends Module {
  val in  = IO(Input(UInt(width.W)))
  val out = IO(Output(UInt(width.W)))
  val addOneDef = Definition(new AddOne(width))
  val i0 = Instance(addOneDef)
  val i1 = Instance(addOneDef)
  i0.in := in
  i1.in := i0.out
  out   := i1.out
}

@instantiable
class AddOne(width: Int) extends Module {
  @public val in  = IO(Input(UInt(width.W)))
  @public val out = IO(Output(UInt(width.W)))
  out := in + 1.U
}

object AddOne {
  abstract class AddOneInjected {
    val in: UInt
    val out: UInt
  }

  object AddOneInjected {
    implicit def fromInstance(instance: Instance[AddOne]): AddOneInjected = ???
  }
}
