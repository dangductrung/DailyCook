package com.adida.dailycook.Upload.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.adida.dailycook.Upload.Fragment.Upload.StepUploadRecyclerView.StepUploadModel;
import com.adida.dailycook.retrofit2.entities.Tag;

import java.util.ArrayList;
import java.util.List;

public class UploadViewModel extends ViewModel {
    private MutableLiveData<List<Tag>> tags;
    private MutableLiveData<List<String>> ingredients;
    private MutableLiveData<List<StepUploadModel>> steps;

    public LiveData<List<Tag>> getTags() {
        if (tags == null) {
            tags = new MutableLiveData<>();
        }
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags.setValue(tags);
    }

    public void addTag(Tag tag) {
        List<Tag> set = this.tags.getValue();
        if (set == null) {
            set = new ArrayList<>();
        }
        set.add(tag);
        this.tags.setValue(set);
    }

    public void removeTag(Tag tag) {
        List<Tag> set = this.tags.getValue();
        if (set != null) {
            set.remove(tag);
        }
        this.tags.setValue(set);
    }

    public LiveData<List<String>> getIngredients() {
        if (ingredients == null) {
            ingredients = new MutableLiveData<>();
        }
        return ingredients;
    }
    public void setIngredients(List<String> ingredients) {
        this.ingredients.setValue(ingredients);
    }

    public void addIngredient(String ingredient) {
        List<String> set = this.ingredients.getValue();
        if (set == null) {
            set = new ArrayList<>();
        }
        set.add(ingredient);
        this.ingredients.setValue(set);
    }

    public void removeIngredient(int index) {
        List<String> set = this.ingredients.getValue();
        if (set != null) {
            set.remove(index);
        }
        this.ingredients.setValue(set);
    }

    public LiveData<List<StepUploadModel>> getSteps() {
        if (steps == null) {
            steps = new MutableLiveData<>();
        }
        return steps;
    }

    public void setSteps(List<StepUploadModel> steps) {
        this.steps.setValue(steps);
    }

    public void addSteps(List<StepUploadModel> steps) {
        List<StepUploadModel> set = this.steps.getValue();
        if (set == null) {
            set = new ArrayList<>();
        }
        set.addAll(steps);
        this.steps.setValue(set);
    }

    public void removeStep(int index) {
        List<StepUploadModel> set = this.steps.getValue();
        if (set != null) {
            set.remove(index);
        }
        this.steps.setValue(set);
    }


}
