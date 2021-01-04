package com.example.whowantstobeamirannaire;

public class Question {

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
}
