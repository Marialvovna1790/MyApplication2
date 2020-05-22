package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class MainActivity extends AppCompatActivity {

    Button btnSignIn, btnRegister;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;
    RelativeLayout root;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnRegister = findViewById(R.id.btnRegister);

        root = findViewById(R.id.root_element);//просто находим род элемент

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
        //благодаря этому объекту мы можем получить нужный нам шаблон
        //получаем этот нужный нам шаблон inflate(R.layout.register_window
        //помещаем его в переменную,  которая идет с типом данных VIEW register_window - это шаблон
        //устанавливаем этот шаблон для всплывающего окна


        final MaterialEditText email = register_window.findViewById(R.id.emailField); //final - переменная константа
        final MaterialEditText pass = register_window.findViewById(R.id.passField);
        final MaterialEditText name = register_window.findViewById(R.id.nameField);
        final MaterialEditText phone = register_window.findViewById(R.id.phoneField);
        //получили все поля

        //делаем кнопку позакрытию окна и отправить

        dialog.setNegativeButton("отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) { // сработает, когда нажмем на "отменить"
                dialogInterface.dismiss(); // окно будет скрыто при нажатии на кнопку

            }

        });//функция позволяет установить кнопку отмены

        dialog.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if(TextUtils.isEmpty(email.getText().toString())) {//класс textUtils предоставляет доступ к функциям(isEmpty) которая позволяет проверить, является ли строка пустой
                    //строка емэйл,  из нее получаем весь текст, который ввел пользователь getText и приводим в формат строки с помощью toString
                    Snackbar.make(root, "Введите вашу почту", Snackbar.LENGTH_SHORT).show(); // show показывает само уведомление/ LENGTH_SHORT - сколько снекбар будет длиться(коротко)
                    return;//если ошибка, то сразу же выходить из этой функции

                }

                if(TextUtils.isEmpty(name.getText().toString())) {
                    Snackbar.make(root, "Введите ваше имя", Snackbar.LENGTH_SHORT).show();
                    return;

                }


                if(TextUtils.isEmpty(phone.getText().toString())) {
                    Snackbar.make(root, "Введите ваш телефон", Snackbar.LENGTH_SHORT).show();
                    return;

                }


                if(pass.getText().toString().length() < 5) {
                    Snackbar.make(root, "Введите пароль, который более 5 символов", Snackbar.LENGTH_SHORT).show();
                    return;

                }
            }

        });
    }
}
