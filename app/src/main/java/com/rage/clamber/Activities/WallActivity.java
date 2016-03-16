package com.rage.clamber.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

import com.rage.clamber.Fragments.ActionBarFragment;
import com.rage.clamber.Fragments.WallSectionFragment;
import com.rage.clamber.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * The WallActivity contains data about each wall in the climbing gym.
 */
public class WallActivity extends AppCompatActivity {

    @Bind(R.id.wall_page_wall_1_button)
    ImageButton wall1Button;
    @Bind(R.id.wall_page_wall_2_button)
    ImageButton wall2Button;
    @Bind(R.id.wall_page_wall_3_button)
    ImageButton wall3Button;
    @Bind(R.id.wall_page_wall_4_button)
    ImageButton wall4Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall);
        ButterKnife.bind(this);

        //Sets ActionBar to top of activity screen.
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.wall_page_frame_layout, ActionBarFragment.newInstance());
        transaction.commit();

        //Sets the images for the image buttons.

        Picasso.with(this).load(R.drawable.wall_1).fit().centerCrop().into(wall1Button);
        Picasso.with(this).load(R.drawable.wall_2).fit().centerCrop().into(wall2Button);
        Picasso.with(this).load(R.drawable.wall_3).fit().centerCrop().into(wall3Button);
        Picasso.with(this).load(R.drawable.wall_4).fit().centerCrop().into(wall4Button);

    }

    /**
     * OnClick listeners for each wall button. When clicked, the WallSectionFragment will launch
     * with the appropriate wall's data.
     */

    @OnClick(R.id.wall_page_wall_1_button)
    public void onWall1Click(ImageButton button) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.wall_page_linear_layout, WallSectionFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();
        //TODO: Going to need to pass through the Wall number so the appropriate wall sections will display.

    }

    @OnClick(R.id.wall_page_wall_2_button)
    public void onWall2Click(ImageButton button) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.wall_page_linear_layout, WallSectionFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();
        //TODO: Going to need to pass through the Wall number so the appropriate wall sections will display.
    }

    @OnClick(R.id.wall_page_wall_3_button)
    public void onWall3Click(ImageButton button) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.wall_page_linear_layout, WallSectionFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();
        //TODO: Going to need to pass through the Wall number so the appropriate wall sections will display.
    }

    @OnClick(R.id.wall_page_wall_4_button)
    public void onWall4Click(ImageButton button) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.wall_page_linear_layout, WallSectionFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();
        //TODO: Going to need to pass through the Wall number so the appropriate wall sections will display.
    }


}
