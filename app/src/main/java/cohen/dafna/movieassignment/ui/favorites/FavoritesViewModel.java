package cohen.dafna.movieassignment.ui.favorites;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import cohen.dafna.movieassignment.database.DBManager;
import cohen.dafna.movieassignment.database.MovieDB;
import cohen.dafna.movieassignment.networking.MovieDatasource;

public class FavoritesViewModel extends AndroidViewModel {

    private final MutableLiveData<List<MovieDB>> movies = new MutableLiveData<>();
    private final MutableLiveData<Throwable> exc = new MutableLiveData<>();
    private final MutableLiveData<MovieDB> clickedMovie = new MutableLiveData<>();


    public FavoritesViewModel(@NonNull Application application) {
        super(application);
        DBManager manager = new DBManager(application.getApplicationContext());
        MovieDatasource.getSharedInstance().getMovieList(movies, manager, exc);
    }

    public MutableLiveData<List<MovieDB>> getMovies() {
        return movies;
    }

    public MutableLiveData<MovieDB> getClickedMovie() {
        return clickedMovie;
    }
}