package cohen.dafna.movieassignment.database;

import android.content.Context;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class DBManager {
    private final MovieDao movieDao;

    public DBManager(Context context) {
        movieDao = AppDatabase.getInstance(context).movieDao();
    }

    private final ExecutorService service = Executors.newSingleThreadExecutor();

    public void insertMovie(MovieDB movieDB) {
        service.execute(new MovieInsertionTask(movieDB));
    }

    public void updateMovie(MovieDB movieDB) {
        service.execute(new MovieUpdateTask(movieDB));
    }

    public void getAllMovies(Consumer<List<MovieDB>> consumer) {
        new Thread(() -> {
            List<MovieDB> movies = movieDao.getAllMovies();
            consumer.accept(movies);
        }).start();
    }

    class MovieInsertionTask implements Runnable {
        MovieDB movieDB;
        public MovieInsertionTask(MovieDB movieDB) {
            this.movieDB = movieDB;
        }
        @Override
        public void run() {
            movieDao.insert(movieDB);
        }
    }

    class MovieUpdateTask implements Runnable {
        MovieDB movieDB;
        public MovieUpdateTask(MovieDB movieDB) {
            this.movieDB = movieDB;
        }
        @Override
        public void run() {
            movieDao.setFavorite(movieDB.getTitle(), movieDB.getId(), movieDB.isFavorite() ? 1 : 0);
        }
    }
}
