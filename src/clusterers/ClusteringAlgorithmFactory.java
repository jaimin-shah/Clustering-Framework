

public class ClusteringAlgorithmFactory {

private static HashMap<String,ClusteringAlgorithm> store = new HashMap<>();

static {

store.add("DBSCAN",new db());

}


private ClusteringAlgorithmFactory(){

}




public static ClusteringAlogrithm getAlgorithmInstance(String algorithm){
  
  return store.get(algorithm);
  

}


}
