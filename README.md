Author: Tim Swarts  
Date: April 2022

# Thema 9 Java Wrapper
## Introduction
This Java project functions as a wrapper application to run a weka classifier trained to distinguish between
benign and malignant forms of breast cancer.    
This project was made using Java version 14.0.2 and was managed with Gradle.
All code can be found in the `src/main/java` folder.   
Underlying resources, including test data, are located in `scr/main/resources`

## Dependencies
- Java version 14.0.8 or up.
- Apache CLI version 1.4
- Weka API version 3.8.0

## Usage
This application can be build into a jar that will automatically be named `2021thema9-1.0.jar`.   
This jar can then be called using the following command:
```bash
~/path/to/project/root$ java -jar ./path/to/2021thema9-1.0.jar -f <file-name>
```
In this command <file-name> is the path to the file of to-be-classified instances.    
The `breastcancer_model_input.arff` file for example, which can be found in the resources folder as discussed previously.   
Alternatively, the help method can be called:
```bash
~/path/to/project/root$ java -jar ./path/to/projectthema9-1.0.jar -h
```
This will display a user manual similar to the one found in this README.

## Support
For any questions or remarks feel free to contact me at:  
[t.swarts@st.hanze.nl](t.swarts@st.hanze.nl)

## Credits
This project was made with help of [Michiel Noback](https://michielnoback.github.io/bincourses/data_mining_project.html)
and a multitude of helpful friends and classmates for whom I am very grateful.

## Further Reading
A report concerning the training of the classifier used in this application and the data used, along with a log
containing data analysis and clean up, can be found here:   
[https://github.com/TimSwarts/thema9](https://github.com/TimSwarts/thema9)