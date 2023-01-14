package uci;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class train
{

    double [][] confusionMatrix=new double[2][2];
    double yesAge=0,yesCholestorel=0,maleYes=0,femaleYes=0;
    double noAge=0,noCholestorel=0,maleNo=0,femaleNo=0;
    double yesAgeMean=0,noAgeMean=0,yesCholestorelMean=0,noCholestorelMean=0,yesAgeSd=0,noAgeSd=0,yesCholestorelSd=0,noCholestorelSd=0;
    double ProbGenderYes=0,ProbCholestorelYes=0,ProbAgeYes=0,ProbGenderNo=0,ProbAgeNo=0,ProbCholestorelNo=0,e,f,ProbYes=0,ProbNo=0;
    public void train(int fold,int K,ArrayList<person> person) throws Exception
    {
       
        int start=fold*(person.size()/K);
        int end=start+((person.size()/K)-1);
        for(int i=0; i<start; i++)
        {
            if((person.get(i).healthDisease).equals("yes"))
            {
                yesAge=yesAge+person.get(i).age;
                yesCholestorel=yesCholestorel+person.get(i).cholesterol;
                if((person.get(i).gender).equals("m"))
                {
                    maleYes++;

                }
                else if((person.get(i).gender).equals("f"))
                {
                    femaleYes++;

                }
            }
            else if((person.get(i).healthDisease).equals("no"))
            {
                noAge=noAge+person.get(i).age;
                noCholestorel=noCholestorel+person.get(i).cholesterol;
                if((person.get(i).gender).equals("m"))
                {
                    maleNo++;

                }
                else if((person.get(i).gender).equals("f"))
                {
                    femaleNo++;

                }
            }

        }
        for(int i=end+1; i<person.size(); i++)
        {
            
            if((person.get(i).healthDisease).equals("yes"))
            {
                yesAge=yesAge+person.get(i).age;
                yesCholestorel=yesCholestorel+person.get(i).cholesterol;
                if((person.get(i).gender).equals("m"))
                {
                    maleYes++;

                }
                else if((person.get(i).gender).equals("f"))
                {
                    femaleYes++;

                }
            }
            else if((person.get(i).healthDisease).equals("no"))
            {
                noAge=noAge+person.get(i).age;
                noCholestorel=noCholestorel+person.get(i).cholesterol;
                if((person.get(i).gender).equals("m"))
                {
                    maleNo++;

                }
                else if((person.get(i).gender).equals("f"))
                {
                    femaleNo++;

                }
            }

        }
        yesAgeMean=yesAge/(maleYes+femaleYes);
        noAgeMean=noAge/(maleNo+femaleNo);
        yesCholestorelMean=yesCholestorel/(maleYes+femaleYes);
        noCholestorelMean=noCholestorel/(maleNo+femaleNo);
        for(int i=0;i<person.size();i++)
        {
            if((person.get(i).healthDisease).equals("yes"))
            {
            	yesAgeSd=yesAgeSd+Math.pow(((person.get(i).age)-yesAgeMean), 2);
            	yesCholestorelSd=yesCholestorelSd+Math.pow(((person.get(i).age)-yesCholestorelMean), 2);
            }
            else if((person.get(i).healthDisease).equals("no"))
            {
            	noAgeSd=noAgeSd+Math.pow(((person.get(i).age)-noAgeMean), 2);
            	noCholestorelSd=noCholestorelSd+Math.pow(((person.get(i).age)-noCholestorelMean), 2);
            }

        }
       
        yesAgeSd=yesAgeSd/((maleYes+femaleYes)-1);
        yesAgeSd=Math.pow(yesAgeSd, 0.5);
        noAgeSd=noAgeSd/((maleNo+femaleNo)-1);
        noAgeSd=Math.pow(noAgeSd, 0.5);
        yesCholestorelSd=yesCholestorelSd/((maleYes+femaleYes)-1);
        yesCholestorelSd=Math.pow(yesCholestorelSd, 0.5);
        noCholestorelSd=noCholestorelSd/((maleNo+femaleNo)-1);
        noCholestorelSd=Math.pow(noCholestorelSd, 0.5);

    }
    public double test(int fold,int K,ArrayList<person> person) throws Exception
    {

        int start=fold*(person.size()/K);
        int end=start+((person.size()/K)-1);

        for(int i=0; i<2; i++)
        {
            for(int j=0; j<2; j++)
            {
                confusionMatrix[i][j]=0;
            }
        }
        for(int i=start; i<end; i++)
        {
            person test=person.get(i);
            if((test.gender).equals("m"))
            {
                ProbGenderYes=maleYes/(maleYes+maleNo);
                ProbGenderNo=maleNo/(maleYes+maleNo);
            }
            else if((test.gender).equals("f"))
            {
                ProbGenderYes=femaleYes/(femaleYes+femaleNo);

                ProbGenderNo=femaleNo/(femaleYes+femaleNo);
            }


            ProbAgeYes=1/(2*3.1416);
            ProbAgeYes=Math.pow(ProbAgeYes, 0.5);
            ProbAgeYes=ProbAgeYes/yesAgeSd;
            e=(test.age-yesAgeMean);
            e=(e/yesAgeSd);
            e=Math.pow(e, 2);
            e=-0.5*e;
            e=Math.pow(2.718, e);
            ProbAgeYes=ProbAgeYes*e;
            ProbAgeNo=1/(2*3.1416);
            ProbAgeNo=Math.pow(ProbAgeNo, 0.5);
            ProbAgeNo=ProbAgeNo/noAgeSd;
            e=(test.age-noAgeMean);
            e=e/noAgeSd;
            e=Math.pow(e, 2);
            e=-0.5*e;
            e=Math.pow(2.718, e);
            ProbAgeNo=ProbAgeNo*e;

            ProbCholestorelYes=1/(2*3.1416);
            ProbCholestorelYes=Math.pow(ProbCholestorelYes, 0.5);
            ProbCholestorelYes=ProbCholestorelYes/yesCholestorelSd;
            f=(test.cholesterol-yesCholestorelMean);
            f=f/yesCholestorelSd;
            f=Math.pow(f, 2);
            f=-0.5*f;
            f=Math.pow(2.718, f);
            ProbCholestorelYes=ProbCholestorelYes*f;

            ProbCholestorelNo=1/(2*3.1416);
            ProbCholestorelNo=Math.pow(ProbCholestorelNo, 0.5);
            ProbCholestorelNo=ProbCholestorelNo/noCholestorelSd;
            f=(test.cholesterol-noCholestorelMean);
            f=f/noCholestorelSd;
            f=Math.pow(f, 2);
            f=-0.5*f;
            f=Math.pow(2.718, f);
            ProbCholestorelNo=ProbCholestorelNo*f;

            ProbYes=ProbAgeYes*ProbGenderYes*ProbCholestorelYes*((maleYes+femaleYes)/(maleYes+femaleYes+maleNo+femaleNo));
            ProbNo=ProbAgeNo*ProbGenderYes*ProbCholestorelNo*((maleNo+femaleNo)/(maleYes+femaleYes+maleNo+femaleNo));
            if(ProbYes>ProbNo && (test.healthDisease).equals("yes"))
            {
                confusionMatrix[0][0]++;
            }
            else if(ProbYes<ProbNo && (test.healthDisease).equals("no"))
            {
                confusionMatrix[1][1]++;
            }
            else if(ProbYes>ProbNo && (test.healthDisease).equals("no"))
            {
                confusionMatrix[1][0]++;;
            }
            else if(ProbYes<ProbNo && (test.healthDisease).equals("yes"))
            {
                confusionMatrix[0][1]++;;
            }

        }
        double precision,recall,fScore;
        precision=confusionMatrix[0][0]/(confusionMatrix[0][0]+confusionMatrix[0][1]);
        recall=confusionMatrix[0][0]/(confusionMatrix[0][0]+confusionMatrix[1][0]);
        fScore=(2*precision*recall)/(precision+recall);
        System.out.println("Done testing for fold : "+fold+" and the accuracy is "+fScore);
        return fScore;
    }
}
