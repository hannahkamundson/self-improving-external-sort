package com.digit.sort.internal;

import com.digit.sort.internal.ailon.TrainingStage;

public class SelfImprovingSort implements InternalSortStrategy {

    /**
     * Handle the training components.
     */
    private TrainingStage trainingStage = new TrainingStage();

    /**
     * The max train value can be out here, so we can get rid of the training object later on
     */
    private final int TRAINING_MAX = trainingStage.getTrainingMax();

    /**
     * What sort strategy should actually be used at a certain point. GenericSort or AilonSort?
     */
    private InternalSortStrategy sortStrategy;

    public SelfImprovingSort() {
        // Start off with a generic sort until we have enough info to build the Ailon sort
        sortStrategy = new GenericSort();
    }

    @Override
    public void sort(int sampleNumber, int[] unsorted) {
        // If we are in the training phase
        if (sampleNumber < TRAINING_MAX) {
            // Train on the unsorted data
            trainingStage.trainOnUnsortedData(sampleNumber, unsorted);

            // Call whatever default sort strategy (generic sort in this case)
            sortStrategy.sort(sampleNumber, unsorted);

            // Train on the sorted data
            trainingStage.trainOnSortedData(sampleNumber, unsorted);
        } else {
            sortStrategy.sort(sampleNumber, unsorted);
        }
    }

    @Override
    public void learn(int sampleNumber) {
        // If we have moved out of the training stage,
        if (sampleNumber == (TRAINING_MAX - 1)) {
            // Get the sort type based on the training info
            sortStrategy = trainingStage.createSort();
            trainingStage = null;
        }
    }
}