package cohen.dafna.movieassignment.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import cohen.dafna.movieassignment.R;
import cohen.dafna.movieassignment.database.MovieDB;
import cohen.dafna.movieassignment.databinding.FragmentFavoritesBinding;


public class FavoritesFragment extends Fragment {

    private FavoritesViewModel favoritesViewModel;
    private FragmentFavoritesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoritesViewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favoritesViewModel.getMovies().observe(getViewLifecycleOwner(), movieDBS -> {
            List<MovieDB> favoriteMovies = new ArrayList<>();
            for (MovieDB movieDB : movieDBS) {
                if (movieDB.isFavorite()) {
                    favoriteMovies.add(movieDB);
                }
            }
            FavoritesAdapter adapter = new FavoritesAdapter(favoriteMovies, favoritesViewModel.getClickedMovie());
            binding.recyclerView.setAdapter(adapter);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        });

        favoritesViewModel.getClickedMovie().observe(getViewLifecycleOwner(), movieDB -> {
            Bundle args = new Bundle();
            args.putParcelable("movie", movieDB);
            if (movieDB == null) {
                System.out.println("NULL favorite");
                return;
            }
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_navigation_favorites_to_movieDetailsFragment, args);
            favoritesViewModel.getClickedMovie().postValue(null);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}