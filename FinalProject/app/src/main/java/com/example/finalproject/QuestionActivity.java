package com.example.finalproject;

import android.animation.Animator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalproject.Models.QuestionModel;
import com.example.finalproject.databinding.ActivityQuestionBinding;

import java.util.ArrayList;

import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class QuestionActivity extends AppCompatActivity {
    ArrayList<QuestionModel> list = new ArrayList<>();
    ArrayList<QuestionModel> setOneList = new ArrayList<>(); // SET-1 için sorular
    ArrayList<QuestionModel> setTwoList = new ArrayList<>(); // SET-2 için sorular

    private Button btnexit;
    private Switch soundSwitch;


    private MusicManager musicManager;


    private  int count=0;
    private  int position=0;
    private  int score= 0;
    private boolean isSoundPlayed = false;  // Sesin çalıp çalmadığını takip edeceğiz

    CountDownTimer timer;




    ActivityQuestionBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityQuestionBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        DBHelper dbHelper = new DBHelper(this);
        dbHelper.addQuestions();
        resetTimer();
        timer.start();
        btnexit=binding.btnShare;
        soundSwitch=binding.soundswitch; // binding ile
        musicManager = MusicManager.getInstance(this);



soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        musicManager.setSoundEnabled(isChecked);
        if (isChecked) {

            isSoundPlayed=true;
            musicManager.stopSound();

        } else {


            musicManager.playSound();
        }
    }
});


// Veritabanına soruyu ekle
        list = dbHelper.getAllQuestions(); // Tüm soruları al

        // SET-1 ve SET-2'yi ayır
        setOne();  // İlk 2 soruyu SET-1'e ekle
        setTwo();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setContentView(binding.getRoot());
        String setName=getIntent().getStringExtra("set");
        if(setName.equals("SET-1")){
            list = new ArrayList<>(setOneList);
        }
        else if(setName.equals("SET-2")){
            list = new ArrayList<>(setTwoList);
        }
        for(int i=0;i<4;i++){

            binding.optionContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer((Button) v );
                }
            });
        }
        binding.btnShare.setOnClickListener(v -> {
            new AlertDialog.Builder(QuestionActivity.this)
                    .setTitle("Exit")
                    .setMessage("Are You Sure Exit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(QuestionActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("No", null) // Hayır seçeneği sadece pencereyi kapatır
                    .show();
        });


        playAnimation(binding.textView10,0,list.get(position).getQuestion());

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timer!=null){
                    timer.cancel();
                }
                timer.start();
                binding.btnNext.setEnabled(false);
                binding.btnNext.setAlpha(0.83f);
                enableOption(true);
                position++;
                if(position == list.size()){
                    Intent intent= new Intent(QuestionActivity.this,ScoreActivity.class);
                    intent.putExtra("score",score);
                    intent.putExtra("total",list.size());
                    startActivity(intent);
                    finish();
                    return;

                }
                count=0;
                playAnimation(binding.textView10,0,list.get(position).getQuestion());


            }
        });

    }

    private void resetTimer() {
        timer= new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long l) {
                binding.timer.setText(String.valueOf(l/1000));
            }

            @Override
            public void onFinish() {
                Dialog dialog= new Dialog(QuestionActivity.this);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.timeout_dialog);
                dialog.findViewById(R.id.tryAgain).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(QuestionActivity.this,SetsActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                dialog.show();
            }
        };
    }

    private void playAnimation(View view, int value, String data) {
         view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100).setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
             @Override
             public void onAnimationStart(@NonNull Animator animation) {
                 if(isSoundPlayed){
                     musicManager = MusicManager.getInstance(getApplicationContext());
                     musicManager.playSound();

                 }


                 if(value==0 && count <4){
                     String option="";
                     if(count==0){
                         option=list.get(position).getOptionA();
                     } else if (count==1) {
                         option=list.get(position).getOptionB();
                     }else if (count==2) {
                         option=list.get(position).getOptionC();
                     }else if (count==3) {
                         option=list.get(position).getOptionD();
                     }

                     playAnimation(binding.optionContainer.getChildAt(count),0,option);
                     count++;
                 }

             }

             @Override
             public void onAnimationEnd(@NonNull Animator animation) {
                 if (value == 0) {
                     try {
                         // view bir TextView olacağı için, bu şekilde değiştirelim
                         if (view instanceof TextView) {
                             ((TextView) view).setText(data);
                         }
                         binding.totalQuestion.setText(position + 1 + '/' + list.size());
                     } catch (Exception e) {
                         // Eğer Button'a cast edilirse, burada işlem yapılabilir
                         if (view instanceof Button) {
                             ((Button) view).setText(data);
                         }
                     }

                     view.setTag(data);
                     playAnimation(view, 1, data);
                 }
             }


             @Override
             public void onAnimationCancel(@NonNull Animator animation) {

             }

             @Override
             public void onAnimationRepeat(@NonNull Animator animation) {

             }
         });


    }

    private void enableOption(boolean enable) {
        for(int i=0;i<4;i++){
            binding.optionContainer.getChildAt(i).setEnabled(enable);
            if(enable){
                binding.optionContainer.getChildAt(i).setBackgroundResource(R.drawable.btn_opt);
            }
        }



    }

    private void checkAnswer(Button selectedOption) {
        if(timer!= null){
            timer.cancel();
        }

        binding.btnNext.setEnabled(true);
        binding.btnNext.setAlpha(1);
        if(selectedOption.getText().toString().equals(list.get(position).getCorrectAnswer())){
              score++;
              selectedOption.setBackgroundResource(R.drawable.right_answ);
        }
        else {
            selectedOption.setBackgroundResource(R.drawable.wrong_answ);
            Button correctOption=binding.optionContainer.findViewWithTag(list.get(position).getCorrectAnswer());
            correctOption.setBackgroundResource(R.drawable.right_answ);
        }
    }


    public void setOne() {
        for (int i = 0; i < 5 && i < list.size(); i++) {
            setOneList.add(list.get(i));
        }
    }

    // Geriye kalan soruları SET-2'ye ekle
    public void setTwo() {
        for (int i = 5; i < list.size(); i++) {
            setTwoList.add(list.get(i));
        }
    }



}
