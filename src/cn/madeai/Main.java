package cn.madeai;

import java.util.Arrays;
import java.util.Collections;
/**
 * function: x*Math.sin(10*Math.PI*x)+2.
 * domain: -1<=x=<2
 * Created by <a href="mailto:huangyebiaoke@outlook.com">huang</a> on 2020/11/25 13:28
 */
public class Main {
    final static int geneLength=15;
    final static int iteration=30;
    final static int populationSize=1000;


    double decode(int[] person){
//        System.out.println(Utils.arrayToString(person).length());
        int num=Integer.parseInt(Utils.arrayToString(person),2);
//        System.out.println(num+" "+(Math.pow(2,geneLength)-1));
        return -1+num*3/(Math.pow(2,geneLength)-1);
    }
    double calculateFitness(int[] person){
        double x=decode(person);
        return x*Math.sin(10*Math.PI*x)+2.;
    }

    double calculateBestFitness(String[] population){
        Double fitness=Double.MIN_VALUE;
        for (String person:population) {
            double currentFitness=calculateFitness(Utils.stringToArray(person));
            if (currentFitness>fitness){
                fitness=currentFitness;
            }
        }
        return fitness;
    }
    /**
     *
     * @param fitnessList
     * @return int the index of people
     * @author <a href="mailto:huangyebiaoke@outlook.com">huang<a/>
     * @date 2020/11/25 13:50
     */
    int selectParent(double[] fitnessList){
        double rand=Math.random();
        double currentFitnessSum=.0;
        int location=0;
        double fitnessSum= Arrays.stream(fitnessList).sum();
//        System.out.println("fitnessSum:"+fitnessSum);
        for (int i = 0; i < fitnessList.length; i++) {
            currentFitnessSum+=fitnessList[i]/fitnessSum;
            if (rand<currentFitnessSum){
                location=i;
                break;
            }
        }
//        todo: location always be zero??
//        System.out.println("loc:"+location);
        return location;
    }

    /**
     * select the the highest fitness of child
     * @param father
     * @param mother
     * @return java.lang.String
     * @author <a href="mailto:huangyebiaoke@outlook.com">huang<a/>
     * @date 2020/11/25 14:21
     */
    String crossover(String father, String mother){
        Double fitness=Double.MIN_VALUE;
        String child="";
        for (int i = 0; i <= father.length(); i++) {
            String currentChild="";
            currentChild+=father.substring(0,i);
            currentChild+=mother.substring(i);
            double currentFitness=calculateFitness(Utils.stringToArray(currentChild));
//            System.out.println(currentChild);
            if (currentFitness>fitness){
                fitness=currentFitness;
                child=currentChild;
            }
        }
        return child;
    }

    /**
     * randomly select child
     * @param father
     * @param mother
     * @return java.lang.String
     * @author <a href="mailto:huangyebiaoke@outlook.com">huang<a/>
     * @date 2020/11/25 14:22
     */
    String crossover2(String father, String mother){
        int location=(int)(Math.random()*geneLength);
        String child="";
        child+=father.substring(0,location);
        child+=mother.substring(location);
        return child;
    }
    String mutateChild(String child){
        double mutateRate=.01;
        int[] tempChild=Utils.stringToArray(child);
        for (int i = 0; i < tempChild.length; i++) {
            double rand=Math.random();
            if (rand<mutateRate){
                tempChild[i]=tempChild[i]==0?1:0;
            }
        }
        return Utils.arrayToString(tempChild);
    }
    String mutateChild2(String child){
        double mutateRate=.2;
        int[] tempChild=Utils.stringToArray(child);
        if (Math.random()<mutateRate){
            int index=(int)(Math.random()*tempChild.length);
            tempChild[index]=tempChild[index]==0?1:0;
        }
        return Utils.arrayToString(tempChild);
    }

    String[] getPopulation(int size){
        String[] population=new String[size];
        for (int i = 0; i < population.length; i++) {
            String person="";
            for (int j = 0; j < geneLength; j++) {
                double rand=Math.random();
//                todo: make rand's range from -1 to 2 can accelerate to figure out the answer
                if (rand<.5){
                    person+="0";
                }else {
                    person+="1";
                }
            }
//            System.out.println("person:"+person);
            population[i]=person;
        }
        return population;
    }

    public static void main(String[] args) {
        Main main=new Main();
//        int[] a={0,0,0,0,0,0,0,0,0,0,0,0,1,0,0};
//        int[] b={1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
//        System.out.println(main.decode(a));
//        System.out.println(1*3./8388608.0);
//        int[] a=Utils.stringToArray("111111111111111111111111111111");
//        Arrays.stream(a).forEach((b)->System.out.print(b+","));
        String[] population=main.getPopulation(populationSize);
        double[] fitnessList=new double[population.length];
        int generation=1;
        Double bestFitness=Double.MIN_VALUE;
        String bestPerson="";
        do{
            for (String person:population) {
                double currentFitness=main.calculateFitness(Utils.stringToArray(person));
//                System.out.println("("+main.decode(Utils.stringToArray(person))+","+currentFitness+")");
                if (currentFitness>bestFitness){
                    bestFitness=currentFitness;
                    bestPerson=person;
                }
            }
            System.out.println("("+main.decode(Utils.stringToArray(bestPerson))+","+bestFitness+")");
            for (int i = 0; i < population.length; i++) {
                fitnessList[i]=main.calculateFitness(Utils.stringToArray(population[i]));
//                System.out.println(main.selectParent(fitnessList));
                String bestMother=population[main.selectParent(fitnessList)];
                String bestFather=population[main.selectParent(fitnessList)];
                String childFromBestParent=main.crossover(bestFather,bestMother);
                childFromBestParent=main.mutateChild(childFromBestParent);
                population[i]=childFromBestParent;
            }
            generation++;
        }while(generation!=iteration);
    }
}
