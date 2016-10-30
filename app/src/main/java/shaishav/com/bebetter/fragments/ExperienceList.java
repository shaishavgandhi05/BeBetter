package shaishav.com.bebetter.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import shaishav.com.bebetter.data.models.Experience;
import shaishav.com.bebetter.data.source.ExperienceSource;
import shaishav.com.bebetter.adapters.ExperienceRecyclerViewAdapter;
import shaishav.com.bebetter.data.providers.ExperienceProvider;
import shaishav.com.bebetter.R;

import java.util.List;


public class ExperienceList extends Fragment {

    // TODO: Customize parameters
    private int mColumnCount = 1;

    ExperienceSource experienceSource;
    public List<Experience> experienceList;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ExperienceList() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lesson_list, container, false);

        //Get the lesson source
        experienceSource = new ExperienceSource(getActivity().getApplicationContext());

        //Get all lessons

        Cursor cursor = getActivity().getContentResolver().query(ExperienceProvider.CONTENT_URI, null, null, null, null);
        experienceList = ExperienceProvider.cursorToExperienceList(cursor);
        cursor.close();

        // Set the adapter
        if (view instanceof LinearLayout) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
            LinearLayout emptyView = (LinearLayout)view.findViewById(R.id.emptyView);
            if(experienceList.size()==0){
                recyclerView.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);

            }
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new ExperienceRecyclerViewAdapter(experienceList));
        }
        return view;
    }




}