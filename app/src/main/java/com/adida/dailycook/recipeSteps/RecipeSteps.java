package com.adida.dailycook.recipeSteps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.adida.dailycook.R;
import com.adida.dailycook.retrofit2.entities.RecipeDetail;
import com.adida.dailycook.retrofit2.entities.RecipeStep;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecipeSteps extends AppCompatActivity {

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
    List<Integer> stepsTimeLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_recipe_steps);

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



        steps = new ArrayList<>();
        RecipeStep step = new RecipeStep();
        step.setStep_id(1);
        step.setDuration_minute(30);
        step.setStep_description("description");
        step.setStep_Order(3);
        step.setStep_image_url("https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/800px-Image_created_with_a_mobile_phone.png");

        RecipeStep step1 = new RecipeStep();
        step1.setStep_id(1);
        step1.setDuration_minute(30);
        step1.setStep_description("description");
        step1.setStep_Order(3);
        step1.setStep_image_url("https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/800px-Image_created_with_a_mobile_phone.png");
        steps.add(step);
        steps.add(step1);

        for(int i=0; i< steps.size();i++){
            stepsTimeLeft.add(steps.get(i).getDuration_minute()*1000);
        }

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
        final int currentStepTimeLeft = stepsTimeLeft.get(page);

        durationCountDown = new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                txtStepDuration.setText("" + currentStepTimeLeft);
            }

            public void onFinish() {
                txtStepDuration.setText("done!");
            }

        };

        durationCountDown.start();
        txtStepNumber.setText(Integer.toString(viewPager.getCurrentItem()));

        if(IsVoiceOn){
            textToSpeech.speak(currentStep.getStep_description(), TextToSpeech.QUEUE_FLUSH, null);
        }
    }

}