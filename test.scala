import org.apache.spark.mllib.tree.DecisionTree
import org.apache.spark.mllib.tree.model.DecisionTreeModel
import org.apache.spark.mllib.util.MLUtils

//val fileName = args(0)
//get the filename
println("file is "+fileName)
// Load and parse the data file.
val data = MLUtils.loadLibSVMFile(sc, fileName)
// Split the data into training and test sets (30% held out for testing)
val splits = data.randomSplit(Array(0.7, 0.3),13)
val (trainingData, testData) = (splits(0), splits(1))

// Train a DecisionTree model.
//  Empty categoricalFeaturesInfo indicates all features are continuous.
val numClasses = 8
val categoricalFeaturesInfo = Map[Int, Int]()
val impurity = "gini"
val maxDepth = 5
val maxBins = 32

var timeStart = System.currentTimeMillis();
val nbIteration = 22;

var model = DecisionTree.trainClassifier(trainingData, numClasses, categoricalFeaturesInfo,
  impurity, maxDepth, maxBins)

for(i <- 1 to nbIteration) {

model = DecisionTree.trainClassifier(trainingData, numClasses, categoricalFeaturesInfo,
  impurity, maxDepth, maxBins)
}

var timeEnd = System.currentTimeMillis();
var time = (timeEnd - timeStart)/nbIteration;
println("Temps moyen apprentissage : " + time + "ms")

timeStart = System.currentTimeMillis();

var labelAndPreds = testData.map { point =>
  val prediction = model.predict(point.features)
  (point.label, prediction)
}

for(i <- 1 to nbIteration) {

// Evaluate model on test instances and compute test error
labelAndPreds = testData.map { point =>
  val prediction = model.predict(point.features)
  (point.label, prediction)
}
}

timeEnd = System.currentTimeMillis();
time = (timeEnd - timeStart)/nbIteration;
println("Temps moyen analyse : " + time + "ms")

val testErr = labelAndPreds.filter(r => r._1 != r._2).count().toDouble / testData.count()
println("Test Error = " + testErr)

System.exit(0)
