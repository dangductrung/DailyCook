package com.adida.dailycook.Upload.Fragment.Upload.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.adida.dailycook.Upload.Fragment.Upload.StepUploadRecyclerView.StepUploadModel;

import java.util.ArrayList;
import java.util.List;

public class UploadViewModel extends ViewModel {
    private MutableLiveData<List<String>> tags;
    private MutableLiveData<List<String>> ingredients;
    private MutableLiveData<List<StepUploadModel>> steps;

    public LiveData<List<String>> getTags() {
        if (tags == null) {
            tags = new MutableLiveData<List<String>>();
        }
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags.setValue(tags);
    }

    public void addTags(List<String> tags) {
        List<String> set = this.tags.getValue();
        set.addAll(tags);
        this.tags.setValue(set);
    }

    public void removeTag(int index) {
        List<String> set = this.tags.getValue();
        set.remove(index);
        this.tags.setValue(set);
    }

    public LiveData<List<String>> getIngredients() {
        if (ingredients == null) {
            ingredients = new MutableLiveData<List<String>>();
        }
        return ingredients;
    }
    public void setIngredients(List<String> ingredients) {
        this.ingredients.setValue(ingredients);
    }

    public void addIngredient(String ingredient) {
        List<String> set = this.ingredients.getValue();
        if (set == null) {
            set = new ArrayList<String>();
        }
        set.add(ingredient);
        this.ingredients.setValue(set);
    }

    public void removeIngredient(int index) {
        List<String> set = this.ingredients.getValue();
        set.remove(index);
        this.ingredients.setValue(set);
    }

    public LiveData<List<StepUploadModel>> getSteps() {
        if (steps == null) {
            steps = new MutableLiveData<List<StepUploadModel>>();
        }
        return steps;
    }

    public void setSteps(List<StepUploadModel> steps) {
        this.steps.setValue(steps);
    }

    public void addSteps(List<StepUploadModel> steps) {
        List<StepUploadModel> set = this.steps.getValue();
        if (set == null) {
            set = new ArrayList<StepUploadModel>();
        }
        set.addAll(steps);
        this.steps.setValue(set);
    }

    public void removeStep(int index) {
        List<StepUploadModel> set = this.steps.getValue();
        set.remove(index);
        this.steps.setValue(set);
    }


}
