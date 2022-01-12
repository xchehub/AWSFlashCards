package com.erranddaddy.awsflashcards.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erranddaddy.awsflashcards.R;
import com.erranddaddy.awsflashcards.fab.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;

public class FragmentCategories extends DefaultFragment  {
    /**
     * Declare the Instance variables that will be used by this fragment
     */
    private List<Card> mCardsList;
    private CardListView mCategoriesList;
    private CardArrayAdapter mCardAdapter;
    private FloatingActionButton buttonFab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Have specific ActionBar actions/icons for this fragment
        this.setHasOptionsMenu(true);
        // Return the inflated view to be used by onViewCreated.
        // 1st argument: the XML layout to be inflated
        // 2nd argument: the root to which this layout is being attached.
        // 3rd argument we specify if we want the fragment to be attached to it's root.
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Initialize the FloatingActionButton and set it's colors
        buttonFab = (FloatingActionButton) view.findViewById(R.id.addNewCategory);
        buttonFab.setColor(getResources().getColor(R.color.action_bar_color));
        buttonFab.setTextColor(getResources().getColor(R.color.action_bar_text_color));

        // We initialize the CardsList and add some Dummy Data for now.
        // We will come back to this point to add our Custom Card matching our App UI as well with real data from a Database.
        mCategoriesList = (CardListView) view.findViewById(R.id.categoriesList);
        mCardsList = new ArrayList<Card>();
        for (int i = 0; i < 10; i++) {
            Card card = new Card(getActivity());
            card.setTitle("Category " + i);
            mCardsList.add(card);
        }
        mCardAdapter = new CardArrayAdapter(getActivity(), mCardsList);
        mCardAdapter.setEnableUndo(true);
        mCategoriesList.setAdapter(mCardAdapter);
    }
}
