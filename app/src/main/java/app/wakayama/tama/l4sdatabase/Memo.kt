package app.wakayama.tama.l4sdatabase

import io.realm.Realm
import io.realm.RealmObject

//openは、Realmを使うのに必要になるから
open class Memo (
    open var title: String = "",
            open var content: String = ""
//RealmObjectと言う型を継承している
) : RealmObject()