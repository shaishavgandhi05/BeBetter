package shaishav.com.bebetter.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;
import me.originqiu.library.EditTag;
import shaishav.com.bebetter.Data.Models.Lesson;
import shaishav.com.bebetter.Data.Source.LessonSource;
import shaishav.com.bebetter.R;
import shaishav.com.bebetter.Utils.Constants;
import shaishav.com.bebetter.Utils.NetworkRequests;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AddLesson extends AppCompatActivity {

    EditText title,lesson;
    EditTag category;
    Switch isPublic;
    ImageButton tooltip;
    Button saveButton;
    LessonSource lessonSource;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add font
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("Exo2-Regular.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_add_lesson);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add a lesson");
        setSupportActionBar(toolbar);

        // Add back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        initialize();
        setEventListeners();

    }

    public void initialize(){

        //Initialize UI components
        title = (EditText)findViewById(R.id.title);
        lesson = (EditText)findViewById(R.id.lesson);
        category = (EditTag)findViewById(R.id.category);
        saveButton = (Button)findViewById(R.id.save);
        isPublic = (Switch)findViewById(R.id.make_public);
        tooltip = (ImageButton)findViewById(R.id.tooltip);
        tooltip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Set tooltip
                new SimpleTooltip.Builder(getApplicationContext())
                        .anchorView(tooltip)
                        .text("At Be Better, we feel that everyone in the community will benefit if we all share our experiences. Some of our lessons can be personal and specific to our personality. You typically would want to share experiences that can be extrapolated and beneficial to everyone. Please keep in mind that the quality of your feed is dependent on you")
                        .gravity(Gravity.TOP)
                        .animated(true)
                        .transparentOverlay(false)
                        .textColor(Color.WHITE)
                        .build()
                        .show();
            }
        });

        //Database connection
        lessonSource = new LessonSource(this);

        title.getBackground().mutate().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        lesson.getBackground().mutate().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

    }



    public void setEventListeners(){
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveButton.setEnabled(false);
                String titleText = title.getText().toString().trim();
                String lessonText = lesson.getText().toString().trim();
                List<String> catList = category.getTagList();
                boolean is_public = isPublic.isChecked();
                String categoryText = Constants.convertListToString(catList);

                if(titleText.length()>0 && lessonText.length()>0 && catList.size()>0) {
                    lessonSource.open();
                    Lesson lesson = lessonSource.createLesson(titleText,lessonText,categoryText,new Date().getTime(),is_public);
                    lessonSource.close();
                    NetworkRequests networkRequests = NetworkRequests.getInstance(getApplicationContext());
                    networkRequests.syncLesson(lesson);
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(getApplicationContext(),"Nice! Keep going!",Toast.LENGTH_LONG).show();
                }
                else {
                    Snackbar.make(view,"Please check your input",Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }



}
