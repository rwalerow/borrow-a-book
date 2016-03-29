package utils

import play.api.i18n.Lang

/**
  * Created by robert on 29.03.16.
  */
object ApplicationImplicits {
	implicit val defaultLang = Lang("pl")
}
