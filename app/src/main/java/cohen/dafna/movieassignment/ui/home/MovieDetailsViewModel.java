package cohen.dafna.movieassignment.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import cohen.dafna.movieassignment.database.DBManager;
import cohen.dafna.movieassignment.database.MovieDB;
import cohen.dafna.movieassignment.networking.MovieDatasource;


public class MovieDetailsViewModel extends AndroidViewModel {
    private final MutableLiveData<MovieDB> currentMovie = new MutableLiveData<>();
    private final MutableLiveData<List<MovieDB>> movies = new MutableLiveData<>();
    private final MutableLiveData<Throwable> exc  = new MutableLiveData<>();
    private final DBManager dbManager;

    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);
        dbManager = new DBManager(application.getApplicationContext());
        MovieDatasource.getSharedInstance().getMovieList(movies, dbManager, exc);
    }

    public MutableLiveData<MovieDB> getCurrentMovie() {
        return currentMovie;
    }

    public void updateMovie(MovieDB movieDB) {
        dbManager.updateMovie(movieDB);
    }

    public MutableLiveData<List<MovieDB>> getMovies() {
        return movies;
    }
}