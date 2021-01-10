package com.example.whowantstobeamirannaire;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class Question implements Parcelable {

    private String mQuestion;
    private List<String> mAnswerChoices;
    private String mCorrectAnswer;

    public Question (String question, List<String> answerChoices, String correctAnswer) {
        mQuestion = question;
        mAnswerChoices = answerChoices;
        mCorrectAnswer = correctAnswer;
    }

    protected Question(Parcel in) {
        mQuestion = in.readString();
        mAnswerChoices = in.createStringArrayList();
        mCorrectAnswer = in.readString();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String mQuestion) {
        this.mQuestion = mQuestion;
    }

    public List<String> getAnswerChoices() {
        return mAnswerChoices;
    }

    public void setAnswerChoices(List<String> mAnswerChoices) {
        this.mAnswerChoices = mAnswerChoices;
    }

    public String getCorrectAnswer() {
        return mCorrectAnswer;
    }

    public void setCorrectAnswer(String mCorrectAnswer) {
        this.mCorrectAnswer = mCorrectAnswer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mQuestion);
        dest.writeList(mAnswerChoices);
        dest.writeString(mCorrectAnswer);
    }
}
