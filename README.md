# DISM-Multi-DISM-algorithm
Step1: Use IMS (on binary class) and Multi-IMS (on multi-class) to select shapelets candidates set on training data. 

Step2: Use DIMS algorithm to transform training data set to new feature space building on top-k shapelets features. 

Step3: Use DISM (on binary class) and Multi-DISM (on multi-class) to smote on transformed training data set. Specially, user need to smote multi-times for all minority-class. After this step, the training data set is re-balanced.

Step4: Train arbitrary classifiers using the re-balanced training data set.

Step5: Firstly transform testing dataset using DIMS (in step 2), then test data set using the trained classify (in step4)
