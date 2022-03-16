package cohen.dafna.movieassignment.ui.home;

import androidx.lifecycle.ViewModelProvider;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import cohen.dafna.movieassignment.R;
import cohen.dafna.movieassignment.database.MovieDB;
import cohen.dafna.movieassignment.databinding.MovieDetailsFragmentBinding;

public class MovieDetailsFragment extends Fragment {

    private MovieDetailsViewModel viewModel;
    private MovieDetailsFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        viewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);

        binding = MovieDetailsFragmentBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() == null) {
            throw new RuntimeException("No arguments found");
        }
        MovieDB movieDB = getArguments().getParcelable("movie");
        binding.detailsMovieTitleTextView.setText(movieDB.getTitle());
        binding.detailsReleaseDateTextView.setText(movieDB.getReleaseDate());
        Picasso.get().load(Uri.parse("https://www.themoviedb.org/t/p/w1280" + movieDB.getPosterPath()))
                .into(binding.detailsMovieImageView);
        binding.detailsMovieOverviewTextView.setText(movieDB.getOverview());
        if (movieDB.isFavorite()) {
            binding.detailsFavoriteImageView.setImageResource(R.drawable.ic_baseline_star_24);
        } else {
            binding.detailsFavoriteImageView.setImageResource(R.drawable.ic_baseline_star_outline_24);
        }
        viewModel.getCurrentMovie().postValue(movieDB);


//        viewModel.getCurrentMovie().observe(getViewLifecycleOwner(), movieDB -> {
//                    binding.detailsMovieTitleTextView.setText(movieDB.getTitle());
//                    binding.detailsReleaseDateTextView.setText(movieDB.getReleaseDate());
//                    Picasso.get().load(Uri.parse(movieDB.getPosterPath()))
//                            .into(binding.detailsMovieImageView);
//                    binding.detailsMovieOverviewTextView.setText(movieDB.getOverview());
//                    if (movieDB.isFavorite()) {
//                        binding.detailsFavoriteImageView.setImageResource(R.drawable.ic_baseline_star_24);
//                    } else {
//                        binding.detailsFavoriteImageView.setImageResource(R.drawable.ic_baseline_star_outline_24);
//                    }
//                }
//        );

        binding.detailsFavoriteImageView.setOnClickListener(v -> {
            if (viewModel.getCurrentMovie().getValue() != null) {
                if (viewModel.getCurrentMovie().getValue().isFavorite()) {
                    binding.detailsFavoriteImageView.setImageResource(R.drawable.ic_baseline_star_outline_24);
                    viewModel.getCurrentMovie().getValue().setAsFavorite(false);
                } else {
                    binding.detailsFavoriteImageView.setImageResource(R.drawable.ic_baseline_star_24);
                    viewModel.getCurrentMovie().getValue().setAsFavorite(true);
                }
                viewModel.updateMovie(viewModel.getCurrentMovie().getValue());
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}