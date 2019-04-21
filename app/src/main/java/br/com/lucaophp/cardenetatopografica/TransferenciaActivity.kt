package br.com.lucaophp.cardenetatopografica

import android.content.Intent
import android.content.SharedPreferences
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter

class TransferenciaActivity : AppCompatActivity() {
    internal var edtIP: EditText? =null
    internal var btnConnect: Button? =null
    internal var ip: String? =null
    fun showToast(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
    }

    fun backup(): Boolean {
        val projetoList = Projeto().buscar()
        for (p in projetoList) {
            try {
                val sd = Environment.getExternalStorageDirectory()
                val file = File(sd, "TOPO/")
                file.mkdirs()
                var path = file.path
                path += "/projeto_" + p.id + ".txt"
                val osw = OutputStreamWriter(FileOutputStream(path, false))
                val e = Estaca()
                val estacas = e.buscar("projeto_id=" + p.id)
                var conteudo = ""
                for (estaca in estacas) {
                    conteudo += "%s;%f;%f;%f\n".format(estaca.info.replace('\n', ' '), estaca.latitude, estaca.longitude, estaca.cota)
                }
                osw.write(conteudo)
                osw.close()
                val finalPath = path
                Thread(Runnable {
                    try {
                        for (i in 0..2)
                            Client.send(finalPath, ip, this@TransferenciaActivity)
                    } catch (e1: Exception) {
                        e1.printStackTrace()
                    }
                }).start()


            } catch (e: IOException) {
                e.printStackTrace()
                return false
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }

        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transferencia)
        edtIP = findViewById(R.id.edtIP) as EditText
        btnConnect = findViewById(R.id.btnConnect) as Button
        val sharedPreferences = getSharedPreferences(NAME_SET, 0)
        val editor = sharedPreferences.edit()
        edtIP!!.setText(sharedPreferences.getString("ip", ""))

        btnConnect!!.setOnClickListener {
            ip = edtIP!!.text.toString().trim { it <= ' ' }
            editor.putString("ip", ip)
            editor.commit()
            showToast(if (backup()) "Transferencia efetuada com sucesso" else "Erro ao fazer a transferencia!!!\n Pode ser que o servidor não esteja iniciado, ou o endereço de ip está incorreto")
        }
    }

    companion object {
        val NAME_SET = "TRANSFERENCIA_TOPO"
    }
}
