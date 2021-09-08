package com.github.zjjfly.sid.ch4

import org.scalatest.WordSpec

/**
  * @author zjjfly
  */
class DataAccessSpec extends WordSpec {
  "A DataAccess Service" should {
    "return query data" in {
      val service = new DataAccess3 with HasNullLogger
      assert(service.query("find mah datah!") != null)
    }
  }
}
