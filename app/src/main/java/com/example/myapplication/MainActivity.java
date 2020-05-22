package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button btnSignIn, btnRegister;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnRegister = findViewById(R.id.btnRegister);


        auth = FirebaseAuth.getInstance(); //запускаем авторизацию в базе данных
        db = FirebaseDatabase.getInstance(); //подключаемся к базе данных
        users = db.getReference("Users");

        btnRegister.setOnClickListener(new View.OnClickListener() { //обработчик событий
            @Override
            public void onClick(View v) {
                //тута вызываем функцию. позже реализую
                showRegisterWindow();


            }
        });


    }

    private void showRegisterWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Зарегистрироваться");
        dialog.setMessage("Введите все данные для регистрации");

        LayoutInflater inflater = LayoutInflater.from(this); //создали объект,  указываем, что работаем с этим же контекстом(классом)
        View register_window = inflater.inflate(R.layout.register_window, null); //внутрь переменной регвин мы получаем при помощи объекта инфлэйтер  шаблон с формочкой
        dialog.setView(register_window); //передаем объект
        //создали объект inflater на основе класса LayoutInflater
    }
}
