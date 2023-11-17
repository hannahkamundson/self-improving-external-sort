package com.digit.sort.internal;

import com.digit.sort.internal.ailon.TrainingStage;

public class SelfImprovingSort implements InternalSortStrategy {

    /**
     * Handle the training components.
     */
    private TrainingStage trainingStage = new TrainingStage();

    /**
     * The max train value can be out here so we can get rid of the training object later on
     */
    private final int TRAINING_MAX = trainingStage.getTrainingMax();

    /**
     * What sort strategy should actually be used at a certain point. GenericSort or AilonSort?
     */
    private InternalSortStrategy sortStrategy;

    private int sample = 0;

    public SelfImprovingSort() {
        // Start off with a generic sort until we have enough info to build the ailon sort
        sortStrategy = new GenericSort();
    }

    

    @Override
    public void sort(int sampleNumber, int[] unsorted) {
        if (sample < TRAINING_MAX) {
            // Train the data
            trainingStage.train(sampleNumber, unsorted);
            // Do a generic sort
            sortStrategy.sort(sample, unsorted);
        } else {
            // If we have moved out of the training stage,
            if (sample == TRAINING_MAX) {
                // Get the sort type based on the training info
                sortStrategy = trainingStage.createSort();
                trainingStage = null;
            }

            sortStrategy.sort(sample, unsorted);
        }

        sample++;
    }
}