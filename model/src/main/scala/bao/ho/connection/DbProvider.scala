package bao.ho.connection

import slick.jdbc.JdbcProfile

trait DbProvider {
  def getDriver(): JdbcProfile

  def getDb(): JdbcProfile#Backend#Database
}
