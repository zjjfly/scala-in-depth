package com.github.zjjfly.sid.ch1

import java.sql.{Connection, PreparedStatement, ResultSet}

object JavaLibsImp extends App {
  assert(Iterables.filter(List(12, -1))(i => i > 0) == Iterable(12))
  assert(Iterables.find(List(-1, 12))(_ > 0).contains(12))
  assert(Predicates.or((i: Int) => i < 0)((i: Int) => i > 10)(12))
  assert(Predicates.and((i: Int) => i < 10)((i: Int) => i > 0)(1))
  assert(Predicates.notNull(1))
  assert(quickSort(List(3, 6, 5, 1, 2)) == List(1, 2, 3, 5, 6))

  //scala简洁的语法的体现
  def quickSort[T](t: List[T])(implicit ev: T => Ordered[T]): List[T] =
    t match {
      case Nil => Nil
      case x :: xs => {
        val (smaller, bigger) = xs.partition(_ < x)
        (quickSort(smaller) :+ x) ++ quickSort(bigger)
      }
    }
}
trait JdbcTemplate {
  def query[T](psc: Connection => PreparedStatement,
               rowMapper: (ResultSet, Int) => T): List[T]
}

object Iterables {
  def filter[A](col: Iterable[A])(predicate: A => Boolean): Iterable[A] = {
    if (col.isEmpty) col
    else {
      col.filter(predicate(_))
    }
  }
  def find[A](col: Iterable[A])(predicate: A => Boolean): Option[A] = {
    if (col.isEmpty) None
    else {
      val iterator: Iterator[A] = col.iterator
      while (iterator.hasNext) {
        val t = iterator.next()
        if (predicate(t)) return Some(t)
      }
      None
    }
  }
}

object Predicates {
  def or[T](p1: T => Boolean)(p2: T => Boolean): T => Boolean =
    (t: T) => p1(t) || p2(t)
  def and[T](p1: T => Boolean)(p2: T => Boolean): T => Boolean =
    (t: T) => p2(t) && p2(t)
  def notNull[T]: T => Boolean = _ != null
}
