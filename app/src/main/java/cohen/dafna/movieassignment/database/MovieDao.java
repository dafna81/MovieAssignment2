package cohen.dafna.movieassignment.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movies")
    LiveData<List<MovieDB>> getAll();

    @Query("SELECT * FROM movies")
    List<MovieDB> getAllMovies();

    @Query("SELECT * FROM movies WHERE `favorite` =1")
    LiveData<List<MovieDB>> getFavoriteMovies();

    @Insert
    void insert(MovieDB movieDB);

    @Query("UPDATE movies SET `favorite` = :favorite WHERE title = :movieTitle AND id = :movieId")
    void setFavorite(String movieTitle, int movieId, int favorite);

//    @Query("SELECT * FROM movies WHERE title Like :movieTitle")
//    MovieDB getMovieByTitle(String movieTitle);
}
