package cohen.dafna.movieassignment.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesList {
    @SerializedName("results")
    private List<Movie> movies;

    public MoviesList(List<Movie> movies) {
        this.movies = movies;
    }

    public MoviesList() {
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
