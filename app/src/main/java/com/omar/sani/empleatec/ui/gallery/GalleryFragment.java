package com.omar.sani.empleatec.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.omar.sani.empleatec.R;
import com.omar.sani.empleatec.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private ImageView profilePicture;
    private TextView userName, userBio, contactInfoTitle, userEmail, userPhone;
    private TextView educationTitle, educationDetails, workExperienceTitle, workExperienceDetails;
    private TextView skillsTitle, skillsDetails, projectsTitle, projectsDetails;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize UI components
        profilePicture = binding.getRoot().findViewById(R.id.profile_picture);
        userName = binding.getRoot().findViewById(R.id.user_name);
        userBio = binding.getRoot().findViewById(R.id.user_bio);
        contactInfoTitle = binding.getRoot().findViewById(R.id.contact_info_title);
        userEmail = binding.getRoot().findViewById(R.id.user_email);
        userPhone = binding.getRoot().findViewById(R.id.user_phone);
        educationTitle = binding.getRoot().findViewById(R.id.education_title);
        educationDetails = binding.getRoot().findViewById(R.id.education_details);
        workExperienceTitle = binding.getRoot().findViewById(R.id.work_experience_title);
        workExperienceDetails = binding.getRoot().findViewById(R.id.work_experience_details);
        skillsTitle = binding.getRoot().findViewById(R.id.skills_title);
        skillsDetails = binding.getRoot().findViewById(R.id.skills_details);
        projectsTitle = binding.getRoot().findViewById(R.id.projects_title);
        projectsDetails = binding.getRoot().findViewById(R.id.projects_details);

        // Example of setting text programmatically
        // You can remove or modify this part as needed

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
