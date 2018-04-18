import java.util.ArrayList;

public class AnomalyDetection {


    double[] array = {4.0, 4.1,4.2, 4.2, 8.0};
    final int begin = -10;
    final int end = 10;
    final double increment = 0.1;


    public double[] getSineSeriesWithAnomaly() {
        double[] sine = new double[(int)Math.ceil((end-begin)/increment)+1];
        int k = 0;
        for(double i = begin; i<end; i+=increment){
            sine[k] = Math.sin(i);
            if(contains(array, Math.round(i*100)/100.0)){
                sine[k] = Math.random()*2 -1;
            }
            k++;
        }
        detect(sine, 10, 0.1);
        return sine;
    }

    public double[] getSteps(){
        double[] sine = new double[(int)Math.ceil((end-begin)/increment)+1];
        int k = 0;
        for(double i = begin; i<end; i+=increment){
            sine[k] = (int)(i/2);
            if(contains(array, Math.round(i*100)/100.0)){
                //sine[k] = Math.random()*2 -1;
            }
            k++;
        }
        detect(sine, 10, 0.3);
        return sine;
    }
    private boolean contains(double[] array, double v) {
        for (double v1 : array) {
            if(v1 == v){
                return true;
            }
        }
        return false;
    }

    public void detect(double[] data, int k, double reach){

        ArrayList<Double> knn = new ArrayList<>(k);
        int increasedReach = 1;
        for (int i = 0; i < k; i++) {
            double datum = data[i];
            knn.add(datum);

        }
        for (int i = k; i < data.length; i++) {
            double datum = data[i];
            if(isClose(knn, datum, reach*increasedReach)){
                knn.remove(0);
                knn.add(datum);
                increasedReach = increasedReach>1? increasedReach -1 :  1;
            }
            else{
                System.out.println("Anomaly detected at x = " + i*increment + " increasing reach to restore model");
                increasedReach ++;
            }
        }
    }

    private boolean isClose(ArrayList<Double> knn, double datum, double reach) {
        for (int i = 0; i<knn.size(); i++) {
            if(Math.abs(knn.get(i)-datum)/(knn.size()-i) >reach){
                return false;
            }
        }
        return true;
    }

    public double[] getConstant() {
        double[] sine = new double[(int)Math.ceil((end-begin)/increment)+1];
        int k = 0;
        for(double i = begin; i<end; i+=increment){
            sine[k] = 0;
            if(contains(array, Math.round(i*100)/100.0)){
                sine[k] = Math.random()*2 -1;
            }
            k++;
        }
        detect(sine, 10, 0);
        return sine;
    }
}
