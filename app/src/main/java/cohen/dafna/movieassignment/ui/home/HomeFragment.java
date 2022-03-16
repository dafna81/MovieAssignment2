package cohen.dafna.movieassignment.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import cohen.dafna.movieassignment.R;
import cohen.dafna.movieassignment.database.MovieDB;
import cohen.dafna.movieassignment.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeViewModel.getMovies().observe(getViewLifecycleOwner(), movieDBS -> {
            MovieAdapter adapter = new MovieAdapter(movieDBS, homeViewModel.getClickedMovie());
            binding.recyclerView.setAdapter(adapter);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        });

        homeViewModel.getClickedMovie().observe(getViewLifecycleOwner(), movieDB -> {
            Bundle args = new Bundle();
            args.putParcelable("movie", movieDB);
            if (movieDB == null) {
                System.out.println("movieDB is null");
                return;
            }
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_navigation_home_to_movieDetailsFragment, args);
            homeViewModel.getClickedMovie().postValue(null);
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}