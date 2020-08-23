package com.adida.dailycook.recipeSteps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Window;

import com.adida.dailycook.R;
import com.adida.dailycook.retrofit2.entities.RecipeStep;

import java.util.ArrayList;
import java.util.List;

public class RecipeSteps extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_recipe_steps);

        List<RecipeStep> steps = new ArrayList<>();

        steps.add(new RecipeStep(1,"Combine the water, yeast, salt, and sugar. Place all four ingredients in a small mixing bowl and stir gently to combine.",35,"https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1280px-Image_created_with_a_mobile_phone.png",0));
        steps.add(new RecipeStep(1,"Combine the water, yeast, salt, and sugar. Place all four ingredients in a small mixing bowl and stir gently to combine.",35,"https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1280px-Image_created_with_a_mobile_phone.png",0));
        steps.add(new RecipeStep(1,"Combine the water, yeast, salt, and sugar. Place all four ingredients in a small mixing bowl and stir gently to combine.",35,"https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1280px-Image_created_with_a_mobile_phone.png",0));
        steps.add(new RecipeStep(1,"Combine the water, yeast, salt, and sugar. Place all four ingredients in a small mixing bowl and stir gently to combine.",35,"https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1280px-Image_created_with_a_mobile_phone.png",0));
        steps.add(new RecipeStep(1,"Combine the water, yeast, salt, and sugar. Place all four ingredients in a small mixing bowl and stir gently to combine.",35,"https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1280px-Image_created_with_a_mobile_phone.png",0));
        steps.add(new RecipeStep(1,"Combine the water, yeast, salt, and sugar. Place all four ingredients in a small mixing bowl and stir gently to combine.",35,"https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1280px-Image_created_with_a_mobile_phone.png",0));
        steps.add(new RecipeStep(1,"Combine the water, yeast, salt, and sugar. Place all four ingredients in a small mixing bowl and stir gently to combine.",35,"https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1280px-Image_created_with_a_mobile_phone.png",0));
//        steps.add(new RecipeStep(2,"12sdfsdf3",67,"imgUrl",1));
//        steps.add(new RecipeStep(3,"12sdfsdf3",39,"imgUrl",2));
//        steps.add(new RecipeStep(4,"123sdfsdf",34,"imgUrl",3));
//        steps.add(new RecipeStep(5,"12sdfsdf3",30,"imgUrl",4));
//        steps.add(new RecipeStep(6,"123sdfsdf",32,"imgUrl",5));

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this,steps));
    }
}