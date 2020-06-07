package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.myapplication.Models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
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
        if (isAuthorized()) {
            startMain();
        }
        setContentView(R.layout.activity_main);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnRegister = findViewById(R.id.btnRegister);

        root = findViewById(R.id.root_element);//просто находим род элемент

        auth = FirebaseAuth.getInstance(); //запускаем авторизацию в базе данных
        db = FirebaseDatabase.getInstance(); //подключаемся к базе данных
        users = db.getReference("Users");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignInWindow();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() { //обработчик событий
            @Override
            public void onClick(View v) {
                //тута вызываем функцию. позже реализую
                showRegisterWindow();

            }


        });


    }

    private void showSignInWindow() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Войти");
        dialog.setMessage("Введите данные для входа");

        LayoutInflater inflater = LayoutInflater.from(this);
        View sing_in_window = inflater.inflate(R.layout.sign_in_window, null); //внутрь переменной регвин мы получаем при помощи объекта инфлэйтер  шаблон с формочкой
        dialog.setView(sing_in_window); //передаем объект
        final MaterialEditText email = sing_in_window.findViewById(R.id.emailField); //final - переменная константа
        final MaterialEditText pass = sing_in_window.findViewById(R.id.passField);


        dialog.setNegativeButton("Oтменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) { // сработает, когда нажмем на "отменить"
                dialogInterface.dismiss(); // окно будет скрыто при нажатии на кнопку

            }

        });

        dialog.setPositiveButton("Войти", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if(TextUtils.isEmpty(email.getText().toString())) {
                    Snackbar.make(root, "Введите вашу почту", Snackbar.LENGTH_SHORT).show();
                    return;

                }


                if(pass.getText().toString().length() < 5) {
                    Snackbar.make(root, "Введите пароль, который более 5 символов", Snackbar.LENGTH_SHORT).show();
                    return;

                }


               auth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
               .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                   @Override
                   public void onSuccess(AuthResult authResult) {
                       setAuthorized(true, MainActivity.this);
                       PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putBoolean("login_ok", true).apply();
                       startMain();

                   }

               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Snackbar.make(root, e.getMessage(), Snackbar.LENGTH_LONG).show();
                   }
               });
            }

        });

        dialog.show();

    }

    public static void setAuthorized(boolean isAuthorized, Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean("login_ok", isAuthorized).apply();
    }

    private boolean isAuthorized() {
        return PreferenceManager.getDefaultSharedPreferences(this).getBoolean("login_ok", false);
    }

    private void startMain() {
        startActivity(new Intent(MainActivity.this, MapActivity.class));
        finish();
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


                if(pass.getText().toString().length() < 6) {
                    Snackbar.make(root, "Введите пароль, который более 6 символов", Snackbar.LENGTH_SHORT).show();
                    return;

                }

                // регистрация пользователя

                auth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("err", "error", e);
                                Snackbar.make(root, e.getLocalizedMessage(), Snackbar.LENGTH_SHORT).show();

                            }
                        })
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) { //обработчик события onSuccess вызовет функцию то если пользователь успешно добавлен в бд
                                //создаем объект на основе классса Юзер
                                User user = new User();
                                user.setEmail(email.getText().toString());
                                user.setName(name.getText().toString());
                                user.setPass(pass.getText().toString());
                                user.setPhone(phone.getText().toString());
                                Log.d("ok", "onsuccess");

                                users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()) //ключ для рользователя - его емейл
                                        .setValue(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Snackbar.make(root, "Пользователь добавлен", Snackbar.LENGTH_SHORT).show();
                                            }
                                        });


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(root,"ошибка регистрации." + e.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                });
            }

        });

        dialog.show();
    }
}
