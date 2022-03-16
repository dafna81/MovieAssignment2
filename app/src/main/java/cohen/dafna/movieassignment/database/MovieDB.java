package cohen.dafna.movieassignment.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import cohen.dafna.movieassignment.model.Movie;

@Entity(tableName = "movies")
public class MovieDB implements Parcelable {
    @PrimaryKey()
    private int id;

    private int favorite;
    private String title;
    private String overview;
    private String posterPath;
    private String releaseDate;

    public MovieDB() {
    }

    public MovieDB(Movie movie) {
        id = movie.getId();
        title = movie.getTitle();
        overview = movie.getOverview();
        posterPath = movie.getPosterPath();
        releaseDate = movie.getReleaseDate();
    }

    public int getId() {
        return id;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFavorite() {
        return favorite == 1;
    }

    public void setAsFavorite(boolean favorite) {
        this.favorite = favorite ? 1 : 0;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    protected MovieDB(Parcel in) {
        id = in.readInt();
        favorite = in.readInt();
        title = in.readString();
        overview = in.readString();
        posterPath = in.readString();
        releaseDate = in.readString();
    }

    public static final Creator<MovieDB> CREATOR = new Creator<MovieDB>() {
        @Override
        public MovieDB createFromParcel(Parcel in) {
            return new MovieDB(in);
        }

        @Override
        public MovieDB[] newArray(int size) {
            return new MovieDB[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(favorite);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(posterPath);
        dest.writeString(releaseDate);
    }
}
