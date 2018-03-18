package com.cmput301w18t05.taskzilla;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.cmput301w18t05.taskzilla.activity.MainActivity;
import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.AddTaskRequest;
import com.cmput301w18t05.taskzilla.request.command.AddUserRequest;
import com.cmput301w18t05.taskzilla.request.command.GetAllTasksRequest;
import com.cmput301w18t05.taskzilla.request.command.GetTaskRequest;
import com.cmput301w18t05.taskzilla.request.command.SearchTaskRequest;

import java.util.ArrayList;

/**
 * Created by praharen on 2018-02-23.
 */

public class RequestManagerTest extends ActivityInstrumentationTestCase2 {
    Context ctx;

    public RequestManagerTest(){
        super(MainActivity.class);
        ctx = InstrumentationRegistry.getTargetContext();   // for passing into request manager.
    }

    public void testAddUserRequest() {
        User user = new User();
        user.setName("test name");
        user.setUsername("myuniqueUN");
        user.setRequesterRating(10.0);
        user.setProviderRating(10.0);
        user.setEmail(new EmailAddress("test3@cmput301.com"));
        user.setPhone(new PhoneNumber("1111"));

        AddUserRequest test2 = new AddUserRequest(user);
        RequestManager.getInstance().invokeRequest(ctx, test2);

        assertTrue(test2.getResult());
    }

    public void testAddTaskRequest() {
        Task task = new Task();
        task.setName("cmput301 number 2 project");

        //AddTaskRequest addTaskRequest = new AddTaskRequest(task);
        //RequestManager.getInstance().invokeRequest(ctx, addTaskRequest);

        // add a task with some desc real quick
        Task mockTask = new Task();
        User mockUser = new User();
        mockUser.setName("Linus Torvalds");
        mockUser.setEmail(new EmailAddress("linus@kernal.org"));

        Bid mockBid = new Bid(mockUser.getId(), mockTask.getId(), 1000.0f);

        mockTask.setName("MockTask");
        mockTask.addBid(mockBid);
        mockTask.setDescription(
                "cite: http://wikipedia.com -- C (/siː/, as in the letter c) is a general-purpose, imperative computer programming language, supporting structured programming, lexical variable scope and recursion, while a static type system prevents many unintended operations. By design, C provides constructs that map efficiently to typical machine instructions, and therefore it has found lasting use in applications that had formerly been coded in assembly language, including operating systems, as well as various application software for computers ranging from supercomputers to embedded systems."
        );
        mockTask.setTaskProvider(mockUser);

        AddTaskRequest addTaskRequest = new AddTaskRequest(mockTask);
        RequestManager.getInstance().invokeRequest(ctx, addTaskRequest);
    }

    public void testSearchTaskRequest() {

        String keywords = "imperative computer programming";

        SearchTaskRequest request = new SearchTaskRequest(keywords);
        RequestManager.getInstance().invokeRequest(ctx, request);

        //assertNotNull(request.getTasks());

        System.out.println("------- Search output -------");
        System.out.println("******************************");

        ArrayList<Task> ret = request.getTasks();
        System.out.println(ret);

        for (Task t : ret) {
            System.out.println(t);
            Log.i("Found Task", t.getId());
        }
    }

    public void testGetTaskRequest() {
        Task mockTask = new Task();
        mockTask.setName("testing get task");
        mockTask.setDescription("testing get task");

        AddTaskRequest addTaskRequest = new AddTaskRequest(mockTask);
        RequestManager.getInstance().invokeRequest(ctx,addTaskRequest);

        while (mockTask.getId() == null);
        String id = mockTask.getId();

        GetTaskRequest getTaskRequest = new GetTaskRequest(id);
        RequestManager.getInstance().invokeRequest(ctx,getTaskRequest);

        Task ret = getTaskRequest.getResult();
        Log.i("Event", "RETURNED TASK: "+ret.toString());
        assertEquals(id, getTaskRequest.getResult().getId());
    }
/*
    public void testRemoveTask() {
        Task mockTask = new Task();
        mockTask.setName("testing remove task");
        mockTask.setDescription("testing remove task");

        AddTaskRequest addTaskRequest = new AddTaskRequest(mockTask);
        RequestManager.getInstance().invokeRequest(ctx,addTaskRequest);

        String id = mockTask.getId();

        RemoveTaskRequest request = new RemoveTaskRequest(id);
        RequestManager.getInstance().invokeRequest(ctx,request);
    }
*/
    public void testGetAllTasks() {
        GetAllTasksRequest getAllTasksRequest = new GetAllTasksRequest();
        RequestManager.getInstance().invokeRequest(ctx,getAllTasksRequest);

        ArrayList<Task> res = getAllTasksRequest.getResult();
        assertNotNull(res);
        assertTrue(res.size() > 0);

        for (Task t : res) {
            Log.i("FOUND TASK: ", t.getId()+" "+t.getName());
        }
    }
}
