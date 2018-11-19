import org.apache.spark.mllib.tree.RandomForest
import org.apache.spark.mllib.tree.model.RandomForestModel
import org.apache.spark.mllib.util.MLUtils

// Load and parse the data file.
val data = MLUtils.loadLibSVMFile(sc, "Data/Covtype/covtype")
// Split the data into training and test sets (30% held out for testing)
val splits = data.randomSplit(Array(0.7, 0.3))
val (trainingData, testData) = (splits(0), splits(1))

// Train a RandomForest model.
// Empty categoricalFeaturesInfo indicates all features are continuous.
val numClasses = 8
val categoricalFeaturesInfo = Map[Int, Int]()
val numTrees = 64 // Use more in practice.
val featureSubsetStrategy = "auto" // Let the algorithm choose.
val impurity = "gini"
val maxDepth = 5
val maxBins = 32

var timeStart = System.currentTimeMillis();
val nbIteration = 50;

val model = RandomForest.trainClassifier(trainingData, numClasses, categoricalFeaturesInfo,
  numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins)

for(i <- 1 to nbIteration) {
	val model = RandomForest.trainClassifier(trainingData, numClasses, categoricalFeaturesInfo,
  numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins)
}

var timeEnd = System.currentTimeMillis();
var time = (timeEnd - timeStart)/nbIteration;
println("Temps moyen apprentissage : " + time + "ms")

timeStart = System.currentTimeMillis();

// Evaluate model on test instances and compute test error
val labelAndPreds = testData.map { point =>
  val prediction = model.predict(point.features)
  (point.label, prediction)
}

for(i <- 1 to nbIteration) {
	val labelAndPreds = testData.map { point =>
  val prediction = model.predict(point.features)
  (point.label, prediction)
}
}

timeEnd = System.currentTimeMillis();
time = (timeEnd - timeStart)/nbIteration;
println("Temps moyen analyse : " + time + "ms")

val testErr = labelAndPreds.filter(r => r._1 != r._2).count.toDouble / testData.count()
println(s"Test Error = $testErr")







