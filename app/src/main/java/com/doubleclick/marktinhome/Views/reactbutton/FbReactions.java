package com.doubleclick.marktinhome.Views.reactbutton;

import android.widget.ArrayAdapter;

import com.doubleclick.marktinhome.R;

import java.util.ArrayList;

public final class FbReactions {

    public static Reaction defaultReact = new Reaction(
            ReactConstants.LIKE,
            ReactConstants.DEFAULT,
            ReactConstants.BLUE,
            R.drawable.ic_like);

    //    public static Reaction[] reactions = {
//            new Reaction(ReactConstants.LIKE, ReactConstants.BLUE, R.drawable.ic_like_circle),
//            new Reaction(ReactConstants.LOVE, ReactConstants.RED_LOVE, R.drawable.ic_heart),
//            new Reaction(ReactConstants.SMILE, ReactConstants.YELLOW_WOW, R.drawable.ic_happy),
//            new Reaction(ReactConstants.WOW, ReactConstants.YELLOW_WOW, R.drawable.ic_surprise),
//            new Reaction(ReactConstants.SAD, ReactConstants.YELLOW_HAHA, R.drawable.ic_sad),
//            new Reaction(ReactConstants.ANGRY, ReactConstants.RED_ANGRY, R.drawable.ic_angry),
//    };
    public static ArrayList<Reaction> Reactions() {
        ArrayList<Reaction> reactions = new ArrayList<>();
        reactions.add(new Reaction(ReactConstants.LIKE, ReactConstants.BLUE, R.drawable.ic_like_circle));
        reactions.add(new Reaction(ReactConstants.LOVE, ReactConstants.RED_LOVE, R.drawable.ic_heart));
        reactions.add(new Reaction(ReactConstants.SMILE, ReactConstants.YELLOW_WOW, R.drawable.ic_happy));
        reactions.add(new Reaction(ReactConstants.WOW, ReactConstants.YELLOW_WOW, R.drawable.ic_surprise));
        reactions.add(new Reaction(ReactConstants.SAD, ReactConstants.YELLOW_HAHA, R.drawable.ic_sad));
        reactions.add(new Reaction(ReactConstants.ANGRY, ReactConstants.RED_ANGRY, R.drawable.ic_angry));
        return reactions;
    }
}
