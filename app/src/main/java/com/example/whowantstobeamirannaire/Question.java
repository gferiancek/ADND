package com.example.whowantstobeamirannaire;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Question implements Parcelable {

    private String mQuestion;
    private String[] mAnswerChoices;
    private String mCorrectAnswer;

    public Question (String question, String[] answerChoices, String correctAnswer) {
        mQuestion = question;
        mAnswerChoices = answerChoices;
        mCorrectAnswer = correctAnswer;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String mQuestion) {
        this.mQuestion = mQuestion;
    }

    public String[] getAnswerChoices() {
        return mAnswerChoices;
    }

    public void setAnswerChoices(String[] mAnswerChoices) {
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
        dest.writeStringArray(mAnswerChoices);
        dest.writeString(mCorrectAnswer);
    }
}
