package app.wakayama.tama.l4sdatabase

import android.icu.text.CaseMap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val realm: Realm = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //memoと言う変数に取得したデータを代入
        val memo: Memo? = read()

        //DBから取得したmemoをEditTextのテキストに表示する処理
        if (memo != null){
            titleEditText.setText(memo.title)
            contentEditText.setText(memo.content)
        }

        //保存ボタンを押したとき、titleEditTextとcontentEditTextに入力されたテキストを取得しsave()メソッドに値を渡す
        saveButton.setOnClickListener {
            val title: String = titleEditText.text.toString()
            val content: String = contentEditText.text.toString()
            save(title, content)
        }

    }

    //画面終了時にRealmを閉じる
    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    fun read(): Memo?{
        //realmを使ってDBの中のMemoリストから最初のデータを1つ取り出している
        return realm.where(Memo::class.java).findFirst()
    }

    fun save(title: String, content: String){
        //保存する処理
        //既に保存されているmめもを取得
        val memo: Memo? = read()

        //この中でDBの操作（書き込み）をする
        realm.executeTransaction {
            //メモの更新、新規作成を条件分岐する
            if (memo != null){
                //更新
                memo.title = title
                memo.content = content
            } else {
                //新規作成
                val newMemo: Memo = it.createObject(Memo::class.java)
                newMemo.title = title
                newMemo.content = content
            }

            Snackbar.make(container,"保存しました！！", Snackbar.LENGTH_SHORT).show()
        }

    }

}