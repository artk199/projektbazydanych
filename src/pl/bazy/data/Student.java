package pl.bazy.data;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Art on 2014-10-21.
 */
public class Student implements Comparable {

    public static final int NUM_OF_RATINGS = 3;

    private final static Logger LOGGER = Logger.getLogger(Student.class.getName());

    private int index;

    private double ratings[];

    private double avg;

    public Student() {
        index = -1;
        ratings = new double[NUM_OF_RATINGS];

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (index != student.index) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return index;
    }

    /*
        Getters and setters
     */
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double[] getRatings() {
        return ratings;
    }

    public void setRatings(double[] ratings) {
        this.ratings = ratings;
    }

    public void setRating(int i,double value){
        if(i < ratings.length) {
            ratings[i] = value;
            avg = -1;
        }else
            LOGGER.log(Level.WARNING,"Za duże i dałeś d$!@#$.");
    }
    public double getRating(int i){
        if(i < ratings.length) {
            return ratings[i];
        }else
            LOGGER.log(Level.WARNING,"Za duże i dałeś d$!@#$.");
        return -1;
    }
    public double averageRating(){
        if(avg == -1){
            double sum = 0;
            for(int i=0;i<ratings.length;i++){
                sum += ratings[i];
            }
            avg = sum/ratings.length;
        }
        return avg;
    }
    @Override
    public int compareTo(Object o) {
        if (this == o) return -1;
        if (o == null || getClass() != o.getClass()) return -1;

        Student student = (Student) o;

        if(student.averageRating() == this.averageRating())
            return 0;

        if(student.averageRating() < this.averageRating())
            return 1;

        return -1;
    }

    @Override
    public String toString() {
        if(avg == -1)
            averageRating();

        return "Student{" +
                "index=" + index +
                ", ratings=" + Arrays.toString(ratings) +
                ", avg=" + avg +
                '}';
    }
}
