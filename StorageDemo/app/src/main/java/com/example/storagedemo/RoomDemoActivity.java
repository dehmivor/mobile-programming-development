package com.example.storagedemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class RoomDemoActivity extends AppCompatActivity {
    private MyRoomDatabase database;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = MyRoomDatabase.getInstance(this);
        userDao = database.userDao();

        // Thêm một user vào database
        new InsertUserTask(userDao).execute(new User("John", "Doe"));

        // Lấy danh sách tất cả user
        new GetUsersTask(userDao).execute();
    }

    private static class InsertUserTask extends AsyncTask<User, Void, Void> {
        private final UserDao userDao;

        InsertUserTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insertAll(users);
            return null;
        }
    }

    private static class GetUsersTask extends AsyncTask<Void, Void, List<User>> {
        private final UserDao userDao;

        GetUsersTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected List<User> doInBackground(Void... voids) {
            return userDao.getAll();
        }

        @Override
        protected void onPostExecute(List<User> users) {
            for (User user : users) {
                Log.d("RoomDemo", "User: " + user.firstName + " " + user.lastName);
            }
        }
    }
}
