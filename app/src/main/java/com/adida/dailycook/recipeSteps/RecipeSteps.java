package com.adida.dailycook.recipeSteps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.adida.dailycook.R;
import com.adida.dailycook.retrofit2.entities.RecipeDetail;
import com.adida.dailycook.retrofit2.entities.RecipeDetailSteps;
import com.adida.dailycook.retrofit2.entities.RecipeStep;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecipeSteps extends AppCompatActivity {

    public static void startActivity(Activity context, RecipeDetailSteps detail){
        Intent intent = new Intent(context,RecipeSteps.class);
        Bundle extras = new Bundle();

        Serializable serializable = (Serializable)detail;
        extras.putSerializable("detail", serializable);
        intent.putExtras(extras);
        context.startActivity(intent);
    }

    TextView txtStepDuration;
    TextView txtStepNumber;
    Button btnCloseRecipeSteps;
    Button btnStepVoice;
    Button btnPreviousRecipeSteps;
    Button btnNextRecipeSteps;
    ViewPager viewPager;
    boolean IsVoiceOn;
    CountDownTimer durationCountDown;
    TextToSpeech textToSpeech;
    List<RecipeStep> steps;
    RecipeDetailSteps detail;
    //List<Integer> stepsTimeLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_recipe_steps);

        detail = (RecipeDetailSteps) getIntent().getExtras().getSerializable("detail");


        txtStepDuration = (TextView) findViewById(R.id.stepDuration);
        txtStepNumber = (TextView)findViewById(R.id.stepNumber);
        btnStepVoice = (Button) findViewById(R.id.btnStepVoice);
        btnPreviousRecipeSteps = (Button)findViewById(R.id.stepPrevious);
        btnNextRecipeSteps = (Button)findViewById(R.id.stepNext);
        btnCloseRecipeSteps = (Button)findViewById(R.id.closeRecipeSteps);


        IsVoiceOn = true;
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
                    int lang = textToSpeech.setLanguage(new Locale("vi"));
                }
            }
        });


        btnCloseRecipeSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnStepVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ToggleVoice() && IsVoiceOn){
                    textToSpeech.speak(steps.get(viewPager.getCurrentItem()).getStep_description(), TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });



        steps = detail.getSteps();
        RecipeStep step = new RecipeStep();
        step.setStep_id(1);
        step.setDuration_minute(0);
        step.setStep_Order(3);
        step.setStep_image_url("https://tr.rbxcdn.com/f06e365c9d4f67e60ae2bff2f9e7fa46/420/420/Decal/Png");

        steps.add(step);

//        stepsTimeLeft = new ArrayList<>();
//        for(int i=0; i< steps.size();i++){
//            stepsTimeLeft.add(steps.get(i).getDuration_minute()*1000);
//        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this,steps));

        btnPreviousRecipeSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
            }
        });

        btnNextRecipeSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            }
        });

        OnPageChange(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            public void onPageSelected(int position) {
                OnPageChange(position);
            }

        });
    }

    public boolean ToggleVoice(){
        boolean isSuccess = false;

        if(IsVoiceOn){
            //Turn off

            //If turned off
            IsVoiceOn = false;
            isSuccess = true;
            btnStepVoice.setText("VOICE OFF");
        }
        else{
            //Turn off

            //If turned On
            IsVoiceOn = true;
            isSuccess = true;
            btnStepVoice.setText("VOICE");
        }

        return isSuccess;
    }

    public void OnPageChange(int page){
        if(durationCountDown != null){
            durationCountDown.cancel();
        }

        final RecipeStep currentStep = steps.get(page);
        //final int currentStepTimeLeft = stepsTimeLeft.get(page);

        final int nextTimeout = 3000;

        durationCountDown = new CountDownTimer(currentStep.getDuration_minute()*60*1000 + nextTimeout, 1000) {

            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished - nextTimeout >= 0){
                    long second = (millisUntilFinished - nextTimeout)/1000;

                    long numberOfMinutes = ((second % 86400 ) % 3600 ) / 60;
                    long numberOfSeconds = ((second % 86400 ) % 3600 ) % 60;

                    txtStepDuration.setText("" + numberOfMinutes + ":" + numberOfSeconds );
                }
                else{
                    //Set timeout
                    txtStepDuration.setText("");
                }
            }

            public void onFinish() {
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            }

        };

        durationCountDown.start();
        txtStepNumber.setText(Integer.toString(viewPager.getCurrentItem() + 1) + "/" + Integer.toString(steps.size()));

        if(IsVoiceOn){
            textToSpeech.speak(currentStep.getStep_description(), TextToSpeech.QUEUE_FLUSH, null);
        }
    }

}