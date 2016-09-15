package com.example.adrianaalexandru.retrofitex;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Adriana on 14/09/2016.
 */
public interface TaskService {

    //each endpoint specifies an annotation of the HTTP method (GET, POST )
    //@Body	payload for the POST call (serialized from a Java object to a JSON string)
    //@Path	variable substitution for the API endpoint (i.e. username will be swapped for {username} in the URL endpoint)
    //@Query specifies the query key name with the value of the annotated parameter

    /**
     * Get all tasks
     *
     * @return
     */
    @GET("tasks")
    Call<List<Task>> getAllTasks();

    /**
     * query tasks by asignee
     * @param asignee
     * @return
     */
    @GET("tasks/query")
    Call<List<Task>> getAllTasksWithAsignee(@Query("asignee") String asignee);

    /**
     * get task by id
     *
     * @param id task id
     * @return the task with the specified id
     */
    @GET("tasks/{id}/asignee/{shbj}")
    Call<Task> getTaskById(@Path("id") String id);

    /**
     * create a new task
     *
     * @param newTask The task to be created
     * @return the newly created task
     */
    //header tells the server what the data actually is
    //STATIC header
    @Headers("Content-Type: application/json")
    @POST("tasks")
    Call<Task> createNewTask(@Body Task newTask);

    /**
     * create or update task
     *
     * @param task The task to be created/updated
     * @return the newly created task
     */
    //dynamic header
    @PUT("tasks/{taskId}")
    Call<Task> createOrUpdateTask(@Path("taskId")String  taskId,@Body Task task,@Header("Authorization") String authorization);
}
