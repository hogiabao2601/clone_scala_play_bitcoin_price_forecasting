package bao.ho.converting

import java.text.SimpleDateFormat
import java.util.Date

object DateUtils {
  val formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ssZ")

  /**
    * Convert a Date to Epoch timestamp
    *
    * @param date
    * @return
    */
  def convertDateToEpoch(date: Date): Long = date.getTime

  /**
    * Convert a Epoch timestamp to Date
    *
    * @param epoch
    * @return
    */
  def convertEpochToDate(epoch: Long): Date = new Date(epoch)

  /**
    * Convert a Date string to Date with yyyy-MM-dd hh:mm:ssZ format
    *
    * @param date
    * @return
    */
  def convertStringToDate(date: String): Date = {
    formatter.parse(date)
  }

  def getDateAsString(date: Date): String = {
    val DATE_FORMAT = "yyyy-MM-dd"
    val dateFormat = new SimpleDateFormat(DATE_FORMAT)
    dateFormat.format(date)
  }
}
