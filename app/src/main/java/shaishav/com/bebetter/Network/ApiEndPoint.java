package shaishav.com.bebetter.Network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import shaishav.com.bebetter.Data.Models.Lesson;
import shaishav.com.bebetter.Data.Models.User;

/**
 * Created by Shaishav on 9/6/2016.
 */
public interface ApiEndPoint {

    @POST("users/")
    Call<User> loginUser(@Body User user);

    @POST("lessons/")
    Call<Lesson> createLesson(@Body Lesson lesson);

    @GET("lessons/")
    Call<List<Lesson>> getLessons();
}
