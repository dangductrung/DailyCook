package com.adida.dailycook.recipeSteps;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.adida.dailycook.R;
import com.adida.dailycook.retrofit2.entities.RecipeStep;
import com.squareup.picasso.Picasso;
import java.util.List;

public class CustomPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<RecipeStep> recipeSteps;

    public CustomPagerAdapter(Context context,List<RecipeStep> mRecipeSteps) {
        mContext = context;
        recipeSteps = mRecipeSteps;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        RecipeStep step = recipeSteps.get(position);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.layout_recipe_step, collection, false);

        TextView txtDescription = layout.findViewById(R.id.stepDescription);
        txtDescription.setText(step.getStep_description());
        txtDescription.setMovementMethod(new ScrollingMovementMethod());

        ImageView imgStepImg = layout.findViewById(R.id.stepImage);
        imgStepImg.setScaleType(ImageView.ScaleType.FIT_XY);

        String imgUrl = step.getStep_image_url();
        if(imgUrl != null && !imgUrl.isEmpty()){
            Picasso.get().load(step.getStep_image_url()).resize(100,100).into(imgStepImg);
        }

        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return recipeSteps.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence cs = "";

        return cs;
    }

}
