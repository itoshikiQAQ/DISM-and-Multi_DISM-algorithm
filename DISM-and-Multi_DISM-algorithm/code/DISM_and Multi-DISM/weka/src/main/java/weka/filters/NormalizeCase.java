/** Class NormalizeAttribute.java
 * 
 * @author AJB
 * @version 1
 * @since 14/4/09
 * 
 * Class normalizes attributes, basic version. Assumes no missing values. 
 * 
 * Normalise onto [0,1] if norm==NormType.INTERVAL, 
 * Normalise onto Normal(0,1) if norm==NormType.STD_NORMAL, 
 * 
 * 
 */

package weka.filters;

import weka.core.Instances;

public class NormalizeCase extends SimpleBatchFilter{
	public enum NormType {INTERVAL,STD,STD_NORMAL};
	
	NormType norm=NormType.STD;
        public void setNormType( NormType n){norm=n;}
/* 
 * 
 */
	protected Instances determineOutputFormat(Instances inputFormat){
	     Instances result = new Instances(inputFormat, 0);
	     return result;
	}
	public Instances process(Instances inst) throws Exception {
//Clone the 		
		  Instances result = new Instances(inst);
		  switch(norm){
		  case INTERVAL:	//Map onto [0,1]
			  intervalNorm(result);
			  break;
		  case STD:			//Subtract the mean of the series
			  standard(result);
			  break;
		  case STD_NORMAL:	//Transform to zero mean, unit variance
			  standardNorm(result);
			  break;
			  default:
				  
		  }
		  return result;
	}
/* Wont normalise the class value*/	
	public void intervalNorm(Instances r){
		double max,min;
		for(int i=0;i<r.numInstances();i++){
			max=Double.MIN_VALUE;
			min=Double.MAX_VALUE;
			for(int j=0;j<r.numAttributes();j++){
				if(j!=r.classIndex()){
					double x=r.instance(i).value(j);
					if(x>max)
						max=x;
					if(x<min)
						min=x;
				}
			}
			for(int j=0;j<r.numAttributes();j++){
				if(j!=r.classIndex()){
					double x=r.instance(i).value(j);
					r.instance(i).setValue(j,(x-min)/(max-min));
				}
			}
		}
	}
	public void standard(Instances r) throws Exception{
		double mean,sum,sumSq,stdev,x,y;
		int size=r.numAttributes();
		int classIndex=r.classIndex();
		if(classIndex>0)
			size--;
		for(int i=0;i<r.numInstances();i++)
		{
			sum=sumSq=mean=stdev=0;
			for(int j=0;j<r.numAttributes();j++){
			if(j!=classIndex){
					x=r.instance(i).value(j);
					sum+=x;
				}
				mean=sum/size;
			}
			for(int j=0;j<r.numAttributes();j++){
				if(j!=classIndex){
					x=r.instance(i).value(j);
					r.instance(i).setValue(j,(x-mean));
				}
			}
		}
	}	
	
	public void standardNorm(Instances r) throws Exception{
		double mean,sum,sumSq,stdev,x;
		int size=r.numAttributes();
		int classIndex=r.classIndex();
		if(classIndex>=0)
			size--;
		for(int i=0;i<r.numInstances();i++)
		{
			sum=sumSq=mean=stdev=0;
			for(int j=0;j<r.numAttributes();j++){
			if(j!=classIndex){
					x=r.instance(i).value(j);
					sum+=x;
					sumSq+=x*x;
				}
				stdev=(sumSq-sum*sum/size)/size;
				mean=sum/size;
				stdev=Math.sqrt(stdev);
				if(stdev==0)
					throw new Exception("Cannot normalise a series with zero variance!");
			}
			for(int j=0;j<r.numAttributes();j++){
				if(j!=classIndex){
					x=r.instance(i).value(j);
					r.instance(i).setValue(j,(x-mean)/(stdev));
				}
			}
			
		}
		
	}
	public String globalInfo() {
		// TODO Auto-generated method stub
		return null;
	}


	public String getRevision() {
		// TODO Auto-generated method stub
		return null;
	}

	static String[] fileNames={	//Number of train,test cases,length,classes
		"Beef", //30,30,470,5
		"Coffee", //28,28,286,2
		"OliveOil",
		"Earthquakes",
		"Ford_A",
		"Ford_B"
};
	static String path="C:\\Research\\Data\\Time Series Data\\Time Series Classification\\";
	
	public static void main(String[] args){

	}
	
	
}
