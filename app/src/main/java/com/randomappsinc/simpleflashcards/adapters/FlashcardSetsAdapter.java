package com.randomappsinc.simpleflashcards.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.randomappsinc.simpleflashcards.R;
import com.randomappsinc.simpleflashcards.activities.EditFlashcardSetActivity;
import com.randomappsinc.simpleflashcards.activities.StudyModeActivity;
import com.randomappsinc.simpleflashcards.persistence.DatabaseManager;
import com.randomappsinc.simpleflashcards.persistence.models.FlashcardSet;
import com.randomappsinc.simpleflashcards.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FlashcardSetsAdapter extends BaseAdapter {

    private Context context;
    private List<FlashcardSet> flashcardSets;
    private TextView noSets;

    public FlashcardSetsAdapter(Context context, TextView noSets) {
        this.context = context;
        this.flashcardSets = new ArrayList<>();
        this.noSets = noSets;
    }

    public void refreshContent(String searchTerm) {
        flashcardSets.clear();
        flashcardSets.addAll(DatabaseManager.get().getFlashcardSets(searchTerm));
        setNoContent();
        notifyDataSetChanged();
    }

    private void setNoContent() {
        int viewVisibility = flashcardSets.isEmpty() ? View.VISIBLE : View.GONE;
        noSets.setVisibility(viewVisibility);
    }

    public int getCount() {
        return flashcardSets.size();
    }

    @Override
    public FlashcardSet getItem(int position) {
        return flashcardSets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public class FlashcardSetViewHolder {
        @BindView(R.id.set_name) TextView setName;
        @BindView(R.id.num_flashcards) TextView numFlashcards;

        private int position;

        FlashcardSetViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        void loadFlashcardSet(int position) {
            this.position = position;
            FlashcardSet flashcardSet = flashcardSets.get(position);
            setName.setText(flashcardSet.getName());
            numFlashcards.setText(String.valueOf(flashcardSet.getFlashcards().size()));
        }

        @OnClick(R.id.browse_button)
        public void browseFlashcards() {
            Intent intent = new Intent(context, StudyModeActivity.class)
                    .putExtra(Constants.FLASHCARD_SET_ID_KEY, getItem(position).getId());
            context.startActivity(intent);
        }

        @OnClick(R.id.quiz_button)
        public void takeQuiz() {

        }

        @OnClick(R.id.edit_button)
        public void editFlashcardSet() {
            Intent intent = new Intent(context, EditFlashcardSetActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            intent.putExtra(Constants.FLASHCARD_SET_ID_KEY, getItem(position).getId());
            context.startActivity(intent);
        }

        @OnClick(R.id.delete_button)
        public void deleteFlashcardSet() {
        }
    }

    // Renders the ListView item that the user has scrolled to or is about to scroll to
    public View getView(final int position, View view, ViewGroup parent) {
        FlashcardSetViewHolder holder;
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.flashcard_set_cell, parent, false);
            holder = new FlashcardSetViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (FlashcardSetViewHolder) view.getTag();
        }
        holder.loadFlashcardSet(position);
        return view;
    }
}
