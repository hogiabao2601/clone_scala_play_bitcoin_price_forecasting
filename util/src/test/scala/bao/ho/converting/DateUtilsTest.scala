package bao.ho.converting

import java.util.Date
import java.util.Calendar

class DateUtilsTest extends BaseSpec {
  //  val LOG = Logger(LoggerFactory.getLogger(this.getClass))

  test("Date String to Date") {
    val date = "2018-01-01 00:00:00+0000"
    val d: Date = DateUtils.convertStringToDate(date)
    println(d)
    //    LOG.info(s"Converted Date :: $d")
    val cal = Calendar.getInstance
    cal.setTime(d)
    val year = cal.get(Calendar.YEAR)
    val month = cal.get(Calendar.MONTH)
    val day = cal.get(Calendar.DAY_OF_MONTH)
    assert(year == 2018)
    assert(month == 0)
    assert(day == 1)
  }

  test("Date to Epoch timestamp") {
    val date = "2018-01-01 00:00:00+0000"
    val d: Date = DateUtils.convertStringToDate(date)
    val epoch = DateUtils.convertDateToEpoch(d)
    assert(epoch == 1514764800000L)
  }

  test("Epoch timestamp to Date") {
    val epoch = 1514764800000L
    val d: Date = DateUtils.convertEpochToDate(epoch)
    println(d)
    val cal = Calendar.getInstance
    cal.setTime(d)
    val year = cal.get(Calendar.YEAR)
    val month = cal.get(Calendar.MONTH)
    val day = cal.get(Calendar.DAY_OF_MONTH)
    assert(year == 2018)
    assert(month == 0)
    assert(day == 1)


  }
}
