package mx.edu.utng.appsqlite

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*;

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAlta.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this,"administracion", null, 1)
            val bd = admin.writableDatabase
            val registro = ContentValues()
            registro.put("codigo", etxId.getText().toString())
            registro.put("nombre", etxNombre.getText().toString())
            registro.put("apellido", etxApellido.getText().toString())
            bd.insert("usuarios", null, registro)
            bd.close()
            etxId.setText("")
            etxNombre.setText("")
            etxApellido.setText("")
            Toast.makeText(this, "Se registraron los datos del usuario", Toast.LENGTH_SHORT).show()
        }

        btnBusCod.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val fila = bd.rawQuery("select nombre,apellido from usuarios where codigo=${etxId.text.toString()}", null)
            if (fila.moveToFirst()) {
                etxNombre.setText(fila.getString(0))
                etxApellido.setText(fila.getString(1))
            } else
                Toast.makeText(this, "No existe un usuario con dicho identificador",  Toast.LENGTH_SHORT).show()
            bd.close()
        }

        btnBusNom.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val fila = bd.rawQuery("select codigo,apellido from usuarios where nombre='${etxNombre.text.toString()}'", null)
            if (fila.moveToFirst()) {
                etxId.setText(fila.getString(0))
                etxApellido.setText(fila.getString(1))
            } else
                Toast.makeText(this, "No existe un usuario con dicho Nombre", Toast.LENGTH_SHORT).show()
            bd.close()
        }

        btnBajaCOod.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val cant = bd.delete("usuarios", "codigo=${etxId.text.toString()}", null)
            bd.close()
            etxId.setText("")
            etxNombre.setText("")
            etxApellido.setText("")
            if (cant == 1)
                Toast.makeText(this, "Se borró el usuario con dicho identificador", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "No existe un usuario con dicho identificador", Toast.LENGTH_SHORT).show()
        }

        btnMod.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val registro = ContentValues()
            registro.put("nombre", etxNombre.text.toString())
            registro.put("apellido", etxApellido.text.toString())
            val cant = bd.update("usuarios", registro, "codigo=${etxId.text.toString()}", null)
            bd.close()
            if (cant == 1)
                Toast.makeText(this, "Se modificaron los datos", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "No existe un usuario con el identificador ingresado", Toast.LENGTH_SHORT).show()
        }
    }

}
