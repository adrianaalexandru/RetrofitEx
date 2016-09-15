package com.example.adrianaalexandru.retrofitex;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private final String ACCESS_TOKEN = "accesstoken";
    private Button newTaskButton;
    private Button tasksListButton;
    private Button taskByIdButton;
    private Button tasksByAsigneeButton;
    private EditText textEdittext;
    private Button updateTaskButton;
    private TaskService taskService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLayout();

        taskService = ServiceGenerator.createService(TaskService.class);

        //create service with authentication in requests headers
        //taskService=ServiceGenerator.createService(TaskService.class,ACCESS_TOKEN);

    }


    private void initLayout() {
        newTaskButton = (Button) findViewById(R.id.activity_main_add_new_task_button);
        tasksListButton = (Button) findViewById(R.id.activity_main_all_tasks_button);
        taskByIdButton = (Button) findViewById(R.id.activity_main_get_tasks_by_id_button);
        tasksByAsigneeButton = (Button) findViewById(R.id.activity_main_get_tasks_by_asignee_button);
        updateTaskButton = (Button) findViewById(R.id.activity_main_update_task_button);
        textEdittext = (EditText) findViewById(R.id.activity_main_text_edittext);


        newTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewTask();
            }
        });
        tasksListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllTasks();
            }
        });
        updateTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = textEdittext.getText().toString();
                if (id != null) {
                    updateTask(id, new Task("updated", "updated","updated",new Date()));
                }
                else {
                    Toast.makeText(MainActivity.this, "Enter id", Toast.LENGTH_SHORT).show();
                }
            }
        });
        taskByIdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = textEdittext.getText().toString();
                if (id != null) {
                    getTasksById(id);
                }
                else {
                    Toast.makeText(MainActivity.this, "Enter id", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tasksByAsigneeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String asignee = textEdittext.getText().toString();
                if (asignee != null) {
                    getTasksByAsignee(asignee);
                }
                else {
                    Toast.makeText(MainActivity.this, "Enter asignee", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateTask(final String id, Task task) {
        Call<Task> call = taskService.createOrUpdateTask(id,task,ACCESS_TOKEN);
        call.enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> call, Response<Task> response) {
                Toast.makeText(MainActivity.this, "UPDATED TASK WITH ID " + id
                        + response.body().toString(), Toast.LENGTH_SHORT).show();
                Log.v(TAG, response.body().toString());
            }

            @Override
            public void onFailure(Call<Task> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showTasksDialog(List<Task> tasks) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
        builderSingle.setTitle("Tasks");

        final ArrayAdapter<Object> arrayAdapter = new ArrayAdapter<Object>(
                MainActivity.this,
                android.R.layout.select_dialog_singlechoice);
        arrayAdapter.addAll(tasks);


        builderSingle.setAdapter(
                arrayAdapter,
                null);
        builderSingle.show();
    }

    private void getTasksById(final String taskId) {
        Call<Task> call = taskService.getTaskById(taskId);
        call.enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> call, Response<Task> response) {
                Toast.makeText(MainActivity.this, "FOUND TASK WITH ID " + taskId + response.body().toString(), Toast.LENGTH_SHORT).show();

                    Log.v(TAG, response.body().toString());

            }

            @Override
            public void onFailure(Call<Task> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addNewTask() {
        try {
            Task task = new Task("task n", "task n description", "c", Constants.SIMPLE_DATE_FORMAT.parse("2016-10-10"));
            Call<Task> call = taskService.createNewTask(task);
            call.enqueue(new Callback<Task>() {
                @Override
                public void onResponse(Call<Task> call, Response<Task> response) {
                    Toast.makeText(MainActivity.this, "CREATED " + response.body().toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Task> call, Throwable t) {
                    Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void getAllTasks() {
        Call<List<Task>> call = taskService.getAllTasks();
        call.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                showTasksDialog(response.body());
                for(int i=0;i<response.body().size();i++){
                    Log.v(TAG, response.body().get(i).toString());
                }
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getTasksByAsignee(String asignee) {
        Call<List<Task>> call = taskService.getAllTasksWithAsignee(asignee);
        call.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                showTasksDialog(response.body());
                for(int i=0;i<response.body().size();i++){
                 Log.v(TAG, response.body().get(i).toString());
                }
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
