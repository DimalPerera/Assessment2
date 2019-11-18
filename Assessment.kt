import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

const val MESSAGE = "It shouldn't break anything!"
const val FILE_NAME = "data.txt"

val words = arrayOf(
    "start", "citizen", "flour", "circle", "petty", "neck", "seem", "lake", "page",
    "color", "ceiling", "angle", "agent", "mild", "touch", "bite", "cause", "finance",
    "greet", "eat", "minor", "echo", "aviation", "baby", "role", "surround", "incapable",
    "refuse", "reliable", "imperial", "outer", "liability", "struggle", "harsh", "coerce",
    "front", "strike", "rage", "casualty", "artist", "ex", "transaction", "parking", "plug",
    "formulate", "press", "kettle", "export", "hiccup", "stem", "exception", "report", "central",
    "cancer", "volunteer", "professional", "teacher", "relax", "trip", "fountain", "effect",
    "news", "mark", "romantic", "policy", "contemporary", "conglomerate", "cotton", "happen",
    "contempt", "joystick", "champagne", "vegetation", "bat", "cylinder", "classify", "even",
    "surgeon", "slip", "private", "fox", "gravity", "aspect", "hypnothize", "generate",
    "miserable", "breakin", "love", "chest", "split", "coach", "pound", "sharp", "battery",
    "cheap", "corpse", "hobby", "mature", "attractive", "rock"
)

fun main(args: Array<String>) {

    println("############################ Tasks 1 ############################")
    writeDataToFile("############################ Tasks 1 ############################")

    for (i in 1..100) {
        println("$i: " + getRandomWordSync())
        writeDataToFile("$i: " + getRandomWordSync() + "\n")
    }

    println("############################ Tasks 2 ############################")
    writeDataToFile("############################ Tasks 2 ############################")


    var i: Int = 1

    while (i <= 100) {

        if (i % 15 == 0) {
            println("FizzBuzz")
            writeDataToFile("FizzBuzz\n")
        } else if (i % 3 == 0) {
            println("Fizz")
            writeDataToFile("Fizz\n")
        } else if (i % 5 == 0) {
            println("Buzz")
            writeDataToFile("Buzz\n")
        } else {
            println("$i " + words[(0..99).random()])
            writeDataToFile("$i " + words[(0..99).random()] + "\n")
        }

        i++

    }


    println("############################ Tasks 3 ############################")

    runBlocking() {
        val ad = getRandomWord()
        println(ad)
    }


}

fun getRandomWordSync(): String {
    val index = (0..99).random()
    return words[index]
}

fun getRandomFizzBuzzWordSync(): String {
    val index = (0..99).random()
    return words[index]
}


suspend fun getRandomWord(slow: Boolean = false): String {

    println("############################ Tasks 4 and 5 ############################")

    var randomWord: String

    val index = (0..199).random()

    try {

        if (index % 3 == 0 && index % 5 == 0)
            randomWord = "FizzBuzz"
        else if (index % 5 == 0)
            randomWord = "Buzz"
        else if (index % 3 == 0)
            randomWord = "Fizz"
        else
            randomWord = words[index]

    } catch (e: ArrayIndexOutOfBoundsException) {

        randomWord = MESSAGE

    }

    writeDataToFile(randomWord)


    if (slow)
        delay(500)
    return randomWord
}


fun writeDataToFile(fileContain: String) {

    try {

        var file = File(FILE_NAME)

        val isNewFileCreated: Boolean = file.createNewFile()

        if (isNewFileCreated) {
            file.writeText(fileContain)
        } else {
            file.appendText(fileContain)
        }

    } catch (e: IOException) {

        println("File writing error occurred ")

    }




}


fun postRequest(url: String, body: String): String {
    return URL(url)
        .openConnection()
        .let {
            it as HttpURLConnection
        }.apply {
            setRequestProperty("Content-Type", "application/json; charset=utf-8")
            requestMethod = "POST"

            doOutput = true
            val outputWriter = OutputStreamWriter(outputStream)
            outputWriter.write(body)
            outputWriter.flush()
        }.let {
            if (it.responseCode == 200) it.inputStream else it.errorStream
        }.let { streamToRead ->
            BufferedReader(InputStreamReader(streamToRead)).use {
                val response = StringBuffer()

                var inputLine = it.readLine()
                while (inputLine != null) {
                    response.append(inputLine)
                    inputLine = it.readLine()
                }
                it.close()
                response.toString()
            }
        }
}


