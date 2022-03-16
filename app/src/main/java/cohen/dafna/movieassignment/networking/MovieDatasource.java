package cohen.dafna.movieassignment.networking;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import cohen.dafna.movieassignment.database.DBManager;
import cohen.dafna.movieassignment.database.MovieDB;
import cohen.dafna.movieassignment.model.Movie;
import cohen.dafna.movieassignment.model.MoviesList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDatasource {
    private static MovieDatasource sharedInstance;
    private final String API_KEY = "2c46288716a18fb7aadcc2a801f3fc6b";

    private MovieDatasource() {
    }

    public synchronized static MovieDatasource getSharedInstance() {
        if (sharedInstance == null) {
            sharedInstance = new MovieDatasource();
        }
        return sharedInstance;
    }

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    MovieService service = retrofit.create(MovieService.class);

    public void getMovieList(MutableLiveData<List<MovieDB>> callback, DBManager manager,
                             MutableLiveData<Throwable> exCallback) {
        manager.getAllMovies(movieDBS -> {
            if (!movieDBS.isEmpty()) {
                callback.postValue(movieDBS);
            } else {
                List<MovieDB> allMovies = new ArrayList<>();
                service.getPopularMovies(API_KEY).enqueue(new Callback<MoviesList>() {
                    @Override
                    public void onResponse(Call<MoviesList> call, Response<MoviesList> response) {
                        MoviesList moviesList = response.body();
                        if (moviesList == null || moviesList.getMovies() == null) {
                            System.out.println("MoviesList is null");
                            return;
                        }
                        for (Movie movie : moviesList.getMovies()) {
                            MovieDB movieDB = new MovieDB(movie);
                            allMovies.add(movieDB);
                            manager.insertMovie(movieDB);
                        }
                        callback.postValue(allMovies);
                    }

                    @Override
                    public void onFailure(Call<MoviesList> call, Throwable t) {
                        exCallback.postValue(t);
                    }
                });
            }
        });
    }

//    public void getCurrentMovie(MutableLiveData<MovieDB> callback) {
//        callback.postValue();
//    }
}
