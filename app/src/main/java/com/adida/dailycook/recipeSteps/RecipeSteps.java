package com.adida.dailycook.recipeSteps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.adida.dailycook.R;
import com.adida.dailycook.retrofit2.entities.RecipeDetail;
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

        //Intent intent = getIntent();
        //RecipeDetail detail = (RecipeDetail) intent.getExtras().getParcelable("detail");

        List<RecipeStep> steps = new ArrayList<>();

        steps.add(new RecipeStep(1,"Bắc chảo lên bếp ở lửa vừa, khi chảo nóng thì cho dầu ăn vào",35,"https://cdn.daynauan.info.vn/wp-content/uploads/2019/05/cach-lam-canh-ga-chien-xu.jpg",0));
        steps.add(new RecipeStep(1,"Lấy từng cánh gà lăn qua bột sao cho bột phủ kín bề mặt của cánh rồi đem nhúng vào bát trứng, sau đó lăn lại với đĩa bột một lần nữa. Làm lần lượt như vậy cho đến khi hết 4 cánh gà trên",35,"https://beptruong.edu.vn/wp-content/uploads/2015/11/cach-lam-canh-ga-chien-bo.jpg",0));
        steps.add(new RecipeStep(1,"Đặt đứng miếng gà trên đĩa, một tay giữ phần xương cánh gà, tay còn lại dùng các ngón bấu chặt vào phần thịt gà rồi sử dụng lực kéo xuống dứt khoát cho thịt rơi ra là hoàn thành.",35,"https://product.hstatic.net/1000093072/product/canh-ga-chien-mam.jpg",0));
        steps.add(new RecipeStep(1,"Xếp cánh gà lên đĩa có cà chua và xà lách. Như vậy, trong vòng chưa đến 40 phút chuẩn bị và chế biến, bạn đã hoàn thành xong món cánh gà chiên xù thơm ngon ăn là ghiền rồi.",35,"https://cdn.daynauan.info.vn/wp-content/uploads/2018/06/canh-ga-chien-xu.jpg",0));
//        steps.add(new RecipeStep(2,"12sdfsdf3",67,"imgUrl",1));
//        steps.add(new RecipeStep(3,"12sdfsdf3",39,"imgUrl",2));
//        steps.add(new RecipeStep(4,"123sdfsdf",34,"imgUrl",3));
//        steps.add(new RecipeStep(5,"12sdfsdf3",30,"imgUrl",4));
//        steps.add(new RecipeStep(6,"123sdfsdf",32,"imgUrl",5));

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this,steps));
    }
}