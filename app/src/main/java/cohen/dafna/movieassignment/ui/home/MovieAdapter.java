package cohen.dafna.movieassignment.ui.home;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cohen.dafna.movieassignment.R;
import cohen.dafna.movieassignment.database.MovieDB;
import cohen.dafna.movieassignment.databinding.MovieItemBinding;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.VH> {
    private final List<MovieDB> movies;
    private final MutableLiveData<MovieDB> clickedMovie;

    public MovieAdapter(List<MovieDB> movies, MutableLiveData<MovieDB> clickedMovie) {
        this.movies = movies;
        this.clickedMovie = clickedMovie;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MovieItemBinding binding = MovieItemBinding.inflate(inflater, parent, false);
        return new VH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        MovieDB movieDB = movies.get(position);
        holder.binding.titleTextView.setText(movieDB.getTitle());
        Picasso.get().load(Uri.parse("https://www.themoviedb.org/t/p/w1280" + movieDB.getPosterPath()))
                .into(holder.binding.movieImageView);
        if (movieDB.isFavorite()) {
            holder.binding.favoriteImageView.setVisibility(View.VISIBLE);
        }
        holder.binding.getRoot().setOnClickListener(v -> {
            clickedMovie.postValue(movieDB);
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class VH extends RecyclerView.ViewHolder {
        MovieItemBinding binding;

        public VH(MovieItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
