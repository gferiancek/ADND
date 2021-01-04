package com.example.whowantstobeamirannaire;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

public class QuestionLibrary {

    private Context mContext;
    private List<Question> mNormalQuestions = new ArrayList<Question>();
    private List<Question> mHardQuestions = new ArrayList<Question>();
    private List<Question> mBrutalQuestions = new ArrayList<Question>();
    private List<Question> mManiacQuestions = new ArrayList<Question>();


    public QuestionLibrary (Context context, String difficulty) {
        mContext = context;
        initializeQuestions(difficulty);
    }

    /**
     * Function that makes the question objects for each difficutly and adds them into a List.
     *
     * @param difficulty  Pulled from the QuestionLibrary constructor (in turn pulled from MainActivity
     * intent extra) to only load the questions for the selected difficultly.
     */
    private void initializeQuestions(String difficulty) {
        Resources appResources = mContext.getResources();
        switch (difficulty) {
            case "normal":
                for (int i = 1; i <= 20; i++) {
                    int normalQuestionID = appResources.getIdentifier("normal_question_" + i, "string", mContext.getPackageName());
                    int normalChoicesID = appResources.getIdentifier("normal_answers_" + i, "array", mContext.getPackageName());
                    String[] normalChoices = appResources.getStringArray(normalChoicesID);
                    mNormalQuestions.add(new Question(mContext.getString(normalQuestionID), normalChoices, normalChoices[0]));
                }
                break;
            case "hard":
                for (int i = 1; i <= 20; i++) {
                    int hardQuestionID = appResources.getIdentifier("hard_question_" + i, "string", mContext.getPackageName());
                    int hardChoicesID = appResources.getIdentifier("hard_answers_" + i, "array", mContext.getPackageName());
                    String[] hardChoices = appResources.getStringArray(hardChoicesID);
                    mHardQuestions.add(new Question(mContext.getString(hardQuestionID), hardChoices, hardChoices[0]));
                }
                break;
            case "brutal":
                for (int i = 1; i <= 20; i++) {
                    int brutalQuestionID = appResources.getIdentifier("brutal_question_" + i, "string", mContext.getPackageName());
                    int brutalChoicesID = appResources.getIdentifier("brutal_answers_" + i, "array", mContext.getPackageName());
                    String[] brutalChoices = appResources.getStringArray(brutalChoicesID);
                    mBrutalQuestions.add(new Question(mContext.getString(brutalQuestionID), brutalChoices, brutalChoices[0]));
                }
                break;
            case "maniac":
                for (int i = 1; i <= 20; i++) {
                int maniacQuestionID = appResources.getIdentifier("maniac_question_" + i, "string", mContext.getPackageName());
                int maniacChoicesID = appResources.getIdentifier("maniac_answers_" + i, "array", mContext.getPackageName());
                String[] maniacChoices = appResources.getStringArray(maniacChoicesID);
                mManiacQuestions.add(new Question(mContext.getString(maniacQuestionID), maniacChoices, maniacChoices[0]));
            }
                break;
        }
    }

    public List<Question> getNormalQuestions() {
        return mNormalQuestions;
    }

    public void setNormalQuestions(List<Question> mNormalQuestions) {
        this.mNormalQuestions = mNormalQuestions;
    }

    public List<Question> getHardQuestions() {
        return mHardQuestions;
    }

    public void setHardQuestions(List<Question> mHardQuestions) {
        this.mHardQuestions = mHardQuestions;
    }

    public List<Question> getBrutalQuestions() {
        return mBrutalQuestions;
    }

    public void setBrutalQuestions(List<Question> mBrutalQuestions) {
        this.mBrutalQuestions = mBrutalQuestions;
    }

    public List<Question> getManiacQuestions() {
        return mManiacQuestions;
    }

    public void setManiacQuestions(List<Question> mManiacQuestions) {
        this.mManiacQuestions = mManiacQuestions;
    }
}
